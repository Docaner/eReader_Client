package com.example.ereader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPage extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView.Adapter adapter;

    public static  ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Главная страница");


        outBookList();
        //RecyclerView
        //RecyclerView recyclerView = findViewById(R.id.recyclerview_books);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(adapter);
    }

    private void outBookList() {

        RecyclerView recyclerView = findViewById(R.id.recyclerview_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Ожидание ответа...");
        dialog.show();

        Call<List<HttpBook>> call = apiInterface.getAllBooks();
        call.enqueue(new Callback<List<HttpBook>>() {
            @Override
            public void onResponse(Call<List<HttpBook>> call, Response<List<HttpBook>> response) {
                dialog.dismiss();

                List<HttpBook> httpBooks = response.body();
                List<Book> books = new ArrayList<>();
                for(int i = 0; i < httpBooks.size(); i++){
                    books.add(httpBooks.get(i).getBook());
                }

                adapter = new BookAdapter(MainPage.this, books);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<HttpBook>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MainPage.this, "Что-то пошло не так... Пожалуйста, попробуйте снова", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Вывод меню в toolbar
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