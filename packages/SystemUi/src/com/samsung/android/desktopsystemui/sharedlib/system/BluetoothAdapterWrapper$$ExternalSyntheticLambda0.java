package com.samsung.android.desktopsystemui.sharedlib.system;

import android.bluetooth.BluetoothDevice;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothAdapterWrapper$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean isConnected;
        isConnected = ((BluetoothDevice) obj).isConnected();
        return isConnected;
    }
}
