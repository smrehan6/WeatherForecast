package me.smr.weatherforecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ForecastFragment extends Fragment {

	// The city to be displayed
	private CityData city;
	private ImageView img;
	private TextView txtCityName, txtCurrent;

	public ForecastFragment(CityData city) {
		this.city = city;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.forecast_frag, container, false);
		img = (ImageView) v.findViewById(R.id.img);
		txtCityName = (TextView) v.findViewById(R.id.txtCity);
		txtCurrent = (TextView) v.findViewById(R.id.txtCurrent);
		img.setImageResource(city.getImage());
		txtCityName.setText(city.getCityName());
		txtCurrent.setText(city.getTemp() + " " + city.getWeather());
		setHasOptionsMenu(true);
		return v;
	}
}
