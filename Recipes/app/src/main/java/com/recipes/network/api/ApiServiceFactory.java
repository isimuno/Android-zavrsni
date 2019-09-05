package com.recipes.network.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiServiceFactory {

    public static ApiService getMealApiService() {
        return new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(createOkHttpClient())
                .build()
                .create(ApiService.class);
    }

    private static OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

}
