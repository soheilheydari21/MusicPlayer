package com.example.musicplayer

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelStore
//import com.example.musicplayer.PlayActivity.seecbar.mySongThread
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.activity_play.view.*
import kotlinx.android.synthetic.main.row_layout.*
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

//@ExperimentalTime
class PlayActivity : AppCompatActivity() {

    var listofsongs = ArrayList<SongInfo>()

    var adapter: PlaySongAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        adapter = PlaySongAdapter(listofsongs)

//        var mySongAdapter = seecbar<mySongThread>()
//        seecbar.mySongThread().start()

        var flag = 0

        imageHeart.setOnClickListener {
            if (flag == 0) {
                imageHeart.setImageResource(R.drawable.baseline_favorite_white_18dp);
                flag = 1;
                Toast.makeText(this," Add song to Favourite list ",Toast.LENGTH_SHORT).show()
            }else if (flag == 1){
                imageHeart.setImageResource(R.drawable.baseline_favorite_border_white_18dp)
                flag = 0;
            }
        }


        imagePause.setOnClickListener {
            if (flag == 0) {
                imagePause.setImageResource(R.drawable.ggg);
                flag = 1;
            }else if (flag == 1){
                imagePause.setImageResource(R.drawable.dddd);
                flag = 0;
            }
        }

        imageGhati.setOnClickListener {
            if (flag == 0) {
                imageGhati.setImageResource(R.drawable.nnnw);
                flag = 1;
            }else if (flag == 1){
                imageGhati.setImageResource(R.drawable.iconspace_suffle_25px_128);
                flag = 0;
            }
        }

        imageTekrar.setOnClickListener {
            if (flag == 0) {
                imageTekrar.setImageResource(R.drawable.nnnn);
                flag = 1;
            }else if (flag == 1){
                imageTekrar.setImageResource(R.drawable.iconspace_repeat_playlist_25px_128);
                flag = 0;
            }
        }

        imageSpeaker.setOnClickListener {

                val audioManager: AudioManager =getSystemService(Context.AUDIO_SERVICE) as AudioManager

                val maxVolume = audioManager.mediaMaxVolume
                val randomIndex = Random.nextInt(((maxVolume - 0) + 1) + 0)

                audioManager.setMediaVolume(randomIndex)
                toast("Max: $maxVolume / Current: ${audioManager.mediaCurrentVolume}")

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

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }


    open inner class PlaySongAdapter : BaseAdapter
    {
        var playListSong = ArrayList <SongInfo>()
        constructor(playListSong:ArrayList<SongInfo>)
        {
            this.playListSong = playListSong
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val playView = layoutInflater.inflate(R.layout.row_layout,null)
            val playSong = this.playListSong[position]

            playView.textViewTitle2.setText(playSong.Title)
            playView.textViewArtist.setText(playSong.Desc)

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

    // Todo fix this
    // timer sicbar
    class seecbar<T> : MainActivity() {

       inner class mySongThread : Thread() {
          override fun run() {
              while (true) {
                  try {
                     Thread.sleep(1000)
                  } catch (ex: Exception) {

                  }
                  runOnUiThread {
                     if (mediaPlayer != null) {
                        seekBar.progress = mediaPlayer!!.currentPosition
                     }
                  }
              }
          }

       }


    }


}


