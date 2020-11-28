package com.example.musicplayer.Services

import android.R
import android.accessibilityservice.GestureDescription
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.session.MediaSessionManager
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.musicplayer.Helper.mediaPlayer
import com.example.musicplayer.Helper.songe


class DataService {

    //ToDo this fixes   mediaPlayer => isRepeat & isShuffle 2
    companion object{
        fun playSong(songIndex: Int = 0)
        {
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(songe!!.SongURL)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        }
    }



}
