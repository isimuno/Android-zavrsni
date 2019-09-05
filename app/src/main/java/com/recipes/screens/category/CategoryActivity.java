package com.recipes.screens.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.recipes.R;
import com.recipes.screens.recipe.RecipeActivity;
import com.recipes.network.api.ApiServiceFactory;
import com.recipes.network.models.Meal;
import com.recipes.network.models.MealList;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    private MealsAdapter adapter;
    private Call<MealList> getMealsCall;
    private ArrayList<Meal> meals = new ArrayList<>();
    private String category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        category = getIntent().getStringExtra("category");
        initViews();
        initRecycler();
        getMeals();
    }

    private void initViews() {
        TextView categoryName = findViewById(R.id.categoryNameTextView);
        categoryName.setText(category);
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.mealsRecyclerView);
        adapter = new MealsAdapter(meals, meal -> {
            Intent intent = new Intent(CategoryActivity.this, RecipeActivity.class);
            intent.putExtra("id", meal.getIdMeal());
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getMeals() {
        getMealsCall = ApiServiceFactory.getMealApiService().getMeals(category);
        getMealsCall.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    meals.addAll(Arrays.asList(response.body().getMeals()));
                    adapter.notifyDataSetChanged();
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
        if (getMealsCall != null) {
            getMealsCall.cancel();
        }
    }
}
