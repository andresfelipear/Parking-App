package com.vancouverparking.parkingapp2.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.databinding.ActivityLoginBinding
import com.vancouverparking.parkingapp2.authentication.viewmodels.LoginState
import com.vancouverparking.parkingapp2.authentication.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity()
{

    private val viewModel: LoginViewModel by viewModels()
    private var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.loginButton?.setOnClickListener {
            handleLogin()

        }

        binding?.forgotPassword?.setOnClickListener {
            startActivity(Intent(this,
                ForgotPasswordActivity::class.java))
        }

    }

    override fun onResume()
    {
        super.onResume()
        binding?.login?.isEnabled = isValidForm()
    }

    private fun isValidForm(): Boolean
    {
        return binding?.email?.text.toString()
            .isNotEmpty() &&
                binding?.password?.text.toString()
                    .isNotEmpty()
    }

    private fun invalidate(state: LoginState)
    {
        if(state.isLoading)
        {
            Toast.makeText(
                this,
                R.string.feat_login_loading,
                Toast.LENGTH_SHORT
            )
                .show()
        } else
        {
            // TODO: If loading dialog is shown, cancel it
        }
        if(state.error != null)
        {
            Toast.makeText(
                this,
                getString(R.string.feat_login_error,
                    state.error.toString()),
                Toast.LENGTH_SHORT
            )
                .show()
        }
        if(state.token != null)
        {
            finish()
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        binding = null
    }


    private fun handleLogin()
    {
        val email = findViewById<TextInputEditText>(R.id.email)
        val password = findViewById<TextInputEditText>(R.id.password)

        viewModel.login(email.text.toString(),
            password.text.toString())
    }

}