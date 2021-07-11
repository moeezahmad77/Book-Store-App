package com.example.book_store.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.Modal.BookData;
import com.example.book_store.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {

    Context context;
    ArrayList<BookData> bookData;

    public OrderDetailAdapter(Context context, ArrayList<BookData> bookData) {
        this.context = context;
        this.bookData = bookData;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.searched_books,parent,false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        Picasso.get().load(bookData.get(position).getCover_url()).into(holder.cover);
        holder.name.setText(bookData.get(position).getBookName());
        holder.price.setText(bookData.get(position).getPrice()+" Rs");
    }

    @Override
    public int getItemCount() {
        return (bookData!=null?bookData.size():0);
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder
    {
        ImageView cover;
        TextView name,price;
        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            cover=itemView.findViewById(R.id.s_book_cover);
            name=itemView.findViewById(R.id.s_book_name);
            price=itemView.findViewById(R.id.s_book_price);
        }
    }

}
