package com.test_apps.slandshow.infokeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by Admin on 23.05.2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "usersDB";
    public static final String TABLE_NAME = "users";
    public static final String TABLE_MAIL = "mail";
    public static final String KEY_ID = "_id";
    public static final String TABLE_PASS = "password";
    public static final String TABLE_SITE_NAME = "site";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " integer primary key autoincrement," +
                TABLE_MAIL + " TEXT, " + TABLE_PASS + " TEXT, " + TABLE_SITE_NAME + " TEXT" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, TABLE_MAIL, TABLE_PASS, TABLE_SITE_NAME},
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor;
        } else {
            return null;
        }
    }

    public void addData(String mail, String pass, String site) {
        ContentValues values = new ContentValues();
        values.put(TABLE_MAIL, mail);
        values.put(TABLE_PASS, pass);
        values.put(TABLE_SITE_NAME, site);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean deleteEntry(String entryId) {
        boolean result = false;
        String q =
                "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID
                        + " = \"" + entryId + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        UserEntry p = new UserEntry();
        Cursor cursor = db.rawQuery(q, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            p.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, KEY_ID + " = ?",
                    new String[]{String.valueOf(p.getId())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    // Delete all entries in database
    public void deleteAllEntries() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
        sqLiteDatabase.close();
    }


}
