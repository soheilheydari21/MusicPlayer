package com.example.musicplayer.Servis

import android.widget.TextView
import com.example.musicplayer.Adapters.changTextArtist
import com.example.musicplayer.Adapters.changTextTitle
import com.example.musicplayer.Adapters.mediaPlayer
import com.example.musicplayer.Adapters.test
import com.example.musicplayer.R

class DataServis {

//ToDo  ihgigihgik
    //*****
companion object{
    fun playSong(songIndex:Int = 0)
    {
        try {
            mediaPlayer!!.reset();
            mediaPlayer!!.setDataSource(test!!.SongURL);
            mediaPlayer!!.prepare();
            mediaPlayer!!.start();

            //var title:String =myListSong.get(songIndex).get("title");
            //songTitle.setText(title);
            //btnPlay.setBackgroundResource(R.drawable.btn_pause);

//                    seekBar.setMax(100);
//                    seekBar.setProgress(0);

            //updateProgress();
        } catch (ex:Exception )
        {
        }

    }
}


}
