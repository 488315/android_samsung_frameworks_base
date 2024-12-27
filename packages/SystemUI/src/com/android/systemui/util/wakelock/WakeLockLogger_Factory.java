package com.android.systemui.util.wakelock;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;

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
