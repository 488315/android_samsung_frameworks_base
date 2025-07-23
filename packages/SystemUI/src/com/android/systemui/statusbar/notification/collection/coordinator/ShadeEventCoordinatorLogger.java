package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeEventCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public ShadeEventCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifRemovedByUser$lambda$3(LogMessage logMessage) {
        return "Notification removed by user";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logShadeEmptied$lambda$1(LogMessage logMessage) {
        return "Shade emptied";
    }

    public final void logNotifRemovedByUser() {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("ShadeEventCoordinator", LogLevel.DEBUG, new ShadeEventCoordinatorLogger$$ExternalSyntheticLambda0(1), null));
    }

    public final void logShadeEmptied() {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("ShadeEventCoordinator", LogLevel.DEBUG, new ShadeEventCoordinatorLogger$$ExternalSyntheticLambda0(0), null));
    }
}
