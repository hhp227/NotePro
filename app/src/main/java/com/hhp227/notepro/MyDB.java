package com.hhp227.notepro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDB extends SQLiteOpenHelper {
    public MyDB(Context context) {
        super(context, "My DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE note (idx INTEGER PRIMARY KEY AUTOINCREMENT, contents TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertMemo(String contents) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {contents};
        db.execSQL("INSERT INTO note(contents) VALUES (?)", args);
    }

    public List<String> selectMemo() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM note", null);

        List<String> notes = new ArrayList<>();

        while(cursor.moveToNext()) {
            String note = cursor.getString(1);
            notes.add(note);
        }
        db.close();
        return notes;
    }
}
