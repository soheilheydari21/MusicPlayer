package com.example.musicPlayer.Services

import com.example.musicPlayer.Helper.mediaPlayer
import com.example.musicPlayer.Helper.songe

class DataService {

    //ToDo this fixes   mediaPlayer => isRepeat & isShuffle 2
    companion object {
        fun playSong(songIndex: Int = 0) {
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(songe!!.SongURL)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        }
    }

}
