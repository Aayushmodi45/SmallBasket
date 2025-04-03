package com.example.smallbasket;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Locale;

public class SelectAddress extends Activity implements LocationListener {

    LocationManager Locmanager = null;
    Location loc = null;
    double lati = 0, longi = 0;
    boolean isGps = false, isNet = false;

    EditText etCustomerName,etCustomerAddress,etCustomerEmail,etCustomerMobileNo;
    Button btProceedToPayment;
    ImageButton btGetMyCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        btGetMyCurrentLocation=findViewById(R.id.btGetMyCurrentLocation);
        etCustomerAddress=findViewById(R.id.etCustomerAddress);
        etCustomerName=findViewById(R.id.etCustomerName);
        etCustomerEmail=findViewById(R.id.etCustomerEmail);
        etCustomerMobileNo=findViewById(R.id.etCustomerMobileNo);
        btProceedToPayment=findViewById(R.id.btProceedToPayment);
        Locmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isNet = Locmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGps = Locmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
////        String CustomerName = etCustomerName.getText().toString();
//        String CustomerAddress = etCustomerAddress.getText().toString();
//        String CustomerEmail = etCustomerEmail.getText().toString();
//        String CustomerMobieNo = etCustomerMobileNo.getText().toString();

        if (isNet || isGps) {
            if (isNet) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Locmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, SelectAddress.this);
                if (Locmanager != null) {
                    loc = Locmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (loc != null) {
                        lati = loc.getLatitude();
                        longi = loc.getLongitude();
                    }
                }
            }
            if (isGps) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, SelectAddress.this);
                if (Locmanager != null) {
                    loc = Locmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (loc != null) {
                        lati = loc.getLatitude();
                        longi = loc.getLongitude();
                    }
                }
            }

            try {
                Geocoder geo = new Geocoder(this, Locale.getDefault());
                List<Address> list = geo.getFromLocation(lati, longi, 1);
                Address addrr = list.get(0);


                btGetMyCurrentLocation.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        etCustomerAddress.setText(addrr.getAddressLine(0));
                    }
                });
            } catch (Exception e) {
            }
        }



            btProceedToPayment.setOnClickListener
                    (new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            String name=etCustomerName.getText().toString().trim();
                            String address=etCustomerAddress.getText().toString().trim();
                            String email=etCustomerEmail.getText().toString().trim();
                            String mobile=etCustomerMobileNo.getText().toString().trim();
                            if( name.isEmpty() || address.isEmpty() || email.isEmpty() || mobile.isEmpty() )
                            {
                                Toast.makeText(SelectAddress.this, "Fill Details", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String message = "Thank you for your order, " + name + ".\n\n" +
                                        "Your order has been successfully placed at Small Basket.\n" +
                                        "Total amount: " + ApplicationData.cartTotal + ".\n\n" +
                                        "Regards,\n" +
                                        "Small Basket";

                                try {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(mobile, null, message, null, null);
                                    Toast.makeText(SelectAddress.this, "SMS sent successfully to " + mobile, Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(SelectAddress.this, "Failed to send SMS. Please try again.", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

//                                Intent ii = new Intent(PaymentAddressFetch.this, PaymentMethod.class);
//                                startActivity(ii);
                            }
                        }
                    });

//           btProceedToPayment.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if(etCustomerName.getText().toString().isEmpty() | etCustomerEmail.getText().toString().isEmpty() | etCustomerAddress.getText().toString().isEmpty() | etCustomerMobileNo.getText().toString().isEmpty())
//                    {
//                        Toast.makeText(getApplicationContext(),"Please Enter all the Details ",Toast.LENGTH_LONG).show();
//                    }
//                    else {
//
//                        Intent email = new Intent(Intent.ACTION_SEND);
//                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{etCustomerEmail.getText().toString()});
//                        email.putExtra(Intent.EXTRA_SUBJECT, "Regarding Your Purchase from Smallbasket");
//                        email.putExtra(Intent.EXTRA_TEXT, "Your order rupees "+ ApplicationData.cartTotal +" is submitted thanks for visiting us");
//                        //need this to prompts email client only
//
//                    }
//                }
//            });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied. Unable to send SMS.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onLocationChanged(@NonNull Location location) {
//        lati = location.getLatitude();
//        longi = location.getLongitude();
//        try {
//            Geocoder geo = new Geocoder(this, Locale.getDefault());
//            List<Address> list = geo.getFromLocation(lati,longi,1);
//
//            Address addr = list.get(0);
//            etCustomerAddress.setText(addr.getAddressLine(0));
//        }
//        catch (Exception e)
//        {
//
//        }
    }


    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

