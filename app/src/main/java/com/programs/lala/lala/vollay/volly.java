package com.programs.lala.lala.vollay;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by UsersFiles on 10/14/2016.
 */
public class volly  {
    private static volly instatnce;
    private RequestQueue requestQueue;
    private Context context;
    public volly(Context context){
this.context=context;
        requestQueue =getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());

        }
        return requestQueue;
    }
 public static  synchronized volly getinsance(Context context){
     if(instatnce==null){
         instatnce=new volly(context);
     }
     return instatnce;
 }

 public <T> void add_request(Request<T> newrequest){
     requestQueue.add(newrequest);
 }

}
