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
    //List<Book> books = new BooksCollection().books;
    List<Book> books;

    public BookAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
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
                    intent.putExtra("status",0);
                    intent.putExtra("positions",position);
                    context.startActivity(intent);
                }
        );

        Book book = books.get(position);

        holder.nameView.setText(book.name);
        holder.authorView.setText(book.author);
        holder.ratingView.setText(book.rating.toString());


        new ImageLoadTask(book, holder.imageView).execute();
        /*
        if(book.bitAvatar != null) {
            holder.imageView.setImageBitmap(books.get(position).bitAvatar);
        }
        else
            holder.imageView.setImageResource(book.image);
    */
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
