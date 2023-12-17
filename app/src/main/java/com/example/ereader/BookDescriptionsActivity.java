package com.example.ereader;


import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ereader.LocalDb.MyDbManager;

import java.io.File;
import java.util.jar.Attributes;
import java.util.logging.Handler;


public class BookDescriptionsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MyDbManager dbManager;
    private BookDownload dbk;
    private  String xe;
    private  String x;
    private Book book;
    private String[] link=new String[10];
    Button downloadUrl;
    private int exist;
    @Override
    protected void onCreate(@Nullable Bundle saved){
        super.onCreate(saved);
        dbManager = new MyDbManager(this);



        //Подтягивание данных из списка книг
        setContentView(R.layout.activity_book_description);
        Book bk =new Book();

        Bundle arguments = getIntent().getExtras();

        book = new Book();
        link[0]=null;
        link[1]="https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1DLNWmUc8RLJ0LbzZmVTjWe3y3g8xLWBG";
        link[2]="https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1p05bN62wkOruWmXlJVLblFRYjofPjBCa";
        link[3]="https://drive.google.com/uc?export=download&confirm=no_antivirus&id=18IN6OvRalVKaNyjYvhQouOFBd7sstaCW";
        link[4]="https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1czbxX4bYfhnor8WdBpVlbtMtd6N9EDov";
        link[5]="https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1qjTpKAfATPzOcJRLvNeZPElFXuaEfOm9";

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
    public  void  StartRead(View v) throws InterruptedException {Intent intent;
        if(exist==1) {
            intent = new Intent(this, ReadActivity.class);
            intent.putExtra("Name", xe);
            intent.putExtra("Status", x);
            startActivity(intent);
        }else
        {
            String getUrl = link[book.getId()];


            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getUrl));
            String title = URLUtil.guessFileName(getUrl,null,null);
            title= xe+".txt";
            request.setTitle(title);
            request.setDescription("Идет загрузка....");
            String cookie = CookieManager.getInstance().getCookie(getUrl);
            request.addRequestHeader("cookie",cookie);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);

            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            File pathDownload = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));

            File file = new File(pathDownload, xe+".txt");
            if(file.exists()) {
                intent = new Intent(this, CerverActivity1.class);
                intent.putExtra("Name", xe);
                intent.putExtra("Status", x);
                startActivity(intent);


                //Toast.makeText(getApplicationContext(), "Книга скачена", Toast.LENGTH_LONG).show();

            }
            else {
                for(int i=0;i<5;i++) {
                    Thread.sleep(3000);
                if(file.exists()){
                    intent = new Intent(this, CerverActivity1.class);
                    intent.putExtra("Name", xe);
                    intent.putExtra("Status", x);
                    startActivity(intent);break;}}
                    if(file.exists()){
                        intent = new Intent(this, CerverActivity1.class);
                        intent.putExtra("Name", xe);
                        intent.putExtra("Status", x);
                        startActivity(intent);
                }else {Toast.makeText(getApplicationContext(), "Книга не доступна", Toast.LENGTH_LONG).show();file.delete();}}


        }




    }



    //Кнопка скачивания
    public void Download(View v) throws InterruptedException {
        //Сюда вставиьт изменение пути для Path
        //Сюда вставить изменение прогресса для progress
        if (exist == 1){//Если книга есть в БД
            dbManager.deleteBook(dbk.author, dbk.name);
            Button bt = findViewById(R.id.button_download);
            bt.setText("Скачать");
            File pathDownload = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));

            File file = new File(pathDownload, xe+".txt");

            for(int i=0;5>i;i++) {file.delete();}

            exist=0;
            Toast.makeText(getApplicationContext(), "Книга удалена", Toast.LENGTH_LONG).show();
        }
        else {
            dbManager.insertToDb(dbk.author, dbk.name, dbk.description, dbk.rating, dbk.progress, dbk.path);
            Button bt = findViewById(R.id.button_download);
            String getUrl = link[book.getId()];


            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getUrl));
            String title = URLUtil.guessFileName(getUrl,null,null);
            title= xe+".txt";
            request.setTitle(title);
            request.setDescription("Идет загрузка....");
            String cookie = CookieManager.getInstance().getCookie(getUrl);
            request.addRequestHeader("cookie",cookie);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);

            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);

            //Toast.makeText(BookDescriptionsActivity.this,"Файл скачен",Toast.LENGTH_SHORT).show();
            File pathDownload = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));

            File file = new File(pathDownload, xe+".txt");
            if(file.exists()) {
                bt.setText("Удалить");
                exist = 1;

                Toast.makeText(getApplicationContext(), "Книга скачена", Toast.LENGTH_LONG).show();
            }
            else {
                for(int i=0;i<5;i++) {
                    Thread.sleep(3000);
                    if (file.exists()) {
                        bt.setText("Удалить");
                        exist = 1;
                        Toast.makeText(getApplicationContext(), "Книга скачена", Toast.LENGTH_LONG).show();
                   break; }
                }
                    if(file.exists()) {
                        bt.setText("Удалить");
                        exist = 1;
                Toast.makeText(getApplicationContext(), "Книга скачена", Toast.LENGTH_LONG).show();
            }else {Toast.makeText(getApplicationContext(), "Книга не скачена", Toast.LENGTH_LONG).show();file.delete();}}
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
