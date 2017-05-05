package com.programs.lala.lala.DataInterfaces;

import com.programs.lala.lala.models.Results.ResultBookModel;
import com.programs.lala.lala.models.Results.ResultFaxModel;
import com.programs.lala.lala.models.Results.ResultLoginModel;
import com.programs.lala.lala.models.Results.ResultPostModel;
import com.programs.lala.lala.models.Results.ResultWinnerModel;
import com.programs.lala.lala.models.winnerModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

/**
 * Created by melde on 3/13/2017.
 */
public interface GetData {



    @GET("postsByType/{type}/{page}")
    Call<ResultPostModel> getCatPost(@Path("type") String  type,@Path("page") int page);



    @Multipart
    @POST("user")
    Call<ResultLoginModel> login(@Part MultipartBody.Part file,
                                 @Part("file") RequestBody name, @Part("username") RequestBody username);

    @Multipart
    @POST("users")
    Call<ResultLoginModel> loginwithoutPhoto( @Part("username") RequestBody username);

    @Multipart
    @POST("pic")
    Call<ResponseBody> uploadWinnerImage(@Part MultipartBody.Part file,
                                 @Part("file") RequestBody name);


    @GET("posts/{page}")
    Call<ResultPostModel> getPosts(@Path("page") int page);

    @GET("books/{page}")
    Call<ResultBookModel> getBooks(@Path("page") int page);

    @Multipart
    @POST("bookSearch/")
    Call<ResultBookModel> searchBooks(@Part("book") RequestBody search);

    @GET("faqsById/{user_id}")
    Call<ResultFaxModel> getFaxs(@Path("user_id") String user_id);

    @Multipart
    @POST("store_comments/{post_id}")
    Call<ResponseBody> sendComment(@Path("post_id") int user_id, @Part("username") RequestBody username,@Part("comment") RequestBody comment,@Part("user_pic") RequestBody pic);

    @Multipart
    @POST("add_faq/{user_id}")
    Call<ResponseBody> makeQuestion(@Path("user_id") String user_id, @Part("faq") RequestBody fax);

    @GET("editUserPoint/{user_id}/{point}")
    Call<ResponseBody> updatePoint(@Path("user_id") String user_id, @Path("point") int point);
    @GET("getWinner")
    Call<ResultWinnerModel> getWinner();
}
