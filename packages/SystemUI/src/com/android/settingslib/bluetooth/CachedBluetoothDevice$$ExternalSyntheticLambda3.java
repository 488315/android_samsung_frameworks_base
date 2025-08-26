package com.android.settingslib.bluetooth;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

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
                callback.onDeviceAttributesChanged();
            }
        });
    }
}
