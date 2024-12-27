package com.android.systemui.plugins;

import android.content.Context;
import android.content.pm.PackageManager;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PluginEnablerImpl_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider pmProvider;

    public PluginEnablerImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.pmProvider = provider2;
    }

    public static PluginEnablerImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new PluginEnablerImpl_Factory(provider, provider2);
    }

    public static PluginEnablerImpl newInstance(Context context, PackageManager packageManager) {
        return new PluginEnablerImpl(context, packageManager);
    }

    @Override // javax.inject.Provider
    public PluginEnablerImpl get() {
        return newInstance((Context) this.contextProvider.get(), (PackageManager) this.pmProvider.get());
    }
}
