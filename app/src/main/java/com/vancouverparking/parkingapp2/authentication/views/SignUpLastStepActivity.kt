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
import com.vancouverparking.parkingapp2.MainActivity
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.authentication.viewmodels.LoginState
import com.vancouverparking.parkingapp2.authentication.viewmodels.SignupState
import com.vancouverparking.parkingapp2.authentication.viewmodels.SignupViewModel
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpLastStepBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignUpLastStepActivity : AppCompatActivity()
{
    private var binding: ActivitySignUpLastStepBinding? = null
    private val viewModel: SignupViewModel by viewModels()
    private var loadingDialog: Dialog? = null
    private var errorDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpLastStepBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)

        enableActionBar(true)

        binding?.email?.doAfterTextChanged {
            binding?.signupButton?.isEnabled = isValidForm()
        }
        binding?.password?.doAfterTextChanged {
            binding?.signupButton?.isEnabled = isValidForm()
        }
        binding?.username?.doAfterTextChanged {
            binding?.signupButton?.isEnabled = isValidForm()
        }

        binding?.signupButton?.setOnClickListener {
            handleSignup()
        }

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

    }

    private fun invalidate(state: SignupState)
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
        }
        if(state.token != null)
        {
            println("Signup Success")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun handleSignup()
    {
        viewModel.signup(binding?.username?.text.toString(),
            binding?.email?.text.toString(),
            binding?.password?.text.toString())
    }

    private fun showErrorDialog(state: SignupState)
    {
        if(errorDialog != null && !errorDialog!!.isShowing)
        {
            errorDialog?.setMessage(getString(R.string.feat_signup_error_bad_credentials_body))
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
            val intent = Intent(this,
                MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    override fun onResume()
    {
        super.onResume()
        binding?.signupButton?.isEnabled = isValidForm()
    }


    private fun isValidForm(): Boolean
    {
        return isValidEmail(binding?.email?.text.toString()) &&
                isValidPassword(binding?.password?.text.toString()) &&
                binding?.username?.text.toString().isNotEmpty()
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


    override fun onDestroy()
    {
        super.onDestroy()
        binding = null
    }
}