package com.example.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        
        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("News Details");
        }
        
        // Get views
        ImageView imageView = findViewById(R.id.detail_image);
        TextView titleTextView = findViewById(R.id.detail_title);
        TextView dateTextView = findViewById(R.id.detail_date);
        TextView sourceTextView = findViewById(R.id.detail_source);
        TextView descriptionTextView = findViewById(R.id.detail_description);
        Button readMoreButton = findViewById(R.id.read_more_button);
        
        // Get data from intent
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            String url = intent.getStringExtra("url");
            String imageUrl = intent.getStringExtra("imageUrl");
            String publishedAt = intent.getStringExtra("publishedAt");
            String source = intent.getStringExtra("source");
            
            // Set data to views
            titleTextView.setText(title);
            descriptionTextView.setText(description);
            sourceTextView.setText(source);
            
            // Format and set date
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault());
                Date date = inputFormat.parse(publishedAt);
                if (date != null) {
                    dateTextView.setText(outputFormat.format(date));
                }
            } catch (ParseException e) {
                dateTextView.setText(publishedAt);
            }
            
            // Load image
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.news_placeholder)
                    .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.news_placeholder);
            }
            
            // Set read more button click listener
            readMoreButton.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

