package com.android.systemui.dagger;

import android.content.Context;
import android.content.res.Resources;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class FrameworkServicesModule_ProvideResourcesFactory implements Provider {
    public final Provider contextProvider;

    public FrameworkServicesModule_ProvideResourcesFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static Resources provideResources(Context context) {
        Resources resources = context.getResources();
        resources.getClass();
        return resources;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideResources((Context) this.contextProvider.get());
    }
}
