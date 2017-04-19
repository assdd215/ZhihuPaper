package com.example.aria.baike.model;

/**
 * Created by Aria on 2017/3/1.
 */

public class Article {

    public static final int BANNER = 0x120;
    public static final int ARTITLE = 0x121;
    public static final int FOOT = 0x122;

    private int id;
    private String title;
    private String classify;
    private String summary;
    private String url;
    private String avactor;
    private int type;

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

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}