package com.packag.ezeety.Services;

import com.packag.ezeety.model.GooglePlaces;
import com.packag.ezeety.model.Lieux;
import com.packag.ezeety.model.LoginResponse;
import com.packag.ezeety.model.MessageBodyHeader;
import com.packag.ezeety.model.User;
import com.packag.ezeety.model.UserPasswordReset;
import com.packag.ezeety.model.UserSignUpEzeety;
import com.packag.ezeety.model.UserSocialNetwork;
import com.packag.ezeety.model.Users;
import com.packag.ezeety.model.Villes;

import java.io.File;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @POST("users/sign_up_2_social_network")
    Call<UserSocialNetwork> RegisterBySosialNetwork(@Field("email") String email,
                                                    @Field("nom_prenom") String name_firstName,
                                                    @Field("profile_picture") String profile_picture);


    @FormUrlEncoded
    @POST("search/ville")
    Call<Villes> getVillesAutoCompletion(@Field("ville") String ville);


    @FormUrlEncoded
    @POST("users/check_email")
    Call<MessageBodyHeader> isEmailAlready(@Field("user_email") String email);

    @FormUrlEncoded
    @POST("users/verify_email")
    Call<MessageBodyHeader> isEmailAlreadyForgotpasswrd(@Field("user_email") String email);


    @GET("https://maps.googleapis.com/maps/api/place/autocomplete/json?types=(cities)&language=fr&key=AIzaSyAd0PUK7nWw1L-mp_KU2al5aepqzXPrwCA")
    Call<GooglePlaces> getPlacesAutocompletion(@Query("input") String cityName);

    @FormUrlEncoded
    @POST("users/reset_password")
    Call<UserPasswordReset> resetPassword(@Field("user_id") int user_id,
                                          @Field("password") String password,
                                          @Field("reset_code") String reset_code);

    @FormUrlEncoded
    @POST("users/reset_password")
    Call<MessageBodyHeader> resetPassword2(@Field("user_id") int user_id,
                                          @Field("password") String password,
                                          @Field("reset_code") String reset_code);

    @FormUrlEncoded
    @POST("users/check_username")
    Call<MessageBodyHeader> checkUserName(@Field("username") String username);


    @Multipart
    //@Headers("Content-Type: multipart/form-data")
    @POST("users/sign_up_2_ezeety")
    Call<UserSignUpEzeety> singInUptoEzeeety(@Part("username") RequestBody username,
                                             @Part("nom_prenom") RequestBody nom_prenom,
                                             @Part("email") RequestBody email,
                                             @Part("password") RequestBody password,
                                             @Part("date_naissance") RequestBody date_naissance,
                                             @Part("google_place_id") RequestBody place_id,
                                             @Part("ville") RequestBody ville,
                                             @Part("pays") RequestBody pays,
                                             @Part("profile_picture") File file
                                             );

    @FormUrlEncoded
    @POST("users/sign_up_2_social_network")
    Call<MessageBodyHeader> sinUptoSocialNetwork(@Field("email") String email,
                                                 @Field("nom_prenom") String nom_prenom,
                                                 @Field("profile_picture") String uri_profil_picture);
}
