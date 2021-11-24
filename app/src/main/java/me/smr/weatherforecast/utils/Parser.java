package me.smr.weatherforecast.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.smr.weatherforecast.models.Forecast;


/**
 * This utility class will be used for {@code JSON parsing} throughout the App.
 */
public final class Parser {

    private Parser() {
    }

    public static ArrayList<Forecast> parseForecasts(JSONArray arr)
            throws JSONException {
        ArrayList<Forecast> list = new ArrayList<Forecast>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject ob = arr.getJSONObject(i);
            Forecast forecast = new Forecast();
            forecast.date = ob.getLong("dt");
            JSONObject temp = ob.getJSONObject("temp");
            forecast.temp = "Min: " + temp.getString("min") + "\nMax: "
                    + temp.getString("max");
            JSONObject weather = ob.getJSONArray("weather").getJSONObject(0);
            forecast.weather = weather.getString("description");
            forecast.img = weather.getString("icon");
            list.add(forecast);
        }
        return list;
    }
}
