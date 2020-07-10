package com.larsonapps.bakingapp.data;

import android.content.Context;

import androidx.annotation.NonNull;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class to handle a room database baking_database
 */
@Database(entities = {BakingRecipeEntity.class, BakingIngredient.class, BakingStep.class},
        version = 2)
public abstract class BakingRoomDatabase extends RoomDatabase {
    // register DAO
    public abstract BakingDao bakingDao();

    // create an instance of the database
    private static volatile BakingRoomDatabase INSTANCE;
    // set number of threads
    private static final int NUMBER_OF_THREADS = 4;
    // create executor
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Method to get or create baking_database
     * @param context to use for database
     * @return the database instance
     */
    static BakingRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BakingRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BakingRoomDatabase.class, "baking_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    /**
     * Callback for database
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };

}
