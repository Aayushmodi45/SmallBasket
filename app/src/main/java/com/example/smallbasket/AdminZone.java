package com.example.smallbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.DataSource;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

public class AdminZone extends Activity {
    Button btshow, btadd;
    TextView tvQuote;
    ImageView ivQuoteImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_zone);

        btshow = findViewById(R.id.btshow);
        btadd = findViewById(R.id.btadd);
        tvQuote = findViewById(R.id.tvQuote);
        ivQuoteImage = findViewById(R.id.ivQuoteImage);

        loadQuoteAndImage();

        btadd.setOnClickListener(v -> {
            Intent ii = new Intent(getApplicationContext(), AddProduct.class);
            startActivity(ii);
        });

        btshow.setOnClickListener(v -> {
            Intent ii = new Intent(getApplicationContext(), Showproduct.class);
            startActivity(ii);
        });
    }

    private void loadQuoteAndImage() {
        String quoteUrl = "https://zenquotes.io/api/quotes";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, quoteUrl, null,
                response -> {
                    try {
                        JSONObject quoteObj = response.getJSONObject(0);
                        String quote = quoteObj.getString("q");
                        String author = quoteObj.getString("a");

                        tvQuote.setText("\"" + quote + "\"\n\n- " + author);

                        // Set the static image from drawable
                        ivQuoteImage.setImageResource(R.drawable.productivity_illustration);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        tvQuote.setText("Failed to parse quote");
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Quote API failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    tvQuote.setText("Could not load quote");

                    // Set the image even if quote loading fails
                    ivQuoteImage.setImageResource(R.drawable.productivity_illustration);
                });

        queue.add(jsonRequest);
    }
}
