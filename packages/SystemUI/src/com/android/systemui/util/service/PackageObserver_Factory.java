package com.android.systemui.util.service;

import android.content.ComponentName;
import android.content.Context;
import dagger.internal.Provider;

public final class PackageObserver_Factory implements Provider {
    private final javax.inject.Provider componentProvider;
    private final javax.inject.Provider contextProvider;

    public PackageObserver_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.componentProvider = provider2;
    }

    public static PackageObserver_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new PackageObserver_Factory(provider, provider2);
    }

    public static PackageObserver newInstance(Context context, ComponentName componentName) {
        return new PackageObserver(context, componentName);
    }

    @Override // javax.inject.Provider
    public PackageObserver get() {
        return newInstance((Context) this.contextProvider.get(), (ComponentName) this.componentProvider.get());
    }
}
