package com.example.book_store.Modal;

import java.util.ArrayList;

public class Orders {
    String order_id,mobile_no,address,total_amount;
    ArrayList<BookData> ordered_books;

    public Orders() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public ArrayList<BookData> getOrdered_books() {
        return ordered_books;
    }

    public void setOrdered_books(ArrayList<BookData> ordered_books) {
        this.ordered_books = ordered_books;
    }
}
