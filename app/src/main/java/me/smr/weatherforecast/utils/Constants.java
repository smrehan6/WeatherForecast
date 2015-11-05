package me.smr.weatherforecast.utils;

public final class Constants {

    private Constants() {
    }

    /**
     * The API key provided by {@code openweathermap.org}
     */
    public static final String APPID = "e1ec7985dbbd4af7f73ae7d3bb99453a";

    private static final String BASE = "http://api.openweathermap.org/data/2.5/";

    /**
     * This URL will be used to search any city in the
     * {@code openweathermap.org} database.
     */
    public static final String SEARCH_CITY = BASE + "find?q=%s&type=like&APPID=" + APPID;

    /**
     * This will be used to get current weather details of any city
     */
    public static final String GET_CURRENT_WEATHER = BASE + "group?id=%s&units=metric&APPID=" + APPID;

    /**
     * URL for getting 14 day forecast of provided city
     */
    public static final String GET_FORECAST = BASE + "forecast/daily?cnt=16&units=metric&APPID=" + APPID + "&id=";

    /**
     * URL for getting the current city details of the lat long
     */
    public static final String GET_CITY = BASE + "weather?lat=%1$f&lon=%2$f&APPID=" + APPID;


}
