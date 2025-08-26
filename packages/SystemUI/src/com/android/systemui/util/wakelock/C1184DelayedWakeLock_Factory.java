package com.android.systemui.util.wakelock;

import android.content.Context;
import android.os.Handler;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* renamed from: com.android.systemui.util.wakelock.DelayedWakeLock_Factory, reason: case insensitive filesystem */
/* loaded from: classes3.dex */
public final class C1184DelayedWakeLock_Factory {
    private final Provider bgHandlerProvider;
    private final Provider contextProvider;
    private final Provider loggerProvider;

    public C1184DelayedWakeLock_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.bgHandlerProvider = provider;
        this.contextProvider = provider2;
        this.loggerProvider = provider3;
    }

    public static C1184DelayedWakeLock_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new C1184DelayedWakeLock_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static DelayedWakeLock newInstance(Handler handler, Context context, WakeLockLogger wakeLockLogger, String str) {
        return new DelayedWakeLock(handler, context, wakeLockLogger, str);
    }

    public DelayedWakeLock get(String str) {
        return newInstance((Handler) this.bgHandlerProvider.get(), (Context) this.contextProvider.get(), (WakeLockLogger) this.loggerProvider.get(), str);
    }

    public static C1184DelayedWakeLock_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new C1184DelayedWakeLock_Factory(provider, provider2, provider3);
    }
}
