package com.example.book_store.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public class book_detail_activity extends AppCompatActivity {

    //rewriting the code to save cart items in firebase databse
    ImageView cover;
    TextView bookname, authorname, price, description;
    Button add_to_cart;
    ArrayList<BookData> bookData_array;
    BookData bookData;
    AlertDialog alertDialog;

    //Firebase
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_activity);
        init();
        Intent intent = getIntent();
        String book_nm = intent.getStringExtra("bookname");
        show_loading_dialog();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<BookData>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<BookData>>() {
                };
                bookData_array = snapshot.getValue(genericTypeIndicator);
                if (bookData_array == null) {
                    bookData_array = new ArrayList<>();
                } else if (bookData_array != null) {
                    //Toast.makeText(this,book_nm,Toast.LENGTH_LONG).show();
                    String check;
                    for (int i = 0; i < bookData_array.size(); i++) {
                        check = bookData_array.get(i).getBookName();
                        if (check.equals(book_nm)) {
                            add_to_cart.setEnabled(false);
                            add_to_cart.setText("Already in cart");
                            break;
                        }
                    }
                }
                dismiss_loading_dialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Picasso.get().load(intent.getStringExtra("cover_url")).into(cover);
        bookname.setText("Book Name: " + intent.getStringExtra("bookname"));
        authorname.setText("Author Name: " + intent.getStringExtra("authorname"));
        price.setText("Price: " + intent.getStringExtra("price"));
        description.setText(intent.getStringExtra("description"));

        bookData = new BookData(intent.getStringExtra("authorname"),
                intent.getStringExtra("bookname"),
                intent.getStringExtra("description"),
                intent.getStringExtra("cover_url"),
                intent.getStringExtra("price"));
    }

    private void init() {
        cover = findViewById(R.id.cover);
        bookname = findViewById(R.id.bookname);
        authorname = findViewById(R.id.author_name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        add_to_cart = findViewById(R.id.addtocart);
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public void add_to_cart(View view) {
        bookData_array.add(bookData);
        add_to_cart.setEnabled(false);
        add_to_cart.setText("Added in cart");
        databaseReference.setValue(bookData_array);
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