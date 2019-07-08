package com.tekprosoft.busride

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goToHome()
    }

    private fun goToHome(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
