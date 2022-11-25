package com.example.weather.DB

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import com.example.weather.Classes.noteClass
import android.content.ContentValues
import android.content.Context
import java.util.ArrayList

class DataBaseHelper(context: Context?) : SQLiteOpenHelper(context, "notes.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableStatement =
            "CREATE TABLE " + NOTE_TABLE + " (" + NOTE_TITLE + " TEXT, " + NOTE_TEXT + " TEXT, " + NOTE_DATE + " TEXT, " + NOTE_IMAGE + " TEXT) "
        db.execSQL(createTableStatement)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {}
    fun addOne(nclass: noteClass): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(NOTE_TITLE, nclass.noteTitle)
        cv.put(NOTE_TEXT, nclass.noteText)
        cv.put(NOTE_DATE, nclass.noteCreationDate)
        cv.put(NOTE_IMAGE, nclass.imageNote)
        val insert = db.insert(NOTE_TABLE, null, cv)
        return if (insert == -1L) {
            false
        } else {
            true
        }
    }

    //failed
    val allNotesFromLocalDB: List<noteClass>
        get() {
            val returnList: MutableList<noteClass> = ArrayList()
            val queryString = "SELECT * FROM " + NOTE_TABLE
            val db = this.readableDatabase
            val cursor = db.rawQuery(queryString, null)
            if (cursor.moveToFirst()) {
                do {
                    val noteTitle = cursor.getString(0)
                    val noteText = cursor.getString(1)
                    val noteDateCreated = cursor.getString(2)
                    val noteImage = cursor.getString(3)
                    val newNote = noteClass(
                        "" + noteText,
                        "" + noteTitle,
                        "" + noteDateCreated,
                        "" + noteImage
                    )
                    returnList.add(newNote)
                } while (cursor.moveToNext())
            } else {
                //failed
            }
            cursor.close()
            db.close()
            return returnList
        }

    fun changeNoteInfo(newTitle: String, newText: String, oldTitle: String) {
        val db = this.writableDatabase
        val query = ("UPDATE " + NOTE_TABLE + " SET " + NOTE_TITLE + " = '" + newTitle + "' WHERE "
                + NOTE_TITLE + " = '" + oldTitle + "'")
        val query2 = ("UPDATE " + NOTE_TABLE + " SET " + NOTE_TEXT + " = '" + newText + "' WHERE "
                + NOTE_TITLE + " = '" + oldTitle + "'")
        db.execSQL(query2)
        db.execSQL(query)
    }

    //failed
    val titles: ArrayList<String?>
        get() {
            val titles: ArrayList<String?> = ArrayList<String?>()
            val queryString = "SELECT " + NOTE_TITLE + " FROM " + NOTE_TABLE
            val db = this.readableDatabase
            val cursor = db.rawQuery(queryString, null)
            if (cursor.moveToFirst()) {
                do {
                    val noteTitle = cursor.getString(0)
                    titles.add(noteTitle)
                } while (cursor.moveToNext())
            } else {
                //failed
            }
            cursor.close()
            db.close()
            return titles
        }

    fun deleteNote(noteTitle: String) {
        val db = this.readableDatabase
        db.delete(NOTE_TABLE, NOTE_TITLE + "= '" + noteTitle + "'", null)
    }

    companion object {
        const val NOTE_TABLE = "NOTE_TABLE"
        const val NOTE_TITLE = "NOTE_TITLE"
        const val NOTE_TEXT = "NOTE_TEXT"
        const val NOTE_DATE = "NOTE_DATE"
        const val NOTE_IMAGE = "NOTE_IMAGE"
    }
}