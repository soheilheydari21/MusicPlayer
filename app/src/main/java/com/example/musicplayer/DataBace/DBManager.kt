package com.example.musicplayer.DataBace

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import com.example.musicplayer.Activity.MainActivity

class DBManager {

    val dbName = "MusicDB"
    val dbTable = "tblMusic"
    val colID = "ID"
    val colTitle = "Title"
    val colArtist = "Artist"
    val dbVertion = 1

    val sqlCreatTable = "CREATE TABLE IF NOT EXISTS " + dbTable +" (" + colID +
            " INTEGER PRIMARY KEY, " + colTitle + " TEXT, " + colArtist + " TEXT);"

    var sqlDB:SQLiteDatabase ?= null

    constructor(context: MainActivity)
    {
        val db = DatabaseHelper(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelper : SQLiteOpenHelper
    {
        var context: Context ?= null
        constructor(context: Context): super(context, dbName, null, dbVertion)
        {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreatTable)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS " + dbTable)
        }
    }

    fun Insert(values:ContentValues) : Long
    {
        val ID = sqlDB!!.insert(dbTable,"", values)
        return ID
    }

    fun RunQuery(columns:Array<String>, selection:String, selectionArgs:Array<String>,sortOrder:String):Cursor
    {
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        val cursor = qb.query(sqlDB,columns,selection,selectionArgs,null,null,sortOrder)
        return cursor
    }
}