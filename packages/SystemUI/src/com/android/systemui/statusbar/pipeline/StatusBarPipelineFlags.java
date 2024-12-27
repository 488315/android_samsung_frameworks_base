package com.android.systemui.statusbar.pipeline;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.flags.FeatureFlags;

public final class StatusBarPipelineFlags {
    public final FeatureFlags featureFlags;
    public final String mobileSlot;
    public final String mobileSlot2;
    public final String wifiSlot;

    public StatusBarPipelineFlags(Context context, FeatureFlags featureFlags) {
        this.featureFlags = featureFlags;
        this.mobileSlot = context.getString(17043145);
        this.mobileSlot2 = context.getString(17043146);
        this.wifiSlot = context.getString(17043166);
    }

    public final boolean isIconControlledByFlags(String str) {
        return str.equals(this.wifiSlot) || str.equals(this.mobileSlot) || (BasicRune.STATUS_NETWORK_MULTI_SIM && str.equals(this.mobileSlot2));
    }
}
