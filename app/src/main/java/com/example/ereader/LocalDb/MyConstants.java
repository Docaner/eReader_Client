package com.example.ereader.LocalDb;
//Класс для хранения переменных для локальной БД
public class MyConstants {
    public static final String DB_NAME="readerDb.db";//База данных
    public static final int VERSION=12;//версия БД
    public static final String TABLE_NAME="downloadBooks";//Таблица

    //Поля
    public static final String _ID="_id";
    public static final String AUTHOR="_author";
    public static final String NAME="_name";
    public static final String DESCRIPTION="_description";
    public static final String RATING ="_rating";
    public static final String PATH="_path";//путь к файлу на телефоне
    public static final String PROGRESS="_progress";//прогресс чтения

    //Create
    public static final String TABLE_STRUCTURE="CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
            AUTHOR+" TEXT,"+
            NAME + " TEXT,"+
            DESCRIPTION + " TEXT,"+
            RATING +" REAL,"+
            PROGRESS+ " INTEGER,"+
            PATH+" TEXT)";

    //Drop
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String CLEAR_TABLE = "DELETE FROM "+ TABLE_NAME;
}
