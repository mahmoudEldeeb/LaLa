package com.programs.lala.lala.fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.programs.lala.lala.DataInterfaces.GetData;
import com.programs.lala.lala.R;
import com.programs.lala.lala.activties.Download;
import com.programs.lala.lala.activties.comment_activity;
import com.programs.lala.lala.activties.confirm_download;
import com.programs.lala.lala.activties.popmenu;
import com.programs.lala.lala.adapters.Book_Adapter;
import com.programs.lala.lala.adapters.CommentRecycleAdapter;
import com.programs.lala.lala.assets.Points;
import com.programs.lala.lala.assets.communication;
import com.programs.lala.lala.assets.confirm_commucation;
import com.programs.lala.lala.assets.constians;
import com.programs.lala.lala.models.PostModel;
import com.programs.lala.lala.vollay.parsing_comment;
import com.programs.lala.lala.vollay.volly;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.path;


/**
 * A simple {@link Fragment} subclass.
 */
public class Post_show_frag extends Fragment {


    RecyclerView recyclerView;
    CommentRecycleAdapter adapter;


    public Post_show_frag() {
        // Required empty public constructor
    }

TextView title,desc ;
ImageView imageView;
  //  RatingBar ratingBar;
    EditText take_comment;
    Button send_comment;
    TextView comments;
    boolean isImage=true;
    Button download;
    boolean confrim;
    String downloadImagePath="";
    confirm_download confirm_download;
    communication communication;
    String Base_Url ;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_post_show_frag, container, false);
        communication = (com.programs.lala.lala.assets.communication) getActivity();
        Base_Url = getResources().getString(R.string.BASE_URL);
        MobileAds.initialize(getActivity().getApplicationContext(), String.valueOf(getResources().getText(R.string.app_id)));
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle bundle=new Bundle();
        confirm_download=new confirm_download();
        bundle=this.getArguments();

        confirm_download.setArguments(bundle);
        final Bundle bundle1 =new Bundle();
        bundle1.putString("points","10");
        final PostModel postModel=bundle.getParcelable("model");
        comments = (TextView) view.findViewById(R.id.comments);
        download= (Button) view.findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), Download.class);
                Bundle bundle1=new Bundle();
                bundle1.putInt("point",0);
                bundle1.putString("path",new constians().Base_Url+downloadImagePath);
                intent.putExtras(bundle1);
                startActivity(intent);


            }
        });
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_activity comment_menu = new comment_activity();
                Bundle b =new Bundle();
                b.putString("post_id",postModel.getPost_id());
                comment_menu.setArguments(b);
                comment_menu .show(getActivity().getFragmentManager(), null);

            }
        });

        imageView= (ImageView) view.findViewById(R.id.user_image);
        ImageView acountImage= (ImageView) view.findViewById(R.id.acountImage);
        title= (TextView) view.findViewById(R.id.title);
        desc= (TextView) view.findViewById(R.id.txtdesc);
        send_comment= (Button) view.findViewById(R.id.send_comment);
        take_comment= (EditText) view.findViewById(R.id.take_comment);
        //ratingBar= (RatingBar) view.findViewById(R.id.ratingBar2);
        title.setText(postModel.getPost_tittle());
        desc.setText(postModel.getPost_desc());
        String imagePath= getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("imagePath", "");

        try {
            Picasso.with(getActivity())
                    .load(String.valueOf(new constians().Base_Url + imagePath)).fit()
                    .into(acountImage);
        }
        catch (Exception e){}

        try {
        if(postModel.getPost_attached().endsWith(".jpg")||postModel.getPost_attached().endsWith(".png")||postModel.getPost_attached().endsWith(".jpeg")){
            Picasso.with(getActivity())
                    .load(String.valueOf(Base_Url + postModel.getPost_attached())).fit()
                    .into(imageView);
          ///  Toast.makeText(getActivity(),"photo",Toast.LENGTH_SHORT).show();
        }
        else  isImage=false;

        }
        catch (Exception e){
           /// Toast.makeText(getActivity(),"video",Toast.LENGTH_SHORT).show();
            isImage=false;
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Toast.makeText(getActivity(),"1111",Toast.LENGTH_SHORT).show();
                if(!isImage){
                    Bundle b =new Bundle();
                    popmenu popmenu = new popmenu();

                    b.putString("post_id",getResources().getText(R.string.BASE_URL)+postModel.getPost_attached());
                    popmenu.setArguments(b);
                    popmenu.show(getActivity().getFragmentManager(), null);
                }
            }
        });


//        ratingBar.setNumStars(4);
send_comment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Call<ResponseBody> connection;
        GetData moviesApi = retrofit.create(GetData.class);
       String username = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("username", "");

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"),username);
        String imagePath= getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("imagePath", "");

        RequestBody comment = RequestBody.create(MediaType.parse("text/plain"),take_comment.getText().toString());
        RequestBody pic = RequestBody.create(MediaType.parse("text/plain"),imagePath);
        connection = moviesApi.sendComment(Integer.parseInt(postModel.getPost_id()),name,comment,pic);
        connection.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    if(response.body().string().equals("تم تسجيل الكومنت")){
                        Toast.makeText(getActivity(),"تم تسجيل الكومنت",Toast.LENGTH_LONG).show();
                        take_comment.setText("");
                    }
                    else      Toast.makeText(getActivity(),"لم يتم ارسال الكومنت ",Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(),"no internet",Toast.LENGTH_SHORT).show();
            }

        });





    }
});


        String path =Base_Url+"getComment/"+postModel.getPost_id();
        getnumberofcomments (path);
        return  view ;
    }









String ss;
    public void getnumberofcomments (String path){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,path
                ,null    , new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ss =response.toString();
                String number  =new parsing_comment(ss).getnumberOF_comments();
            //    Toast.makeText(getActivity(), "number" +number, Toast.LENGTH_SHORT).show();
                number ="مشاهده"+number+"تعليق";
                comments.setText(number);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"no internet",Toast.LENGTH_LONG).show();

            }
        });
        volly.getinsance(getActivity()).add_request(jsonObjectRequest);

    }


    }

