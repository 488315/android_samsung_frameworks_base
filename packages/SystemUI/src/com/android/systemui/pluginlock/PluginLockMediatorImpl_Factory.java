package com.android.systemui.pluginlock;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.SPluginManager;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PluginLockMediatorImpl_Factory implements Provider {
    private final Provider clockProvider;
    private final Provider contextProvider;
    private final Provider dozeParametersProvider;
    private final Provider monitorProvider;
    private final Provider sPluginManagerProvider;
    private final Provider selectedUserInteractorProvider;
    private final Provider settingsHelperProvider;
    private final Provider shadeExpansionStateManagerProvider;
    private final Provider shortcutManagerProvider;
    private final Provider utilsProvider;
    private final Provider wakefulnessLifecycleProvider;

    public PluginLockMediatorImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
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
        this.wakefulnessLifecycleProvider = provider11;
    }

    public static PluginLockMediatorImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11) {
        return new PluginLockMediatorImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9), Providers.asDaggerProvider(provider10), Providers.asDaggerProvider(provider11));
    }

    public static PluginLockMediatorImpl newInstance(Context context, SelectedUserInteractor selectedUserInteractor, SPluginManager sPluginManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeParameters dozeParameters, ExternalClockProvider externalClockProvider, Lazy lazy, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, ShadeExpansionStateManager shadeExpansionStateManager, WakefulnessLifecycle wakefulnessLifecycle) {
        return new PluginLockMediatorImpl(context, selectedUserInteractor, sPluginManager, keyguardUpdateMonitor, dozeParameters, externalClockProvider, lazy, settingsHelper, pluginLockUtils, shadeExpansionStateManager, wakefulnessLifecycle);
    }

    public static PluginLockMediatorImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        return new PluginLockMediatorImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    @Override // javax.inject.Provider
    public PluginLockMediatorImpl get() {
        return newInstance((Context) this.contextProvider.get(), (SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (SPluginManager) this.sPluginManagerProvider.get(), (KeyguardUpdateMonitor) this.monitorProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (ExternalClockProvider) this.clockProvider.get(), DoubleCheck.lazy(this.shortcutManagerProvider), (SettingsHelper) this.settingsHelperProvider.get(), (PluginLockUtils) this.utilsProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get());
    }
}
