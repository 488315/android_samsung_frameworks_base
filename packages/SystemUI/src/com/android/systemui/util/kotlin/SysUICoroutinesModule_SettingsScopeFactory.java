package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_SettingsScopeFactory implements Provider {
    private final Provider bgDispatcherProvider;
    private final SysUICoroutinesModule module;

    public SysUICoroutinesModule_SettingsScopeFactory(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        this.module = sysUICoroutinesModule;
        this.bgDispatcherProvider = provider;
    }

    public static SysUICoroutinesModule_SettingsScopeFactory create(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider) {
        return new SysUICoroutinesModule_SettingsScopeFactory(sysUICoroutinesModule, Providers.asDaggerProvider(provider));
    }

    public static CoroutineScope settingsScope(SysUICoroutinesModule sysUICoroutinesModule, CoroutineDispatcher coroutineDispatcher) {
        CoroutineScope coroutineScope = sysUICoroutinesModule.settingsScope(coroutineDispatcher);
        coroutineScope.getClass();
        return coroutineScope;
    }

    public static SysUICoroutinesModule_SettingsScopeFactory create(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        return new SysUICoroutinesModule_SettingsScopeFactory(sysUICoroutinesModule, provider);
    }

    @Override // javax.inject.Provider
    public CoroutineScope get() {
        return settingsScope(this.module, (CoroutineDispatcher) this.bgDispatcherProvider.get());
    }
}
