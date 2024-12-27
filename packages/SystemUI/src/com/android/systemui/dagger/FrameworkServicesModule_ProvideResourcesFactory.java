package com.android.systemui.dagger;

import android.content.Context;
import android.content.res.Resources;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class FrameworkServicesModule_ProvideResourcesFactory implements Provider {
    public final javax.inject.Provider contextProvider;

    public FrameworkServicesModule_ProvideResourcesFactory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static Resources provideResources(Context context) {
        Resources resources = context.getResources();
        Preconditions.checkNotNullFromProvides(resources);
        return resources;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideResources((Context) this.contextProvider.get());
    }
}
