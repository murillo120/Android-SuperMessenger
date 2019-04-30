package com.example.messenger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import kotlinx.android.synthetic.main.registration_activity.*

class Registration_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)

        btn_complete.setOnClickListener {
            val name = et_name.text
            val email = et_email.text
            val pass = et_password.text

            Log.d("abacaxi", "VALUES: $name $email $pass")
        }

        flat_btn_go_to_login.setOnClickListener {
            Log.d("abacaxi2", " visionario")

            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }
    }
}
