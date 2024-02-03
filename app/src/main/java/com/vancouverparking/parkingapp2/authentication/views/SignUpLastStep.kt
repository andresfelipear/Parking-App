package com.vancouverparking.parkingapp2.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpLastStepBinding

class SignUpLastStep : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpLastStepBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}