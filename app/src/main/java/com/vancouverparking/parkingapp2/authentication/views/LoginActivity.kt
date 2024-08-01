package com.vancouverparking.parkingapp2.authentication.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
    private var loadingDialog: Dialog? = null
    private var errorDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)

        loadingDialog = Dialog(this)
        loadingDialog?.setContentView(R.layout.loading_dialog)
        loadingDialog?.setCancelable(false)
        loadingDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        errorDialog = AlertDialog.Builder(this)
            .setTitle(R.string.feat_login_error_bad_credentials_title)
            .setPositiveButton(android.R.string.ok,
                null)
            .create()

        binding?.loginButton?.setOnClickListener {
            handleLogin()
        }

        binding?.forgotPassword?.setOnClickListener {
            startActivity(Intent(this,
                ForgotPasswordActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
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

    private fun showErrorDialog(state: LoginState)
    {
        if(errorDialog != null && !errorDialog!!.isShowing)
        {
            errorDialog?.setMessage(getString(R.string.feat_login_error_bad_credentials_body))
            errorDialog?.show()
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
        return isValidEmail(binding?.email?.text.toString()) &&
                isValidPassword(binding?.password?.text.toString())
    }

    private fun isValidEmail(email: String): Boolean
    {
        val cleanEmail = email.trim()
            .lowercase()
        return Patterns.EMAIL_ADDRESS.matcher(cleanEmail)
            .matches() && email.isNotEmpty()
    }

    private fun isValidPassword(password: String): Boolean
    {
        val cleanPassword = password.trim()
        return cleanPassword.isNotEmpty() && cleanPassword.length >= 6
    }

    private fun invalidate(state: LoginState)
    {
        if(state.isLoading && loadingDialog != null)
        {
            loadingDialog?.show()
        } else
        {
            loadingDialog?.dismiss()
        }
        if(state.error != null)
        {
            showErrorDialog(state)
            viewModel.clearState()
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
        viewModel.login(binding?.email?.text.toString(),
            binding?.password?.text.toString())
    }

}