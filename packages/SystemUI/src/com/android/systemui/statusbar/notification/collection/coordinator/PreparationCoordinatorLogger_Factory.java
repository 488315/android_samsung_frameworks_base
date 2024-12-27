package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;

public final class PreparationCoordinatorLogger_Factory implements Provider {
    private final javax.inject.Provider bufferProvider;

    public PreparationCoordinatorLogger_Factory(javax.inject.Provider provider) {
        this.bufferProvider = provider;
    }

    public static PreparationCoordinatorLogger_Factory create(javax.inject.Provider provider) {
        return new PreparationCoordinatorLogger_Factory(provider);
    }

    public static PreparationCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new PreparationCoordinatorLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public PreparationCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
