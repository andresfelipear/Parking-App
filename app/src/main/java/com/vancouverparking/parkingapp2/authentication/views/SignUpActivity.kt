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
import com.vancouverparking.parkingapp2.authentication.adapters.CountryAdapter
import com.vancouverparking.parkingapp2.authentication.data.CountryCode
import com.vancouverparking.parkingapp2.authentication.data.getListOfCountries
import com.vancouverparking.parkingapp2.authentication.viewmodels.LoginState
import com.vancouverparking.parkingapp2.authentication.viewmodels.LoginViewModel
import com.vancouverparking.parkingapp2.authentication.viewmodels.SignUpPhoneState
import com.vancouverparking.parkingapp2.authentication.viewmodels.SignUpPhoneViewModel
import com.vancouverparking.parkingapp2.core.domain.Country
import com.vancouverparking.parkingapp2.databinding.ActivityLoginBinding
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    private val viewModel: SignUpPhoneViewModel by viewModels()
    private var binding: ActivitySignUpBinding? = null
    private var loadingDialog: Dialog? = null
    private var errorDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
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

        val adapter = CountryAdapter(this, getListOfCountries())

        binding?.spinnerCountries?.adapter = adapter

        binding?.continueSignUpButton?.setOnClickListener {
            handlePhoneNumber()
        }

        enableActionBar(true)

        binding?.phoneNumber?.doAfterTextChanged {
            binding?.continueSignUpButton?.isEnabled = isValidForm()
        }

    }

    private fun handlePhoneNumber()
    {
        val countryCode:CountryCode = binding?.spinnerCountries?.selectedItem as CountryCode
        val phoneNumber = countryCode.countryPhoneCode + binding?.phoneNumber?.text.toString()
        viewModel.sendVerificationCode(phoneNumber)
    }

    private fun enableActionBar(enable: Boolean)
    {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding?.toolbar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
    override fun onResume()
    {
        super.onResume()
        binding?.continueSignUpButton?.isEnabled = isValidForm()
    }

    private fun isValidForm(): Boolean
    {
        return binding?.phoneNumber?.text.toString()
            .isNotEmpty()
    }

    private fun showErrorDialog(state: SignUpPhoneState)
    {
        if(errorDialog != null && !errorDialog!!.isShowing)
        {
            errorDialog?.setMessage(getString(R.string.feat_signup_error_send_code))
            errorDialog?.show()
        }
    }

    private fun invalidate(state: SignUpPhoneState)
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
        if(state.isSuccess)
        {
            println("Success code sent!")
            startActivity(Intent(this, SignUpVerificationActivity ::class.java))
            finish()
        }
    }


    override fun onDestroy()
    {
        super.onDestroy()
        binding = null
    }
}