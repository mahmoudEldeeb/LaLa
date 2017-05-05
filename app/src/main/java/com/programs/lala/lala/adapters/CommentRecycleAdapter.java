package com.programs.lala.lala.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.programs.lala.lala.R;
import com.programs.lala.lala.assets.constians;
import com.programs.lala.lala.models.CommentModel;
import com.programs.lala.lala.models.MatchsModel;
import com.programs.lala.lala.models.PostModel;
import com.programs.lala.lala.vollay.volly;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;

/**
 * Created by melde on 3/13/2017.
 */
public class CommentRecycleAdapter extends RecyclerView.Adapter<CommentRecycleAdapter.ViewHolder>{

    List<CommentModel> List ;
    Context con;
    LayoutInflater inflater;
    int scroll = 0;
    final String Path = "http://lalaprogram95.890m.com/";
public CommentRecycleAdapter(Context c,  List<CommentModel> data){

    this.con = c;
     List = data;
    inflater = LayoutInflater.from(c);
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
    View views=  LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
     ViewHolder holder = new ViewHolder(views,List);
    return holder;

        }

@Override
public void onBindViewHolder(ViewHolder holder,int position){

    holder.comment.setText(List.get(position).getComment_desc());
    holder.username.setText(List.get(position).getUsername());
    try {
        Picasso.with(con)
                .load(String.valueOf(new constians().Base_Url + List.get(position).getUser_pic())).fit()
                .into(holder.poster);

    }
    catch (Exception e){}

}

@Override
public int getItemCount(){

    return List.size();
        }

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView poster;
    TextView username,comment;
Button  reportBtn;
    List<CommentModel> List ;


    public ViewHolder(View itemView ,    List<CommentModel> List) {
        super(itemView);
        this.List=List;
        poster = (ImageView) itemView.findViewById(R.id.user_image);
        username = (TextView) itemView.findViewById(R.id.txtusername);
        comment = (TextView) itemView.findViewById(R.id.comment);
        reportBtn = (Button) itemView.findViewById(R.id.reportBtn);
        reportBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String path=new constians().Base_Url+"report";
        int postion = getAdapterPosition();
        make_report(path,List.get(postion).getUsername(), List.get(postion).getComment_desc());
        Toast.makeText(con, "تم الابلاغ علي هذا التعليق ", Toast.LENGTH_SHORT).show();

    }
    public void make_report(String path, final String username , final String comment  ) {
try{
        StringRequest request = new StringRequest(StringRequest.Method.POST, path
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Toast.makeText(con,response.toString(), Toast.LENGTH_SHORT).show();

                } catch (Exception ex) {

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(con, "no internet", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("comment", comment);
                return params;
            }
        };

        volly.getinsance(con).add_request(request);
}catch (Exception e){}
    }

}
}