package com.life.ammar.movies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {
    SharedPreferences sharedPreferences;
    public static RecyclerView recyclerView;
    MoviesAdapter moviesAdapter;
    List<Movie> movieList;
    Realm realm;
    RealmResults<MovieEntry> results;
    static FragmentTransaction Gtransaction;
    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_main, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        recyclerView = (RecyclerView) viewRoot.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        movieList = new ArrayList<>();
        realm = Realm.getInstance(getContext());
        moviesAdapter = new MoviesAdapter(movieList, getContext());
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                Gtransaction = getActivity().getSupportFragmentManager().beginTransaction();
                /*Bundle args = new Bundle();
                args.putInt("idAsInt", getActivity().
                        getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("idAsInt", 0));
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.container2, detailsFragment).commit();*/
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });
        return viewRoot;
    }

    @Override
    public void onStart() {
        super.onStart();
        setMovieList();
    }

    public void setMovieList() {
        movieList.clear();
        if(sharedPreferences.getString("OrderBy","Most Popular").equals("Most Popular")) {
            results = realm.where(MovieEntry.class).equalTo("type",0).findAll();
            for (int i=0; i<results.size(); i++) {
                MovieEntry movieEntry = results.get(i);
                Movie movie = null;
                try {
                    // w92", "w154", "w185", "w342", "w500", "w780", or "original"
                    movie = new Movie(new URL("http://image.tmdb.org/t/p/" + "w185" + movieEntry.getPosterPath()), movieEntry.getId());
                } catch (MalformedURLException e) {}
                movieList.add(movie);
            }
        } else if(sharedPreferences.getString("OrderBy","Most Popular").equals("Favourites")) {
            results = realm.where(MovieEntry.class).equalTo("favourite",true).findAll();
            for (int i=0; i<results.size(); i++) {
                MovieEntry movieEntry = results.get(i);
                Movie movie = null;
                try {
                    // w92", "w154", "w185", "w342", "w500", "w780", or "original"
                    movie = new Movie(new URL("http://image.tmdb.org/t/p/" + "w185" + movieEntry.getPosterPath()), movieEntry.getId());
                } catch (MalformedURLException e) {}
                movieList.add(movie);
            }
        } else {
            results = realm.where(MovieEntry.class).equalTo("type",1).findAll();
            for (int i=0; i<results.size(); i++) {
                MovieEntry movieEntry = results.get(i);
                Movie movie = null;
                try {
                    // w92", "w154", "w185", "w342", "w500", "w780", or "original"
                    movie = new Movie(new URL("http://image.tmdb.org/t/p/" + "w185" + movieEntry.getPosterPath()), movieEntry.getId());
                } catch (MalformedURLException e) {}
                movieList.add(movie);
            }
        }
        moviesAdapter.notifyDataSetChanged();
    }

    public void loadMovies() {
        String order_by = "sort_by=";
        if(sharedPreferences.getString("OrderBy","Most Popular").equals("Most Popular")) {
            order_by += "popularity.desc";
        } else {
            order_by += "vote_average.desc";
        }
        new loadMovies(getContext())
                .execute(new String[]{"https://api.themoviedb.org/3/discover/movie?", order_by,
                        getResources().getString(R.string.apiKey)});
    }
}
