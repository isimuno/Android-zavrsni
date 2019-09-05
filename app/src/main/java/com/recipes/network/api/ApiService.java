package com.recipes.network.api;

import com.recipes.network.models.CategoryList;
import com.recipes.network.models.MealList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/json/v1/1/list.php?c=list")
    Call<CategoryList> getCategories();

    @GET("api/json/v1/1/filter.php")
    Call<MealList> getMeals(@Query("c") String category);

    @GET("api/json/v1/1/lookup.php")
    Call<MealList> getRecipe(@Query("i") String id);

}

