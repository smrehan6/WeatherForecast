package me.smr.weatherforecast;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * @return gloval Context for Application
     */
    public static App getInstance() {
        return instance;
    }

}
