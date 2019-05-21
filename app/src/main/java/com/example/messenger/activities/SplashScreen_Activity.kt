package com.example.messenger.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.messenger.R


class SplashScreen_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spashscreen_activity)

        val handle = Handler()
        handle.postDelayed(Runnable {
            registerScreen()
        }, 2000)
    }

    fun registerScreen() {
        startActivity(object : Intent(this, Home_Activity::class.java) {

        })
    }
}