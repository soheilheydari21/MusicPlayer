package com.example.musicplayer

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.fragment_two.*
import kotlinx.android.synthetic.main.row_layout.*
import kotlinx.android.synthetic.main.row_layout.view.*


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


//        val est = view.findViewById<ImageView>(R.id.imageViewDownload)
//        est.imageViewDownload.setImageResource(R.drawable.play);


        val ArraySong = ArrayList<SongInfo>()
        ArraySong.add(SongInfo("Iran","Armin Zarei","https://dl.next1.ir/files/2020/06/tak/ArminZarei-Iran-320(www.Next1.ir).mp3"))
        ArraySong.add(SongInfo("Vatan","Hamid Hiraad","https://dl.next1.ir/files/2020/06/tak/HamidHiraad-Vatan-128(www.Next1.ir).mp3"))
        ArraySong.add(SongInfo("Taki Taki","Selena Gomez","https://baarzesh.net/wp-content/uploads/2018/12/Taki-Taki-DJ-SNAKE-ft-SELENA-GOMEZ-ft-CARDI-B-ft-OZUNA.mp3"))
        ArraySong.add(SongInfo("Haghighat","Mahasti","https://98music8old.ir/download/musics/160804/2_01-Haghighat.mp3"))
        ArraySong.add(SongInfo("Duet","Hamed Fard","https://dl.myahanghaa.ir/up/2018/Hamed%20Fard%20-%20Duet%20128.mp3"))

        val songList = view.findViewById<ListView>(R.id.listSongOnline)
        songList.adapter = MySongOnline(activity!!.applicationContext, ArraySong)

//        adapter = MySongAdapter(activity!!.applicationContext,listofsongs)
//        listSongOnline.adapter = adapter

         return view
    }



}