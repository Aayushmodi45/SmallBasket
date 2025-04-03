package com.example.smallbasket;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.smallbasket.model.Grocery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Showproduct extends Activity {

    private RecyclerView recyclerView;
    private GroceryAdapter adapter;
    private ArrayList<Grocery> listgrocery = new ArrayList<>();

    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showproduct);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GroceryAdapter(this, listgrocery);
        recyclerView.setAdapter(adapter);

        dbref = FirebaseDatabase.getInstance().getReference("Grocery");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listgrocery.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Grocery g = snap.getValue(Grocery.class);
                    listgrocery.add(g);
                }

                // Notify adapter about data changes
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });


    }
}
