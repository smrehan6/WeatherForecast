package me.smr.weatherforecast.models;

import android.os.Parcel;
import android.os.Parcelable;

import me.smr.weatherforecast.R;

public class CityData implements Parcelable {

    private String id;
    private String cityName;
    private String temp;
    private String weather;
    private String image;

    public CityData() {
        // default constructor
    }

    protected CityData(Parcel in) {
        id = in.readString();
        cityName = in.readString();
        temp = in.readString();
        weather = in.readString();
        image = in.readString();
    }

    public static final Creator<CityData> CREATOR = new Creator<CityData>() {
        @Override
        public CityData createFromParcel(Parcel in) {
            return new CityData(in);
        }

        @Override
        public CityData[] newArray(int size) {
            return new CityData[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    public String getTemp() {
        return temp + " °C";
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
                return R.drawable.logo;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(cityName);
        parcel.writeString(temp);
        parcel.writeString(weather);
        parcel.writeString(image);
    }
}
