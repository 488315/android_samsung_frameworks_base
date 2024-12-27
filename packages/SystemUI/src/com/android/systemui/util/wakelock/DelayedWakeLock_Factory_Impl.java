package com.android.systemui.util.wakelock;

import com.android.systemui.util.wakelock.DelayedWakeLock;
import dagger.internal.InstanceFactory;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DelayedWakeLock_Factory_Impl implements DelayedWakeLock.Factory {
    private final C0296DelayedWakeLock_Factory delegateFactory;

    public DelayedWakeLock_Factory_Impl(C0296DelayedWakeLock_Factory c0296DelayedWakeLock_Factory) {
        this.delegateFactory = c0296DelayedWakeLock_Factory;
    }

    public static Provider createFactoryProvider(C0296DelayedWakeLock_Factory c0296DelayedWakeLock_Factory) {
        return InstanceFactory.create(new DelayedWakeLock_Factory_Impl(c0296DelayedWakeLock_Factory));
    }

    @Override // com.android.systemui.util.wakelock.DelayedWakeLock.Factory
    public DelayedWakeLock create(String str) {
        return this.delegateFactory.get(str);
    }

    public static javax.inject.Provider create(C0296DelayedWakeLock_Factory c0296DelayedWakeLock_Factory) {
        return InstanceFactory.create(new DelayedWakeLock_Factory_Impl(c0296DelayedWakeLock_Factory));
    }
}
