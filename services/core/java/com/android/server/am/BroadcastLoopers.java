package com.android.server.am;

import android.os.Looper;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Slog;

import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;

public abstract class BroadcastLoopers {
    public static final ArraySet sLoopers = new ArraySet();

    public static void addMyLooper() {
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            ArraySet arraySet = sLoopers;
            synchronized (arraySet) {
                try {
                    if (arraySet.add(myLooper)) {
                        Slog.w(
                                "BroadcastLoopers",
                                "Found previously unknown looper " + myLooper.getThread());
                    }
                } finally {
                }
            }
        }
    }

    public static void waitForCondition(PrintWriter printWriter, BiConsumer biConsumer) {
        CountDownLatch countDownLatch;
        ArraySet arraySet = sLoopers;
        synchronized (arraySet) {
            try {
                int size = arraySet.size();
                countDownLatch = new CountDownLatch(size);
                for (int i = 0; i < size; i++) {
                    Looper looper = (Looper) sLoopers.valueAt(i);
                    if (looper.getQueue().isIdle()) {
                        countDownLatch.countDown();
                    } else {
                        biConsumer.accept(looper, countDownLatch);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        long j = 0;
        while (countDownLatch.getCount() > 0) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (uptimeMillis >= 1000 + j) {
                printWriter.println(
                        "Waiting for " + countDownLatch.getCount() + " loopers to drain...");
                printWriter.flush();
                j = uptimeMillis;
            }
            SystemClock.sleep(100L);
        }
        printWriter.println("Loopers drained!");
        printWriter.flush();
    }
}
