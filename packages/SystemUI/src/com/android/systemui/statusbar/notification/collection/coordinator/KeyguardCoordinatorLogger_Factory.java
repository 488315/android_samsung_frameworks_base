package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
