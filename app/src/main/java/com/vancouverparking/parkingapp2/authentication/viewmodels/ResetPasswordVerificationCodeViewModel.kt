package com.vancouverparking.parkingapp2.authentication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.di.AuthenticationModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ResetPasswordVerificationCodeState(
        val token: String? = null,
        val isLoading: Boolean = false,
        val error: Throwable? = null,
)
class ResetPasswordVerificationCodeViewModel(
    private val repository: RemoteAuthRepository = AuthenticationModule.provideRemoteRepository()
): ViewModel()
{
    private val internalState = MutableStateFlow(ResetPasswordVerificationCodeState())
    val state: StateFlow<ResetPasswordVerificationCodeState> = internalState

    fun validateRecoveryCode(recoveryCode: String, email: String)
    {
        internalState.value = internalState.value.copy(
            isLoading = true
        )

        viewModelScope.launch(Dispatchers.IO)
        {
            val token = repository.validateResetPasswordCode(recoveryCode.trim().lowercase(), email.trim().lowercase())

            internalState.value = internalState.value.copy(
                isLoading = false,
                token = token,
                error = if(token != null)
                {
                    null
                }
                else
                {
                    RuntimeException("NO TOKEN")
                }
            )

        }
    }

    fun clearState()
    {
        internalState.value = ResetPasswordVerificationCodeState()
    }

}