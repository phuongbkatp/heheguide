package com.haris.navigato.InterfaceUtil;

/**
 * Created by hp on 6/8/2018.
 */

public interface WeatherCallback {

    void onWeatherSuccess(String temp, String tempTagline);

    void onWeatherFailure(String error);


}
