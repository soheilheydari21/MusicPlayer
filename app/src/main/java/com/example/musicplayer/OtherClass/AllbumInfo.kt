package com.example.musicplayer.OtherClass

import java.util.*

class AllbumInfo()
{
    var allbum:String ?= null
    var allbum2:String ?= null
    var allbum3:String ?= null
    var title:String ?= null
    var artist:String ?= null
    var allbumUrl:String ?= null

    constructor(Allbum:String, Allbum2:String, Allbum3:String, Title:String, Artist:String, AllbumUrl:String) : this() {
        this.allbum = Allbum
        this.allbum2 = Allbum2
        this.allbum3 = Allbum3
        this.title = Title
        this.artist = Artist
        this.allbumUrl = AllbumUrl
    }
}