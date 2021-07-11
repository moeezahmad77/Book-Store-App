package com.example.book_store.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.Activities.order_detail;
import com.example.book_store.Activities.search;
import com.example.book_store.Interface.ItemClickListner;
import com.example.book_store.Modal.BookData;
import com.example.book_store.Modal.Orders;
import com.example.book_store.R;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    public static ArrayList<BookData> bookData;
    Context context;
    ArrayList<Orders> orders;

    public OrderHistoryAdapter(Context context, ArrayList<Orders> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.order_history,parent,false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        holder.order_id.setText("ID: "+orders.get(position).getOrder_id());
        holder.total_amount.setText("Total Amount: "+orders.get(position).getTotal_amount()+" Rs");
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void itemClickListner(int position) {
                bookData= new ArrayList<>();
                bookData=orders.get(position).getOrdered_books();
                Intent intent= new Intent(context,order_detail.class);
                context.startActivity(intent);
                //Toast.makeText(context,orders.get(position).getOrder_id(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (orders!=null?orders.size():0);
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView order_id,total_amount;
        ItemClickListner itemClickListner;

        public void setItemClickListner(ItemClickListner itemClickListner) {
            this.itemClickListner = itemClickListner;
        }

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id=itemView.findViewById(R.id.order_id);
            total_amount=itemView.findViewById(R.id.total_amount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListner.itemClickListner(getAdapterPosition());
        }
    }

}
