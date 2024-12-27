package com.android.systemui.util.wakelock;

import android.content.Context;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* renamed from: com.android.systemui.util.wakelock.DelayedWakeLock_Factory, reason: case insensitive filesystem */
/* loaded from: classes3.dex */
public final class C0296DelayedWakeLock_Factory {
    private final Provider bgHandlerProvider;
    private final Provider contextProvider;
    private final Provider loggerProvider;
    private final Provider mainHandlerProvider;

    public C0296DelayedWakeLock_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.bgHandlerProvider = provider;
        this.mainHandlerProvider = provider2;
        this.contextProvider = provider3;
        this.loggerProvider = provider4;
    }

    public static C0296DelayedWakeLock_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new C0296DelayedWakeLock_Factory(provider, provider2, provider3, provider4);
    }

    public static DelayedWakeLock newInstance(Lazy lazy, Lazy lazy2, Context context, WakeLockLogger wakeLockLogger, String str) {
        return new DelayedWakeLock(lazy, lazy2, context, wakeLockLogger, str);
    }

    public DelayedWakeLock get(String str) {
        return newInstance(DoubleCheck.lazy(this.bgHandlerProvider), DoubleCheck.lazy(this.mainHandlerProvider), (Context) this.contextProvider.get(), (WakeLockLogger) this.loggerProvider.get(), str);
    }
}
