package com.android.systemui.statusbar.policy;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
