package mobi.thalic.bakingapp.data;

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

import mobi.thalic.bakingapp.data.BakingDao;
import mobi.thalic.bakingapp.data.BakingRecipeEntity;
import mobi.thalic.bakingapp.data.BakingRoomDatabase;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BakingRoomDatabaseRecipeTest {
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
        BakingRecipeEntity bakingRecipeEntity = new BakingRecipeEntity(1,
                "Chocolate Cake", 8, "https://some.com/image/5464554.png");
        List<BakingRecipeEntity> bakingRecipeEntities = new ArrayList<>();
        bakingRecipeEntities.add(bakingRecipeEntity);
        bakingDao.InsertAllBakingRecipes(bakingRecipeEntities);
        List<BakingRecipeEntity> bakingRecipeEntities1 = bakingDao.getAllBakingRecipes();
        assertEquals(bakingRecipeEntities.get(0).getName(), bakingRecipeEntities1.get(0).getName());
        assertEquals(bakingRecipeEntities.get(0).getId(), bakingRecipeEntities1.get(0).getId());
        assertEquals(bakingRecipeEntities.get(0).getImage(), bakingRecipeEntities1.get(0).getImage());
        assertEquals(bakingRecipeEntities.get(0).getServings(), bakingRecipeEntities1.get(0).getServings());
    }

    @After
    public void closeDatabase() {
        bakingRoomDatabase.close();
    }
}
