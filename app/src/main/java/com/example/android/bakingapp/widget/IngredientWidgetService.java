package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.app.Notification;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.model.Recipe;

import org.parceler.Parcels;

public class IngredientWidgetService extends IntentService {

    public IngredientWidgetService() {
        super("IngredientWidgetService");
    }

    public static final String ACTION_UPDATE_WIDGET = "com.example.android.bakingapp.action.update_update";
    public static final String RECIPE_ITEM_EXTRA_WIDGET = "recipe_item_extra_for_widget";

    //method that triggers the service to perform the update widget action
    public static void startActionUpdateWidget(Context context, Recipe recipe) {
        Intent intent = new Intent(context, IngredientWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(RECIPE_ITEM_EXTRA_WIDGET, Parcels.wrap(recipe));
        //workaround for IllegalArgumentException
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(new Intent(intent));
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1,new Notification());
    }

    //onHandleIntent extracts action and handles each action seperately
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                Recipe receivedRecipe = Parcels.unwrap(intent.getParcelableExtra(RECIPE_ITEM_EXTRA_WIDGET));
                handleUpdateWidget(receivedRecipe);
            }
        }
    }

    public void handleUpdateWidget(Recipe recipe) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                IngredientWidgetProvider.class));
        //update all widgets
        IngredientWidgetProvider.updateBakingAppWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}