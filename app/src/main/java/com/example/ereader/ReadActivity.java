package com.example.ereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Read");
        try {
            PlayWithRawFiles();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void PlayWithRawFiles() throws IOException {
        ArrayList<String> data_base = new ArrayList<String>();
        String text = "";
        StringBuffer buf = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("g.txt"); //save this .txt under src/main/assets/g.txt
            int size = is.available();
            buf = new StringBuffer();
            byte[] buffer = new byte[size];
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            if (is != null) {
                while ((text = reader.readLine()) != null) {
                    data_base.add(text.toString()); //create your own arraylist variable to hold each line being read from g.txt
                }
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView tv = (TextView) findViewById(R.id.book_text);
        tv.setText(buf.toString());


    }

    //Вывод меню в toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.readmenu,menu);
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
        if (id==R.id.settings) {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        return true;
    }
}
