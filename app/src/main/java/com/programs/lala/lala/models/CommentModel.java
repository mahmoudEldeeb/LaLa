package com.programs.lala.lala.models;

/**
 * Created by melde on 3/13/2017.
 */
public class CommentModel {
    String comment_id ;
    String comment_desc;
    String post_id;
    String user_id;
    String  username;
    String user_pic;
    public  CommentModel(String comment_id ,String comment_desc ,String post_id
    ,String user_id ,String username ,String user_pic) {
        setComment_id(comment_id);
        setComment_desc(comment_desc);
        setPost_id(post_id);
        setUser_id(user_id);
        setUsername(username);
        setUser_pic(user_pic);
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_desc() {
        return comment_desc;
    }

    public void setComment_desc(String comment_desc) {
        this.comment_desc = comment_desc;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
