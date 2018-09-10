package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepActivity;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    private List<Recipe> mRecipeList;
    private Context mContext;

    public RecipeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setRecipeList(List<Recipe> mRecipeList) {
        this.mRecipeList = mRecipeList;
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        String recipeName = mRecipeList.get(position).getName();
        holder.textRecipeName.setText(recipeName);
        holder.cardRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StepActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mRecipeList != null){
            return mRecipeList.size();
        }
        return 0;
    }

     class RecipeViewHolder extends RecyclerView.ViewHolder{
        TextView textRecipeName;
        CardView cardRecipe;

         RecipeViewHolder(View itemView) {
            super(itemView);
            textRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            cardRecipe = itemView.findViewById(R.id.cv_item_recipe);
        }
    }
}
