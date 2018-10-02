package com.namkit.namki.novafolio.JSONWakeup;

/**
 * Created by namki on 2018-03-22.
 */

public class WakeupItem_JSON {
    private String mImageUrl;
    private String mCreator;
    private int mTimes;

    public WakeupItem_JSON(String mImageUrl, String mCreator, int mTimes) {
        this.mImageUrl = mImageUrl;
        this.mCreator = mCreator;
        this.mTimes = mTimes;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmCreator() {
        return mCreator;
    }

    public void setmCreator(String mCreator) {
        this.mCreator = mCreator;
    }

    public int getmTimes() {
        return mTimes;
    }

    public void setmTimes(int mTimes) {
        this.mTimes = mTimes;
    }
}
