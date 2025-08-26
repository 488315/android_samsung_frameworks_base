package com.android.systemui.statusbar.notification.shared;

import com.android.systemui.flags.RefactorFlagUtils;

/* loaded from: classes3.dex */
public final class NotificationThrottleHun {
    public static final NotificationThrottleHun INSTANCE = new NotificationThrottleHun();

    private NotificationThrottleHun() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        throw new IllegalStateException("Legacy code path not supported when com.android.systemui.notification_avalanche_throttle_hun is enabled.".toString());
    }
}
