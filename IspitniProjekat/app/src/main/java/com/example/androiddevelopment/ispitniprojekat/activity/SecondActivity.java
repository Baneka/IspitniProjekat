package com.example.androiddevelopment.ispitniprojekat.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.androiddevelopment.ispitniprojekat.db.model.ContactList;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import static android.R.attr.name;
import static android.R.attr.rating;
import static com.example.androiddevelopment.ispitniprojekat.activity.MainActivity.NOTIF_STATUS;
import static com.example.androiddevelopment.ispitniprojekat.activity.MainActivity.NOTIF_TOAST;

/**
 * Created by androiddevelopment on 20.3.17..
 */
public class SecondActivity extends AppCompatActivity {
    private DataHelper databaseHelper;
    private SharedPreferences prefs;
    private Contact c;

    private EditText name;
    private EditText bio;
    private EditText birth;
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

            name = (EditText) findViewById(R.id.actor_name);
            bio = (EditText) findViewById(R.id.actor_biography);
            birth = (EditText) findViewById(R.id.actor_birth);
            rating = (RatingBar) findViewById(R.id.acrtor_rating);

            name.setText(c.getmName());
            bio.setText(c.getmBiography());
            birth.setText(c.getmBirth());
            rating.setRating(c.getmScore());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ListView listView = (ListView) findViewById(R.id.contact_list);

        try {
            List<ContactList> list = getDatabaseHelper().getMovieDao().queryBuilder()
                    .where()
                    .eq(ContactList.FIELD_NAME_USER, c.getmId())
                    .query();

            ListAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ContactList contactList = (ContactList) listView.getItemAtPosition(position);
                    Toast.makeText(SecondActivity.this, c.getmName() + " " + c.getmGenre() + " " + c.getmYear(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.contact_list);

        if (listview != null){
            ArrayAdapter<ContactList> adapter = (ArrayAdapter<ContactList>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<ContactList> list = getDatabaseHelper().getMovieDao().queryBuilder()
                            .where()
                            .eq(ContactList.FIELD_NAME_USER, c.getmId())
                            .query();

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
        mBuilder.setContentTitle("Pripremni test");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_add);

        mBuilder.setLargeIcon(bm);
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void showMessage(String message){
        boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
        boolean status = prefs.getBoolean(NOTIF_STATUS, false);

        if (toast){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        if (status){
            showStatusMesage(message);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.priprema_add_contact_list:
                //OTVORI SE DIALOG UNESETE INFORMACIJE
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.contact_list_dialog);

                Button add = (Button) dialog.findViewById(R.id.add_contact_list);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText name = (EditText) dialog.findViewById(R.id.contact_name);
                        EditText genre = (EditText) dialog.findViewById(R.id.contact_genre);
                        EditText year = (EditText) dialog.findViewById(R.id.contact_year);

                        ContactList contactList = new ContactList();
                        contactList.setmName(name.getText().toString());
                        contactList.setmGenre(genre.getText().toString());
                        contactList.setmYear(year.getText().toString());
                        contactList.setmUser(contactList);

                        dialog.create();
                        //URADITI REFRESH
                        refresh();

                        showMessage("New Contact added Contact ");

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
            case R.id.priprema_edit:
                Contact.setmName(name.getText().toString());
                Contact.setmBirth(birth.getText().toString());
                Contact.setmBiography(bio.getText().toString());
                Contact.setmScore(rating.getRating());

                try {
                    getDatabaseHelper().getContactDao().update(c);

                    showMessage("Actor detail updated");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.priprema_remove:
                try {
                    getDatabaseHelper().getContactDao().delete(c);

                    showMessage("Contact deleted");

                    finish(); //moramo pozvati da bi se vratili na prethodnu aktivnost
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

        // nakon rada sa bazo podataka potrebno je obavezno
        //osloboditi resurse!
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}

