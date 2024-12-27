package com.android.systemui.util.service.dagger;

import android.content.res.Resources;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ObservableServiceModule_ProvidesMaxReconnectAttemptsFactory implements Provider {
    private final javax.inject.Provider resourcesProvider;

    public ObservableServiceModule_ProvidesMaxReconnectAttemptsFactory(javax.inject.Provider provider) {
        this.resourcesProvider = provider;
    }

    public static ObservableServiceModule_ProvidesMaxReconnectAttemptsFactory create(javax.inject.Provider provider) {
        return new ObservableServiceModule_ProvidesMaxReconnectAttemptsFactory(provider);
    }

    public static int providesMaxReconnectAttempts(Resources resources) {
        return ObservableServiceModule.providesMaxReconnectAttempts(resources);
    }

    @Override // javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(providesMaxReconnectAttempts((Resources) this.resourcesProvider.get()));
    }
}
