package com.example.android.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private List<Step> mStepList;
    private final StepAdapterOnClickHandler mClickHandler;

    public interface StepAdapterOnClickHandler {
        void onClick(int position);
    }

    public StepAdapter(List<Step> stepList, StepAdapterOnClickHandler clickHandler) {
        mStepList = stepList;
        mClickHandler = clickHandler;
    }


    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_step, parent, false);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.textStepNumber.setText(mStepList.get(position).getId() + " - ");
        holder.textStepName.setText(mStepList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mStepList != null) {
            return mStepList.size();
        }
        return 0;
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textStepNumber;
        TextView textStepName;

        StepViewHolder(View itemView) {
            super(itemView);
            textStepNumber = itemView.findViewById(R.id.tv_step_number);
            textStepName = itemView.findViewById(R.id.tv_step_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }
}
