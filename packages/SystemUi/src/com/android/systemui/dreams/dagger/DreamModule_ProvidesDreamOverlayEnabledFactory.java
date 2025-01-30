package com.android.systemui.dreams.dagger;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamModule_ProvidesDreamOverlayEnabledFactory implements Provider {
    public final Provider componentProvider;
    public final Provider packageManagerProvider;

    public DreamModule_ProvidesDreamOverlayEnabledFactory(Provider provider, Provider provider2) {
        this.packageManagerProvider = provider;
        this.componentProvider = provider2;
    }

    public static Boolean providesDreamOverlayEnabled(PackageManager packageManager, ComponentName componentName) {
        Boolean bool;
        try {
            bool = Boolean.valueOf(packageManager.getServiceInfo(componentName, 128).enabled);
        } catch (PackageManager.NameNotFoundException unused) {
            bool = Boolean.FALSE;
        }
        Preconditions.checkNotNullFromProvides(bool);
        return bool;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesDreamOverlayEnabled((PackageManager) this.packageManagerProvider.get(), (ComponentName) this.componentProvider.get());
    }
}
