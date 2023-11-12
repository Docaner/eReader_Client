package com.example.ereader;

import java.util.Objects;

public class Book {

    public int id;
    public String name;
    public String author;
    public String description;
    public Double rating;
    public int image;
    public Book(int id,String name, String author, String description, Double rating,int image) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.image = image;
    }
    public Book(int id,String name, String author, String description, Double rating) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.image = R.drawable.default_book;
    }
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
