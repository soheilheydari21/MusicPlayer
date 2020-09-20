package com.example.musicplayer.OtherClass

class SongOnline {
    //    var ID:Int ?= null
    var Title:String ?= null
    var Desc:String ?= null
    var SongURL:String ?= null
    var Cover:Int ?= null

    constructor(title:String, desc:String, songURL:String, cover:Int)
    {
        this.Title = title
        this.Desc = desc
        this.SongURL = songURL
        this.Cover = cover
//        this.CoverURL = coverURL
    }
}