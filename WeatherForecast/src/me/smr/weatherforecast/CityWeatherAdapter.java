package me.smr.weatherforecast;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CityWeatherAdapter extends
		RecyclerView.Adapter<CityWeatherAdapter.ViewHolder> {

	private ArrayList<CityData> data;

	public CityWeatherAdapter(ArrayList<CityData> data) {
		super();
		this.data = data;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		protected ImageView img;
		protected TextView name;
		protected TextView temp;
		protected TextView weather;

		public ViewHolder(View v) {
			super(v);
			img = (ImageView) v.findViewById(R.id.imgWeather);
			name = (TextView) v.findViewById(R.id.cityName);
			temp = (TextView) v.findViewById(R.id.txtTemp);
			weather = (TextView) v.findViewById(R.id.txtWeather);
		}
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, int position) {
		CityData city = data.get(position);
		vh.name.setText(city.getCityName());
		vh.temp.setText(city.getTemp());
		vh.weather.setText(city.getWeather());
		vh.img.setImageResource(city.getImage());
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.city_item, parent, false);
		ViewHolder holder = new ViewHolder(v);
		return holder;
	}
}
