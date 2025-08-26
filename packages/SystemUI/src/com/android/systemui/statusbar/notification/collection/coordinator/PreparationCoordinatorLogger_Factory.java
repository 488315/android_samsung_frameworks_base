package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class PreparationCoordinatorLogger_Factory implements Provider {
    private final Provider bufferProvider;

    public PreparationCoordinatorLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static PreparationCoordinatorLogger_Factory create(javax.inject.Provider provider) {
        return new PreparationCoordinatorLogger_Factory(Providers.asDaggerProvider(provider));
    }

    public static PreparationCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new PreparationCoordinatorLogger(logBuffer);
    }

    public static PreparationCoordinatorLogger_Factory create(Provider provider) {
        return new PreparationCoordinatorLogger_Factory(provider);
    }

    @Override // javax.inject.Provider
    public PreparationCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
