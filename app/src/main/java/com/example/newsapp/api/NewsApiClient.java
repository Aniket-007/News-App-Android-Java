package com.example.newsapp.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.model.NewsArticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsApiClient {
    private static final String TAG = "NewsApiClient";
    private static final String API_KEY = "551cce9c7c1a4750a16312b21d41800a"; // Get from newsapi.org
    private static final String BASE_URL = "https://newsapi.org/v2/";
    
    private final RequestQueue requestQueue;
    private Context context;

    public NewsApiClient(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public interface NewsApiCallback {
        void onSuccess(List<NewsArticle> articles);
        void onError(String message);
    }

    public void getTopHeadlines(String country, String category, NewsApiCallback callback) {
        String url = BASE_URL + "top-headlines?country=" + country;
        
        if (category != null && !category.isEmpty()) {
            url += "&category=" + category;
        }
        
        url += "&apiKey=" + API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        List<NewsArticle> articles = parseArticles(response);
                        callback.onSuccess(articles);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error: " + e.getMessage());
                        callback.onError("Failed to parse news data");
                    }
                },
                error -> {
                    Log.e(TAG, "API request error: " + error.networkResponse != null ? error.networkResponse.statusCode + "" : "Unknown error");
                    callback.onError("Failed to fetch news. Please check your internet connection.");
                }
        ) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
                headers.put("Accept", "application/json");
                return headers;
            }
        };


        requestQueue.add(request);
    }

    private List<NewsArticle> parseArticles(JSONObject response) throws JSONException {
        List<NewsArticle> articles = new ArrayList<>();
        
        JSONArray articlesArray = response.getJSONArray("articles");
        for (int i = 0; i < articlesArray.length(); i++) {
            JSONObject articleObj = articlesArray.getJSONObject(i);
            
            String title = articleObj.getString("title");
            String description = articleObj.optString("description", "No description available");
            String url = articleObj.getString("url");
            String imageUrl = articleObj.optString("urlToImage", "");
            String publishedAt = articleObj.getString("publishedAt");
            
            JSONObject sourceObj = articleObj.getJSONObject("source");
            String source = sourceObj.getString("name");
            
            NewsArticle article = new NewsArticle(title, description, url, imageUrl, publishedAt, source);
            articles.add(article);
        }
        
        return articles;
    }
}

