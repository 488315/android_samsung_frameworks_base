package com.android.systemui.statusbar.dagger;

import com.android.systemui.flags.FeatureFlags;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideAnimationFeatureFlagsFactory */
/* loaded from: classes2.dex */
public final class C2630xd0a13107 implements Provider {
    public final Provider featureFlagsProvider;

    public C2630xd0a13107(Provider provider) {
        this.featureFlagsProvider = provider;
    }

    public static CentralSurfacesDependenciesModule$2 provideAnimationFeatureFlags(FeatureFlags featureFlags) {
        return new CentralSurfacesDependenciesModule$2(featureFlags);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new CentralSurfacesDependenciesModule$2((FeatureFlags) this.featureFlagsProvider.get());
    }
}
