package com.android.systemui.dagger;

import android.content.Context;
import android.content.res.Resources;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
