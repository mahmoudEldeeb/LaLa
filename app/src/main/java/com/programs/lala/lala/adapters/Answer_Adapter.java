package com.programs.lala.lala.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.programs.lala.lala.R;
import com.programs.lala.lala.assets.constians;
import com.programs.lala.lala.models.FaxModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Answer_Adapter extends RecyclerView.Adapter<Answer_Adapter.viewholder> {
    List<FaxModel> arrayList;
    Context con;
    LayoutInflater inflater;
    int scroll = 0;

    public Answer_Adapter(Context cons, List<FaxModel> data) {
        this.con = cons;
        this.arrayList = data;
        inflater = LayoutInflater.from(cons);

    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_answer_questions, parent, false);
        viewholder holder = new viewholder(views, con, arrayList);
        return holder;
    }

    @Override
    public void onBindViewHolder(final viewholder holder, final int position) {
        holder.question.setText(arrayList.get(position).getFaq_desc()+"");

        holder.answer.setText(arrayList.get(position).getComment()+"");
        String imagePath = con.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("imagePath", "");
      try {
          Picasso.with(con)
                  .load(String.valueOf(new constians().Base_Url + imagePath)).fit()
                  .into(holder.image_user);
      }
      catch (Exception e){}

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {

        TextView question, answer;
        Context con;
        List<FaxModel> arraydata;
ImageView image_user;
        public viewholder(View view, Context con, List<FaxModel> arraydata) {
            super(view);
            this.con = con;
            this.arraydata = arraydata;
            question = (TextView) view.findViewById(R.id.question);
            answer = (TextView) view.findViewById(R.id.answer);
            image_user= (ImageView) view.findViewById(R.id.person_image);
        }
    }
}

