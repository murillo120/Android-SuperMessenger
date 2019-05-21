package com.example.messenger.activities

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.messenger.R
import com.example.messenger.models.User
import com.example.messenger.viewModel.ViewModel
import kotlinx.android.synthetic.main.login_activity.*


class Login_Activity : AppCompatActivity() {


    val context: Context = this
    val viewModel = ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)


        var intent: Intent
        intent = this.intent!!
        if (intent.getSerializableExtra("user") != null) {
            val data: User = intent.getSerializableExtra("user") as User

            logo_login.alpha = 0f

            et_login_email.setText(data.user_email)
            et_login_password.setText(data.password)

        }

        viewModel.login.observe(this, object : Observer<User> {
            override fun onChanged(user: User?) {

                val intent = Intent(applicationContext, Home_Activity::class.java)
                bar_login.visibility = View.GONE

                intent.putExtra("user", user)

                startActivity(intent)

            }

        })

        flat_back_to_registration.setOnClickListener {

            val myIntent = Intent(this, Registration_Activity::class.java)

            startActivity(myIntent)

            finish()

        }

        btn_login_home.setOnClickListener {

            val email = et_login_email.text.toString()
            val password = et_login_password.text.toString()

            val user = User(email, password)

            if (email.isEmpty() || password.isEmpty()) {
                Toast("please Fill the Fields")
            }
            bar_login.visibility = View.VISIBLE
            viewModel.login(user)

        }
    }

    fun Toast(text: String) {

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

    }


}