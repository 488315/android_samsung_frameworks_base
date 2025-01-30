package com.android.systemui.statusbar.notification.row;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
enum NotificationControlsEvent implements UiEventLogger.UiEventEnum {
    NOTIFICATION_CONTROLS_OPEN(594),
    NOTIFICATION_CONTROLS_SAVE_IMPORTANCE(595),
    NOTIFICATION_CONTROLS_CLOSE(596);

    private final int mId;

    NotificationControlsEvent(int i) {
        this.mId = i;
    }

    public final int getId() {
        return this.mId;
    }
}
