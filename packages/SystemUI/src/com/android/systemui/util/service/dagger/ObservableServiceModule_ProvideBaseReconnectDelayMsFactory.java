package com.android.systemui.util.service.dagger;

import android.content.res.Resources;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
