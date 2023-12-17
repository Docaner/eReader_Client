package com.example.ereader;

import java.util.Objects;

public class Book {

    public int id;
    public String author;
    public String name;
    public String description;
    //Ссылка на аватар
    public String urlAvatar;
    //Ссылка на книгу
    public String link;

    public Double rating;

    public int image = R.drawable.default_book;


    //Конструктор для HttpBook
    public Book(int id, String author, String name, String description, String urlAvatar, String link, double rating){
        this.id = id;
        this.author = author;
        this.name = name;
        this.description = description;
        this.urlAvatar = urlAvatar;
        this.link = link;
        this.rating = rating;
    }

    public Book(int id,String name, String author, String description, Double rating,int image) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.rating = rating;
    }
    public Book(int id,String name, String author, String description, Double rating) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.rating = rating;
    }

    public Book(){}
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String getDescription() {
        return description;
    }
    public Double getRating() {
        return rating;
    }
    public int getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book item = (Book) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
