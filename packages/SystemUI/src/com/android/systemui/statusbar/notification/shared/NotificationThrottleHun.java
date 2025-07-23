package com.android.systemui.statusbar.notification.shared;

import com.android.systemui.flags.RefactorFlagUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
