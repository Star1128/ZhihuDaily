package com.example.zhihudaily.Bean.comments;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/19
 * @version 1.0.0
 */
public class LongComments {

    private List<Comments> comments;

    public LongComments(List<Comments> comments) {
        this.comments = comments;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
