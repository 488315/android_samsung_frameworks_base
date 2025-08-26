package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class SmartspaceDedupingCoordinator_Factory implements Provider {
    private final Provider clockProvider;
    private final Provider executorProvider;
    private final Provider notifPipelineProvider;
    private final Provider smartspaceControllerProvider;
    private final Provider statusBarStateControllerProvider;

    public SmartspaceDedupingCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.statusBarStateControllerProvider = provider;
        this.smartspaceControllerProvider = provider2;
        this.notifPipelineProvider = provider3;
        this.executorProvider = provider4;
        this.clockProvider = provider5;
    }

    public static SmartspaceDedupingCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new SmartspaceDedupingCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static SmartspaceDedupingCoordinator newInstance(SysuiStatusBarStateController sysuiStatusBarStateController, LockscreenSmartspaceController lockscreenSmartspaceController, NotifPipeline notifPipeline, DelayableExecutor delayableExecutor, SystemClock systemClock) {
        return new SmartspaceDedupingCoordinator(sysuiStatusBarStateController, lockscreenSmartspaceController, notifPipeline, delayableExecutor, systemClock);
    }

    public static SmartspaceDedupingCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new SmartspaceDedupingCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public SmartspaceDedupingCoordinator get() {
        return newInstance((SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (LockscreenSmartspaceController) this.smartspaceControllerProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (DelayableExecutor) this.executorProvider.get(), (SystemClock) this.clockProvider.get());
    }
}
