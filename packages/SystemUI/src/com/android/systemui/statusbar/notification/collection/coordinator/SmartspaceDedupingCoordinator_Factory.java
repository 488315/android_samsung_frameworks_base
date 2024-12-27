package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SmartspaceDedupingCoordinator_Factory implements Provider {
    private final javax.inject.Provider clockProvider;
    private final javax.inject.Provider executorProvider;
    private final javax.inject.Provider notifPipelineProvider;
    private final javax.inject.Provider smartspaceControllerProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;

    public SmartspaceDedupingCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        this.statusBarStateControllerProvider = provider;
        this.smartspaceControllerProvider = provider2;
        this.notifPipelineProvider = provider3;
        this.executorProvider = provider4;
        this.clockProvider = provider5;
    }

    public static SmartspaceDedupingCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new SmartspaceDedupingCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static SmartspaceDedupingCoordinator newInstance(SysuiStatusBarStateController sysuiStatusBarStateController, LockscreenSmartspaceController lockscreenSmartspaceController, NotifPipeline notifPipeline, DelayableExecutor delayableExecutor, SystemClock systemClock) {
        return new SmartspaceDedupingCoordinator(sysuiStatusBarStateController, lockscreenSmartspaceController, notifPipeline, delayableExecutor, systemClock);
    }

    @Override // javax.inject.Provider
    public SmartspaceDedupingCoordinator get() {
        return newInstance((SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (LockscreenSmartspaceController) this.smartspaceControllerProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (DelayableExecutor) this.executorProvider.get(), (SystemClock) this.clockProvider.get());
    }
}
