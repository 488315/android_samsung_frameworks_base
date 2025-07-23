package com.android.systemui.statusbar.notification.row;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
