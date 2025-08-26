package com.android.systemui.util.time;

/* loaded from: classes3.dex */
public interface SystemClock {
    long currentThreadTimeMillis();

    long currentTimeMillis();

    long elapsedRealtime();

    long elapsedRealtimeNanos();

    long uptimeMillis();
}
