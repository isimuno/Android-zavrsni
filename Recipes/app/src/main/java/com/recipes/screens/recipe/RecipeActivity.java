package com.recipes.screens.recipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.recipes.R;
import com.recipes.network.api.ApiServiceFactory;
import com.recipes.network.models.Meal;
import com.recipes.network.models.MealList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    private TextView recipeName;
    private TextView recipeInstructions;
    private ImageView recipeImage;
    private Call<MealList> getRecipeCall;
    private String recipeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeId = getIntent().getStringExtra("id");
        initViews();
        getRecipe();
    }

    private void initViews() {
        recipeName = findViewById(R.id.recipeNameTextView);
        recipeImage = findViewById(R.id.recipeImage);
        recipeInstructions = findViewById(R.id.recipeInstructions);
    }

    private void getRecipe() {
        getRecipeCall = ApiServiceFactory.getMealApiService().getRecipe(recipeId);
        getRecipeCall.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Meal meal = response.body().getMeals()[0];
                    recipeName.setText(meal.getStrMeal());
                    recipeInstructions.setText(meal.getStrInstructions());
                    Glide.with(recipeImage)
                            .load(meal.getStrMealThumb())
                            .into(recipeImage);
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getRecipeCall != null) {
            getRecipeCall.cancel();
        }
    }
}
