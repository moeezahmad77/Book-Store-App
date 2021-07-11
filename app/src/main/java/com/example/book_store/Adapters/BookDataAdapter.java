package com.example.book_store.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.Interface.BookClickListner;
import com.example.book_store.Modal.BookData;
import com.example.book_store.R;
import com.example.book_store.Activities.book_detail_activity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BookDataAdapter extends RecyclerView.Adapter<BookDataAdapter.BookDataViewHolder> {

    private List<BookData> bookDatalist;
    private Context context;

    public BookDataAdapter(List<BookData> bookDatalist, Context context) {
        this.bookDatalist = bookDatalist;
        this.context = context;
    }

    @NonNull
    @Override
    public BookDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.layout_book_item,parent,false);
        return new BookDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookDataViewHolder holder, int position) {
        holder.bookname.setText(bookDatalist.get(position).getBookName());
        holder.price.setText("Price: "+String.valueOf(bookDatalist.get(position).getPrice())+" Rs");
        Picasso.get().load(bookDatalist.get(position).getCover_url()).into(holder.cover);

        //implementing item click listner
        holder.setBookClickListner(new BookClickListner() {
            @Override
            public void onBookClickListner(int position) {
                //Toast.makeText(context,bookDatalist.get(position).getBookName(),Toast.LENGTH_LONG).show();
                //new Intent
                Intent intent= new Intent(context, book_detail_activity.class);
                intent.putExtra("cover_url",bookDatalist.get(position).getCover_url());
                intent.putExtra("bookname",bookDatalist.get(position).getBookName());
                intent.putExtra("authorname",bookDatalist.get(position).getAuthorName());
                intent.putExtra("price",bookDatalist.get(position).getPrice());
                intent.putExtra("description",bookDatalist.get(position).getDescription());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (bookDatalist!=null?bookDatalist.size():0);
    }


    public class BookDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cover;
        TextView bookname,price;

        BookClickListner bookClickListner;

        public void setBookClickListner(BookClickListner bookClickListner) {
            this.bookClickListner = bookClickListner;
        }

        public BookDataViewHolder(@NonNull View itemView) {
            super(itemView);
            cover=itemView.findViewById(R.id.book_cover);
            bookname=itemView.findViewById(R.id.book_name);
            price=itemView.findViewById(R.id.price);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
         bookClickListner.onBookClickListner(getAdapterPosition());
        }
    }
}
