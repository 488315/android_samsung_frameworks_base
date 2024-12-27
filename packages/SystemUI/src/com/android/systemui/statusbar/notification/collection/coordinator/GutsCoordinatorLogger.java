package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class GutsCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public GutsCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logGutsClosed(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("GutsCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinatorLogger$logGutsClosed$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Guts closed for class ", logMessage.getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logGutsOpened(String str, NotificationGuts notificationGuts) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("GutsCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinatorLogger$logGutsOpened$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                String str2 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                String str1 = logMessage.getStr1();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("Guts of type ", str2, " (leave behind: ", ") opened for class ", bool1);
                m.append(str1);
                return m.toString();
            }
        }, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = Reflection.getOrCreateKotlinClass(notificationGuts.mGutsContent.getClass()).getSimpleName();
        NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
        logMessageImpl.bool1 = gutsContent != null && gutsContent.isLeavebehind();
        logBuffer.commit(obtain);
    }
}
