package com.example.smallbasket;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.smallbasket.model.Grocery;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProceedAdapter extends ArrayAdapter<Grocery> {
    Context context;
    int design;
    ArrayList<Grocery> SelectedList;

    ProceedAdapter(Context context, int design, ArrayList<Grocery> SelectedList)
    {
        super(context,design,SelectedList);
        this.context = context;
        this.design = design;
        this.SelectedList = SelectedList;
        Toast.makeText(context," SIZE = "+SelectedList.size(),Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(design, null, false);

        TextView productName = view.findViewById(R.id.productName);
        TextView productQuantity = view.findViewById(R.id.productQuantity);
        TextView Unit = view.findViewById(R.id.Unit);
        TextView cost = view.findViewById(R.id.cost);
        TextView description = view.findViewById(R.id.description);
        TextView selectedNumber = view.findViewById(R.id.selectedNumber);
        TextView productPrice = view.findViewById(R.id.productPrice);
        ImageView productImg = view.findViewById(R.id.productImg);

        Grocery grocery = SelectedList.get(position);

        productName.setText(grocery.getProductName());
        productQuantity.setText(""+grocery.getProductStock());
        Unit.setText(grocery.getMeasurement());

        selectedNumber.setText(""+grocery.getOrder());
        productPrice.setText(""+(grocery.getProductPrice() * grocery.getOrder()));

//        Activity activity = (Activity) getContext();
        Glide.with(context).load(grocery.getImgUrl()).into(productImg);
        return view;

    }



}
