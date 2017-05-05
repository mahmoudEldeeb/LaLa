package com.programs.lala.lala.activties;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.programs.lala.lala.DataInterfaces.GetData;
import com.programs.lala.lala.R;
import com.programs.lala.lala.models.Results.ResultLoginModel;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.programs.lala.lala.assets.constians.Base_Url;

public class UploadWinnerPhoto extends AppCompatActivity {
Button upload;
    ImageButton imageView;
    String mediaPath;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_winner_photo);
        upload= (Button) findViewById(R.id.button6);
        imageView= (ImageButton) findViewById(R.id.upload_image);
        progressDialog=new ProgressDialog(getBaseContext());
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFunction();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
    }


    public void uploadFunction() {
        if (isNetworkAvailable()) {

//            progressDialog.show();
            try {
                // Map is used to multipart the file using okhttp3.RequestBody
                File file = new File(mediaPath);
                // Parsing any Media type file

                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                final RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl((String) getResources().getText(R.string.BASE_URL))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final Call<ResponseBody> connection;
                GetData moviesApi = retrofit.create(GetData.class);
                connection = moviesApi.uploadWinnerImage(fileToUpload, filename);
                connection.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String res = response.body().string();
                            Toast.makeText(getBaseContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                            if (res.equals("true")) {

                                getBaseContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                                        .putBoolean("uploadPhoto", true).apply();
                                getBaseContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                                        .putBoolean("downloadPrize", true).apply();

                            } else
                                Toast.makeText(getBaseContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "there is error try later", Toast.LENGTH_SHORT).show();
                        //   progressDialog.dismiss();

                    }
                });

            }
            catch (Exception e){}
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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

                Cursor cursor = getBaseContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                Bitmap bitmap= BitmapFactory.decodeFile(mediaPath);
                Bitmap o=modifyOrientation(bitmap,mediaPath);
                modfyFormat(bitmap,mediaPath);
                imageView.setImageBitmap(o);

                cursor.close();
                comprees();

            } else {
                Toast.makeText(getBaseContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_LONG).show();
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
}
