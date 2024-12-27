package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class GroupWhenCoordinator_Factory implements Provider {
    private final javax.inject.Provider delayableExecutorProvider;
    private final javax.inject.Provider systemClockProvider;

    public GroupWhenCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.delayableExecutorProvider = provider;
        this.systemClockProvider = provider2;
    }

    public static GroupWhenCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new GroupWhenCoordinator_Factory(provider, provider2);
    }

    public static GroupWhenCoordinator newInstance(DelayableExecutor delayableExecutor, SystemClock systemClock) {
        return new GroupWhenCoordinator(delayableExecutor, systemClock);
    }

    @Override // javax.inject.Provider
    public GroupWhenCoordinator get() {
        return newInstance((DelayableExecutor) this.delayableExecutorProvider.get(), (SystemClock) this.systemClockProvider.get());
    }
}
