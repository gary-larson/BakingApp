package com.larsonapps.bakingapp.data;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BakingRoomDatabaseIngredientTest {
    private BakingDao bakingDao;
    private BakingRoomDatabase bakingRoomDatabase;

    @Before
    public void createDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        bakingRoomDatabase = Room.inMemoryDatabaseBuilder(context, BakingRoomDatabase.class).build();
        bakingDao = bakingRoomDatabase.bakingDao();
    }

    @Test
    public void testBakingRecipeInsertAllAndSelectAll() {
        BakingIngredient bakingIngredient = new BakingIngredient();
        bakingIngredient.setRecipeKey(1);
        bakingIngredient.setIngredient("Milk");
        bakingIngredient.setMeasure("Cup");
        bakingIngredient.setQuantity(2.5);
        BakingIngredient bakingIngredient1 = new BakingIngredient();
        bakingIngredient1.setRecipeKey(2);
        bakingIngredient1.setIngredient("Curry");
        bakingIngredient1.setMeasure("Tablespoon");
        bakingIngredient1.setQuantity(0.5);
        List<BakingIngredient> bakingIngredients = new ArrayList<>();
        bakingIngredients.add(bakingIngredient);
        bakingIngredients.add(bakingIngredient1);
        bakingDao.InsertAllBakingIngredients(bakingIngredients);
        List<BakingIngredient> bakingIngredients1 = bakingDao.getAllBakingIngredients(1);

        if (bakingIngredients1 != null && bakingIngredients1.size() > 0) {
            assertEquals(bakingIngredients.get(0).getRecipeKey(), bakingIngredients1.get(0).getRecipeKey());
            assertEquals(bakingIngredients.get(0).getIngredient(), bakingIngredients1.get(0).getIngredient());
            assertEquals(bakingIngredients.get(0).getMeasure(), bakingIngredients1.get(0).getMeasure());
            assertEquals(bakingIngredients.get(0).getQuantity(), bakingIngredients1.get(0).getQuantity(), 0.01);
        }
        List<BakingIngredient> bakingIngredients2 = bakingDao.getAllBakingIngredients(2);

        if (bakingIngredients2 != null && bakingIngredients2.size() > 0) {
            assertEquals(bakingIngredients.get(1).getRecipeKey(), bakingIngredients2.get(0).getRecipeKey());
            assertEquals(bakingIngredients.get(1).getIngredient(), bakingIngredients2.get(0).getIngredient());
            assertEquals(bakingIngredients.get(1).getMeasure(), bakingIngredients2.get(0).getMeasure());
            assertEquals(bakingIngredients.get(1).getQuantity(), bakingIngredients2.get(0).getQuantity(), 0.01);
        }
    }

    @After
    public void closeDatabase() {
        bakingRoomDatabase.close();
    }
}
