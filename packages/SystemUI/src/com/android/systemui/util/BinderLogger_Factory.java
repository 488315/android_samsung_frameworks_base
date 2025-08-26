package com.android.systemui.util;

import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class BinderLogger_Factory implements Provider {
    private final Provider featureFlagsProvider;

    public BinderLogger_Factory(Provider provider) {
        this.featureFlagsProvider = provider;
    }

    public static BinderLogger_Factory create(javax.inject.Provider provider) {
        return new BinderLogger_Factory(Providers.asDaggerProvider(provider));
    }

    public static BinderLogger newInstance(FeatureFlags featureFlags) {
        return new BinderLogger(featureFlags);
    }

    public static BinderLogger_Factory create(Provider provider) {
        return new BinderLogger_Factory(provider);
    }

    @Override // javax.inject.Provider
    public BinderLogger get() {
        return newInstance((FeatureFlags) this.featureFlagsProvider.get());
    }
}
