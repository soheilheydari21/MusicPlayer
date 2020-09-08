@file:Suppress("DEPRECATION")

package com.example.musicplayer.Activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.musicplayer.Adapters.mediaPlayer
import com.example.musicplayer.R
import com.example.musicplayer.OtherClass.SongInfo
//import com.example.musicplayer.Activity.PlayActivity.seecbar.mySongThread
import kotlinx.android.synthetic.main.activity_play.*
import kotlin.random.Random



//@ExperimentalTime
class PlayActivity : AppCompatActivity() {

    //ToDo: this fixes    Notification


    var listofsongs = ArrayList<SongInfo>()
    var adapter: PlaySongAdapter<Any>?= null

    private var CHANNEL_ID = "Your_Channel_ID";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        //ToDo: this fixes    Notification 2
        CreatNotificationChannel()
        val notificationLayout = RemoteViews(packageName,R.layout.notification)
        var builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Your Title")
            .setSmallIcon(R.drawable.ic_baseline_music_note_24)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)





        adapter = PlaySongAdapter(listofsongs)

//        var mySongAdapter = seecbar<mySongThread>()
//        seecbar.mySongThread().start()

        var flag = 0

        imageHeart.setOnClickListener {
            if (flag == 0) {
                imageHeart.setImageResource(R.drawable.heart1);
                flag = 1;
                Toast.makeText(this," Add song to Favourite list ",Toast.LENGTH_SHORT).show()
            }else if (flag == 1){
                imageHeart.setImageResource(R.drawable.heart)
                flag = 0;
            }
        }

        imagePause.setOnClickListener {



            if (flag == 0) {
                imagePause.setImageResource(R.drawable.play);
                flag = 1;
//                mediaPlayer!!.Pause()


               //ToDo: this fixes    Notification 3

                with(NotificationManagerCompat.from(this)){
                    notify(0,builder.build())
                }


            }else if (flag == 1){
                imagePause.setImageResource(R.drawable.pause);
                flag = 0;
//                mediaPlayer!!.start()
            }
        }



        randomButton.setOnClickListener {
            if (flag == 0) {
                randomButton.setImageResource(R.drawable.random1);
                flag = 1;
            }else if (flag == 1){
                randomButton.setImageResource(R.drawable.random);
                flag = 0;
            }
        }

        imageTekrar.setOnClickListener {
            if (flag == 0) {
                imageTekrar.setImageResource(R.drawable.refresh1);
                flag = 1;
            }else if (flag == 1){
                imageTekrar.setImageResource(R.drawable.refresh);
                flag = 0;

            }
        }

        //ToDo: fix this    Btn forward
        //btnForward
        var forwardTime: Int = 5000
        var playTime: Int = 0
        var endTime: Int = 0
        btnForward.setOnClickListener {
            if ((playTime + forwardTime) <= endTime) {
                playTime += forwardTime
                mediaPlayer!!.seekTo(playTime)
            }
            else if (!imagePause.isEnabled) {
                imagePause.isEnabled = true
            }

        }

        //ToDo: fix this    Btn backward
        //btnBackward
        var backwardTime: Int = 5000
        btnBackward.setOnClickListener {
            if ((playTime - backwardTime) > 0) {
                playTime -= backwardTime
                mediaPlayer!!.seekTo(playTime)
            }
            else if (!imagePause.isEnabled) {
                imagePause.isEnabled = true
            }
        }

        imageSpeaker.setOnClickListener {

                val audioManager: AudioManager =getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val maxVolume = audioManager.mediaMaxVolume
                val randomIndex = Random.nextInt(((maxVolume - 0) + 1) + 0)

                audioManager.setMediaVolume(randomIndex)
//                toast("Max: $maxVolume / Current: ${audioManager.mediaCurrentVolume}")
        }

        baseline.setOnClickListener {
            val listIntent = Intent(this@PlayActivity, MainActivity::class.java)
            startActivity(listIntent)
        }
    }

    // volume
    fun AudioManager.setMediaVolume(volumeIndex:Int) {

        this.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            volumeIndex,
            AudioManager.FLAG_SHOW_UI
        )
    }
    val AudioManager.mediaMaxVolume:Int
    get() = this.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    val AudioManager.mediaCurrentVolume:Int
    get() = this.getStreamVolume(AudioManager.STREAM_MUSIC)

//    fun Context.toast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//
//    }

//ToDo: this fixed
     open inner class PlaySongAdapter<T> : BaseAdapter
    {
        var playListSong = ArrayList <SongInfo>()
        constructor(playListSong:ArrayList<SongInfo>)
        {
            this.playListSong = playListSong
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val playView = layoutInflater.inflate(R.layout.row_layout,null)
            val playSong = this.playListSong[position]

            playView.findViewById<TextView>(R.id.textViewTitle2).setText(playSong.Title)
            playView.findViewById<TextView>(R.id.textViewArtist).setText(playSong.Desc)

            return playView
        }

        override fun getItem(position: Int): Any {
            return this.playListSong[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return this.playListSong.size
        }


    }

    //Todo: fix this    Timer sicbar

//    class seecbar<T> : MainActivity(MySongAdapter) {
//
//       inner class mySongThread : Thread() {
//          override fun run() {
//              while (true) {
//                  try {
//                     Thread.sleep(1000)
//                  } catch (ex: Exception) {
//
//                  }
//                  runOnUiThread {
//                     if (mediaPlayer != null) {
//                        seekBar.progress = mediaPlayer!!.currentPosition
//                     }
//                  }
//              }
//          }
//
//       }
//    }


    //ToDo: this fixes    Notification 4
    private fun CreatNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val name = "App Notification"
            val descriptionText = "This is your notification description"
            val importance : Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }

            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}


