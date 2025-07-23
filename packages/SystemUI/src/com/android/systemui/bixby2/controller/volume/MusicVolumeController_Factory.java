package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MusicVolumeController_Factory implements Provider {
    private final Provider contextProvider;

    public MusicVolumeController_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static MusicVolumeController_Factory create(javax.inject.Provider provider) {
        return new MusicVolumeController_Factory(Providers.asDaggerProvider(provider));
    }

    public static MusicVolumeController newInstance(Context context) {
        return new MusicVolumeController(context);
    }

    public static MusicVolumeController_Factory create(Provider provider) {
        return new MusicVolumeController_Factory(provider);
    }

    @Override // javax.inject.Provider
    public MusicVolumeController get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
