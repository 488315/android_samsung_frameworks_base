package com.android.systemui.bouncer.data.repository;

import com.android.systemui.flags.FeatureFlagsClassic;

public final class BouncerRepository {
    public final FeatureFlagsClassic flags;

    public BouncerRepository(FeatureFlagsClassic featureFlagsClassic) {
        this.flags = featureFlagsClassic;
    }
}
