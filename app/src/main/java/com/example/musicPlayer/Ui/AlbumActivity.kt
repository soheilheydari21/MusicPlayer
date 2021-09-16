package com.example.musicPlayer.Ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.musicPlayer.Helper.*
import com.example.musicPlayer.Models.SongInfo
import com.example.musicPlayer.R
import kotlinx.android.synthetic.main.activity_album.*

class AlbumActivity : AppCompatActivity() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        var artistAlbum:TextView? = null
        @SuppressLint("StaticFieldLeak")
        var imageAlbum:ImageView? = null
    }

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        
        artistAlbum = findViewById(R.id.textArtistAlbum)
        imageAlbum = findViewById(R.id.imageViewCoverAlbum)

        textAlbumName.text = changTextAlbum
        artistAlbum?.text = changTextArtistAlbum

        setCoverAlbumActivity()

        val allSongAlbum = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = this.contentResolver.query(allSongAlbum,null,selection,null,sortOrder)
        val listOfMusicAlbum = ArrayList<SongInfo>()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @Suppress("DEPRECATION")
                    val songAlbumURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAlbumDesc = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songAlbumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val cover = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))

                    try {
                        listOfMusicAlbum.add(
                            SongInfo(
                                songAlbumName,
                                songAlbumDesc,
                                songAlbumURL,
                                cover
                            )
                        )
                    }
                    catch (e:Exception){ }

                }while (cursor.moveToNext())
            }
            cursor.close()

            val songAlbumList = findViewById<ListView>(R.id.listViewAlbum)
            songAlbumList.adapter = MyMusicAlbumAd(this.applicationContext, listOfMusicAlbum)

        }

    }

    private fun setCoverAlbumActivity() {
        if (changeCoverAlbum != null) {
            Glide.with(this).asBitmap()
                .load(changeCoverAlbum)
                .into(imageViewCoverAlbum)
        }
        else {
            Glide.with(this)
                .load(R.drawable.coverrrl)
                .into(imageViewCoverAlbum)
        }
    }


}