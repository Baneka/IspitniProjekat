package com.example.androiddevelopment.ispitniprojekat.db.model;

/**
 * Created by androiddevelopment on 20.3.17..
 */


public class Contact {



    private String mName;
    private String mBiography;
    private String mBirth;
    private float mScore;
    private boolean mId;
    private String mYear;
    private String mGenre;
    private static String mBrojTelefona;


    public String getmName() {
        return mName;
    }

    public static void setmName(String mName) {
    }

    public String getmBiography() {
        return mBiography;
    }

    public static void setmBiography(String mBiography) {
    }

    public String getmBirth() {
        return mBirth;
    }

    public static void setmBirth(String mBirth) {
    }

    public float getmScore() {
        return mScore;
    }

    public static void setmScore(float mScore) {
    }

    public boolean getmId() {
        return mId;
    }

    public boolean ismId() {
        return mId;
    }

    public void setmId(boolean mId){
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public String getmGenre() {
        return mGenre;
    }

    public void setmGenre(String mGenre) {
        this.mGenre = mGenre;
    }
}
