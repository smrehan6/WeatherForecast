package me.smr.weatherforecast.fragments;

import java.text.DateFormat;
import java.util.ArrayList;

import me.smr.weatherforecast.R;
import me.smr.weatherforecast.models.CityData;
import me.smr.weatherforecast.models.Forecast;
import me.smr.weatherforecast.utils.CallService;
import me.smr.weatherforecast.utils.CommonUtils;
import me.smr.weatherforecast.utils.Parser;
import me.smr.weatherforecast.utils.RequestInterface;
import me.smr.weatherforecast.utils.RequestType;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ForecastFragment extends Fragment implements RequestInterface {

	// The city to be displayed
	private CityData city;
	private ImageView img;
	private TextView txtCityName, txtCurrent;
	ArrayList<Forecast> list;
	ListView listView;

	public ForecastFragment(CityData city) {
		this.city = city;
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.forecast_frag, container, false);
		listView = (ListView) v.findViewById(R.id.lvForecasts);

		// header
		View header = inflater.inflate(R.layout.forecast_header, null, false);
		img = (ImageView) header.findViewById(R.id.img);
		txtCityName = (TextView) header.findViewById(R.id.txtCity);
		txtCurrent = (TextView) header.findViewById(R.id.txtCurrent);

		txtCityName.setText(city.getCityName());
		txtCurrent.setText(city.getTemp() + " " + city.getWeather());
		img.setImageResource(city.getImage());
		listView.addHeaderView(header);

		setHasOptionsMenu(true);
		getActivity().setTitle(city.getCityName());
		CommonUtils.showToast("Loading...");
		new CallService(getActivity(), this, RequestType.GET_FORECAST, false)
				.execute(CommonUtils.GET_FORECAST + city.getId());
		return v;
	}

	@Override
	public void onResult(RequestType type, JSONObject result) {
		try {
			list = Parser.parseForecasts(result.getJSONArray("list"));
			listView.setAdapter(new ForecastAdapter());
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtils.showToast("Error occured!");
		}

	}

	@Override
	public void onError(RequestType type) {
	}

	private class ForecastAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Forecast getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			TextView date, temp, weather;
			ImageView img;
			if (v == null) {
				v = LayoutInflater.from(getActivity()).inflate(
						R.layout.forecast_list_item, parent, false);
			}
			Forecast forecast = list.get(position);
			date = (TextView) v.findViewById(R.id.txtDate);
			temp = (TextView) v.findViewById(R.id.txtTemp);
			weather = (TextView) v.findViewById(R.id.txtWeather);
			img = (ImageView) v.findViewById(R.id.img);
			date.setText(DateFormat.getDateInstance().format(
					forecast.date * 1000));
			temp.setText(forecast.temp);
			weather.setText(forecast.weather);
			img.setImageResource(getImage(forecast.img));
			return v;
		}

	}

	public int getImage(String image) {
		switch (image) {
		case "01d":
			return R.drawable.d1;
		case "01n":
			return R.drawable.n1;
		case "02d":
			return R.drawable.d2;
		case "02n":
			return R.drawable.n2;
		case "03d":
		case "03n":
			return R.drawable.d3;
		case "04d":
		case "04n":
			return R.drawable.d4;
		case "09d":
		case "09n":
			return R.drawable.d9;
		case "10d":
			return R.drawable.d10;
		case "10n":
			return R.drawable.n10;
		case "11d":
		case "11n":
			return R.drawable.d11;
		case "13d":
		case "13n":
			return R.drawable.d13;
		case "50d":
		case "50n":
			return R.drawable.d50;
		default:
			return R.drawable.logo;
		}
	}
}
