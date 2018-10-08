package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.ListRecipesActivity;
import com.example.android.bakingapp.utils.Constants;

import org.parceler.Parcels;

import java.util.List;

public class IngredientWidgetProvider extends AppWidgetProvider {

    private static Recipe mRecipe;
    private static List<Ingredient> mIngredients;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {

        mRecipe = recipe;

        CharSequence widgetTitle;
        CharSequence widgetText;

        if(mRecipe == null){
            widgetTitle = context.getString(R.string.no_recipe);
            widgetText = context.getString(R.string.select_recipe);
        } else {

            //get ingredients in string format
            mIngredients = mRecipe.getIngredients();
            StringBuilder ingredientsForWidget = new StringBuilder();

            for (int i = 0; i<mIngredients.size(); i++){
                Ingredient passedIngredient = mIngredients.get(i);
                String ingredientDetail = String.valueOf(passedIngredient.getQuantity()) + " " +
                        passedIngredient.getMeasure() + " " + passedIngredient.getIngredient() + "\n";
                ingredientsForWidget.append(ingredientDetail);
            }

            widgetTitle = mRecipe.getName();
            widgetText = ingredientsForWidget.toString();
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);
        views.setTextViewText(R.id.appwidget_title, widgetTitle);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        //set Pending intent to open ListRecipesActivity upon clicking
        Intent intent = new Intent(context, ListRecipesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_title, pendingIntent);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        IngredientWidgetService.startActionUpdateWidget(context,mRecipe);
    }

    public static void updateBakingAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                              Recipe recipe, int[] appWidgetIds) {
        // This scrolls through all instances of a widget, and updates them
        //method called upon creation and upon timed-update as defined in the xml file
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        mRecipe = Parcels.unwrap(intent.getParcelableExtra(Constants.RECIPE_TO_WIDGET));
        IngredientWidgetService.startActionUpdateWidget(context, mRecipe);
    }

}
