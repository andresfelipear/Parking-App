package com.vancouverparking.parkingapp2.authentication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.DefaultRemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SignUpVerificationCodeState(
        val isLoading: Boolean = false,
        val error: Throwable? = null,
        val isSuccess: Boolean = false
)
class SignUpVerificationCodeViewModel(
    private val repository: RemoteAuthRepository = DefaultRemoteAuthRepository()
) : ViewModel()
{
    private val internalState = MutableStateFlow(SignUpVerificationCodeState())
    val state: StateFlow<SignUpVerificationCodeState> = internalState

    fun verifyCode(verificationId: String,
                   verificationCode: String)
    {
        internalState.value = internalState.value.copy(
            isLoading = true
        )

        println("Verification code and id")
        println(verificationId)
        println(verificationCode)

        viewModelScope.launch(Dispatchers.IO)
        {
            val result = repository.verifyVerificationCode(verificationId.trim(), verificationCode.trim())

            internalState.value = internalState.value.copy(
                isLoading = false,
                isSuccess = result != null,
                error = if(result != null){
                    null
                }
                else
                {
                    RuntimeException("Invalid verification code, try again!")
                }
            )

        }
    }

    fun clearState()
    {
        internalState.value = SignUpVerificationCodeState()
    }


}
