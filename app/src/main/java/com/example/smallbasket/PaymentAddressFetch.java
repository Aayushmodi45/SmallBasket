//package com.example.pizzaparadise;
//
//import static android.os.Build.VERSION_CODES.R;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.telephony.SmsManager;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.google.android.material.badge.BadgeUtils;
//
//import java.util.List;
//import java.util.Locale;
//
//public class PaymentAddressFetch extends Activity implements LocationListener
//{
//    boolean isGps = false, isNet = false;
//    LocationManager man = null;
//    Location loc = null;
//    double latitude = 0,longitude=0;
//    Button btCurrentLocation1,proceedToPay1,sendEmail;
//    EditText cAddress1,cFullName,cEmail,cMobileNumber;
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment_address_fetch);
//
//        cAddress1 = findViewById(R.id.cAddress);
//        btCurrentLocation1 = findViewById(R.id.btCurrentLocation);
//        proceedToPay1=findViewById(R.id.proceedToPay);
//        cFullName=findViewById(R.id.cFullName);
//        cEmail=findViewById(R.id.cEmail);
//        cMobileNumber=findViewById(R.id.cMobileNumber);
//
//
//
//
//        man = (LocationManager) getSystemService(LOCATION_SERVICE);
//        isNet = man.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        isGps = man.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        if (isNet || isGps)
//        {
//            if (isNet) {
//                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                man.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, this);
//                if (man != null) {
//                    loc = man.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                    if (loc != null) {
//                        latitude = loc.getLatitude();
//                        longitude = loc.getLongitude();
//                    }
//                }
//            }
//            if (isGps) {
//                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                man.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, this);
//                if (man != null) {
//                    loc = man.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                    if (loc != null) {
//                        latitude = loc.getLatitude();
//                        longitude = loc.getLongitude();
//
//                    }
//                }
//            }
//            try {
//                Geocoder geo = new Geocoder(this, Locale.getDefault());
//                List<Address> list = geo.getFromLocation(latitude, longitude, 1);
//                Address addrr = list.get(0);
//
//
//                btCurrentLocation1.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v) {
//                        cAddress1.setText(addrr.getAddressLine(0));
//                    }
//                });
//            } catch (Exception e) {
//            }
//        }
//
//        proceedToPay1.setOnClickListener
//                (new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        String name=cFullName.getText().toString().trim();
//                        String address=cAddress1.getText().toString().trim();
//                        String email=cEmail.getText().toString().trim();
//                        String mobile=cMobileNumber.getText().toString().trim();
//                        if( name.isEmpty() || address.isEmpty() || email.isEmpty() || mobile.isEmpty() )
//                        {
//                            Toast.makeText(PaymentAddressFetch.this, "Fill Details", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            String message = "Thank you for your order, " + name + ".\n\n" +
//                                    "Your order has been successfully placed at Pizza Paradise.\n" +
//                                    "Total amount: " + ApplicationData.cardValue + ".\n\n" +
//                                    "Regards,\n" +
//                                    "Pizza Paradise";
//
//                            try {
//                                SmsManager smsManager = SmsManager.getDefault();
//                                smsManager.sendTextMessage(mobile, null, message, null, null);
//                                Toast.makeText(PaymentAddressFetch.this, "SMS sent successfully to " + mobile, Toast.LENGTH_SHORT).show();
//                            } catch (Exception e) {
//                                Toast.makeText(PaymentAddressFetch.this, "Failed to send SMS. Please try again.", Toast.LENGTH_SHORT).show();
//                                e.printStackTrace();
//                            }
//
//                            Intent ii = new Intent(PaymentAddressFetch.this, PaymentMethod.class);
//                            startActivity(ii);
//                        }
//                    }
//                });
//
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                Toast.makeText(this, "SMS permission granted. Please try again.", Toast.LENGTH_SHORT).show();
//            } else {
////                Toast.makeText(this, "SMS permission denied. Unable to send SMS.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//
//    @Override
//    public void onLocationChanged(@NonNull Location location) {
//
//    }
//
//    @Override
//    public void onLocationChanged(@NonNull List<Location> locations) {
//        LocationListener.super.onLocationChanged(locations);
//    }
//
//    @Override
//    public void onFlushComplete(int requestCode) {
//        LocationListener.super.onFlushComplete(requestCode);
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        LocationListener.super.onStatusChanged(provider, status, extras);
//    }
//
//    @Override
//    public void onProviderEnabled(@NonNull String provider) {
//        LocationListener.super.onProviderEnabled(provider);
//    }
//
//    @Override
//    public void onProviderDisabled(@NonNull String provider) {
//        LocationListener.super.onProviderDisabled(provider);
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }
//}