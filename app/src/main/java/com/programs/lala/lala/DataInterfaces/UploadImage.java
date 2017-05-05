package com.programs.lala.lala.DataInterfaces;

import com.programs.lala.lala.models.ServerResponseUploadImage;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by melde on 3/15/2017.
 */

public interface UploadImage {
    @Multipart
    @POST("/retrofit_tutorial/retrofit_client.php")
    Call<ServerResponseUploadImage> uploadFile(@Part("file1") MultipartBody.Part file, @Part("file") RequestBody name);


}
