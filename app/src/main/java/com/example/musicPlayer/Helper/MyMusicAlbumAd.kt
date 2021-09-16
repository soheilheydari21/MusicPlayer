package com.example.musicPlayer.Helper

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
import com.example.musicPlayer.Ui.AlbumActivity
import com.example.musicPlayer.Ui.MainActivity
import com.example.musicPlayer.Models.SongInfo
import com.example.musicPlayer.R

//ToDo this fixes  Music album adapter

class MyMusicAlbumAd (context: Context, myListSong: ArrayList<SongInfo>) : BaseAdapter()  {

    companion object {
        var TextTrack: TextView? = null
        var ItemMusic: LinearLayout? = null
        var myListSongAlbum = ArrayList<SongInfo>()
    }

    private val mContext: Context
    init {
        myListSongAlbum = myListSong
        mContext = context
    }

    @SuppressLint("ViewHolder", "CutPasteId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflate = LayoutInflater.from(mContext)
        val myView = layoutInflate.inflate(R.layout.music_album_item,parent,false)
        val song = myListSongAlbum[position]
//        val image = myListSongAlbum[position].getpach()?.let { getSongArt(it) }

        TextTrack = myView.findViewById(R.id.textNameTrack)
        ItemMusic = myView.findViewById(R.id.PlayAlbum)
        TextTrack?.text = song.Title

        if (mediaPlayer == null)
            mediaPlayer = MediaPlayer()

        ItemMusic?.setOnClickListener {
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(song.SongURL)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()

            myView.findViewById<TextView>(R.id.textNameTrack).setTextColor(Color.parseColor("#13f8d1"))
            myView.findViewById<TextView>(R.id.textNameTrack).isSelected = true
            songe = song

            changTextTitle = myView.findViewById<TextView>(R.id.textNameTrack).text.toString()
            changTextArtist = AlbumActivity.artistAlbum?.text.toString()
            changeCover = changeCoverAlbum

            MainActivity.TitleN?.text = changTextTitle
            MainActivity.ArtistN?.text = changTextArtist
            MainActivity.PlayN?.setImageResource(R.drawable.ic_round_pause)
        }
        return myView

    }

    override fun getItem(position: Int): Any {
        return myListSongAlbum[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return myListSongAlbum.size
    }

//    private fun getSongArt(uri: String): ByteArray? {
//        val retrever = MediaMetadataRetriever()
//        retrever.setDataSource(uri)
//        val art: ByteArray? = retrever.embeddedPicture
//        retrever.release()
//        return art
//    }

}
