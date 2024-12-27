package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;

public final class PreparationCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public PreparationCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logDelayingGroupRelease(GroupEntry groupEntry, NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logDelayingGroupRelease$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return MotionLayout$$ExternalSyntheticOutline0.m("Delaying release of group ", logMessage.getStr1(), " because child ", logMessage.getStr2(), " is still inflating");
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(groupEntry);
        ((LogMessageImpl) obtain).str2 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }

    public final void logDoneWaitingForGroupInflation(GroupEntry groupEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logDoneWaitingForGroupInflation$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Finished inflating all members of group ", logMessage.getStr1(), ", releasing group");
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(groupEntry);
        logBuffer.commit(obtain);
    }

    public final void logFreeNotifViews(NotificationEntry notificationEntry, String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logFreeNotifViews$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return FontProvider$$ExternalSyntheticOutline0.m("Freeing content views for notif ", logMessage.getStr1(), " reason=", logMessage.getStr2());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        ((LogMessageImpl) obtain).str2 = str;
        logBuffer.commit(obtain);
    }

    public final void logGroupInflationTookTooLong(GroupEntry groupEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logGroupInflationTookTooLong$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Group inflation took too long for ", logMessage.getStr1(), ", releasing children early");
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(groupEntry);
        logBuffer.commit(obtain);
    }

    public final void logInflationAborted(NotificationEntry notificationEntry, String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logInflationAborted$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return FontProvider$$ExternalSyntheticOutline0.m("Infation aborted for notif ", logMessage.getStr1(), " reason=", logMessage.getStr2());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        ((LogMessageImpl) obtain).str2 = str;
        logBuffer.commit(obtain);
    }

    public final void logNotifInflated(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logNotifInflated$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Inflation completed for notif ", logMessage.getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }
}
