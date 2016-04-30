package com.life.ammar.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import io.realm.Realm;

public class Main extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    static Context context;
    static ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getBaseContext();
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(isTablet()) {
            Realm realm = Realm.getInstance(this);
            int type;
            if(sharedPreferences.getString("OrderBy","Most Popular").equals("Most Popular")) {
                type = 0;
            } else {
                type = 1;
            }
            MovieEntry movieEntry = realm.where(MovieEntry.class).equalTo("type",type).findFirst();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            DetailsFragment detailFragment=new DetailsFragment();
            Bundle args = new Bundle();
            if(movieEntry!= null) {
                args.putInt("idAsInt", movieEntry.getId());
            }
            detailFragment.setArguments(args);
            transaction.add(R.id.container2, detailFragment);
            transaction.commit();
        }
        if(isConnected()) {
            loadMoviesF();
        } else {
            Toast.makeText(getBaseContext(), "Please enable network connection", Toast.LENGTH_SHORT).show();
        }
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
        return true;
    }
    public void loadMoviesF() {
        String order_by = "sort_by=";
        if(sharedPreferences.getString("OrderBy","Most Popular").equals("Most Popular")) {
            order_by += "popularity.desc";
            new loadMovies(getBaseContext())
                    .execute(new String[]{"https://api.themoviedb.org/3/discover/movie?", order_by,
                            "&api_key=" + getResources().getString(R.string.apiKey)});
            new loadMovies(getBaseContext())
                    .execute(new String[]{"https://api.themoviedb.org/3/discover/movie?", "sort_by=vote_average.desc",
                            "&api_key=" + getResources().getString(R.string.apiKey)});
        } else {
            order_by += "vote_average.desc";
            new loadMovies(getBaseContext())
                    .execute(new String[]{"https://api.themoviedb.org/3/discover/movie?", order_by,
                            "&api_key=" + getResources().getString(R.string.apiKey)});
            new loadMovies(getBaseContext())
                    .execute(new String[]{"https://api.themoviedb.org/3/discover/movie?", "sort_by=popularity.desc",
                            "&api_key=" + getResources().getString(R.string.apiKey)});
        }
    }

    public static boolean isTablet() {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    public static boolean isConnected() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
