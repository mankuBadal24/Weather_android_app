package com.example.weather_app_by_mb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageView imgAnim ;
        TextView txtAnim;
        TextView txtAnim2;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgAnim = findViewById(R.id.logo);
        txtAnim = findViewById(R.id.intro);
        txtAnim2 = findViewById(R.id.name);
        Animation move = AnimationUtils.loadAnimation(Splash.this,R.anim.move);
        imgAnim.setAnimation(move);
        txtAnim.setAnimation(move);
        txtAnim2.setAnimation(move);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent pass = new Intent(Splash.this,MainActivity.class);
                startActivity(pass);
                finish();
            }
        },5000);
    }
}