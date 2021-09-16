package com.example.musicPlayer.Models

class LikeInfo {

    var ID:Int? = null
    var Title:String? = null
    var Artist:String? = null
    var SongURL:String ?= null
    var Cover:String ?= null

    constructor(id:Int, title:String, artist:String, songUrl:String, cover:String) {
        this.ID = id
        this.Title = title
        this.Artist = artist
        this.SongURL = songUrl
        this.Cover = cover
    }

    fun getpach(): String? { return SongURL }
    fun setpach(songURL: String) { this.SongURL = songURL }

    fun gettitle(): String? { return Title }
    fun settitle(title: String) { this.Title = title }

    fun getartist(): String? { return Artist }
    fun setartist(artist: String) { this.Artist = artist }

}