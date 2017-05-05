package com.programs.lala.lala.models;

/**
 * Created by UsersFiles on 3/12/2017.
 */
public class MainData {


    public MainData(String title ,String dece ,String image_Path ,int comments) {
      this. setComments_num(comments);
        setDescription(dece);
        this.setTitle(title);
        this.setImage_Path(image_Path);
    }
    public String getImage_Path() {
        return image_Path;
    }

    public void setImage_Path(String image_Path) {
        this.image_Path = image_Path;
    }

    public int getComments_num() {
        return comments_num;
    }

    public void setComments_num(int comments_num) {
        this.comments_num = comments_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title ,description ,image_Path ;
    int id,comments_num ;
}
