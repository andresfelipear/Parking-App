package com.vancouverparking.parkingapp2.authentication.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vancouverparking.parkingapp2.MainActivity
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.authentication.viewmodels.LoginState
import com.vancouverparking.parkingapp2.authentication.viewmodels.ResetPasswordState
import com.vancouverparking.parkingapp2.authentication.viewmodels.ResetPasswordViewModel
import com.vancouverparking.parkingapp2.databinding.ActivityResetPasswordBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ResetPasswordActivity : AppCompatActivity()
{
    private val viewModel: ResetPasswordViewModel by viewModels()
    private var binding: ActivityResetPasswordBinding? = null
    private var loadingDialog: Dialog? = null
    private var errorDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    invalidate(state)
                }
            }
        }


        binding?.password?.doAfterTextChanged {
            binding?.resetPasswordButton?.isEnabled = validateForm()
        }

        binding?.confirmPassword?.doAfterTextChanged {
            binding?.resetPasswordButton?.isEnabled = validateForm()
        }

        binding?.resetPasswordButton?.setOnClickListener {
            handleResetPassword()
        }

        enableActionBar(true)
    }

    private fun showErrorDialog(state: ResetPasswordState)
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
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)

        binding?.toolbar?.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun invalidate(state: ResetPasswordState)
    {
        if(state.isLoading)
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
            Toast.makeText(
                this,
                R.string.feat_reset_password_success,
                Toast.LENGTH_SHORT
            ).show()

            startActivity(Intent(this,
                LoginActivity::class.java))
            finish()
        }

    }

    private fun handleResetPassword()
    {
        val email = intent.getStringExtra("email")
        if(email != null)
        {
            viewModel.resetPassword(email, binding?.password?.text.toString(), )
        }
    }

    private fun validateForm(): Boolean
    {
        return binding?.password?.text.toString()
            .isNotEmpty() &&
                binding?.confirmPassword?.text.toString()
                    .isNotEmpty() &&
                binding?.password?.text.toString() == binding?.confirmPassword?.text.toString() &&
                binding?.password?.text.toString().length >= 6
    }

    override fun onDestroy()
    {
        super.onDestroy()
        binding = null;
    }

    override fun onResume()
    {
        super.onResume()
        binding?.resetPasswordButton?.isEnabled = validateForm()
    }
}