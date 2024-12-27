package com.android.systemui.pluginlock;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.SPluginManager;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PluginLockMediatorImpl_Factory implements Provider {
    private final javax.inject.Provider clockProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider dozeParametersProvider;
    private final javax.inject.Provider monitorProvider;
    private final javax.inject.Provider sPluginManagerProvider;
    private final javax.inject.Provider selectedUserInteractorProvider;
    private final javax.inject.Provider settingsHelperProvider;
    private final javax.inject.Provider shadeExpansionStateManagerProvider;
    private final javax.inject.Provider shortcutManagerProvider;
    private final javax.inject.Provider utilsProvider;

    public PluginLockMediatorImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10) {
        this.contextProvider = provider;
        this.selectedUserInteractorProvider = provider2;
        this.sPluginManagerProvider = provider3;
        this.monitorProvider = provider4;
        this.dozeParametersProvider = provider5;
        this.clockProvider = provider6;
        this.shortcutManagerProvider = provider7;
        this.settingsHelperProvider = provider8;
        this.utilsProvider = provider9;
        this.shadeExpansionStateManagerProvider = provider10;
    }

    public static PluginLockMediatorImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10) {
        return new PluginLockMediatorImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static PluginLockMediatorImpl newInstance(Context context, SelectedUserInteractor selectedUserInteractor, SPluginManager sPluginManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeParameters dozeParameters, ExternalClockProvider externalClockProvider, Lazy lazy, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, ShadeExpansionStateManager shadeExpansionStateManager) {
        return new PluginLockMediatorImpl(context, selectedUserInteractor, sPluginManager, keyguardUpdateMonitor, dozeParameters, externalClockProvider, lazy, settingsHelper, pluginLockUtils, shadeExpansionStateManager);
    }

    @Override // javax.inject.Provider
    public PluginLockMediatorImpl get() {
        return newInstance((Context) this.contextProvider.get(), (SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (SPluginManager) this.sPluginManagerProvider.get(), (KeyguardUpdateMonitor) this.monitorProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (ExternalClockProvider) this.clockProvider.get(), DoubleCheck.lazy(this.shortcutManagerProvider), (SettingsHelper) this.settingsHelperProvider.get(), (PluginLockUtils) this.utilsProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get());
    }
}
