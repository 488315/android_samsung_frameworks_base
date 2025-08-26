package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NotificationChildrenContainerLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer notificationRenderBuffer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public NotificationChildrenContainerLogger(LogBuffer logBuffer) {
        this.notificationRenderBuffer = logBuffer;
    }
}
