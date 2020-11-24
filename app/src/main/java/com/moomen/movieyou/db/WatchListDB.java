package com.moomen.movieyou.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.moomen.movieyou.model.db.WatchListModel;

import java.util.ArrayList;

public class WatchListDB extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;
    Context context;

    public WatchListDB(@Nullable Context context) {
        super(context, "WatchListDB", null, 1);
        sqLiteDatabase = getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WatchListModel.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + WatchListModel.TABLE_NAME);
        onCreate(db);
    }

    public boolean addMovieTOWatchList(int page, String movieID, String isChecked, String nameMovie, String mediaType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WatchListModel.COL_PAGE, page);
        contentValues.put(WatchListModel.COL_MOVIE_ID, movieID);
        contentValues.put(WatchListModel.COL_MOVIE_IS_CHECKED, isChecked);
        contentValues.put(WatchListModel.COL_MOVIE_NAME, nameMovie);
        contentValues.put(WatchListModel.COL_MEDIA_TYPE, mediaType);
        contentValues.put(WatchListModel.COL_ID, mediaType + movieID);
        return sqLiteDatabase.insert(WatchListModel.TABLE_NAME, null, contentValues) != -1;
    }

    public void deleteMovieFromWatchList(String movieTypeWithID) {
        sqLiteDatabase.delete(WatchListModel.TABLE_NAME, WatchListModel.COL_ID + " = ?", new String[]{movieTypeWithID});
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList<WatchListModel> getMovieFromWatchList(String movieID, String mediaType) {
        ArrayList<WatchListModel> item = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + WatchListModel.TABLE_NAME +
                " where " + WatchListModel.COL_MOVIE_ID + " = ? AND " + WatchListModel.COL_MOVIE_IS_CHECKED + " = ? AND " + WatchListModel.COL_MEDIA_TYPE +
                " = ?", new String[]{String.valueOf(movieID), "true", String.valueOf(mediaType)}, null);
        try {
            while (cursor.moveToNext()) {
                WatchListModel a = new WatchListModel();
                a.setIdMovieWatchList(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MOVIE_ID)));
                a.setIsChecked(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MOVIE_IS_CHECKED)));
                a.setNameMovie(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MOVIE_NAME)));
                a.setMediaType(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MEDIA_TYPE)));
                a.setId(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_ID)));
                item.add(a);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return item;
    }

    public ArrayList<WatchListModel> getAllMovieForPage(String page) {
        ArrayList<WatchListModel> item = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + WatchListModel.TABLE_NAME +
                " where " + WatchListModel.COL_PAGE + " =? ", new String[]{page}, null);
        try {
            while (cursor.moveToNext()) {
                WatchListModel a = new WatchListModel();
                a.setPage(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_PAGE)));
                a.setIdMovieWatchList(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MOVIE_ID)));
                a.setIsChecked(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MOVIE_IS_CHECKED)));
                a.setNameMovie(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MOVIE_NAME)));
                a.setMediaType(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MEDIA_TYPE)));
                a.setId(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_ID)));
                item.add(a);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return item;
    }

    public ArrayList<WatchListModel> getAllMovieAreChecking() {
        ArrayList<WatchListModel> item = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + WatchListModel.TABLE_NAME +
                " where " + WatchListModel.COL_MOVIE_IS_CHECKED + " =? ", new String[]{"true"}, null);
        try {
            while (cursor.moveToNext()) {
                WatchListModel a = new WatchListModel();
                a.setPage(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_PAGE)));
                a.setIdMovieWatchList(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MOVIE_ID)));
                a.setIsChecked(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MOVIE_IS_CHECKED)));
                a.setNameMovie(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MOVIE_NAME)));
                a.setMediaType(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_MEDIA_TYPE)));
                a.setId(cursor.getString(cursor.getColumnIndex(WatchListModel.COL_ID)));
                item.add(a);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return item;
    }

    public int getSize() {
        int size = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + WatchListModel.TABLE_NAME +
                " where " + WatchListModel.COL_MOVIE_IS_CHECKED + " =? ", new String[]{"true"}, null);
        try {
            while (cursor.moveToNext()) {
                size++;
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return size;
    }
}
