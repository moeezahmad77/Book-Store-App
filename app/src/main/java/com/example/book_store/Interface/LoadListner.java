package com.example.book_store.Interface;

import com.example.book_store.Modal.BookCategoryGroup;
import com.example.book_store.Modal.Orders;

import java.util.ArrayList;
import java.util.List;

public interface LoadListner {
    void onFirebaseSuccess(ArrayList<Orders> orders);
}
