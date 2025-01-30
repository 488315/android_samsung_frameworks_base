package com.android.systemui.statusbar.policy;

import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HeadsUpManagerLogger {
    public final LogBuffer buffer;

    public HeadsUpManagerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logReleaseAllImmediately() {
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logReleaseAllImmediately$2 headsUpManagerLogger$logReleaseAllImmediately$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logReleaseAllImmediately$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "release all immediately";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logReleaseAllImmediately$2, null));
    }

    public final void logRemoveNotification(String str, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logRemoveNotification$2 headsUpManagerLogger$logRemoveNotification$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logRemoveNotification$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "remove notification " + logMessage.getStr1() + " releaseImmediately: " + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logRemoveNotification$2, null);
        obtain.setStr1(NotificationUtils.logKey(str));
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logShowNotification(NotificationEntry notificationEntry) {
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logShowNotification$2 headsUpManagerLogger$logShowNotification$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logShowNotification$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("show notification ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logShowNotification$2, null);
        NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer, obtain);
    }

    public final void logUpdateEntry(NotificationEntry notificationEntry, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logUpdateEntry$2 headsUpManagerLogger$logUpdateEntry$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logUpdateEntry$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "update entry " + logMessage.getStr1() + " updatePostTime: " + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logUpdateEntry$2, null);
        obtain.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logUpdateNotification(String str, boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logUpdateNotification$2 headsUpManagerLogger$logUpdateNotification$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logUpdateNotification$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "update notification " + logMessage.getStr1() + " alert: " + logMessage.getBool1() + " hasEntry: " + logMessage.getBool2();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logUpdateNotification$2, null);
        obtain.setStr1(NotificationUtils.logKey(str));
        obtain.setBool1(z);
        obtain.setBool2(z2);
        logBuffer.commit(obtain);
    }
}
