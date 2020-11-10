package com.example.musicplayer.Services

import com.example.musicplayer.Helper.*

class DataService {

    //ToDo this fixes   mediaPlayer => isRepeat & isShuffle 2
    companion object{
        fun playSong(songIndex:Int = 0)
        {

            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(songe!!.SongURL)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()

        }

    }


}
