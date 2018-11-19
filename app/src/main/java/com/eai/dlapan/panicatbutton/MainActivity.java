package com.eai.dlapan.panicatbutton;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnPanic, btnMenu;
    private TextView btnNearMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();
        final int pulsatorBaseDuration = pulsator.getDuration();

        btnPanic = (ImageButton) findViewById(R.id.btnPanic);
        btnPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pulsator.setDuration(1000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pulsator.setDuration(pulsatorBaseDuration);

                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                }, 10*1000);
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
    }
}
