package com.programs.lala.lala.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by melde on 3/14/2017.
 */
public class MatchsModel  {



    public String getMatch_id () {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getFirst_team() {
        return first_team;
    }

    public void setFirst_team(String first_team) {
        this.first_team = first_team;
    }

    public String getSecond_team() {
        return second_team;
    }

    public void setSecond_team(String second_team) {
        this.second_team = second_team;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String first_team;
    String second_team;
    String time;
    String match_id;

    public String getImg_team1() {
        return img_team1;
    }

    public void setImg_team1(String img_team1) {
        this.img_team1 = img_team1;
    }

    public String getImg_team2() {
        return img_team2;
    }

    public void setImg_team2(String img_team2) {
        this.img_team2 = img_team2;
    }

    String img_team1;
    String img_team2;


    public  MatchsModel(String id ,String second_team ,String first_team ,String date ,String img_team1 ,String img_team2){
      setFirst_team(first_team);
      setSecond_team(second_team);
      setTime(date);
        setImg_team1(img_team1);
        setImg_team2(img_team2);

  }


}
