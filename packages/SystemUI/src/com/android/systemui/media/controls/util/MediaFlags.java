package com.android.systemui.media.controls.util;

import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;

public final class MediaFlags {
    public final FeatureFlagsClassic featureFlags;

    public MediaFlags(FeatureFlagsClassic featureFlagsClassic) {
        this.featureFlags = featureFlagsClassic;
    }

    public final void isPersistentSsCardEnabled() {
        Flags.INSTANCE.getClass();
        this.featureFlags.getClass();
    }
}
