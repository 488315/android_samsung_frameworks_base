package com.android.systemui.statusbar.notification.interruption;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationInterruptLogger {
    public final LogBuffer buffer;

    public NotificationInterruptLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logMaybeHeadsUpDespiteOldWhen(NotificationEntry notificationEntry, long j, long j2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationInterruptLogger$logMaybeHeadsUpDespiteOldWhen$2 notificationInterruptLogger$logMaybeHeadsUpDespiteOldWhen$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logMaybeHeadsUpDespiteOldWhen$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                long long1 = logMessage.getLong1();
                long long2 = logMessage.getLong2();
                String str2 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("Maybe heads up: old when ", long1, " (age=");
                m.append(long2);
                m.append(" ms) but ");
                m.append(str2);
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(m, ": ", str1);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logMaybeHeadsUpDespiteOldWhen$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = str;
        logMessageImpl.long1 = j;
        logMessageImpl.long2 = j2;
        logBuffer.commit(obtain);
    }

    public final void logNoAlertingSuppressedBy(NotificationEntry notificationEntry, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationInterruptLogger$logNoAlertingSuppressedBy$2 notificationInterruptLogger$logNoAlertingSuppressedBy$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoAlertingSuppressedBy$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str2 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                String str1 = logMessage.getStr1();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("No alerting: aborted by suppressor: ", str2, " awake=", " sbnKey=", bool1);
                m.append(str1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logNoAlertingSuppressedBy$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = "StatusBarNotificationPresenter";
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logNoFullscreenWarning(NotificationEntry notificationEntry, String str) {
        LogLevel logLevel = LogLevel.WARNING;
        NotificationInterruptLogger$logNoFullscreenWarning$2 notificationInterruptLogger$logNoFullscreenWarning$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$logNoFullscreenWarning$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("No FullScreenIntent: WARNING: ", logMessage.getStr2(), ": ", logMessage.getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$logNoFullscreenWarning$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        ((LogMessageImpl) obtain).str2 = str;
        logBuffer.commit(obtain);
    }
}
