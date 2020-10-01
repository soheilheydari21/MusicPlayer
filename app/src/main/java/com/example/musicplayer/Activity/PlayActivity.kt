@file:Suppress("DEPRECATION")

package com.example.musicplayer.Activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.get
import androidx.core.view.size
import com.example.musicplayer.Adapters.*
//import com.example.musicplayer.Activity.PlayActivity.seecbar.*
import com.example.musicplayer.R
import com.example.musicplayer.OtherClass.SongInfo
import com.example.musicplayer.Servis.DataServis
import com.example.musicplayer.Servis.DataServis.Companion.playSong
//import com.example.musicplayer.Activity.PlayActivity.seecbar.mySongThread
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.fragment_one.*
import java.io.InterruptedIOException
import kotlin.random.Random


//@ExperimentalTime
class PlayActivity : AppCompatActivity() {

    //  Notification
    var listofsongs = ArrayList<SongInfo>()
    var adapter: PlaySongAdapter<Any>?= null
    private var CHANNEL_ID = "Your_Channel_ID";
    private var totalTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        totalTime = mediaPlayer!!.duration

        //  Notification 2
        CreatNotificationChannel()
        val notificationLayout = RemoteViews(packageName,R.layout.notification)
        var builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Your Title")
            .setSmallIcon(R.drawable.ic_baseline_music_note_24)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Set text Artist & Title
        textViewTitle2.setText(changTextTitle)
        textViewArtist2.setText(changTextArtist)

        adapter = PlaySongAdapter(listofsongs)

        // Sicbarr
        var mySongAdapter = mySongThread()
        mySongThread().start()

        coverPlayActivity.setOnClickListener {
            //  Notification 3
            with(NotificationManagerCompat.from(this)){
                notify(0,builder.build())
            }
        }

        var isLike = true
        imageHeart.setOnClickListener {
            if (isLike) {
                imageHeart.setImageResource(R.drawable.heart1);
                isLike = false;
                Toast.makeText(this," Add song to Favourite list ",Toast.LENGTH_SHORT).show()
            }else if (isLike == false){
                imageHeart.setImageResource(R.drawable.heart)
                isLike = true;
            }
        }

        //seekBar
        seekBar.max = mediaPlayer!!.duration
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser)
                    {
                        mediaPlayer!!.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )

        Thread(Runnable {
            while (mediaPlayer!= null){
                try {
                    var msg = Message()
                    msg.what = mediaPlayer!!.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                }catch (e: InterruptedException){

                }
            }
        }).start()


        imagePause.setOnClickListener {
            if (mediaPlayer!!.isPlaying) {
                imagePause.setImageResource(R.drawable.play);
                mediaPlayer!!.pause()

            }else {
                imagePause.setImageResource(R.drawable.pause);
                mediaPlayer!!.start()

            }
        }

        //ToDo this fixes   btnShuffle
        //btn Shuffle
        var isRepeat = false
        var isShuffle = false

        randomButton.setOnClickListener {
            if (isShuffle) {
                randomButton.setImageResource(R.drawable.random);
                isShuffle = false;

            }else{
                randomButton.setImageResource(R.drawable.random1);
                isShuffle = true;
                imageTekrar.setImageResource(R.drawable.refresh);
                isRepeat = false;

            }
        }

        //ToDo this fixes    btnRepeat
        //btn Repeat
        imageTekrar.setOnClickListener {
            if (isRepeat) {
                imageTekrar.setImageResource(R.drawable.refresh);
                isRepeat = false;

            }else{
                imageTekrar.setImageResource(R.drawable.refresh1);
                isRepeat = true;
                randomButton.setImageResource(R.drawable.random);
                isShuffle = false;

            }
        }

        //ToDo this fixes    mediaPlayer
//        mediaPlayer!!.setOnCompletionListener {
//            if (isRepeat)
//            {
//                playSong(currentSongIndex)
//
//            }
//            else if (isShuffle)
//            {
//                var rand:Random = Random
//                currentSongIndex=rand.nextInt((myListSong.size()-1));
//                playSong(currentSongIndex)
//
//            }
//            else
//            {
//                if (currentSongIndex<(myListSong.size()-1))
//                {
//                    playSong(currentSongIndex+1);
//                    currentSongIndex=currentSongIndex+1;
//
//                }
//                else
//                {
//                    playSong(0);
//                    currentSongIndex=0;
//
//                }
//
//            }
//        }

        //btn forward
        var currentPosition: Int = 0
        var duration = mediaPlayer!!.getDuration()

        btnForward.setOnClickListener {
            currentPosition = mediaPlayer!!.getCurrentPosition()

            if(mediaPlayer!!.isPlaying() && duration != currentPosition)
            {
                currentPosition = currentPosition + 5000
                mediaPlayer!!.seekTo(currentPosition)
            }
        }

        //btn backward
        var backwardTime: Int = 5000

        btnBackward.setOnClickListener {
            currentPosition = mediaPlayer!!.getCurrentPosition()

            if(mediaPlayer!!.isPlaying() && currentPosition > 5000)
            {
                currentPosition = currentPosition - 5000
                mediaPlayer!!.seekTo(currentPosition)
            }
        }

        // btn Speaker
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
            playView.findViewById<TextView>(R.id.textViewArtist2).setText(playSong.Desc)

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

    //seekBar 2
    @SuppressLint("HandlerLeak")
    var handler = object : Handler()
    {
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what
            seekBar.progress = currentPosition
            var elapsedTime = createTimeLable(currentPosition)
            elapsedTimeLable.text = elapsedTime

            var remaningTime = createTimeLable(totalTime - currentPosition)
            remaningTimeLable.text = "-$remaningTime"
        }
    }

    fun createTimeLable(time: Int): String {
        var timeLable = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLable = "$min:"
        if (sec < 10) timeLable += "0"
        timeLable += sec
        return timeLable
    }

    //  Notification 4
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


    inner class mySongThread() : Thread()
    {
        override fun run() {
            while (true)
            {
                try {
                    Thread.sleep(1000)
                }
                catch (ex:Exception)
                {
                }
                runOnUiThread {
                    if (mediaPlayer!= null)
                    {
                        seekBar.progress = mediaPlayer!!.currentPosition
                    }
                }
            }
        }
    }

}


