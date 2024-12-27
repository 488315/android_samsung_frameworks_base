package com.android.systemui.statusbar.notification.row;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifBindPipelineLogger {
    public final LogBuffer buffer;

    public NotifBindPipelineLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logManagedRow(NotificationEntry notificationEntry) {
        LogLevel logLevel = LogLevel.INFO;
        NotifBindPipelineLogger$logManagedRow$2 notifBindPipelineLogger$logManagedRow$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$logManagedRow$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Row set for notif: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$logManagedRow$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }
}
