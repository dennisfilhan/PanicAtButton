package com.eai.dlapan.panicatbutton;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eai.dlapan.panicatbutton.ext.PanicDialog;
import com.eai.dlapan.panicatbutton.sevices.WeatherService;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnPanic, btnMenu;
    private TextView btnNearMe;

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
                    latlon=TextUtils.join(",",new String[]{""+_location.getLatitude(),""+_location.getLongitude()});
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
        setContentView(R.layout.activity_main);

        /*
        * Init
        * */
        final PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();
        final int pulsatorBaseDuration = pulsator.getDuration();

        btnPanic = (ImageButton) findViewById(R.id.btnPanic);
        btnPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PanicDialog(MainActivity.this, pulsator).show();
            }
        });
        btnMenu = (ImageButton) findViewById(R.id.btnMainMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });
        btnNearMe = (TextView) findViewById(R.id.btnMainNearMe);
        btnNearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NearMeActivity.class));
            }
        });

        /*
         * Services
         * */
        TextView lblTemp, lblTempDesc, lblWeatherLoc;
        lblTemp=findViewById(R.id.lblWeatherTemp);
        lblTempDesc=findViewById(R.id.lblWeatherTempDesc);
        lblWeatherLoc=findViewById(R.id.lblWeatherLoc);
        ImageView lblWeatherIco=findViewById(R.id.lblWeatherImg);
        weatherService=new WeatherService(getApplicationContext(),lblTemp,lblTempDesc,lblWeatherLoc,lblWeatherIco);
        weatherService.setWeather(null);
        LinearLayout layoutWeather = findViewById(R.id.layoutWeatherMain);
        layoutWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherService.setWeather(latlon);
            }
        });

        // Ask Location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "LOCATION PERMISSON DENIED.", Toast.LENGTH_LONG).show();
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,10,mLocationListener);
        }
    }
}
