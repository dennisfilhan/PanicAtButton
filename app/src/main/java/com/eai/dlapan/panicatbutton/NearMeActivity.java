package com.eai.dlapan.panicatbutton;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eai.dlapan.panicatbutton.api.APIConfig;
import com.eai.dlapan.panicatbutton.domain.NewsModel.News;
import com.eai.dlapan.panicatbutton.domain.NewsModel.NewsResponse;
import com.eai.dlapan.panicatbutton.ext.NewsRecycler;
import com.eai.dlapan.panicatbutton.sevices.NewsService;
import com.eai.dlapan.panicatbutton.sevices.WeatherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearMeActivity extends AppCompatActivity {

    // Information
    private Double lat, lon;
    private String latlon;
    private Location _location;
    private List<News> newsList;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location!=null){
                lat = location.getLatitude();
                lon = location.getLongitude();
                _location = location;
                Log.d("LOCATION__INFO", "Location founded. Lat "+lat+" Lon "+lon);
                if(_location!=null){
                    latlon= TextUtils.join(",",new String[]{""+_location.getLatitude(),""+_location.getLongitude()});
                    weatherService.setWeather(latlon);
                    Log.d("LOCATION__INFO", "Weather is Updated");
                }
            }else{
                Log.d("LOCATION", "Location not found.");
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private LocationManager locationManager;

    // Elements
    private RecyclerView newsRecycler;

    // Adapter
    private NewsRecycler newsAdapter;

    // Services
    private WeatherService weatherService;
    private NewsService newsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_me);

        /*
        * Elements
        * */
        newsList=new ArrayList<>();
        newsRecycler=(RecyclerView)findViewById(R.id.recyclerNews);
        newsRecycler.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsRecycler(this.newsList);
        newsRecycler.setAdapter(newsAdapter);

        /*
         * Services
         * */
        weatherService=new WeatherService(
                getApplicationContext(),
                (TextView)findViewById(R.id.lblWeatherTemp),
                (TextView)findViewById(R.id.lblWeatherTempDesc),
                (TextView)findViewById(R.id.lblWeatherLoc),
                (ImageView) findViewById(R.id.lblWeatherImg)
        );
        weatherService.setWeather(latlon);
        LinearLayout layoutWeather = findViewById(R.id.layoutWeatherMain);
        layoutWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherService.setWeather(latlon);
            }
        });
        newsService=new NewsService();

        loadNews();
    }

    private void loadNews(){
        Call<NewsResponse> call = APIConfig.RETROFIT_SERVICE.getListNews();
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                Log.d("NEWS_DATA_RESPONSE", "Response "+response.body().toString());
                List<News> list=response.body().getArticles();
                newsList.clear();
                newsList.addAll(list);
                newsAdapter.notifyDataSetChanged();
                //newsRecycler.setAdapter(new NewsRecycler(list));

                //Log.d("DATA_LIST_NEWS","Count: "+newsRecycler.getAdapter().getItemCount());
                Log.d("DATA_LIST_NEWS","Items: "+list.toString());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d("NEWS_DATA_RESPONSE", "Failed! Cause "+t.getMessage());
            }
        });
    }
}
