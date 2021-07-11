package com.example.book_store.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.AudioRecord;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.book_store.Adapters.OrderHistoryAdapter;
import com.example.book_store.Interface.LoadListner;
import com.example.book_store.Modal.BookData;
import com.example.book_store.Modal.Orders;
import com.example.book_store.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class order_history extends AppCompatActivity implements LoadListner {

    RecyclerView recyclerView;
    OrderHistoryAdapter orderHistoryAdapter;
    DatabaseReference reference;
    ArrayList<Orders> orders;
    AlertDialog alertDialog;

    LoadListner loadListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        recyclerView=findViewById(R.id.order_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference= FirebaseDatabase.getInstance().getReference("Orders/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        orders= new ArrayList<>();
        loadListner=this;
        show_loading_dialog();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot groupSnapshot:snapshot.getChildren())
                {
                    Orders order= new Orders();
                    order.setOrder_id(groupSnapshot.getKey().toString());
                    order.setTotal_amount(groupSnapshot.child("total_amount").getValue().toString());
                    GenericTypeIndicator<ArrayList<BookData>> genericTypeIndicator= new GenericTypeIndicator<ArrayList<BookData>>() {};
                    order.setOrdered_books(groupSnapshot.child("ordered_books").getValue(genericTypeIndicator));
                    orders.add(order);
                }
                if(orders.isEmpty())
                {
                    dismiss_loading_dialog();
                    Toast.makeText(getApplicationContext(),"You have not placed any orders yet",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dismiss_loading_dialog();
                    loadListner.onFirebaseSuccess(orders);
                    /*orderHistoryAdapter= new OrderHistoryAdapter(getApplicationContext(),orders);
                    recyclerView.setAdapter(orderHistoryAdapter);*/
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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


    @Override
    public void onFirebaseSuccess(ArrayList<Orders> orders) {
        dismiss_loading_dialog();
        orderHistoryAdapter= new OrderHistoryAdapter(this,orders);
        recyclerView.setAdapter(orderHistoryAdapter);
    }

}