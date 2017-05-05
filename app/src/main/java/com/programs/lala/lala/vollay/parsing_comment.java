package com.programs.lala.lala.vollay;

import com.programs.lala.lala.models.CommentModel;
import com.programs.lala.lala.models.MatchsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.programs.lala.lala.vollay.Parsing_Json.Json_Id;
import static com.programs.lala.lala.vollay.Parsing_Json.first_team;
import static com.programs.lala.lala.vollay.Parsing_Json.second_team;
import static com.programs.lala.lala.vollay.Parsing_Json.time;

/**
 * Created by UsersFiles on 3/17/2017.
 */

public class parsing_comment {  String Json="";
    final  static String json_array="result";
    final static String username="username";

     final static String user_id="user_id";
    final static String post_id="post_id";
    final static String comment_desc="comment_desc";
    final static String comment_id="comment_id";

    final static String user_pic="user_pic";


    List<CommentModel> arrayList;
    public parsing_comment(String JSonObject)
    {
        this.Json+=JSonObject;
        arrayList=new ArrayList<>();
    }
    public List<CommentModel> getListOfMoves(){
        try {
            JSONObject mainobject=new JSONObject(Json);

            JSONArray array =mainobject.getJSONArray(json_array);

            for (int i=0;i<array.length();i++){

                JSONObject branchOject =array.getJSONObject(i);

                arrayList.add(new CommentModel(
                        branchOject.getString(comment_id) ,
                        branchOject.getString(comment_desc),
                        branchOject.getString(post_id) ,
                        branchOject.getString(user_id)
                        , branchOject.getString(username),
                        branchOject.getString(user_pic)
                        )
                );
            }
        }
        catch (Exception ex){

        }
        return  arrayList;
    }




    public String  getnumberOF_comments(){
        String number="";
        try {
            JSONObject mainobject=new JSONObject(Json);

            JSONArray array =mainobject.getJSONArray(json_array);

            JSONObject branchOject =array.getJSONObject(0);
            number= branchOject.getString("numbers");
        }
        catch (Exception ex){

        }
        return number;

    }


}
