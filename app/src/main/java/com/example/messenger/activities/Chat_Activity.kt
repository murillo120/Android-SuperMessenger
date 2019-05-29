package com.example.messenger.activities

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.messenger.R
import com.example.messenger.models.Message
import com.example.messenger.models.User
import com.example.messenger.viewModel.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.chat_activity.*
import kotlinx.android.synthetic.main.from_message_row.view.*
import kotlinx.android.synthetic.main.to_message_row.view.*
import java.util.*
import kotlin.collections.ArrayList

class Chat_Activity : AppCompatActivity() {

    lateinit var user: User

    val list = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)

        val viewModel = ViewModel()
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()




        viewModel.listMessages.observe(this,object : Observer<ArrayList<Message>> {
            override fun onChanged(t: ArrayList<Message>?) {




            }

        })

        val intent = intent

        user = intent.getSerializableExtra("user") as User

        supportActionBar?.title =  user.username

        send_message.setOnClickListener {

            val message = Message()

            message.message = et_chatMessage.text.toString()
            message.fromID = FirebaseAuth.getInstance().uid!!
            message.toID = user.uid

            val key = database.getReference("/messages").push().key

            message.key = key!!

            database.getReference("messages").push().setValue(message)

            et_chatMessage.setText("")

        }

        getMessages()


        viewModel.getMessages()

        val chat = ChatAdapter()

        chat.setTheContext(applicationContext)
        chat.setList(list)
        rv_chat.adapter = chat

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

                    var message = p0.getValue(Message::class.java)

                    if (message?.toID == user.uid && message.fromID == FirebaseAuth.getInstance().uid){
                        list.add(message)
                        Log.d("abacaxi", message.toString())

                    }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        for (data in list){
            Log.d("abacaxi", data.message)

        }


    }


    class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){

        lateinit var mycontext: Context
        lateinit var messages: ArrayList<Message>

        fun setList(message: ArrayList<Message>){
            messages = message
        }

        fun setTheContext(context: Context){
            mycontext = context
        }

        override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ChatViewHolder {

            var view : View?


            if(viewType == 1) {

                view = LayoutInflater.from(mycontext).inflate(R.layout.to_message_row, p0, false)
            }else{

                view = LayoutInflater.from(mycontext).inflate(R.layout.from_message_row,p0,false)
            }

            return ChatViewHolder(view)

        }

        override fun getItemCount(): Int {
            return messages.size

        }

        override fun onBindViewHolder(viewHolder: ChatViewHolder, position: Int) {

            val data = messages.get(position)

            if (viewHolder.itemViewType == 1) {

                viewHolder.to_message.text = data.message
            }

            if (viewHolder.itemViewType == 2){
                viewHolder.from_message.text = data.message
            }

        }

        override fun getItemViewType(position: Int): Int {

            val data = messages.get(position)

            if (data.fromID == FirebaseAuth.getInstance().uid){
                return 1
            }else{
                return 2
            }

        }

        class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view){

            val from_message = view.from_message
            val to_message = view.to_message


        }
    }
}









