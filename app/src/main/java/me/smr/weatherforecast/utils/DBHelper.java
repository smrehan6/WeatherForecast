package me.smr.weatherforecast.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

import me.smr.weatherforecast.App;
import me.smr.weatherforecast.models.City;

/**
 * This Singleton class will be used for all the database operations.
 * <p>
 * Usage: {@code DBHelper.getInstance()}
 * */
public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "forecast.db";
	private static final int DB_VERSION = 1;

	private static abstract class CityTable implements BaseColumns {
		public static final String TABLE_NAME = "cities";
		public static final String COL_ID = "_id";
		public static final String COL_NAME = "name";
		public static final String COL_COUNTRY_CODE = "country_code";
		public static final String COL_LAT = "lantitude";
		public static final String COL_LON = "longtitude";
	}

	// TODO fix city order
	private static final String CREATE_TABLE_CITIES = "CREATE TABLE IF NOT EXISTS "
			+ CityTable.TABLE_NAME
			+ "("
			+ CityTable.COL_ID
			+ " INTEGER PRIMARY KEY, "
			+ CityTable.COL_NAME
			+ " TEXT NOT NULL, "
			+ CityTable.COL_COUNTRY_CODE
			+ " TEXT NOT NULL, "
			+ CityTable.COL_LAT
			+ " DOUBLE NOT NULL, "
			+ CityTable.COL_LON + " DOUBLE NOT NULL)";

	private static DBHelper instance = null;

	public static DBHelper getInstance() {
		if (instance == null) {
			instance = new DBHelper();
		}
		return instance;
	}

	private DBHelper() {
		super(App.getInstance(), DATABASE_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_CITIES);
		// TODO other tables
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("DBHelper", "Upgrading table from " + oldVersion + " to "
				+ newVersion + " which will destroy all data!");
		db.execSQL("DROP TABLE IF EXISTS " + CityTable.TABLE_NAME);
		// TODO other tables
		onCreate(db);
	}

	// TODO CRUDs
	/**
	 * Inserts or updates a {@link City} in the database.
	 * 
	 * @param city
	 *            the {@code City} to be added/updated.
	 * @return the row ID of the newly inserted row, or -1 if an error occurred.
	 * */
	public long addOrUpdateCity(final City city) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CityTable.COL_ID, city.getCityId());
		values.put(CityTable.COL_NAME, city.getCityName());
		values.put(CityTable.COL_COUNTRY_CODE, city.getCountryCode());
		values.put(CityTable.COL_LAT, city.getLatitude());
		values.put(CityTable.COL_LON, city.getLongtitude());
		long result = db.replace(CityTable.TABLE_NAME, null, values);
		db.close();
		return result;
	}

	/**
	 * Gets all the {@code City}s store in the database.
	 * 
	 * @return an {@code ArrayList} of all the stored {@link City}s in the
	 *         Database.
	 * */
	public ArrayList<City> getAllCities() {
		final SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<City> list = new ArrayList<City>();
		Cursor cursor = db.query(CityTable.TABLE_NAME, null, null, null, null,
				null, null);
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setCityId(cursor.getInt(0));
				city.setCityName(cursor.getString(1));
				city.setCountryCode(cursor.getString(2));
				city.setLatitude(cursor.getDouble(3));
				city.setLongtitude(cursor.getDouble(4));
				list.add(city);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * Deletes a {@code City} from the database.
	 * 
	 * @param cityId
	 *            The ID if the city to be deleted.
	 * @return The number of {@code City}s deleted {@code 0} if none deleted.
	 * */
	public int deleteCity(int cityId) {
		final SQLiteDatabase db = this.getWritableDatabase();
		int result = db.delete(CityTable.TABLE_NAME, CityTable.COL_ID + "="
				+ cityId, null);
		db.close();
		return result;
	}

	/**
	 * @return {@code String} of all stored ids saperated by comma. <br/>
	 *         Eg. : 524901,703448,2643743
	 * */
	public String getAllCityIDs() {
		StringBuilder sb = new StringBuilder();
		for (City city : getAllCities()) {
			if (sb.length() == 0) {
				sb.append(city.getCityId());
			} else {
				sb.append("," + city.getCityId());
			}
		}
		return sb.toString();
	}

	/**
	 * @return the number of {@code City}s stored.
	 * */
	public int getCityCount() {
		final SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(CityTable.TABLE_NAME, null, null, null, null,
				null, null);
		int result = cursor.getCount();
		db.close();
		cursor.close();
		return result;
	}
}
