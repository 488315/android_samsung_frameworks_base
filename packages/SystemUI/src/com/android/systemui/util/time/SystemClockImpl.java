package com.android.systemui.util.time;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
