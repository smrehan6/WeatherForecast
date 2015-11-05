package me.smr.weatherforecast.fragments;

import static me.smr.weatherforecast.utils.CommonUtils.showToast;

import java.util.ArrayList;

import me.smr.weatherforecast.R;
import me.smr.weatherforecast.adapters.CitySearchAdapter;
import me.smr.weatherforecast.models.City;
import me.smr.weatherforecast.utils.CallService;
import me.smr.weatherforecast.utils.CommonUtils;
import me.smr.weatherforecast.utils.Constants;
import me.smr.weatherforecast.utils.DBHelper;
import me.smr.weatherforecast.utils.Parser;
import me.smr.weatherforecast.utils.RequestInterface;
import me.smr.weatherforecast.utils.RequestType;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This fragment will be used to add/remove cities by the user.
 * */
public class AddCityFragment extends Fragment implements OnKeyListener,
		RequestInterface, OnItemClickListener {

	/**
	 * list of searched city results.
	 * */
	private ArrayList<City> fetchedCities;
	/**
	 * list if saved cities in the database.
	 * */
	private ArrayList<City> savedCities = new ArrayList<City>();
	private AutoCompleteTextView etCity;
	private ListView listView;
	private SavedCityAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_add_city, container, false);
		etCity = (AutoCompleteTextView) v.findViewById(R.id.etCity);
		listView = (ListView) v.findViewById(R.id.lvCities);
		getActivity().setTitle(R.string.add_city);

		etCity.setOnKeyListener(this);
		etCity.setOnItemClickListener(this);

		savedCities = DBHelper.getInstance().getAllCities();
		Log.v("Cities", savedCities.toString());
		adapter = new SavedCityAdapter();
		listView.setAdapter(adapter);
		setHasOptionsMenu(true);
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.add, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_done:
			if (DBHelper.getInstance().getCityCount() == 0) {
				CommonUtils.showToast("Please add at least one city");
			} else {
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.container, new WeatherFragment())
						.commit();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// v.onKeyDown(keyCode, event);
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER)) {
			if (etCity.getText().toString().trim().length() == 0) {
				showToast(R.string.hint_name);
				return false;
			}
			searchCity(etCity.getText().toString().trim());
			return true;
		}
		return v.onKeyDown(keyCode, event);
	}

	/**
	 * makes a web API request to fetch the list of cities matching the provided
	 * city name.
	 * */
	private void searchCity(String cityName) {
		new CallService(getActivity(), this, RequestType.SEARCH_CITY, true)
				.execute(String.format(Constants.SEARCH_CITY, cityName));
	}

	@Override
	public void onResult(RequestType type, JSONObject result) {
		switch (type) {
		case SEARCH_CITY:
			try {
				fetchedCities = Parser.parseCities(result);
				if (fetchedCities.size() != 0) {
					etCity.setAdapter(new CitySearchAdapter(getActivity(),
							fetchedCities));
					etCity.showDropDown();
				} else {
					showToast("No data found for '"
							+ etCity.getText().toString() + "'");
				}

			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.invalid_resp);
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onError(RequestType type) {
		// nothing
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		City city = fetchedCities.get(position);
		Log.v("City", city.toString());
		if (DBHelper.getInstance().addOrUpdateCity(city) != -1) {
			// TODO add animations in the listview
			etCity.setText("");
			showToast("City Added!");
			savedCities.add(city);
			adapter.notifyDataSetChanged();
			// TODO hide soft keyboard
		} else {
			showToast("Error! City not Added.");
		}
	}

	/**
	 * Custom adapter for City {@code ListView}
	 * */
	private class SavedCityAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return savedCities.size();
		}

		@Override
		public City getItem(int position) {
			return savedCities.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			if (v == null) {
				// TODO set custom layout with delete. maybe reorder
				v = LayoutInflater.from(getActivity()).inflate(
						R.layout.city_dropdown_item, parent, false);
			}
			TextView tv = (TextView) v.findViewById(R.id.txtCityName);
			tv.setText(savedCities.get(position).toString());
			return v;
		}

	}

}
