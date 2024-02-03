package com.vancouverparking.parkingapp2.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.vancouverparking.parkingapp2.MainActivity
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpBinding
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpLastStepBinding

class SignUpLastStep : AppCompatActivity()
{
    private var binding: ActivitySignUpLastStepBinding? = null
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

    override fun onResume()
    {
        super.onResume()
        binding?.signupButton?.isEnabled = isValidForm()
    }

    private fun isValidForm(): Boolean
    {
        return binding?.email?.text.toString()
            .isNotEmpty() &&
                binding?.password?.text.toString()
                    .isNotEmpty() &&
                binding?.username?.text.toString()
                    .isNotEmpty()
    }


    override fun onDestroy()
    {
        super.onDestroy()
        binding = null
    }
}