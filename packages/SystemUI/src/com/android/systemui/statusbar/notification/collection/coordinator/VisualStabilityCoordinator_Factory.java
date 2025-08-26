package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class VisualStabilityCoordinator_Factory implements Provider {
    private final Provider communalSceneInteractorProvider;
    private final Provider delayableExecutorProvider;
    private final Provider dumpManagerProvider;
    private final Provider headsUpRepositoryProvider;
    private final Provider javaAdapterProvider;
    private final Provider keyguardStateControllerProvider;
    private final Provider keyguardTransitionInteractorProvider;
    private final Provider loggerProvider;
    private final Provider mainExecutorProvider;
    private final Provider seenNotificationsInteractorProvider;
    private final Provider shadeAnimationInteractorProvider;
    private final Provider shadeInteractorProvider;
    private final Provider statusBarStateControllerProvider;
    private final Provider visibilityLocationProvider;
    private final Provider visualStabilityProvider;
    private final Provider wakefulnessLifecycleProvider;

    public VisualStabilityCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        this.delayableExecutorProvider = provider;
        this.mainExecutorProvider = provider2;
        this.dumpManagerProvider = provider3;
        this.headsUpRepositoryProvider = provider4;
        this.shadeAnimationInteractorProvider = provider5;
        this.javaAdapterProvider = provider6;
        this.seenNotificationsInteractorProvider = provider7;
        this.statusBarStateControllerProvider = provider8;
        this.visibilityLocationProvider = provider9;
        this.visualStabilityProvider = provider10;
        this.wakefulnessLifecycleProvider = provider11;
        this.communalSceneInteractorProvider = provider12;
        this.shadeInteractorProvider = provider13;
        this.keyguardTransitionInteractorProvider = provider14;
        this.keyguardStateControllerProvider = provider15;
        this.loggerProvider = provider16;
    }

    public static VisualStabilityCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12, javax.inject.Provider provider13, javax.inject.Provider provider14, javax.inject.Provider provider15, javax.inject.Provider provider16) {
        return new VisualStabilityCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9), Providers.asDaggerProvider(provider10), Providers.asDaggerProvider(provider11), Providers.asDaggerProvider(provider12), Providers.asDaggerProvider(provider13), Providers.asDaggerProvider(provider14), Providers.asDaggerProvider(provider15), Providers.asDaggerProvider(provider16));
    }

    public static VisualStabilityCoordinator newInstance(DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, DumpManager dumpManager, HeadsUpRepository headsUpRepository, ShadeAnimationInteractor shadeAnimationInteractor, JavaAdapter javaAdapter, SeenNotificationsInteractor seenNotificationsInteractor, StatusBarStateController statusBarStateController, VisibilityLocationProvider visibilityLocationProvider, VisualStabilityProvider visualStabilityProvider, WakefulnessLifecycle wakefulnessLifecycle, CommunalSceneInteractor communalSceneInteractor, ShadeInteractor shadeInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardStateController keyguardStateController, VisualStabilityCoordinatorLogger visualStabilityCoordinatorLogger) {
        return new VisualStabilityCoordinator(delayableExecutor, delayableExecutor2, dumpManager, headsUpRepository, shadeAnimationInteractor, javaAdapter, seenNotificationsInteractor, statusBarStateController, visibilityLocationProvider, visualStabilityProvider, wakefulnessLifecycle, communalSceneInteractor, shadeInteractor, keyguardTransitionInteractor, keyguardStateController, visualStabilityCoordinatorLogger);
    }

    public static VisualStabilityCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        return new VisualStabilityCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16);
    }

    @Override // javax.inject.Provider
    public VisualStabilityCoordinator get() {
        return newInstance((DelayableExecutor) this.delayableExecutorProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (HeadsUpRepository) this.headsUpRepositoryProvider.get(), (ShadeAnimationInteractor) this.shadeAnimationInteractorProvider.get(), (JavaAdapter) this.javaAdapterProvider.get(), (SeenNotificationsInteractor) this.seenNotificationsInteractorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (VisibilityLocationProvider) this.visibilityLocationProvider.get(), (VisualStabilityProvider) this.visualStabilityProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (CommunalSceneInteractor) this.communalSceneInteractorProvider.get(), (ShadeInteractor) this.shadeInteractorProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (VisualStabilityCoordinatorLogger) this.loggerProvider.get());
    }
}
