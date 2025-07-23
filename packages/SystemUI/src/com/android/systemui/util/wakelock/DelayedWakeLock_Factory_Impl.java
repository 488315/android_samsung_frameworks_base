package com.android.systemui.util.wakelock;

import com.android.systemui.util.wakelock.DelayedWakeLock;
import dagger.internal.InstanceFactory;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DelayedWakeLock_Factory_Impl implements DelayedWakeLock.Factory {
    private final C0435DelayedWakeLock_Factory delegateFactory;

    public DelayedWakeLock_Factory_Impl(C0435DelayedWakeLock_Factory c0435DelayedWakeLock_Factory) {
        this.delegateFactory = c0435DelayedWakeLock_Factory;
    }

    public static Provider createFactoryProvider(C0435DelayedWakeLock_Factory c0435DelayedWakeLock_Factory) {
        return InstanceFactory.create(new DelayedWakeLock_Factory_Impl(c0435DelayedWakeLock_Factory));
    }

    @Override // com.android.systemui.util.wakelock.DelayedWakeLock.Factory
    public DelayedWakeLock create(String str) {
        return this.delegateFactory.get(str);
    }

    public static javax.inject.Provider create(C0435DelayedWakeLock_Factory c0435DelayedWakeLock_Factory) {
        return InstanceFactory.create(new DelayedWakeLock_Factory_Impl(c0435DelayedWakeLock_Factory));
    }
}
