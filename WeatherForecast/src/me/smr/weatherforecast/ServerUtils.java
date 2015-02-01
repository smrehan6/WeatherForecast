package me.smr.weatherforecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * It would be better to keep all server related stuff here. i.e. to call the
 * web APIs.
 * */
public final class ServerUtils {

	/**
	 * Timeout limit in milliseconds for each server request.
	 * */
	private static final int TIMEOUT = 10 * 1000;

	/**
	 * Replaces all {@code SPACEs} with {@code %20}.
	 * 
	 * @param url
	 *            the {@code String} which might contain the spaces.
	 * */
	public static String removeSpacesFromUrl(String url) {
		return url.replaceAll(" ", "%20");
	}

	public static void callService(final Context ctx,
			final RequestInterface reqInterface, final String URL) {
		new AsyncTask<Void, Void, JSONObject>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				// TODO show loader here
			}

			@Override
			protected JSONObject doInBackground(Void... arg0) {
				return getJSON(URL);
			}

			@Override
			protected void onPostExecute(JSONObject result) {
				super.onPostExecute(result);
				// TODO dissmiss loader
				reqInterface.onResult(result);
			}

		};
	}

	private static JSONObject getJSON(String url) {
		// TODO reduce line of code here
		StringBuilder builder = new StringBuilder();
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParameters);
		HttpGet httpGet = null;
		try {
			url = removeSpacesFromUrl(url);
			Log.v("URL", url);
			httpGet = new HttpGet(url);
			httpGet.setParams(httpParameters);
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				JSONObject jObj = null;
				jObj = new JSONObject(builder.toString());
				return jObj;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}
}
