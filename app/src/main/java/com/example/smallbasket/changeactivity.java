package com.example.smallbasket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class changeactivity extends Activity {

    EditText oldpass,newpass;
    TextView errorMsg;
    Button btUp,btCancle;
    SharedPreferences shad;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_changeactivity);

        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.newpass);
        btUp = findViewById(R.id.btUp);
        btCancle = findViewById(R.id.btCancle);
        errorMsg = findViewById(R.id.errorMsg);

        shad = getSharedPreferences("admin",MODE_PRIVATE);
        String existing_pass = shad.getString("Password","admin2024");

        btUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verify_pass = oldpass.getText().toString();
                if (verify_pass.equals(existing_pass)){
                    String set_pass = newpass.getText().toString();
                    SharedPreferences.Editor edit = shad.edit();
                    edit.putString("Password",set_pass);
                    edit.commit();
                    Toast.makeText(changeactivity.this, "Password Updated", Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(ii);
                }
                else {

                    errorMsg.setText("Old Password Does Not Match");


//                    Toast.makeText(ChangePassword.this, "Old Password did not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpass.setText("");
                newpass.setText("");
            }
        });
    }
}