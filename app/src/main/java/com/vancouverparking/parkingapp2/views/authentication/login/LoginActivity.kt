package com.vancouverparking.parkingapp2.views.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.databinding.ActivityLoginBinding
import com.vancouverparking.parkingapp2.viewmodels.LoginViewModel.LoginViewModel
import com.vancouverparking.parkingapp2.views.authentication.forgotPassword.ForgotPasswordActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.loginButton?.setOnClickListener {
            handleLogin()

        }

        binding?.forgotPassword?.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

    }

    private fun handleLogin()
    {
        val email = findViewById<TextInputEditText>(R.id.email)
        val password = findViewById<TextInputEditText>(R.id.password)

        viewModel.login(email.text.toString(), password.text.toString())
    }

}