package com.android.systemui.util.service.dagger;

import android.content.res.Resources;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class ObservableServiceModule_ProvideBaseReconnectDelayMsFactory implements Provider {
    private final Provider resourcesProvider;

    public ObservableServiceModule_ProvideBaseReconnectDelayMsFactory(Provider provider) {
        this.resourcesProvider = provider;
    }

    public static ObservableServiceModule_ProvideBaseReconnectDelayMsFactory create(javax.inject.Provider provider) {
        return new ObservableServiceModule_ProvideBaseReconnectDelayMsFactory(Providers.asDaggerProvider(provider));
    }

    public static int provideBaseReconnectDelayMs(Resources resources) {
        return ObservableServiceModule.provideBaseReconnectDelayMs(resources);
    }

    public static ObservableServiceModule_ProvideBaseReconnectDelayMsFactory create(Provider provider) {
        return new ObservableServiceModule_ProvideBaseReconnectDelayMsFactory(provider);
    }

    @Override // javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(provideBaseReconnectDelayMs((Resources) this.resourcesProvider.get()));
    }
}
