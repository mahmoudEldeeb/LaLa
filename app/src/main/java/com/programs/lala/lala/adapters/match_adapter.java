package com.programs.lala.lala.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.programs.lala.lala.R;
import com.programs.lala.lala.assets.communication;
import com.programs.lala.lala.assets.constians;
import com.programs.lala.lala.models.MatchsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import com.squareup.picasso.Picasso;

/**
 * Created by UsersFiles on 6/8/2016.
 */
public class match_adapter extends RecyclerView.Adapter<match_adapter.viewholder> {
     List<MatchsModel> arrayList ;
     Context con;
     LayoutInflater inflater;
    int scroll = 0;
    final String Path = "http://lalaprogram95.890m.com/";
    public match_adapter(Context cons, List<MatchsModel> data) {
        this.con = cons;
        arrayList = data;
        inflater = LayoutInflater.from(cons);
     }
    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View views=  LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_items, parent, false);
        //View views = inflater.inflate(R.layout.row_list, parent, false);
        viewholder holder = new viewholder(views, con, arrayList);
        return holder;
    }



    @Override
    public void onBindViewHolder(final viewholder holder, final int position) {
        //غير التكست باللي هترجعه من مودل الماتش
        holder.txt_team.setText(arrayList.get(position).getFirst_team());
       holder.txt_result.setText(arrayList.get(position).getTime());
        holder.txt_team1.setText(arrayList.get(position).getSecond_team());
            Picasso.with(con)
                    .load(new constians().Base_Url +arrayList.get(position).getImg_team2() )
                    .into(holder.Imag_team);
            Picasso.with(con)
                    .load(String.valueOf(new constians().Base_Url + arrayList.get(position).getImg_team1()))
                    .into(holder.Imag_team1);

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_team1, txt_result,txt_team;
        ImageView Imag_team1,Imag_result,Imag_team;
         Context con;
         List<MatchsModel> arraydata;
        communication communication;
        public viewholder(View view, Context con, List<MatchsModel> arraydata) {
            super(view);
            this.con = con;
            this.arraydata = arraydata;
            view.setOnClickListener(this);
            txt_team1 = (TextView) view.findViewById(R.id.txt_team1);
             txt_result = (TextView) view.findViewById(R.id.txt_result);
            txt_team= (TextView) view.findViewById(R.id.txt_team);
            Imag_team1 = (ImageView) view.findViewById(R.id.Imag_team1);
            Imag_team = (ImageView) view.findViewById(R.id.Imag_team );
           // Imag_result = (ImageView) view.findViewById(R.id.Imag_result);

            communication = (com.programs.lala.lala.assets.communication) con;
          }
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onClick(View view) {



             int postion = getAdapterPosition();

            communication.Resopnse_MatchesModel(arraydata.get(postion));



        }
    }




}
