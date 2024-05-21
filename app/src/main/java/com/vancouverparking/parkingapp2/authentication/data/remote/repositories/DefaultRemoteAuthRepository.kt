package com.vancouverparking.parkingapp2.authentication.data.remote.repositories

import android.app.Activity
import android.content.ContentValues.TAG
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.vancouverparking.parkingapp2.authentication.data.local.daos.UserDao
import com.vancouverparking.parkingapp2.authentication.data.local.entities.UserDetails
import com.vancouverparking.parkingapp2.authentication.data.remote.api.AuthenticationApi
import com.vancouverparking.parkingapp2.authentication.data.remote.request.ForgotPasswordRequest
import com.vancouverparking.parkingapp2.authentication.data.remote.request.LoginRequest
import com.vancouverparking.parkingapp2.authentication.data.remote.request.SignUpRequest
import com.vancouverparking.parkingapp2.authentication.di.AuthenticationModule
import com.vancouverparking.parkingapp2.authentication.di.DatabaseModule
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

const val TIME_FOR_RESEND_CODE = 120L

class DefaultRemoteAuthRepository(
        private val api: AuthenticationApi = AuthenticationModule.provideAuthenticationApi(),
        private val dao: UserDao = AuthenticationModule.provideTokenDao()
) : RemoteAuthRepository
{
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var verificationInProgress = false
    private var verificationIdDeferred: CompletableDeferred<String?>? = null
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    val phoneNumber = "+16505554567"
    val smsCode = "123456"

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        override fun onVerificationCompleted(credential: PhoneAuthCredential)
        {
            Log.d(TAG, "onVerificationCompleted:$credential")
            println("Verification completed")
            verificationInProgress = false
        }

        override fun onVerificationFailed(e: FirebaseException)
        {
            println("Verification failed")
            if(e is FirebaseAuthInvalidCredentialsException)
            {
                // Invalid request
                // Handle invalid phone number
            }
            else if(e is FirebaseTooManyRequestsException)
            {
                // Handle SMS quota exceeded
            }
        }

        override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
        ) {
            println("Verification onCodeSent")
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token
            verificationIdDeferred?.complete(verificationId)
        }

    }


    override suspend fun login(email: String,
                               password: String): String?
    {
        try
        {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if(authResult.user != null)
            {
                return authResult.user!!.uid
            }
            return null
        }
        catch(exception: FirebaseAuthException)
        {
            exception.printStackTrace()
            return null
        }
    }

    override suspend fun signUp(fullname: String,
                                email: String,
                                password: String): String?
    {
        try
        {
            // Create a user in Firebase Authentication

            val userRecord = firebaseAuth.createUserWithEmailAndPassword(email, password)

            val user = firebaseAuth.currentUser
            dao.insertUser(
                UserDetails(user!!.uid, email, 0, fullname))
            return user!!.uid
        }
        catch(exception: FirebaseAuthException)
        {
            exception.printStackTrace()
            return null
        }
    }

    override suspend fun forgotPassword(email: String,
                                        mobile: String): String?
    {
        val forgotPasswordRequest = ForgotPasswordRequest(email, mobile)
        return try
        {
            val response = api.forgotPassword(forgotPasswordRequest)
            response.token
        }
        catch(exception: Exception)
        {
            exception.printStackTrace()
            null
        }
    }

    override suspend fun resetPassword(email: String,
                                       password: String): String?
    {
        TODO("Not yet implemented")
    }

    override suspend fun validateResetPasswordCode(recoveryCode: String,
                                                   email: String): String?
    {
        TODO("Not yet implemented")
    }

    override suspend fun sendVerificationCode(phoneNumber: String): String?
    {
        try
        {
            // Configure faking the auto-retrieval with the whitelisted numbers.
            firebaseAuth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)
            //firebaseAuth.firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode)

            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(TIME_FOR_RESEND_CODE, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)

            verificationInProgress = true

            return getVerificationId()
        }
        catch(exception: Exception)
        {
            exception.printStackTrace()
            return null
        }
    }

    /**
     * This function waits until the verification id is assigned to the storedVerificationId variable
     */
    private suspend fun getVerificationId(): String? {
        val deferred = CompletableDeferred<String?>()
        verificationIdDeferred = deferred
        return deferred.await()
    }


    override suspend fun verifyVerificationCode(verificationId: String,
                                                verificationCode: String): String?
    {
        try
        {
            var user: FirebaseUser? = null
            val credential = PhoneAuthProvider.getCredential(verificationId!!, verificationCode)

            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful)
                    {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")

                        user = task.result?.user!!
                    }
                    else
                    {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException)
                        {
                            user = null
                        }
                    }
                }
            return user.toString()
        }
        catch(exception: Exception)
        {
            exception.printStackTrace()
            return null
        }
    }

    private fun resendVerificationCode(
            phoneNumber: String,
            token: PhoneAuthProvider.ForceResendingToken?,
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(TIME_FOR_RESEND_CODE, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(Activity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }



}