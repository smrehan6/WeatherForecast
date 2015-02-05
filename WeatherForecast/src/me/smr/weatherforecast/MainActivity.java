package me.smr.weatherforecast;

import me.smr.weatherforecast.utils.DBHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			if (DBHelper.getInstance().getCityCount() == 0) {
				// called only if the cities array is empty
				getSupportFragmentManager()
						.beginTransaction()
						.add(R.id.container, new AddCityFragment(),
								AddCityFragment.class.getSimpleName()).commit();
			} else {
				getSupportFragmentManager().beginTransaction()
						.add(R.id.container, new WeatherFragment()).commit();
			}
		}
	}

	@Override
	public void onBackPressed() {
		Fragment frag = getSupportFragmentManager().findFragmentByTag(
				AddCityFragment.class.getSimpleName());
		if (frag != null && frag.isVisible()
				&& DBHelper.getInstance().getCityCount() != 0) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new WeatherFragment()).commit();
		} else
			super.onBackPressed();
	}
}
