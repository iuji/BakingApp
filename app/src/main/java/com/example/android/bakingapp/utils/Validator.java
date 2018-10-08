package com.example.android.bakingapp.utils;

import android.content.Context;

import com.example.android.bakingapp.R;

public class Validator {
    public static boolean isLandscapeAndIsNotTablet(Context context) {
        return context.getResources().getBoolean(R.bool.isLand) && !context.getResources().getBoolean(R.bool.isTablet);
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }

}
