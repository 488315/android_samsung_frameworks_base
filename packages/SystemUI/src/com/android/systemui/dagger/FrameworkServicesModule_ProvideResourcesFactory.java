package com.android.systemui.dagger;

import android.content.Context;
import android.content.res.Resources;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
