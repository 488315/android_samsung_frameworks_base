package com.android.systemui.statusbar.notification;

import com.android.systemui.flags.FeatureFlags;

public final class NotifPipelineFlags {
    public final FeatureFlags featureFlags;

    public NotifPipelineFlags(FeatureFlags featureFlags) {
        this.featureFlags = featureFlags;
    }
}
