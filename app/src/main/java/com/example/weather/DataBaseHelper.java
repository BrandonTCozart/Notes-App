package com.example.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String NOTE_TABLE = "NOTE_TABLE";

    public static final String NOTE_TITLE = "NOTE_TITLE";
    public static final String NOTE_TEXT = "NOTE_TEXT";
    public static final String NOTE_DATE = "NOTE_DATE";
    public static final String NOTE_IMAGE = "NOTE_IMAGE";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + NOTE_TABLE + " (" + NOTE_TITLE + " TEXT, " + NOTE_TEXT + " TEXT, " + NOTE_DATE + " TEXT, " + NOTE_IMAGE + " TEXT) ";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean addOne(noteClass nclass){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOTE_TITLE, nclass.getNoteTitle());
        cv.put(NOTE_TEXT, nclass.getNoteText());
        cv.put(NOTE_DATE, nclass.getNoteCreationDate());
        cv.put(NOTE_IMAGE, nclass.getImageNote());

        long insert = db.insert(NOTE_TABLE, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }

    }

    public List<noteClass> getAllNotesFromLocalDB(){

        List<noteClass> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + NOTE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String noteTitle = cursor.getString(0);
                String noteText = cursor.getString(1);
                String noteDateCreated = cursor.getString(2);
                String noteImage = cursor.getString(3);

                //noteClass newNote = new noteClass(noteText, noteTitle, noteDateCreated, noteImage);
                noteClass newNote = new noteClass(""+noteTitle, ""+noteDateCreated);

                returnList.add(newNote);
            } while (cursor.moveToNext());

        }else{
            //failed

        }

        cursor.close();
        db.close();
        return returnList;
    }



}
