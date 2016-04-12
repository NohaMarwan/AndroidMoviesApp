package com.life.ammar.movies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ammar on 25/03/16.
 */
public class MoviesDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "movies.db";

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesEntry.tableName + " (" +
                MoviesEntry.ID + " INTEGER PRIMARY KEY," +
                MoviesEntry.adult + " Boolean NOT NULL, " +
                MoviesEntry.backGroundPath + " TEXT NOT NULL, " +
                MoviesEntry.category + " TEXT NOT NULL, " +
                MoviesEntry.overView + " TEXT NOT NULL, " +
                MoviesEntry.title + " TEXT NOT NULL," +
                MoviesEntry.releaseDate + " TEXT NOT NULL, " +
                MoviesEntry.posterPath + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.tableName);
        onCreate(db);
    }
}
