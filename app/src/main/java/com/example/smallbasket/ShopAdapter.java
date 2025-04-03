package com.example.smallbasket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.bumptech.glide.Glide;
import com.example.smallbasket.model.Grocery;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShopAdapter extends ArrayAdapter<Grocery> {
    Context context;
    ArrayList<Grocery> listgrocery = new ArrayList<>();
    int design;
    ArrayList<Grocery> groceryList;
    ArrayList<Grocery> cartList = new ArrayList<Grocery>();
    int cardValue = 0;
   // int qty [];
    int price[];

    ShopAdapter(Context context,int design,ArrayList<Grocery> groceryList){
        super(context,design,groceryList);
        this.context = context;
        this.design = design;
        this.groceryList = groceryList;
        cartList.clear();
        ApplicationData.qty = new int[groceryList.size()];
        for(int i=0;i<groceryList.size();i++)
        {
            ApplicationData.qty[i]=0;
        }
        ApplicationData.cartTotal =0;
        price = new int[groceryList.size()];

    }

    public void setFilteredList(ArrayList<Grocery> filteredList)
    {
        this.groceryList = filteredList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(design,null,false);

        TextView productName = view.findViewById(R.id.productName);
        TextView quantity = view.findViewById(R.id.quantity);
        TextView unit = view.findViewById(R.id.unit);
        TextView cost = view.findViewById(R.id.cost);
        TextView description = view.findViewById(R.id.description);
        ImageButton PlusButton = view.findViewById(R.id.PlusButton);
        ImageButton MinusButton = view.findViewById(R.id.MinusButton);
        TextView numberOfProduct = view.findViewById(R.id.numberOfProduct);
        ImageView cardImg = view.findViewById(R.id.cardimg);
        Button ProceedButton = ((Activity)context).findViewById(R.id.ProceedButton);

        Grocery grocery = groceryList.get(position);
        productName.setText(grocery.getProductName());
        quantity.setText(""+grocery.getProductStock());
        unit.setText(grocery.getMeasurement());
        cost.setText(""+grocery.getProductPrice());
        description.setText(grocery.getDescription());
        numberOfProduct.setText(""+ApplicationData.qty[position]);

        Glide.with(context).load(grocery.getImgUrl()).into(cardImg);


        price[position] = groceryList.get(position).getProductPrice();


        PlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationData.qty[position]++;
                grocery.setOrder(ApplicationData.qty[position]);
                numberOfProduct.setText(""+grocery.getOrder());
                cardValue = cardValue + price[position];
                TextView Total = ((Activity)context).findViewById(R.id.Total);
                Total.setText(""+cardValue);
            }
        });

        MinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ApplicationData.qty[position] >0) {
                    ApplicationData.qty[position] -= 1;
                    grocery.setOrder(ApplicationData.qty[position]);
                    numberOfProduct.setText("" + grocery.getOrder());
                    cardValue = cardValue - price[position];
                    TextView Total = ((Activity) context).findViewById(R.id.Total);
                    Total.setText("" + cardValue);
                }
            }
        });

        ProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartList.clear();
                for(int i=0; i <=groceryList.size()-1; i++){
                    Grocery gr = groceryList.get(i);
                    if (gr.getOrder() > 0){
                        cartList.add(gr);
                      //  Toast.makeText(context, "ListSize:"+cartList.size(), Toast.LENGTH_SHORT).show();
                    }
                    ApplicationData.cartTotal = (double) cardValue;
                }

                if(cartList.isEmpty())
                {
                    Toast.makeText(context,"Please select itmes ",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent ii = new Intent(context, Yourcart.class);
                ii.putExtra("selection",cartList);
                ii.putExtra("cartTotal",cardValue);
                context.startActivity(ii);
                }

//
            }
        });



        return view;

    }




}