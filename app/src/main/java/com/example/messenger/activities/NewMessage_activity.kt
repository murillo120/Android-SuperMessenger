package com.example.messenger.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.messenger.R
import com.example.messenger.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.new_message_activity.*
import kotlinx.android.synthetic.main.userlist_cel.view.*
import java.util.*

class NewMessage_activity : AppCompatActivity() {

    lateinit var listzinha: ArrayList<User>

    val custom_adapter = CustomAdapter(R.layout.userlist_cel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_message_activity)

        custom_adapter.settheContext(this)
        listzinha = ArrayList()
        supportActionBar?.title = "Select the User"

        getContacts()


        custom_adapter.onItemClick = {

            Log.d("abacaxi", it.user_email)
            val intent = Intent(applicationContext, Chat_Activity::class.java)

            intent.putExtra("user", it)

            startActivity(intent)

        }


    }

    private fun getContacts() {

        val dbref = FirebaseDatabase.getInstance().getReference("/user")

        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                p0.children.forEach {

                    var user = it.getValue(User::class.java)

                    listzinha.add(user!!)

                }

                Log.d("abacaxi", listzinha.toString())

                custom_adapter.setList(listzinha)

                rv_newMessage.adapter = custom_adapter

            }
        })
    }
}

class CustomAdapter constructor(val resource: Int) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    lateinit var users: List<User>
    var onItemClick: ((User) -> Unit)? = null

    lateinit var context: Context


    fun settheContext(context: Context) {

        this.context = context

    }

    fun setList(userlist: List<User>) {

        this.users = userlist

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {

        val myView: View = LayoutInflater.from(context).inflate(resource, p0, false)

        return CustomViewHolder(myView)
    }

    override fun getItemCount(): Int {

        return users.size
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {

        val user = users.get(p1)

        Picasso.get().load(user.photo).into(p0.user_img)
        p0.user_name.text = user.username


    }


    inner class CustomViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val user_img = item.civ_personPhoto
        val user_name = item.tv_personName

        init {
            item.setOnClickListener {
                onItemClick?.invoke(users[adapterPosition])
            }
        }


    }

}
