package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import dagger.internal.Provider;

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
