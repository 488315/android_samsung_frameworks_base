package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GutsCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public GutsCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logGutsClosed$lambda$3(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Guts closed for class ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logGutsOpened$lambda$1(LogMessage logMessage) {
        String str2 = logMessage.getStr2();
        boolean bool1 = logMessage.getBool1();
        String str1 = logMessage.getStr1();
        StringBuilder m = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Guts of type ", str2, " (leave behind: ", ") opened for class ", bool1);
        m.append(str1);
        return m.toString();
    }

    public final void logGutsClosed(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("GutsCoordinator", LogLevel.DEBUG, new GutsCoordinatorLogger$$ExternalSyntheticLambda0(1), null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logGutsOpened(String str, NotificationGuts notificationGuts) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("GutsCoordinator", LogLevel.DEBUG, new GutsCoordinatorLogger$$ExternalSyntheticLambda0(0), null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = Reflection.getOrCreateKotlinClass(notificationGuts.mGutsContent.getClass()).getSimpleName();
        logMessageImpl.bool1 = notificationGuts.isLeavebehind();
        logBuffer.commit(obtain);
    }
}
