package com.example.messenger.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.messenger.R
import com.example.messenger.models.User
import kotlinx.android.synthetic.main.chat_activity.*

class Chat_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)

        val intent = intent

        val user: User = intent.getSerializableExtra("user") as User

        supportActionBar?.title =  user.username

        send_message.setOnClickListener {

            

        }


        getMessages()

    }

    fun getMessages(){






    }
}