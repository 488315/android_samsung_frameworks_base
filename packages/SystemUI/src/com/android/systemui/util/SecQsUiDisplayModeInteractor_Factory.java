package com.android.systemui.util;

import android.hardware.devicestate.DeviceStateManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class SecQsUiDisplayModeInteractor_Factory implements Provider {
    private final Provider deviceStateManagerProvider;
    private final Provider mainExecutorProvider;
    private final Provider scopeProvider;

    public SecQsUiDisplayModeInteractor_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.deviceStateManagerProvider = provider;
        this.mainExecutorProvider = provider2;
        this.scopeProvider = provider3;
    }

    public static SecQsUiDisplayModeInteractor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SecQsUiDisplayModeInteractor_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static SecQsUiDisplayModeInteractor newInstance(DeviceStateManager deviceStateManager, DelayableExecutor delayableExecutor, CoroutineScope coroutineScope) {
        return new SecQsUiDisplayModeInteractor(deviceStateManager, delayableExecutor, coroutineScope);
    }

    public static SecQsUiDisplayModeInteractor_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new SecQsUiDisplayModeInteractor_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public SecQsUiDisplayModeInteractor get() {
        return newInstance((DeviceStateManager) this.deviceStateManagerProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (CoroutineScope) this.scopeProvider.get());
    }
}
