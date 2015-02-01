package me.smr.weatherforecast;


/**
 * This class will provide the common utilities and constants.
 * */
public final class CommonUtils {

	private CommonUtils() {
		// private constructor so the objects can't be created
	}

	/**
	 * The APPID (API key) provided by {@code openweathermap.org}
	 * */
	public static final String APPID = "e1ec7985dbbd4af7f73ae7d3bb99453a";

	/**
	 * This URL will be used to search any city in the
	 * {@code openweathermap.org} database.
	 * */
	public static final String SEARCH_CITY = "http://api.openweathermap.org/data/2.5/find?q=%s&type=like";

}
