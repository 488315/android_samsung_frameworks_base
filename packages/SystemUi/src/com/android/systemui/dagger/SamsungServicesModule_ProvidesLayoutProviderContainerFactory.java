package com.android.systemui.dagger;

import android.content.Context;
import com.android.systemui.navigationbar.layout.LayoutProviderContainerImpl;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungServicesModule_ProvidesLayoutProviderContainerFactory implements Provider {
    public final Provider contextProvider;

    public SamsungServicesModule_ProvidesLayoutProviderContainerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static LayoutProviderContainerImpl providesLayoutProviderContainer(Context context) {
        return new LayoutProviderContainerImpl(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new LayoutProviderContainerImpl((Context) this.contextProvider.get());
    }
}
