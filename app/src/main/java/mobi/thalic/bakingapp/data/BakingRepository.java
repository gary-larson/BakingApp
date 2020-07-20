package mobi.thalic.bakingapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mobi.thalic.bakingapp.R;
import mobi.thalic.bakingapp.utilities.BakingExecutor;
import mobi.thalic.bakingapp.utilities.BakingJsonUtilities;
import mobi.thalic.bakingapp.utilities.BakingNetworkUtilities;
import mobi.thalic.bakingapp.utilities.BakingResult;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface for the callback
 * @param <T>
 */
interface RepositoryCallback<T> {
    void onComplete(BakingResult<T> bakingResult);
}

public class BakingRepository {
    // Declare Variables
    private final BakingExecutor executor;
    private final String mErrorMessage;
    private MutableLiveData<BakingResult<List<BakingRecipeEntity>>> mBakingResult;
    private MutableLiveData<List<BakingIngredient>> mBakingIngredients;
    private MutableLiveData<List<BakingStep>> mBakingSteps;
    // Database variable
    BakingDao mBakingDao;

    public BakingRepository (Application application) {
        // Initialize variables
        mErrorMessage = application.getString(R.string.error_message);
        executor = new BakingExecutor();
        BakingRoomDatabase bakingRoomDatabase = BakingRoomDatabase.getDatabase(application);
        mBakingDao = bakingRoomDatabase.bakingDao();
        mBakingResult = new MutableLiveData<>();
        mBakingIngredients = new MutableLiveData<>();
        mBakingSteps = new MutableLiveData<>();
    }

    /**
     * Method to retrieve the Baking list from the internet
     * @param callback to handle results
     */
    public void retrieveBakingList (
            final RepositoryCallback<List<BakingRecipe>> callback
    ) {
        executor.execute(() -> {
            // build url
            URL bakingRequestUrl = BakingNetworkUtilities.buildUrl();

            try {
                // attempt to get baking information
                String jsonBakingRecipeResponse = BakingNetworkUtilities
                        .getResponseFromHttpUrl(bakingRequestUrl);
                // if null cancel task (Unknown error)
                if (jsonBakingRecipeResponse == null) {
                    BakingResult<List<BakingRecipe>> errorResult = new BakingResult.Error<>(mErrorMessage);
                    callback.onComplete(errorResult);
                }
                // return Json decoded movie Information
                List<BakingRecipe> bakingRecipes = BakingJsonUtilities.getBakingRecipes(jsonBakingRecipeResponse);
                BakingResult<List<BakingRecipe>> bakingResult;
                if (bakingRecipes == null) {
                    bakingResult = new BakingResult.Error<>(mErrorMessage);
                } else {
                    bakingResult = new BakingResult.Success<>(bakingRecipes);
                }
                callback.onComplete(bakingResult);
            } catch (Exception e) {
                e.printStackTrace();
                // in case of an error return null
                BakingResult<List<BakingRecipe>> errorResult = new BakingResult.Error<>(mErrorMessage);
                callback.onComplete(errorResult);
            }
        });
    }

    /**
     * Method to determine if data is in room database if not retrieve data and put it into database
     * @return results
     */
    public LiveData<BakingResult<List<BakingRecipeEntity>>> getBakingRecipes () {
        // Start Database executor
        BakingRoomDatabase.databaseWriteExecutor.execute(() -> {
            // get Baking Recipes from database
            List<BakingRecipeEntity> bakingRecipeEntities = mBakingDao.getAllBakingRecipes();
            // test results and retrieve data from internet if required
            if (bakingRecipeEntities.size() == 0) {
                retrieveBakingList(bakingResult -> {
                    // test for success
                    if (bakingResult instanceof BakingResult.Success) {
                        // convert information to usable format
                        List<BakingRecipe> bakingRecipes =
                                ((BakingResult.Success<List<BakingRecipe>>) bakingResult).data;
                        List<BakingRecipeEntity> newBakingRecipeEntities = new ArrayList<>();
                        // setup loop to convert data for database
                        for (int i = 0; i < bakingRecipes.size(); i++) {
                            // create entity and populate with data
                            BakingRecipeEntity bakingRecipeEntity = new BakingRecipeEntity(
                                    bakingRecipes.get(i).getId(),
                                    bakingRecipes.get(i).getName(),
                                    bakingRecipes.get(i).getServings(),
                                    bakingRecipes.get(i).getImage());
                            // add to baking recipe entity list
                            newBakingRecipeEntities.add(bakingRecipeEntity);
                            // put ingredients for this recipe in database
                            mBakingDao.InsertAllBakingIngredients(bakingRecipes.get(i)
                                    .getIngredientList());
                            // put steps for this recipe in database
                            mBakingDao.InsertAllBakingSteps(bakingRecipes.get(i).getStepList());
                        }
                        // put all recipes in database
                        mBakingDao.InsertAllBakingRecipes(newBakingRecipeEntities);
                        // create baking result for entities
                        BakingResult<List<BakingRecipeEntity>> newBakingResult = new
                                BakingResult.Success<>(newBakingRecipeEntities);
                        // post baking results to be distributed through live data
                        mBakingResult.postValue(newBakingResult);
                    } else {
                        // On error extract error
                        String errorString = ((BakingResult.Error<List<BakingRecipe>>) bakingResult)
                                .mErrorMessage;
                        // put error in entity result
                        BakingResult<List<BakingRecipeEntity>> newBakingResult = new
                                BakingResult.Error<>(errorString);
                        // post error
                        mBakingResult.postValue(newBakingResult);
                    }
                });
            } else {
                // create new success result for database data
                BakingResult<List<BakingRecipeEntity>> newBakingResult = new
                        BakingResult.Success<>(bakingRecipeEntities);
                // post result
                mBakingResult.postValue(newBakingResult);
            }
        });
        // return result through live data
        return mBakingResult;
    }

    public LiveData<List<BakingIngredient>> getBakingIngredients(int recipeKey) {
        // Start Database executor
        BakingRoomDatabase.databaseWriteExecutor.execute(() -> {
            // get Baking Ingredients from database
            List<BakingIngredient> bakingIngredients = mBakingDao.getAllBakingIngredients(recipeKey);
            // enter results through live data
            mBakingIngredients.postValue(bakingIngredients);
        });
        return mBakingIngredients;
    }

    public LiveData<List<BakingStep>> getBakingSteps(int recipeKey) {

        // Start Database executor
        BakingRoomDatabase.databaseWriteExecutor.execute(() -> {
            // get baking steps from database
            List<BakingStep> bakingSteps = mBakingDao.getAllBakingSteps(recipeKey);
            // enter results through live data
            mBakingSteps.postValue(bakingSteps);
        });
        return mBakingSteps;
    }

//    /**
//     * Getter for baking widget recipe list
//     * @return baking widget recipe list
//     */
//    public List<BakingWidgetRecipe> getBakingWidgetRecipeList() {
//        retrieveBakingWidgetList(bakingResult -> {
//            if (bakingResult instanceof BakingResult.Success) {
//                List<BakingWidgetRecipe> bakingWidgetRecipeList = ((BakingResult.Success<List<BakingWidgetRecipe>>) bakingResult).data;
//                return bakingWidgetRecipeList;
//            }
//        });
//        return baking;
//    }
//
//    public void retrieveBakingWidgetList (
//            final RepositoryCallback<List<BakingWidgetRecipe>> callback
//    ) {
//        BakingRoomDatabase.databaseWriteExecutor.execute(() -> {
//            List<BakingWidgetRecipe> bakingWidgetRecipeList = new ArrayList<>();
//            List<BakingRecipeEntity> bakingRecipeEntityList = mBakingDao.getAllBakingRecipes();
//            if (bakingRecipeEntityList.size() == 0) {
//                BakingResult<List<BakingWidgetRecipe>> errorResult =
//                        new BakingResult.Error("No Recipes Available.");
//                callback.onComplete(errorResult);
//            }
//            for (int i = 0; i < bakingRecipeEntityList.size(); i++) {
//                BakingWidgetRecipe bakingWidgetRecipe = new BakingWidgetRecipe();
//                bakingWidgetRecipe.setRecipeName(bakingRecipeEntityList.get(i).getName());
//                bakingWidgetRecipe.setBakingIngredientList(
//                        mBakingDao.getAllBakingIngredients(bakingRecipeEntityList.get(i).getId()));
//                bakingWidgetRecipeList.add(bakingWidgetRecipe);
//            }
//            BakingResult<List<BakingWidgetRecipe>> result =
//                    new BakingResult.Success<>(bakingWidgetRecipeList);
//            callback.onComplete(result);
//        });
//    }
}
