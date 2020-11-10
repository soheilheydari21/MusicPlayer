package com.example.musicplayer.Less

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.Controllers.FragmentTracks
import com.example.musicplayer.Helper.*
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.activity_folder.*
import kotlinx.android.synthetic.main.activity_play.*

class FolderActivity : AppCompatActivity() {

    lateinit var RecyclerFolder: RecyclerView
    lateinit var adapter : MyMusicFolderAp

    companion object{
        var musicAdapter: MyMusicFolderAp? = null
        var Cover: ImageView? = null
    }

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)

        textFolderName.text = changNameFolder
        textAddressFolder.text = changAddressFolder

        RecyclerFolder = findViewById(R.id.listMusicFolder)

        RecyclerFolder.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@FolderActivity,1)
        }

        FragmentTracks.Cover = findViewById(R.id.coverNavar)

        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//        val selection = MediaStore.Audio.Media.IS_MUSIC+ "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = this.contentResolver.query(allsong,null,null,null,sortOrder)
        val listofsongs = ArrayList<SongInfo>()

        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    @Suppress("DEPRECATION")
                    val songURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    val cover = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK))

                    listofsongs.add(
                        SongInfo(
                            songName,
                            songAuthor,
                            songURL,
                            cover
                        )
                    )

                }while (cursor.moveToNext())
            }
            cursor.close()

            val songList = findViewById<RecyclerView>(R.id.listMusicFolder)
            songList.adapter = MyMusicFolderAp(
                this.applicationContext,
                listofsongs
            )

        }

    }


}