package com.example.ereader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
