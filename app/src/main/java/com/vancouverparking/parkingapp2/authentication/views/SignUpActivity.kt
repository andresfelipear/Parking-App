package com.vancouverparking.parkingapp2.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.authentication.adapters.CountryAdapter
import com.vancouverparking.parkingapp2.core.domain.Country
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val countryList = listOf(
            Country("+1", R.drawable.ic_flag_canada),
            Country("+1", R.drawable.ic_flag_united_states),
            Country("+57", R.drawable.ic_flag_colombia),
            Country("+86", R.drawable.ic_flag_china),
            Country("+44", R.drawable.ic_flag_england),
            Country("+55", R.drawable.ic_flag_brazil),
            Country("+91", R.drawable.ic_flag_india),
            Country("+34", R.drawable.ic_flag_spain)
        )

        val adapter = CountryAdapter(this, countryList)

        binding.spinnerCountries.adapter = adapter

        binding.continueSignUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpVerificationActivity ::class.java))
        }


    }
}