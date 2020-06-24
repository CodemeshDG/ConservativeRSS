package com.dommyg.conservativerss.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static AppExecutors instance;

    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    /**
     * Responsible for background database operations (cached data).
     */
    private final Executor diskIO = Executors.newSingleThreadExecutor();

    /**
     * Responsible for posting to the main thread.
     */
    private final Executor mainThreadExecutor = new MainThreadExecutor();

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getMainThreadExecutor() {
        return mainThreadExecutor;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
