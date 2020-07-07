package com.larsonapps.bakingapp.data;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BakingRoomDatabaseStepTest {
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
        BakingStep bakingStep = new BakingStep();
        bakingStep.setRecipeKey(1);
        bakingStep.setId(55);
        bakingStep.setShortDescription("This is a short Description");
        bakingStep.setDescription("This is a description");
        bakingStep.setVideoURL("https://videos/6488846.png");
        bakingStep.setThumbnailURL("https://thumbnails/94886.png");
        BakingStep bakingStep1 = new BakingStep();
        bakingStep1.setRecipeKey(2);
        bakingStep1.setId(66);
        bakingStep1.setShortDescription("This is a very short Description");
        bakingStep1.setDescription("This is an amazing description");
        bakingStep1.setVideoURL("https://videos/4648.png");
        bakingStep1.setThumbnailURL("https://thumbnails/48848486.png");
        List<BakingStep> bakingSteps = new ArrayList<>();
        bakingSteps.add(bakingStep);
        bakingSteps.add(bakingStep1);
        bakingDao.InsertAllBakingSteps(bakingSteps);
        List<BakingStep> bakingSteps1 = bakingDao.getAllBakingSteps(1);
        if (bakingSteps1 != null && bakingSteps1.size() > 0) {
            assertEquals(bakingSteps.get(0).getRecipeKey(), bakingSteps1.get(0).getRecipeKey());
            assertEquals(bakingSteps.get(0).getId(), bakingSteps1.get(0).getId());
            assertEquals(bakingSteps.get(0).getShortDescription(), bakingSteps1.get(0).getShortDescription());
            assertEquals(bakingSteps.get(0).getDescription(), bakingSteps1.get(0).getDescription());
            assertEquals(bakingSteps.get(0).getVideoURL(), bakingSteps1.get(0).getVideoURL());
            assertEquals(bakingSteps.get(0).getThumbnailURL(), bakingSteps1.get(0).getThumbnailURL());
        }
        List<BakingStep> bakingSteps2 = bakingDao.getAllBakingSteps(2);
        if (bakingSteps2 != null && bakingSteps2.size() > 0) {
            assertEquals(bakingSteps.get(1).getRecipeKey(), bakingSteps2.get(0).getRecipeKey());
            assertEquals(bakingSteps.get(1).getId(), bakingSteps2.get(0).getId());
            assertEquals(bakingSteps.get(1).getShortDescription(), bakingSteps2.get(0).getShortDescription());
            assertEquals(bakingSteps.get(1).getDescription(), bakingSteps2.get(0).getDescription());
            assertEquals(bakingSteps.get(1).getVideoURL(), bakingSteps2.get(0).getVideoURL());
            assertEquals(bakingSteps.get(1).getThumbnailURL(), bakingSteps2.get(0).getThumbnailURL());
        }
    }

    @After
    public void closeDatabase() {
        bakingRoomDatabase.close();
    }
}
