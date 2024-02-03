package com.vancouverparking.parkingapp2.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.textfield.TextInputEditText
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.databinding.ActivityLoginBinding
import com.vancouverparking.parkingapp2.authentication.viewmodels.LoginState
import com.vancouverparking.parkingapp2.authentication.viewmodels.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity()
{

    private val viewModel: LoginViewModel by viewModels()
    private var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)

        binding?.loginButton?.setOnClickListener {
            handleLogin()

        }

        binding?.forgotPassword?.setOnClickListener {
            startActivity(Intent(this,
                ForgotPasswordActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest{ state ->
                    invalidate(state)
                }
            }
        }

        enableActionBar(true)


        binding?.email?.doAfterTextChanged {
            binding?.loginButton?.isEnabled = isValidForm()
        }

        binding?.password?.doAfterTextChanged {
            binding?.loginButton?.isEnabled = isValidForm()
        }


    }

    private fun enableActionBar(enable: Boolean)
    {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
        supportActionBar?.setDisplayShowHomeEnabled(enable)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding?.toolbar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onResume()
    {
        super.onResume()
        binding?.loginButton?.isEnabled = isValidForm()
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
                R.string.feat_login_bad_credentials,
                Toast.LENGTH_SHORT
            )
                .show()
        }
        if(state.token != null)
        {
            println("Login Success!")
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