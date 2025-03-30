package com.example.newsapp.model;

public class NewsArticle {
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String publishedAt;
    private String source;

    public NewsArticle(String title, String description, String url, String imageUrl, String publishedAt, String source) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
        this.source = source;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }
}

