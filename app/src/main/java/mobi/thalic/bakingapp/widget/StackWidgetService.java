package mobi.thalic.bakingapp.widget;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import mobi.thalic.bakingapp.R;
import mobi.thalic.bakingapp.data.BakingDao;
import mobi.thalic.bakingapp.data.BakingIngredient;
import mobi.thalic.bakingapp.data.BakingRecipeEntity;
import mobi.thalic.bakingapp.data.BakingRoomDatabase;
import mobi.thalic.bakingapp.data.BakingWidgetRecipe;

/**
 * Class to handle stack widget service
 */
public class StackWidgetService extends RemoteViewsService {
    /**
     * Method to call stack remote views factory
     * @param intent to pass
     * @return new instance of stack remote views factory
     */
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new StackRemoteViewsFactory(getApplication(), getApplicationContext());
    }
}

/**
 * Class to handle stack remote views factory
 */
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    // Declare variables
    private Context mContext;
    private List<BakingWidgetRecipe> mBakingRecipes;
    private Application mApplication;
    private BakingDao mBakingDao;

    /**
     * Constructor for list remote views factory
     * @param context t0 use
     */
    public StackRemoteViewsFactory(Application application, Context context) {
        mContext = context;
        mApplication = application;
    }

    /**
     * Method to hadle create initializations
     */
    @Override
    public void onCreate() {
        BakingRoomDatabase bakingRoomDatabase = BakingRoomDatabase.getDatabase(mApplication);
        mBakingDao = bakingRoomDatabase.bakingDao();
    }

    /**
     * Method to update widget when the data changes
     */
    @Override
    public void onDataSetChanged() {
        // Declare and initialize variables
        List<BakingWidgetRecipe> bakingWidgetRecipeList = new ArrayList<>();
        // get baking recipes from room database
        List<BakingRecipeEntity> bakingRecipeEntityList = mBakingDao.getAllBakingRecipes();
        // loop through recipes
        for (int i = 0; i < bakingRecipeEntityList.size(); i++) {
            // declare and initialize variables
            BakingWidgetRecipe bakingWidgetRecipe = new BakingWidgetRecipe();
            bakingWidgetRecipe.setRecipeName(bakingRecipeEntityList.get(i).getName());
            // get list of ingredients for this recipe
            List<BakingIngredient> bakingIngredientList =
                    mBakingDao.getAllBakingIngredients(bakingRecipeEntityList.get(i).getId());
            List<String> stringList = new ArrayList<>();
            // add ingredients names to list
            for (int j = 0; j < bakingIngredientList.size(); j++) {
                stringList.add(bakingIngredientList.get(j).getIngredient());
            }
            // add list of names to baking recipes
            bakingWidgetRecipe.setBakingIngredientList(stringList);
            // add recipe to recipe list
            bakingWidgetRecipeList.add(bakingWidgetRecipe);
        }
        mBakingRecipes = bakingWidgetRecipeList;
    }

    /**
     * clean up when destroyed
     */
    @Override
    public void onDestroy() {
        mBakingRecipes = null;
    }

    /**
     * method to get count of baking recipes
     * @return count of baking recipes
     */
    @Override
    public int getCount() {
        if (mBakingRecipes == null) {
            return 0;
        }
        return mBakingRecipes.size();
    }

    /**
     * Method to bind data to the widget views
     * @param position position in baking recipe list
     * @return bound view
     */
    @Override
    public RemoteViews getViewAt(int position) {
        // declare and initilize variables
        RemoteViews stackView = new RemoteViews(mContext.getPackageName(),
                R.layout.baking_widget_stack_item);
        // set recipe name
        stackView.setTextViewText(R.id.tv_widget_recipe_name,
                mBakingRecipes.get(position).getRecipeName() );
        // create a string of the ingredients
        List<String> stringList = mBakingRecipes.get(position).getBakingIngredientList();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < stringList.size(); i++) {
            if (temp.toString().equals("")) {
                temp = new StringBuilder("• " + stringList.get(i));
            } else {
                temp.append("\n").append("• ").append(stringList.get(i));
            }
        }
        // set ingredients
        stackView.setTextViewText(R.id.tv_widget_ingredients, temp);
        return stackView;
    }

    // required overrides and basic returns
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
