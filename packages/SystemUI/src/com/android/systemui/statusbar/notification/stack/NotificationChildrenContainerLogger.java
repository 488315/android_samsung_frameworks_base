package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationChildrenContainerLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer notificationRenderBuffer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
