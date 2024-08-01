package com.vancouverparking.parkingapp2.authentication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.DefaultRemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.di.AuthenticationModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ResetPasswordState(
        val token: String? = null,
        val isLoading: Boolean = false,
        val error: Throwable? = null,
)

class ResetPasswordViewModel(
        private val repository: RemoteAuthRepository = AuthenticationModule.provideRemoteRepository()

) : ViewModel()
{
    private val internalState = MutableStateFlow(ResetPasswordState())
    val state: StateFlow<ResetPasswordState> = internalState
    fun resetPassword(email: String,
                      password: String)
    {
        internalState.value = internalState.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            val token = repository.resetPassword(email.trim()
                .lowercase(),
                password.trim())
            internalState.value = internalState.value.copy(
                isLoading = false,
                token = token,
                error = if(token != null){
                    null
                }
                else
                {
                    RuntimeException("Error Reseating password")
                }
            )
        }
    }

    fun clearState()
    {
        internalState.value = ResetPasswordState()
    }


}


