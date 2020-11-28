package com.example.musicplayer.Notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R

class CreateNotification {

    private final var CHANNEL_ID = "Your_Channel_ID"
    private final var ACTION_PREVIUOS = "Action_Previuos"
    private final var CHANNEL_PLAY = "Action_Play"
    private final var CHANNEL_NEXT = "Action_Next"

    var notification:Notification? = null

    fun createNotification(context: Context, track:SongInfo, playButton:Int, pause:Int, size:Int){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationManagerCompat:NotificationManagerCompat= NotificationManagerCompat.from(context)
            val mediaSessionCompat:MediaSessionCompat = MediaSessionCompat(context,"tag")
//            val icon:Bitmap = BitmapFactory.decodeResource(context.resources,track.getimage())

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(track.gettitle())
                .setContentText(track.getdesc())
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
//                .setLargeIcon(icon)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            notificationManagerCompat.notify(1, notification)
        }

    }
}