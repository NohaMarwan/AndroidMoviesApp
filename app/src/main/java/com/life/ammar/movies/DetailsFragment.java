package com.life.ammar.movies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import io.realm.Realm;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {
    public DetailsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        Realm realm = Realm.getInstance(getContext());
        MovieEntry results = realm.where(MovieEntry.class).equalTo("id",
                getActivity().getIntent().getExtras().getInt("idAsInt")).findFirst();
        ImageView BackImage = (ImageView) rootView.findViewById(R.id.backIV);
        TextView title = (TextView) rootView.findViewById(R.id.titleTextView);
        TextView overView = (TextView) rootView.findViewById(R.id.overviewTextView);
        TextView releaseDate = (TextView) rootView.findViewById(R.id.releaseDateTextView);
        ImageView posterImage = (ImageView) rootView.findViewById(R.id.posterImage);
        TextView voteAvg =(TextView) rootView.findViewById(R.id.voteAvgTextView);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/" + "w185" + String.valueOf(results.getBackdropPath())).into(BackImage);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/" + "w185" + String.valueOf(results.getPosterPath())).into(posterImage);
        title.setText(results.getTitle());
        voteAvg.setText(results.getVoteAverage() + "");
        overView.setText(results.getOverview());
        releaseDate.setText(results.getReleaseDate());
        return rootView;
    }
}
