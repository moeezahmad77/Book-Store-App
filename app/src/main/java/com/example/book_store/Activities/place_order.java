package com.example.book_store.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_store.MainActivity;
import com.example.book_store.Modal.Orders;
import com.example.book_store.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

public class place_order extends AppCompatActivity {

    EditText phone,address;
    LocationManager locationManager;
    LocationListener locationListener;
    String user_current_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        init();
    }

    private void init()
    {
        phone=findViewById(R.id.phone_no);
        address=(EditText) findViewById(R.id.address);
        user_current_address="";
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener= new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                user_current_address=update_location_info(location);
                address.setText(user_current_address);
            }
        };
    }


    public void find_user_current_location(View view)
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Getting location wait",Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,60000,50,locationListener);
        }

    }


    public void place_order(View view)
    {
        if(TextUtils.isEmpty(phone.getText())
        || TextUtils.isEmpty(address.getText()))
        {
            Toast.makeText(this,"Fields can not be empty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Orders orders= new Orders();
            orders.setTotal_amount(String.valueOf(Cart.total_books_price));
            orders.setMobile_no(phone.getText().toString());
            orders.setAddress(address.getText().toString());
            orders.setOrdered_books(Cart.bookData_arr);
            //firebase
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Orders/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            ref.push().setValue(orders);
            ref=FirebaseDatabase.getInstance().getReference("Cart/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            ref.removeValue();
            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private String update_location_info(Location location)
    {
        String address="";
        Geocoder geocoder= new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if(addresses!=null && addresses.size()>0)
            {
                if(addresses.get(0).getAddressLine(0)!=null)
                {
                    address+=addresses.get(0).getAddressLine(0)+"\n";
                }
                if(addresses.get(0).getPostalCode()!=null)
                {
                    address+="Postal Code: "+addresses.get(0).getPostalCode();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return address;
    }

}