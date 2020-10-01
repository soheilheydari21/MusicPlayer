package com.example.musicplayer.OtherClass

import android.icu.text.CaseMap
import android.provider.MediaStore
import android.widget.ImageView

class SongInfo
{
//    var ID:Int ?= null
    var Title:String ?= null
    var Desc:String ?= null
    var SongURL:String ?= null
    var Cover:String ?= null

    constructor(title:String, desc:String, songURL:String, cover:String)
    {
        this.Title = title
        this.Desc = desc
        this.SongURL = songURL
        this.Cover = cover
    }


}