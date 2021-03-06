package me.smr.weatherforecast.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.smr.weatherforecast.models.City;
import me.smr.weatherforecast.models.CityData;
import me.smr.weatherforecast.models.Forecast;


/**
 * This utility class will be used for {@code JSON parsing} throughout the App.
 */
public final class Parser {

    private Parser() {
    }

    public static City parseCity(JSONObject ob) throws JSONException {
        int id = ob.getInt("id");
        String name = ob.getString("name");
        String countryCode = ob.getJSONObject("sys").getString("country");
        double latitude = ob.getJSONObject("coord").getDouble("lat");
        double longtitude = ob.getJSONObject("coord").getDouble("lon");
        return new City(id, name, countryCode, latitude, longtitude);
    }

    public static ArrayList<City> parseCities(JSONObject result)
            throws JSONException {
        ArrayList<City> list = new ArrayList<City>();
        JSONArray arr = result.getJSONArray("list");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject ob = arr.getJSONObject(i);
            int id = ob.getInt("id");
            String name = ob.getString("name");
            String countryCode = ob.getJSONObject("sys").getString("country");
            double latitude = ob.getJSONObject("coord").getDouble("lat");
            double longtitude = ob.getJSONObject("coord").getDouble("lon");
            City city = new City(id, name, countryCode, latitude, longtitude);
            list.add(city);
        }
        return list;
    }

    public static ArrayList<CityData> parseWeatherData(String data)
            throws JSONException {
        ArrayList<CityData> list = new ArrayList<CityData>();
        JSONArray arr = new JSONObject(data).getJSONArray("list");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject ob = arr.getJSONObject(i);
            CityData cityData = new CityData();
            cityData.setId(ob.getString("id"));
            cityData.setCityName(ob.getString("name") + ", "
                    + ob.getJSONObject("sys").getString("country"));
            JSONObject weather = ob.getJSONArray("weather").getJSONObject(0);
            cityData.setImage(weather.getString("icon"));
            cityData.setWeather(weather.getString("description"));
            cityData.setTemp(ob.getJSONObject("main").getString("temp"));
            list.add(cityData);
        }
        return list;
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
