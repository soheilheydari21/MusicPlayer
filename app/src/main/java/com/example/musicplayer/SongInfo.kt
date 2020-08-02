package com.example.musicplayer

import android.icu.text.CaseMap
import android.provider.MediaStore

class SongInfo
{
//    var ID:Int ?= null
    var Title:String ?= null
    var Desc:String ?= null
    var SongURL:String ?= null


    constructor(title:String,desc:String,songURL:String)
    {
        this.Title = title
        this.Desc = desc
        this.SongURL = songURL
    }
}