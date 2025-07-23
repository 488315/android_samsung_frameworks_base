package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GroupWhenCoordinator_Factory implements Provider {
    private final Provider delayableExecutorProvider;
    private final Provider systemClockProvider;

    public GroupWhenCoordinator_Factory(Provider provider, Provider provider2) {
        this.delayableExecutorProvider = provider;
        this.systemClockProvider = provider2;
    }

    public static GroupWhenCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new GroupWhenCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static GroupWhenCoordinator newInstance(DelayableExecutor delayableExecutor, SystemClock systemClock) {
        return new GroupWhenCoordinator(delayableExecutor, systemClock);
    }

    public static GroupWhenCoordinator_Factory create(Provider provider, Provider provider2) {
        return new GroupWhenCoordinator_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public GroupWhenCoordinator get() {
        return newInstance((DelayableExecutor) this.delayableExecutorProvider.get(), (SystemClock) this.systemClockProvider.get());
    }
}
