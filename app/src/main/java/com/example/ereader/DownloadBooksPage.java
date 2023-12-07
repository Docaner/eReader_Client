package com.example.ereader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ereader.LocalDb.MyDbManager;

import java.util.List;

public class DownloadBooksPage extends AppCompatActivity {
    private Toolbar toolbar;
    private MyDbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_books_page);


        //toolbar
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Page");

        dbManager = new MyDbManager(this);
        dbManager.openDb();

        List<BookDownload> listDownloadBooks = dbManager.getFromDb();
        RecyclerView.Adapter adapter = new BookAdapterDownload(this,listDownloadBooks);

        //RecyclerView
        RecyclerView mRecycleView;
        mRecycleView = (RecyclerView) findViewById(R.id.recyclerview_download_books);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(adapter);
        dbManager.closeDb();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    //Реакция на меню в toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Выбор item'а
        Intent intent= new Intent(this, MainPage.class);
        int id = item.getItemId();
        if (id==R.id.main_book) {//главный список
            intent = new Intent(this, MainPage.class);
        }
        if (id==R.id.library) {//список скачанных книг
            intent = new Intent(this, DownloadBooksPage.class);
        }
        if (id==R.id.exit_to_app) {// к логину и паролю
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        return true;
    }
}