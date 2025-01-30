package com.android.systemui.bixby2.interactor;

import android.content.Context;
import com.android.systemui.bixby2.controller.DeviceController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DeviceControlActionInteractor_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider deviceControllerProvider;

    public DeviceControlActionInteractor_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.deviceControllerProvider = provider2;
    }

    public static DeviceControlActionInteractor_Factory create(Provider provider, Provider provider2) {
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
