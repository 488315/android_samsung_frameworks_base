package com.android.server.power;

import android.app.ActivityManager;

import java.util.function.Supplier;

public final /* synthetic */ class LowPowerStandbyController$$ExternalSyntheticLambda2
        implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return ActivityManager.getService();
    }
}
