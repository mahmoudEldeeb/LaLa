package com.programs.lala.lala.assets;


import com.programs.lala.lala.models.MatchsModel;
import com.programs.lala.lala.models.PostModel;

/**
 * Created by UsersFiles on 3/12/2017.

 */
public interface communication  {
    public void resoponse(PostModel mainData);
    public void Resopnse_MatchesModel(MatchsModel mainData);


    public void resoponse(String path,String name,String points);
    public void resoponse(int path);

   public void resoponse();
    public void updatepoints(int p);
}
