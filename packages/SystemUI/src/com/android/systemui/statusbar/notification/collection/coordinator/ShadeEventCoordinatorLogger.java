package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShadeEventCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public ShadeEventCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logNotifRemovedByUser() {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("ShadeEventCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger$logNotifRemovedByUser$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return "Notification removed by user";
            }
        }, null));
    }

    public final void logShadeEmptied() {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("ShadeEventCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger$logShadeEmptied$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return "Shade emptied";
            }
        }, null));
    }
}
