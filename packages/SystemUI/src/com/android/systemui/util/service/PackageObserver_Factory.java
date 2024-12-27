package com.android.systemui.util.service;

import android.content.ComponentName;
import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
