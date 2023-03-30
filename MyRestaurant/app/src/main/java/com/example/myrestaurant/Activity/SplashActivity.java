package com.example.myrestaurant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.R;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIMER = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        //tạo đối tượng view
        ImageView imgLogo = (ImageView)findViewById(R.id.imgLogo);
        TextView txtRestaurant = (TextView)findViewById(R.id.txtRestaurant);
        TextView txtPowered = (TextView)findViewById(R.id.txtPowered);

        //lấy đối tượng animation
        Animation sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //thiết lập animation cho component
        imgLogo.setAnimation(sideAnim);
        txtRestaurant.setAnimation(sideAnim);
        txtPowered.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish(); //destroy activity khi back sẽ ko về splash
            }
        }, SPLASH_TIMER);
    }
}
