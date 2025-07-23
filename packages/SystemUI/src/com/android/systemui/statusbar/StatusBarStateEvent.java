package com.android.systemui.statusbar;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
