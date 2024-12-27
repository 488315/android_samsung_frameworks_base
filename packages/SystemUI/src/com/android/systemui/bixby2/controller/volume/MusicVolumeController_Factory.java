package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import dagger.internal.Provider;

public final class MusicVolumeController_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public MusicVolumeController_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static MusicVolumeController_Factory create(javax.inject.Provider provider) {
        return new MusicVolumeController_Factory(provider);
    }

    public static MusicVolumeController newInstance(Context context) {
        return new MusicVolumeController(context);
    }

    @Override // javax.inject.Provider
    public MusicVolumeController get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
