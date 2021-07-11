package com.example.book_store.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.Modal.BookCategoryGroup;
import com.example.book_store.Modal.BookData;
import com.example.book_store.R;

import java.util.List;

public class BookCategoryAdapter extends RecyclerView.Adapter<BookCategoryAdapter.BookCategoryViewHolder> {

    private List<BookCategoryGroup> datalist;
    private Context context;

    public BookCategoryAdapter(List<BookCategoryGroup> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public BookCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.layout_group,parent,false);
        return new BookCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCategoryViewHolder holder, int position) {
        holder.category_title.setText(datalist.get(position).getTitle());
        List<BookData> bookData=datalist.get(position).getList_items();
        BookDataAdapter bookDataAdapter= new BookDataAdapter(bookData,context);
        holder.book_list.setLayoutManager
                (new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.book_list.setAdapter(bookDataAdapter);
        //holder.book_list.setNestedScrollingEnabled(false);
        /*holder.btnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"More Button Clicked",Toast.LENGTH_LONG).show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return (datalist!=null?datalist.size():0);
    }

    public class BookCategoryViewHolder extends RecyclerView.ViewHolder{
        TextView category_title;
        RecyclerView book_list;
        //Button btnmore;
        public BookCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category_title=itemView.findViewById(R.id.title);
            //btnmore=itemView.findViewById(R.id.btn_more);
            book_list=itemView.findViewById(R.id.recycler_view_list);
        }
    }

}
