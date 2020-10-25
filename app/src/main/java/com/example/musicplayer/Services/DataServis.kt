package com.example.musicplayer.Services

import com.example.musicplayer.Helper.*

class DataServis {

//ToDo  ihgigihgik
    //*****
companion object{
    fun playSong(songIndex:Int = 0)
    {

        mediaPlayer!!.reset()
        mediaPlayer!!.setDataSource(songe!!.SongURL)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()

//            var title:String =MySongAdapter.myListSong.get(songIndex).get("title")
//            songTitle.setText(title);
//            imagePause.setBackgroundResource(R.drawable.pause)


//        PlayActivity.Seeckbar?.setMax(100)
//        PlayActivity.Seeckbar?.setProgress(0)
//
//            updateProgress()
//        try {
//
//        } catch (ex:Exception )
//        {
////            e.printStackTrace();
//        }

    }


//    fun loadAlbumArt(albumId: SongInfo, view:RoundedImageView){
//        var artworkUri:Uri = Uri.parse("content://media/external/audio/albumart");
//        var path:Uri = ContentUris.withAppendedId(artworkUri, albumId.toLong());
//        Glide.with(view.getContext()).load(path).into(view);
//    }


}


}
