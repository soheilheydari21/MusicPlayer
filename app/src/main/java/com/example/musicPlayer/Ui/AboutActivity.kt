package com.example.musicPlayer.Ui

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import com.example.musicPlayer.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    private lateinit var notification: NotificationCompat.Builder
    private lateinit var manage: NotificationManager
    private val channel = "notify"
    private lateinit var notificationChannel: NotificationChannel

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ServiceCast", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        //notification
        manage = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channel, "notify", NotificationManager.IMPORTANCE_HIGH)
            manage.createNotificationChannel(notificationChannel)
        }

        notification = NotificationCompat.Builder(this, channel)
            .setContentTitle(textView2.text)
            .setContentText(textView9.text)
            .setSmallIcon(R.drawable.ic_baseline_music_note_24)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.pause))
            .setAutoCancel(true)
            .setColor(getColor(R.color.colorTheme))
            .addAction(R.drawable.ic_round_previous, "previous", null)
            .addAction(R.drawable.ic_round_pause, "play", null)
            .addAction(R.drawable.ic_round_next, "next", null)

        imageView.setOnClickListener {
            manage.notify("media", 1, notification.build())
        }

        //click
        cardViewLinkedinMe.setOnClickListener {
            goToUrl("https://www.linkedin.com/in/soheil-heydari/")
            animationBlur(cardViewLinkedinMe)
        }

        cardViewGithub.setOnClickListener {
            goToUrl("https://github.com/soheilheydari21")
            animationBlur(cardViewGithub)
        }

        cardViewSupporters.setOnClickListener {
            goToUrl("https://www.linkedin.com/in/mohammadkhoddami/")
            animationBlur(cardViewSupporters)
        }

    }

    private fun goToUrl(open:String) {
        val uri:Uri = Uri.parse(open)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun animationBlur(cardView: CardView) {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        cardView.startAnimation(animScale)
    }

}