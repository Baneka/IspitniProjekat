package com.example.androiddevelopment.ispitniprojekat.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.androiddevelopment.ispitniprojekat.R;
import com.example.androiddevelopment.ispitniprojekat.db.DataHelper;
import com.example.androiddevelopment.ispitniprojekat.db.model.Contact;
import com.example.androiddevelopment.ispitniprojekat.dialog.AboutDialog;
import com.example.androiddevelopment.ispitniprojekat.preferences.Preferences;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by androiddevelopment on 20.3.17..
 */

public class MainActivity extends AppCompatActivity {
    private DataHelper databaseHelper;
    private SharedPreferences prefs;


    public static String CONTACT_KEY = "CONTACT_KEY";
    public static String NOTIF_TOAST = "notif_toast";
    public static String NOTIF_STATUS = "notif_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        final ListView listView = (ListView) findViewById(R.id.contact_list);

        try {
            List<Contact> list = getDatabaseHelper().getContactDao().queryForAll();
            ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, list);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Contact c = (Contact) listView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra(CONTACT_KEY, c.getmId());
                    startActivity(intent);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.contact_list);

        if (listview != null){
            ArrayAdapter<Contact> adapter = (ArrayAdapter<Contact>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Contact> list = getDatabaseHelper().getContactDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("IspitniProjekat");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_add);

        mBuilder.setLargeIcon(bm);

        mNotificationManager.notify(1, mBuilder.build());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_new_actor:
                //DIALOG ZA UNOS PODATAKA
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.contact_dialog);

                Button add = (Button) dialog.findViewById(R.id.add_contact);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText name = (EditText) dialog.findViewById(R.id.actor_name);
                        EditText bio = (EditText) dialog.findViewById(R.id.actor_biography);
                        RatingBar rating = (RatingBar) dialog.findViewById(R.id.acrtor_rating);
                        EditText birth = (EditText) dialog.findViewById(R.id.actor_birth);

                        Contact c = new Contact();
                        c.setmName(name.getText().toString());
                        c.setmBiography(bio.getText().toString());
                        c.setmBirth(birth.getText().toString());
                        c.setmScore(rating.getRating());

                        try {
                            getDatabaseHelper().getContactDao().create(c);

                            //provera podesavanja
                            boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
                            boolean status = prefs.getBoolean(NOTIF_STATUS, false);

                            if (toast){
                                Toast.makeText(MainActivity.this, "Added new contact", Toast.LENGTH_SHORT).show();
                            }

                            if (status){
                                showStatusMesage("Added new contact");
                            }

                            //REFRESH
                            refresh();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();

                    }
                });
                final Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();

                break;
            case R.id.about:

                android.app.AlertDialog alertDialog = new AboutDialog(this).prepareDialog();
                alertDialog.show();
                break;
            case R.id.preferences:
                startActivity(new Intent(MainActivity.this, Preferences.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }




    public DataHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DataHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    public void setDatabaseHelper(DataHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
}
