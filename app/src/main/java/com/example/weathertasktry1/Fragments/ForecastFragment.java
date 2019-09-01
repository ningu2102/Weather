package com.example.weathertasktry1.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weathertasktry1.Common;
import com.example.weathertasktry1.IOpenWeatherMap;
import com.example.weathertasktry1.R;
import com.example.weathertasktry1.RetrofitClient;

import io.reactivex.Scheduler;
import com.example.weathertasktry1.Model.WeatherForecastResult;
import com.example.weathertasktry1.WeatherForecastAdpater;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    TextView cityname,geocoord;
    RecyclerView recycler_forecast;

    static ForecastFragment instance;
    public static ForecastFragment getInstance()
    {
        if (instance ==  null)
            instance = new ForecastFragment();
        return instance;
    }


    public ForecastFragment() {
        // Required empty public constructor
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        cityname = view.findViewById(R.id.citynamef);
        geocoord = view.findViewById(R.id.geocoordf);

        recycler_forecast = view.findViewById(R.id.recycler_forecast);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        getWeatherForecastInformation();

        return view;
    }

    private void getWeatherForecastInformation()
    {
        compositeDisposable.add(mService.getForecastWeatherByLatLng(
                String.valueOf(Common.latitudeee),
                String.valueOf(Common.longitudeee),
                Common.api_key,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                        displayForecastWeather(weatherForecastResult);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ForecastERROR",""+throwable.getMessage());

                    }
                }));


    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult)
    {
        cityname.setText(new StringBuilder(weatherForecastResult.city.name));
        geocoord.setText(new StringBuilder(weatherForecastResult.city.coord.toString()));

        WeatherForecastAdpater weatherForecastAdpater = new WeatherForecastAdpater(getContext(),weatherForecastResult);
        recycler_forecast.setAdapter(weatherForecastAdpater);

    }

}
