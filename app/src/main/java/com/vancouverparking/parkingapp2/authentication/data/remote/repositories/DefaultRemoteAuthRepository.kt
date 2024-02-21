package com.vancouverparking.parkingapp2.authentication.data.remote.repositories

import com.vancouverparking.parkingapp2.authentication.data.remote.api.AuthenticationApi
import com.vancouverparking.parkingapp2.authentication.data.remote.request.ForgotPasswordRequest
import com.vancouverparking.parkingapp2.authentication.data.remote.request.LoginRequest
import com.vancouverparking.parkingapp2.authentication.data.remote.request.SignUpRequest
import com.vancouverparking.parkingapp2.authentication.di.AuthenticationModule

class DefaultRemoteAuthRepository(
    private val api: AuthenticationApi = AuthenticationModule.provideAuthenticationApi()
): RemoteAuthRepository
{
    override suspend fun login(email: String,
                               password: String): String?
    {

        val loginRequest = LoginRequest(email, password)

        return try
        {
            val response = api.login(loginRequest)
            response.token
        }
        catch(exception: Exception)
        {
            exception.printStackTrace()
            null
        }
    }

    override suspend fun signUp(fullname: String,
                                email: String,
                                password: String): String?
    {
        val signUpRequest = SignUpRequest(email, password)
        return try
        {
            val response = api.signUp(signUpRequest)
            response.token
        }
        catch(exception: Exception)
        {
            exception.printStackTrace()
            null
        }
    }

    override suspend fun forgotPassword(email: String,
                                        mobile: String): String?
    {
        val forgotPasswordRequest = ForgotPasswordRequest(email,mobile)
        return  try {
            val response = api.forgotPassword(forgotPasswordRequest)
            response.token
        } catch (exception: Exception){
            exception.printStackTrace()
            null
        }
    }

    override suspend fun resetPassword(email: String,
                                       password: String): String?
    {
        TODO("Not yet implemented")
    }

    override suspend fun validateResetPasswordCode(code: String): String?
    {
        TODO("Not yet implemented")
    }


}