package com.android.systemui.statusbar.notification.logging;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface NotificationPanelLogger {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum NotificationPanelEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_PANEL_OPEN_STATUS_BAR(200),
        NOTIFICATION_PANEL_OPEN_LOCKSCREEN(201),
        NOTIFICATION_DRAG(1226);

        private final int mId;

        NotificationPanelEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    static int toNotificationSection(int i) {
        if (i == 1) {
            return 2;
        }
        if (i == 7) {
            return 1;
        }
        if (i == 8) {
            return 6;
        }
        if (i == 10 || i == 11) {
            return 3;
        }
        switch (i) {
            case 15:
                return 4;
            case 16:
                return 10;
            case 17:
                return 11;
            case 18:
                return 12;
            case 19:
                return 13;
            case 20:
                return 5;
            default:
                return 0;
        }
    }
}
