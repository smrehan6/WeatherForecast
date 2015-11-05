package me.smr.weatherforecast.utils;

import static me.smr.weatherforecast.utils.CommonUtils.showDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
 */
public class CallService extends AsyncTask<String, Void, String> {

    /**
     * Timeout limit constant in milliseconds for each server request.
     */
    private static final int TIMEOUT = 10 * 1000;
    private Context ctx;
    private RequestInterface callback;
    private ProgressDialog dialog;
    private final RequestType type;
    private final boolean showLoader;

    public CallService(Context ctx, RequestInterface reqInterface,
                       RequestType type, boolean showLoader) {
        this.ctx = ctx;
        this.callback = reqInterface;
        this.type = type;
        this.showLoader = showLoader;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showLoader) {
            dialog = new ProgressDialog(ctx);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        final String url = removeSpacesFromUrl(params[0]);
        HttpURLConnection conn = null;
        InputStream is = null;
        Log.v("URL", url);
        try {
            URL mUrl = new URL(url);
            conn = (HttpURLConnection) mUrl.openConnection();
            conn.setReadTimeout(TIMEOUT);
            conn.setConnectTimeout(TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                return sb.toString();
            } else {
                return ctx.getString(R.string.invalid_resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ctx.getString(R.string.connection_error);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error Occured: " + e.getMessage();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.v("result", result);
        if (showLoader)
            dialog.dismiss();
        try {
            JSONObject response = new JSONObject(result);
            callback.onResult(type, response);
        } catch (JSONException e) {
            e.printStackTrace();
            showDialog(ctx, result);
            callback.onError(type);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(type);
            showDialog(ctx, "Error Occured: " + e.getMessage());
        }
    }

    /**
     * Replaces all {@code SPACEs} with {@code %20}.
     *
     * @param url the {@code String} which might contain the spaces.
     */
    private static String removeSpacesFromUrl(String url) {
        return url.replaceAll(" ", "%20");
    }

}
