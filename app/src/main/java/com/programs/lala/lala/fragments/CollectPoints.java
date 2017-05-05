package com.programs.lala.lala.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.programs.lala.lala.R;
import com.programs.lala.lala.activties.Download;
import com.programs.lala.lala.assets.Points;
import com.programs.lala.lala.assets.communication;
import com.programs.lala.lala.assets.constians;
import com.squareup.picasso.Picasso;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

/**
 * A simple {@link Fragment} subclass.
 */

public class CollectPoints extends Fragment  {


    InterstitialAd mInterstitialAd;
    InterstitialAd interstitial;
    public CollectPoints() {
        // Required empty public constructor
    }

Button share;
    int points;
    Button video;
    final int RESULT_OF_SHARE=0;
    ImageView imageView;
     ProgressDialog progressDialog;
Button download;
    communication communication;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_collect_points, container, false);
        download= (Button) view.findViewById(R.id.button2);
        ///////////////////
        communication = (com.programs.lala.lala.assets.communication) getActivity();


        progressDialog=new ProgressDialog(getActivity());
        final String prize= getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("prize_path", "");
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Download.class);
                Bundle bundle1=new Bundle();
                bundle1.putInt("point",0);
                bundle1.putString("path",new constians().Base_Url+prize);
                intent.putExtras(bundle1);
                if(prize.equals("")){Toast.makeText(getActivity(),"لم تفز بجائزة بعد ",Toast.LENGTH_SHORT).show();}
               else startActivity(intent);

            }
        });
        imageView= (ImageView) view.findViewById(R.id.imageView3);
              try {
                  Picasso.with(getActivity())
                          .load(String.valueOf(new constians().Base_Url + prize)).fit()
                          .into(imageView);
                  getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                          .putBoolean("downloadPrize", false).apply();


              }
              catch (Exception e){}
        video= (Button) view.findViewById(R.id.button8);

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 watchVideo();
            }
        });
        share = (Button) view.findViewById(R.id.button3);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLink();

            }
        });
        return view;
    }
    public  void watchVideo(){
       // Intent i=new Intent(getActivity(), Ads.class);
        ////startActivity(i);

        if(isOnline()) {
            progressDialog.show();
            interstitial = new InterstitialAd(getActivity());
            interstitial.setAdUnitId("ca-app-pub-2817140095934646/2298043214");
            AdRequest adRequest = new AdRequest.Builder().build();

            interstitial.loadAd(adRequest);
            interstitial.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    // Call displayInterstitial() function
                    displayInterstitial();
                }
            });

        }
        else Toast.makeText(getActivity(),"no internet",Toast.LENGTH_SHORT).show();

    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

public  void ShareLink(){
    Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
    if (intent != null) {
        // The application exists
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TITLE, "asd");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.programs.lala.lala");
        // Start the specific social application
startActivityForResult(shareIntent,0);
        //startActivity(shareIntent);
    } else {
        // The application does not exist
        // Open GooglePlay or use the default system picker

        Toast.makeText(getActivity().getApplicationContext(),
                "Installed application first", Toast.LENGTH_LONG).show();
    }

}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sendPointsToServer();
    }

    public void sendPointsToServer(){

        int points= getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getInt("points", 0);
        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                .putInt("points", points+15).apply();
        Points p=new Points(getActivity());

        communication.updatepoints(points+15);

        p.updatePoints(points+15);

    }
    public void displayInterstitial() {

        if (interstitial.isLoaded()) {
            interstitial.show();
            progressDialog.dismiss();
            sendPointsToServer();
        }
    }
    private void beginPlayingGame() {
        // Play for a while, then display the New Game Button
    }
    private void requestNewInterstitial() {

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);


    }


}
