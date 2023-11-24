package com.example.ereader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private StringBuilder text = new StringBuilder();
    private int a;
    private  TextView mTextStatus;
    private TextView textView;;
    private ScrollView  mScrollView;
    private  double scrollViewHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Read");

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("Doner_Loren_Neuderzimaa_strast_r2_Pr8Ji.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                text.append(mLine);
                text.append('\n');
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error reading file!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
            TextView output= (TextView) findViewById(R.id.book_read);
            output.setText((CharSequence) text);
            TextView mTextStatus = (TextView) findViewById(R.id.book_read);
            ScrollView mScrollView = (ScrollView) findViewById(R.id.scrollView2);
            //scrollToBottom();

            mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    scrollViewHeight = mScrollView.getChildAt(0).getBottom() - mScrollView.getHeight();
                    double getScrollY = mScrollView.getScrollY();
                    double scrollPosition = (getScrollY / scrollViewHeight) * 100d;
                    Log.i("scrollview", "scroll Percent Y: " + (int) scrollPosition);
                    TextView procent= (TextView) findViewById(R.id.textView5);
                    String stringdouble= String.format("%.2f",scrollPosition)+" %";
                    procent.setText(stringdouble);
                }
            });
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(0, (int)(mScrollView.getChildAt(0).getBottom() - mScrollView.getHeight())*30/100);
                }
            });
        }

    }
    private void scrollToBottom()
    {
        mScrollView.post(new Runnable()
        {
            public void run()
            {
                mScrollView.smoothScrollTo(0, mTextStatus.getBottom());
            }
        });
    }
// then get the TextView and set its text



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