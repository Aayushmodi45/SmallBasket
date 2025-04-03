package com.example.smallbasket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smallbasket.model.Methods;

import java.util.ArrayList;

public class PaymentAdapter extends ArrayAdapter<Methods> {
   Context cont;
int design;
ArrayList<Methods>list;

PaymentAdapter(Context cont,int design,ArrayList<Methods> list)
{
           super(cont,design,list);

          this.cont = cont;
          this.design = design;
          this.list = list;

}
public View getView(int position, View convertView, ViewGroup parent)
{
    LayoutInflater inflater =LayoutInflater.from(cont);
    View view =inflater.inflate(design,null,false);

    ImageView paymentLogo =view.findViewById(R.id.paymentLogo);
    TextView paymentType = view.findViewById(R.id.paymentType);
    ImageView arrow = view.findViewById(R.id.arrow);
    RelativeLayout paymentButton = view.findViewById(R.id.paymentButton);

    Methods md =list.get(position);
    paymentLogo.setImageDrawable(cont.getResources().getDrawable(md.getImage()));
    paymentType.setText(md.getMethod_name());

    paymentButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent ii = new Intent(cont,SelectAddress.class);
            cont.startActivity(ii);
        }
    });
    return view;

}




}
