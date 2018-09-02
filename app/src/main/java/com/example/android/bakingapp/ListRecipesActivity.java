package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.adapter.RecipeAdapter;
import com.example.android.bakingapp.api.BakingService;
import com.example.android.bakingapp.api.Client;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.Connectivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListRecipesActivity extends AppCompatActivity {
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageDisplay;
    @BindView(R.id.view_error)
    LinearLayout mViewError;
    @BindView(R.id.rv_recipes)
    RecyclerView mRecyclerView;

    private static final String TAG = ListRecipesActivity.class.getSimpleName();
    private RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);
        ButterKnife.bind(this);
        setupRecyclerView();
        createRetrofit();
    }

    private void setupRecyclerView() {
        mAdapter = new RecipeAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void createRetrofit() {
        startLoading();
        if (Connectivity.isConnected(this)) {
            Retrofit retrofit = Client.getClient();
            BakingService service = retrofit.create(BakingService.class);

            Call<List<Recipe>> requestRecipes = service.listRecipe();

            requestRecipes.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if (response.isSuccessful()) {
                        mAdapter.setRecipeList(response.body());
                        endLoading();
                        showListMovieView();
                    } else {
                        Log.e(TAG, "erro: " + response.code());
                        endLoading();
                        showErrorMessage();
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.e(TAG, "erro: " + t.getMessage());
                    endLoading();
                    showErrorMessage();
                }
            });
        } else {
            endLoading();
            mErrorMessageDisplay.setText(R.string.error_message_connection);
            showErrorMessage();
        }
    }
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 300;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    private void startLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void endLoading() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.GONE);
        mViewError.setVisibility(View.VISIBLE);
    }

    private void showListMovieView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mViewError.setVisibility(View.GONE);
    }

}
