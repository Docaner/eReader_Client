package com.example.ereader.LocalDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ereader.BookAdapterDownload;
import com.example.ereader.BookDownload;
import com.google.android.material.shape.CutCornerTreatment;

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


    //Найти по имени, вставить прогресс
    public void insertToDbByName(String name, int progress){

        ContentValues cv = new ContentValues();
        cv.put(MyConstants.PROGRESS,progress);
        db.update(MyConstants.TABLE_NAME,cv,"_name = ?",new String[]{name});
    }


    //Найти по имени, вернуть путь и прогресс
    public BookDownload getProgressPathByName(String name){
        BookDownload bk = new BookDownload();
        db = MyDbHelper.getReadableDatabase();
        String command1 = "SELECT " + MyConstants.PROGRESS + ", " + MyConstants.PATH +" FROM " + MyConstants.TABLE_NAME + " WHERE " +
                MyConstants.NAME + " = '" + name +"'";
        Cursor c = db.rawQuery(command1,null);
        if (c.moveToFirst()){
            do{
                bk.progress = Integer.parseInt(c.getString(0));
                bk.path  = c.getString(1);
            }
            while (c.moveToNext());
        }
        c.close();
        db = MyDbHelper.getWritableDatabase();
        return bk;
    }

    //Очитска всей БД
    public void clearAllDb(){
        MyDbHelper.clearTable(db);
    }


    //Удаление конкретной записи
    public void deleteBook(String author,String name){
        MyDbHelper.deleteBook(db,author,name);
    }


    //Проверка на существование записи
    public int searchAuthorName(String author,String name){
        db = MyDbHelper.getReadableDatabase();
        String command1 = "SELECT count(1) FROM " + MyConstants.TABLE_NAME + " WHERE " +
                MyConstants.NAME + " = '" + name +"' and " +
                MyConstants.AUTHOR + " = '" + author +"'";
        Cursor c = db.rawQuery(command1,null);
        String prov="123";
        if (c.moveToFirst()){
            do{
                prov = c.getString(0);
            }
            while (c.moveToNext());
        }
        c.close();
        db = MyDbHelper.getWritableDatabase();
        return Integer.parseInt(prov);
    }


    //Возвращение конкретной записи
    public BookDownload getOneBook(String author, String name){
        BookDownload bk = new BookDownload();
        db = MyDbHelper.getReadableDatabase();

        String command1 = "SELECT * FROM " + MyConstants.TABLE_NAME + " WHERE " +
                MyConstants.NAME + " = '" + name +"' and " +
                MyConstants.AUTHOR + " = '" + author +"'";

        Cursor c = db.rawQuery(command1,null);
        if (c.moveToFirst()){
            do{
                bk.id = Integer.parseInt(c.getString(0));
                bk.author = c.getString(1);
                bk.name = c.getString(2);
                bk.description  = c.getString(3);
                bk.rating =Double.parseDouble(c.getString(4));
                bk.progress = Integer.parseInt(c.getString(5));
                bk.path  = c.getString(6);
            }
            while (c.moveToNext());
        }
        c.close();
        db = MyDbHelper.getWritableDatabase();

        return  bk;
    }


    //Считать всю БД
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

            String path = "Пока пусто";//затычка

            BookDownload bookdown = new BookDownload(id,name,author,description,rating,path,progress);
            list.add(bookdown);
        }
        cursor.close();
        return list;
    }

    public void closeDb(){
        MyDbHelper.close();
    }


}

