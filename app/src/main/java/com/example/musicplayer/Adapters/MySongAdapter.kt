package com.example.musicplayer.Adapters

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicplayer.Activity.MainActivity
import com.example.musicplayer.Activity.PlayActivity
import com.example.musicplayer.OtherClass.AllbumInfo
import com.example.musicplayer.R
import com.example.musicplayer.OtherClass.SongInfo
import com.rishabhharit.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.row_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

var adapter: MySongAdapter?= null
//var listofsongs = ArrayList<SongInfo>()
var mediaPlayer: MediaPlayer? = null

var changTextTitle = "Title"
var changTextArtist = "Artist"

//****
var currentSongIndex = 0
var test:SongInfo ?= null

class MySongAdapter(context: Context, myListSong: ArrayList<SongInfo>) : BaseAdapter() {

     var myListSong = ArrayList<SongInfo>()
     private val mContext: Context

    init {
        this.myListSong = myListSong
        mContext=context
    }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val layoutInflate = LayoutInflater.from(mContext)
            var myView = layoutInflate.inflate(R.layout.row_layout,parent,false)
            val song = this.myListSong[position]

            val holder : ViewHolder
            if (convertView == null)
            {
                holder = ViewHolder()
                myView.findViewById<TextView>(R.id.textViewTitle).text = song.Title
                myView.findViewById<TextView>(R.id.textViewDesc).text = song.Desc
                myView.tag = holder

            }
            else
            {
                holder = convertView.tag as ViewHolder
                myView = convertView

            }

//            song.Cover?.let { myView.findViewById<RoundedImageView>(R.id.coverMusic).setImageResource() }
//            myView.findViewById<RoundedImageView>(R.id.coverMusic).setImageResource(song.Cover)

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

                    myView.findViewById<TextView>(R.id.textViewDesc).setTextColor(Color.parseColor("#00d6b3"))
                    myView.findViewById<TextView>(R.id.textViewTitle).setTextColor(Color.parseColor("#13f8d1"))

                    changTextTitle = myView.findViewById<TextView>(R.id.textViewTitle).text.toString()
                    changTextArtist = myView.findViewById<TextView>(R.id.textViewDesc).text.toString()
                    flags = 0;
                }
                test = song
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



class ViewHolder
{
//    var myViewImageCover : RoundedImageView? = null
    var myViewTextTitle : TextView? = null
    var myViewTextArtist : TextView? = null
}


