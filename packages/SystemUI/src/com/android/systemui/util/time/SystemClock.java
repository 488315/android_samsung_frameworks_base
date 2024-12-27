package com.android.systemui.util.time;

public interface SystemClock {
    long currentThreadTimeMillis();

    long currentTimeMillis();

    long elapsedRealtime();

    long elapsedRealtimeNanos();

    long uptimeMillis();
}
