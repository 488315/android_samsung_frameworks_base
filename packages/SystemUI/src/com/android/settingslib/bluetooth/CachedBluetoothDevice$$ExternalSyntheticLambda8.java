package com.android.settingslib.bluetooth;

import java.util.function.ToIntFunction;

/* loaded from: classes.dex */
public final /* synthetic */ class CachedBluetoothDevice$$ExternalSyntheticLambda8 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((CachedBluetoothDevice) obj).mDevice.getBatteryLevel();
    }
}
