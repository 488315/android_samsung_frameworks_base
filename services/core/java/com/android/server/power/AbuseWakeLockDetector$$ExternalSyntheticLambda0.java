package com.android.server.power;

import com.android.server.location.gnss.hal.GnssNative;

import java.util.function.Predicate;

public final /* synthetic */ class AbuseWakeLockDetector$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = ((PowerManagerService.WakeLock) obj).mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
        return i == 6 || i == 10 || i == 26;
    }
}
