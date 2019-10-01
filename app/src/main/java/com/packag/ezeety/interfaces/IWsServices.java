package com.packag.ezeety.interfaces;

import com.packag.ezeety.Pojos.Lieux;
import com.packag.ezeety.Pojos.LoginResponse;
import com.packag.ezeety.Pojos.MessageBodyHeader;
import com.packag.ezeety.Pojos.User;
import com.packag.ezeety.Pojos.Users;
import com.packag.ezeety.Pojos.Villes;

import java.util.Date;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IWsServices {

    @GET("auth/login/")
    Call<User> GetUserLogin(@Path("email") String email, @Path("password") String password);

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> isValideUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("sign_in_with_ezeety") int sign_in_with_ezeety
    );

    //@FormUrlEncoded
    @GET("auth/login")
    Call<LoginResponse> getToken();

    @GET("lieux/type")
    Call<Lieux> GetAllLieu();

    @GET("auth/login")
    Call<LoginResponse> getTokenForOnUser();

    @GET("user/get_all")
    Call<Users> getListUser();


    @Multipart
    @FormUrlEncoded
    @POST("user/sign_up_2_ezeety")
    Call<User> signUp(@Part("profile_picture") MultipartBody.Part file,
                      @Field("name_firstname") String nomPrenom,
                      @Field("password") String password,
                      @Field("birthday")Date dateNaissance,
                      @Field("email") String email,
                      @Field("hometown") Integer ville);


    @FormUrlEncoded
    @POST("search/ville")
    Call<Villes> getVillesAutoCompletion(@Field("ville") String ville);


    @FormUrlEncoded
    @POST("users/check_email")
    Call<MessageBodyHeader> isEmailAlready(@Field("user_email") String email);

    @FormUrlEncoded
    @POST("users/verify_email")
    Call<MessageBodyHeader> isEmailAlready002(@Field("user_email") String email);
}
