package com.programs.lala.lala.models;

/**
 * Created by melde on 3/14/2017.
 */
public class BookModel {
    String book_id;
    String book_name;
    String book_url;
    String book_cover;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_url() {
        return book_url;
    }

    public void setBook_url(String book_url) {
        this.book_url = book_url;
    }

    public String getBook_cover() {
        return book_cover;
    }

    public void setBook_cover(String book_cover) {
        this.book_cover = book_cover;
    }

    public String getBook_rate() {
        return book_rate;
    }

    public void setBook_rate(String book_rate) {
        this.book_rate = book_rate;
    }

    String book_rate;

}
