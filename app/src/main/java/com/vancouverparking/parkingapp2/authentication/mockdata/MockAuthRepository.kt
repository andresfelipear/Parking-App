package com.vancouverparking.parkingapp2.authentication.mockdata

import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository

class MockAuthRepository : RemoteAuthRepository
{
    private val mockUsers = mutableMapOf(
        "daniel@test.io" to "123456",
        "murillo@test.io" to "98765",
        "jorch@test.io" to "qwerty"
    )

    override suspend fun login(email: String,
                               password: String): String?
    {
        if(mockUsers.containsKey(email))
        {
            if(mockUsers[email] == password)
            {
                return "Token: MOCK_TOKEN $email"
            }
        }
        return null
    }

    override suspend fun signUp(fullName: String, email: String,
                                password: String): String?
    {
        if(mockUsers.containsKey(email))
        {
            return null
        }
        mockUsers[email] = password
        return "Token: MOCK_TOKEN $email"
    }

    override suspend fun forgotPassword(email: String,
                                        mobile: String): String?
    {
        if(mockUsers.containsKey(email))
        {
            return "Token: MOCK_TOKEN $email"
        }
        return null
    }

    override suspend fun resetPassword(email: String,
                                       password: String): String?
    {
        if(mockUsers.containsKey(email))
        {
            mockUsers[email] = password
            return "Token: MOCK_TOKEN $email $password"
        }
        return null
    }

    override suspend fun validateResetPasswordCode(code: String): String?
    {
        TODO("Not yet implemented")
    }
}