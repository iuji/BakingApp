package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android.bakingapp.adapter.StepAdapter;
import com.example.android.bakingapp.model.Step;

import java.util.List;

public class ListStepsFragment extends Fragment implements StepAdapter.StepAdapterOnClickHandler {
    List<Step> mStepList;

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnStepClickListener {
        void onStepSelected(Step stepSelected);
    }

    @Override
    public void onClick(Step stepSelected) {
        mCallback.onStepSelected(stepSelected);
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
    }

    public void populateStepList(List<Step> mStepList) {
        this.mStepList = mStepList;
    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_list_steps, container, false);

        // Get a reference to the RecyclerView in the fragment_list_steps xml layout file
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_steps);

        // Create the adapter
        StepAdapter mAdapter = new StepAdapter(mStepList, this);

        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(mAdapter);

        // Create the LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        // Set the LayoutManager on the RecyclerView
        recyclerView.setLayoutManager(layoutManager);

        // Return the root view
        return rootView;
    }

}
