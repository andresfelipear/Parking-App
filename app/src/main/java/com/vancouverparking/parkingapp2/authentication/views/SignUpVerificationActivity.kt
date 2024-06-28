package com.vancouverparking.parkingapp2.authentication.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vancouverparking.parkingapp2.MainActivity
import com.vancouverparking.parkingapp2.R
import com.vancouverparking.parkingapp2.authentication.viewmodels.SignUpPhoneState
import com.vancouverparking.parkingapp2.authentication.viewmodels.SignUpPhoneViewModel
import com.vancouverparking.parkingapp2.authentication.viewmodels.SignUpVerificationCodeState
import com.vancouverparking.parkingapp2.authentication.viewmodels.SignUpVerificationCodeViewModel
import com.vancouverparking.parkingapp2.databinding.ActivitySignUpVerificationBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignUpVerificationActivity : AppCompatActivity()
{
    private val viewModel: SignUpVerificationCodeViewModel by viewModels()
    private var binding: ActivitySignUpVerificationBinding? = null
    private val boxes = mutableListOf<EditText>()
    private var loadingDialog: Dialog? = null
    private var errorDialog: AlertDialog? = null
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
            binding?.codeBox5?.let { add(it) }
            binding?.codeBox6?.let { add(it) }
        }

        setBoxFocusListeners()
        setBoxTextListeners()
        binding?.continueSignUpButton?.setOnClickListener {
            handleVerificationCode()
            //startActivity(Intent(this, SignUpLastStepActivity::class.java))
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
        binding?.codeBox5?.doAfterTextChanged {
            binding?.continueSignUpButton?.isEnabled = isValidForm()
        }
        binding?.codeBox6?.doAfterTextChanged {
            binding?.continueSignUpButton?.isEnabled = isValidForm()
        }

        loadingDialog = Dialog(this)
        loadingDialog?.setContentView(R.layout.loading_dialog)
        loadingDialog?.setCancelable(false)
        loadingDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        errorDialog = AlertDialog.Builder(this)
            .setTitle(R.string.feat_signup_error_bad_verification_code_title)
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

    private fun showErrorDialog(state: SignUpVerificationCodeState)
    {
        if(errorDialog != null && !errorDialog!!.isShowing)
        {
            errorDialog?.setMessage(getString(R.string.feat_signup_error_verify_code))
            errorDialog?.show()
        }
    }
    private fun invalidate(state: SignUpVerificationCodeState)
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
            println("Success!. Code was verified correctly!")
            val intent = Intent(this, SignUpLastStepActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleVerificationCode()
    {
        val verificationId = intent.getStringExtra("verificationId")
        val verificationCode = binding?.codeBox1?.text.toString() + binding?.codeBox2?.text.toString() +
                    binding?.codeBox3?.text.toString() + binding?.codeBox4?.text.toString() +
                    binding?.codeBox5?.text.toString() + binding?.codeBox6?.text.toString()

        viewModel.verifyCode(verificationId!!, verificationCode)


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
                    .isNotEmpty() &&
                binding?.codeBox3?.text.toString()
                    .isNotEmpty() &&
                binding?.codeBox4?.text.toString()
                    .isNotEmpty() &&
                binding?.codeBox5?.text.toString()
                    .isNotEmpty() &&
                binding?.codeBox6?.text.toString()
                    .isNotEmpty()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        binding = null
    }
}