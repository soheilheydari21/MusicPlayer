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
        var myView = layoutInflate.inflate(R.layout.row_online_layout,parent,false)
        val song = this.myListSong[position]

        val holder : ViewHolderOnlin
        if (convertView == null)
        {
            holder = ViewHolderOnlin()
            myView.findViewById<TextView>(R.id.textViewTitle).text = song.Title
            myView.findViewById<TextView>(R.id.textViewDesc).text = song.Desc
            song.Cover?.let { myView.findViewById<RoundedImageView>(R.id.coverMusic).setImageResource(it) }
            myView.tag = holder

        }
        else
        {
            holder = convertView.tag as ViewHolderOnlin
            myView = convertView

        }

        var isDownload = true
        myView.findViewById<ImageView>(R.id.imageViewDownload).setOnClickListener {
            if (isDownload)
            {
                myView.findViewById<ImageView>(R.id.imageViewDownload).setImageResource(
                    R.drawable.stop
                );
                isDownload = false;
                Toast.makeText(nContext,"loding Music",Toast.LENGTH_SHORT).show()
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource(song.SongURL)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()

            }
            else if (isDownload == false)
            {
                myView.findViewById<ImageView>(R.id.imageViewDownload).setImageResource(
                    R.drawable.play_download
                );
                isDownload = true;
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


class ViewHolderOnlin
{
    var myViewImage : RoundedImageView? = null
    var myViewTitle : TextView? = null
    var myViewArtist : TextView? = null
}


