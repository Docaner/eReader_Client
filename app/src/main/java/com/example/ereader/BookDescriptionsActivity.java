package com.example.ereader;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ereader.LocalDb.MyDbManager;

import java.util.jar.Attributes;
import java.util.logging.Handler;


public class BookDescriptionsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MyDbManager dbManager;
    private BookDownload dbk;
    private  String xe;
    private  String x;
    DownloadManager manager;
    private int exist;
    @Override
    protected void onCreate(@Nullable Bundle saved){
        super.onCreate(saved);
        dbManager = new MyDbManager(this);

        //Подтягивание данных из списка книг
        setContentView(R.layout.activity_book_description);
        Book bk =new Book();

        Bundle arguments = getIntent().getExtras();

        Book book = new Book();
        int status = arguments.getInt("status");

        if (status == 0){
            int positions = getIntent().getIntExtra("positions", 0);
            x= String.valueOf(status);
            book = BooksCollection.getBooksCollections().get(positions,bk);

            TextView textView1 = findViewById(R.id.textView);//оценка
            textView1.setText(book.getRating().toString());
            TextView textView2 =findViewById(R.id.textView2);//автор
            textView2.setText(book.getAuthor());
            TextView textView3 =findViewById(R.id.textView3);//описание
            textView3.setText(book.getDescription());
            TextView textView4 =findViewById(R.id.textView4);//название
            textView4.setText(book.getName());
            xe=book.getName();
            ImageView imageView1 = findViewById(R.id.imageView);
            imageView1.setImageResource(book.getImage());
            exist = dbManager.searchAuthorName(book.author,book.name);
            dbk = new BookDownload(book);
        }
        if (status == 1){
            String author = arguments.getString("author");
            String name = arguments.getString("name");
            x= String.valueOf(status);
            dbk = dbManager.getOneBook(author,name);

            TextView textView1 = findViewById(R.id.textView);//оценка
            textView1.setText(dbk.getRating().toString());
            TextView textView2 =findViewById(R.id.textView2);//автор
            textView2.setText(dbk.getAuthor());
            TextView textView3 =findViewById(R.id.textView3);//описание
            textView3.setText(dbk.getDescription());
            TextView textView4 =findViewById(R.id.textView4);//название
            textView4.setText(dbk.getName());
            xe=dbk.getName();
            ImageView imageView1 = findViewById(R.id.imageView);
            imageView1.setImageResource(R.drawable.default_book);
            exist = dbManager.searchAuthorName(dbk.author,dbk.name);
        }

        if (exist ==1){//Если книга есть в БД
            Button bt =findViewById(R.id.button_download);
            bt.setText("Удалить");
            x= String.valueOf(exist);
        }

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(xe);
    }

    //Кнопка чтения
    public  void  StartRead(View v)
    {
        Intent intent = new Intent(this, ReadActivity.class);
        intent.putExtra("Name",xe);
        intent.putExtra("Status",x);
        startActivity(intent);
    }


    //Кнопка скачивания
    public void Download(View v){
        //Сюда вставиьт изменение пути для Path
        //Сюда вставить изменение прогресса для progress
        if (exist == 1){//Если книга есть в БД
            dbManager.deleteBook(dbk.author, dbk.name);
            Button bt = findViewById(R.id.button_download);
            new CountDownTimer(3000, 1000) {
                public void onFinish() {
                    bt.setText("Скачать");

                    exist=0;
                    Toast.makeText(getApplicationContext(), "Книга удалена", Toast.LENGTH_LONG).show();
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();

        }
        else {
            dbManager.insertToDb(dbk.author, dbk.name, dbk.description, dbk.rating, dbk.progress, dbk.path);
            Button bt = findViewById(R.id.button_download);
            new CountDownTimer(5000, 1000) {
                public void onFinish() {
                    manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse("https://all-the-books.ru/download_book/00ae0503fb38bd4900ae27731912fde3/");
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    long reference = manager.enqueue(request);
                    bt.setText("Удалить");

                    exist=1;
                    Toast.makeText(getApplicationContext(), "Книга скачена", Toast.LENGTH_LONG).show();
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();


        }
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
