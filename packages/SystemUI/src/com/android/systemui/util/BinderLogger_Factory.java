package com.android.systemui.util;

import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Provider;

public final class BinderLogger_Factory implements Provider {
    private final javax.inject.Provider featureFlagsProvider;

    public BinderLogger_Factory(javax.inject.Provider provider) {
        this.featureFlagsProvider = provider;
    }

    public static BinderLogger_Factory create(javax.inject.Provider provider) {
        return new BinderLogger_Factory(provider);
    }

    public static BinderLogger newInstance(FeatureFlags featureFlags) {
        return new BinderLogger(featureFlags);
    }

    @Override // javax.inject.Provider
    public BinderLogger get() {
        return newInstance((FeatureFlags) this.featureFlagsProvider.get());
    }
}
