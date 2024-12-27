package com.android.server.chimera;

import android.bluetooth.BluetoothAdapter;

import java.util.function.Function;

public final /* synthetic */ class ChimeraAppClassifier$$ExternalSyntheticLambda1
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((BluetoothAdapter) obj).getHWUsingApps();
    }
}
