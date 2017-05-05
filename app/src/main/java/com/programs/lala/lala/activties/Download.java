package com.programs.lala.lala.activties;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.programs.lala.lala.R;
import com.programs.lala.lala.assets.Points;

public class Download extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    String path="http://bookapp.esy.es/appbook/16830813_169142883587638_3393782504522167518_n.jpg";
    Intent in=new Intent(this,base_activity.class);
    int pointToDownload=0;
    String title="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Intent intent=getIntent();
        path="";
        Bundle bundle=intent.getExtras();
        path = bundle.getString("path");
        title=bundle.getString("name");

        pointToDownload =bundle.getInt("point");
        int points1= this.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getInt("points", 0);
        if(points1>=pointToDownload){
        if (intent != null) {
            if (Build.VERSION.SDK_INT >= 23)
            {
                if (checkPermission())
                {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse( path) );

                    request.setTitle(title);
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    String myfile = URLUtil.guessFileName( path,null, MimeTypeMap.getFileExtensionFromUrl(path));
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,myfile);
                    DownloadManager DownloadManager= (android.app.DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.enqueue(request);
                    lessPoints();
                } else {
                    requestPermission(); // Code for permission
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse( path) );

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    String myfile = URLUtil.guessFileName( path,null, MimeTypeMap.getFileExtensionFromUrl(path));
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,myfile);
                    request.setTitle(myfile);

                    DownloadManager DownloadManager= (android.app.DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.enqueue(request);
                    lessPoints();

                }
            }
            else
            {

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse( path) );

                request.setTitle(title);
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                String myfile = URLUtil.guessFileName( path,null, MimeTypeMap.getFileExtensionFromUrl(path));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,myfile);
                DownloadManager DownloadManager= (android.app.DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.enqueue(request);
                lessPoints();

            }
        }

    }
    else {
            Toast.makeText(getBaseContext(),"ليس لديك نقاط كافية للتحميل",Toast.LENGTH_LONG).show();
    finish();
    }

    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
    public void lessPoints(){

        finish();
       }
}
