package com.example.zhihudaily.Bean.extra;

/**
 * NOTE:
 *
 * @author wxc 2021/8/18
 * @version 1.0.0
 */
public class GoodAndComments {
    private int longComments;
    private int popularity;
    private int shortComments;
    private int comments;

    public GoodAndComments(int longComments, int popularity, int shortComments, int comments) {
        this.longComments = longComments;
        this.popularity = popularity;
        this.shortComments = shortComments;
        this.comments = comments;
    }

    public int getLongComments() {
        return longComments;
    }

    public void setLongComments(int longComments) {
        this.longComments = longComments;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getShortComments() {
        return shortComments;
    }

    public void setShortComments(int shortComments) {
        this.shortComments = shortComments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
