package com.programs.lala.lala.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.programs.lala.lala.DataInterfaces.GetData;
import com.programs.lala.lala.R;
import com.programs.lala.lala.adapters.RecyclerView_adapter;
import com.programs.lala.lala.adapters.match_adapter;
import com.programs.lala.lala.assets.constians;
import com.programs.lala.lala.models.MainData;
import com.programs.lala.lala.models.MatchsModel;
import com.programs.lala.lala.models.Results.ResultMatchesModel;
import com.programs.lala.lala.vollay.Parsing_Json;
import com.programs.lala.lala.vollay.volly;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesOfDay extends Fragment {

    String Base_Url;
    RecyclerView match_recyle;
    match_adapter adapter;
    int type=0;
    String s="ddd" ;

    ArrayList<MatchsModel> arraydata;
    ArrayList<MatchsModel> newarraydata=new ArrayList<>();
    public MatchesOfDay() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_matches_of_day, container, false);
        MobileAds.initialize(getActivity().getApplicationContext(), String.valueOf(getResources().getText(R.string.app_id)));
        Base_Url =getResources().getString(R.string.BASE_URL)+ "matches/0";
        AdView mAdView = (AdView) view.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView mAdView1 = (AdView) view.findViewById(R.id.adView2);

        mAdView1.loadAd(adRequest);



        arraydata=new ArrayList<>();
        match_recyle= (RecyclerView) view.findViewById(R.id.match_recyle);
        match_recyle.setLayoutManager(new LinearLayoutManager(getActivity()));





        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,Base_Url
            ,null    , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                s =response.toString();
                List<MatchsModel> arr =new Parsing_Json(s).getListOfMoves();
                if (arr.size()>0) {
                   // Toast.makeText(getActivity(), arr.size() + "bakkkkkkkr", Toast.LENGTH_LONG).show();
                    adapter   = new match_adapter(getActivity(), arr);
                    match_recyle.setAdapter(adapter );
                    GridLayoutManager grid = new GridLayoutManager(getActivity(),1);
                    match_recyle.setLayoutManager(grid);
                    adapter.notifyDataSetChanged();
                }else{ Toast.makeText(getActivity(),"no internet",Toast.LENGTH_LONG).show();
                }
                  //  Toast.makeText(getActivity(), "Errrrrrrror",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"no internet",Toast.LENGTH_LONG).show();

            }
        });

        volly.getinsance(getActivity()).add_request(jsonObjectRequest);














        return view ;
    }

}
