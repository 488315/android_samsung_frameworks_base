package com.android.server.policy;

import android.metrics.LogMaker;
import android.os.SystemClock;

import com.android.internal.logging.MetricsLogger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DisplayFoldDurationLogger {
    public volatile int mScreenState = -1;
    public volatile Long mLastChanged = null;
    public final MetricsLogger mLogger = new MetricsLogger();

    public final void log() {
        int i;
        Long l = this.mLastChanged;
        if (l == null || this.mLastChanged == null) {
            return;
        }
        int i2 = this.mScreenState;
        if (i2 == 1) {
            i = Integer.MIN_VALUE;
        } else if (i2 != 2) {
            return;
        } else {
            i = -2147483647;
        }
        this.mLogger.write(
                new LogMaker(1594)
                        .setType(4)
                        .setSubtype(i)
                        .setLatency(SystemClock.uptimeMillis() - l.longValue()));
    }
}
