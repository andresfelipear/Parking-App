package com.vancouverparking.parkingapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.vancouverparking.parkingapp2.authentication.views.LoginActivity
import com.vancouverparking.parkingapp2.authentication.views.SignUpActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var loginButton: Button

        findViewById<Button>(R.id.login).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        findViewById<Button>(R.id.signUp).setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }
}