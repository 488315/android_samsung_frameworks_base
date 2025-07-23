package com.android.settingslib.bluetooth;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class CachedBluetoothDevice$$ExternalSyntheticLambda3 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        final CachedBluetoothDevice.Callback callback = (CachedBluetoothDevice.Callback) obj;
        int i = CachedBluetoothDevice.$r8$clinit;
        Objects.requireNonNull(callback);
        ((Executor) obj2).execute(new Runnable() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                CachedBluetoothDevice.Callback.this.onDeviceAttributesChanged();
            }
        });
    }
}
