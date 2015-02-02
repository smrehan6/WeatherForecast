package me.smr.weatherforecast.utils;

import static me.smr.weatherforecast.utils.CommonUtils.showDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import me.smr.weatherforecast.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * This is a custom {@code AsyncTask} to call the web-APIs
 * */
public class CallService extends AsyncTask<String, Void, String> {

	/**
	 * Timeout limit constant in milliseconds for each server request.
	 * */
	private static final int TIMEOUT = 10 * 1000;
	private Context ctx;
	private RequestInterface callback;
	private ProgressDialog dialog;
	private final RequestType type;

	public CallService(Context ctx, RequestInterface reqInterface,
			RequestType type) {
		this.ctx = ctx;
		this.callback = reqInterface;
		this.type = type;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(ctx);
		dialog.setMessage("Loading...");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		final String url = removeSpacesFromUrl(params[0]);
		Log.v("URL", url);
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		try {
			HttpGet req = new HttpGet(url);
			req.setParams(httpParams);
			HttpResponse httpResponse = httpClient.execute(req);
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream is = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				Log.v("Response", sb.toString());
				return sb.toString();
			} else
				return ctx.getString(R.string.invalid_resp);
		} catch (IOException e) {
			e.printStackTrace();
			return ctx.getString(R.string.connection_error);
		} catch (Exception e) {
			e.printStackTrace();
			return "Error Occured: " + e.getMessage();
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		dialog.dismiss();
		try {
			JSONObject response = new JSONObject(result);
			callback.onResult(type, response);
		} catch (JSONException e) {
			showDialog(ctx, result);
		} catch (Exception e) {
			e.printStackTrace();
			showDialog(ctx, "Error Occured: " + e.getMessage());
		}
	}

	/**
	 * Replaces all {@code SPACEs} with {@code %20}.
	 * 
	 * @param url
	 *            the {@code String} which might contain the spaces.
	 * */
	private static String removeSpacesFromUrl(String url) {
		return url.replaceAll(" ", "%20");
	}

}
