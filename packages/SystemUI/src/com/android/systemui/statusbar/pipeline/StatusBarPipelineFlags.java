package com.android.systemui.statusbar.pipeline;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.flags.FeatureFlags;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class StatusBarPipelineFlags {
    public final FeatureFlags featureFlags;
    public final String mobileSlot;
    public final String mobileSlot2;
    public final String wifiSlot;

    public StatusBarPipelineFlags(Context context, FeatureFlags featureFlags) {
        this.featureFlags = featureFlags;
        this.mobileSlot = context.getString(17043287);
        this.mobileSlot2 = context.getString(17043288);
        this.wifiSlot = context.getString(17043309);
    }

    public final boolean isIconControlledByFlags(String str) {
        if (Intrinsics.areEqual(str, this.wifiSlot) || Intrinsics.areEqual(str, this.mobileSlot)) {
            return true;
        }
        return BasicRune.STATUS_NETWORK_MULTI_SIM && Intrinsics.areEqual(str, this.mobileSlot2);
    }
}
