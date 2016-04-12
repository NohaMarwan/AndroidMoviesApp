package com.life.ammar.movies;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ammar on 20/03/16.
 */
public class loadMovies extends AsyncTask<String, Void, Movie[]> {
    public Context context;
    MoviesDB moviesDB;
    public loadMovies(Context _context) {
        context = _context;
        moviesDB = new MoviesDB(context);
    }
    public Movie[] MoviesFromJson(String json) throws JSONException {
        if(json != null) {
            Movie movies[];
            JSONObject jsonObject;
            JSONArray jsonArray = new JSONObject(json).getJSONArray("results");
            movies = new Movie[jsonArray.length()];
            for (int i=0; i<jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                URL url = null;
                try {
                    // "w92", "w154", "w185", "w342", "w500", "w780", or "original"
                    url = new URL("http://image.tmdb.org/t/p/w342" + jsonObject.getString("poster_path"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                movies[i] = new Movie(url, jsonObject.getInt("id"));
            }
            return movies;
        }
        return null;
    }

    public void udateDatabaseFromJson(String json) throws JSONException{
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONObject(json).getJSONArray("results");
        for (int i=0; i<jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            moviesDB.createEntry(jsonObject.getInt("id"), jsonObject.getBoolean("adult"),
                    "http://image.tmdb.org/t/p/w780" + jsonObject.getString("backdrop_path"),
                    "category", jsonObject.getString("overview"), jsonObject.getString("original_title"),
                    jsonObject.getString("release_date"),
                    "http://image.tmdb.org/t/p/w342" + jsonObject.getString("poster_path"));
            }
        }

    @Override
    protected Movie[] doInBackground(String... params) {
        String json = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + '\n');
            }
            json = stringBuffer.toString();
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        } catch (Exception e) {

        }
        try {
            udateDatabaseFromJson(json);
            return MoviesFromJson(json);
        } catch (JSONException e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Movie... params) {
        if (params != null)
            MainFragment.moviesGrid.setAdapter(new MoviesAdapter(context, R.layout.movie_grid_item, params));
    }
}
