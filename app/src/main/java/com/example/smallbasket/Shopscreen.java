package com.example.smallbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smallbasket.model.Grocery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Shopscreen extends Activity {

    ListView Products;
    ArrayList<Grocery> listgrocery = new ArrayList<>();
    ArrayList<Grocery> cartList;
    DatabaseReference dbref;
    TextView Total;
    Button ProceedButton;
    SearchView search;
    ShopAdapter adapter;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopscreen);



        Products = findViewById(R.id.Products);
        Total = findViewById(R.id.Total);
        ProceedButton = findViewById(R.id.ProceedButton);
        search = findViewById(R.id.search);
        search.clearFocus();


        dbref = FirebaseDatabase.getInstance().getReference("Grocery");
        adapter = new ShopAdapter(this, R.layout.shopadapter, new ArrayList<>());
        Products.setAdapter(adapter);


        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listgrocery.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Grocery g = snap.getValue(Grocery.class);
                    listgrocery.add(g);
                }

                ShopAdapter adapter = new ShopAdapter(Shopscreen.this,R.layout.shopadapter,listgrocery);
                Products.setAdapter(adapter);
                // Notify adapter about data changes
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        ProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(),Yourcart.class);
                startActivity(ii);
            }
        });
    }

    private void filterList(String Text) {
        ArrayList<Grocery>  filteredList = new ArrayList<>();
        for(Grocery gr : listgrocery)
        {
            if(gr.getProductName().toLowerCase().contains(Text.toLowerCase()))
            {
                filteredList.add(gr);
            }
        }
        if(filteredList.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"There no item related search",Toast.LENGTH_LONG).show();
        }
        else {
            adapter.setFilteredList(filteredList);
        }
    }
}