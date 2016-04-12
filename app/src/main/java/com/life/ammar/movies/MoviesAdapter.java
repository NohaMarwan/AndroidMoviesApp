package com.life.ammar.movies;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ammar on 07/03/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private int layout;
    static public Movie movies[] = null;

    public MoviesAdapter(Context _context, int _layout, Movie[] _movies) {
        super(_context, _layout, _movies);
        context = _context;
        layout = _layout;
        movies = _movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View movieGrid = convertView;
        ImageView imageView;
        if(convertView == null) {
            movieGrid = ((Activity) context).getLayoutInflater()
                    .inflate(layout, parent, false);
            imageView = (ImageView) movieGrid.findViewById(R.id.movie_grid_image);
            movieGrid.setTag(imageView);
        } else {
            imageView = (ImageView) movieGrid.getTag();
        }
        Movie movie = movies[position];
        Picasso.with(getContext()).load(String.valueOf(movie.getUrl())).into(imageView);
        return movieGrid;
    }
}
