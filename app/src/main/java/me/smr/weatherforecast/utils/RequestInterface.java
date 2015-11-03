package me.smr.weatherforecast.utils;

import org.json.JSONObject;

public interface RequestInterface {
	public abstract void onResult(RequestType type, JSONObject result);

	public abstract void onError(RequestType type);
}
