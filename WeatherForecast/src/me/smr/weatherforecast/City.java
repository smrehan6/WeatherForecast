package me.smr.weatherforecast;

/**
 * This model class will be used to keep the required details of any city.
 * */
public class City {

	private int cityId;
	private String cityName;
	private String countryCode;
	private double lantitude, longtitude;

	public City(int cityId, String cityName, String countryCode,
			double lantitude, double longtitude) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.countryCode = countryCode;
		this.lantitude = lantitude;
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

	public double getLantitude() {
		return lantitude;
	}

	public void setLantitude(double lantitude) {
		this.lantitude = lantitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

}
