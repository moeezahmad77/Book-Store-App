package com.example.book_store.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.Activities.book_detail_activity;
import com.example.book_store.Interface.BookClickListner;
import com.example.book_store.Modal.BookData;
import com.example.book_store.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchedBookViewHolder> {

    Context context;
    ArrayList<BookData> bookData;

    public SearchAdapter(Context context, ArrayList<BookData> bookData) {
        this.context = context;
        this.bookData = bookData;
    }

    @NonNull
    @Override
    public SearchedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.searched_books,parent,false);
        return new SearchedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedBookViewHolder holder, int position) {
        Picasso.get().load(bookData.get(position).getCover_url()).into(holder.s_imgae);
        holder.s_book_name.setText(bookData.get(position).getBookName());
        holder.s_book_price.setText("Price: "+bookData.get(position).getPrice()+" Rs");
        holder.setBookClickListner(new BookClickListner() {
            @Override
            public void onBookClickListner(int position) {
                Intent intent= new Intent(context, book_detail_activity.class);
                intent.putExtra("cover_url",bookData.get(position).getCover_url());
                intent.putExtra("bookname",bookData.get(position).getBookName());
                intent.putExtra("authorname",bookData.get(position).getAuthorName());
                intent.putExtra("price",bookData.get(position).getPrice());
                intent.putExtra("description",bookData.get(position).getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (bookData!=null?bookData.size():0);
    }

    public class SearchedBookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView s_imgae;
        TextView s_book_name, s_book_price;
        BookClickListner bookClickListner;

        public void setBookClickListner(BookClickListner bookClickListner) {
            this.bookClickListner = bookClickListner;
        }
        public SearchedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            s_imgae = itemView.findViewById(R.id.s_book_cover);
            s_book_name = itemView.findViewById(R.id.s_book_name);
            s_book_price = itemView.findViewById(R.id.s_book_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            bookClickListner.onBookClickListner(getAdapterPosition());
        }
    }
}
