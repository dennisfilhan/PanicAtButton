package com.eai.dlapan.panicatbutton.sevices;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.eai.dlapan.panicatbutton.api.EndpointEmeract;
import com.eai.dlapan.panicatbutton.api.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherService {
    public final String WEATHER_DATA_RESPONSE="WEATHER_DATA_RESPONSE";
    private Context context;
    private TextView lblTemp, lblLocation, lblTempDescription;
    private ImageView lblTempIcon;
    private EndpointEmeract service;

    public WeatherService(Context context, TextView lblTemp, TextView lblTempDescription, TextView lblLocation, ImageView lblTempIcon) {
        this.context = context;
        this.lblTemp = lblTemp;
        this.lblLocation = lblLocation;
        this.lblTempDescription = lblTempDescription;
        this.lblTempIcon = lblTempIcon;
        this.service = RetrofitClient.getRetrofitInstance().create(EndpointEmeract.class);
    }

    public void setWeather(String latlon){
        Call<Map<String,Object>> call = service.getCurrentWeather();
        if(latlon!=null){call=service.getCurrentWeather(latlon);}
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Log.d(WEATHER_DATA_RESPONSE,"Response: "+response.body().toString());
                Map<String, Object> item = (Map) response.body().get("data");
                double temp = Double.parseDouble(((Map) item.get("main")).get("temp").toString());
                temp = Math.floor(Math.round(temp));
                String loc = item.get("name").toString();
                String tempDesc = ((Map)((ArrayList<Map<String,Object>>)item.get("weather")).get(0)).get("description").toString();
                String tempIco = ((Map)((ArrayList<Map<String,Object>>)item.get("weather")).get(0)).get("icon").toString();
                lblTemp.setText(String.valueOf((int)temp)+"℃");
                lblLocation.setText(loc);
                lblTempDescription.setText(tempDesc);
                String imgPath = "https://openweathermap.org/img/w/"+tempIco+".png";
                Log.d("PICASSO",imgPath);
                Picasso.get().load(imgPath).into(lblTempIcon);
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.d(WEATHER_DATA_RESPONSE, "Failed! Cause: " + t.getMessage());
            }
        });
    }
}
