package com.example.ereader;

import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import java.util.List;


public class BookAdapterDownload extends RecyclerView.Adapter<BookViewHolder>{

    private List<BookDownload> items;
    private Context context;

    TextView tvTest;
    public BookAdapterDownload(Context context,List<BookDownload> items) {
        this.context = context;
        this.items=items;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recource_book,parent,false);
        return new BookViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        BookDownload item = items.get(position);
        holder.itemView.setOnClickListener(
                view -> {
                    Intent intent = new Intent(context,BookDescriptionsActivity.class);
                    intent.putExtra("positions",position);
                    context.startActivity(intent);
                }
        );

        holder.nameView.setText(item.name);
        holder.authorView.setText(item.author);
        holder.ratingView.setText(item.rating.toString());
        holder.imageView.setImageResource(R.drawable.default_book);
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}
