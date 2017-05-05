package com.programs.lala.lala.vollay;



 import com.programs.lala.lala.models.MatchsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
 import java.util.List;

/**
 * Created by UsersFiles on 4/8/2016.
 */
public class Parsing_Json
{
    String Json="";
    final  static String json_array="result";

    final static String time="time";
    final static String second_team="second_team";
    final static String first_team="first_team";
    final static String Json_Id="match_id";

    final static String f_team_pic="f_team_pic";
    final static String s_team_pic="s_team_pic";
    List<MatchsModel>arrayList;
    public Parsing_Json(String JSonObject)
    {
        this.Json+=JSonObject;
        arrayList=new ArrayList<>();
    }
    public List<MatchsModel> getListOfMoves(){
     try {
    JSONObject mainobject=new JSONObject(Json);

    JSONArray  array =mainobject.getJSONArray(json_array);

     for (int i=0;i<array.length();i++){

         JSONObject branchOject =array.getJSONObject(i);

         arrayList.add(new MatchsModel(
                 branchOject.getString(Json_Id) ,
                 branchOject.getString(first_team),
                 branchOject.getString(second_team) ,
                 branchOject.getString(time),
                 branchOject.getString(f_team_pic) ,
                 branchOject.getString(s_team_pic)
              )
        );
      }
}
catch (Exception ex){

}
        return  arrayList;
    }


}
