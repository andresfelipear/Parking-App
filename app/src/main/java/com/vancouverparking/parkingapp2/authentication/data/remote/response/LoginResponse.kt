package com.vancouverparking.parkingapp2.authentication.data.remote.response

data class LoginResponse(
    val token: String? = null,
    val error: String? = null
)
