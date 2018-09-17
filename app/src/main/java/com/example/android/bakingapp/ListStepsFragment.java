package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.bakingapp.adapter.IngredientAdapter;
import com.example.android.bakingapp.adapter.StepAdapter;
import com.example.android.bakingapp.model.Recipe;

public class ListStepsFragment extends Fragment implements StepAdapter.StepAdapterOnClickHandler {
    private Recipe mRecipe;

    // Define a new interface OnStepClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    @Override
    public void onClick(int position) {
        mCallback.onStepSelected(position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    // Mandatory empty constructor
    public ListStepsFragment() {
        // constructor
    }

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_list_steps, container, false);
        TextView toolbarTitle = rootView.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(mRecipe.getName());


        ImageButton backButton = rootView.findViewById(R.id.iv_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        // Get a reference to the RecyclerView in the fragment_list_steps xml layout file
        RecyclerView mStepRecyclerView = rootView.findViewById(R.id.rv_steps);
        RecyclerView mIngredientRecyclerView = rootView.findViewById(R.id.rv_ingredients);

        // Create the adapter
        StepAdapter mStepAdapter = new StepAdapter(mRecipe.getSteps(), this);
        IngredientAdapter mIngredientAdapter = new IngredientAdapter(mRecipe.getIngredients());

        // Set the adapter on the RecyclerView
        mStepRecyclerView.setAdapter(mStepAdapter);
        mIngredientRecyclerView.setAdapter(mIngredientAdapter);

        // Set Has Fixed Size
        mStepRecyclerView.setHasFixedSize(true);
        mIngredientRecyclerView.setHasFixedSize(true);

        // Create the LayoutManager
        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(getContext());

        // Set the LayoutManager on the RecyclerView
        mStepRecyclerView.setLayoutManager(stepLayoutManager);
        mIngredientRecyclerView.setLayoutManager(ingredientLayoutManager);

        // Disable scrolling into the RecyclerView
        mStepRecyclerView.setNestedScrollingEnabled(false);
        mIngredientRecyclerView.setNestedScrollingEnabled(false);

        // Return the root view
        return rootView;
    }

}
