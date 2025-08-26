package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import java.io.IOException;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes3.dex */
public final class KeyguardCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public KeyguardCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logAllMarkedSeenOnUnlock$lambda$5(LogMessage logMessage) {
        return logMessage.getInt1() + " Notifications have been marked as seen now that device is unlocked. " + logMessage.getInt2() + " notifications remain unseen.";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logProviderHasFilteredOutSeenNotifs$lambda$13(LogMessage logMessage) {
        return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("UI showing unseen filter treatment: ", logMessage.getBool1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRemoveSeenOnLockscreen$lambda$26(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Notification marked as seen on lockscreen removed: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logResetSeenOnLockscreen$lambda$24(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Reset tracking updated notification for lockscreen seen duration threshold: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logSeenOnLockscreen$lambda$1(LogMessage logMessage) {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Notification [", logMessage.getStr1(), "] on lockscreen will be marked as seen when unlocked.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logStopTrackingLockscreenSeenDuration$lambda$22(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Stop tracking removed notification for lockscreen seen duration threshold: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logTrackingLockscreenSeenDuration$lambda$18(LogMessage logMessage) {
        return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Tracking ", " unseen notifications for lockscreen seen duration threshold: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logTrackingLockscreenSeenDuration$lambda$20(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Tracking new notification for lockscreen seen duration threshold: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logTrackingUnseen$lambda$3(LogMessage logMessage) {
        return (logMessage.getBool1() ? "Start" : "Stop").concat(" tracking unseen notifications.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logUnseenAdded$lambda$7(LogMessage logMessage) {
        return "Unseen notif added: " + logMessage.getStr1() + ", postTime: " + logMessage.getLong1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logUnseenHun$lambda$15(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unseen notif has become heads up: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logUnseenRemoved$lambda$11(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unseen notif removed: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logUnseenUpdated$lambda$9(LogMessage logMessage) {
        String str1 = logMessage.getStr1();
        String str2 = logMessage.getStr2();
        long long1 = logMessage.getLong1();
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Unseen notif updated: ", str1, ", source: ", str2, ", postTime: ");
        sbM.append(long1);
        return sbM.toString();
    }

    public final void logAllMarkedSeenOnUnlock(int i, int i2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(3), null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        ((LogMessageImpl) logMessageObtain).int2 = i2;
        logBuffer.commit(logMessageObtain);
    }

    public final void logProviderHasFilteredOutSeenNotifs(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(8), null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
    }

    public final void logRemoveSeenOnLockscreen(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(9), null);
        ((LogMessageImpl) logMessageObtain).str1 = notificationEntry.mKey;
        logBuffer.commit(logMessageObtain);
    }

    public final void logResetSeenOnLockscreen(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(10), null);
        ((LogMessageImpl) logMessageObtain).str1 = notificationEntry.mKey;
        logBuffer.commit(logMessageObtain);
    }

    public final void logSeenOnLockscreen(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(1), null);
        ((LogMessageImpl) logMessageObtain).str1 = notificationEntry.mKey;
        logBuffer.commit(logMessageObtain);
    }

    public final void logShadeExpanded() {
        LogBuffer.log$default(this.buffer, "KeyguardCoordinator", LogLevel.DEBUG, "Notifications have been marked as seen due to shade expansion.");
    }

    public final void logStopTrackingLockscreenSeenDuration(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(2), null);
        ((LogMessageImpl) logMessageObtain).str1 = notificationEntry.mKey;
        logBuffer.commit(logMessageObtain);
    }

    public final void logTrackingLockscreenSeenDuration(Set<NotificationEntry> set) throws IOException {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(6), null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = CollectionsKt___CollectionsKt.joinToString$default(set, null, null, null, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(7), 31);
        logMessageImpl.int1 = set.size();
        logBuffer.commit(logMessageObtain);
    }

    public final void logTrackingUnseen(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(0), null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
    }

    public final void logUnseenAdded(String str, long j) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(12), null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        ((LogMessageImpl) logMessageObtain).long1 = j;
        logBuffer.commit(logMessageObtain);
    }

    public final void logUnseenHun(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(13), null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        logBuffer.commit(logMessageObtain);
    }

    public final void logUnseenRemoved(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(4), null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        logBuffer.commit(logMessageObtain);
    }

    public final void logUnseenUpdated(String str, UpdateSource updateSource, long j) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(11), null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        String string = updateSource.toString();
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str2 = string;
        logMessageImpl.long1 = j;
        logBuffer.commit(logMessageObtain);
    }

    public final void logTrackingLockscreenSeenDuration(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(5), null);
        ((LogMessageImpl) logMessageObtain).str1 = notificationEntry.mKey;
        logBuffer.commit(logMessageObtain);
    }
}
