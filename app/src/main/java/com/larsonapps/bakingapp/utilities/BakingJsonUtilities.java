package com.larsonapps.bakingapp.utilities;

import com.larsonapps.bakingapp.data.BakingIngredient;
import com.larsonapps.bakingapp.data.BakingRecipe;
import com.larsonapps.bakingapp.data.BakingStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to parse data from JSON objects
 */
public class BakingJsonUtilities {
    // Declare constants
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String INGREDIENTS = "ingredients";
    private static final String QUANTITY = "quantity";
    private static final String MEASURE = "measure";
    private static final String INGREDIENT = "ingredient";
    private static final String STEPS = "steps";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String VIDEO_URL = "videoURL";
    private static final String THUMBNAIL_URL = "thumbnailURL";
    private static final String SERVINGS = "servings";
    private static final String IMAGE = "image";

    /**
     * Method to convert a Json string to a list of movie results
     * @param bakingJsonStr is the string to be converted
     * @return the list of movie results
     * @throws JSONException in case of error
     */
    public static List<BakingRecipe> getBakingRecipes(String bakingJsonStr) throws JSONException {
        // Declare and initialize variables to return results

        List<BakingRecipe> bakingRecipes = new ArrayList<>();

        // Check if there are actual results
        JSONArray bakingJsonArray = new JSONArray(bakingJsonStr);
        if (bakingJsonArray.length() == 0) {
            return null;
        }
        for (int i = 0; i < bakingJsonArray.length(); i++) {
            // Create a bakingRecipe object to put data
            BakingRecipe bakingRecipe = new BakingRecipe();
            // Get the current json recipe
            JSONObject currentRecipeJson = bakingJsonArray.getJSONObject(i);
            // Get information for this recipe
            bakingRecipe.setId(currentRecipeJson.getInt(ID));
            bakingRecipe.setName(currentRecipeJson.getString(NAME));
            // Get ingredients array
            JSONArray ingredientJsonArray = currentRecipeJson.getJSONArray(INGREDIENTS);
            // Create List to hold the recipe ingredients
            List<BakingIngredient> bakingIngredients = new ArrayList<>();
            // Retrieve ingredients
            for (int j = 0; j < ingredientJsonArray.length(); j++) {
                // Create a baking ingredient object to hold the ingredient data
                BakingIngredient bakingIngredient = new BakingIngredient();
                // Get the current json ingredient
                JSONObject currentIngredientJson = ingredientJsonArray.getJSONObject(j);
                // Get ingredient data
                bakingIngredient.setQuantity(currentIngredientJson.getDouble(QUANTITY));
                bakingIngredient.setMeasure(currentIngredientJson.getString(MEASURE));
                bakingIngredient.setIngredient(currentIngredientJson.getString(INGREDIENT));
                // Add recipe key to baking ingredient
                bakingIngredient.setRecipeKey(bakingRecipe.getId());
                // add ingredient to ingredients list
                bakingIngredients.add(bakingIngredient);
            }
            // Add ingredients list to baking recipe
            bakingRecipe.setIngredientList(bakingIngredients);
            // Get steps array
            JSONArray stepJsonArray = currentRecipeJson.getJSONArray(STEPS);
            // Create List to hold the recipe ingredients
            List<BakingStep> bakingSteps = new ArrayList<>();
            // Retrieve ingredients
            for (int j = 0; j < stepJsonArray.length(); j++) {
                // Create a baking step object to hold the step data
                BakingStep bakingStep = new BakingStep();
                // Get the current JSON step
                JSONObject currentStepJson = stepJsonArray.getJSONObject(j);
                // Get step data
                bakingStep.setId(currentStepJson.getInt(ID));
                bakingStep.setShortDescription(currentStepJson.getString(SHORT_DESCRIPTION));
                bakingStep.setDescription(currentStepJson.getString(DESCRIPTION));
                bakingStep.setVideoURL(currentStepJson.getString(VIDEO_URL));
                bakingStep.setThumbnailURL(currentStepJson.getString(THUMBNAIL_URL));
                // add recipe key to baking step
                bakingStep.setRecipeKey(bakingRecipe.getId());
                // add step to steps list
                bakingSteps.add(bakingStep);
            }
            // add steps list to baking recipe
            bakingRecipe.setStepList(bakingSteps);
            bakingRecipe.setServings(currentRecipeJson.getInt(SERVINGS));
            bakingRecipe.setImage(currentRecipeJson.getString(IMAGE));
            // add baking recipe to baking recipe list
            bakingRecipes.add(bakingRecipe);
        }
        // return completed recipe list
        return bakingRecipes;
    }
}
