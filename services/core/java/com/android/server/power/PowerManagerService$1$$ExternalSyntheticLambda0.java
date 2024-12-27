package com.android.server.power;

import java.util.function.Predicate;

public final /* synthetic */ class PowerManagerService$1$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        PowerManagerService.WakeLock wakeLock = (PowerManagerService.WakeLock) obj;
        return wakeLock.mDisabled && PowerManagerService.isScreenLock(wakeLock);
    }
}
