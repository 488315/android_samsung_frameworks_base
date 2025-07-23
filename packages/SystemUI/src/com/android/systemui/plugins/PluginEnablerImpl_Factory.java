package com.android.systemui.plugins;

import android.content.Context;
import android.content.pm.PackageManager;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PluginEnablerImpl_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider pmProvider;

    public PluginEnablerImpl_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.pmProvider = provider2;
    }

    public static PluginEnablerImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new PluginEnablerImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static PluginEnablerImpl newInstance(Context context, PackageManager packageManager) {
        return new PluginEnablerImpl(context, packageManager);
    }

    public static PluginEnablerImpl_Factory create(Provider provider, Provider provider2) {
        return new PluginEnablerImpl_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public PluginEnablerImpl get() {
        return newInstance((Context) this.contextProvider.get(), (PackageManager) this.pmProvider.get());
    }
}
