package com.android.systemui.statusbar.policy;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
