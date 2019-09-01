package com.example.weathertasktry1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertasktry1.Model.WeatherForecastResult;
import com.squareup.picasso.Picasso;

public class WeatherForecastAdpater extends RecyclerView.Adapter<WeatherForecastAdpater.MyViewHolder> {

    Context context;
    WeatherForecastResult weatherForecastResult;

    public WeatherForecastAdpater(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/")
                            .append(weatherForecastResult.list.get(position).weather.get(0).getIcon())
                            .append(".png").toString()).into(holder.img_weather);

        holder.txt_date.setText(new StringBuilder(Common.convertUnixToDate(weatherForecastResult.list.get(position).dt)));
        holder.txt_temp.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getTemp())).append("°C"));
        holder.txt_desc.setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).getDescription()));

    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_date,txt_desc,txt_temp;
        ImageView img_weather;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_weather = itemView.findViewById(R.id.weatherf);
            txt_date = itemView.findViewById(R.id.datef);
            txt_desc = itemView.findViewById(R.id.descriptionf);
            txt_temp = itemView.findViewById(R.id.tempraturef);

        }
    }
}
