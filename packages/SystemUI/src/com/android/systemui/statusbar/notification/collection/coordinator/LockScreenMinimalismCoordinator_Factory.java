package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class LockScreenMinimalismCoordinator_Factory implements Provider {
    private final Provider dumpManagerProvider;
    private final Provider headsUpInteractorProvider;
    private final Provider loggerProvider;
    private final Provider scopeProvider;
    private final Provider seenNotificationsInteractorProvider;
    private final Provider shadeInteractorProvider;
    private final Provider statusBarStateControllerProvider;

    public LockScreenMinimalismCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.dumpManagerProvider = provider;
        this.headsUpInteractorProvider = provider2;
        this.loggerProvider = provider3;
        this.scopeProvider = provider4;
        this.seenNotificationsInteractorProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.shadeInteractorProvider = provider7;
    }

    public static LockScreenMinimalismCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7) {
        return new LockScreenMinimalismCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7));
    }

    public static LockScreenMinimalismCoordinator newInstance(DumpManager dumpManager, HeadsUpNotificationInteractor headsUpNotificationInteractor, LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger, CoroutineScope coroutineScope, SeenNotificationsInteractor seenNotificationsInteractor, StatusBarStateController statusBarStateController, ShadeInteractor shadeInteractor) {
        return new LockScreenMinimalismCoordinator(dumpManager, headsUpNotificationInteractor, lockScreenMinimalismCoordinatorLogger, coroutineScope, seenNotificationsInteractor, statusBarStateController, shadeInteractor);
    }

    public static LockScreenMinimalismCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new LockScreenMinimalismCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    @Override // javax.inject.Provider
    public LockScreenMinimalismCoordinator get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (HeadsUpNotificationInteractor) this.headsUpInteractorProvider.get(), (LockScreenMinimalismCoordinatorLogger) this.loggerProvider.get(), (CoroutineScope) this.scopeProvider.get(), (SeenNotificationsInteractor) this.seenNotificationsInteractorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ShadeInteractor) this.shadeInteractorProvider.get());
    }
}
