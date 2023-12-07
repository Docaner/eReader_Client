package com.example.ereader.LocalDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ereader.BookAdapterDownload;
import com.example.ereader.BookDownload;

import java.util.ArrayList;

public class MyDbManager {
    private Context context;
    private MyDbHelper MyDbHelper;
    private SQLiteDatabase db;
    public MyDbManager(Context context) {
        this.context = context;
        MyDbHelper = new MyDbHelper(context);
    }
    public void openDb(){
        db = MyDbHelper.getWritableDatabase();
    }

    //Записывать
    public void insertToDb(String author,String name,String description, Double rating, int progress,String path){

        ContentValues cv = new ContentValues();

        cv.put(MyConstants.AUTHOR,author);
        cv.put(MyConstants.NAME,name);
        cv.put(MyConstants.DESCRIPTION,description);
        cv.put(MyConstants.RATING,rating);
        cv.put(MyConstants.PROGRESS,progress);
        cv.put(MyConstants.PATH,path);

        db.insert(MyConstants.TABLE_NAME,null,cv);
    }

    //Найти по имени, вставить прогресс (Не сделано)
    public void insertToDb(String name,int progress){

        ContentValues cv = new ContentValues();

        cv.put(MyConstants.NAME,name);
        cv.put(MyConstants.PROGRESS,progress);

       db.insert(MyConstants.TABLE_NAME,null,cv);
    }

    //Считывать
    public ArrayList<BookDownload> getFromDb(){

        ArrayList<BookDownload> list = new ArrayList<BookDownload>();
        Cursor cursor = db.query(MyConstants.TABLE_NAME,null,null,null,
                null,null,null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MyConstants._ID));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.AUTHOR));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.DESCRIPTION));
            double rating = cursor.getDouble(cursor.getColumnIndexOrThrow(MyConstants.RATING));
            //String path = cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.PATH));
            int progress = cursor.getInt(cursor.getColumnIndexOrThrow(MyConstants.PROGRESS));

            String path = "";//затычка

            BookDownload bookdown = new BookDownload(id,name,author,description,rating,path,progress);
            list.add(bookdown);
        }
        cursor.close();
        return list;
    }
    //Возвращает Автор, Имя, Путь, Прогресс
    public ArrayList<BookDownload> getFromDbNameAuthorProgres(){

        ArrayList<BookDownload> list = new ArrayList<BookDownload>();
        Cursor cursor = db.query(MyConstants.TABLE_NAME,null,null,null,
                null,null,null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MyConstants._ID));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.AUTHOR));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.NAME));
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.PATH));
            int progress = cursor.getInt(cursor.getColumnIndexOrThrow(MyConstants.PROGRESS));

            BookDownload bookdown = new BookDownload(id,name,author,path,progress);
            list.add(bookdown);
        }
        cursor.close();
        return list;
    }

    public void closeDb(){
        MyDbHelper.close();
    }


}

