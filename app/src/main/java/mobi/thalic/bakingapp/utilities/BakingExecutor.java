package mobi.thalic.bakingapp.utilities;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Class to deal with Executor
 */
public class BakingExecutor implements Executor {
    /**
     * Method to start a thread to execute
     * @param command to start
     */
    @Override
    public void execute(@NonNull Runnable command) {
        new Thread(command).start();
    }
}
