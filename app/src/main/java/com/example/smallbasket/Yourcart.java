package com.example.smallbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallbasket.model.Grocery;

import java.util.ArrayList;

public class Yourcart extends Activity {
    ListView listItem;
    int cardTotal;
    TextView Total;
    ArrayList<Grocery>  selection = new ArrayList<Grocery>();
    Button PaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourcart);

        listItem = findViewById(R.id.listItems);
        Total = findViewById(R.id.Total);
        PaymentButton = findViewById(R.id.PaymentButton);


        Intent ii = getIntent();
        selection.clear();
        selection = (ArrayList<Grocery>) ii.getSerializableExtra("selection");
       // Toast.makeText(Yourcart.this,"list size :"+selection.size(),Toast.LENGTH_LONG).show();

        ProceedAdapter adapter = new ProceedAdapter(getApplicationContext(),R.layout.selected_items,selection);
        listItem.setAdapter(adapter);

        cardTotal = ii.getIntExtra("cartTotal",0);
        Total.setText(""+cardTotal);

        PaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(),Payment.class);
                startActivity(ii);
            }
        });


    }
}