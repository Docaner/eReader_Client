package com.example.ereader.LocalDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
//Класс для создания удаления и обновления таблиц БД
public class MyDbHelper extends SQLiteOpenHelper
{
    public MyDbHelper(@Nullable Context context) {
        super(context, MyConstants.DB_NAME, null, MyConstants.VERSION);
    }
    //Очистка всей БД
    public void clearTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(MyConstants.CLEAR_TABLE);
    }
    //Создание таблицы, если не создана
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MyConstants.TABLE_STRUCTURE);
    }
    //Уничтожение
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(MyConstants.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
