package me.smr.weatherforecast;

public class CityData {

	private String cityName;
	private String temp;
	private String weather;
	private String image;

	public String getCityName() {
		return cityName;
	}

	public String getTemp() {
		return temp;
	}

	public String getWeather() {
		return weather;
	}

	public int getImage() {
		switch (image) {
		case "01d":
			return R.drawable.d1;
		case "01n":
			return R.drawable.n1;
		case "02d":
			return R.drawable.d2;
		case "02n":
			return R.drawable.n2;
		case "03d":
		case "03n":
			return R.drawable.d3;
		case "04d":
		case "04n":
			return R.drawable.d4;
		case "09d":
		case "09n":
			return R.drawable.d9;
		case "10d":
			return R.drawable.d10;
		case "10n":
			return R.drawable.n10;
		case "11d":
		case "11n":
			return R.drawable.d11;
		case "13d":
		case "13n":
			return R.drawable.d13;
		case "50d":
		case "50n":
			return R.drawable.d50;
		default:
			return R.drawable.ic_launcher;
		}
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
