package com.tekprosoft.busride.util

import android.content.Context
import androidx.appcompat.app.AlertDialog


class Helpers {
    companion object{
        fun showInfoDialog(context: Context, msg: String){
            val builder = AlertDialog.Builder(context)
            builder.setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("OK") { dialog, id ->
                    //do things
                }
            val alert = builder.create()
            alert.show()
        }

        fun isNumber(str: String): Boolean{
            return try {
                str.toInt()
                true
            }catch (e: Exception){
                false
            }
        }

        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}