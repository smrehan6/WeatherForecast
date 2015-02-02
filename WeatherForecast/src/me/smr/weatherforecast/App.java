package me.smr.weatherforecast;

import android.app.Application;
import android.content.Context;

public class App extends Application {

	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		// TODO implementation pending
		context = this.getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}

}
