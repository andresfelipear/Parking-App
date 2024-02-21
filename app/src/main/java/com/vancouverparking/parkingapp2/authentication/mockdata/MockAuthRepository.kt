package com.vancouverparking.parkingapp2.authentication.mockdata

import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository

class MockAuthRepository : RemoteAuthRepository
{
    private val mockUsers = mutableMapOf(
        "daniel@test.io" to "123456",
        "murillo@test.io" to "98765",
        "jorch@test.io" to "qwerty"
    )

    private val mockRecoveryCodes = mutableMapOf(
        "daniel@test.io" to "1414",
        "murillo@test.io" to "2202",
        "jorch@test.io" to "0812"
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

    override suspend fun validateResetPasswordCode(recoveryCode: String, email: String): String?
    {
        if(mockRecoveryCodes.containsKey(email))
        {
            if(mockRecoveryCodes[email] == recoveryCode)
            {
                return "Token: MOCK_TOKEN $email $recoveryCode"
            }
        }
        return null
    }
}