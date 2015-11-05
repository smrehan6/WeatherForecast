package me.smr.weatherforecast.fragments;

import java.util.ArrayList;

import me.smr.weatherforecast.R;
import me.smr.weatherforecast.adapters.CityWeatherAdapter;
import me.smr.weatherforecast.models.CityData;
import me.smr.weatherforecast.utils.CallService;
import me.smr.weatherforecast.utils.CommonUtils;
import me.smr.weatherforecast.utils.DBHelper;
import me.smr.weatherforecast.utils.Parser;
import me.smr.weatherforecast.utils.RequestInterface;
import me.smr.weatherforecast.utils.RequestType;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class WeatherFragment extends Fragment implements RequestInterface,
		OnRefreshListener {

	// The cities' weather data will be stored as a JSON using this key.
	private static final String KEY_WEATHER_DATA = "weather_data";
	// This will be used to store timestamp
	private static final String KEY_TIMESTAMP = "timestamp";
	private static final long ONE_HOUR = 60L * 60L * 1000L;
	private ArrayList<CityData> dataList = new ArrayList<CityData>();
	private String jsonString;
	private RecyclerView recyclerView;
	private CityWeatherAdapter adapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private SwipeRefreshLayout swipeLayout;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO autorefresh when cities added/deleted
		View v = inflater.inflate(R.layout.layout_weather_frag, container,
				false);
		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeLayout);
		recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
		mLayoutManager = new LinearLayoutManager(getActivity());

		recyclerView.setHasFixedSize(false);
		recyclerView.setLayoutManager(mLayoutManager);

		jsonString = getActivity().getSharedPreferences(KEY_WEATHER_DATA, 0)
				.getString(KEY_WEATHER_DATA, null);
		if (jsonString != null) {
			// we have previously store data so set adapter
			long timestamp = getActivity().getSharedPreferences(
					KEY_WEATHER_DATA, 0).getLong(KEY_TIMESTAMP, 0);
			if (System.currentTimeMillis() - timestamp >= ONE_HOUR) {
				// stored data is old so refresh
				CommonUtils.showToast("Refreshing...");
				getCitiesWeather(true);
			} else {
				try {
					dataList = Parser.parseWeatherData(jsonString);
					Log.v("list", dataList.toString());
					adapter = new CityWeatherAdapter(getActivity()
							.getSupportFragmentManager(), dataList);
					recyclerView.setAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else {
			// call webservice to get data
			Log.d(KEY_WEATHER_DATA, "no data found. fetching...");
			getCitiesWeather(true);
		}
		setHasOptionsMenu(true);
		swipeLayout.setColorSchemeResources(R.color.red, R.color.purple,
				R.color.orange, R.color.green);
		swipeLayout.setOnRefreshListener(this);
		getActivity().setTitle(R.string.app_name);

		// this is a workaround for enabling the swipeLayout only if the top
		// child is visible; otherwise it will give problems scrolling up and
		// down.
		recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				int topRowVerticalPosition = (recyclerView == null || recyclerView
						.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0)
						.getTop();
				swipeLayout.setEnabled(topRowVerticalPosition >= 0);
			}
		});

		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			getActivity()
					.getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.container, new AddCityFragment(),
							AddCityFragment.class.getSimpleName()).commit();
			return true;
		case R.id.action_help:
			CommonUtils.showDialog(getActivity(), R.string.help_msg);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResult(RequestType type, JSONObject result) {
		swipeLayout.setRefreshing(false);
		dataList.clear();
		// store the result
		getActivity().getSharedPreferences(KEY_WEATHER_DATA, 0).edit()
				.putString(KEY_WEATHER_DATA, result.toString())
				.putLong(KEY_TIMESTAMP, System.currentTimeMillis()).commit();
		try {
			dataList = Parser.parseWeatherData(result.toString());
			adapter = new CityWeatherAdapter(getActivity()
					.getSupportFragmentManager(), dataList);
			recyclerView.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
			CommonUtils.showToast("Error occured!");
			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onError(RequestType type) {
		Log.v("adsf", "error");
		swipeLayout.setRefreshing(false);
	}

	/**
	 * calls the web-service to get current weather if all the stored cities.
	 * 
	 * @param flag
	 *            a {@code boolean} to determine if the
	 *            {@code SwipeRefreshLayout} should be refreshed or not.
	 * */
	private void getCitiesWeather(boolean flag) {
		if (flag)
			swipeLayout.setRefreshing(true);
		final String URL = String.format(CommonUtils.GET_CURRENT_WEATHER,
				DBHelper.getInstance().getAllCityIDs());
		new CallService(getActivity(), this, RequestType.GET_CURRENT, false)
				.execute(URL);
	}

	@Override
	public void onRefresh() {
		getCitiesWeather(false);
	}

}
