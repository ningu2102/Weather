package com.example.weathertasktry1;

import com.example.weathertasktry1.Model.WeatherForecastResult;
import com.example.weathertasktry1.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap
{
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                 @Query("lon") String lng,
                                                 @Query("appid") String appid,
                                                 @Query("units") String units);

    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByLatLng(@Query("lat") String lat,
                                                                 @Query("lon") String lng,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String units);

    @GET("weather")
    Observable<WeatherResult> getWeatherByCityName(@Query("q") String cityname,
                                                   @Query("appid") String appid,
                                                   @Query("units") String units);
}

