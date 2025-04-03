package com.example.smallbasket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
EditText user,etpass;
SharedPreferences shad;

Button login,change,btshop;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.user);
        etpass = findViewById(R.id.etpass);
        login = findViewById(R.id.login);
        change = findViewById(R.id.change);
        btshop = findViewById(R.id.btshop);

        shad = getSharedPreferences("admin", Context.MODE_PRIVATE);
        String mail = shad.getString("Mail","adminm");
        String password = shad.getString("Password","admin2024");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  String email = user.getText().toString();
                  String pass = etpass.getText().toString();

                  if(email.equals(mail) && pass.equals(password))
                  {
                      Intent ii = new Intent(getApplication(),AdminZone.class);
                      startActivity(ii);
                  }
                  else
                  {
                      Toast.makeText(MainActivity.this, "UnAuthorized User", Toast.LENGTH_LONG).show();

                  }

            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(), changeactivity.class);
                startActivity(ii);
            }
        });

        btshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(MainActivity.this,Shopscreen.class);
                startActivity(ii);
            }
        });



    }
}