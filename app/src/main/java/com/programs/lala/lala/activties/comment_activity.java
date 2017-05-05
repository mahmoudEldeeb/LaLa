package com.programs.lala.lala.activties;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.programs.lala.lala.R;
import com.programs.lala.lala.adapters.CommentRecycleAdapter;
import com.programs.lala.lala.adapters.match_adapter;
import com.programs.lala.lala.models.CommentModel;
import com.programs.lala.lala.models.MatchsModel;
import com.programs.lala.lala.vollay.Parsing_Json;
import com.programs.lala.lala.vollay.parsing_comment;
import com.programs.lala.lala.vollay.volly;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.programs.lala.lala.R.id.match_recyle;
import static com.programs.lala.lala.fragments.login_frag.context;

/**
 * Created by UsersFiles on 10/8/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class comment_activity extends DialogFragment {
    RecyclerView comment_recycle;
int numberpage=0;
    String Base_Url = "";

    String Base_Url1 ;
    CommentRecycleAdapter adapter;
    int type=0;

    int itemVisible=0;
    String s="ddd" ;
    List<CommentModel> arr = new ArrayList<>();
    List<CommentModel> arraydata=new ArrayList<>();
    String post_id;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog =getDialog();
        if (dialog!=null )
        {
            int width =ViewGroup.LayoutParams.MATCH_PARENT;
            int height =ViewGroup.LayoutParams.MATCH_PARENT;
 dialog.getWindow().setLayout(width,height);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Comments  ");
          View view =inflater.inflate(R.layout.comment_activty,container,false);
        Base_Url1 = getResources().getString(R.string.BASE_URL)+"/comments/";
    Bundle bundle = new Bundle();
    bundle = this.getArguments();
    post_id = bundle.getString("post_id");

    comment_recycle = (RecyclerView) view.findViewById(R.id.comments_recyle);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    comment_recycle.setLayoutManager(layoutManager);
        GridLayoutManager grid = new GridLayoutManager(getActivity(), 1);
        comment_recycle.setLayoutManager(grid);
        getcomments();
        adapter = new CommentRecycleAdapter(getActivity(), arraydata);
        comment_recycle.setAdapter(adapter);
        comment_recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisiblePosition + 1 == itemVisible + 20) {
                    getcomments();
                    itemVisible += 20;
                }
            }

        });
        return view ;
    }


    public void getcomments(){
        try{
            Base_Url=Base_Url1+numberpage+"/"+ post_id;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Base_Url
                    , null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    s = response.toString();
                   arr= new parsing_comment(s).getListOfMoves();
                    if (arr.size() > 0) {
                     //   Log.v("ggg","rr");
                        arraydata.addAll(arr);
                        adapter.notifyItemInserted(arraydata.size() - 1);
                        adapter.notifyDataSetChanged();
                        numberpage++;
                        Base_Url="";
                    } else {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "no internet", Toast.LENGTH_LONG).show();

                    Base_Url="";
                }
            });

            volly.getinsance(getActivity()).add_request(jsonObjectRequest);
        }
        catch (Exception e){}

    }
}
