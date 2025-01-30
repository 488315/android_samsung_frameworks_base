package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PreparationCoordinatorLogger {
    public final LogBuffer buffer;

    public PreparationCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logDelayingGroupRelease(GroupEntry groupEntry, NotificationEntry notificationEntry) {
        LogLevel logLevel = LogLevel.DEBUG;
        PreparationCoordinatorLogger$logDelayingGroupRelease$2 preparationCoordinatorLogger$logDelayingGroupRelease$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logDelayingGroupRelease$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return MotionLayout$$ExternalSyntheticOutline0.m22m("Delaying release of group ", logMessage.getStr1(), " because child ", logMessage.getStr2(), " is still inflating");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", logLevel, preparationCoordinatorLogger$logDelayingGroupRelease$2, null);
        obtain.setStr1(NotificationUtilsKt.getLogKey(groupEntry));
        obtain.setStr2(NotificationUtilsKt.getLogKey(notificationEntry));
        logBuffer.commit(obtain);
    }
}
