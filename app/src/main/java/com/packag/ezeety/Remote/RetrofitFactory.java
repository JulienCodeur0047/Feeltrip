package com.packag.ezeety.Remote;

import android.text.TextUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

  public static final String API_BASE_URL = "http://192.162.69.209:3001/api/";
  private static Retrofit retrofit = null;
  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
  private static Retrofit.Builder builder =
          new Retrofit.Builder()
                  .baseUrl(API_BASE_URL)
                  .addConverterFactory(GsonConverterFactory.create());
  public static <S> S createService(Class<S> serviceClass) {
    return createService(serviceClass, null);
  }
  public static <S> S createService(
          Class<S> serviceClass, final String authToken) {
    if (!TextUtils.isEmpty(authToken)) {
      AuthenticationInterceptor interceptor =
              new AuthenticationInterceptor(authToken);

      if (!httpClient.interceptors().contains(interceptor)) {
        httpClient.addInterceptor(interceptor);

        builder.client(httpClient.build());
        retrofit = builder.build();
      }
    }

    return retrofit.create(serviceClass);
  }
  private RetrofitFactory(){}

  public static Retrofit getRetrofit(){

    if(retrofit == null){
      retrofit = new Retrofit.Builder()
              .baseUrl(API_BASE_URL)
              .addConverterFactory(GsonConverterFactory.create()).build();
    }
    return retrofit;
  }
}