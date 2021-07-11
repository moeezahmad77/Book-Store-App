package com.example.book_store.Modal;

public class BookData {
    private String AuthorName,BookName,Description,cover_url,Price;
    //private int Price;

    public BookData() {
    }

    public BookData(String authorName, String bookName, String description, String cover_url, String price) {
        AuthorName = authorName;
        BookName = bookName;
        Description = description;
        this.cover_url = cover_url;
        Price = price;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
