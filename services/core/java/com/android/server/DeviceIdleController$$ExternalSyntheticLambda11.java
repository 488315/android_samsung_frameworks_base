package com.android.server;

import com.android.server.deviceidle.DeviceIdleConstraintTracker;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DeviceIdleController$$ExternalSyntheticLambda11
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((DeviceIdleConstraintTracker) obj).active;
    }
}
