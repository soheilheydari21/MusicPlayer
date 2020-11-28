@file:Suppress("DEPRECATION")

package com.example.musicplayer.Less

import android.annotation.SuppressLint
import android.content.ContentValues
import android.media.AudioManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import com.bumptech.glide.Glide
import com.example.musicplayer.DataBase.DBManager
import com.example.musicplayer.Helper.*
import com.example.musicplayer.Helper.MyTrackAdapter.Companion.myListTrack
//import com.example.musicplayer.Activity.PlayActivity.seecbar.*
import com.example.musicplayer.R
import com.example.musicplayer.Services.DataService.Companion.playSong
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import eightbitlab.com.blurview.RenderScriptBlur
//import com.jackandphantom.blurimage.BlurImage
//import com.example.musicplayer.Activity.PlayActivity.seecbar.mySongThread
import kotlinx.android.synthetic.main.activity_play.*
import kotlin.random.Random

var totalTime: Int = 0

//@ExperimentalTime
class PlayActivity : AppCompatActivity() {

    companion object{
        var seekbar:SeekBar? = null
        var Pause:ImageView? = null
        var position:Int = -1
    }


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

//        getIntentMethod()

        blurBackground()

        setCoverPlayActivity()

        setCoverBlur()

        seekbar = findViewById(R.id.seekBar)
        Pause = findViewById(R.id.imagePause)
        totalTime = mediaPlayer!!.duration

        // Set text Artist & Title
        textViewTitle2.text = changTextTitle
        textViewArtist2.text = changTextArtist

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
                animationLike()

                //database
                val dbManager = DBManager(this)
                val values = ContentValues()
                values.put("Title", textViewTitle2.text.toString())
                values.put("Artist", textViewArtist2.text.toString())
                values.put("Cover", Glide.with(this)
                    .asBitmap()
                    .load(R.id.coverPlayActivity)
                    .toString())

                val ID = dbManager.Insert(values)
                if (ID > 0)
                    Toast.makeText(this," Add song to Favourite list ",Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this," ERROR!! NOT Add song to Favourite list ",Toast.LENGTH_SHORT).show()


            }else if (!isLike){
                imageHeart.setImageResource(R.drawable.heart)
                isLike = true
                animationLike()
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
            imagePause.setImageResource(R.drawable.pause)

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
                randomButton.setImageResource(R.drawable.random)
                isShuffle = false
                animationShuffle()

            }
            else if(!isShuffle){
                randomButton.setImageResource(R.drawable.random1)
                isShuffle = true
                imageTekrar.setImageResource(R.drawable.refresh)
                isRepeat = false
                animationShuffle()

            }
        }

        //ToDo  btnRepeat
        //btn Repeat
        imageTekrar.setOnClickListener {
            if (isRepeat) {
                imageTekrar.setImageResource(R.drawable.refresh)
                isRepeat = false
                animationRepeat()

            }
            else if (!isRepeat) {
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
                val rand = Random
                currentSongIndex = rand.nextInt((myListTrack.size - 1))
                playSong(currentSongIndex)
            }
            else
            {
                if (currentSongIndex<(myListTrack.size - 1))
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
        var currentPosition = 0
        val duration = mediaPlayer!!.duration

        btnForward.setOnClickListener {
            currentPosition = mediaPlayer!!.currentPosition
            if(mediaPlayer!!.isPlaying && duration != currentPosition)
            {
                currentPosition += 5000
                mediaPlayer!!.seekTo(currentPosition)
                animationForward()
            }
        }

        //btn backward
        var backwardTime = 5000

        btnBackward.setOnClickListener {
            currentPosition = mediaPlayer!!.currentPosition
            if(mediaPlayer!!.isPlaying && currentPosition > 5000)
            {
                currentPosition -= 5000
                mediaPlayer!!.seekTo(currentPosition)
                animationBackward()
            }
        }

        // btn Speaker
        imageSpeaker.setOnClickListener {
                val audioManager: AudioManager =getSystemService(AUDIO_SERVICE) as AudioManager
                val maxVolume = audioManager.mediaMaxVolume
                val randomIndex = Random.nextInt(((maxVolume - 0) + 1) + 0)
                audioManager.setMediaVolume(randomIndex)
                animationSpeaker()
        }

        //btn list
        baseline.setOnClickListener {
            animationList()
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

    private val AudioManager.mediaMaxVolume
    get() = this.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    val AudioManager.mediaCurrentVolume
    get() = this.getStreamVolume(AudioManager.STREAM_MUSIC)

    //seekBar 2
    @SuppressLint("HandlerLeak")
    var handler = object : Handler()
    {
        @SuppressLint("SetTextI18n")
        override fun handleMessage(msg: Message) {
            val currentPosition = msg.what
            seekBar.progress = currentPosition
            val elapsedTime = createTimeLabel(currentPosition)
            elapsedTimeLable.text = elapsedTime

            val remainingTime = createTimeLabel(totalTime - currentPosition)
            totalTimer.text = "-$remainingTime"

        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }


    inner class mySongThread() : Thread()
    {
        override fun run() {
            while (true)
            {
                try {
                    sleep(1000)
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

    private fun setCoverPlayActivity()
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

    private fun setCoverBlur()
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
        val radius = 22f
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
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_pause)
        imagePause.startAnimation(animScale)
    }

    private fun animationLike()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_pause)
        imageHeart.startAnimation(animScale)
    }

    private fun animationRepeat()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_repeat)
        imageTekrar.startAnimation(animScale)
    }

    private fun animationList()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        baseline.startAnimation(animScale)
    }

    private fun animationBackward()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        btnBackward.startAnimation(animScale)
    }

    private fun animationForward()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        btnForward.startAnimation(animScale)
    }

    private fun animationShuffle()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        randomButton.startAnimation(animScale)
    }

    private fun animationSpeaker()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        imageSpeaker.startAnimation(animScale)
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


