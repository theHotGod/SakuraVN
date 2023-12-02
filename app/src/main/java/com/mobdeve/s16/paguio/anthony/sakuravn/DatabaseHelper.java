package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "USER_INFO";
    // Table
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String INDEX = "index";
    // DB Info
    public static final String DB_NAME = "PLAYER_INFO.db";
    // DB Ver
    public static final int DB_VERSION = 1;

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT NOT NULL, " +
                    "`" + INDEX + "` INTEGER NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
