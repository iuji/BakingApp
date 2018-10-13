package com.example.android.bakingapp.model;

import org.parceler.Parcel;

@Parcel
public class Ingredient {
    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredient() {
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

}
