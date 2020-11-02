package com.example.musicplayer.Helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R

class MyMusicAlbumAd (context: Context, myListSong: ArrayList<SongInfo>) : BaseAdapter()  {

    companion object{
        var TextTrack: TextView? = null
        var ItemMusic: LinearLayout? = null
        var myListSong = ArrayList<SongInfo>()
    }

    private val mContext: Context
    init {
        MyMusicAlbumAd.myListSong = myListSong
        mContext = context
    }

    @SuppressLint("ViewHolder", "CutPasteId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflate = LayoutInflater.from(mContext)
        val myView = layoutInflate.inflate(R.layout.music_album_item,parent,false)
        val song = MyMusicAlbumAd.myListSong[position]

        TextTrack = myView.findViewById(R.id.textNameTrack)
        ItemMusic = myView.findViewById(R.id.PlayAlbum)

        MyMusicAlbumAd.TextTrack?.text = song.Title

        if (mediaPlayer == null)
            mediaPlayer = MediaPlayer()

        var flags = true
        MyMusicAlbumAd.ItemMusic?.setOnClickListener {
            if (!flags)
            {
                mediaPlayer!!.stop()
                myView.findViewById<TextView>(R.id.textNameTrack).setTextColor(Color.parseColor("#ffffff"))
                flags = true

            }
            else if (flags)
            {
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(song.SongURL)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()

                myView.findViewById<TextView>(R.id.textNameTrack).setTextColor(Color.parseColor("#13f8d1"))
                myView.findViewById<TextView>(R.id.textNameTrack).isSelected = true
                flags = false
            }
            songe = song
        }
        return myView

    }

    override fun getItem(position: Int): Any {
        return MyMusicAlbumAd.myListSong[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return MyMusicAlbumAd.myListSong.size
    }

}