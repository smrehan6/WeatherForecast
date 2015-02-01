package me.smr.weatherforecast;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

public class CallService extends AsyncTask<String, Void, JSONObject> {

	private Context ctx;
	private RequestInterface reqInterface;

	public CallService(Context ctx, RequestInterface reqInterface) {
		this.ctx = ctx;
		this.reqInterface = reqInterface;
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		// TODO might need this
		return null;
	}

}
