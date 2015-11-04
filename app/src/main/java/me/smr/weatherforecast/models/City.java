package me.smr.weatherforecast.models;

/**
 * This model class will be used to keep the required details of any city.
 * */
public class City {

	private int cityId;
	private String cityName;
	private String countryCode;
	private double latitude, longtitude;

	public City() {
		super();
	}

	public City(int cityId, String cityName, String countryCode,
			double lantitude, double longtitude) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.countryCode = countryCode;
		this.latitude = lantitude;
		this.longtitude = longtitude;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double lantitude) {
		this.latitude = lantitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	@Override
	public String toString() {
		return this.cityName + ", " + this.countryCode;
	}

}
