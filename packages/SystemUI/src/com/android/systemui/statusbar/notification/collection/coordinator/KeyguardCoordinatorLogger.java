package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

public final class KeyguardCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public KeyguardCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logAllMarkedSeenOnUnlock(int i, int i2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logAllMarkedSeenOnUnlock$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return logMessage.getInt1() + " Notifications have been marked as seen now that device is unlocked. " + logMessage.getInt2() + " notifications remain unseen.";
            }
        }, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logProviderHasFilteredOutSeenNotifs(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logProviderHasFilteredOutSeenNotifs$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("UI showing unseen filter treatment: ", logMessage.getBool1());
            }
        }, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logRemoveSeenOnLockscreen(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logRemoveSeenOnLockscreen$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Notification marked as seen on lockscreen removed: ", logMessage.getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = notificationEntry.mKey;
        logBuffer.commit(obtain);
    }

    public final void logResetSeenOnLockscreen(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logResetSeenOnLockscreen$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Reset tracking updated notification for lockscreen seen duration threshold: ", logMessage.getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = notificationEntry.mKey;
        logBuffer.commit(obtain);
    }

    public final void logSeenOnLockscreen(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logSeenOnLockscreen$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Notification [", logMessage.getStr1(), "] on lockscreen will be marked as seen when unlocked.");
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = notificationEntry.mKey;
        logBuffer.commit(obtain);
    }

    public final void logShadeExpanded() {
        this.buffer.log("KeyguardCoordinator", LogLevel.DEBUG, "Notifications have been marked as seen due to shade expansion.", null);
    }

    public final void logStopTrackingLockscreenSeenDuration(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logStopTrackingLockscreenSeenDuration$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Stop tracking removed notification for lockscreen seen duration threshold: ", logMessage.getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = notificationEntry.mKey;
        logBuffer.commit(obtain);
    }

    public final void logTrackingLockscreenSeenDuration(Set<NotificationEntry> set) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logTrackingLockscreenSeenDuration$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Tracking ", " unseen notifications for lockscreen seen duration threshold: ", logMessage.getStr1());
            }
        }, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = CollectionsKt___CollectionsKt.joinToString$default(set, null, null, null, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logTrackingLockscreenSeenDuration$1$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(NotificationEntry notificationEntry) {
                return notificationEntry.mKey;
            }
        }, 31);
        logMessageImpl.int1 = set.size();
        logBuffer.commit(obtain);
    }

    public final void logTrackingUnseen(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logTrackingUnseen$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return (logMessage.getBool1() ? "Start" : "Stop").concat(" tracking unseen notifications.");
            }
        }, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logUnseenAdded(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logUnseenAdded$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unseen notif added: ", logMessage.getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logUnseenHun(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logUnseenHun$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unseen notif has become heads up: ", logMessage.getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logUnseenRemoved(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logUnseenRemoved$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unseen notif removed: ", logMessage.getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logUnseenUpdated(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logUnseenUpdated$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unseen notif updated: ", logMessage.getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logTrackingLockscreenSeenDuration(NotificationEntry notificationEntry) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logTrackingLockscreenSeenDuration$4
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Tracking new notification for lockscreen seen duration threshold: ", logMessage.getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = notificationEntry.mKey;
        logBuffer.commit(obtain);
    }
}
