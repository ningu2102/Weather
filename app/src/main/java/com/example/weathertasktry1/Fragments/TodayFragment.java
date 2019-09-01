package com.example.weathertasktry1.Fragments;


import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathertasktry1.Common;
import com.example.weathertasktry1.IOpenWeatherMap;
import com.example.weathertasktry1.Model.WeatherResult;
import com.example.weathertasktry1.R;
import com.example.weathertasktry1.RetrofitClient;
import com.squareup.picasso.Picasso;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {

    ImageView img_weather;
    TextView txt_cityname,txt_humidity,txt_sunrise,txt_sunset,txt_pressure,txt_temperature,txt_description,txt_date_time,txt_wind,txt_geocoord;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;



    static TodayFragment instance;

    public static TodayFragment getInstance()
    {
        if(instance == null)
        {
            instance = new TodayFragment();
        }
        return instance;
    }

    public TodayFragment() {
        // Required empty public constructor
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_today, container, false);

        img_weather = (ImageView) view.findViewById(R.id.weather);
        txt_cityname = (TextView) view.findViewById(R.id.cityname);
        txt_humidity = (TextView)view.findViewById(R.id.humidity);
        txt_sunrise = (TextView) view.findViewById(R.id.sunrise);
        txt_sunset = (TextView) view.findViewById(R.id.sunset);
        txt_pressure = (TextView) view.findViewById(R.id.pressure);
        txt_temperature = (TextView) view.findViewById(R.id.temprature);
        txt_description = (TextView) view.findViewById(R.id.description);
        txt_date_time = (TextView) view.findViewById(R.id.date_time);
        txt_wind = (TextView) view.findViewById(R.id.wind);
        txt_geocoord = (TextView) view.findViewById(R.id.geocoord);

        //weather_panel = (LinearLayout) view.findViewById(R.id.weather_panel);
        //loading = (ProgressBar) view.findViewById(R.id.loading);

        getWeatherInformation();
        return view;
    }

    private void getWeatherInformation()
    {
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Common.latitudeee),
                String.valueOf(Common.longitudeee),Common.api_key,"metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {
                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/")
                                .append(weatherResult.getWeather().get(0).getIcon())
                                .append(".png").toString()).into(img_weather);

                        txt_cityname.setText(weatherResult.getName());
                        Log.d("city",weatherResult.getName());
                        txt_description.setText(new StringBuilder("Weather in ")
                                .append(weatherResult.getName()).toString());
                        txt_wind.setText(new StringBuilder("Speeed: ").append(weatherResult.getWind().getSpeed()).append("Deg: ").append(weatherResult.getWind().getSpeed()));
                        txt_temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("°C"));
                        txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" hpa"));
                        txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append(" %"));
                        txt_date_time.setText(Common.convertUnixToDate(weatherResult.getDt()));
                        txt_sunrise.setText(Common.convertUnixToHour(weatherResult.getSys().getSunrise()));
                        txt_sunset.setText(Common.convertUnixToHour(weatherResult.getSys().getSunset()));
                        txt_geocoord.setText(new StringBuilder("").append(weatherResult.getCoord().toString()).append(" ").toString());

//                        weather_panel.setVisibility(View.GONE);
//                        loading.setVisibility(View.GONE);


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(),"ereiyy"+throwable.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("error",throwable.getMessage());
                    }
                })
        );
    }

}
