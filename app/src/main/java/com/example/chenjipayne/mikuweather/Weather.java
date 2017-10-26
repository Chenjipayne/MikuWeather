package com.example.chenjipayne.mikuweather;

import java.io.Serializable;

/**
 * Created by chenji payne on 2017/10/22.
 */

public class Weather implements Serializable {
    private String humidity;
    private String cityname;
    private String weathername;
    private String temperature;
    private String wind;

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getWeathername() {
        return weathername;
    }

    public void setWeathername(String weathername) {
        this.weathername = weathername;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
