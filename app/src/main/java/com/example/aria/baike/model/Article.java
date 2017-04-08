package com.example.aria.baike.model;

/**
 * Created by Aria on 2017/3/1.
 */

public class Article {
    private int id;
    private String title;
    private String classify;
    private String summary;
    private String url;
    private String avactor;

    public Article(){}

    public void setAvactor(String avactor) {
        this.avactor = avactor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvactor() {
        return avactor;
    }

    public int getId() {
        return id;
    }

    public String getClassify() {
        return classify;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}