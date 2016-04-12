package com.life.ammar.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ammar on 25/03/16.
 */
public class MoviesDB {
    private MoviesDBHelper moviesDBHelper;
    private SQLiteDatabase db;

    public MoviesDB(Context context) {
        moviesDBHelper = new MoviesDBHelper(context);
        db = moviesDBHelper.getWritableDatabase();
    }

    public long createEntry (int id, boolean adult, String backGroundPath, String category, String overView,
                             String title, String releaseDate, String posterPath) {
        ContentValues values = new ContentValues();
        values.put(MoviesEntry.ID, id);
        values.put(MoviesEntry.adult, adult);
        values.put(MoviesEntry.backGroundPath, backGroundPath);
        values.put(MoviesEntry.category, category);
        values.put(MoviesEntry.overView, overView);
        values.put(MoviesEntry.title, title);
        values.put(MoviesEntry.releaseDate, releaseDate);
        values.put(MoviesEntry.posterPath, posterPath);
        return db.insert(MoviesEntry.tableName, null, values);
    }

    public Cursor selectEntry(String id) {
        String[] _id = new String[] {id};
        String[] cols = new String[] {MoviesEntry.backGroundPath,MoviesEntry.title,MoviesEntry.overView, MoviesEntry.releaseDate};
        Cursor mCursor = db.query(true, MoviesEntry.tableName,cols,"ID = ?"
                , _id, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
