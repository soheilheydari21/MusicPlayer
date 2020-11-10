package com.example.musicplayer.Less

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.widget.ListView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.musicplayer.Helper.*
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.music_album_item.*

class AlbumActivity : AppCompatActivity() {


    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        textAlbumName.text = changTextAlbum
        textArtistAlbum.text = changTextArtistAlbum

        setCoverAlbumActivity()

        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = this.contentResolver.query(allsong,null,selection,null,sortOrder)
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
                    val cover = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))

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

            val songList = findViewById<ListView>(R.id.listViewAlbum)
            songList.adapter = MyMusicAlbumAd(
                this.applicationContext,
                listofsongs
            )

        }

    }

    fun setCoverAlbumActivity()
    {
        if (changeCoverAlbum != null) {
            Glide.with(this).asBitmap()
                .load(changeCoverAlbum)
                .into(imageViewCoverAlbum)
        }
        else
        {
            Glide.with(this)
                .load(R.drawable.coverrrl)
                .into(imageViewCoverAlbum)
        }
    }


}