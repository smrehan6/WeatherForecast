package me.smr.weatherforecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

/**
 * This fragment will be used to add/remove cities by the user.
 * */
public class AddCityFragment extends Fragment {

	AutoCompleteTextView etCity;
	ListView lvCities;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main, container, false);
		etCity = (AutoCompleteTextView) v.findViewById(R.id.etCity);
		lvCities = (ListView) v.findViewById(R.id.lvCities);
		// TODO further implementation
		Log.v("test", String.format(CommonUtils.SEARCH_CITY, "test"));
		return v;
	}
}
