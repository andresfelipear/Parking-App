package com.vancouverparking.parkingapp2.authentication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.di.AuthenticationModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SignUpPhoneState(
        val isLoading: Boolean = false,
        val error: Throwable? = null,
        val isSuccess: Boolean = false
)
class SignUpPhoneViewModel(
    private val repository: RemoteAuthRepository = AuthenticationModule.provideRemoteRepository()
):ViewModel()
{
    private val internalState = MutableStateFlow(SignUpPhoneState())
    val state:StateFlow<SignUpPhoneState> = internalState

    fun sendVerificationCode(phoneNumber: String)
    {
        internalState.value = internalState.value.copy(
            isLoading = true
        )

        viewModelScope.launch(Dispatchers.IO)
        {
            val result = repository.sendVerificationCode(phoneNumber.trim())

            internalState.value = internalState.value.copy(
                isLoading = false,
                isSuccess = result == true,
                error = if(result == true){
                    null
                }
                else
                {
                    RuntimeException("Error sending verification code!")
                }
            )

        }

    }

    fun clearState()
    {
        internalState.value = SignUpPhoneState()
    }


}
