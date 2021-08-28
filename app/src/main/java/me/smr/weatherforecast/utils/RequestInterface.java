package me.smr.weatherforecast.utils;

import org.json.JSONObject;

public interface RequestInterface {
	void onResult(RequestType type, JSONObject result);
	void onError(RequestType type);
}
