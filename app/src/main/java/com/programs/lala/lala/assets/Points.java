package com.programs.lala.lala.assets;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.programs.lala.lala.DataInterfaces.GetData;
import com.programs.lala.lala.models.Results.ResultBookModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.programs.lala.lala.assets.constians.Base_Url;

/**
 * Created by melde on 3/19/2017.
 */

public class Points
{
    Context con;
    String user_id;

    public Points(Context c) {

    this.con=c;
        user_id= this.con.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("userid", "");
    }
    public  void updatePoints(int point){
        if (isNetworkAvailable()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(new constians().Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final Call<ResponseBody> connection;
            GetData moviesApi = retrofit.create(GetData.class);

            connection = moviesApi.updatePoint(user_id,point);

            connection.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.v("bb",user_id+"   1");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(con, "there is error try later", Toast.LENGTH_SHORT).show();



                }
            });

        }}
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
