package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VisualStabilityCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public VisualStabilityCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logAllowancesChanged$lambda$1(LogMessage logMessage) {
        logMessage.getBool1();
        logMessage.getBool2();
        logMessage.getBool3();
        logMessage.getBool4();
        logMessage.getStr1();
        logMessage.getStr2();
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(" async=", logMessage.getStr3());
    }

    public final void logAllowancesChanged(boolean z, boolean z2, boolean z3, boolean z4, String str, boolean z5, boolean z6) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("VisualStability", LogLevel.DEBUG, new VisualStabilityCoordinatorLogger$$ExternalSyntheticLambda0(), null);
        ((LogMessageImpl) obtain).bool1 = z;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logMessageImpl.bool4 = z4;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = String.valueOf(z5);
        logMessageImpl.str3 = String.valueOf(z6);
        logBuffer.commit(obtain);
    }
}
