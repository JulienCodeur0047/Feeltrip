package com.packag.ezeety.interfaces;

import com.packag.ezeety.Pojos.Lieux;
import com.packag.ezeety.Pojos.LoginResponse;
import com.packag.ezeety.Pojos.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IWsServices {

    @GET("auth/login/")
    Call<User> GetUserLogin(@Path("email") String email, @Path("password") String password);

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> isValideUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("lieu/get_all")
    Call<Lieux> GetAllLieu();
}
