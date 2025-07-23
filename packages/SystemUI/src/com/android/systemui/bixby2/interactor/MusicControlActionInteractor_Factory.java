package com.android.systemui.bixby2.interactor;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MusicControlActionInteractor_Factory implements Provider {
    private final Provider contextProvider;

    public MusicControlActionInteractor_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static MusicControlActionInteractor_Factory create(javax.inject.Provider provider) {
        return new MusicControlActionInteractor_Factory(Providers.asDaggerProvider(provider));
    }

    public static MusicControlActionInteractor newInstance(Context context) {
        return new MusicControlActionInteractor(context);
    }

    public static MusicControlActionInteractor_Factory create(Provider provider) {
        return new MusicControlActionInteractor_Factory(provider);
    }

    @Override // javax.inject.Provider
    public MusicControlActionInteractor get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
