package com.android.systemui.util.service.dagger;

import android.content.res.Resources;
import dagger.internal.Provider;

public final class ObservableServiceModule_ProvideBaseReconnectDelayMsFactory implements Provider {
    private final javax.inject.Provider resourcesProvider;

    public ObservableServiceModule_ProvideBaseReconnectDelayMsFactory(javax.inject.Provider provider) {
        this.resourcesProvider = provider;
    }

    public static ObservableServiceModule_ProvideBaseReconnectDelayMsFactory create(javax.inject.Provider provider) {
        return new ObservableServiceModule_ProvideBaseReconnectDelayMsFactory(provider);
    }

    public static int provideBaseReconnectDelayMs(Resources resources) {
        return ObservableServiceModule.provideBaseReconnectDelayMs(resources);
    }

    @Override // javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(provideBaseReconnectDelayMs((Resources) this.resourcesProvider.get()));
    }
}
