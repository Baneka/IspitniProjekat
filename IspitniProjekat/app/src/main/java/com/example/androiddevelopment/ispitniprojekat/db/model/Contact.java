package com.example.androiddevelopment.ispitniprojekat.db.model;

/**
 * Created by androiddevelopment on 20.3.17..
 */


public class Contact {



    private int mName;
    private int mBiography;
    private int mBirth;
    private float mScore;
    private boolean mId;

    public int getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmBiography() {
        return mBiography;
    }

    public void setmBiography(String mBiography) {
        this.mBiography = mBiography;
    }

    public int getmBirth() {
        return mBirth;
    }

    public void setmBirth(String mBirth) {
        this.mBirth = mBirth;
    }

    public float getmScore() {
        return mScore;
    }

    public void setmScore(float mScore) {
        this.mScore = mScore;
    }

    public boolean getmId() {
        return mId;
    }

    public boolean ismId() {
        return mId;
    }

    public void setmId(boolean mId) {
        this.mId = mId;
    }
}
