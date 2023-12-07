package com.example.ereader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ereader.LocalDb.MyDbManager;

public class BookDescriptionsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MyDbManager dbManager;
    private BookDownload dbk;

    @Override
    protected void onCreate(@Nullable Bundle saved){
        super.onCreate(saved);

        //Подтягивание данных из списка книг
        setContentView(R.layout.activity_book_description);
        Book bk =new Book();
        int positions = getIntent().getIntExtra("positions",0);
        Book book = BooksCollection.getBooksCollections().get(positions,bk);
        dbk = new BookDownload(book);

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


        dbManager = new MyDbManager(this);


        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Page");
    }
    public  void  StartRead(View v)
    {
        Intent intent = new Intent(this, ReadActivity.class);
        startActivity(intent);
    }
    //Кнопка скачивания
    public void Download(View v){
        //Сюда вставиьт изменение пути для Path
        //Сюда вставить изменение прогресса для progress
        dbManager.insertToDb(dbk.author,dbk.name,dbk.description,dbk.rating,dbk.progress,dbk.path);
    }
    @Override
    protected void onResume(){
        super.onResume();
        dbManager.openDb();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        dbManager.closeDb();
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
