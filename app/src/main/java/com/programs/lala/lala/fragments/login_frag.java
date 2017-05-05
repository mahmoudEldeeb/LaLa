package com.programs.lala.lala.fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.programs.lala.lala.DataInterfaces.GetData;
import com.programs.lala.lala.DataInterfaces.UploadImage;
import com.programs.lala.lala.R;
import com.programs.lala.lala.activties.base_activity;
import com.programs.lala.lala.loginactivty;
import com.programs.lala.lala.models.LoginModel;
import com.programs.lala.lala.models.Results.ResultLoginModel;
import com.programs.lala.lala.models.Results.ResultPostModel;
import com.programs.lala.lala.models.ServerResponseUploadImage;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static com.programs.lala.lala.assets.constians.Base_Url;


public class login_frag extends Fragment {
    private static Retrofit retrofit = null;
    public static int unique_id;
    String mediaPath;
    ProgressDialog progressDialog;
Button login_btn;
    ImageButton pick_Picture;
    public static Context context;
    final int REQUEST_CODE_ASK_PERMISSIONS=123;
    String id,image,username,prizepicture;
    EditText name;
    TextView repeatLogin;
    List<LoginModel>arrayData=new ArrayList<>();
    public login_frag( ) {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login_frag, container, false);
        requestPermision();
        requestPermision2();
        login_btn= (Button) view.findViewById(R.id.login_btn);
        pick_Picture=(ImageButton) view.findViewById(R.id.imageButton);
        name= (EditText) view.findViewById(R.id.editText);
        repeatLogin= (TextView) view.findViewById(R.id.repeat);
        progressDialog=new ProgressDialog(getActivity());
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPath!=null){
                loginFunction();}
                else
                {loginFunctionwithoutPhoto();}


            }
        });

        pick_Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //    requestPermision();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
                            }
        });
        //uploadFile();
        return view;
    }
public void requestPermision() {
    if (Build.VERSION.SDK_INT >= 23) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            // requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
           // return;
        }

    }
    else {/*Intent galleryIntent = new Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);*/
    }
}

public void requestPermision2(){
    if (Build.VERSION.SDK_INT >= 23) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            // requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            // return;
        }

    }
    else {/*Intent galleryIntent = new Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);*/
    }

}

    @Override
    public void onRequestPermissionsResult( int requestCode, String[] permissions,
                                            int[] grantResults){
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /*Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 0);
*/
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "لا يمكنك التقاط صور", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void comprees(){

        Bitmap bmp = BitmapFactory.decodeFile(mediaPath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,30, bos);
        InputStream in = new ByteArrayInputStream(bos.toByteArray());
        ContentBody foto = new InputStreamBody(in, "image/jpeg", "filename");
        File file=new File(mediaPath);
        // ContentBody mimePart = new ByteArrayBody(bos.toByteArray(), file.getName());

        File f1 = new File(Environment.getExternalStorageDirectory()
                + File.separator + file.getName()+".jpeg");

        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(f1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            foto.writeTo(fo);
            fo.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPath=f1.getPath();

    }



    public void  loginFunctionwithoutPhoto(){
    final String user_name=name.getText().toString();
    if (isNetworkAvailable()) {

        progressDialog.show();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final Call<ResultLoginModel> connection;
            GetData moviesApi = retrofit.create(GetData.class);


            RequestBody username1 = RequestBody.create(MediaType.parse("text/plain"), user_name);
            connection = moviesApi.loginwithoutPhoto(username1);

            connection.enqueue(new Callback<ResultLoginModel>() {
                @Override
                public void onResponse(Call<ResultLoginModel> call, Response<ResultLoginModel> response) {
                    // arrayData = response.body().getUser_id();
                    progressDialog.dismiss();
                    try{ arrayData = response.body().getUser();
                        id = arrayData.get(0).getUser_id();
                        //Toast.makeText(getActivity(),id,Toast.LENGTH_LONG).show();

                        if (!arrayData.get(0).getUser_id().equals("false")) {
                            progressDialog.dismiss();
                            id = arrayData.get(0).getUser_id();
                            username = user_name;
                            //image = arrayData.get(0).getUser_pic();
                            //progressDialog.dismiss();
                            //Log.v("gg","j22j");
                            save_data(username, image, id);
                            goToMain();
                        } else {
                           repeatLogin.setVisibility(View.VISIBLE);
                        }
                    }
                    catch (Exception e){}

                }

                @Override
                public void onFailure(Call<ResultLoginModel> call, Throwable t) {
                    progressDialog.dismiss();
                    if(isNetworkAvailable()){
                        Toast.makeText(getActivity(), "no internet", Toast.LENGTH_SHORT).show();}
                    else{
                        Toast.makeText(getActivity(), "الصورة كبيرة الحجم", Toast.LENGTH_SHORT).show();}
                }

            });
        }catch (Exception e){}
    }


}
    public void loginFunction() {
       final String user_name=name.getText().toString();
        if (isNetworkAvailable()) {

            progressDialog.show();

    // Map is used to multipart the file using okhttp3.RequestBody
    File file = new File(mediaPath);


    // Parsing any Media type file
    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
    final RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    final Call<ResultLoginModel> connection;
    GetData moviesApi = retrofit.create(GetData.class);


    RequestBody username1 = RequestBody.create(MediaType.parse("text/plain"), user_name);
    connection = moviesApi.login(fileToUpload, filename, username1);

    connection.enqueue(new Callback<ResultLoginModel>() {
        @Override
        public void onResponse(Call<ResultLoginModel> call, Response<ResultLoginModel> response) {
            progressDialog.dismiss();

                arrayData = response.body().getUser();
                 if(!arrayData.get(0).getUser_id().equals("false")){
                    progressDialog.dismiss();
                   id=arrayData.get(0).getUser_id();
                    username = user_name;
                    image = arrayData.get(0).getUser_pic();
                    progressDialog.dismiss();
                    save_data(username, image, id);
                    goToMain();
               }
                else
               { repeatLogin.setVisibility(View.VISIBLE);}


            }
        @Override
        public void onFailure(Call<ResultLoginModel> call, Throwable t) {
          if(!isNetworkAvailable()){
            Toast.makeText(getActivity(), "no internet", Toast.LENGTH_SHORT).show();}
            else{
              Toast.makeText(getActivity(), "الصورة كبيرة الحجم", Toast.LENGTH_SHORT).show();}
            progressDialog.dismiss();
        }
    });

    }}
    public void save_data(String username, String image, String id){
        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                .putString("userid", id).apply();

        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                .putString("imagePath", image).apply();
        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                .putString("prize_path", "").apply();
        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                .putString("username", username).apply();
        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                .putInt("points", 0).apply();

        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                .putBoolean("isfirstrun", false).apply();

    }
    public void goToMain(){
     try {
       /*  Fragment fragment = new com.programs.lala.lala.fragments.Main_frag();
         Bundle arguments = new Bundle();
         arguments.putString("type", "main");
         fragment.setArguments(arguments);
         FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
         trans.replace(R.id.mainFrame, fragment);
         trans.addToBackStack("main");
         trans.commit();
*/
         Intent loginintent = new Intent(getActivity(), base_activity.class);
         startActivity(loginintent);
     }catch (Exception e){}
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                cursor.close();
                Bitmap bitmap=BitmapFactory.decodeFile(mediaPath);
                Bitmap o=modifyOrientation(bitmap,mediaPath);
                modfyFormat(bitmap,mediaPath);
                cursor.close();
                pick_Picture.setImageBitmap(o);
                comprees();

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "قم بتفعيل الاذن للبرنامج لاخد صور", Toast.LENGTH_LONG).show();
        }

    }


public  void modfyFormat(Bitmap bmb, String mediaPath){

    File file = new File(mediaPath);
    try {
        FileOutputStream out = new FileOutputStream(file.getName());
        bmb.compress(Bitmap.CompressFormat.PNG, 50, out); //100-best quality
        out.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }

    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
public void convertPhoto(){

}
}
