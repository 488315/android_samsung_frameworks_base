package com.android.systemui.bixby2.interactor;

import android.content.Context;
import dagger.internal.Provider;

public final class MusicControlActionInteractor_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public MusicControlActionInteractor_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static MusicControlActionInteractor_Factory create(javax.inject.Provider provider) {
        return new MusicControlActionInteractor_Factory(provider);
    }

    public static MusicControlActionInteractor newInstance(Context context) {
        return new MusicControlActionInteractor(context);
    }

    @Override // javax.inject.Provider
    public MusicControlActionInteractor get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
