package com.example.musicplayer.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DBManager {

    val dbName = "LikeMusic"
    val dbTable = "TableLike"
    val colID = "ID"
    val colTitle = "Title"
    val colArtist = "Artist"
    val colCover = "Cover"
    val dbVersion = 1

    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + dbTable +" (" + colID +
            " INTEGER PRIMARY KEY, " + colTitle + " TEXT, " + colArtist + " TEXT, " + colCover + " IMAGE);"

    var sqlDB:SQLiteDatabase? = null
    constructor(context: Context){
        val db = DatabaseHelper(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelper:SQLiteOpenHelper
    {
        var mContext:Context? = null
        constructor(context: Context):super(context, dbName, null, dbVersion){
            this.mContext = context

        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(mContext,"create database",Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS " + dbTable )
        }

    }

    fun Insert(values:ContentValues): Long
    {
        val ID = sqlDB!!.insert(dbTable, "", values)
        return ID
    }

//    fun RunQuery(columns:Array<String>, selection:String, selectionArgs:Array<String>, sortOrder:String) : Cursor
//    {
//        val qb:SQLiteQueryBuilder
//        qb.tables = dbTable
//        val cursor = qb.query(sqlDB, columns, selection, selectionArgs, null, null, sortOrder)
//        return cursor
//    }

}