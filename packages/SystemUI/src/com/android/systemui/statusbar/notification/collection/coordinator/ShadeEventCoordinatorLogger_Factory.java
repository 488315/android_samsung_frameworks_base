package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeEventCoordinatorLogger_Factory implements Provider {
    private final javax.inject.Provider bufferProvider;

    public ShadeEventCoordinatorLogger_Factory(javax.inject.Provider provider) {
        this.bufferProvider = provider;
    }

    public static ShadeEventCoordinatorLogger_Factory create(javax.inject.Provider provider) {
        return new ShadeEventCoordinatorLogger_Factory(provider);
    }

    public static ShadeEventCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new ShadeEventCoordinatorLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public ShadeEventCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
