package com.example.musicplayer.Less

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.musicplayer.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        cardViewLinkedinMe.setOnClickListener {
            goToUrl("https://www.linkedin.com/in/soheil-heydari/")
            animationLinkedinMe()
        }

        cardViewGithub.setOnClickListener {
            goToUrl("https://github.com/soheilheydari21")
            animationGithub()
        }

        cardViewSupporters.setOnClickListener {
            goToUrl("https://www.linkedin.com/in/mohammadkhoddami/")
            animationSupporters()
        }

    }

    private fun goToUrl(open:String){
        val uri:Uri = Uri.parse(open)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun animationLinkedinMe()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        cardViewLinkedinMe.startAnimation(animScale)
    }

    private fun animationGithub()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        cardViewGithub.startAnimation(animScale)
    }

    private fun animationSupporters()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        cardViewSupporters.startAnimation(animScale)
    }

}