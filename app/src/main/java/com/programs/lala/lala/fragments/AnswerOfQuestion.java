package com.programs.lala.lala.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.programs.lala.lala.DataInterfaces.GetData;
import com.programs.lala.lala.R;
import com.programs.lala.lala.adapters.Answer_Adapter;
import com.programs.lala.lala.adapters.RecyclerView_adapter;
import com.programs.lala.lala.assets.communication;
import com.programs.lala.lala.models.FaxModel;
import com.programs.lala.lala.models.PostModel;
import com.programs.lala.lala.models.Results.ResultFaxModel;
import com.programs.lala.lala.models.Results.ResultPostModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerOfQuestion extends Fragment {

    String Base_Url = "";
    RecyclerView main_recyle;
    Answer_Adapter adapter;
    int type=0;
    int itemVisible=0;
    int numberOfPage=0;
    List<FaxModel> arraydata=new ArrayList<>();
    List<FaxModel> newarraydata=new ArrayList<>();
    Button makeQuestion;
    communication communication;
    public AnswerOfQuestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_answer_of_question, container, false);
       /////////////////////////////
        MobileAds.initialize(getActivity().getApplicationContext(), String.valueOf(getResources().getText(R.string.app_id)));
        AdView mAdView = (AdView) view.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
       //////////////////
        Base_Url=getResources().getString(R.string.BASE_URL);
        main_recyle= (RecyclerView) view.findViewById(R.id.answer_recycle);
        main_recyle.setLayoutManager(new LinearLayoutManager(getActivity()));
        getMoviesFromSite();

            adapter = new Answer_Adapter( getActivity(),arraydata);
        main_recyle.setAdapter(adapter);
        main_recyle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

                if (lastVisiblePosition + 1 == itemVisible + 20) {
                    getMoviesFromSite();
                    itemVisible += 20;


                }
            }

        });

        makeQuestion= (Button) view.findViewById(R.id.button4);
        makeQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communication = (com.programs.lala.lala.assets.communication) getActivity();
                communication.resoponse(0);

            }
        });
        return view;
    }

    public void getMoviesFromSite() {
        if (isNetworkAvailable()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final Call<ResultFaxModel> connection;
            GetData moviesApi = retrofit.create(GetData.class);
           String id= getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                    .getString("userid", "");
            connection = moviesApi.getFaxs(id);

            connection.enqueue(new Callback<ResultFaxModel>() {
                @Override
                public void onResponse(Call<ResultFaxModel> call, Response<ResultFaxModel> response) {
                 newarraydata = response.body().getResult();
                    arraydata.addAll(newarraydata);
                     adapter.notifyItemInserted(arraydata.size() - 1);
                    adapter.notifyDataSetChanged();
                    numberOfPage++;

               }

                @Override
                public void onFailure(Call<ResultFaxModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "there is error try later", Toast.LENGTH_SHORT).show();



                }
            });





        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
