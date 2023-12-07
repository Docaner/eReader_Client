package com.example.ereader;

import java.util.Objects;

public class BookDownload{
    public int id;
    public String name;
    public String author;
    public String description;
    public Double rating;
    public String path;//путь к файлу с текстом
    public int progress;//прогресс
    public BookDownload(int id,String name, String author, String description, Double rating,String path, int progress) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.path = path;
        this.progress = progress;
    }
    public BookDownload(int id,String name, String author, String path, int progress) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.path = path;
        this.progress = progress;
    }
    public BookDownload(Book bk){
        this.id = bk.id;
        this.name = bk.name;
        this.author = bk.author;
        this.description = bk.description;
        this.rating = bk.rating;
        this.path = "path";
        this.progress = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDownload item = (BookDownload) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


}

