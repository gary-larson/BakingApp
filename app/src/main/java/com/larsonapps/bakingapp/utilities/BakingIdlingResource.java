package com.larsonapps.bakingapp.utilities;

import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class to deal with idling resources for testing purposes
 */
public class BakingIdlingResource implements IdlingResource {
    // Declare variables
    // allow null when not testing
    @Nullable
    private volatile ResourceCallback mCallback;

    // Indicater of resource idle
    private AtomicBoolean mIsIdleNow  = new AtomicBoolean(true);

    /**
     * Getter for class name
     * @return class name
     */
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    /**
     * Getter for idle indicator
     * @return idle indicator
     */
    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    /**
     * Method to register callback
     * @param callback to set
     */
    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    /**
     * Setter for idle state
     * @param idleState to set
     */
    public void setIdleState (boolean idleState) {
        mIsIdleNow.set(idleState);
        if (idleState && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}
