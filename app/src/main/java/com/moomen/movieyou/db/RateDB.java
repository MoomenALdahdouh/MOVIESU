package com.moomen.movieyou.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RateDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "WATCHLIST";

    public static final String COL_RATED = "RATED";
    public static final String COL_REMIND = "REMIND";
    public static final String COL_THANKS = "THANKS";
    public static final String TABLE_CREATE = " create table " + TABLE_NAME +
            "(" +
            COL_RATED + " varchar(20)," +
            COL_REMIND + " varchar(30)," +
            COL_THANKS + " varchar(30) " +
            ")";
    SQLiteDatabase sqLiteDatabase;
    Context context;
    private String rateApp;

    public RateDB(@Nullable Context context) {
        super(context, "RateDB", null, 1);
        sqLiteDatabase = getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean writeReatedStatuse(String isRate, String remind, String thanks) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RATED, isRate);
        contentValues.put(COL_REMIND, remind);
        contentValues.put(COL_THANKS, thanks);
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public String readReatedStatuse() {
        String statuse = "";
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + TABLE_NAME + " where " + COL_RATED +
                " = ?", new String[]{"true"}, null);
        try {
            while (cursor.moveToNext()) {
                statuse = cursor.getString(cursor.getColumnIndex(COL_RATED));
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return statuse;
    }

    public ArrayList<String> readRemindStatuse() {
        ArrayList<String> statuse = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + TABLE_NAME + " where " + COL_REMIND +
                " = ?", new String[]{"true"}, null);
        try {
            while (cursor.moveToNext()) {
                statuse.add(cursor.getString(cursor.getColumnIndex(COL_REMIND)));
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return statuse;
    }

    public ArrayList<String> readThankStatuse() {
        ArrayList<String> statuse = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + TABLE_NAME + " where " + COL_THANKS +
                " = ?", new String[]{"true"}, null);
        try {
            while (cursor.moveToNext()) {
                statuse.add(cursor.getString(cursor.getColumnIndex(COL_THANKS)));
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return statuse;
    }

}
