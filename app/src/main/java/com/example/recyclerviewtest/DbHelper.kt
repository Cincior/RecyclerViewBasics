package com.example.recyclerviewtest

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(private val context: Context, factory: SQLiteDatabase.CursorFactory?)
    :SQLiteOpenHelper(context, DbName, factory, DbVersion)
{
    override fun onCreate(p0: SQLiteDatabase) {
        val query = ("CREATE TABLE $DbTableName (Id INTEGER PRIMARY KEY,$DbInfoCol TEXT)")
        p0.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS tabelaTest")
    }

    fun getItems() : Cursor
    {
        val db = readableDatabase

        return db.rawQuery("SELECT * FROM $DbTableName", null)
    }

    fun getItem(id: Long) : Cursor
    {
        val db = readableDatabase

        val rV = db.rawQuery("SELECT * FROM $DbTableName WHERE Id = ?", arrayOf(id.toString()))

        return rV
    }

    fun addItem(i: Item): Long
    {
        val db = writableDatabase
        val inputData = ContentValues()
        inputData.put(DbInfoCol, i.info)

        val returnedId = db.insert(DbTableName, null, inputData)
        db.close()
        return returnedId
    }

    fun deleteAll()
    {
        val db = writableDatabase

        db.delete(DbTableName, null, null)
        db.close()
    }

    companion object
    {
        private val DbName = "Test"
        private val DbVersion = 1
        val DbTableName = "tabelaTest"
        val DbInfoCol = "Info"
    }
}