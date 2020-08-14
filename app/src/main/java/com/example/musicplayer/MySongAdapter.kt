package com.example.musicplayer

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.row_layout.view.*

var adapter:MySongAdapter ?= null
//var listofsongs = ArrayList<SongInfo>()
var mediaPlayer: MediaPlayer? = null

 class MySongAdapter(context: Context, myListSong: ArrayList<SongInfo>) : BaseAdapter() {

     var myListSong = ArrayList<SongInfo>()

     private val mContext: Context

    init {
        this.myListSong = myListSong
        mContext=context
    }
//constructor(myListSong:ArrayList<SongInfo>)
//    constructor(context: Context, myListSong: ArrayList<SongInfo>) : this() {
//        this.myListSong = myListSong
//        mContext = context
//    }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val layoutInflate = LayoutInflater.from(mContext)
            val myView = layoutInflate.inflate(R.layout.row_layout,parent,false)
            val song = this.myListSong[position]


            myView.findViewById<TextView>(R.id.textViewTitle).text = song.Title
            myView.findViewById<TextView>(R.id.textViewDesc).text = song.Desc

//            ToDo this fixed
            myView.findViewById<ImageView>(R.id.coverMusic).setOnClickListener {

//                val playIntent = Intent(mContext, PlayActivity::class.java)
//                mContext.startActivity(playIntent)
            }



            myView.findViewById<LinearLayout>(R.id.PlayMusic).setOnClickListener {

                if (myView.findViewById<TextView>(R.id.textViewDesc).textColors.equals("#00d6b3"))
                {

                    mediaPlayer!!.stop()
                    myView.findViewById<TextView>(R.id.textViewDesc).setTextColor(Color.parseColor("#C3C3C3"))
                    myView.findViewById<TextView>(R.id.textViewTitle).setTextColor(Color.parseColor("#ffffff"))
                }
                else
                {

                    mediaPlayer = MediaPlayer()

                    mediaPlayer!!.setDataSource(song.SongURL)
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
//                    seekBar.max = mediaPlayer!!.duration

                    myView.findViewById<TextView>(R.id.textViewDesc).setTextColor(Color.parseColor("#00d6b3"))
                    myView.findViewById<TextView>(R.id.textViewTitle).setTextColor(Color.parseColor("#13f8d1"))

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