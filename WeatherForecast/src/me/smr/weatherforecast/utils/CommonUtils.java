package me.smr.weatherforecast.utils;

import me.smr.weatherforecast.App;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

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

	/**
	 * shows {@code AlertDialog}. If the {@code Activity} is not visible then
	 * show a {@code Toast}.
	 * 
	 * @param ctx
	 *            The context of the activity.
	 * @param msg
	 *            The message to be shown.
	 * */
	public static void showDialog(Context ctx, String msg) {
		AlertDialog.Builder alert = new Builder(ctx);
		alert.setMessage(msg);
		// TODO title changes
		alert.setTitle("Alert");
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		try {
			alert.show();
		} catch (Exception e) {
			showToast(msg);
		}
	}

	/**
	 * shows {@code AlertDialog}. If the {@code Activity} is not visible then
	 * show a {@code Toast}.
	 * 
	 * @param ctx
	 *            The context of the activity.
	 * @param resid
	 *            The resource id of the string in the {@code strings.xml}.
	 * */
	public static void showDialog(Context ctx, int resid) {
		showDialog(ctx, ctx.getString(resid));
	}

	/**
	 * shows custom {@code Toast}.
	 * 
	 * @param msg
	 *            The message String to be displayed.
	 * */
	public static void showToast(String msg) {
		// TODO custom Toast implementation.
		Toast.makeText(App.getContext(), msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * shows custom {@code Toast}.
	 * 
	 * @param resid
	 *            The resource id of the string in the {@code strings.xml}.
	 * */
	public static void showToast(int resId) {
		showToast(App.getContext().getString(resId));
	}
}
