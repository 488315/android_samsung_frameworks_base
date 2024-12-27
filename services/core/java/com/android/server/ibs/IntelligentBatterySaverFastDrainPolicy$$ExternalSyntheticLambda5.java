package com.android.server.ibs;

import android.bluetooth.BluetoothDevice;

import java.util.function.Predicate;

public final /* synthetic */ class IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda5
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((BluetoothDevice) obj).isConnected();
    }
}
