package com.example.ereader;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void startRegisterActivity(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void startMainPage(View v){
        Intent intent = new Intent(this,MainPage.class);
        startActivity(intent);
    }

}