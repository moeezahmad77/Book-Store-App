package com.example.book_store.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.Interface.Cart;
import com.example.book_store.Modal.BookData;
import com.example.book_store.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    SharedPreferences sharedPreferences;
    Gson gson= new Gson();
    ArrayList<BookData> bookData;
    Context context;

    public CartAdapter(ArrayList<BookData> bookData, Context context) {
        this.bookData = bookData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cart_view,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.book_name.setText(bookData.get(position).getBookName());
        holder.price.setText("Price: "+bookData.get(position).getPrice());
        Picasso.get().load(bookData.get(position).getCover_url()).into(holder.cover);
        holder.icon.setImageResource(R.drawable.ic_baseline_delete_24);
        holder.setCart(new Cart() {
            @Override
            public void onCartDeleteListner(int position) {
                sharedPreferences=context.getSharedPreferences("com.example.book_store",Context.MODE_PRIVATE);
                com.example.book_store.Activities.Cart.bookData_arr.remove(position);
                com.example.book_store.Activities.Cart.cartAdapter.notifyDataSetChanged();
                com.example.book_store.Activities.Cart.databaseReference.setValue(com.example.book_store.Activities.Cart.bookData_arr);
                if(com.example.book_store.Activities.Cart.bookData_arr.isEmpty())
                {
                    //set shared preferences empty
                    sharedPreferences.edit().putString("cart_items",null).apply();
                    Toast.makeText(context,"The cart is empty",Toast.LENGTH_LONG).show();
                    com.example.book_store.Activities.Cart.constraintLayout.setVisibility(View.INVISIBLE);
                }
                else
                {
                    String json=gson.toJson(com.example.book_store.Activities.Cart.bookData_arr);
                    sharedPreferences.edit().putString("cart_items",json).apply();
                    ArrayList<Integer> individual_prices= new ArrayList<>();
                    for(int i=0;i< com.example.book_store.Activities.Cart.bookData_arr.size();
                    i++)
                    {
                        individual_prices.add(Integer.parseInt(com.example.book_store.Activities.Cart
                                .bookData_arr.get(i).getPrice()));
                    }
                    int total=0;
                    for(int i=0;i<individual_prices.size();i++)
                    {
                        total+=individual_prices.get(i);
                    }
                    com.example.book_store.Activities.Cart.total_books_price=total;
                    com.example.book_store.Activities.Cart.total_price.
                            setText("Total Price: "+String.valueOf(total)+" Rs");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (bookData!=null?bookData.size():0);
    }

    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cover,icon;
        TextView book_name, price;
        Cart cart;

        public void setCart(Cart cart) {
            this.cart = cart;
        }

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cover=itemView.findViewById(R.id.cover_img);
            icon=itemView.findViewById(R.id.delete_icon);
            book_name=itemView.findViewById(R.id.bookname);
            price=itemView.findViewById(R.id.bookprice);
            icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cart.onCartDeleteListner(getAdapterPosition());
        }
    }


}
