package com.android.systemui.util.time;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SystemClockImpl implements SystemClock {
    @Override // com.android.systemui.util.time.SystemClock
    public long currentThreadTimeMillis() {
        return android.os.SystemClock.currentThreadTimeMillis();
    }

    @Override // com.android.systemui.util.time.SystemClock
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override // com.android.systemui.util.time.SystemClock
    public long elapsedRealtime() {
        return android.os.SystemClock.elapsedRealtime();
    }

    @Override // com.android.systemui.util.time.SystemClock
    public long elapsedRealtimeNanos() {
        return android.os.SystemClock.elapsedRealtimeNanos();
    }

    @Override // com.android.systemui.util.time.SystemClock
    public long uptimeMillis() {
        return android.os.SystemClock.uptimeMillis();
    }
}
