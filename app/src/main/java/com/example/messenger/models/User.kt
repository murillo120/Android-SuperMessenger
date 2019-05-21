package com.example.messenger.models

import java.io.Serializable

data class User(val user_email: String,
                var password: String,
                var username: String = "",
                var photo: String = "",
                var uid: String = "") : Serializable{

    constructor(): this("","","","","")
}
