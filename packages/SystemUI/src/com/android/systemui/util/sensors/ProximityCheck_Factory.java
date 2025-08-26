package com.android.systemui.util.sensors;

import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class ProximityCheck_Factory implements Provider {
    private final Provider delayableExecutorProvider;
    private final Provider sensorProvider;

    public ProximityCheck_Factory(Provider provider, Provider provider2) {
        this.sensorProvider = provider;
        this.delayableExecutorProvider = provider2;
    }

    public static ProximityCheck_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new ProximityCheck_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static ProximityCheck newInstance(ProximitySensor proximitySensor, DelayableExecutor delayableExecutor) {
        return new ProximityCheck(proximitySensor, delayableExecutor);
    }

    public static ProximityCheck_Factory create(Provider provider, Provider provider2) {
        return new ProximityCheck_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public ProximityCheck get() {
        return newInstance((ProximitySensor) this.sensorProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get());
    }
}
