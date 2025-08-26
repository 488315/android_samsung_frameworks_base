package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class VisualStabilityCoordinatorLogger_Factory implements Provider {
    private final Provider bufferProvider;

    public VisualStabilityCoordinatorLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static VisualStabilityCoordinatorLogger_Factory create(javax.inject.Provider provider) {
        return new VisualStabilityCoordinatorLogger_Factory(Providers.asDaggerProvider(provider));
    }

    public static VisualStabilityCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new VisualStabilityCoordinatorLogger(logBuffer);
    }

    public static VisualStabilityCoordinatorLogger_Factory create(Provider provider) {
        return new VisualStabilityCoordinatorLogger_Factory(provider);
    }

    @Override // javax.inject.Provider
    public VisualStabilityCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
