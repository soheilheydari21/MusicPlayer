package com.example.musicplayer.Less

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.Helper.changTextAlbum
import com.example.musicplayer.Helper.changTextArtistAlbum
import com.example.musicplayer.R
import kotlinx.android.synthetic.main.activity_album.*

class AlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        textAlbumName.text = changTextAlbum
        textArtistAlbum.text = changTextArtistAlbum

    }
}