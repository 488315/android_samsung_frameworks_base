package com.android.systemui.util.time;

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
