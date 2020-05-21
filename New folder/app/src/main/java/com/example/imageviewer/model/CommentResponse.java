package com.example.imageviewer.model;

import java.util.ArrayList;

public class CommentResponse {
    public CommentSet comments;
    public String stat;

    public ArrayList<Comment> getCommentList() {
        if(comments !=null)
            return comments.comment;
        else
            return new ArrayList<Comment>();
    }
}
