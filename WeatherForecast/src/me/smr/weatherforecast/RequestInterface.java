package me.smr.weatherforecast;

import org.json.JSONObject;

public interface RequestInterface {
	public abstract void onResult(JSONObject result);
}
