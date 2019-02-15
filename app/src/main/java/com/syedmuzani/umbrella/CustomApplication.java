package com.syedmuzani.umbrella;

import android.app.Application;
import android.content.res.Configuration;

import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Muz on 2017-05-29.
 * Replaces existing Application class
 */
public class CustomApplication extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /* Facebook */
        AppEventsLogger.activateApp(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
