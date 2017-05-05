package com.programs.lala.lala.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.programs.lala.lala.R;
import com.programs.lala.lala.assets.communication;
import com.programs.lala.lala.assets.constians;
import com.programs.lala.lala.models.MainData;
import com.programs.lala.lala.models.PostModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.programs.lala.lala.fragments.login_frag.context;

/**
 * Created by UsersFiles on 6/8/2016.
 */
public class RecyclerView_adapter extends RecyclerView.Adapter<RecyclerView_adapter.viewholder> {
     List<PostModel> arrayList ;
    Context con;
    LayoutInflater inflater;
    int scroll = 0;
    public RecyclerView_adapter(Context cons,List<PostModel> data) {
        this.con = cons;
        arrayList = data;
        inflater = LayoutInflater.from(cons);

     }
    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View views=  LayoutInflater.from(parent.getContext()).inflate(R.layout.artical_row, parent, false);

        //View views = inflater.inflate(R.layout.row_list, parent, false);
        viewholder holder = new viewholder(views, con, arrayList);
        return holder;
    }

    @Override
    public void onBindViewHolder(final viewholder holder, final int position) {
        holder.title.setText(arrayList.get(position).getPost_tittle());
        holder.datetxt.setText(arrayList.get(position).getPost_desc()+"");
       holder.commintes.setText(arrayList.get(position).getNumber_of_rate()+"تعليق");



                try {
                Picasso.with(con)
                        .load(String.valueOf("http://lalaprogram95.890m.com/" + arrayList.get(position).getPost_attached())).fit()
                        .into(holder.img);
            }
catch(Exception e){
            }


        }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, datetxt,commintes;
        ImageView img;
         Context con;
         List<PostModel> arraydata;
        communication communication;
        public viewholder(View view, Context con,  List<PostModel> arraydata) {
            super(view);
            this.con = con;
            this.arraydata = arraydata;
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.txttitle);
            datetxt = (TextView) view.findViewById(R.id.txt_description);
            commintes= (TextView) view.findViewById(R.id.txt_comments);
            img = (ImageView) view.findViewById(R.id.image_post);
            communication = (com.programs.lala.lala.assets.communication) con;
          }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();
            String userid = con.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                    .getString("userid", "");
            Boolean uploadPhoto = con.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                    .getBoolean("uploadPhoto", false);
         if(postion==0&&arraydata.get(postion).getPost_id().equals(userid)&&!uploadPhoto){
             communication.resoponse();
         }

            communication.resoponse(arraydata.get(postion));



        }
    }
}
