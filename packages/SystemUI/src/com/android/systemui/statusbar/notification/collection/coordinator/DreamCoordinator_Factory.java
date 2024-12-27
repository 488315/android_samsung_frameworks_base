package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DreamCoordinator_Factory implements Provider {
    private final javax.inject.Provider keyguardRepositoryProvider;
    private final javax.inject.Provider scopeProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;

    public DreamCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.statusBarStateControllerProvider = provider;
        this.scopeProvider = provider2;
        this.keyguardRepositoryProvider = provider3;
    }

    public static DreamCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new DreamCoordinator_Factory(provider, provider2, provider3);
    }

    public static DreamCoordinator newInstance(SysuiStatusBarStateController sysuiStatusBarStateController, CoroutineScope coroutineScope, KeyguardRepository keyguardRepository) {
        return new DreamCoordinator(sysuiStatusBarStateController, coroutineScope, keyguardRepository);
    }

    @Override // javax.inject.Provider
    public DreamCoordinator get() {
        return newInstance((SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (CoroutineScope) this.scopeProvider.get(), (KeyguardRepository) this.keyguardRepositoryProvider.get());
    }
}
