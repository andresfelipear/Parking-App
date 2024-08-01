package com.vancouverparking.parkingapp2.authentication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.DefaultRemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SignupState(
        val isLoading: Boolean = false,
        val error: Throwable? = null,
        val token: String? = null,
        val isSuccess: Boolean = false
)

class SignupViewModel(
        private val repository: RemoteAuthRepository = DefaultRemoteAuthRepository()
) : ViewModel()
{
    private val internalState = MutableStateFlow(SignupState())
    val state: StateFlow<SignupState> = internalState

    fun signup(username: String,
               email: String,
               password: String)
    {
        internalState.value = internalState.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO)
        {
            val token = repository.signUp(username.trim().lowercase(),
                email.trim().lowercase(),
                password.trim())

            internalState.value = internalState.value.copy(
                isLoading = false,
                token = token,
                error = if(token != null)
                {
                    null
                }
                else
                {
                    RuntimeException("Error NO TOKEN")
                }
            )
        }
    }

    fun clearState()
    {
        internalState.value = SignupState()
    }

}