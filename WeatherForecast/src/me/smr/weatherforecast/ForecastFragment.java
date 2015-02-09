package me.smr.weatherforecast;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ForecastFragment extends Fragment implements RequestInterface {

	// The city to be displayed
	private CityData city;
	private ImageView img;
	private TextView txtCityName, txtCurrent;
	ArrayList<Forecast> list;

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
		getActivity().setTitle(city.getCityName());

		new CallService(getActivity(), this, RequestType.GET_FORECAST, false)
				.execute(CommonUtils.GET_FORECAST + city.getId());
		return v;
	}

	@Override
	public void onResult(RequestType type, JSONObject result) {
		try {
			list = Parser.parseForecasts(result.getJSONArray("list"));
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtils.showToast("Error occured!");
		}

	}

	@Override
	public void onError(RequestType type) {
	}
}
