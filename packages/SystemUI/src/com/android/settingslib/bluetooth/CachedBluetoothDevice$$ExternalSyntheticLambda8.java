package com.android.settingslib.bluetooth;

import java.util.function.ToIntFunction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class CachedBluetoothDevice$$ExternalSyntheticLambda8 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((CachedBluetoothDevice) obj).mDevice.getBatteryLevel();
    }
}
