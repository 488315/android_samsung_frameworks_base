package com.android.systemui.util.sensors;

import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ProximitySensorImpl_Factory implements Provider {
    private final Provider delayableExecutorProvider;
    private final Provider executionProvider;
    private final Provider primaryProvider;
    private final Provider secondaryProvider;

    public ProximitySensorImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.primaryProvider = provider;
        this.secondaryProvider = provider2;
        this.delayableExecutorProvider = provider3;
        this.executionProvider = provider4;
    }

    public static ProximitySensorImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new ProximitySensorImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static ProximitySensorImpl newInstance(ThresholdSensor thresholdSensor, ThresholdSensor thresholdSensor2, DelayableExecutor delayableExecutor, Execution execution) {
        return new ProximitySensorImpl(thresholdSensor, thresholdSensor2, delayableExecutor, execution);
    }

    public static ProximitySensorImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new ProximitySensorImpl_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public ProximitySensorImpl get() {
        return newInstance((ThresholdSensor) this.primaryProvider.get(), (ThresholdSensor) this.secondaryProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get(), (Execution) this.executionProvider.get());
    }
}
