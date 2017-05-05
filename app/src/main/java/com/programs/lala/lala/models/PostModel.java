package com.programs.lala.lala.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by melde on 3/13/2017.
 */
public class PostModel implements Parcelable {
    String post_id;
    String post_cat;
    String post_tittle;
    String post_desc;
    String post_attached;
    int number_of_rate;
    double rate;
public PostModel(){

}
    protected PostModel(Parcel in) {
        post_id = in.readString();
        post_cat = in.readString();
        post_tittle = in.readString();
        post_desc = in.readString();
        post_attached = in.readString();
        number_of_rate = in.readInt();
        rate = in.readDouble();
    }

    public static final Creator<PostModel> CREATOR = new Creator<PostModel>() {
        @Override
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };

    public String getPost_id() {
        return post_id;
    }

    public double getRate() {
        return rate;
    }

    public int getNumber_of_rate() {
        return number_of_rate;
    }

    public String getPost_attached() {
        return post_attached;
    }

    public String getPost_desc() {
        return post_desc;
    }

    public String getPost_tittle() {
        return post_tittle;
    }

    public String getPost_cat() {
        return post_cat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(post_id);
        dest.writeString(post_cat);
        dest.writeString(post_tittle);
        dest.writeString(post_desc);
        dest.writeString(post_attached);
        dest.writeInt(number_of_rate);
        dest.writeDouble(rate);
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setPost_cat(String post_cat) {
        this.post_cat = post_cat;
    }

    public void setPost_tittle(String post_tittle) {
        this.post_tittle = post_tittle;
    }

    public void setPost_desc(String post_desc) {
        this.post_desc = post_desc;
    }

    public void setPost_attached(String post_attached) {
        this.post_attached = post_attached;
    }

    public void setNumber_of_rate(int number_of_rate) {
        this.number_of_rate = number_of_rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
