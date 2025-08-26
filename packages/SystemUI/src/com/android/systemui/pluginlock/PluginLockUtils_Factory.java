package com.android.systemui.pluginlock;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.pluginlock.utils.DumpUtils;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes2.dex */
public final class PluginLockUtils_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider dumpUtilsProvider;
    private final Provider selectedUserInteractorProvider;
    private final Provider updateMonitorProvider;

    public PluginLockUtils_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.selectedUserInteractorProvider = provider2;
        this.dumpUtilsProvider = provider3;
        this.updateMonitorProvider = provider4;
    }

    public static PluginLockUtils_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new PluginLockUtils_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static PluginLockUtils newInstance(Context context, SelectedUserInteractor selectedUserInteractor, DumpUtils dumpUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new PluginLockUtils(context, selectedUserInteractor, dumpUtils, keyguardUpdateMonitor);
    }

    public static PluginLockUtils_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new PluginLockUtils_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public PluginLockUtils get() {
        return newInstance((Context) this.contextProvider.get(), (SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (DumpUtils) this.dumpUtilsProvider.get(), (KeyguardUpdateMonitor) this.updateMonitorProvider.get());
    }
}
