package com.android.systemui.pluginlock;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.pluginlock.utils.DumpUtils;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import dagger.internal.Provider;

public final class PluginLockUtils_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider dumpUtilsProvider;
    private final javax.inject.Provider selectedUserInteractorProvider;
    private final javax.inject.Provider updateMonitorProvider;

    public PluginLockUtils_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.contextProvider = provider;
        this.selectedUserInteractorProvider = provider2;
        this.dumpUtilsProvider = provider3;
        this.updateMonitorProvider = provider4;
    }

    public static PluginLockUtils_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new PluginLockUtils_Factory(provider, provider2, provider3, provider4);
    }

    public static PluginLockUtils newInstance(Context context, SelectedUserInteractor selectedUserInteractor, DumpUtils dumpUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new PluginLockUtils(context, selectedUserInteractor, dumpUtils, keyguardUpdateMonitor);
    }

    @Override // javax.inject.Provider
    public PluginLockUtils get() {
        return newInstance((Context) this.contextProvider.get(), (SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (DumpUtils) this.dumpUtilsProvider.get(), (KeyguardUpdateMonitor) this.updateMonitorProvider.get());
    }
}
