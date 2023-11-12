package com.example.ereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BookDescriptionsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle saved){
        super.onCreate(saved);

        //Подтягивание данных из списка книг
        setContentView(R.layout.activity_book_description);

        int positions = getIntent().getIntExtra("positions",0);
        Book book = BooksCollection.getBooksCollections().get(positions);

        TextView textView1 = findViewById(R.id.textView);//оценка
        textView1.setText(book.getRating().toString());
        TextView textView2 =findViewById(R.id.textView2);//автор
        textView2.setText(book.getAuthor());
        TextView textView3 =findViewById(R.id.textView3);//описание
        textView3.setText(book.getDescription());
        TextView textView4 =findViewById(R.id.textView4);//название
        textView4.setText(book.getName());
        ImageView imageView1 = findViewById(R.id.imageView);
        imageView1.setImageResource(book.getImage());


        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Page");
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
        if (id==R.id.library) {
            intent = new Intent(this, Book_description.class);
        }
        if (id==R.id.main_book) {
            intent = new Intent(this, MainPage.class);
        }
        if (id==R.id.exit_to_app) {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        return true;
    }
}
