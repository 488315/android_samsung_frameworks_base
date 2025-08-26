package com.android.settingslib.avatarpicker;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class ThreadUtils {
    public static volatile ListeningExecutorService sListeningService;

    public static synchronized ListeningExecutorService getBackgroundExecutor() {
        try {
            if (sListeningService == null) {
                sListeningService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
            }
        } catch (Throwable th) {
            throw th;
        }
        return sListeningService;
    }
}
