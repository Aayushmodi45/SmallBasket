package com.example.smallbasket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smallbasket.R;
import com.example.smallbasket.model.Grocery;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddProduct extends Activity {

    EditText ProductName,ProductStock,ProductPrice,ProductDescription;
    Spinner Measurement;
    Button ImgSelect,AddGrocery,Cancel;
    ImageView ImgPreview;
    Uri imgPath;
    String gid = "";
    DatabaseReference dbRef;
    StorageReference mStore,pStore;

    String measurement[] = {"g","kg","ml","l","units"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);

        ProductName = findViewById(R.id.ProductName);
        ProductPrice = findViewById(R.id.ProductPrice);
        ProductStock = findViewById(R.id.ProductStock);
        Measurement = findViewById(R.id.Measurement);
        ImgSelect = findViewById(R.id.ImgSelect);
        ImgPreview = findViewById(R.id.ImgPreview);
        ProductDescription = findViewById(R.id.Description);
        AddGrocery = findViewById(R.id.AddGrocery);
        Cancel = findViewById(R.id.Cancel);

        ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,measurement);
        Measurement.setAdapter(ad);

        ImgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(ii,121);
            }
        });

        AddGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String GroceryName = ProductName.getText().toString();
                int GroceryPrice = Integer.parseInt(ProductPrice.getText().toString());
                int Quantity = Integer.parseInt(ProductStock.getText().toString());
                String Unit = Measurement.getSelectedItem().toString();
                String Description = ProductDescription.getText().toString();

                dbRef = FirebaseDatabase.getInstance().getReference("Grocery");
                pStore = FirebaseStorage.getInstance().getReferenceFromUrl("gs://smallbasket-fe217.appspot.com/images");

                gid = dbRef.push().getKey();
                mStore = pStore.child(GroceryName);
                // creates image with given name

                mStore.putFile(imgPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mStore.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // uri contains the reference of the created image
                                Grocery grocery = new Grocery(uri.toString(), GroceryName, Description, Unit, gid, Quantity, GroceryPrice);
                                dbRef.child(gid).setValue(grocery);

                                // Clear input fields and image preview
                                ProductName.setText("");
                                ProductPrice.setText("");
                                ProductDescription.setText("");
                                ProductStock.setText("");
                                ImgPreview.setImageURI(null);  // Clears the ImageView
                                imgPath = null;  // Reset the imgPath

                                Toast.makeText(AddProduct.this, "Grocery Added Successfully...", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddProduct.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 121 && resultCode == RESULT_OK){
            imgPath = data.getData();
            ImgPreview.setImageURI(imgPath);
        }
    }
}