package com.android.systemui.plugins;

import android.content.Context;
import android.content.pm.PackageManager;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginEnablerImpl_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider pmProvider;

    public PluginEnablerImpl_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.pmProvider = provider2;
    }

    public static PluginEnablerImpl_Factory create(Provider provider, Provider provider2) {
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
