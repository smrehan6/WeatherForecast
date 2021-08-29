package me.smr.weatherforecast.utils;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.smr.weatherforecast.App;
import me.smr.weatherforecast.R;

/**
 * This class will provide the common utilities and constants.
 * */
public final class CommonUtils {

	private CommonUtils() {
		// private constructor so the objects can't be created
	}

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
		Builder alert = new Builder(ctx);
		alert.setMessage(msg);
		alert.setTitle(R.string.app_name);
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
		View v = View.inflate(App.getInstance(), R.layout.custom_toast, null);
		TextView tv = (TextView) v.findViewById(R.id.txtMsg);
		tv.setText(msg);
		Toast toast = new Toast(App.getInstance());
		toast.setView(v);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * shows custom {@code Toast}.
	 * 
	 * @param resId
	 *            The resource id of the string in the {@code strings.xml}.
	 * */
	public static void showToast(int resId) {
		showToast(App.getInstance().getString(resId));
	}
}
