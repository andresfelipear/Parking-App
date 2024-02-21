package com.vancouverparking.parkingapp2.authentication.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.authentication.viewmodels.ForgotPasswordState
import com.vancouverparking.parkingapp2.authentication.viewmodels.ForgotPasswordViewModel
import com.vancouverparking.parkingapp2.authentication.viewmodels.LoginState
import com.vancouverparking.parkingapp2.databinding.ActivityForgotPasswordBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val SHORTEST_EMAIL_ADDRESS = 4

class ForgotPasswordActivity : AppCompatActivity()
{
    private val viewModel: ForgotPasswordViewModel by viewModels()
    private var binding: ActivityForgotPasswordBinding ? = null
    private var loadingDialog: Dialog? = null
    private var errorDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)

        loadingDialog = Dialog(this)
        loadingDialog?.setContentView(R.layout.loading_dialog)
        loadingDialog?.setCancelable(false)
        loadingDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        errorDialog = AlertDialog.Builder(this)
            .setTitle(R.string.feat_forgot_password_invalid_email_mobile_title)
            .setPositiveButton(android.R.string.ok, null)
            .create()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest{ state ->
                    invalidate(state)
                }
            }
        }

        binding?.phoneEmail?.doAfterTextChanged {
            binding?.submitButton?.isEnabled = isValidForm()
        }

        binding?.submitButton?.setOnClickListener {
            handleSubmit()
        }

        enableActionBar(true)


    }


    private fun showErrorDialog(state: ForgotPasswordState) {
        if (errorDialog != null && !errorDialog!!.isShowing) {
            errorDialog?.setMessage(getString(R.string.feat_login_error, state.error.toString()))
            errorDialog?.show()
        }
    }

    private fun invalidate(state: ForgotPasswordState)
    {
        if(state.isLoading)
        {
            loadingDialog?.show()
        }
        else
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
            val intent = Intent(this, ResetPasswordVerificationCodeActivity::class.java)
            intent.putExtra("email", binding?.phoneEmail?.text.toString())
            startActivity(intent)

            finish()
        }
    }

    private fun handleSubmit()
    {
        viewModel.forgotPassword(binding?.phoneEmail?.text.toString())

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

    private fun isValidForm(): Boolean
    {
        return binding?.phoneEmail?.text.toString().isNotEmpty() &&
                (binding?.phoneEmail?.text.toString().length >= SHORTEST_EMAIL_ADDRESS)
    }

    override fun onResume()
    {
        super.onResume()
        binding?.submitButton?.isEnabled = isValidForm()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        binding = null
    }
}