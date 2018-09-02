package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
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
    }

    @Override
    public int getItemCount() {
        if(mRecipeList != null){
            return mRecipeList.size();
        }
        return 0;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        TextView textRecipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            textRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        }
    }
}
