package com.vancouverparking.parkingapp2.authentication.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.di.AuthenticationModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

data class ForgotPasswordState(
        val token: String? = null,
        val isLoading: Boolean = false,
        val error: Throwable? = null,
)

class ForgotPasswordViewModel(
        private val repository: RemoteAuthRepository = AuthenticationModule.provideRemoteRepository()
) : ViewModel()
{
    private val internalState = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = internalState

    fun forgotPassword(emailMobile: String)
    {
        val isValidEmailMobile = isValidEmailMobile(emailMobile)

        if(isValidEmailMobile)
        {
            internalState.value = internalState.value.copy(
                isLoading = true
            )
            viewModelScope.launch(Dispatchers.IO) {

                val token = if(Patterns.EMAIL_ADDRESS.matcher(emailMobile)
                        .matches())
                {
                    repository.forgotPassword(emailMobile.trim(),
                        "")
                } else
                {
                    repository.forgotPassword("",
                        emailMobile)
                }

                internalState.value = internalState.value.copy(
                    isLoading = false,
                    token = token,
                    error = if(token != null)
                    {
                        null
                    }
                    else
                    {
                        RuntimeException("No search results.")
                    }
                )
            }
        }
    }

    private fun isValidEmailMobile(emailMobile: String): Boolean
    {
        val cleanEmailMobile = emailMobile.trim()
            .lowercase()
        val emailPattern = Patterns.EMAIL_ADDRESS
        val phonePattern = Patterns.PHONE
        return when
        {
            emailPattern.matcher(cleanEmailMobile)
                .matches() ->
            {
                true
            }

            phonePattern.matcher(cleanEmailMobile)
                .matches() ->
            {
                true
            }

            else ->
            {
                internalState.value = internalState.value.copy(
                    error = IllegalArgumentException("Invalid email or phone.")
                )
                false
            }
        }

    }

}
