package com.android.systemui.statusbar.notification;

import android.content.Context;
import com.android.internal.config.sysui.SystemUiSystemPropertiesFlags;
import com.android.systemui.flags.FeatureFlags;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifPipelineFlags {
    public final FeatureFlags featureFlags;
    public final SystemUiSystemPropertiesFlags.FlagResolver sysPropFlags;

    public NotifPipelineFlags(Context context, FeatureFlags featureFlags, SystemUiSystemPropertiesFlags.FlagResolver flagResolver) {
        this.featureFlags = featureFlags;
        this.sysPropFlags = flagResolver;
    }
}
