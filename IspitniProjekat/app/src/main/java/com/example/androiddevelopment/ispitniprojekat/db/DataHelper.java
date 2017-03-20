package com.example.androiddevelopment.ispitniprojekat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;

import com.example.androiddevelopment.ispitniprojekat.db.model.Contact;
import com.example.androiddevelopment.ispitniprojekat.db.model.ContactList;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by androiddevelopment on 20.3.17..
 */

public class DataHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "priprema.db";
    private static final int    DATABASE_VERSION = 1;


    private Dao<Contact, Integer> mContactDao;
    private Object mProductDao;
    private Dao contactDao;
    private Dao<ContactList, ?> mDao;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ContactList.class);
            TableUtils.createTable(connectionSource, Contact.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ContactList.class, true);
            TableUtils.dropTable(connectionSource, Contact.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getMovieDao() throws SQLException {
        if (mProductDao == null) {
            mProductDao = getDao(ContactList.class);
        }

        return mProductDao;
    }

    public Dao<Contact, Integer> getContactListDao() throws SQLException {
        if (mProductDao == null) {
            mDao = getDao(ContactList.class);
        }

        return mContactDao;
    }

    //obavezno prilikom zatvarnaj rada sa bazom osloboditi resurse
    @Override
    public void close() {
        mProductDao = null;
        mContactDao = null;

        super.close();
    }

    public Dao getContactDao() {
        return contactDao;
    }

    public void setContactDao(Dao contactDao) {
        this.contactDao = contactDao;
    }
}

