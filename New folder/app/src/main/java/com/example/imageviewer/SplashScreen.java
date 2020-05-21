package com.example.imageviewer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private static int timeout=3000;
    TextView txt;
    ImageView img;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_view);
        txt=findViewById(R.id.splash_txt);
        img=findViewById(R.id.splash_img);

        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.myanim);

        Animation animation1 = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.anim_opposit);
        img.startAnimation(animation);
        txt.startAnimation(animation1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        },timeout
        );
    }
}
