package me.smr.weatherforecast.utils;

import static me.smr.weatherforecast.utils.CommonUtils.showDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.smr.weatherforecast.api.WeatherAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * This is a custom {@code AsyncTask} to call the web-APIs
 */
public class CallService extends AsyncTask<String, Void, String> {

    /**
     * Timeout limit constant in milliseconds for each server request.
     */
    private static final WeatherAPI api;
    private Context ctx;
    private final RequestInterface callback;
    private ProgressDialog dialog;
    private final RequestType type;
    private final boolean showLoader;

    static {
        api = new Retrofit.Builder().baseUrl(WeatherAPI.BASE_URL).build().create(WeatherAPI.class);
    }

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
        Call<ResponseBody> call = null;
        switch (type) {
            case SEARCH_CITY:
                call = api.find(params[0]);
                break;
            case GET_CURRENT:
                call = api.getCurrentWeather(params[0]);
                break;
            case GET_FORECAST:
                call = api.getForecast(params[0]);
                break;
            case GET_CITY_BY_LOCATION:
                call = api.getWeatherByLoc(params[0], params[1]);
        }
        try {
            return call.execute().body().string();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
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

}
