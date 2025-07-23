package com.android.internal.util;

import android.util.Slog;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class GcUtils {
    private static final String TAG = "GcUtils";

    public static void runGcAndFinalizersSync() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        createFinalizationObserver(countDownLatch);
        do {
            try {
                Runtime.getRuntime().gc();
                Runtime.getRuntime().runFinalization();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (!countDownLatch.await(100L, TimeUnit.MILLISECONDS));
        Slog.v(TAG, "Running gc and finalizers");
    }

    private static void createFinalizationObserver(final CountDownLatch countDownLatch) {
        new Object() { // from class: com.android.internal.util.GcUtils.1
            protected void finalize() throws Throwable {
                try {
                    countDownLatch.countDown();
                } finally {
                    super.finalize();
                }
            }
        };
    }

    private GcUtils() {
    }
}
