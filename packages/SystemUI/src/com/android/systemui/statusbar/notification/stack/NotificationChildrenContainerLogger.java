package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class NotificationChildrenContainerLogger {
    public final LogBuffer notificationRenderBuffer;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public NotificationChildrenContainerLogger(LogBuffer logBuffer) {
        this.notificationRenderBuffer = logBuffer;
    }
}
