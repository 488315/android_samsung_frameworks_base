package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class OriginalUnseenKeyguardCoordinator_Factory implements Provider {
    private final Provider dumpManagerProvider;
    private final Provider headsUpManagerProvider;
    private final Provider keyguardRepositoryProvider;
    private final Provider keyguardTransitionInteractorProvider;
    private final Provider loggerProvider;
    private final Provider sceneInteractorProvider;
    private final Provider scopeProvider;
    private final Provider seenNotificationsInteractorProvider;
    private final Provider statusBarStateControllerProvider;

    public OriginalUnseenKeyguardCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.dumpManagerProvider = provider;
        this.headsUpManagerProvider = provider2;
        this.keyguardRepositoryProvider = provider3;
        this.keyguardTransitionInteractorProvider = provider4;
        this.loggerProvider = provider5;
        this.scopeProvider = provider6;
        this.seenNotificationsInteractorProvider = provider7;
        this.statusBarStateControllerProvider = provider8;
        this.sceneInteractorProvider = provider9;
    }

    public static OriginalUnseenKeyguardCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9) {
        return new OriginalUnseenKeyguardCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9));
    }

    public static OriginalUnseenKeyguardCoordinator newInstance(DumpManager dumpManager, HeadsUpManager headsUpManager, KeyguardRepository keyguardRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardCoordinatorLogger keyguardCoordinatorLogger, CoroutineScope coroutineScope, SeenNotificationsInteractor seenNotificationsInteractor, StatusBarStateController statusBarStateController, SceneInteractor sceneInteractor) {
        return new OriginalUnseenKeyguardCoordinator(dumpManager, headsUpManager, keyguardRepository, keyguardTransitionInteractor, keyguardCoordinatorLogger, coroutineScope, seenNotificationsInteractor, statusBarStateController, sceneInteractor);
    }

    public static OriginalUnseenKeyguardCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new OriginalUnseenKeyguardCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    @Override // javax.inject.Provider
    public OriginalUnseenKeyguardCoordinator get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (KeyguardRepository) this.keyguardRepositoryProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get(), (KeyguardCoordinatorLogger) this.loggerProvider.get(), (CoroutineScope) this.scopeProvider.get(), (SeenNotificationsInteractor) this.seenNotificationsInteractorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (SceneInteractor) this.sceneInteractorProvider.get());
    }
}
