package com.vancouverparking.parkingapp2.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpVerificationBinding

class SignUpVerificationActivity : AppCompatActivity()
{
    private lateinit var binding: ActivitySignUpVerificationBinding
    private val boxes = mutableListOf<EditText>()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boxes.apply{
            add(binding.codeBox1)
            add(binding.codeBox2)
            add(binding.codeBox3)
            add(binding.codeBox4)
        }

        setBoxFocusListeners()
        setBoxTextListeners()
        binding.continueSignUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpLastStep::class.java))
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
}