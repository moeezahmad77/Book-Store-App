package com.example.book_store.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.example.book_store.Adapters.SearchAdapter;
import com.example.book_store.MainActivity;
import com.example.book_store.Modal.BookCategoryGroup;
import com.example.book_store.Modal.BookData;
import com.example.book_store.R;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {

    EditText search_book;
    SearchAdapter searchAdapter;
    RecyclerView recyclerView;
    ArrayList<BookData> bookData;
    List<BookCategoryGroup> bookCategoryGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //initializing
        search_book=findViewById(R.id.search_text_view);
        recyclerView=findViewById(R.id.search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookData= new ArrayList<>();
        searchAdapter= new SearchAdapter(this,bookData);
        bookCategoryGroups=MainActivity.bookCategoryGroups;
        recyclerView.setAdapter(searchAdapter);

        search_book.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
                if(TextUtils.isEmpty(search_book.getText()))
                {
                    bookData.clear();
                    searchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filter(String searched_txt)
    {
        bookData.clear();
        searchAdapter.notifyDataSetChanged();
        for(int i=0;i<bookCategoryGroups.size();i++)
        {
            for(int y=0;y<bookCategoryGroups.get(i).getList_items().size();y++)
            {
                if(bookCategoryGroups.get(i).getList_items().get(y).getBookName().toLowerCase().contains(searched_txt.toLowerCase()))
                {
                    bookData.add(bookCategoryGroups.get(i).getList_items().get(y));
                }
            }
        }
        searchAdapter.notifyDataSetChanged();
    }

}