package com.example.book_store.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.book_store.Adapters.OrderDetailAdapter;
import com.example.book_store.Adapters.OrderHistoryAdapter;
import com.example.book_store.R;

public class order_detail extends AppCompatActivity {

    RecyclerView recyclerView;
    OrderDetailAdapter orderDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        recyclerView=findViewById(R.id.order_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderDetailAdapter= new OrderDetailAdapter(this,OrderHistoryAdapter.bookData);
        recyclerView.setAdapter(orderDetailAdapter);
    }
}