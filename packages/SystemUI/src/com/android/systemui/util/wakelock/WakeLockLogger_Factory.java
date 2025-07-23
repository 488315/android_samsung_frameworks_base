package com.android.systemui.util.wakelock;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WakeLockLogger_Factory implements Provider {
    private final Provider bufferProvider;

    public WakeLockLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static WakeLockLogger_Factory create(javax.inject.Provider provider) {
        return new WakeLockLogger_Factory(Providers.asDaggerProvider(provider));
    }

    public static WakeLockLogger newInstance(LogBuffer logBuffer) {
        return new WakeLockLogger(logBuffer);
    }

    public static WakeLockLogger_Factory create(Provider provider) {
        return new WakeLockLogger_Factory(provider);
    }

    @Override // javax.inject.Provider
    public WakeLockLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
