package com.android.systemui.bixby2.interactor;

import android.content.Context;
import com.android.systemui.bixby2.controller.DeviceController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DeviceControlActionInteractor_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider deviceControllerProvider;

    public DeviceControlActionInteractor_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.deviceControllerProvider = provider2;
    }

    public static DeviceControlActionInteractor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new DeviceControlActionInteractor_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static DeviceControlActionInteractor newInstance(Context context, DeviceController deviceController) {
        return new DeviceControlActionInteractor(context, deviceController);
    }

    public static DeviceControlActionInteractor_Factory create(Provider provider, Provider provider2) {
        return new DeviceControlActionInteractor_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public DeviceControlActionInteractor get() {
        return newInstance((Context) this.contextProvider.get(), (DeviceController) this.deviceControllerProvider.get());
    }
}
