package com.programs.lala.lala.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.programs.lala.lala.DataInterfaces.GetData;
import com.programs.lala.lala.R;
import com.programs.lala.lala.activties.base_activity;
import com.programs.lala.lala.loginactivty;
import com.programs.lala.lala.models.MainData;
import com.programs.lala.lala.adapters.RecyclerView_adapter;
import com.programs.lala.lala.models.PostModel;
import com.programs.lala.lala.models.Results.ResultPostModel;
import com.programs.lala.lala.models.Results.ResultWinnerModel;
import com.programs.lala.lala.models.winnerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.programs.lala.lala.R.id.toolbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main_frag extends Fragment {
    String Base_Url = "";
    RecyclerView main_recyle;
    RecyclerView_adapter adapter;
    String type="main";
int num=0;
    int itemVisible=0;
    int numberPage=0;
     List<PostModel> arraydata=new ArrayList<>();
     List<PostModel> newarraydata=new ArrayList<>();

    public Main_frag( ) {

     }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_main_frag, container, false);
        Base_Url = getResources().getString(R.string.BASE_URL);
        MobileAds.initialize(getActivity().getApplicationContext(), String.valueOf(getResources().getText(R.string.app_id)));
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        main_recyle= (RecyclerView) view.findViewById(R.id.main_recycle);
        main_recyle.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle bundle=this.getArguments();
        if (bundle !=null ){
             type=bundle.getString("type");
        }
        if(type.equals("main")&&num==0){
            getWinerPost();}
            getMoviesFromSite();
        adapter = new RecyclerView_adapter( getActivity(),arraydata);
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
        return view;
    }


    public void getMoviesFromSite() {
        if (isNetworkAvailable()) {

            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Base_Url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final Call<ResultPostModel> connection;
                GetData moviesApi = retrofit.create(GetData.class);

                if (type.equals("main")) {
                    connection = moviesApi.getPosts(numberPage);

                } else {
                    connection = moviesApi.getCatPost(type, numberPage);


                }

                connection.enqueue(new Callback<ResultPostModel>() {
                    @Override
                    public void onResponse(Call<ResultPostModel> call, Response<ResultPostModel> response) {
                        try {
                            newarraydata = response.body().getResult();
                            arraydata.addAll(newarraydata);
                            adapter.notifyItemInserted(arraydata.size() - 1);
                            adapter.notifyDataSetChanged();
                            numberPage++;
                        } catch (Exception e) {
                        }

                    }

                    @Override
                    public void onFailure(Call<ResultPostModel> call, Throwable t) {
                        Toast.makeText(getActivity(), "no internet", Toast.LENGTH_SHORT).show();


                    }
                });


            }
            catch (Exception e){}
        }

    }
public void getWinerPost(){
try {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    final Call<ResultWinnerModel> connection;
    GetData moviesApi = retrofit.create(GetData.class);
    connection = moviesApi.getWinner();
    try {
        connection.enqueue(new Callback<ResultWinnerModel>() {
            @Override
            public void onResponse(Call<ResultWinnerModel> call, Response<ResultWinnerModel> response) {
                List<winnerModel> winner = new ArrayList<winnerModel>();
                winner = response.body().getResult();
                PostModel post = new PostModel();
                post.setPost_id(winner.get(0).getUser_id());
                post.setPost_tittle(winner.get(0).getUsername());
                post.setPost_desc("الفائز بجائزة الاسبوع اضغط هنا للحصول على جائزتك  ");
                post.setPost_attached(winner.get(0).getUser_pic());
                arraydata.add(post);
                adapter.notifyItemInserted(arraydata.size() - 1);
                adapter.notifyDataSetChanged();
                num = 1;
                String userid = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                        .getString("userid", "");
                boolean downloadPhoto = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                        .getBoolean("downloadPrize", true);
                if (userid.equals(winner.get(0).getUser_id()) && !winner.get(0).getPrize().equals("null") && downloadPhoto) {
                    getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                            .putString("prize_path", winner.get(0).getPrize()).apply();
                    Fragment fragment = new com.programs.lala.lala.fragments.CollectPoints();
                    FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                    trans.replace(R.id.mainFrame, fragment);
                    trans.addToBackStack("جمع النقاط");
                    trans.commit();
                }
            }

            @Override
            public void onFailure(Call<ResultWinnerModel> call, Throwable t) {
                Toast.makeText(getActivity(), "no internet", Toast.LENGTH_SHORT).show();
            }
        });


    } catch (Exception e) {
    }
}
catch (Exception e){}
}
    public void clearRecycle() {

       /* numberOfPage = 1;
        itemVisible = 0;
        movies.clear(); //clear list*/

        adapter.notifyDataSetChanged();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }}
