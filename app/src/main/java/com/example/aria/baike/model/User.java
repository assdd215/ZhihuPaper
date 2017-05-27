package com.example.aria.baike.model;

/**
 * Created by Aria on 2017/2/16.
 */

public class User {

    private int id;
    private String account;
    private String password;
    private String username;
    private String avactor;

    public User(String account,String password){
        this.account = account;
        this.password = password;
    }

    public User(String account,String password,int id,String username,String avactor){
        this.account = account;
        this.password = password;
        this.id = id;
        this.username = username;
        this.avactor = avactor;
    }

    public User(){}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvactor(String avactor) {
        this.avactor = avactor;
    }

    public int getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getAvactor() {
        return avactor;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
