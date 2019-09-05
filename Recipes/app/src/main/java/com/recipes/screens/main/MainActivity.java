package com.recipes.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.recipes.R;
import com.recipes.network.api.ApiServiceFactory;
import com.recipes.network.models.Category;
import com.recipes.network.models.CategoryList;
import com.recipes.screens.category.CategoryActivity;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Call<CategoryList> getCategoriesCall;
    private ArrayList<Category> categories = new ArrayList<>();
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
        getCategories();
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.categoriesRecyclerView);
        adapter = new CategoryAdapter(categories, category -> {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            intent.putExtra("category", category.getStrCategory());
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    private void getCategories() {
        getCategoriesCall = ApiServiceFactory.getMealApiService().getCategories();
        getCategoriesCall.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories.addAll(Arrays.asList(response.body().getMeals()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getCategoriesCall != null) {
            getCategoriesCall.cancel();
        }
    }
}
