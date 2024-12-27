package com.android.systemui.util.wakelock;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WakeLockLogger_Factory implements Provider {
    private final javax.inject.Provider bufferProvider;

    public WakeLockLogger_Factory(javax.inject.Provider provider) {
        this.bufferProvider = provider;
    }

    public static WakeLockLogger_Factory create(javax.inject.Provider provider) {
        return new WakeLockLogger_Factory(provider);
    }

    public static WakeLockLogger newInstance(LogBuffer logBuffer) {
        return new WakeLockLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public WakeLockLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
