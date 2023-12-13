package com.example.ereader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import com.example.ereader.LocalDb.MyDbManager;


public class ReadActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private boolean isNightModeOn;
    private StringBuilder text = new StringBuilder();
    private int a,g=0;
    private MyDbManager dbManager;
    private boolean useAlternativeTheme;
    private double scrollPosition;
    private String Name;
    private BookDownload bookdown;
    private  TextView mTextStatus;
    private TextView textView;;
    private int x;
    private int d=0;
    int z=0;
    private ScrollView  mScrollView;
    private  double scrollViewHeight;
    private  BookDownload bookDownload;
    EditText etText;
    Button btnSave, btnLoad;

    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final String SAVED_TEXT = "saved_text";
        setContentView(R.layout.activity_read);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        setTheme(android.R.style.Theme);
        dbManager = new MyDbManager(this);
        dbManager.openDb();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Name = extras.getString("Name");
            x=(int)extras.getInt("Status");
            //The key argument here must match that used in the other activity
        }
        getSupportActionBar().setTitle(Name);
        isNightModeOn=false;
        BufferedReader reader = null;
        loadTextA();
        bookdown=dbManager.getProgressPathByName(Name);





        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(Name+".txt")));

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
                    scrollPosition = (getScrollY / scrollViewHeight) * 100d;
                    Log.i("scrollview", "scroll Percent Y: " + (int) scrollPosition);
                    TextView procent= (TextView) findViewById(R.id.textView5);
                    String stringdouble= String.format("%.2f",scrollPosition)+" %";
                    procent.setText(stringdouble);

                }
            });

            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(0, (int)(mScrollView.getChildAt(0).getBottom() - mScrollView.getHeight())*bookdown.progress/100);
                }
            });

            Button btnTextSize = (Button) findViewById(R.id.button);
            Button btnTextSizedown = (Button) findViewById(R.id.button3);
            Button switch_btn = findViewById(R.id.button6);
            Button switch_btn1 = findViewById(R.id.button7);
            switch_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }

            });
            switch_btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        isNightModeOn=true;

            }});
            btnTextSize.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    double p=scrollPosition;
if(d!=0)
                    a+=3;
d++;
                    saveTextA();
                    mTextStatus.setTextSize(a); //значение присваивается в sp (px лучше не использовать)
                    mScrollView.post(new Runnable() {
                        public void run() {
                            mScrollView.scrollTo(0, (int)((mScrollView.getChildAt(0).getBottom() - mScrollView.getHeight())*p/100));
                        }
                    });
                }
            });
            btnTextSizedown.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    double p=scrollPosition;
                    if(d!=0)
                        d++;
                    a-=3;
                    saveTextA();
                    mTextStatus.setTextSize(a); //значение присваивается в sp (px лучше не использовать)
                    mScrollView.post(new Runnable() {
                        public void run() {
                            mScrollView.scrollTo(0, (int)((mScrollView.getChildAt(0).getBottom() - mScrollView.getHeight())*p/100));
                        }
                    });
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
        if (id==R.id.library) {//список скачанных книг
            intent = new Intent(this, DownloadBooksPage.class);
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


    void saveTextA() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("A",a);
        ed.commit();

    }

    void loadTextA() {
        sPref = getPreferences(MODE_PRIVATE);
        int savedText = sPref.getInt("A",a);
        a=(savedText);

    }
    void saveTextp() {
        g=1;
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("P",(int) g);
        ed.commit();

    }

    void loadTextp() {
        sPref = getPreferences(MODE_PRIVATE);
        int savedText = sPref.getInt("P",(int)scrollPosition);
        g=(savedText);

    }
    @Override
    protected void onPause() {
        super.onPause();
        isNightModeOn=true;

        loadTextp();
        dbManager.insertToDbByName(Name,(int)scrollPosition+1);
    }
    @Override
    protected void onResume() {

        super.onResume();
        dbManager.insertToDbByName(Name,(int)scrollPosition+1);
    }
    @Override
    protected void onDestroy(){


        super.onDestroy();
        dbManager.closeDb();
    }
    @Override
    protected void onStop() {
        super.onStop();


        dbManager.insertToDbByName(Name,(int)scrollPosition+1);
        dbManager.closeDb();
    }

}
