package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import org.parceler.Parcels;

public class StepActivity extends AppCompatActivity implements ListStepsFragment.OnStepClickListener, InfoStepFragment.OnChangeStepClickListener {
    private Recipe mRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        if (intent.getParcelableExtra("recipe") != null) {
            mRecipe = Parcels.unwrap(intent.getParcelableExtra("recipe"));
            setupListStepsFragment();
        }
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public void onStepSelected(int position) {
        if (position > mRecipe.getSteps().size() || position < 0) {
            Toast.makeText(this, "No more steps", Toast.LENGTH_SHORT).show();
        } else {
            setupInfoStepFragment(position);
        }
    }

    @Override
    public void onChangeStep(int position) {
        if (position > mRecipe.getSteps().size() || position < 0) {
            Toast.makeText(this, "No more steps", Toast.LENGTH_SHORT).show();
        } else {
            setupInfoStepFragment(position);
        }
    }

    private void setupInfoStepFragment(int position) {
        Step stepSelected = mRecipe.getSteps().get(position);
        FragmentManager manager = getSupportFragmentManager();
        if (isTablet()) {
            InfoStepFragment infoStepFragment = (InfoStepFragment) manager.findFragmentById(R.id.secundary_frame);
            infoStepFragment.populateView(stepSelected, position);
        } else {
            FragmentTransaction tx = manager.beginTransaction();

            InfoStepFragment infoStepFragment = new InfoStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("step", Parcels.wrap(stepSelected));
            bundle.putInt("position", position);
            infoStepFragment.setArguments(bundle);
            tx.addToBackStack(null);

            tx.replace(R.id.primary_frame, infoStepFragment);
            tx.commit();
        }
    }

    private void setupListStepsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        ListStepsFragment listStepsFragment = new ListStepsFragment();
        listStepsFragment.setRecipe(mRecipe);
        tx.replace(R.id.primary_frame, listStepsFragment);
        if (isTablet()) {
            tx.replace(R.id.secundary_frame, new InfoStepFragment());
        }
        tx.commit();
    }

}
