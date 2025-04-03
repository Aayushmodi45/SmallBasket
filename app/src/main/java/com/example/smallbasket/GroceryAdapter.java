package com.example.smallbasket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smallbasket.model.Grocery;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    private Context context;
    ArrayList<Grocery> groceryList;
    DatabaseReference dbDel,dbUp;
    StorageReference delimage;

    public GroceryAdapter(Context context, ArrayList<Grocery> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grocery, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Grocery grocery = groceryList.get(position);

        holder.productName.setText(String.valueOf(grocery.getProductName()));
        holder.quantity.setText(String.valueOf(grocery.getProductStock()));
        holder.unit.setText(grocery.getMeasurement());
        holder.cost.setText(String.valueOf(grocery.getProductPrice()));
        holder.description.setText(grocery.getDescription());

        Glide.with(context)
                .load(grocery.getImgUrl())
                .into(holder.cardimg);
        dbDel = FirebaseDatabase.getInstance().getReference("Grocery");
        dbUp = FirebaseDatabase.getInstance().getReference("Grocery");


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(grocery);
            }
        });

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(context,UpdateProduct.class);
                ii.putExtra("position",position);
                ii.putExtra("list", groceryList);
                context.startActivity(ii);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public static class GroceryViewHolder extends RecyclerView.ViewHolder {
        ImageView cardimg;
        TextView quantity, unit, cost, description, productName;
        ImageButton deleteButton,updateButton;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            cardimg = itemView.findViewById(R.id.cardimg);
            quantity = itemView.findViewById(R.id.quantity);
            unit = itemView.findViewById(R.id.unit);
            cost = itemView.findViewById(R.id.cost);
            description = itemView.findViewById(R.id.description);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            updateButton= itemView.findViewById(R.id.updateButton);
        }
    }


// public void deleteProduct(Grocery item)
// {
//
//     Toast.makeText(context,"innn = "+item.getProductName(),Toast.LENGTH_LONG).show();
//
//     Query delQ = dbDel.orderByChild("gid").equalTo(item.getGid());
//     delimage = FirebaseStorage.getInstance().getReferenceFromUrl(item.getImgUrl());
//
//     delQ.addListenerForSingleValueEvent(new ValueEventListener() {
//         @Override
//         public void onDataChange(@NonNull DataSnapshot snapshot) {
//             for (DataSnapshot snap : snapshot.getChildren()) {
//                 snap.getRef().removeValue();
//
//                 delimage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                     @Override
//                     public void onSuccess(Void unused) {
//                         Toast.makeText(context.getApplicationContext(), "Product  DELETED.....", Toast.LENGTH_LONG).show();
//                     }
//                 });
//             }
//         }
//
//         @Override
//         public void onCancelled(@NonNull DatabaseError error) {
//
//         }
//     });
// }




    void deleteProduct(Grocery item) {
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        build.setTitle("Do you want to delete?");

        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the image storage reference
                delimage = FirebaseStorage.getInstance().getReferenceFromUrl(item.getImgUrl());

                // Delete the image from storage first
                delimage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Image deleted successfully, now delete from the database
                        Query delQ = dbDel.orderByChild("gid").equalTo(item.getGid());
                        delQ.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    snap.getRef().removeValue();
                                    Toast.makeText(context.getApplicationContext(), "Product DELETED from Storage and Database", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(context.getApplicationContext(), "Failed to delete from database", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(e -> {
                    // Handle failure to delete the image
                    Toast.makeText(context.getApplicationContext(), "Failed to delete image from storage", Toast.LENGTH_SHORT).show();
                });
            }
        });

        build.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No action needed
            }
        });

        build.show();
    }

}
