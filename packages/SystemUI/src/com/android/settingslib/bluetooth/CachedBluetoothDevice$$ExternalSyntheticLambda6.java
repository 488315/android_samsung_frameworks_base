package com.android.settingslib.bluetooth;

import java.util.function.Function;

/* loaded from: classes.dex */
public final /* synthetic */ class CachedBluetoothDevice$$ExternalSyntheticLambda6 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((CachedBluetoothDevice) obj).mDevice.getBatteryLevel());
    }
}
