package com.haris.navigato.ObjectUtil;

/**
 * Created by hp on 5/20/2018.
 */

public class WeatherDetail extends Object {
    String placeName;
    String temperature;
    String summary;


    public WeatherDetail(String placeName, String temperature, String summary) {
        this.placeName = placeName;
        this.temperature = temperature;
        this.summary = summary;
    }


    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
