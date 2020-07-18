package mobi.thalic.bakingapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mobi.thalic.bakingapp.data.BakingIngredient;
import mobi.thalic.bakingapp.data.BakingRecipeEntity;
import mobi.thalic.bakingapp.data.BakingRepository;
import mobi.thalic.bakingapp.data.BakingStep;
import mobi.thalic.bakingapp.utilities.BakingResult;

/**
 * Class to handle baking view model
 */
public class BakingViewModel extends AndroidViewModel {
    // Declare variables
    Application mApplication;
    BakingRepository mBakingRepository;
    private int mRecipeKey;
    private String mRecipeName;
    private BakingStep mBakingStep;
    LiveData<BakingResult<List<BakingRecipeEntity>>> mLiveDataBakingRecipes;
    LiveData<List<BakingIngredient>> mLiveDataBakingIngredients;
    LiveData<List<BakingStep>> mLiveDataBakingSteps;
    MutableLiveData<String> mMutableLiveDataDescription;
    MutableLiveData<BakingStep> mMutableLiveDataBakingStep;

    /**
     * Constructor foe baking view model
     * @param application to be used in the repository
     */
    public BakingViewModel (Application application) {
        super(application);
        this.mApplication = application;
        mBakingRepository = new BakingRepository(mApplication);
        mMutableLiveDataDescription = new MutableLiveData<>();
        mMutableLiveDataBakingStep = new MutableLiveData<>();
    }

    /**
     * Method to get baking recipes results
     * @return results through live data
     */
    public LiveData<BakingResult<List<BakingRecipeEntity>>> getBakingRecipes() {
        if (mLiveDataBakingRecipes == null) {
            // get baking recipes from baking repository
            mLiveDataBakingRecipes = mBakingRepository.getBakingRecipes();
        }
        // return baking recipes through live data
        return mLiveDataBakingRecipes;
    }

    /**
     * Method to get baking ingredients through live data
     * @return baking ingredients through live data
     */
    public LiveData<List<BakingIngredient>> getBakingIngredients() {
        if (mLiveDataBakingIngredients == null || mLiveDataBakingIngredients.getValue() == null ||
                mLiveDataBakingIngredients.getValue().get(0).getRecipeKey() != mRecipeKey) {
            // get baking ingredients from baking repository
            mLiveDataBakingIngredients = mBakingRepository.getBakingIngredients(mRecipeKey);
        }
        // return baking ingredients through live data
        return mLiveDataBakingIngredients;
    }

    /**
     * Method to get baking steps through live data
     * @return baking steps through live data
     */
    public LiveData<List<BakingStep>> getBakingSteps() {
        if (mLiveDataBakingSteps == null || mLiveDataBakingSteps.getValue() == null ||
                mLiveDataBakingSteps.getValue().get(0).getStepKey() != mRecipeKey) {
            // get baking steps from baking repository
            mLiveDataBakingSteps = mBakingRepository.getBakingSteps(mRecipeKey);
        }
        // return baking steps through live data
        return mLiveDataBakingSteps;
    }

    /**
     * Setter for recipe key
     * @param recipeKey to set
     */
    public void setRecipeKey(int recipeKey) {mRecipeKey = recipeKey;}

    /**
     * Getter for recipe name
     * @return recipe name
     */
    public String getRecipeName(){return mRecipeName;}

    /**
     * Setter for recipe name
     * @param recipeName to set
     */
    public void setRecipeName(String recipeName) {mRecipeName = recipeName;}

    /**
     * Getter for baking Step
     * @return baking step
     */
    public BakingStep getBakingStep() {
        return mBakingStep;
    }

    /**
     * Method to get live data baking steps
     * @return live data baking steps
     */
    public LiveData<BakingStep> getLiveDataBakingStep() {
        return mMutableLiveDataBakingStep;
    }

    /**
     * Method to get previous baking step from baking step list
     * @param stepId currently viewed
     */
    public void setPreviousBakingStep(int stepId) {
        // Declare variables
        List<BakingStep> bakingSteps;
        // get baking step list
        bakingSteps = mLiveDataBakingSteps.getValue();
        // get baking step from list
        if (bakingSteps != null) {
            for (int i = 1; i < bakingSteps.size(); i++) {
                if (bakingSteps.get(i).getId() == stepId) {
                    // set baking step if found
                    BakingStep bakingStep = bakingSteps.get(i - 1);
                    mMutableLiveDataBakingStep.setValue(bakingStep);
                    mMutableLiveDataDescription.setValue(bakingStep.getDescription());
                    mBakingStep = bakingStep;
                    return;
                }
            }
        }
    }

    /**
     * Method to set previous baking step from baking step list
     * @param stepId currently viewed
     */
    public void setNextBakingStep(int stepId) {
        // Declare variables
        List<BakingStep> bakingSteps;
        // get baking step list
        bakingSteps = mLiveDataBakingSteps.getValue();
        // get baking step from list
        if (bakingSteps != null) {
            for (int i = 0; i < bakingSteps.size() - 1; i++) {
                if (bakingSteps.get(i).getId() == stepId) {
                    // set baking step if found
                    BakingStep bakingStep = bakingSteps.get(i + 1);
                    mMutableLiveDataBakingStep.setValue(bakingStep);
                    mMutableLiveDataDescription.setValue(bakingStep.getDescription());
                    mBakingStep = bakingStep;
                    return;
                }
            }
        }
    }

    /**
     * Setter for baking step
     * @param bakingStep to set
     */
    public void setBakingStep(BakingStep bakingStep) {
        this.mBakingStep = bakingStep;
        mMutableLiveDataBakingStep.setValue(bakingStep);
        mMutableLiveDataDescription.setValue(bakingStep.getDescription());
    }

    /**
     * Getter for baking steps last id
     * @return baking steps last id
     */
    public int getLastStepId() {
        if (mLiveDataBakingSteps.getValue() != null && mLiveDataBakingSteps.getValue().size() > 0) {
            return mLiveDataBakingSteps.getValue().get(mLiveDataBakingSteps.getValue().size() - 1).getId();
        }
        return 0;
    }

    /**
     * Method to get the current baking step description
     * @return baking step description
     */
    public LiveData<String> getBakingStepDescription () {
        return mMutableLiveDataDescription;
    }
}