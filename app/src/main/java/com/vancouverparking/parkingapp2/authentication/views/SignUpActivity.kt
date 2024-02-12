package com.vancouverparking.parkingapp2.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.authentication.adapters.CountryAdapter
import com.vancouverparking.parkingapp2.authentication.data.getListOfCountries
import com.vancouverparking.parkingapp2.core.domain.Country
import com.vancouverparking.parkingapp2.databinding.ActivityLoginBinding
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private var binding: ActivitySignUpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)


        val adapter = CountryAdapter(this, getListOfCountries())

        binding?.spinnerCountries?.adapter = adapter

        binding?.continueSignUpButton?.setOnClickListener {
            startActivity(Intent(this, SignUpVerificationActivity ::class.java))
        }

        enableActionBar(true)

        binding?.phoneNumber?.doAfterTextChanged {
            binding?.continueSignUpButton?.isEnabled = isValidForm()
        }

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


    override fun onDestroy()
    {
        super.onDestroy()
        binding = null
    }
}