package com.life.ammar.movies;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
    public static MoviesAdapter moviesAdapter;
    static List<Movie> movieList;
    Realm realm;
    RealmResults<MovieEntry> results;
    static FragmentTransaction Gtransaction;
    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_main, container, false);
        this.setHasOptionsMenu(true);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        recyclerView = (RecyclerView) viewRoot.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        movieList = new ArrayList<>();
        realm = Realm.getInstance(getContext());
        moviesAdapter = new MoviesAdapter(movieList, getContext());
        recyclerView.setAdapter(moviesAdapter);
        if(Main.isTablet()) {
            recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    Gtransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                }
            });
        }
        return viewRoot;
    }

    @Override
    public void onStart() {
        super.onStart();
        setMovieList();
    }

    public void setMovieList() {
        movieList.clear();
        // If arranged with most popular
        if(sharedPreferences.getString("OrderBy","Most Popular").equals("Most Popular")) {
            results = realm.where(MovieEntry.class).equalTo("type",0).findAll();
            for (int i=0; i<results.size(); i++) {
                MovieEntry movieEntry = results.get(i);
                Movie movie = null;
                try {
                    // w92", "w154", "w185", "w342", "w500", "w780", or "original"
                    movie = new Movie(new URL("http://image.tmdb.org/t/p/" + "w500" + movieEntry.getPosterPath()), movieEntry.getId());
                } catch (MalformedURLException e) {}
                movieList.add(movie);
            } // If favourite movies displaying
        } else if(sharedPreferences.getString("OrderBy","Most Popular").equals("Favourites")) {
            results = realm.where(MovieEntry.class).equalTo("favourite",true).findAll();
            if(results.size()<1) {
                Toast.makeText(getContext(), "There is no favourite yet", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(getContext(), results.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            for (int i=0; i<results.size(); i++) {
                MovieEntry movieEntry = results.get(i);
                Movie movie = null;
                try {
                    // w92", "w154", "w185", "w342", "w500", "w780", or "original"
                    movie = new Movie(new URL("http://image.tmdb.org/t/p/" + "w500" + movieEntry.getPosterPath()), movieEntry.getId());
                } catch (MalformedURLException e) {}
                movieList.add(movie);
            } // If arranged with ordering by
        } else {
            results = realm.where(MovieEntry.class).equalTo("type",1).findAll();
            for (int i=0; i<results.size(); i++) {
                MovieEntry movieEntry = results.get(i);
                Movie movie = null;
                try {
                    // w92", "w154", "w185", "w342", "w500", "w780", or "original"
                    movie = new Movie(new URL("http://image.tmdb.org/t/p/" + "w500" + movieEntry.getPosterPath()), movieEntry.getId());
                } catch (MalformedURLException e) {}
                movieList.add(movie);
            }
        }
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
