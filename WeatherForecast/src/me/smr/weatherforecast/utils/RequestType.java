package me.smr.weatherforecast.utils;

/**
 * These types define which types of web API requests can be made
 * */
public enum RequestType {
	/**
	 * search city request
	 */
	SEARCH_CITY,
	/**
	 * get current weather data
	 * */
	CURRENT,
	/**
	 * get forecast
	 * */
	GET_FORECAST
}
