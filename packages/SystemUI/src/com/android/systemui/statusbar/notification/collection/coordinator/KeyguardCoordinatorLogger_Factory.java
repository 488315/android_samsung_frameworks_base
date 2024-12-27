package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;

public final class KeyguardCoordinatorLogger_Factory implements Provider {
    private final javax.inject.Provider bufferProvider;

    public KeyguardCoordinatorLogger_Factory(javax.inject.Provider provider) {
        this.bufferProvider = provider;
    }

    public static KeyguardCoordinatorLogger_Factory create(javax.inject.Provider provider) {
        return new KeyguardCoordinatorLogger_Factory(provider);
    }

    public static KeyguardCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new KeyguardCoordinatorLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public KeyguardCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
