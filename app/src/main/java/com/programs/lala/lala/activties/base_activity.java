package com.programs.lala.lala.activties;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.programs.lala.lala.R;
import com.programs.lala.lala.assets.communication;
import com.programs.lala.lala.assets.constians;
import com.programs.lala.lala.fragments.AnswerOfQuestion;
import com.programs.lala.lala.fragments.Book_Show;
import com.programs.lala.lala.fragments.CollectPoints;
import com.programs.lala.lala.fragments.Main_frag;
import com.programs.lala.lala.fragments.MatchesOfDay;
import com.programs.lala.lala.fragments.Post_show_frag;
import com.programs.lala.lala.fragments.make_question;
import com.programs.lala.lala.loginactivty;
import com.programs.lala.lala.models.MatchsModel;
import com.programs.lala.lala.models.PostModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

import static android.R.attr.baseline;
import static android.R.attr.path;

////E/VdcInflateDelegate? Exception while inflating <vector>
//org.xmlpull.v1.XmlPullParserException: Binary XML file line #1<vector> tag requires viewportWidth > 0
public class base_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,communication {
     android.support.v4.app.FragmentManager manager ;

    NavigationView navigationView;
    List<String> tittles=new ArrayList<>();
    int user_point;
    int pos=0;
String userid,imagePath,prize_path,username;
    Toolbar toolbar ;
    String namepage="الصفحة الرئيسية";
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(namepage);
        tittles.add(pos++,"الصفحة الرئيسية");
       //setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

       manager=getSupportFragmentManager();
        Boolean isFirstRun = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getBoolean("isfirstrun", true);
        Boolean uploadPhoto = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getBoolean("uploadPhoto", true);
        boolean down=getBaseContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getBoolean("downloadPrize", true);
          userid = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("userid", "");
         imagePath = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("imagePath", "");
             prize_path = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("prize_path", "");
        username = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("username", "");
         user_point = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getInt("points",0);
        putUserData();

        if (isFirstRun) {
          Intent loginintent =new Intent(base_activity.this,loginactivty.class);
            startActivity(loginintent);


        } else {
           Fragment fragment =new com.programs.lala.lala.fragments.Main_frag();
            Bundle arguments = new Bundle();
            arguments.putString("type","main");
            fragment.setArguments(arguments);
            FragmentTransaction trans=  this.getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.mainFrame,fragment);
            trans.addToBackStack("main");
            trans.commit();
         }


    }
    @Override
    public void onBackPressed() {

        //toolbar.setTitle(namepage);
            // add your code here
        if(manager.getBackStackEntryCount() == 1) {
            manager.popBackStack();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);}
        if(pos==tittles.size()){pos-=2;
        }
        if(pos>0) {

            toolbar.setTitle(tittles.get(pos));
            pos--;
        }
        else if(pos==0){
            toolbar.setTitle(tittles.get(pos));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(manager.getBackStackEntryCount() != 1) {
                manager.popBackStack();
            }
            else

            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        displayMenu(item.getItemId());
          return true;
    }




    @Override
    public void resoponse(String path, String name, String points) {
        Intent intent=new Intent(this, Download.class);
        intent.putExtra("name",name);
        intent.putExtra("path",new constians().Base_Url+path);
        intent.putExtra("point",Integer.parseInt(points));
        int points1= this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getInt("points", 0);
        startActivity(intent);

        //putUserData();
    }
    @Override
    public void resoponse(int path) {
        Fragment fragment =new make_question();
        Bundle arguments = new Bundle();
        arguments.putInt("type",path);
        fragment.setArguments(arguments);
        FragmentTransaction trans= manager.beginTransaction();
        trans.replace(R.id.mainFrame,fragment);
        trans.addToBackStack("post");
        trans.commit();
    }

    @Override
    public void resoponse() {
        Intent intent=new Intent(this, UploadWinnerPhoto.class);

       // intent.putExtra("path",new constians().Base_Url+path);
        startActivity(intent);
    }

    @Override
    public void updatepoints(int p) {
        putUserData();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void displayMenu(int ID){
        Fragment fragment =null;
        switch (ID)
        {
            case R.id.nav_main:
                fragment =new Main_frag();
                if (fragment!=null)
                {
                    toolbar.setTitle("الصفحه الرئسيه");
                    tittles.add(pos++,"الصفحة الرئيسية");
                    Bundle arguments = new Bundle();
                    arguments.putString("type","main");
                    fragment.setArguments(arguments);
                    FragmentTransaction trans= manager.beginTransaction();
                    trans.replace(R.id.mainFrame,fragment);
                    trans.addToBackStack("Main");
                    trans.commit();
                }
                break;
            case R.id.nav_book:
                fragment =new Book_Show();
                if (fragment!=null)
                {
                    toolbar.setTitle("الكتب والمجلات");
                    tittles.add(pos++,"الكتب والمجلات");
                    Bundle arguments = new Bundle();
                    arguments.putInt("cat",0);
                    fragment.setArguments(arguments);
                    FragmentTransaction trans= manager.beginTransaction();
                    trans.replace(R.id.mainFrame,fragment);
                    trans.addToBackStack("book");
                    trans.commit();
                }
                break;


            case R.id.nav_question:
                fragment =new AnswerOfQuestion();
                if (fragment!=null)
                {
                    toolbar.setTitle("الاستفسارات");
                    tittles.add(pos++,"الاستفسارات");
                    FragmentTransaction trans= manager.beginTransaction();
                    trans.replace(R.id.mainFrame,fragment);
                    trans.addToBackStack("AnswerOfQuestion");
                    trans.commit();
                }
                break;
            case R.id.nav_science:
                fragment =new Main_frag();
                if (fragment!=null)
                {
                    toolbar.setTitle("العلوم والطبيعه");
                    tittles.add(pos++,"العلوم والطبيعة");
                    Bundle arguments = new Bundle();
                    arguments.putString("type","science");
                    fragment.setArguments(arguments);
                    FragmentTransaction tr= manager.beginTransaction();
                    tr.replace(R.id.mainFrame,fragment);
                    tr.addToBackStack("scince");
                    tr.commit();
                }





                break;
            case R.id.nav_sports:
                fragment =new Main_frag();
                if (fragment!=null)
                {
                    toolbar.setTitle("الرياضه");
                    tittles.add(pos++,"الرياضة");
                    Bundle arguments = new Bundle();
                    arguments.putString("type","sports");
                    fragment.setArguments(arguments);
                    FragmentTransaction tr= manager.beginTransaction();
                    tr.replace(R.id.mainFrame,fragment);
                    tr.addToBackStack("sport");
                    tr.commit();
                }
                break;

            case R.id.nav_technoloy:

                fragment =new Main_frag();
                if (fragment!=null)
                {
                    toolbar.setTitle("التكنولوجيا");
                    tittles.add(pos++,"التكنولوجيا");
                    Bundle arguments = new Bundle();
                    arguments.putString("type","tech");
                    fragment.setArguments(arguments);
                    FragmentTransaction tr= manager.beginTransaction();
                    tr.replace(R.id.mainFrame,fragment);
                    tr.addToBackStack("tech");
                    tr.commit();
                }

                break;
            case R.id.nav_collect_points:
                fragment =new CollectPoints();
                if (fragment!=null)
                {
                    toolbar.setTitle("جمع النقاط");
                    tittles.add(pos++,"جمع النقاط");
                    FragmentTransaction tr= manager.beginTransaction();
                    tr.replace(R.id.mainFrame,fragment);
                    tr.addToBackStack("CollectPoints");
                    tr.commit();
                }

                break;
            case R.id.nav_orderBook:
                fragment =new make_question();
                if (fragment!=null)
                {
                    toolbar.setTitle("اطلب ");
                    tittles.add(pos++,"اطلب");
                    Bundle arguments = new Bundle();
                    arguments.putInt("type",1);
                    fragment.setArguments(arguments);
                    FragmentTransaction tr= manager.beginTransaction();
                    tr.replace(R.id.mainFrame,fragment);
                    tr.addToBackStack("make_question");
                    tr.commit();
                }


                break;

            case R.id.nav_matches_today:
                toolbar.setTitle("مباريات اليوم");
                tittles.add(pos++,"مبارايات اليوم");
                fragment =new MatchesOfDay();
            FragmentTransaction trans= manager.beginTransaction();
            trans.replace(R.id.mainFrame,fragment);
            trans.addToBackStack("matches");
            trans.commit();
            break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void resoponse(PostModel mainData) {
        Fragment fragment =new Post_show_frag();

//
        Bundle arguments = new Bundle();
        arguments.putParcelable("model",mainData);// putExtra("moveDetails", model);

        fragment.setArguments(arguments);
        FragmentTransaction trans= manager.beginTransaction();
        trans.replace(R.id.mainFrame,fragment);
        trans.addToBackStack("post");
        trans.commit();
    }
public  void putUserData(){
    View hView =  navigationView.getHeaderView(0);
    ImageView image_user = (ImageView) hView.findViewById(R.id.user_image);
    TextView point = (TextView) hView.findViewById(R.id.textView);
    TextView name= (TextView) hView.findViewById(R.id.name);
    name.setText(username);
    int point2 = this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            .getInt("points",0);
    point.setText(point2+getResources().getString(R.string.points_number));

   try {
       Picasso.with(this)
               .load(String.valueOf(new constians().Base_Url + imagePath)).fit()
               .into(image_user);

   }
   catch (Exception e){}
}



    @Override
    public void Resopnse_MatchesModel(MatchsModel mainData) {

    }




}
