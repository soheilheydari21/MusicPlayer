package com.example.musicplayer.Adapters

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.musicplayer.R
import com.example.musicplayer.OtherClass.SongInfo
import com.example.musicplayer.OtherClass.SongOnline
import com.rishabhharit.roundedimageview.RoundedImageView

//var mediaPlayerOnline: MediaPlayer? = null

class MySongOnline(context: Context, myListSong: ArrayList<SongOnline>) : BaseAdapter() {

    var myListSong = ArrayList<SongOnline>()
    private val nContext: Context

    init {
        this.myListSong = myListSong
        nContext=context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflate = LayoutInflater.from(nContext)
        val myView = layoutInflate.inflate(R.layout.row_online_layout,parent,false)
        val song = this.myListSong[position]

        myView.findViewById<TextView>(R.id.textViewTitle).text = song.Title
        myView.findViewById<TextView>(R.id.textViewDesc).text = song.Desc
        song.Cover?.let { myView.findViewById<RoundedImageView>(R.id.coverMusic).setImageResource(it) }

        var flage = 0
        myView.findViewById<ImageView>(R.id.imageViewDownload).setOnClickListener {
            if (flage == 0)
            {
                myView.findViewById<ImageView>(R.id.imageViewDownload).setImageResource(
                    R.drawable.stop
                );
                flage = 1;
//                Toast.makeText(nContext,"Loading...",Toast.LENGTH_SHORT).show()

                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource(song.SongURL)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
            }

            else if (flage == 1)
            {
                myView.findViewById<ImageView>(R.id.imageViewDownload).setImageResource(
                    R.drawable.play_download
                );
                flage = 0;
                mediaPlayer!!.stop()
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


