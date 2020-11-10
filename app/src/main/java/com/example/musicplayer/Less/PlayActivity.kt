@file:Suppress("DEPRECATION")

package com.example.musicplayer.Less

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.example.musicplayer.Controllers.FragmentTracks
import com.example.musicplayer.Helper.*
import com.example.musicplayer.Helper.MyTrackAdapter.Companion.myListSong
//import com.example.musicplayer.Activity.PlayActivity.seecbar.*
import com.example.musicplayer.R
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.Services.DataService.Companion.playSong
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
//import com.jackandphantom.blurimage.BlurImage
//import com.example.musicplayer.Activity.PlayActivity.seecbar.mySongThread
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.music_album_item.*
import kotlin.random.Random

var totalTime: Int = 0

//@ExperimentalTime
class PlayActivity : AppCompatActivity() {

    companion object{
        var seekbar:SeekBar? = null
        var Pause:ImageView? = null
        var TitlePlay:TextView? = null
        var uri:Uri? = null
        var position:Int = -1
    }

    //  Notification
    var listSongs = ArrayList<SongInfo>()
    private var CHANNEL_ID = "Your_Channel_ID"


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

//        getIntentMethod()

        blurBackground()

        setCoverPlayActivity()

        setCoverBlue()

        seekbar = findViewById(R.id.seekBar)
        Pause = findViewById(R.id.imagePause)
        TitlePlay = findViewById(R.id.textViewTitle2)

        totalTime = mediaPlayer!!.duration

        //  Notification 2
        CreatNotificationChannel()
        val notificationLayout = RemoteViews(packageName,R.layout.notification)
        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Your Title")
            .setSmallIcon(R.drawable.ic_baseline_music_note_24)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Set text Artist & Title
        textViewTitle2.text = changTextTitle
        textViewArtist2.text = changTextArtist

        // SeekBar
        var TestAdapter = mySongThread()
        mySongThread().start()

        //  Notification 3
        coverPlayActivity.setOnClickListener {

            with(NotificationManagerCompat.from(this)){
                notify(0,builder.build())
            }
        }


        //ToDo  More => create list
        imageCreate.setOnClickListener {
            TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.imageCreate),
                "Not Active", "it can be used in later Updates")
                .tintTarget(true)
                .outerCircleColor(R.color.colorTheme))
        }

        //ToDo  add Favourite tab
        var isLike = true
        imageHeart.setOnClickListener {
            if (isLike) {
                imageHeart.setImageResource(R.drawable.heart1)
                isLike = false
                Toast.makeText(this," Add song to Favourite list ",Toast.LENGTH_SHORT).show()
                animationHeart()

            }else if (!isLike){
                imageHeart.setImageResource(R.drawable.heart)
                isLike = true
                animationHeart()
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
                    val msg = Message()
                    msg.what = mediaPlayer!!.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                }catch (e: InterruptedException){

                }
            }
        }).start()

        if (mediaPlayer!!.isPlaying)
            imagePause.setImageResource(R.drawable.pause);

        //btn pause
        imagePause.setOnClickListener {
            if (mediaPlayer!!.isPlaying) {
                imagePause.setImageResource(R.drawable.play)
                mediaPlayer!!.pause()
                animationPause()

            }else {
                imagePause.setImageResource(R.drawable.pause)
                mediaPlayer!!.start()
                animationPause()

            }
        }

        //ToDo  btnShuffle
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

        //ToDo  btnRepeat
        //btn Repeat
        imageTekrar.setOnClickListener {
            if (isRepeat) {
                imageTekrar.setImageResource(R.drawable.refresh)
                isRepeat = false
                animationRepeat()

            }else{
                imageTekrar.setImageResource(R.drawable.refresh1)
                isRepeat = true
                randomButton.setImageResource(R.drawable.random)
                isShuffle = false
                animationRepeat()
            }
        }

        //ToDo this fixes   mediaPlayer => isRepeat & isShuffle
        mediaPlayer!!.setOnCompletionListener {
            if (isRepeat)
            {
                playSong(currentSongIndex)
            }
            else if (isShuffle)
            {
                val rand:Random = Random
                currentSongIndex = rand.nextInt((myListSong.size - 1))
                playSong(currentSongIndex)
            }
            else
            {
                if (currentSongIndex<(myListSong.size - 1))
                {
                    playSong(currentSongIndex + 1)
                    currentSongIndex += 1
                }
                else {
                    playSong(0)
                    currentSongIndex = 0
                }

            }
        }

        //btn forward
        var currentPosition: Int = 0
        val duration = mediaPlayer!!.duration

        btnForward.setOnClickListener {
            currentPosition = mediaPlayer!!.currentPosition
            if(mediaPlayer!!.isPlaying && duration != currentPosition)
            {
                currentPosition += 5000
                mediaPlayer!!.seekTo(currentPosition)
            }
        }

        //btn backward
        var backwardTime: Int = 5000

        btnBackward.setOnClickListener {
            currentPosition = mediaPlayer!!.currentPosition
            if(mediaPlayer!!.isPlaying && currentPosition > 5000)
            {
                currentPosition -= 5000
                mediaPlayer!!.seekTo(currentPosition)
            }
        }

        // btn Speaker
        imageSpeaker.setOnClickListener {
                val audioManager: AudioManager =getSystemService(AUDIO_SERVICE) as AudioManager
                val maxVolume = audioManager.mediaMaxVolume
                val randomIndex = Random.nextInt(((maxVolume - 0) + 1) + 0)
                audioManager.setMediaVolume(randomIndex)

        }

        //btn list
        baseline.setOnClickListener {
            onBackPressed()
        }
    }


    // volume
    private fun AudioManager.setMediaVolume(volumeIndex:Int) {

        this.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            volumeIndex,
            AudioManager.FLAG_SHOW_UI
        )
    }

    private val AudioManager.mediaMaxVolume:Int
    get() = this.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    val AudioManager.mediaCurrentVolume:Int
    get() = this.getStreamVolume(AudioManager.STREAM_MUSIC)

    //seekBar 2
    @SuppressLint("HandlerLeak")
    var handler = object : Handler()
    {
        @SuppressLint("SetTextI18n")
        override fun handleMessage(msg: Message) {
            val currentPosition = msg.what
            seekBar.progress = currentPosition
            val elapsedTime = createTimeLable(currentPosition)
            elapsedTimeLable.text = elapsedTime

            val remaningTime = createTimeLable(totalTime - currentPosition)
            totalTimer.text = "-$remaningTime"

        }
    }

    fun createTimeLable(time: Int): String {
        var timeLable = ""
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

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

    fun setCoverPlayActivity()
    {
        if (changeCover != null) {
            Glide.with(this).asBitmap()
                .load(changeCover)
                .into(coverPlayActivity)
        }
        else
        {
            Glide.with(this)
                .load(R.drawable.coverrrl)
                .into(coverPlayActivity)
        }
    }

    fun setCoverBlue()
    {
        if (changeCover != null) {
            Glide.with(this).asBitmap()
                .load(changeCover)
                .into(imageblur)
        }
        else
        {
            Glide.with(this)
                .load(R.drawable.matt)
                .into(imageblur)
        }
    }

    private fun blurBackground()
    {
        val radius:Float = 22f

        val decorView = window.decorView

        val rootView:ViewGroup = decorView.findViewById(android.R.id.content)

        val windowBackground = decorView.background

        blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm( RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)
    }

    //animation
    private fun animationPause()
    {
        val animPause = AnimationUtils.loadAnimation(this, R.anim.anim_pause)
        imagePause.startAnimation(animPause)
    }

    private fun animationHeart()
    {
        val animHeart = AnimationUtils.loadAnimation(this, R.anim.anim_pause)
        imageHeart.startAnimation(animHeart)
    }

    private fun animationRepeat()
    {
        val animRepeat = AnimationUtils.loadAnimation(this, R.anim.anim_repeat)
        imageTekrar.startAnimation(animRepeat)
    }



//    private fun getIntentMethod()
//    {
//        position = intent.getIntExtra("position", -1)
//        listSongs = listofsongs
////        uri = Uri.parse(listSongs[position].getpach())
//
//        uri?.let { metaData(it) }
//    }


//    private fun metaData(uri:Uri)
//    {
//        val retriever = MediaMetadataRetriever()
//        retriever.setDataSource(uri.toString())
//        val art:ByteArray? = retriever.embeddedPicture
//        if(art != null)
//        {
//            setCoverPlayActivity?.let {
//                Glide.with(this)
//                    .asBitmap()
//                    .load(art)
//                    .into(it)
//            }
//        }
//        else
//        {
//            setCoverPlayActivity?.let {
//                Glide.with(this)
//                    .asBitmap()
//                    .load(R.drawable.coverrrl)
//                    .into(it)
//            }
//        }
//    }




}


