package com.bwap.weatherapp.WeatherApp.controller;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class WeatherService {
    private OkHttpClient client;
    private Response response;
    private String CityName;
    String unit;
}
