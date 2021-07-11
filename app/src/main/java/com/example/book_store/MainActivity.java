package com.example.book_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_store.Activities.Cart;
import com.example.book_store.Activities.login;
import com.example.book_store.Activities.order_history;
import com.example.book_store.Activities.search;
import com.example.book_store.Adapters.BookCategoryAdapter;
import com.example.book_store.Interface.FirebaseLoadListner;
import com.example.book_store.Modal.BookCategoryGroup;
import com.example.book_store.Modal.BookData;
import com.example.book_store.Modal.Orders;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FirebaseLoadListner, NavigationView.OnNavigationItemSelectedListener {

    FirebaseLoadListner firebaseLoadListner;
    RecyclerView recyclerView;
    DatabaseReference category;
    AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    TextView email;

    public static List<BookCategoryGroup> bookCategoryGroups;
    //for navigation drawer
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=this.getSharedPreferences("com.example.book_store",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("first",true).apply();
        drawerLayout=findViewById(R.id.drawer_layout);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //init
        category= FirebaseDatabase.getInstance().getReference("Category");
        firebaseLoadListner=this;
        recyclerView=findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View header_view=navigationView.getHeaderView(0);
        email = (TextView) header_view.findViewById(R.id.user_email);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //load data
        getFirebaseData();
    }

    private void getFirebaseData() {
        show_loading_dialog();
        category.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookCategoryGroups= new ArrayList<>();
                for(DataSnapshot groupSnapshot:snapshot.getChildren())
                {
                    ArrayList<BookData> bookData_arr= new ArrayList<>();
                    BookCategoryGroup bookCategoryGroup= new BookCategoryGroup();
                    bookCategoryGroup.setTitle(groupSnapshot.child("Title").getValue().toString());

                    //getting the list of books data for a particular category
                    FirebaseDatabase database2=FirebaseDatabase.getInstance();
                    DatabaseReference reference2=database2.getReference("Category/"+groupSnapshot.getKey()+"/list_items");
                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot groupbooksnapshot:snapshot.getChildren())
                            {
                                bookData_arr.add(groupbooksnapshot.getValue(BookData.class));
                            }
                            if(!sharedPreferences.getBoolean("first",false))
                            {
                                bookCategoryGroup.setList_items(bookData_arr);
                                firebaseLoadListner.onFirebaseLoadSuccess(bookCategoryGroups);
                            }
                            else {
                                bookCategoryGroup.setList_items(bookData_arr);
                                bookCategoryGroups.add(bookCategoryGroup);
                                firebaseLoadListner.onFirebaseLoadSuccess(bookCategoryGroups);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                firebaseLoadListner.onFirebaseLoadFailure(error.getMessage());
            }
        });

    }


    @Override
    public void onFirebaseLoadSuccess(List<BookCategoryGroup> bookCategoryGroups) {
        BookCategoryAdapter bookCategoryAdapter= new BookCategoryAdapter(bookCategoryGroups,this);
        recyclerView.setAdapter(bookCategoryAdapter);
        new CountDownTimer(5000,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                dismiss_loading_dialog();
                sharedPreferences.edit().putBoolean("first",false).apply();
            }
        }.start();
    }

    @Override
    public void onFirebaseLoadFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.nav_cart:
                intent= new Intent(getApplicationContext(),Cart.class);
                startActivity(intent);
                break;
            case R.id.nav_search:
                intent = new Intent(getApplicationContext(), search.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                SharedPreferences sharedPreferences= this.getSharedPreferences("com.example.book_store",MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("logged_in",false).apply();
                intent= new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                break;
            case R.id.nav_order:
                intent= new Intent(getApplicationContext(), order_history.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}