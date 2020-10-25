package com.example.musicplayer.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.musicplayer.Less.MainActivity
import com.example.musicplayer.R

class SpalshScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalshscreen)

        Handler().postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 5000)
    }
}
