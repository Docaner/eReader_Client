package com.example.ereader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    Context context;
    List<Book> books = new BooksCollection().books;

    public BookAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recource_book, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.itemView.setOnClickListener(
                view -> {
                    Intent intent = new Intent(context,BookDescriptionsActivity.class);
                    intent.putExtra("positions",position);
                    context.startActivity(intent);
                }
        );
        holder.nameView.setText(books.get(position).name);
        holder.authorView.setText(books.get(position).author);
        holder.ratingView.setText(books.get(position).rating.toString());
        holder.imageView.setImageResource(books.get(position).image);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
