package com.example.book_store.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_store.Adapters.CartAdapter;
import com.example.book_store.Modal.BookData;
import com.example.book_store.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    public static ArrayList<BookData> bookData_arr;
    public static int total_books_price;
    AlertDialog alertDialog;

    RecyclerView recyclerView;
    public static CartAdapter cartAdapter;
    public static TextView total_price;
    public static ConstraintLayout constraintLayout;

    //Firebase-database
    public static DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //initialize views
        init();
        constraintLayout.setVisibility(View.INVISIBLE);
        show_loading_dialog();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<BookData>> genericTypeIndicator= new GenericTypeIndicator<ArrayList<BookData>>() {};
                bookData_arr=snapshot.getValue(genericTypeIndicator);
                if(bookData_arr!=null)
                {
                    constraintLayout.setVisibility(View.VISIBLE);
                    //show the data in the recycler view through adapter
                    cartAdapter= new CartAdapter(bookData_arr,getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(cartAdapter);
                    calculate_total_price();
                }
                else
                {
                    constraintLayout.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"The cart is empty",Toast.LENGTH_LONG).show();
                }
                dismiss_loading_dialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void init()
    {
        recyclerView=findViewById(R.id.cart_recyclerview);
        total_price=findViewById(R.id.total_price);
        constraintLayout=findViewById(R.id.cart);
        databaseReference=FirebaseDatabase.getInstance().getReference("Cart/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    private void calculate_total_price()
    {
        ArrayList<Integer> individual_book_prices= new ArrayList<>();
        for(int i=0;i<bookData_arr.size();i++)
        {
            individual_book_prices.add(Integer.parseInt(bookData_arr.get(i).getPrice()));
        }
        int total_amount=0;
        for(int i=0;i<individual_book_prices.size();i++)
        {
            total_amount+=individual_book_prices.get(i);
        }
        total_price.setText("Total amount: "+String.valueOf(total_amount)+" Rs");
    }

    public void confirm_purchase(View view)
    {
        Intent intent= new Intent(this,place_order.class);
        startActivity(intent);
    }
    private void show_loading_dialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        LayoutInflater layoutInflater= this.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.loading_dialog,null));
        builder.setCancelable(false);
        alertDialog=builder.create();
        alertDialog.show();
    }
    private void dismiss_loading_dialog() {
        alertDialog.dismiss();
    }


}