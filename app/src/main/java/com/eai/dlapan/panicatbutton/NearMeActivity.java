package com.eai.dlapan.panicatbutton;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eai.dlapan.panicatbutton.sevices.WeatherService;

public class NearMeActivity extends AppCompatActivity {

    // Information
    private Double lat, lon;
    private String latlon;
    private Location _location;

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


    // Services
    private WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_me);

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
    }
}
