package com.android.systemui.util.sensors;

import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ProximitySensorImpl_Factory implements Provider {
    private final javax.inject.Provider delayableExecutorProvider;
    private final javax.inject.Provider executionProvider;
    private final javax.inject.Provider primaryProvider;
    private final javax.inject.Provider secondaryProvider;

    public ProximitySensorImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.primaryProvider = provider;
        this.secondaryProvider = provider2;
        this.delayableExecutorProvider = provider3;
        this.executionProvider = provider4;
    }

    public static ProximitySensorImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new ProximitySensorImpl_Factory(provider, provider2, provider3, provider4);
    }

    public static ProximitySensorImpl newInstance(ThresholdSensor thresholdSensor, ThresholdSensor thresholdSensor2, DelayableExecutor delayableExecutor, Execution execution) {
        return new ProximitySensorImpl(thresholdSensor, thresholdSensor2, delayableExecutor, execution);
    }

    @Override // javax.inject.Provider
    public ProximitySensorImpl get() {
        return newInstance((ThresholdSensor) this.primaryProvider.get(), (ThresholdSensor) this.secondaryProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get(), (Execution) this.executionProvider.get());
    }
}
