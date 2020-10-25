package com.example.musicplayer.Models

import com.example.musicplayer.Helper.songe

public class SongInfo {

    var Title:String ?= null
    var Desc:String ?= null
    var SongURL:String ?= null
    var Cover:String ?= null

    public constructor(title:String, desc:String, songURL:String, cover:String) {
        this.Title = title
        this.Desc = desc
        this.SongURL = songURL
        this.Cover = cover
    }

    fun getpach(): String? {
        return SongURL
    }

    fun setpach(songURL: String) {
        this.SongURL = songURL
    }

    fun gettitle(): String? {
        return Title
    }

    fun settitle(title: String) {
        this.Title = title
    }


}