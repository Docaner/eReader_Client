package com.example.ereader;

import com.google.gson.annotations.SerializedName;

public class HttpBook {
    @SerializedName("id")
    String id;
    @SerializedName("author")
    String author;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("avatar")
    String avatar;
    @SerializedName("link")
    String link;
    @SerializedName("rating")
    String rating;

    public Book getBook(){
        return new Book(
                Integer.parseInt(id),
                author,
                name,
                description,
                avatar,
                link,
                Double.parseDouble(rating)
                );
    }
}
