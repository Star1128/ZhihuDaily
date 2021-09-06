package com.example.zhihudaily.Bean.comments;

/**
 * NOTE:
 *
 * @author wxc 2021/8/19
 * @version 1.0.0
 */
public class Comments {

    private String author;
    private String content;
    private String avatar;
    private int time;
    private int id;
    private int likes;
    private boolean isGood;

    public Comments(String author, String content, String avatar, int time, int id, int likes) {
        this.author = author;
        this.content = content;
        this.avatar = avatar;
        this.time = time;
        this.id = id;
        this.likes = likes;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean getIsGood() {
        return isGood;
    }

    public void setIsGood(boolean good) {
        isGood = good;
    }
}

