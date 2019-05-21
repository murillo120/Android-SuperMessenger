package com.example.messenger.activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.messenger.R
import com.example.messenger.models.User
import com.example.messenger.viewModel.ViewModel
import kotlinx.android.synthetic.main.registration_activity.*

class Registration_Activity : AppCompatActivity() {

    private val viewmodel = ViewModel()

    var progressbar: Boolean = true

    var photoURI: Uri? = null

    companion object {
        val code = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)


        viewmodel.register.observe(this, object : Observer<User> {
            override fun onChanged(user: User?) {

                if (user != null){
                    Toast.makeText(applicationContext,
                        "Registration Completed, please Log in",
                        Toast.LENGTH_SHORT).show()

                    progress_firebase.visibility = View.GONE

                    val intent = Intent(applicationContext, Home_Activity::class.java)

                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

                    intent.putExtra("user", user)

                    startActivity(intent)

                    finish()
                }

            }

        })


        btn_complete.setOnClickListener {

            performRegister()

        }

        flat_btn_go_to_login.setOnClickListener {

            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }

        btn_profilephoto.setOnClickListener {

            val myintent = Intent(Intent.ACTION_PICK)
            myintent.type = "image/*"
            Log.d("abacaxi", "funfou")
            startActivityForResult(myintent, code)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("abacaxi", requestCode.toString())


        if (requestCode == code && resultCode == Activity.RESULT_OK) {
            Log.d("abacaxi", "deu certo")

            photoURI = data?.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, photoURI)

            val bitmapDrawable = BitmapDrawable(bitmap)

            btn_photo.setImageBitmap(bitmap)
            btn_profilephoto.alpha = 0f

        }
    }


    fun performRegister() {
        val name = et_name.text.toString()
        val email = et_email.text.toString()
        val pass = et_password.text.toString()


        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || photoURI == null) {
            Toast.makeText(
                applicationContext,
                "please fill the fields Correctly",
                Toast.LENGTH_SHORT
            ).show()

            return

        }
        if (pass.length <= 6) {

            Toast.makeText(
                applicationContext,
                "Your password should have 6 characters",
                Toast.LENGTH_SHORT
            ).show()
            et_password.setError("The password should have 6 characters")

            return
        }


        var user = User(email,pass, name,photoURI.toString())

        progress_firebase.visibility = View.VISIBLE

        viewmodel.register(user)

    }

}
