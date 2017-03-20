package com.example.androiddevelopment.ispitniprojekat.db.model;

/**
 * Created by androiddevelopment on 20.3.17..
 */

public class ContactList {
    public static final Object FIELD_NAME_USER = null;
    private String mName;
    private String mGenre;
    private String mYear;
    private ContactList mUser;

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmName() {
        return mName;
    }

    public void setmGenre(String mGenre) {
        this.mGenre = mGenre;
    }

    public String getmGenre() {
        return mGenre;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public String getmYear() {
        return mYear;
    }

    public void setmUser(ContactList mUser) {
        this.mUser = mUser;
    }

    public ContactList getmUser() {
        return mUser;
    }
}
