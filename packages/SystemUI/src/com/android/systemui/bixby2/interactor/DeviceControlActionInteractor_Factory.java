package com.android.systemui.bixby2.interactor;

import android.content.Context;
import com.android.systemui.bixby2.controller.DeviceController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceControlActionInteractor_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider deviceControllerProvider;

    public DeviceControlActionInteractor_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.deviceControllerProvider = provider2;
    }

    public static DeviceControlActionInteractor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new DeviceControlActionInteractor_Factory(provider, provider2);
    }

    public static DeviceControlActionInteractor newInstance(Context context, DeviceController deviceController) {
        return new DeviceControlActionInteractor(context, deviceController);
    }

    @Override // javax.inject.Provider
    public DeviceControlActionInteractor get() {
        return newInstance((Context) this.contextProvider.get(), (DeviceController) this.deviceControllerProvider.get());
    }
}
