package com.android.systemui.statusbar;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
