package com.life.ammar.movies;

import android.content.Context;
import android.os.AsyncTask;
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
                if(params[1].equals("popularity.desc")) {
                    movieEntry.setType(0);
                } else {
                    movieEntry.setType(1);
                }
                realm.copyToRealmOrUpdate(movieEntry);
                realm.commitTransaction();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
