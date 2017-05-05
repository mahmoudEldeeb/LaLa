package com.programs.lala.lala.fragments;


import android.app.SearchManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.programs.lala.lala.DataInterfaces.GetData;
import com.programs.lala.lala.R;
import com.programs.lala.lala.adapters.Book_Adapter;
import com.programs.lala.lala.adapters.RecyclerView_adapter;
import com.programs.lala.lala.models.BookModel;
import com.programs.lala.lala.models.PostModel;
import com.programs.lala.lala.models.Results.ResultBookModel;
import com.programs.lala.lala.models.Results.ResultPostModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.SEARCH_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Book_Show extends Fragment  {

    String Base_Url = "";
    RecyclerView main_recyle;
    Book_Adapter adapter;
    int type=0;
    ArrayList<BookModel> arraydata=new ArrayList<>();
    ArrayList<BookModel> newarraydata=new ArrayList<>();
    int itemVisible=0;
    public Book_Show() {
        // Required empty public constructor
    }

Toolbar toolbar;
    Button search;
    EditText name;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_book__show, container, false);
Base_Url=getResources().getString(R.string.BASE_URL);
        MobileAds.initialize(getActivity().getApplicationContext(), String.valueOf(getResources().getText(R.string.app_id)));
        AdView mAdView = (AdView) view.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        search= (Button) view.findViewById(R.id.button2);
        name= (EditText) view.findViewById(R.id.editText3);
search.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        searchBook(name.getText().toString());
    }
});
        main_recyle= (RecyclerView) view.findViewById(R.id.main_recycle);
        setHasOptionsMenu(true);

                toolbar= (Toolbar) view.findViewById(R.id.searchbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        main_recyle.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new Book_Adapter( getActivity(),arraydata);
        getBook();
        main_recyle.setAdapter(adapter);

        main_recyle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

                if (lastVisiblePosition + 1 == itemVisible + 20) {
                    getBook();
                    itemVisible += 20;
                }
            }

        });



        return view;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchbar, menu);

    }
    public  void searchBook(String s){

    RequestBody book = RequestBody.create(MediaType.parse("text/plain"),s);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    final Call<ResultBookModel> connection;
    GetData moviesApi = retrofit.create(GetData.class);

    connection = moviesApi.searchBooks(book);

    connection.enqueue(new Callback<ResultBookModel>() {
        @Override
        public void onResponse(Call<ResultBookModel> call, Response<ResultBookModel> response) {

            itemVisible = 0;
            arraydata.clear();
            arraydata = response.body().getResult();
            adapter = new Book_Adapter(getActivity(), arraydata);
            main_recyle.setAdapter(adapter);
        }

        @Override
        public void onFailure(Call<ResultBookModel> call, Throwable t) {
            Toast.makeText(getActivity(), "there is error try later", Toast.LENGTH_SHORT).show();
        }
    });



    }
    public  void getBook(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Call<ResultBookModel> connection;
        GetData moviesApi = retrofit.create(GetData.class);


        connection = moviesApi.getBooks(0);

        connection.enqueue(new Callback<ResultBookModel>() {
            @Override
            public void onResponse(Call<ResultBookModel> call, Response<ResultBookModel> response) {
                arraydata =  response.body().getResult();

                adapter = new Book_Adapter(getActivity(),arraydata);
                main_recyle.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResultBookModel> call, Throwable t) {
                Toast.makeText(getActivity(), "there is error try later", Toast.LENGTH_SHORT).show();



            }
        });


    }
}
