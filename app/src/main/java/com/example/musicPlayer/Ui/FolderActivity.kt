package com.example.musicPlayer.Ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicPlayer.Helper.*
import com.example.musicPlayer.Models.SongInfo
import com.example.musicPlayer.R
import kotlinx.android.synthetic.main.activity_folder.*
import kotlinx.android.synthetic.main.activity_play.*
import java.lang.Exception

class FolderActivity : AppCompatActivity() {

    private lateinit var recyclerFolder: RecyclerView

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)

        textFolderName.text = changNameFolder
        textAddressFolder.text = changAddressFolder

        recyclerFolder = findViewById(R.id.listMusicFolder)

        recyclerFolder.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@FolderActivity,1)
        }

        MainActivity.Cover = findViewById(R.id.coverNavar)

        val allMusicFolder = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = this.contentResolver.query(allMusicFolder,null,null,null,sortOrder)
        val listOfMusicFolder= ArrayList<SongInfo>()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @Suppress("DEPRECATION")
                    val songFolderURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songFolderDesc = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songFolderName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val cover = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK))

                    try {
                        listOfMusicFolder.add(
                            SongInfo(
                                songFolderName,
                                songFolderDesc,
                                songFolderURL,
                                cover
                            )
                        )
                    }
                    catch (e:Exception){ }

                }while (cursor.moveToNext())
            }
            cursor.close()

            val songFolderList = findViewById<RecyclerView>(R.id.listMusicFolder)
            songFolderList.adapter = MyMusicFolderAp(this.applicationContext, listOfMusicFolder)

        }
    }


}