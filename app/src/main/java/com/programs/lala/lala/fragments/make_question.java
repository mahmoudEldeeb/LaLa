package com.programs.lala.lala.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.programs.lala.lala.DataInterfaces.GetData;
import com.programs.lala.lala.R;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;
public class make_question extends Fragment {
TextView text;
Button publish_BTN;
    EditText editText;
    public make_question() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_make_question, container, false);

/*
        MobileAds.initialize(getActivity().getApplicationContext(), String.valueOf(getResources().getText(R.string.app_id)));
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/

        editText= (EditText) view.findViewById(R.id.editText2);
        publish_BTN = (Button) view.findViewById(R.id.publish_BTN);
        publish_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeQuestion();

                //Btn_publish_function(view);
            }
        });
text= (TextView) view.findViewById(R.id.textView2);
        Bundle bundle=this.getArguments();
        int type=bundle.getInt("type");
        if(type==0){
          // Toast.makeText(getActivity(),"0",Toast.LENGTH_SHORT).show();
            text.setText(getResources().getString(R.string.ask_question));
        }
        else{

          //  Toast.makeText(getActivity(),"1",Toast.LENGTH_SHORT).show();
            text.setText(getResources().getString(R.string.ask_book));

        }
        return view;
    }
    public void Btn_publish_function(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    public void makeQuestion()  {
    if (isNetworkAvailable()) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Call<ResponseBody> connection;
        GetData data = retrofit.create(GetData.class);
String s=editText.getText().toString();

        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"),s);
        String id= getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("userid", "");

        connection = data.makeQuestion(id, filename);

        //Toast.makeText(getActivity(),connection.request().url()+"",Toast.LENGTH_SHORT).show();
        connection.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Log.v("dddd",connection.request().url()+"");
                String result = "";
                try {
                    result =response.body().string();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                    Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
               // Log.v("cfvgh",connection.request().url()+"");

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity().getBaseContext(), "there is error try later", Toast.LENGTH_SHORT).show();
            }
        });
    } else
        Toast.makeText(getActivity().getBaseContext(), "there is no internet conection", Toast.LENGTH_SHORT).show();
}
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
