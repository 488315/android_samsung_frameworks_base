package com.android.systemui.statusbar.pipeline.dagger;

import android.net.wifi.WifiManager;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.RealWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.DisabledWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl;
import com.samsung.android.wifi.SemWifiManager;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarPipelineModule_Companion_ProvideRealWifiRepositoryFactory implements Provider {
    public final javax.inject.Provider disabledWifiRepositoryProvider;
    public final javax.inject.Provider wifiManagerProvider;
    public final javax.inject.Provider wifiRepositoryImplFactoryProvider;

    public StatusBarPipelineModule_Companion_ProvideRealWifiRepositoryFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.wifiManagerProvider = provider;
        this.disabledWifiRepositoryProvider = provider2;
        this.wifiRepositoryImplFactoryProvider = provider3;
    }

    public static RealWifiRepository provideRealWifiRepository(WifiManager wifiManager, DisabledWifiRepository disabledWifiRepository, WifiRepositoryImpl.Factory factory) {
        StatusBarPipelineModule.Companion.getClass();
        if (wifiManager == null) {
            return disabledWifiRepository;
        }
        Executor executor = factory.mainExecutor;
        SemWifiManager semWifiManager = factory.semWifiManager;
        return new WifiRepositoryImpl(factory.featureFlags, factory.scope, executor, factory.bgDispatcher, factory.wifiPickerTrackerFactory, wifiManager, factory.inputLogger, factory.tableLogger, semWifiManager, factory.broadcastDispatcher);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideRealWifiRepository((WifiManager) this.wifiManagerProvider.get(), (DisabledWifiRepository) this.disabledWifiRepositoryProvider.get(), (WifiRepositoryImpl.Factory) this.wifiRepositoryImplFactoryProvider.get());
    }
}
