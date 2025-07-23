package com.android.systemui.pluginlock;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PluginLockDataImpl_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider mediatorProvider;

    public PluginLockDataImpl_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.mediatorProvider = provider2;
    }

    public static PluginLockDataImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new PluginLockDataImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static PluginLockDataImpl newInstance(Context context, PluginLockMediator pluginLockMediator) {
        return new PluginLockDataImpl(context, pluginLockMediator);
    }

    public static PluginLockDataImpl_Factory create(Provider provider, Provider provider2) {
        return new PluginLockDataImpl_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public PluginLockDataImpl get() {
        return newInstance((Context) this.contextProvider.get(), (PluginLockMediator) this.mediatorProvider.get());
    }
}
