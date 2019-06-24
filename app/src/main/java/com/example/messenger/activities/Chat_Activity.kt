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
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.chat_activity.*
import kotlinx.android.synthetic.main.from_message_row.view.*
import kotlinx.android.synthetic.main.to_message_row.view.*

class Chat_Activity : AppCompatActivity() {

    lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)

        val viewModel = ViewModel()
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()

        viewModel.listMessages.observe(this, object : Observer<ArrayList<Message>> {
            override fun onChanged(messagesOfChat: ArrayList<Message>?) {

                for (data in messagesOfChat!!) {

                    Log.d("abacaxi", "FROM_ID ${data.fromID}")
                    Log.d("abacaxi", "FIREBASE_UID ${FirebaseAuth.getInstance().uid}")
                    Log.d("abacaxi", "TO_ID ${data.toID}")
                    Log.d("abacaxi", "USER_UID ${user.uid}")

                    if (data.fromID != FirebaseAuth.getInstance().uid &&
                        data.fromID != user.uid) {

                        if (data.toID != user.uid) {
                            messagesOfChat.remove(data)
                        }
                    }

                }
                val chat = ChatAdapter()
                chat.setTheContext(applicationContext)
                chat.setList(messagesOfChat!!)
                rv_chat.adapter = chat
            }
        })

        val intent = intent

        user = intent.getSerializableExtra("user") as User

        supportActionBar?.title = user.username

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

        viewModel.getMessages()
    }


    class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

        lateinit var mycontext: Context
        lateinit var messages: ArrayList<Message>

        fun setList(message: ArrayList<Message>) {
            messages = message
            Log.d("abacaxi", " Lista setada ")
        }

        fun setTheContext(context: Context) {
            mycontext = context
        }

        override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ChatViewHolder {

            var view: View?

            if (viewType == 1) {
                view = LayoutInflater.from(mycontext).inflate(R.layout.from_message_row, p0, false)

            } else {
                view = LayoutInflater.from(mycontext).inflate(R.layout.to_message_row, p0, false)
            }

            return ChatViewHolder(view)

        }

        override fun getItemCount(): Int {
            return messages.size

        }

        override fun onBindViewHolder(viewHolder: ChatViewHolder, position: Int) {

            val data = messages.get(position)
            Log.d("abacaxi", viewHolder.itemViewType.toString())
            if (viewHolder.itemViewType == 1) {
                viewHolder.from_message.text = data.message
            }

            if (viewHolder.itemViewType == 2) {
                viewHolder.to_message.text = data.message
            }
        }

        override fun getItemViewType(position: Int): Int {

            val data = messages.get(position)

            if (data.fromID == FirebaseAuth.getInstance().uid) {
                return 1
            } else {
                return 2
            }
        }

        class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val from_message = view.from_message
            val to_message = view.to_message
        }
    }
}