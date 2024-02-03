package com.vancouverparking.parkingapp2.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.vancouverparking.parkingapp2.MainActivity
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpLastStepBinding
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpVerificationBinding

class SignUpVerificationActivity : AppCompatActivity()
{
    private var binding: ActivitySignUpVerificationBinding? = null
    private val boxes = mutableListOf<EditText>()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpVerificationBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)

        boxes.apply{
            binding?.codeBox1?.let { add(it) }
            binding?.codeBox2?.let { add(it) }
            binding?.codeBox3?.let { add(it) }
            binding?.codeBox4?.let { add(it) }
        }

        setBoxFocusListeners()
        setBoxTextListeners()
        binding?.continueSignUpButton?.setOnClickListener {
            startActivity(Intent(this, SignUpLastStep::class.java))
        }

        enableActionBar(true)

        binding?.codeBox1?.doAfterTextChanged {
            binding?.continueSignUpButton?.isEnabled = isValidForm()
        }
        binding?.codeBox2?.doAfterTextChanged {
            binding?.continueSignUpButton?.isEnabled = isValidForm()
        }
        binding?.codeBox3?.doAfterTextChanged {
            binding?.continueSignUpButton?.isEnabled = isValidForm()
        }
        binding?.codeBox4?.doAfterTextChanged {
            binding?.continueSignUpButton?.isEnabled = isValidForm()
        }

    }

    private fun setBoxFocusListeners()
    {
        for(box in boxes)
        {
            box.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && box.text.isNullOrEmpty())
                {
                    box.requestFocus()
                }

            }
        }

        // Set the last box's focus change listener
        boxes.last().setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && boxes.last().text.isNullOrEmpty())
            {
                boxes.last().requestFocus()
            }
        }
    }

    private fun setBoxTextListeners()
    {
        for (i in 0 until boxes.size - 1)
        {
            boxes[i].addTextChangedListener(object : TextWatcher
            {
                override fun beforeTextChanged(
                        charSequence: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                ) {
                    // No implementation needed
                }

                override fun onTextChanged(
                        charSequence: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                ) {
                    if (count == 1) {
                        // Move focus to the next box
                        boxes[i + 1].requestFocus()
                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                    // No implementation needed
                }
            })
        }

        // Set the last box's text change listener
        boxes.last().addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(
                    charSequence: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
            ) {
                // No implementation needed
            }

            override fun onTextChanged(
                    charSequence: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
            ) {
                // Additional handling if needed for the last box
            }

            override fun afterTextChanged(editable: Editable?) {
                // No implementation needed
            }
        })
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
        binding?.continueSignUpButton?.isEnabled = isValidForm()
    }

    private fun isValidForm(): Boolean
    {
        return binding?.codeBox1?.text.toString()
            .isNotEmpty() &&
                binding?.codeBox2?.text.toString()
                    .isNotEmpty()&&
                binding?.codeBox3?.text.toString()
                    .isNotEmpty()&&
                binding?.codeBox4?.text.toString()
                    .isNotEmpty()
    }


    override fun onDestroy()
    {
        super.onDestroy()
        binding = null
    }
}