package me.smr.weatherforecast.utils;

import me.smr.weatherforecast.App;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "forecast.db";
	private static final int DB_VERSION = 1;

	private static abstract class CityTable implements BaseColumns {
		public static final String TABLE_NAME = "cities";
		public static final String COL_ID = "_id";
		public static final String COL_NAME = "name";
		public static final String COL_COUNTRY_CODE = "country_code";
		public static final String COL_LAN = "lantitude";
		public static final String COL_LON = "longtitude";
	}

	public DBHelper() {
		super(App.getContext(), DATABASE_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
