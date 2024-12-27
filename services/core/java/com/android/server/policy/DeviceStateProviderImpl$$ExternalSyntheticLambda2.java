package com.android.server.policy;

import android.hardware.devicestate.DeviceState;

import java.util.function.ToIntFunction;

public final /* synthetic */ class DeviceStateProviderImpl$$ExternalSyntheticLambda2
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((DeviceState) obj).getIdentifier();
    }
}
