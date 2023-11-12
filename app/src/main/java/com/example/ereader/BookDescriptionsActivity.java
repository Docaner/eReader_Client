package com.example.ereader;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BookDescriptionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle saved){
        super.onCreate(saved);
        //Для переноса данных
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
    }
}
