package com.life.ammar.movies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by ammar on 20/03/16.
 */
public class loadMovies extends AsyncTask<String, Void, Void> {
    public Context context;
    public loadMovies(Context _context) {
        context = _context;
    }

    @Override
    protected Void doInBackground(String... params) {
        String json = null;
        try {
            URL url = new URL(params[0] + params[1] + params[2]);
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
        } catch (MalformedURLException e) {}
        catch (IOException e) {}
        catch (Exception e) {}
        if(json != null) {
            Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create();
            JsonArray jsonArray = new JsonParser().parse(json).getAsJsonObject().getAsJsonArray("results");
            Realm realm = Realm.getInstance(context);
            for (int i=0; i<jsonArray.size(); i++) {
                realm.beginTransaction();
                MovieEntry movieEntry = gson.fromJson(jsonArray.get(i), MovieEntry.class);
                if(params[1].equals("sort_by=popularity.desc")) {
                    movieEntry.setType(0);
                } else {
                    movieEntry.setType(1);
                }
                if (realm.where(MovieEntry.class).equalTo("id",movieEntry.getId()).findFirst() == null) {
                    movieEntry.setFavourite(false);
                }
                realm.copyToRealmOrUpdate(movieEntry);
                realm.commitTransaction();
                // Add trailers
                {
                    String jsonT = null;
                    try {
                        URL urlT = new URL("https://api.themoviedb.org/3/movie/" + movieEntry.getId()
                                + "/trailers?" + params[2]);
                        HttpURLConnection httpURLConnectionT = (HttpURLConnection) urlT.openConnection();
                        httpURLConnectionT.setRequestMethod("GET");
                        httpURLConnectionT.connect();
                        InputStream inputStreamT = httpURLConnectionT.getInputStream();
                        BufferedReader bufferedReaderT = new BufferedReader(new InputStreamReader(inputStreamT));
                        StringBuffer stringBufferT = new StringBuffer();
                        String lineT;
                        while ((lineT = bufferedReaderT.readLine()) != null) {
                            stringBufferT.append(lineT + '\n');
                        }
                        jsonT = stringBufferT.toString();
                    } catch (MalformedURLException e) {
                    } catch (IOException e) {
                    } catch (Exception e) {
                    }
                    if (jsonT != null) {
                        Gson gsonT = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                            @Override
                            public boolean shouldSkipField(FieldAttributes f) {
                                return f.getDeclaringClass().equals(RealmObject.class);
                            }

                            @Override
                            public boolean shouldSkipClass(Class<?> clazz) {
                                return false;
                            }
                        }).create();
                        JsonArray jsonArrayT = new JsonParser().parse(jsonT).getAsJsonObject().getAsJsonArray("youtube");
                        Realm realmT = Realm.getInstance(context);
                        for (int iT=0; iT<jsonArrayT.size(); iT++) {
                            realmT.beginTransaction();
                            MovieTrailer movieTrailer = gsonT.fromJson(jsonArrayT.get(iT), MovieTrailer.class);
                            movieTrailer.setId(movieEntry.getId());
                            realmT.copyToRealmOrUpdate(movieTrailer);
                            realmT.commitTransaction();
                        }
                    }
                }
                {
                    String jsonR = null;
                    try {
                        URL urlR = new URL("https://api.themoviedb.org/3/movie/" + movieEntry.getId()
                                + "/reviews?" + params[2]);
                        HttpURLConnection httpURLConnectionR = (HttpURLConnection) urlR.openConnection();
                        httpURLConnectionR.setRequestMethod("GET");
                        httpURLConnectionR.connect();
                        InputStream inputStreamR = httpURLConnectionR.getInputStream();
                        BufferedReader bufferedReaderR = new BufferedReader(new InputStreamReader(inputStreamR));
                        StringBuffer stringBufferR = new StringBuffer();
                        String lineR;
                        while ((lineR = bufferedReaderR.readLine()) != null) {
                            stringBufferR.append(lineR + '\n');
                        }
                        jsonR = stringBufferR.toString();
                    } catch (MalformedURLException e) {
                    } catch (IOException e) {
                    } catch (Exception e) {
                    }
                    if (jsonR != null) {
                        Gson gsonR = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                            @Override
                            public boolean shouldSkipField(FieldAttributes f) {
                                return f.getDeclaringClass().equals(RealmObject.class);
                            }

                            @Override
                            public boolean shouldSkipClass(Class<?> clazz) {
                                return false;
                            }
                        }).create();
                        JsonArray jsonArrayR = new JsonParser().parse(jsonR).getAsJsonObject().getAsJsonArray("results");
                        Realm realmR = Realm.getInstance(context);
                        for (int iR=0; iR<jsonArrayR.size(); iR++) {
                            realmR.beginTransaction();
                            MovieReview movieReview = gsonR.fromJson(jsonArrayR.get(iR), MovieReview.class);
                            movieReview.setMovieId(movieEntry.getId());
                            realmR.copyToRealmOrUpdate(movieReview);
                            realmR.commitTransaction();
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Realm realm = Realm.getInstance(context);
        RealmResults<MovieEntry> results;
        MainFragment.movieList.clear();
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
                MainFragment.movieList.add(movie);
            } // If favourite movies displaying
        } else if(sharedPreferences.getString("OrderBy","Most Popular").equals("Favourites")) {
            results = realm.where(MovieEntry.class).equalTo("favourite",true).findAll();
            if(results.size()<1) {
                Toast.makeText(context, "There is no favourite yet", Toast.LENGTH_SHORT).show();
            }
            for (int i=0; i<results.size(); i++) {
                MovieEntry movieEntry = results.get(i);
                Movie movie = null;
                try {
                    // w92", "w154", "w185", "w342", "w500", "w780", or "original"
                    movie = new Movie(new URL("http://image.tmdb.org/t/p/" + "w500" + movieEntry.getPosterPath()), movieEntry.getId());
                } catch (MalformedURLException e) {}
                MainFragment.movieList.add(movie);
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
                MainFragment.movieList.add(movie);
            }
        }
        MainFragment.moviesAdapter.notifyDataSetChanged();
    }
}
