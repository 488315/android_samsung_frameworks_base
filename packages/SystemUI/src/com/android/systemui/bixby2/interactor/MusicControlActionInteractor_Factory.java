package com.android.systemui.bixby2.interactor;

import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
