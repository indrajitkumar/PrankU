package com.amcoder.pranku;

import android.support.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;

/**
 * Created by philips on 6/7/17.
 */

public class PrankUApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
