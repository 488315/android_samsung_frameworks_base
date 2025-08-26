package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes.dex */
public final class BluetoothVolumeController_Factory implements Provider {
    private final Provider contextProvider;

    public BluetoothVolumeController_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static BluetoothVolumeController_Factory create(javax.inject.Provider provider) {
        return new BluetoothVolumeController_Factory(Providers.asDaggerProvider(provider));
    }

    public static BluetoothVolumeController newInstance(Context context) {
        return new BluetoothVolumeController(context);
    }

    public static BluetoothVolumeController_Factory create(Provider provider) {
        return new BluetoothVolumeController_Factory(provider);
    }

    @Override // javax.inject.Provider
    public BluetoothVolumeController get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
