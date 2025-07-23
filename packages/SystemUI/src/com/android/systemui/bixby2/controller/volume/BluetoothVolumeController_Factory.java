package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
