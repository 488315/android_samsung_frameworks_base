package com.android.systemui.complication.dagger;

import com.android.systemui.complication.ComplicationLayoutParams;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RegisteredComplicationsModule_ProvideClockTimeLayoutParamsFactory implements Provider {
    public final Provider featureFlagsProvider;

    public RegisteredComplicationsModule_ProvideClockTimeLayoutParamsFactory(Provider provider) {
        this.featureFlagsProvider = provider;
    }

    public static ComplicationLayoutParams provideClockTimeLayoutParams(FeatureFlags featureFlags) {
        return ((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.HIDE_SMARTSPACE_ON_DREAM_OVERLAY) ? new ComplicationLayoutParams(0, -2, 6, 8, 2) : new ComplicationLayoutParams(0, -2, 6, 1, 1, 0);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideClockTimeLayoutParams((FeatureFlags) this.featureFlagsProvider.get());
    }
}
