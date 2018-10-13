package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.utils.Validator;

import org.parceler.Parcels;

public class StepActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener, StepDetailFragment.OnChangeStepClickListener {
    private Recipe mRecipe;
    private Step mStep;
    private StepDetailFragment mStepDetailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent.getParcelableExtra("recipe") != null) {
                mRecipe = Parcels.unwrap(intent.getParcelableExtra("recipe"));
                setupListStepsFragment();
            }
        }else{
            mRecipe = Parcels.unwrap(savedInstanceState.getParcelable("recipe"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipe", Parcels.wrap(mRecipe));
    }

    @Override
    public void onStepSelected(int position) {
        setupStepDetailFragment(position);
    }

    @Override
    public void onChangeStep(int position) {
        if (position > (mRecipe.getSteps().size()-1) || position < 0) {
            Toast.makeText(this, "No more steps", Toast.LENGTH_SHORT).show();
        } else {
            updateData(position);
        }
    }

    private void setupStepDetailFragment(int position) {
        mStep = mRecipe.getSteps().get(position);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();
        mStepDetailFragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", Parcels.wrap(mStep));
        bundle.putInt("position", position);
        mStepDetailFragment.setArguments(bundle);
        if (Validator.isTablet(getApplicationContext())) {
            tx.replace(R.id.secundary_frame, mStepDetailFragment);
            tx.commit();
        } else {
            tx.addToBackStack(null);
            tx.replace(R.id.primary_frame, mStepDetailFragment);
            tx.commit();
        }
    }

    private void updateData(int position){
        mStep = mRecipe.getSteps().get(position);
        if(mStepDetailFragment != null){
            mStepDetailFragment.populateView(mStep, position);
        }
    }

    private void setupListStepsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", Parcels.wrap(mRecipe));
        recipeDetailFragment.setArguments(bundle);
        tx.replace(R.id.primary_frame, recipeDetailFragment);
        tx.commit();
    }

}
