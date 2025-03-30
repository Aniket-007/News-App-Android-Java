package com.example.newsapp;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.adapter.NewsAdapter;
import com.example.newsapp.api.NewsApiClient;
import com.example.newsapp.model.NewsArticle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsArticle> newsArticles;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsApiClient newsApiClient;

    private String currentCategory = "";
    private static final String COUNTRY = "us"; // Default country

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        recyclerView = findViewById(R.id.news_recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsArticles = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsArticles);
        recyclerView.setAdapter(newsAdapter);

        // Initialize API client
        newsApiClient = new NewsApiClient(this);

        // Set up swipe to refresh
        swipeRefreshLayout.setOnRefreshListener(() -> loadNews(currentCategory));

        // Load initial news
        loadNews(currentCategory);
    }

    private void loadNews(String category) {
        progressBar.setVisibility(View.VISIBLE);

        newsApiClient.getTopHeadlines(COUNTRY, category, new NewsApiClient.NewsApiCallback() {
            @Override
            public void onSuccess(List<NewsArticle> articles) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                newsArticles.clear();
                newsArticles.addAll(articles);
                newsAdapter.notifyDataSetChanged();

                if (articles.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No news available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_general) {
            currentCategory = "";
            setTitle("Top Headlines");
            loadNews(currentCategory);
            return true;
        } else if (id == R.id.action_business) {
            currentCategory = "business";
            setTitle("Business News");
            loadNews(currentCategory);
            return true;
        } else if (id == R.id.action_technology) {
            currentCategory = "technology";
            setTitle("Technology News");
            loadNews(currentCategory);
            return true;
        } else if (id == R.id.action_sports) {
            currentCategory = "sports";
            setTitle("Sports News");
            loadNews(currentCategory);
            return true;
        } else if (id == R.id.action_entertainment) {
            currentCategory = "entertainment";
            setTitle("Entertainment News");
            loadNews(currentCategory);
            return true;
        } else if (id == R.id.action_health) {
            currentCategory = "health";
            setTitle("Health News");
            loadNews(currentCategory);
            return true;
        } else if (id == R.id.action_science) {
            currentCategory = "science";
            setTitle("Science News");
            loadNews(currentCategory);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

