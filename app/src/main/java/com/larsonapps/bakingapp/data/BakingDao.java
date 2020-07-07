package com.larsonapps.bakingapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BakingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertBakingRecipe(BakingRecipeEntity bakingRecipeEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAllBakingRecipes(List<BakingRecipeEntity> bakingRecipes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertBakingIngrediant(BakingIngredient bakingIngredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAllBakingIngredients(List<BakingIngredient> bakingIngredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertBakingStep(BakingStep bakingStep);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAllBakingSteps(List<BakingStep> bakingSteps);

    @Query("SELECT * FROM baking_recipe")
    List<BakingRecipeEntity> getAllBakingRecipes();

    @Query("SELECT * FROM ingredients WHERE recipe_key = :recipeKey")
    LiveData<List<BakingIngredient>> getAllBakingIngredients(int recipeKey);

    @Query("SELECT * FROM steps WHERE recipe_key = :recipeKey")
    LiveData<List<BakingStep>> getAllBakingSteps(int recipeKey);
}
