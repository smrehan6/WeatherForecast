package me.smr.weatherforecast;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This Adapter will be used to show dropdown in the city search
 * {@code TextView}
 * */
public class CitySearchAdapter extends ArrayAdapter<City> {

	private Context ctx;
	private ArrayList<City> data;

	public CitySearchAdapter(Context ctx, ArrayList<City> list) {
		super(ctx, android.R.layout.simple_dropdown_item_1line, list);
		this.ctx = ctx;
		this.data = list;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public City getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		if (v == null) {
			v = LayoutInflater.from(ctx).inflate(
					android.R.layout.simple_dropdown_item_1line, parent, false);
		}
		TextView txt = (TextView) v.findViewById(android.R.id.text1);
		txt.setText(data.get(position).toString());
		return v;
	}
}
