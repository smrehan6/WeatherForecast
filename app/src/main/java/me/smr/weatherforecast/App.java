package me.smr.weatherforecast;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }

    /**
     * @return gloval Context for Application
     */
    public static Context getContext() {
        return context;
    }

}
