package com.life.ammar.movies;

import android.provider.BaseColumns;

/**
 * Created by ammar on 25/03/16.
 */
public class MoviesEntry implements BaseColumns {
    public static final String tableName = "movies";
    public static final String ID = "ID";
    public static final String adult = "adult";
    public static final String backGroundPath = "backdropPath";
    public static final String title = "title";
    public static final String overView = "overView";
    public static final String releaseDate = "releaseDate";
    public static final String posterPath = "posterPath";
    public static final String category = "category";
}
