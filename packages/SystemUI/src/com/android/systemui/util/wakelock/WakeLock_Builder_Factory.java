package com.android.systemui.util.wakelock;

import android.content.Context;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.Provider;

public final class WakeLock_Builder_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider loggerProvider;

    public WakeLock_Builder_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.loggerProvider = provider2;
    }

    public static WakeLock_Builder_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new WakeLock_Builder_Factory(provider, provider2);
    }

    public static WakeLock.Builder newInstance(Context context, WakeLockLogger wakeLockLogger) {
        return new WakeLock.Builder(context, wakeLockLogger);
    }

    @Override // javax.inject.Provider
    public WakeLock.Builder get() {
        return newInstance((Context) this.contextProvider.get(), (WakeLockLogger) this.loggerProvider.get());
    }
}
