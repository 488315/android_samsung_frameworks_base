package com.android.systemui.statusbar;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public enum StatusBarStateEvent implements UiEventLogger.UiEventEnum {
    STATUS_BAR_STATE_UNKNOWN(428),
    STATUS_BAR_STATE_SHADE(429),
    STATUS_BAR_STATE_KEYGUARD(430),
    STATUS_BAR_STATE_SHADE_LOCKED(431);

    private int mId;

    StatusBarStateEvent(int i) {
        this.mId = i;
    }

    public final int getId() {
        return this.mId;
    }
}
