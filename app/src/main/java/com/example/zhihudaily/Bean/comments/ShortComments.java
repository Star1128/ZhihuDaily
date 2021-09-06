package com.example.zhihudaily.Bean.comments;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/19
 * @version 1.0.0
 */
public class ShortComments {

    private List<Comments> comments;

    public ShortComments(List<Comments> comments) {
        this.comments = comments;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
