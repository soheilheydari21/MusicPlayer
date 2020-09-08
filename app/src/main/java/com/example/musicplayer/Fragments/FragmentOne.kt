package com.example.musicplayer.Fragments

import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.musicplayer.*
import com.example.musicplayer.Activity.MainActivity
import com.example.musicplayer.Adapters.MySongAdapter
import com.example.musicplayer.Adapters.mediaPlayer
import com.example.musicplayer.OtherClass.SongInfo
import kotlin.collections.ArrayList


class FragmentOne : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_one, container, false)



//ToDo: this fixes    Request allo
        if (activity?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.READ_EXTERNAL_STORAGE) }
            != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(Activity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        else
        {
            loadData()
        }


        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val cursor = activity!!.contentResolver.query(allsong,null,null,null,null)
        var listofsongs = ArrayList<SongInfo>()
        if (cursor != null)
        {
            if (cursor.moveToFirst() == true)
            {
                do {
                    val songURL = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))

                    listofsongs.add(
                        SongInfo(
                            songName,
                            songAuthor,
                            songURL
                        )
                    )

                }while (cursor.moveToNext() == true)
            }
            cursor!!.close()

            val songList = view.findViewById<ListView>(R.id.listVewSong)
            songList.adapter = MySongAdapter(
                activity!!.applicationContext,
                listofsongs
            )



            var flage = 0
            view.findViewById<ImageView>(R.id.imageViewPlayN).setOnClickListener {

                if (flage == 0)
                {
                    view.findViewById<ImageView>(R.id.imageViewPlayN).setImageResource(
                        R.drawable.pause_navar
                    );
                    flage = 1;
                    mediaPlayer = MediaPlayer()

                    mediaPlayer!!.start()

                }
                else if (flage == 1)
                {
                    view.findViewById<ImageView>(R.id.imageViewPlayN).setImageResource(
                        R.drawable.play_navar
                    );
                    flage = 0;
                    mediaPlayer!!.pause()
                }
            }

//            coverMusic.setOnClickListener {
//                activity?.let{
//                    val intent = Intent(context, PlayActivity::class.java)
//                    startActivity(intent)
//                }
//            }


        }

        return view
    }

//    ****
    fun loadData()
    {
//        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//
//
//        val cursor = activity!!.contentResolver.query(allsong,null,null,null,null)
//        var listofsongs = ArrayList<SongInfo>()
//        if (cursor != null)
//        {
//            if (cursor.moveToFirst() == true)
//            {
//                do {
//                    val songURL = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DATA))
//                    val songAuthor = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ARTIST))
//                    val songName = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
//
//                    listofsongs.add(SongInfo(songName,songAuthor,songURL))
//
//                }while (cursor.moveToNext() == true)
//            }
//            cursor!!.close()
//
//            val songList = view.findViewById<ListView>(R.id.listVewSong)
//            songList.adapter = MySongAdapter(activity!!.applicationContext, listofsongs)
//            return
    }

 //ToDo: this fixed    Requested allo 2
// fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == MainActivity.PERMISSION_REQUEST_CODE){
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show()
//                loadData()
//            }
//        }
//    }

    //******
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<RoundedImageView>(R.id.coverMusic).setOnClickListener {
//
//            var intent = Intent(context, PlayActivity::class.java)
//            startActivity(intent)
//        }
//
//    }

//    override fun openPlayerActivity() {
//        if (context != null) {
//            startActivity(PlayActivity.getStartIntent(context!!))
//            activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
//        }
//    }


}