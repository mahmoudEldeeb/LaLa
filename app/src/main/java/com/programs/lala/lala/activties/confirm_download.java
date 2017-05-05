package com.programs.lala.lala.activties;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.programs.lala.lala.R;
import com.programs.lala.lala.assets.confirm_commucation;

/**
 * Created by UsersFiles on 10/8/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class confirm_download extends DialogFragment  {
    TextView question;
Button yesbtn,nobtn;
    confirm_commucation confirm_commucation;

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("تاكيد خصم النقاط  ");
          View view =inflater.inflate(R.layout.confirm_download,container,false);
question = (TextView) view.findViewById(R.id.question);
        yesbtn = (Button) view.findViewById(R.id.yesbtn);
        nobtn = (Button) view.findViewById(R.id.nobtn);

yesbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        confirm_commucation.getconfrim(true);
        dismiss();
    }
});

        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_commucation.getconfrim(false);
                dismiss();
            }
        });
        String points="";
        Bundle bundle =this.getArguments();
        if (bundle !=null)
        {
            points =bundle.getString("points");
//            Toast.makeText(getContext(),points,Toast.LENGTH_LONG).show();
            Log.v("ddd",points+"");

        }
points ="سوف يتم خصم عدد "+points+"من النقاط من رصيدك";
        question.setText(points);

        return view ;
    }

    public void setListener(confirm_commucation listener) {
        this.confirm_commucation = listener;
    }

}
