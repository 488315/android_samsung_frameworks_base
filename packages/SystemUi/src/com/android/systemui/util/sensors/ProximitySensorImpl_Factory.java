package com.android.systemui.util.sensors;

import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ProximitySensorImpl_Factory implements Provider {
    public final Provider delayableExecutorProvider;
    public final Provider executionProvider;
    public final Provider primaryProvider;
    public final Provider secondaryProvider;

    public ProximitySensorImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.primaryProvider = provider;
        this.secondaryProvider = provider2;
        this.delayableExecutorProvider = provider3;
        this.executionProvider = provider4;
    }

    public static ProximitySensorImpl newInstance(ThresholdSensor thresholdSensor, ThresholdSensor thresholdSensor2, DelayableExecutor delayableExecutor, Execution execution) {
        return new ProximitySensorImpl(thresholdSensor, thresholdSensor2, delayableExecutor, execution);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ProximitySensorImpl((ThresholdSensor) this.primaryProvider.get(), (ThresholdSensor) this.secondaryProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get(), (Execution) this.executionProvider.get());
    }
}
