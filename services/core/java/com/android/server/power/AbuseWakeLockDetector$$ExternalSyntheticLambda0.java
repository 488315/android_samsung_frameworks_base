package com.android.server.power;

import com.android.server.location.gnss.hal.GnssNative;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AbuseWakeLockDetector$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = ((PowerManagerService.WakeLock) obj).mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
        return i == 6 || i == 10 || i == 26;
    }
}
