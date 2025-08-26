package com.android.systemui.util.service;

import android.content.ComponentName;
import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class PackageObserver_Factory implements Provider {
    private final Provider componentProvider;
    private final Provider contextProvider;

    public PackageObserver_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.componentProvider = provider2;
    }

    public static PackageObserver_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new PackageObserver_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static PackageObserver newInstance(Context context, ComponentName componentName) {
        return new PackageObserver(context, componentName);
    }

    public static PackageObserver_Factory create(Provider provider, Provider provider2) {
        return new PackageObserver_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public PackageObserver get() {
        return newInstance((Context) this.contextProvider.get(), (ComponentName) this.componentProvider.get());
    }
}
