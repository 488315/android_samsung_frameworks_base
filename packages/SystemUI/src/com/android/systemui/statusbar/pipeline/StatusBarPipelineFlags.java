package com.android.systemui.statusbar.pipeline;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.flags.FeatureFlags;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
