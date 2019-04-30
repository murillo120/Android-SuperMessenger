package com.example.messenger

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_activity.*


class Login_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        flat_back_to_registration.setOnClickListener {

            val myIntent = Intent(this, Registration_Activity::class.java)

            startActivity(myIntent)

        }
    }
}