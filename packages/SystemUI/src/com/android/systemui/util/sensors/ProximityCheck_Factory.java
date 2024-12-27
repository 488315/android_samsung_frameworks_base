package com.android.systemui.util.sensors;

import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Provider;

public final class ProximityCheck_Factory implements Provider {
    private final javax.inject.Provider delayableExecutorProvider;
    private final javax.inject.Provider sensorProvider;

    public ProximityCheck_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.sensorProvider = provider;
        this.delayableExecutorProvider = provider2;
    }

    public static ProximityCheck_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new ProximityCheck_Factory(provider, provider2);
    }

    public static ProximityCheck newInstance(ProximitySensor proximitySensor, DelayableExecutor delayableExecutor) {
        return new ProximityCheck(proximitySensor, delayableExecutor);
    }

    @Override // javax.inject.Provider
    public ProximityCheck get() {
        return newInstance((ProximitySensor) this.sensorProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get());
    }
}
