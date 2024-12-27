package com.android.server;

import com.android.server.deviceidle.DeviceIdleConstraintTracker;

import java.util.function.Predicate;

public final /* synthetic */ class DeviceIdleController$$ExternalSyntheticLambda11
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((DeviceIdleConstraintTracker) obj).active;
    }
}
