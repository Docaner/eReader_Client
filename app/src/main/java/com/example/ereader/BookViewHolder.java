package com.example.ereader;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, authorView,ratingView;
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);
        authorView = itemView.findViewById(R.id.author);
        ratingView = itemView.findViewById(R.id.rating);
    }
}
