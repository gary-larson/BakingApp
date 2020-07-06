package com.larsonapps.bakingapp;

import com.larsonapps.bakingapp.data.BakingIngredient;
import com.larsonapps.bakingapp.data.BakingRecipe;
import com.larsonapps.bakingapp.data.BakingStep;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BakingRecipeBaseTests {
    private static final int ID_VALUE = 546;
    private static final String NAME_VALUE = "Chocolate Cookies";
    private static final int SERVINGS_VALUE = 12;
    private static final String IMAGE_VALUE = "https://somedomain.com/imagepath/5464564.png";

    private static final int STEP_KEY_VALUE = 669;
    private static final int RECIPE_KEY_VALUE = 21;
    private static final int STEP_ID_VALUE = 55;
    private static final String SHORT_DESCRIPTION_VALUE = "Experimental short description";
    private static final String DESCRIPTION_VALUE = "Sufficient description";
    private static final String VIDEO_URL_VALUE = "https://example.com/video/464848648.png";
    private static final String THUMBNAIL_URL_VALUE = "https://example.com/thumbnails/4848664.png";

    // Create baking step
    BakingStep bakingStep = new BakingStep(STEP_KEY_VALUE, RECIPE_KEY_VALUE, STEP_ID_VALUE,
            SHORT_DESCRIPTION_VALUE, DESCRIPTION_VALUE, VIDEO_URL_VALUE, THUMBNAIL_URL_VALUE);

    List<BakingStep> bakingSteps = new ArrayList<>();

    private static final int INGREDIENT_KEY_VALUE = 34;
    private static final int RECIPE_KET_VALUE = 55;
    private static final double QUANTITY_VALUE = 20.45;
    private static final String MEASURE_VALUE = "Tablespoon";
    private static final String INGREDIENT_VALUE = "Milk";

    // Create baking ing=redient
    BakingIngredient bakingIngredient = new BakingIngredient(INGREDIENT_KEY_VALUE, RECIPE_KET_VALUE,
            QUANTITY_VALUE, MEASURE_VALUE, INGREDIENT_VALUE);

    List<BakingIngredient> bakingIngredients = new ArrayList<>();

    // Tests for the constructor and Getters
    BakingRecipe bakingRecipe = new BakingRecipe(ID_VALUE, NAME_VALUE, bakingIngredients,
            bakingSteps, SERVINGS_VALUE, IMAGE_VALUE);

    @Test
    public void testBakingRecipeIDGetter() {
        assertEquals(ID_VALUE, bakingRecipe.getId());
    }

    @Test
    public void testBakingRecipeNameGetter() {
        assertEquals(NAME_VALUE, bakingRecipe.getName());
    }

    // This tests both setter and getter
    @Test
    public void testBakingRecipeIngredientListGetter() {
        bakingIngredients.add(bakingIngredient);
        bakingRecipe.setIngredientList(bakingIngredients);
        assertEquals (bakingIngredients, bakingRecipe.getIngredientList());
    }

    // This tests both setter and getter
    @Test
    public void testBakingRecipeStepListGetter() {
        bakingSteps.add(bakingStep);
        bakingRecipe.setStepList(bakingSteps);
        assertEquals(bakingSteps, bakingRecipe.getStepList());
    }

    @Test
    public void testBakingRecipeServingsGetter() {
        assertEquals(SERVINGS_VALUE, bakingRecipe.getServings());
    }

    @Test
    public void testBakingRecipeImageGetter() {
        assertEquals(IMAGE_VALUE, bakingRecipe.getImage());
    }

    // Test default constructor and setters
    BakingRecipe bakingRecipeDefault = new BakingRecipe();
    @Test
    public void testBakingRecipeIdSetter() {
        int temp = 915;
        bakingRecipeDefault.setId(temp);
        assertEquals(temp, bakingRecipeDefault.getId());
    }

    @Test
    public void testBakingRecipeNameSetter() {
        String temp = "Silk Pie";
        bakingRecipeDefault.setName(temp);
        assertEquals(temp, bakingRecipeDefault.getName());
    }

    // This tests both setter and getter
    @Test
    public void testBakingRecipeIngredientListSetter() {
        BakingIngredient bakingIngredient1 = new BakingIngredient(111, 222,
                333.33, "Cup", "Sugar");
        bakingIngredients.add(bakingIngredient1);
        bakingRecipeDefault.setIngredientList(bakingIngredients);
        assertEquals (bakingIngredients, bakingRecipeDefault.getIngredientList());
    }

    // This tests both setter and getter
    @Test
    public void testBakingRecipeStepListSetter() {
        BakingStep bakingStep1 = new BakingStep(111, 222, 333,
                "Short Description", "Description", "Video Url",
                "Thumbnail Url");
        bakingSteps.add(bakingStep1);
        bakingRecipeDefault.setStepList(bakingSteps);
        assertEquals(bakingSteps, bakingRecipeDefault.getStepList());
    }

    @Test
    public void testBakingRecipeServingsSetter() {
        int temp = 4;
        bakingRecipeDefault.setServings(temp);
        assertEquals(temp, bakingRecipeDefault.getServings());
    }

    @Test
    public void testBakingRecipeImageSetter() {
        String temp = "https://example.com/images/546884+6.png";
        bakingRecipeDefault.setImage(temp);
        assertEquals(temp, bakingRecipeDefault.getImage());
    }
}
