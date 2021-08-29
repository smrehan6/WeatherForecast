package me.smr.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.smr.weatherforecast.R;
import me.smr.weatherforecast.models.CityData;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.ViewHolder> {

    private ArrayList<CityData> data;
    private OnCityClickedListener listener;

    public CityWeatherAdapter(ArrayList<CityData> data, OnCityClickedListener listener) {
        super();
        this.data = data;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView temp;
        private TextView weather;
        private CardView card;

        public ViewHolder(View v) {
            super(v);
            card = (CardView) v.findViewById(R.id.card_view);
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
        final CityData city = data.get(position);
        vh.name.setText(city.getCityName());
        vh.temp.setText(city.getTemp());
        vh.weather.setText(city.getWeather());
        vh.img.setImageResource(city.getImage());

        vh.card.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (listener != null) listener.onCityClicked(city);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
        return new ViewHolder(v);
    }
}
