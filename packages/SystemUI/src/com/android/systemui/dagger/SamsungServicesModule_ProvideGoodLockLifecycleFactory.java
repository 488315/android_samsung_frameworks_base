package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.lockstar.GoodLockLifecycle;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class SamsungServicesModule_ProvideGoodLockLifecycleFactory implements Provider {
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider mainHandlerProvider;

    public SamsungServicesModule_ProvideGoodLockLifecycleFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.dumpManagerProvider = provider4;
    }

    public static GoodLockLifecycle provideGoodLockLifecycle(Context context, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager) {
        return new GoodLockLifecycle(context, handler, keyguardUpdateMonitor, dumpManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new GoodLockLifecycle((Context) this.contextProvider.get(), (Handler) this.mainHandlerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
