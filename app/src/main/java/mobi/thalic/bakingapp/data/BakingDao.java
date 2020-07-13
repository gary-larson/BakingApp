package mobi.thalic.bakingapp.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Class to handle all room database quesries
 */
@Dao
public interface BakingDao {

    /**
     * Method to Insert a list of baking recipes
      * @param bakingRecipes to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAllBakingRecipes(List<BakingRecipeEntity> bakingRecipes);

    /**
     * Method to insert a list of baking ingredients
     * @param bakingIngredients to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAllBakingIngredients(List<BakingIngredient> bakingIngredients);

    /**
     * Method to insert a list of baking steps
     * @param bakingSteps to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAllBakingSteps(List<BakingStep> bakingSteps);

    /**
     * Method to retrieve all baking recipes
     * @return list of baking recipes
     */
    @Query("SELECT * FROM baking_recipe")
    List<BakingRecipeEntity> getAllBakingRecipes();

    /**
     * Method to retrieve all of a recipes baking ingredients
     * @param recipeKey of the recipe to retrieve
     * @return list of baking ingredients
     */
    @Query("SELECT * FROM ingredients WHERE recipe_key = :recipeKey")
    List<BakingIngredient> getAllBakingIngredients(int recipeKey);
    //LiveData<List<BakingIngredient>> getAllBakingIngredients(int recipeKey);

    /**
     * Method to retrieve all of a recipes baking steps
     * @param recipeKey of the recipe to retrieve
     * @return list of baking steps
     */
    @Query("SELECT * FROM steps WHERE recipe_key = :recipeKey")
    List<BakingStep> getAllBakingSteps(int recipeKey);
    //LiveData<List<BakingStep>> getAllBakingSteps(int recipeKey);
}
