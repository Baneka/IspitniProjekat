package com.example.androiddevelopment.ispitniprojekat.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.androiddevelopment.ispitniprojekat.R;
import com.example.androiddevelopment.ispitniprojekat.db.DataHelper;
import com.example.androiddevelopment.ispitniprojekat.db.model.Contact;

import java.sql.SQLException;

import static android.R.attr.name;
import static android.R.attr.rating;

/**
 * Created by androiddevelopment on 20.3.17..
 */

public class SecondActivity extends AppCompatActivity {
    private DataHelper databaseHelper;
    private SharedPreferences prefs;
    private Contact c;
    private EditText birth;
    private EditText bio;
    private EditText name;
    private RatingBar rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int key = getIntent().getExtras().getInt(MainActivity.CONTACT_KEY);

        try {
            c = (Contact) getDatabaseHelper().getContactDao().queryForId(key);

            name = (EditText) findViewById(R.id.contact_name);
            bio = (EditText) findViewById(R.id.contact_biography);
            birth = (EditText) findViewById(R.id.contact_birth);
            rating = (RatingBar) findViewById(R.id.contact_rating);

            name.setText(c.getmName());
            bio.setText(c.getmBiography());
            birth.setText(c.getmBirth());
            rating.setRating(c.getmScore());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public DataHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public void setDatabaseHelper(DataHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
}
