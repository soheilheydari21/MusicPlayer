@file:Suppress("DEPRECATION")

package com.example.musicPlayer.Ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.musicPlayer.DataBase.DBManager
import com.example.musicPlayer.Helper.*
import com.example.musicPlayer.Helper.MyTrackAdapter.Companion.myListTrack
import com.example.musicPlayer.R
import com.example.musicPlayer.Services.DataService.Companion.playSong
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.activity_play.*
import kotlin.random.Random


var totalTime: Int = 0

//@ExperimentalTime
@Suppress("UNUSED_VALUE")
class PlayActivity : AppCompatActivity() {

    companion object{
        var seekbar:SeekBar? = null
        var Pause:ImageView? = null
        var position:Int = -1
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.parseColor("#1A000000")
        setContentView(R.layout.activity_play)

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
            TapTargetView.showFor(
                this, TapTarget.forView(
                    findViewById(R.id.imageCreate),
                    "Not Active", "it can be used in later Updates"
                )
                    .tintTarget(true)
                    .outerCircleColor(R.color.colorTheme)
            )
        }

        //ToDo  add Favourite tab
        var isLike = true
        imageHeart.setOnClickListener {
            if (isLike) {
                imageHeart.setImageResource(R.drawable.heart1)
                isLike = false
                animationZoom(imageHeart)

                //database
                val dbManager = DBManager(this)
                val values = ContentValues()
                values.put("Title", textViewTitle2.text.toString())
                values.put("Artist", textViewArtist2.text.toString())

                values.put(
                    "Cover", Glide.with(this)
                        .asBitmap()
                        .load(R.id.coverPlayActivity)
                        .toString()
                )

                val ID = dbManager.Insert(values)
                if (ID > 0)
                    Toast.makeText(this, " Add song to Favourite list ", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, " ERROR!! NOT Add song to Favourite list ", Toast.LENGTH_SHORT).show()

            }
            else if (!isLike) {
                imageHeart.setImageResource(R.drawable.heart)
                isLike = true
                animationZoom(imageHeart)
            }
        }

        //seekBar
        seekBar.max = mediaPlayer!!.duration
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser)
                        mediaPlayer!!.seekTo(progress)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) { }
                override fun onStopTrackingTouch(seekBar: SeekBar?) { }
            }
        )

        Thread(Runnable {
            while (mediaPlayer != null) {
                try {
                    val msg = Message()
                    msg.what = mediaPlayer!!.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                }
                catch (e: InterruptedException) { }
            }
        }).start()

        if (mediaPlayer!!.isPlaying)
            imagePause.setImageResource(R.drawable.pause)

        //btn pause
        imagePause.setOnClickListener {
            if (mediaPlayer!!.isPlaying) {
                imagePause.setImageResource(R.drawable.play)
                MainActivity.PlayN?.setImageResource(R.drawable.ic_round_play)
                mediaPlayer!!.pause()
                animationZoom(imagePause)
            }
            else {
                imagePause.setImageResource(R.drawable.pause)
                MainActivity.PlayN?.setImageResource(R.drawable.ic_round_pause)
                mediaPlayer!!.start()
                animationZoom(imagePause)
            }
        }

        //ToDo btn Shuffle
        var isRepeat = false
        var isShuffle = false

        loadData(isRepeat, isShuffle)

        if (!isShuffle && !isRepeat) {
            randomButton.setImageResource(R.drawable.random)
            imageTekrar.setImageResource(R.drawable.refresh)
        }
        else if (isShuffle or !isRepeat) {
            imageTekrar.setImageResource(R.drawable.refresh1)
            isRepeat = true
            randomButton.setImageResource(R.drawable.random)
            isShuffle = false
        }
        else if (!isShuffle or isRepeat) {
            randomButton.setImageResource(R.drawable.random1)
            isShuffle = true
            imageTekrar.setImageResource(R.drawable.refresh)
            isRepeat = false
        }

        randomButton.setOnClickListener {
            if (isShuffle) {
                randomButton.setImageResource(R.drawable.random)
                isShuffle = false
                animationBlur(randomButton)
            }
            else if(!isShuffle) {
                randomButton.setImageResource(R.drawable.random1)
                isShuffle = true
                imageTekrar.setImageResource(R.drawable.refresh)
                isRepeat = false
                animationBlur(randomButton)
            }
            saveData(isRepeat, isShuffle)
        }

        //ToDo  btn Repeat
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
            saveData(isRepeat, isShuffle)
        }

        //ToDo this fixes   mediaPlayer => isRepeat & isShuffle
        mediaPlayer!!.setOnCompletionListener {
            if (isRepeat)
                playSong(currentSongIndex)
            else if (isShuffle) {
                val rand = Random
                currentSongIndex = rand.nextInt((myListTrack.size - 1))
                playSong(currentSongIndex)
            }
            else {
                if (currentSongIndex<(myListTrack.size - 1)) {
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
            if(mediaPlayer!!.isPlaying && duration != currentPosition) {
                currentPosition += 5000
                mediaPlayer!!.seekTo(currentPosition)
                animationBlur(btnForward)
            }
        }

        //btn backward
        var backwardTime = 5000
        btnBackward.setOnClickListener {
            currentPosition = mediaPlayer!!.currentPosition
            if(mediaPlayer!!.isPlaying && currentPosition > 5000) {
                currentPosition -= 5000
                mediaPlayer!!.seekTo(currentPosition)
                animationBlur(btnBackward)
            }
        }

        // btn Speaker
        imageSpeaker.setOnClickListener {
            val audioManager: AudioManager =getSystemService(AUDIO_SERVICE) as AudioManager
            val maxVolume = audioManager.mediaMaxVolume
            val randomIndex = Random.nextInt(((maxVolume - 0) + 1) + 0)
            audioManager.setMediaVolume(randomIndex)
            animationBlur(imageSpeaker)
        }

        //btn list
        baseline.setOnClickListener {
            animationBlur(baseline)
            onBackPressed()
        }
    }

    private fun saveData(repeat: Boolean, shuffle:Boolean) {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("REPEAT_KEY", repeat)
            putBoolean("SHUFFLE_KEY", shuffle)
        }.apply()
    }

    private fun loadData(repeat: Boolean, shuffle:Boolean) {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val repeatSh:Boolean = sharedPreferences.getBoolean("REPEAT_KEY", false)
        val shuffleSh:Boolean = sharedPreferences.getBoolean("SHUFFLE_KEY", false)
        repeat == repeatSh
        shuffle == shuffleSh
    }


    // volume
    private fun AudioManager.setMediaVolume(volumeIndex: Int) {
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
    var handler = object : Handler() {
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


    inner class mySongThread() : Thread() {
        override fun run() {
            while (true) {
                try { sleep(1000) }
                catch (ex: Exception) { }
                runOnUiThread {
                    if (mediaPlayer!= null)
                        seekBar.progress = mediaPlayer!!.currentPosition
                }
            }

        }
    }

    private fun setCoverPlayActivity() {
        if (changeCover != null) {
            Glide.with(this).asBitmap()
                .load(changeCover)
                .into(coverPlayActivity)
        }
        else {
            Glide.with(this)
                .load(R.drawable.coverrrl)
                .into(coverPlayActivity)
        }
    }

    private fun setCoverBlur() {
        if (changeCover != null) {
            Glide.with(this).asBitmap()
                .load(changeCover)
                .into(imageblur)
        }
        else {
            Glide.with(this)
                .load(R.drawable.matt)
                .into(imageblur)
        }
    }

    private fun blurBackground() {
        val radius = 22f
        val decorView = window.decorView
        val rootView:ViewGroup = decorView.findViewById(android.R.id.content)
        val windowBackground = decorView.background

        blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)
    }

    //animation
    private fun animationZoom(imageView: ImageView) {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_pause)
        imageView.startAnimation(animScale)
    }

    private fun animationRepeat() {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_repeat)
        imageTekrar.startAnimation(animScale)
    }

    private fun animationBlur(imageView: ImageView) {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        imageView.startAnimation(animScale)
    }


}




