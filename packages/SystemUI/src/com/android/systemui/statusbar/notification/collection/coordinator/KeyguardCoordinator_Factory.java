package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardCoordinator_Factory implements Provider {
    private final javax.inject.Provider ambientStateProvider;
    private final javax.inject.Provider bgDispatcherProvider;
    private final javax.inject.Provider dumpManagerProvider;
    private final javax.inject.Provider headsUpManagerProvider;
    private final javax.inject.Provider keyguardNotificationVisibilityProvider;
    private final javax.inject.Provider keyguardRepositoryProvider;
    private final javax.inject.Provider keyguardTransitionRepositoryProvider;
    private final javax.inject.Provider loggerProvider;
    private final javax.inject.Provider scopeProvider;
    private final javax.inject.Provider sectionHeaderVisibilityProvider;
    private final javax.inject.Provider secureSettingsProvider;
    private final javax.inject.Provider seenNotificationsInteractorProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;

    public KeyguardCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12, javax.inject.Provider provider13) {
        this.bgDispatcherProvider = provider;
        this.dumpManagerProvider = provider2;
        this.headsUpManagerProvider = provider3;
        this.keyguardNotificationVisibilityProvider = provider4;
        this.keyguardRepositoryProvider = provider5;
        this.keyguardTransitionRepositoryProvider = provider6;
        this.loggerProvider = provider7;
        this.scopeProvider = provider8;
        this.sectionHeaderVisibilityProvider = provider9;
        this.secureSettingsProvider = provider10;
        this.seenNotificationsInteractorProvider = provider11;
        this.statusBarStateControllerProvider = provider12;
        this.ambientStateProvider = provider13;
    }

    public static KeyguardCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12, javax.inject.Provider provider13) {
        return new KeyguardCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13);
    }

    public static KeyguardCoordinator newInstance(CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager, HeadsUpManager headsUpManager, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, KeyguardRepository keyguardRepository, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardCoordinatorLogger keyguardCoordinatorLogger, CoroutineScope coroutineScope, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, SecureSettings secureSettings, SeenNotificationsInteractor seenNotificationsInteractor, StatusBarStateController statusBarStateController, AmbientState ambientState) {
        return new KeyguardCoordinator(coroutineDispatcher, dumpManager, headsUpManager, keyguardNotificationVisibilityProvider, keyguardRepository, keyguardTransitionRepository, keyguardCoordinatorLogger, coroutineScope, sectionHeaderVisibilityProvider, secureSettings, seenNotificationsInteractor, statusBarStateController, ambientState);
    }

    @Override // javax.inject.Provider
    public KeyguardCoordinator get() {
        return newInstance((CoroutineDispatcher) this.bgDispatcherProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (KeyguardNotificationVisibilityProvider) this.keyguardNotificationVisibilityProvider.get(), (KeyguardRepository) this.keyguardRepositoryProvider.get(), (KeyguardTransitionRepository) this.keyguardTransitionRepositoryProvider.get(), (KeyguardCoordinatorLogger) this.loggerProvider.get(), (CoroutineScope) this.scopeProvider.get(), (SectionHeaderVisibilityProvider) this.sectionHeaderVisibilityProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (SeenNotificationsInteractor) this.seenNotificationsInteractorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (AmbientState) this.ambientStateProvider.get());
    }
}
