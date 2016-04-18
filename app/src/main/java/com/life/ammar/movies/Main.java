package com.life.ammar.movies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import io.realm.Realm;

public class Main extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getBaseContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sharedPreferences2 = getSharedPreferences("sp",Context.MODE_PRIVATE);
        if(isTablet()) {
            Realm realm = Realm.getInstance(this);
            MovieEntry movieEntry = realm.where(MovieEntry.class).findFirst();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            DetailsFragment detailFragment=new DetailsFragment();
            Bundle args = new Bundle();
            if(movieEntry!= null) {
                sharedPreferences2.edit().putInt("idAsInt",movieEntry.getId()).commit();
                args.putInt("idAsInt", sharedPreferences2.getInt("idAsInt", movieEntry.getId()));
            }
            else loadMoviesF();
            detailFragment.setArguments(args);
            transaction.add(R.id.container2, detailFragment);
            transaction.commit();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container1, new MainFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getBaseContext(), settingActivity.class));
            return true;
        }
        if (id == R.id.loadMovies) {
            loadMoviesF();
        }

        return super.onOptionsItemSelected(item);
    }
    public void loadMoviesF() {
        String order_by = "sort_by=";
        if(sharedPreferences.getString("OrderBy","Most Popular").equals("Most Popular")) {
            order_by += "popularity.desc";
            new loadMovies(getBaseContext())
                    .execute(new String[]{"https://api.themoviedb.org/3/discover/movie?", order_by,
                            "&" + getResources().getString(R.string.apiKey)});
        } else {
            order_by += "vote_average.desc";
            new loadMovies(getBaseContext())
                    .execute(new String[]{"https://api.themoviedb.org/3/discover/movie?", order_by,
                            "&" + getResources().getString(R.string.apiKey)});
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    public static boolean isTablet() {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
