package com.android.systemui.util.kotlin;

import android.os.Handler;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_SettingsBgDispatcherFactory implements Provider {
    private final Provider bgHandlerProvider;
    private final SysUICoroutinesModule module;

    public SysUICoroutinesModule_SettingsBgDispatcherFactory(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        this.module = sysUICoroutinesModule;
        this.bgHandlerProvider = provider;
    }

    public static SysUICoroutinesModule_SettingsBgDispatcherFactory create(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider) {
        return new SysUICoroutinesModule_SettingsBgDispatcherFactory(sysUICoroutinesModule, Providers.asDaggerProvider(provider));
    }

    public static CoroutineDispatcher settingsBgDispatcher(SysUICoroutinesModule sysUICoroutinesModule, Handler handler) {
        CoroutineDispatcher coroutineDispatcher = sysUICoroutinesModule.settingsBgDispatcher(handler);
        coroutineDispatcher.getClass();
        return coroutineDispatcher;
    }

    public static SysUICoroutinesModule_SettingsBgDispatcherFactory create(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        return new SysUICoroutinesModule_SettingsBgDispatcherFactory(sysUICoroutinesModule, provider);
    }

    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return settingsBgDispatcher(this.module, (Handler) this.bgHandlerProvider.get());
    }
}
