package com.life.ammar.movies;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {
    MoviesDB moviesDB;
    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        moviesDB = new MoviesDB(getContext());
        Cursor cursor = moviesDB.selectEntry(getActivity().getIntent().getExtras().getInt("IDAsInt") + "");
        ImageView BackImage = (ImageView) rootView.findViewById(R.id.backIV);
        TextView title = (TextView) rootView.findViewById(R.id.titleTextView);
        TextView overView = (TextView) rootView.findViewById(R.id.overviewTextView);
        TextView releaseDate = (TextView) rootView.findViewById(R.id.releaseDateTextView);
        Picasso.with(getContext()).load(String.valueOf(cursor.getString(0))).into(BackImage);
        title.setText(cursor.getString(1));
        overView.setText(cursor.getString(2));
        releaseDate.setText(cursor.getString(3));
        return rootView;
    }
}
