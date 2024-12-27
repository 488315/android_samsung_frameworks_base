package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BluetoothVolumeController_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public BluetoothVolumeController_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static BluetoothVolumeController_Factory create(javax.inject.Provider provider) {
        return new BluetoothVolumeController_Factory(provider);
    }

    public static BluetoothVolumeController newInstance(Context context) {
        return new BluetoothVolumeController(context);
    }

    @Override // javax.inject.Provider
    public BluetoothVolumeController get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
