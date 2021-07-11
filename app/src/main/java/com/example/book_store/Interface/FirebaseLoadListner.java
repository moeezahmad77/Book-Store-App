package com.example.book_store.Interface;

import com.example.book_store.Modal.BookCategoryGroup;

import java.util.List;

public interface FirebaseLoadListner {
    void onFirebaseLoadSuccess(List<BookCategoryGroup> bookCategoryGroups);
    void onFirebaseLoadFailure(String message);
}
