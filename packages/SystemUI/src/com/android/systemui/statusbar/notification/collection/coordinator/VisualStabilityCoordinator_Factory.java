package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import dagger.internal.Provider;

public final class VisualStabilityCoordinator_Factory implements Provider {
    private final javax.inject.Provider communalInteractorProvider;
    private final javax.inject.Provider delayableExecutorProvider;
    private final javax.inject.Provider dumpManagerProvider;
    private final javax.inject.Provider headsUpManagerProvider;
    private final javax.inject.Provider javaAdapterProvider;
    private final javax.inject.Provider seenNotificationsInteractorProvider;
    private final javax.inject.Provider shadeAnimationInteractorProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;
    private final javax.inject.Provider visibilityLocationProvider;
    private final javax.inject.Provider visualStabilityProvider;
    private final javax.inject.Provider wakefulnessLifecycleProvider;

    public VisualStabilityCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11) {
        this.delayableExecutorProvider = provider;
        this.dumpManagerProvider = provider2;
        this.headsUpManagerProvider = provider3;
        this.shadeAnimationInteractorProvider = provider4;
        this.javaAdapterProvider = provider5;
        this.seenNotificationsInteractorProvider = provider6;
        this.statusBarStateControllerProvider = provider7;
        this.visibilityLocationProvider = provider8;
        this.visualStabilityProvider = provider9;
        this.wakefulnessLifecycleProvider = provider10;
        this.communalInteractorProvider = provider11;
    }

    public static VisualStabilityCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11) {
        return new VisualStabilityCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static VisualStabilityCoordinator newInstance(DelayableExecutor delayableExecutor, DumpManager dumpManager, HeadsUpManager headsUpManager, ShadeAnimationInteractor shadeAnimationInteractor, JavaAdapter javaAdapter, SeenNotificationsInteractor seenNotificationsInteractor, StatusBarStateController statusBarStateController, VisibilityLocationProvider visibilityLocationProvider, VisualStabilityProvider visualStabilityProvider, WakefulnessLifecycle wakefulnessLifecycle, CommunalInteractor communalInteractor) {
        return new VisualStabilityCoordinator(delayableExecutor, dumpManager, headsUpManager, shadeAnimationInteractor, javaAdapter, seenNotificationsInteractor, statusBarStateController, visibilityLocationProvider, visualStabilityProvider, wakefulnessLifecycle, communalInteractor);
    }

    @Override // javax.inject.Provider
    public VisualStabilityCoordinator get() {
        return newInstance((DelayableExecutor) this.delayableExecutorProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (ShadeAnimationInteractor) this.shadeAnimationInteractorProvider.get(), (JavaAdapter) this.javaAdapterProvider.get(), (SeenNotificationsInteractor) this.seenNotificationsInteractorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (VisibilityLocationProvider) this.visibilityLocationProvider.get(), (VisualStabilityProvider) this.visualStabilityProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (CommunalInteractor) this.communalInteractorProvider.get());
    }
}
