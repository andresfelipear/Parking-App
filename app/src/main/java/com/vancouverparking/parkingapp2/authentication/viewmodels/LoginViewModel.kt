package com.vancouverparking.parkingapp2.authentication.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.di.AuthenticationModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginState(
        val isLoading: Boolean = false,
        val error: Throwable? = null,
        val token: String? = null,
)

class LoginViewModel(
        private val repository: RemoteAuthRepository = AuthenticationModule.provideRemoteRepository()
) : ViewModel()
{
    private val internalState = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = internalState

    fun login(email: String,
              password: String)
    {
        internalState.value = internalState.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            val token = repository.login(email.trim()
                .lowercase(),
                password.trim())

            internalState.value = internalState.value.copy(
                isLoading = false,
                token = token,
                error = if(token != null)
                {
                    null
                } else
                {
                    RuntimeException("Error NO TOKEN")
                }
            )
        }
    }

    fun clearState()
    {
        internalState.value = LoginState()
    }

}
