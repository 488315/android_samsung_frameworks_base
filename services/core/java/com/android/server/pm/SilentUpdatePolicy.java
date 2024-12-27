package com.android.server.pm;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Pair;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SilentUpdatePolicy {
    public static final long SILENT_UPDATE_THROTTLE_TIME_MS = TimeUnit.SECONDS.toMillis(30);
    public String mAllowUnlimitedSilentUpdatesInstaller;
    public final ArrayMap mSilentUpdateInfos = new ArrayMap();
    public long mSilentUpdateThrottleTimeMs = SILENT_UPDATE_THROTTLE_TIME_MS;

    public final boolean isSilentUpdateAllowed(String str, String str2) {
        Long l;
        long j;
        if (str == null) {
            return true;
        }
        Pair create = Pair.create(str, str2);
        synchronized (this.mSilentUpdateInfos) {
            l = (Long) this.mSilentUpdateInfos.get(create);
        }
        long longValue = l != null ? l.longValue() : -1L;
        synchronized (this.mSilentUpdateInfos) {
            j = this.mSilentUpdateThrottleTimeMs;
        }
        return SystemClock.uptimeMillis() - longValue > j;
    }
}
