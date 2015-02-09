package me.smr.weatherforecast;

import org.json.JSONObject;

import me.smr.weatherforecast.utils.CallService;
import me.smr.weatherforecast.utils.CommonUtils;
import me.smr.weatherforecast.utils.DBHelper;
import me.smr.weatherforecast.utils.Parser;
import me.smr.weatherforecast.utils.RequestInterface;
import me.smr.weatherforecast.utils.RequestType;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends ActionBarActivity implements
		ConnectionCallbacks, OnConnectionFailedListener, RequestInterface {

	private static final String KEY_PREF = "firstrun";
	private GoogleApiClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (getSharedPreferences(KEY_PREF, 0).getBoolean(KEY_PREF, true)
				&& servicesConnected()) {
			// get current city lat lon
			buildGoogleApiClient();
			mClient.connect();
			CommonUtils.showToast("Retrieving your current location...");
			// this will be done one time only
			getSharedPreferences(KEY_PREF, 0).edit()
					.putBoolean(KEY_PREF, false).commit();
		} else if (savedInstanceState == null) {
			if (DBHelper.getInstance().getCityCount() == 0) {
				// called only if the cities array is empty too add a city
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
		// check if add frag is visible
		if (frag != null && frag.isVisible()
				&& DBHelper.getInstance().getCityCount() != 0) {
			// show WeatherFrag if used added a city
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new WeatherFragment()).commit();
		} else
			// do the usual
			super.onBackPressed();
	}

	protected synchronized void buildGoogleApiClient() {
		mClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.v("GPS", "connected");
		Location lastLocation = LocationServices.FusedLocationApi
				.getLastLocation(mClient);
		if (lastLocation != null) {
			getCity(lastLocation);
		} else {
			showLocationErrorDialog();
		}

	}

	@Override
	public void onConnectionSuspended(int arg0) {
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		showLocationErrorDialog();
	}

	private boolean servicesConnected() {

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (ConnectionResult.SUCCESS == resultCode) {
			return true;
		} else {
			showLocationErrorDialog();
			return false;
		}
	}

	private void showLocationErrorDialog() {
		CommonUtils
				.showDialog(this,
						"Unable to get you current location.\nPlease add a city manually.");
	}

	@SuppressLint("DefaultLocale")
	private void getCity(Location location) {
		final String URL = String.format(CommonUtils.GET_CITY,
				location.getLatitude(), location.getLongitude());
		new CallService(this, this, RequestType.GET_CITY_BY_LOCATION, true)
				.execute(URL);
	}

	@Override
	public void onResult(RequestType type, JSONObject result) {
		try {
			DBHelper.getInstance().addOrUpdateCity(Parser.parseCity(result));
			getSupportFragmentManager().popBackStackImmediate();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new WeatherFragment()).commit();
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtils.showToast("Error occured!");
		}
	}

	@Override
	public void onError(RequestType type) {
	}

}
