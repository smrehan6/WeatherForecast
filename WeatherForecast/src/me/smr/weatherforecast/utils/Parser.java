package me.smr.weatherforecast.utils;

import java.util.ArrayList;

import me.smr.weatherforecast.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This utility class will be used for {@code JSON parsing} throughout the App.
 * */
public final class Parser {

	private Parser() {
	}

	public static ArrayList<City> parseCities(JSONObject result) throws JSONException {
		ArrayList<City> list = new ArrayList<City>();
		JSONArray arr = result.getJSONArray("list");
		for (int i = 0; i < arr.length(); i++) {
			JSONObject ob = arr.getJSONObject(i);
			long id = ob.getLong("id");
			String name = ob.getString("name");
			String countryCode = ob.getJSONObject("sys").getString("country");
			double latitude = ob.getJSONObject("coord").getDouble("lat");
			double longtitude = ob.getJSONObject("coord").getDouble("lon");
			City city = new City(id, name, countryCode, latitude, longtitude);
			list.add(city);
		}
		return list;
	}
}