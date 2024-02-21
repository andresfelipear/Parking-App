package com.vancouverparking.parkingapp2.authentication.data.remote.request

data class ForgotPasswordRequest(
    val email: String,
    val mobile: String,
)
