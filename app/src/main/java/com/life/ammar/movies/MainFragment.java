package com.life.ammar.movies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {
    SharedPreferences sharedPreferences;
    public static GridView moviesGrid;
    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_main, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        moviesGrid = (GridView) viewRoot.findViewById(R.id.grid_movies);
        moviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Details.class);
                intent.putExtra("IDAsInt", MoviesAdapter.movies[position].getId());
                startActivity(intent);
            }
        });
        return viewRoot;
    }

    @Override
    public void onStart() {
        super.onStart();
        String order_by = "sort_by=";
        if(sharedPreferences.getString("OrderBy","Most Popular").equals("Most Popular")) {
            order_by += "popularity.desc";
        } else {
            order_by += "vote_average.desc";
        }
        new loadMovies(getContext())
                .execute("https://api.themoviedb.org/3/discover/movie?" + order_by +
                        getResources().getString(R.string.apiKey));
    }

}
