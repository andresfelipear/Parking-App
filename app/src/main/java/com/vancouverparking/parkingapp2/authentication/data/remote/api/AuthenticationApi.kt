package com.vancouverparking.parkingapp2.authentication.data.remote.api

import com.vancouverparking.parkingapp2.authentication.data.remote.request.LoginRequest
import com.vancouverparking.parkingapp2.authentication.data.remote.request.SignUpRequest
import com.vancouverparking.parkingapp2.authentication.data.remote.response.LoginResponse
import com.vancouverparking.parkingapp2.authentication.data.remote.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi
{
    @POST("/api/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("/api/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponse

}
