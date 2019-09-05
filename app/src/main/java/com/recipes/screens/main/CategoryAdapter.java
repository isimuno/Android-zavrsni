package com.recipes.screens.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recipes.R;
import com.recipes.network.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<Category> categories;
    private OnItemClickListener listener;

    public CategoryAdapter(ArrayList<Category> categories, OnItemClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CategoryViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_category, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        final Category category = categories.get(i);
        TextView categoryName = categoryViewHolder.itemView.findViewById(R.id.categoryName);
        categoryName.setText(category.getStrCategory());
        categoryViewHolder.itemView.setOnClickListener(view -> listener.onItemClick(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
