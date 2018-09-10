package com.example.android.bakingapp.model;

import org.parceler.Parcel;

@Parcel
class Ingredient {
    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredient() {
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
