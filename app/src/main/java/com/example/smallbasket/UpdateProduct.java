package com.example.smallbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smallbasket.R;
import com.example.smallbasket.model.Grocery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateProduct extends Activity {
   TextView ProductName;
    EditText ProductStock,ProductPrice,ProductDescription;
    Spinner Measurement;
    Button ImgSelect,Update,Cancel;
    DatabaseReference dbRef,dbUpq;
    String gid;
    ImageView ImgPreview;


    ArrayList<Grocery> groceryList = new ArrayList<>();
    int position =0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        ProductName = findViewById(R.id.ProductName);
        ProductPrice = findViewById(R.id.ProductPrice);
        ProductStock = findViewById(R.id.ProductStock);
        Measurement = findViewById(R.id.Measurement);
        ImgSelect = findViewById(R.id.ImgSelect);
        ProductDescription = findViewById(R.id.Description);
        Update = findViewById(R.id.Update);
        Cancel = findViewById(R.id.Cancel);
        ImgPreview = findViewById(R.id.ImgPreview);

        Intent i2 = getIntent();
        groceryList = (ArrayList<Grocery>) i2.getSerializableExtra("list");
        position = i2.getIntExtra("position", -1);
        Grocery gr = groceryList.get(position);

        if (groceryList != null && position != -1) {

            if (gr != null) {
                gid = gr.getGid();  // Set gid here

                ProductName.setText(gr.getProductName());
                ProductPrice.setText(String.valueOf(gr.getProductPrice()));
                ProductStock.setText(String.valueOf(gr.getProductStock()));
                ProductDescription.setText(gr.getDescription());

                String[] measurement = {"g", "kg", "ml", "l", "units"};
                ArrayAdapter<String> ad = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, measurement);
                Measurement.setAdapter(ad);

                // Set the measurement unit in the spinner
                String measurementUnit = gr.getMeasurement();
                int spinnerPosition = ad.getPosition(measurementUnit);
                Measurement.setSelection(spinnerPosition);

                Glide.with(this)
                        .load(gr.getImgUrl())
                        .into(ImgPreview);
            } else {
                Toast.makeText(this, "Invalid grocery item", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Invalid position or grocery list", Toast.LENGTH_LONG).show();
        }

        Update.setOnClickListener(v-> {
            if (gid != null && !gid.isEmpty()) {
                String GroceryName = ProductName.getText().toString();
                int GroceryPrice = Integer.parseInt(ProductPrice.getText().toString());
                int Quantity = Integer.parseInt(ProductStock.getText().toString());
                String Unit = Measurement.getSelectedItem().toString();
                String Description = ProductDescription.getText().toString();
                String imgUrl = gr.getImgUrl();

                Grocery grocery = new Grocery(imgUrl, GroceryName, Description, Unit, gid, Quantity, GroceryPrice);

                dbRef = FirebaseDatabase.getInstance().getReference("Grocery");
                dbRef.child(gid).setValue(grocery).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Record updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to update record", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Invalid Product ID", Toast.LENGTH_SHORT).show();
            }
        });
    }

}