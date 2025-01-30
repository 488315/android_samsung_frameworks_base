package com.android.systemui.statusbar.pipeline.dagger;

import android.net.wifi.WifiManager;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.RealWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.DisabledWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.pipeline.dagger.StatusBarPipelineModule_Companion_ProvideRealWifiRepositoryFactory */
/* loaded from: classes2.dex */
public final class C3206x52cd85cf implements Provider {
    public final Provider disabledWifiRepositoryProvider;
    public final Provider wifiManagerProvider;
    public final Provider wifiRepositoryImplFactoryProvider;

    public C3206x52cd85cf(Provider provider, Provider provider2, Provider provider3) {
        this.wifiManagerProvider = provider;
        this.disabledWifiRepositoryProvider = provider2;
        this.wifiRepositoryImplFactoryProvider = provider3;
    }

    public static RealWifiRepository provideRealWifiRepository(WifiManager wifiManager, DisabledWifiRepository disabledWifiRepository, WifiRepositoryImpl.Factory factory) {
        StatusBarPipelineModule.Companion.getClass();
        return wifiManager == null ? disabledWifiRepository : new WifiRepositoryImpl(factory.broadcastDispatcher, factory.connectivityManager, factory.connectivityRepository, factory.logger, factory.wifiTableLogBuffer, factory.mainExecutor, factory.scope, wifiManager, factory.semWifiManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideRealWifiRepository((WifiManager) this.wifiManagerProvider.get(), (DisabledWifiRepository) this.disabledWifiRepositoryProvider.get(), (WifiRepositoryImpl.Factory) this.wifiRepositoryImplFactoryProvider.get());
    }
}
