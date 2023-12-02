package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    // Add a new record
    public long addRecord(String name, String email, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.EMAIL, email);
        contentValues.put(DatabaseHelper.INDEX, index);
        return database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    // Update an existing record
    public int updateRecord(long id, String name, String email, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.EMAIL, email);
        contentValues.put(DatabaseHelper.INDEX, index);
        return database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + "=" + id, null);
    }

    // Delete a record
    public void deleteRecord(long id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + id, null);
    }

    // Fetch a specific record by ID
    public Cursor fetchRecordById(long id) {
        String[] columns = new String[]{DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.EMAIL, DatabaseHelper.INDEX};
        return database.query(DatabaseHelper.TABLE_NAME, columns, DatabaseHelper._ID + "=" + id, null, null, null, null);
    }
}