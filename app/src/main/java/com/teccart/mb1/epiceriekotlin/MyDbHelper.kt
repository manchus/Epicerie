package com.teccart.mb1.epiceriekotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        val query =
            "CREATE TABLE IF NOT EXISTS produits(id integer primary key autoincrement,nom text,img integer, prix number(5,2));"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVerdbsion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS produits;"
        db.execSQL(query)
        onCreate(db)
    }
}
