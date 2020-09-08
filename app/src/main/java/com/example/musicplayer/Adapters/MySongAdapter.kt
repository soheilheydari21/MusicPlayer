package com.example.musicplayer.Adapters

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.musicplayer.R
import com.example.musicplayer.OtherClass.SongInfo

var adapter: MySongAdapter?= null

//var listofsongs = ArrayList<SongInfo>()

var mediaPlayer: MediaPlayer? = null



 class MySongAdapter(context: Context, myListSong: ArrayList<SongInfo>) : BaseAdapter() {



     var myListSong = ArrayList<SongInfo>()

     private val mContext: Context

    init {
        this.myListSong = myListSong
        mContext=context
    }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {





            val layoutInflate = LayoutInflater.from(mContext)
            val myView = layoutInflate.inflate(R.layout.row_layout,parent,false)
            val song = this.myListSong[position]

            myView.findViewById<TextView>(R.id.textViewTitle).text = song.Title
            myView.findViewById<TextView>(R.id.textViewDesc).text = song.Desc

//ToDo: this fixed    Start activity

//            myView.coverMusic.setOnClickListener(View.OnClickListener {
//
//                val playIntent = Intent(mContext, PlayActivity::class.java)
//                mContext.startActivity(playIntent)
//            })

            var flags = 1
            myView.findViewById<LinearLayout>(R.id.PlayMusic).setOnClickListener {
                if (flags == 0)
                {
                    mediaPlayer!!.stop()
                    myView.findViewById<TextView>(R.id.textViewDesc).setTextColor(Color.parseColor("#c3c3c3"))
                    myView.findViewById<TextView>(R.id.textViewTitle).setTextColor(Color.parseColor("#ffffff"))

                    flags = 1;
                }else if (flags == 1)
                {
                    mediaPlayer = MediaPlayer()
                    mediaPlayer!!.setDataSource(song.SongURL)
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
//                    seekBar.max = mediaPlayer!!.duration
                    myView.findViewById<TextView>(R.id.textViewDesc).setTextColor(Color.parseColor("#00d6b3"))
                    myView.findViewById<TextView>(R.id.textViewTitle).setTextColor(Color.parseColor("#13f8d1"))




                    flags = 0;
                }

            }

            return myView

        }

        override fun getItem(position: Int): Any {
            return this.myListSong[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return this.myListSong.size
        }





}