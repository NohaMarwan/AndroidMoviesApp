package com.life.ammar.movies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import io.realm.Realm;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getInstance(getBaseContext());
                MovieEntry m = realm.where(MovieEntry.class).equalTo("id",
                        getIntent().getExtras().getInt("idAsInt")).findFirst();
                realm.beginTransaction();
                m.setFavourite(!m.getFavourite());
                realm.copyToRealmOrUpdate(m);
                realm.commitTransaction();
                Toast.makeText(getBaseContext(),"Fav is now: " + m.getFavourite(), Toast.LENGTH_LONG).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
