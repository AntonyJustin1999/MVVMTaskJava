package com.test.app.LoadMaps.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.app.LoadMaps.API.ApiCall;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    public static String BaseUrl = "https://restcountries.com/";

    public RestManager(){
    }

    public ApiCall getAPI() throws Throwable {
        try{
            Retrofit retrofit = null;

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.cache(null);
            httpClient.addInterceptor(logging);
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            if (retrofit == null) {
                String apiUrl = BaseUrl;
                retrofit = new Retrofit.Builder()
                        .baseUrl(apiUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(httpClient.build())
                        .build();
            }
            return retrofit.create(ApiCall.class);
        } catch (Exception e){
            throw new Throwable(e);
        }
    }
}
