package com.example.musicplayer.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.Observer
import com.example.musicplayer.Adapters.MySongOnline
import com.example.musicplayer.OtherClass.NetworkConnection
import com.example.musicplayer.R
import com.example.musicplayer.OtherClass.SongInfo
import com.example.musicplayer.OtherClass.SongOnline
import kotlinx.android.synthetic.main.fragment_two.*


class FragmentTwo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_two, container, false)

        //desconnect
        val networkConnection =
            NetworkConnection(activity!!.applicationContext)
        networkConnection.observe(this, Observer { isConnected ->

            if (isConnected){
                layoutDisconnect.visibility = View.GONE
                layoutConnect.visibility = View.VISIBLE

            }else{
                layoutConnect.visibility = View.GONE
                layoutDisconnect.visibility =View.VISIBLE
            }
        })

        val ArraySong = ArrayList<SongOnline>()
        ArraySong.add(
            SongOnline(
                "Iran",
                "Armin Zarei",
                "https://dl.next1.ir/files/2020/06/tak/ArminZarei-Iran-320(www.Next1.ir).mp3",
                R.drawable.armin_zarei_iran
            )
        )
        ArraySong.add(
            SongOnline(
                "Vatan",
                "Hamid Hiraad",
                "https://dl.next1.ir/files/2020/06/tak/HamidHiraad-Vatan-128(www.Next1.ir).mp3",
                R.drawable.photo____________________
            )
        )
        ArraySong.add(
            SongOnline(
                "Taki Taki",
                "Selena Gomez",
                "https://baarzesh.net/wp-content/uploads/2018/12/Taki-Taki-DJ-SNAKE-ft-SELENA-GOMEZ-ft-CARDI-B-ft-OZUNA.mp3",
                R.drawable.dj_snake_taki_taki_300x300
            )
        )
        ArraySong.add(
            SongOnline(
                "Haghighat",
                "Mahasti",
                "https://98music8old.ir/download/musics/160804/2_01-Haghighat.mp3",
                R.drawable.coverrrl
            )
        )
        ArraySong.add(
            SongOnline(
                "Duet",
                "Hamed Fard",
                "https://dl.myahanghaa.ir/up/2018/Hamed%20Fard%20-%20Duet%20128.mp3",
                R.drawable.duet
            )
        )

        val songList = view.findViewById<ListView>(R.id.listSongOnline)
        songList.adapter = MySongOnline(
            activity!!.applicationContext,
            ArraySong
        )

         return view
    }


}