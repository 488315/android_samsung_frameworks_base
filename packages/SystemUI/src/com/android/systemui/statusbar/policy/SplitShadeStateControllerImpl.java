package com.android.systemui.statusbar.policy;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;

public final class SplitShadeStateControllerImpl implements SplitShadeStateController {
    public final FeatureFlags featureFlags;

    public SplitShadeStateControllerImpl(FeatureFlags featureFlags) {
        this.featureFlags = featureFlags;
    }

    public final void shouldUseSplitNotificationShade() {
        Flags flags = Flags.INSTANCE;
        this.featureFlags.getClass();
    }
}
