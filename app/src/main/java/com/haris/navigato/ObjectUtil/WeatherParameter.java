package com.haris.navigato.ObjectUtil;

import com.haris.navigato.InterfaceUtil.ConnectivityCallback;
import com.haris.navigato.InterfaceUtil.WeatherCallback;

/**
 * Created by hp on 6/8/2018.
 */

public class WeatherParameter {
    private double latitude;
    private double longitude;
    private WeatherCallback weatherCallback;
    private ConnectivityCallback connectivityCallback;

    public WeatherParameter(double latitude, double longitude, WeatherCallback weatherCallback, ConnectivityCallback connectivityCallback) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherCallback = weatherCallback;
        this.connectivityCallback = connectivityCallback;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public WeatherCallback getWeatherCallback() {
        return weatherCallback;
    }

    public void setWeatherCallback(WeatherCallback weatherCallback) {
        this.weatherCallback = weatherCallback;
    }

    public ConnectivityCallback getConnectivityCallback() {
        return connectivityCallback;
    }

    public void setConnectivityCallback(ConnectivityCallback connectivityCallback) {
        this.connectivityCallback = connectivityCallback;
    }
}
