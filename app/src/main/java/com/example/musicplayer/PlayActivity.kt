package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.activity_play.view.*
import kotlinx.android.synthetic.main.row_layout.*
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

        var flag = 0

        imageHeart.setOnClickListener {
            if (flag == 0) {
                imageHeart.setImageResource(R.drawable.baseline_favorite_white_18dp);
                flag = 1;
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
            if (flag == 0) {
                imageSpeaker.setImageResource(R.drawable.mute);
                flag = 1;
            }else if (flag == 1){
                imageSpeaker.setImageResource(R.drawable.baseline_volume_up_white_18dp);
                flag = 0;
            }
        }





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

//    timer sicbar

//fun createTimerLable(duration: Duration)
//    {
//        var timerLable = ""
//        var min = 0
//        var sec = 0
//
//        min = duration / 1000 / 60
//        sec = duration / 1000
//              % 60
//
//        timerLable += min + ":"
//
//        if(sec < 10){
//           timerLable += "0"
//           timerLable += sec
//           return timerLable
//        }
//
//    }


}


