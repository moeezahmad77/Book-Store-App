package com.example.book_store.Modal;

import java.util.ArrayList;

public class BookCategoryGroup {
    private String Title;
    private ArrayList<BookData> list_items;

    public BookCategoryGroup() {
    }

    public BookCategoryGroup(String title, ArrayList<BookData> list_items) {
        Title = title;
        this.list_items = list_items;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<BookData> getList_items() {
        return list_items;
    }

    public void setList_items(ArrayList<BookData> list_items) {
        this.list_items = list_items;
    }
}
