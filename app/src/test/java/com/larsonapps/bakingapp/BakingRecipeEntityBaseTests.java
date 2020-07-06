package com.larsonapps.bakingapp;

import com.larsonapps.bakingapp.data.BakingRecipeEntity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BakingRecipeEntityBaseTests {
    private static final int ID_VALUE = 546;
    private static final String NAME_VALUE = "Chocolate Cookies";
    private static final int SERVINGS_VALUE = 12;
    private static final String IMAGE_VALUE = "https://somedomain.com/imagepath/5464564";

    // Tests for the constructor and Getters
    BakingRecipeEntity bakingRecipeEntity = new BakingRecipeEntity(ID_VALUE, NAME_VALUE,
            SERVINGS_VALUE, IMAGE_VALUE);

     @Test
    public void testBakingRecipeEntityIDGetter() {
         assertEquals(ID_VALUE, bakingRecipeEntity.getId());
     }

     @Test
    public void testBakingRecipeEntityNameGetter() {
         assertEquals(NAME_VALUE, bakingRecipeEntity.getName());
     }

     @Test
    public void testBakingRecipeEntityServingsGetter() {
         assertEquals(SERVINGS_VALUE, bakingRecipeEntity.getServings());
     }

     @Test
    public void testBakingRecipeEntityImageGetter() {
         assertEquals(IMAGE_VALUE, bakingRecipeEntity.getImage());
     }

     // Test setters
    @Test
    public void testBakingRecipeEntityIdSetter() {
         int temp = 915;
         bakingRecipeEntity.setId(temp);
         assertEquals(temp, bakingRecipeEntity.getId());
    }

    @Test
    public void testBakingRecipeEntityNameSetter() {
         String temp = "Silk Pie";
         bakingRecipeEntity.setName(temp);
         assertEquals(temp, bakingRecipeEntity.getName());
    }

    @Test
    public void testBakingRecipeEntityServingsSetter() {
         int temp = 4;
         bakingRecipeEntity.setServings(temp);
         assertEquals(temp, bakingRecipeEntity.getServings());
    }

    @Test
    public void testBakingRecipeEntityImageSetter() {
         String temp = "https://example.com/images/546884+6.png";
         bakingRecipeEntity.setImage(temp);
         assertEquals(temp, bakingRecipeEntity.getImage());
    }
}
