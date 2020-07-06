package com.larsonapps.bakingapp;

import com.larsonapps.bakingapp.data.BakingIngredient;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BakingIngredientBaseTests {
    private static final int INGREDIENT_KEY_VALUE = 34;
    private static final int RECIPE_KET_VALUE = 55;
    private static final double QUANTITY_VALUE = 20.45;
    private static final String MEASURE_VALUE = "Tablespoon";
    private static final String INGREDIENT_VALUE = "Milk";

    // Test baking ingredient constructor and baking ingredient getters
    BakingIngredient bakingIngredient = new BakingIngredient(INGREDIENT_KEY_VALUE, RECIPE_KET_VALUE,
            QUANTITY_VALUE, MEASURE_VALUE, INGREDIENT_VALUE);

    @Test
    public void testBakingIngredientIngredientKeyGetter() {
        assertEquals(INGREDIENT_KEY_VALUE,  bakingIngredient.getIngredientKey());
    }

    @Test
    public void testBakingIngredientRecipeKeyGetter() {
        assertEquals(RECIPE_KET_VALUE,  bakingIngredient.getRecipeKey());
    }

    @Test
    public void testBakingIngredientQuantityGetter() {
        assertEquals(QUANTITY_VALUE,  bakingIngredient.getQuantity(), 0.001);
    }

    @Test
    public void testBakingIngredientMeasureGetter() {
        assertEquals(MEASURE_VALUE,  bakingIngredient.getMeasure());
    }

    @Test
    public void testBakingIngredientIngredientGetter() {
        assertEquals(INGREDIENT_VALUE,  bakingIngredient.getIngredient());
    }

    // Test default constructor and setters
    BakingIngredient bakingIngredientDefault = new BakingIngredient();
    @Test
    public void testBakingIngredientIngredientKeySetter() {
        int temp = 14;
        bakingIngredientDefault.setIngredientKey(temp);
        assertEquals(temp,  bakingIngredientDefault.getIngredientKey());
    }

    @Test
    public void testBakingIngredientRecipeKeySetter() {
        int temp = 587;
        bakingIngredientDefault.setRecipeKey(temp);
        assertEquals(temp,  bakingIngredientDefault.getRecipeKey());
    }

    @Test
    public void testBakingIngredientQuantitySetter() {
        double temp = 25.01;
        bakingIngredientDefault.setQuantity(temp);
        assertEquals(temp,  bakingIngredientDefault.getQuantity(), 0.001);
    }

    @Test
    public void testBakingIngredientMeasureSetter() {
        String temp = "Cup";
        bakingIngredientDefault.setMeasure(temp);
        assertEquals(temp,  bakingIngredientDefault.getMeasure());
    }

    @Test
    public void testBakingIngredientIngredientSetter() {
        String temp = "Sugar";
        bakingIngredientDefault.setIngredient(temp);
        assertEquals(temp,  bakingIngredientDefault.getIngredient());
    }
}
