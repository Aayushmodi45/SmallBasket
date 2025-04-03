package com.example.smallbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.health.connect.datatypes.MealType;
import android.os.Bundle;
import android.widget.ListView;

import com.example.smallbasket.model.Methods;

import java.util.ArrayList;

public class Payment extends Activity {

    ListView paymentList;
    ArrayList<Methods>list =new ArrayList<Methods>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentList = findViewById(R.id.PaymentList);
        list.add(new Methods("Wallets",R.drawable.wallet));
        list.add(new Methods("Netbanking",R.drawable.mobilebankinglogo));
        list.add(new Methods("Google Pay",R.drawable.googlepay));
        list.add(new Methods("Cash on Delivery",R.drawable.cashondeliverylogo));
        list.add(new Methods("CreditDebit card",R.drawable.creditdebit));

        PaymentAdapter adapter = new PaymentAdapter(Payment.this,R.layout.payment_buttons,list);
        paymentList.setAdapter(adapter);


      }
}