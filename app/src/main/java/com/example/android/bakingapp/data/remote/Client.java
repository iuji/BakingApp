package com.example.android.bakingapp.data.remote;

import com.example.android.bakingapp.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public Client() {
    }

    public static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
