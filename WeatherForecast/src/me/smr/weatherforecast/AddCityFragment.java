package me.smr.weatherforecast;

import static me.smr.weatherforecast.utils.CommonUtils.showToast;

import java.util.ArrayList;

import me.smr.weatherforecast.utils.CallService;
import me.smr.weatherforecast.utils.CommonUtils;
import me.smr.weatherforecast.utils.Parser;
import me.smr.weatherforecast.utils.RequestInterface;
import me.smr.weatherforecast.utils.RequestType;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

/**
 * This fragment will be used to add/remove cities by the user.
 * */
public class AddCityFragment extends Fragment implements OnKeyListener,
		RequestInterface, OnItemClickListener {

	private AutoCompleteTextView etCity;
	private ListView lvCities;
	private ArrayList<City> cityList;
	private ArrayList<City> addedCities = new ArrayList<City>();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main, container, false);
		etCity = (AutoCompleteTextView) v.findViewById(R.id.etCity);
		lvCities = (ListView) v.findViewById(R.id.lvCities);
		getActivity().setTitle(R.string.add_city);
		// TODO further implementation
		etCity.setOnKeyListener(this);
		etCity.setOnItemClickListener(this);
		return v;
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
		new CallService(getActivity(), this, RequestType.REQ_SEARCH_CITY)
				.execute(String.format(CommonUtils.SEARCH_CITY, cityName));
	}

	@Override
	public void onResult(RequestType type, JSONObject result) {
		switch (type) {
		case REQ_SEARCH_CITY:
			try {
				cityList = Parser.parseCities(result);
				etCity.setAdapter(new CitySearchAdapter(getActivity(), cityList));
				etCity.showDropDown();
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.invalid_resp);
			}
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO add to database and show in listview
		addedCities.add(cityList.get(position));
		// TODO clearText
	}
}
