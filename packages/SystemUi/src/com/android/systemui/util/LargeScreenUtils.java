package com.android.systemui.util;

import android.content.res.Resources;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LargeScreenUtils {
    static {
        new LargeScreenUtils();
    }

    private LargeScreenUtils() {
    }

    public static final boolean shouldUseSplitNotificationShade(Resources resources) {
        return resources.getBoolean(R.bool.config_use_split_notification_shade);
    }
}
