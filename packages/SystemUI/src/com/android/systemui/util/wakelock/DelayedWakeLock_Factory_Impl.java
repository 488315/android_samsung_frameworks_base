package com.android.systemui.util.wakelock;

import com.android.systemui.util.wakelock.DelayedWakeLock;
import dagger.internal.InstanceFactory;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class DelayedWakeLock_Factory_Impl implements DelayedWakeLock.Factory {
    private final C1184DelayedWakeLock_Factory delegateFactory;

    public DelayedWakeLock_Factory_Impl(C1184DelayedWakeLock_Factory c1184DelayedWakeLock_Factory) {
        this.delegateFactory = c1184DelayedWakeLock_Factory;
    }

    public static Provider createFactoryProvider(C1184DelayedWakeLock_Factory c1184DelayedWakeLock_Factory) {
        return InstanceFactory.create(new DelayedWakeLock_Factory_Impl(c1184DelayedWakeLock_Factory));
    }

    @Override // com.android.systemui.util.wakelock.DelayedWakeLock.Factory
    public DelayedWakeLock create(String str) {
        return this.delegateFactory.get(str);
    }

    public static javax.inject.Provider create(C1184DelayedWakeLock_Factory c1184DelayedWakeLock_Factory) {
        return InstanceFactory.create(new DelayedWakeLock_Factory_Impl(c1184DelayedWakeLock_Factory));
    }
}
