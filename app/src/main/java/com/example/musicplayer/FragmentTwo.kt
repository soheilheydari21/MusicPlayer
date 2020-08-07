package com.example.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_two.*


class FragmentTwo : Fragment() {

//    var listofsongs = ArrayList<SongInfo>()
//    var adapter:MySongAdapter ?= null
//    var mediaPlayer:MediaPlayer ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_two, container, false)
//        val ArraySong = ArrayList<SongInfo>()

        //desconnect

        val networkConnection = NetworkConnection(activity!!.applicationContext)
        networkConnection.observe(this, Observer { isConnected ->

            if (isConnected){
                layoutDisconnect.visibility = View.GONE
                layoutConnect.visibility = View.VISIBLE

            }else{
                layoutConnect.visibility = View.GONE
                layoutDisconnect.visibility =View.VISIBLE

            }
        })



        val ArraySong = ArrayList<SongInfo>()
        ArraySong.add(SongInfo("Iran","Armin Zarei","https://dl.next1.ir/files/2020/06/tak/ArminZarei-Iran-320(www.Next1.ir).mp3"))
        ArraySong.add(SongInfo("Vatan","Hamid Hiraad","https://dl.next1.ir/files/2020/06/tak/HamidHiraad-Vatan-128(www.Next1.ir).mp3"))

        val songList = view.findViewById<ListView>(R.id.listSongOnline)
        songList.adapter = MySongAdapter(activity!!.applicationContext, ArraySong)

//        adapter = MySongAdapter(activity!!.applicationContext,listofsongs)
//        listSongOnline.adapter = adapter

         return view
    }



}