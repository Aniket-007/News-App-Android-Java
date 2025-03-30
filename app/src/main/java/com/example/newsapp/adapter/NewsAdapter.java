package com.example.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.NewsDetailActivity;
import com.example.newsapp.R;
import com.example.newsapp.model.NewsArticle;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<NewsArticle> articles;

    public NewsAdapter(Context context, List<NewsArticle> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsArticle article = articles.get(position);
        
        holder.titleTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSource());
        
        // Load image with Glide
        if (article.getImageUrl() != null && !article.getImageUrl().isEmpty()) {
            Glide.with(context)
                .load(article.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.news_placeholder)
                .into(holder.newsImageView);
        } else {
            holder.newsImageView.setImageResource(R.drawable.news_placeholder);
        }
        
        // Set click listener to open detail activity
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("description", article.getDescription());
            intent.putExtra("url", article.getUrl());
            intent.putExtra("imageUrl", article.getImageUrl());
            intent.putExtra("publishedAt", article.getPublishedAt());
            intent.putExtra("source", article.getSource());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void updateNews(List<NewsArticle> newArticles) {
        this.articles = newArticles;
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView newsImageView;
        TextView titleTextView;
        TextView sourceTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.news_card);
            newsImageView = itemView.findViewById(R.id.news_image);
            titleTextView = itemView.findViewById(R.id.news_title);
            sourceTextView = itemView.findViewById(R.id.news_source);
        }
    }
}

