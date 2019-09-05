package com.recipes.screens.category;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.recipes.R;
import com.recipes.network.models.Meal;

import java.util.ArrayList;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealHolder> {

    private ArrayList<Meal> meals;
    private OnMealClickListener listener;

    public MealsAdapter(ArrayList<Meal> meals, OnMealClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MealHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_meal, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder mealHolder, int i) {
        final Meal meal = meals.get(i);
        ImageView mealImage = mealHolder.itemView.findViewById(R.id.mealImageView);
        TextView mealName = mealHolder.itemView.findViewById(R.id.mealNameTextView);
        mealImage.setClipToOutline(true);
        Glide.with(mealImage)
                .load(meal.getStrMealThumb())
                .into(mealImage);
        mealName.setText(meal.getStrMeal());
        mealHolder.itemView.setOnClickListener(view -> listener.onMealClicked(meal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealHolder extends RecyclerView.ViewHolder {
        public MealHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
