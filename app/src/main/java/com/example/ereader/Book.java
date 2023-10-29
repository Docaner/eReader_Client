package com.example.ereader;

public class Book {
    public String name;
    public String author;
    public String description;
    public Double rating;

    public Book(String name, String author, String description, Double rating) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.rating = rating;
    }
}
