package com.example.android.bakingapp.api;

import com.example.android.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingService {
    @GET("android-baking-app-json")
    Call<List<Recipe>> listRecipe();
}
