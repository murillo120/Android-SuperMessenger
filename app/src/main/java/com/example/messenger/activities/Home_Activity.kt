package com.example.messenger.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.messenger.R
import com.example.messenger.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Home_Activity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        verifyUser()

    }

    fun verifyUser() {

        val id = FirebaseAuth.getInstance().uid
        Log.d("abacaxi", "uid: $id")

        if (id == null){
            val intent = Intent(this, Registration_Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_messenger, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

            when(item?.itemId){
                R.id.menu_newMessage ->{
                    val intent = Intent(this, NewMessage_activity::class.java)
                    startActivity(intent)

                }
                R.id.menu_sign_out ->{
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, Registration_Activity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                }

            }

        return super.onOptionsItemSelected(item)
    }

















}