package com.example.smallbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminZone extends Activity {
    Button btshow,btadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_zone);

        btshow = findViewById(R.id.btshow);
        btadd = findViewById(R.id.btadd);
//        btdelete = findViewById(R.id.btdelete);
//        btupdate = findViewById(R.id.btupdate);

        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(),AddProduct.class);
                startActivity(ii);
            }
        });

        btshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(), Showproduct.class);
                startActivity(ii);
            }
        });


    }
}