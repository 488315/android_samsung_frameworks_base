package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PreparationCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public PreparationCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDelayingGroupRelease$lambda$11(LogMessage logMessage) {
        return MotionLayout$$ExternalSyntheticOutline0.m("Delaying release of group ", logMessage.getStr1(), " because child ", logMessage.getStr2(), " is still inflating");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDoneWaitingForGroupInflation$lambda$7(LogMessage logMessage) {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Finished inflating all members of group ", logMessage.getStr1(), ", releasing group");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFreeNotifViews$lambda$5(LogMessage logMessage) {
        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Freeing content views for notif ", logMessage.getStr1(), " reason=", logMessage.getStr2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logGroupInflationTookTooLong$lambda$9(LogMessage logMessage) {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Group inflation took too long for ", logMessage.getStr1(), ", releasing children early");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logInflationAborted$lambda$3(LogMessage logMessage) {
        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Infation aborted for notif ", logMessage.getStr1(), " reason=", logMessage.getStr2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifInflated$lambda$1(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Inflation completed for notif ", logMessage.getStr1());
    }

    public final void logDelayingGroupRelease(GroupEntry groupEntry, NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.DEBUG, new PreparationCoordinatorLogger$$ExternalSyntheticLambda0(2), null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(groupEntry);
        ((LogMessageImpl) obtain).str2 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }

    public final void logDoneWaitingForGroupInflation(GroupEntry groupEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.DEBUG, new PreparationCoordinatorLogger$$ExternalSyntheticLambda0(3), null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(groupEntry);
        logBuffer.commit(obtain);
    }

    public final void logFreeNotifViews(NotificationEntry notificationEntry, String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.DEBUG, new PreparationCoordinatorLogger$$ExternalSyntheticLambda0(4), null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        ((LogMessageImpl) obtain).str2 = str;
        logBuffer.commit(obtain);
    }

    public final void logGroupInflationTookTooLong(GroupEntry groupEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.WARNING, new PreparationCoordinatorLogger$$ExternalSyntheticLambda0(1), null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(groupEntry);
        logBuffer.commit(obtain);
    }

    public final void logInflationAborted(NotificationEntry notificationEntry, String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.DEBUG, new PreparationCoordinatorLogger$$ExternalSyntheticLambda0(5), null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        ((LogMessageImpl) obtain).str2 = str;
        logBuffer.commit(obtain);
    }

    public final void logNotifInflated(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", LogLevel.DEBUG, new PreparationCoordinatorLogger$$ExternalSyntheticLambda0(0), null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }
}
