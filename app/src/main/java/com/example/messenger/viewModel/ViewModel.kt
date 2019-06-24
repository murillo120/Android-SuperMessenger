package com.example.messenger.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.net.Uri
import android.util.Log
import com.example.messenger.models.Message
import com.example.messenger.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.ArrayList

class ViewModel : ViewModel() {

    val register = MutableLiveData<User>()
    val login = MutableLiveData<User>()

    val dbref = FirebaseDatabase.getInstance()

    val listMessages = MutableLiveData<ArrayList<Message>>()

    var list = ArrayList<Message>()


    fun register(user: User) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.user_email, user.password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {

                    Log.d("abacaxi", "Algo deu Errado:  ${user.user_email}")

                    Log.d("abacaxi", it.exception?.message)

                    return@addOnCompleteListener
                }

                if (it.isSuccessful) {


                    val filename = UUID.randomUUID().toString()

                    val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

                    ref.putFile(Uri.parse(user.photo))
                        .addOnSuccessListener {
                            Log.d("abacaxi", "imageUploaded:  ${it.metadata!!.path}")
                            Log.d("abacaxi", "URL:  ${ref.downloadUrl}")


                            ref.downloadUrl.addOnSuccessListener {
                                user.photo = it.toString()
                                Log.d("abacaxi", "user.photo:  ${user.photo}")

                                val uid = FirebaseAuth.getInstance().uid
                                user.uid = uid!!
                                user.password = ""
                                dbref.getReference("user/$uid").setValue(user)
                                register.value = user

                            }

                        }
                }
            }
    }

    fun login(user: User) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.user_email, user.password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {


                    Log.d("abacaxi", "Algo deu Errado")

                }

                if (it.isSuccessful) {

                    login.value = user
                }

            }
    }

    fun getMessages(){

        val db: FirebaseDatabase = FirebaseDatabase.getInstance()

        db.getReference("/messages").addChildEventListener(object : ChildEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                val messager = p0.getValue(Message::class.java)

                list.add(messager!!)

                listMessages.value = list

            }
            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })





    }
}
