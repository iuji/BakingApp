package com.example.android.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{
    private List<Ingredient> mIngredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.textIngredientName.setText(mIngredientList.get(position).getIngredient());
        holder.textIngredientQuantity.setText(Float.toString(mIngredientList.get(position).getQuantity()));
        holder.textIngredientMeasure.setText(mIngredientList.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if(mIngredientList != null){
            return mIngredientList.size();
        }
        return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder{
        TextView textIngredientName;
        TextView textIngredientQuantity;
        TextView textIngredientMeasure;

        IngredientViewHolder(View itemView) {
            super(itemView);
            textIngredientName = itemView.findViewById(R.id.tv_ingredient_name);
            textIngredientQuantity = itemView.findViewById(R.id.tv_ingredient_quantity);
            textIngredientMeasure = itemView.findViewById(R.id.tv_ingredient_measure);
        }
    }
}
