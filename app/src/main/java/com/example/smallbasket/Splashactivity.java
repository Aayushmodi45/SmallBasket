package com.example.smallbasket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class Splashactivity extends Activity {
    ImageView iv1,iv2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashactivity);

        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splashactivity.this,MainActivity.class));
                finish();
            }
        },3000);


        Animation animation = AnimationUtils.loadAnimation(this,R.anim.come);
        iv1.startAnimation(animation);

        Animation a1 = AnimationUtils.loadAnimation(this,R.anim.come);
        iv2.startAnimation(a1);
    }
}