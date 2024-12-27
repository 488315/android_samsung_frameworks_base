package com.android.systemui.util;

import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
