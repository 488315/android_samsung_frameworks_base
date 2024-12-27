package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import dagger.internal.Provider;

public final class AllVolumeController_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public AllVolumeController_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static AllVolumeController_Factory create(javax.inject.Provider provider) {
        return new AllVolumeController_Factory(provider);
    }

    public static AllVolumeController newInstance(Context context) {
        return new AllVolumeController(context);
    }

    @Override // javax.inject.Provider
    public AllVolumeController get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
