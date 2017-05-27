package com.example.aria.baike.global;

import com.example.aria.baike.model.Article;
import com.example.aria.baike.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aria on 2017/4/24.
 */

public class UserMessage {
    private User user;
    private List<Article> starArticles;

    private UserMessage(User user,List<Article> starArticles){
        this.user = user;
        this.starArticles = starArticles;
    }

    private UserMessage(){
        user = new User();
        starArticles = new ArrayList<Article>();
    }

    private static UserMessage userMessage;

    public static UserMessage getInstance(){
        if (userMessage == null){
            userMessage = new UserMessage();
        }
        return userMessage;
    }

    public void setStarArticles(List<Article> starArticles) {
        this.starArticles = starArticles;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Article> getStarArticles() {
        return starArticles;
    }

    public User getUser() {
        return user;
    }

    public String getUserName(){
        return user.getUsername();
    }

    public String getAccount(){
        return user.getAccount();
    }


}
