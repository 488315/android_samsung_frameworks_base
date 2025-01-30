package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BluetoothVolumeController_Factory implements Provider {
    private final Provider contextProvider;

    public BluetoothVolumeController_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static BluetoothVolumeController_Factory create(Provider provider) {
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
