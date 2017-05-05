package com.programs.lala.lala.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.programs.lala.lala.R;
import com.programs.lala.lala.activties.comment_activity;
import com.programs.lala.lala.activties.confirm_download;
import com.programs.lala.lala.assets.Points;
import com.programs.lala.lala.assets.communication;
import com.programs.lala.lala.assets.confirm_commucation;
import com.programs.lala.lala.assets.constians;
import com.programs.lala.lala.models.BookModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.programs.lala.lala.fragments.login_frag.context;

/**
 * Created by melde on 3/16/2017.
 */

public class Book_Adapter extends RecyclerView.Adapter<Book_Adapter.viewholder> {
    ArrayList<BookModel> arrayList ;
    Context con;
    LayoutInflater inflater;
    int downloadPosition = 0;
    public Book_Adapter(Context cons, ArrayList<BookModel> data) {
        this.con = cons;
        arrayList = data;
        inflater = LayoutInflater.from(cons);
    }
    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View views=  LayoutInflater.from(parent.getContext()).inflate(R.layout.book_raw, parent, false);
        viewholder holder = new viewholder(views, con ,arrayList);
        return holder;
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        holder.title.setText(arrayList.get(position).getBook_name());
       holder.points.setText(arrayList.get(position).getBook_rate()+"نقطه");
try {


    Picasso.with(con)
            .load(String.valueOf(new constians().Base_Url + arrayList.get(position).getBook_cover())).fit()
            .into(holder.cover);

}
catch (Exception e){}


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener ,confirm_commucation {

        ImageView cover;
        TextView title,points;
        RatingBar  ratingBar;
        Button download ;
        ArrayList<BookModel> data;
        communication communication;
        boolean confrim ;
        public viewholder(View view, Context con,ArrayList<BookModel> data) {
            super(view);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            title= (TextView) itemView.findViewById(R.id.textView4);
            points= (TextView) itemView.findViewById(R.id.textView3);
            download= (Button) itemView.findViewById(R.id.button);
             download.setOnClickListener(this);

             communication = (com.programs.lala.lala.assets.communication) con;
            this.data=data;
        }


        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();
             confirm_download confirm_download = new confirm_download();
            Bundle bundle =new Bundle();
            bundle.putString("points",data.get(postion).getBook_rate());

            confirm_download.setArguments(bundle);
             confirm_download.setListener(viewholder.this);
            final Activity activity = (Activity) con;

            confirm_download.show(activity.getFragmentManager(), null);
            downloadPosition=postion;
        }

        @Override
        public void getconfrim(boolean confrim) {
            this.confrim =confrim;
            if (confrim)
            { int points= con.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                    .getInt("points", 0);
                con.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                        .putInt("points", points-10).apply();
                Points p=new Points(con);
                p.updatePoints(points-10);

                communication.resoponse(arrayList.get(downloadPosition).getBook_url(),arrayList.get(downloadPosition).getBook_name(),arrayList.get(downloadPosition).getBook_rate());

                communication.updatepoints(0);
            }
            else
            {

            }
         }
    }

}





