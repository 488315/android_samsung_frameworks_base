package com.android.systemui.statusbar;

import android.app.PendingIntent;
import com.android.app.tracing.coroutines.TraceData$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ActionClickLogger {
    public final LogBuffer buffer;

    public ActionClickLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logKeyguardGone(PendingIntent pendingIntent, Integer num) {
        LogLevel logLevel = LogLevel.DEBUG;
        ActionClickLogger$logKeyguardGone$2 actionClickLogger$logKeyguardGone$2 = new Function1() { // from class: com.android.systemui.statusbar.ActionClickLogger$logKeyguardGone$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return TraceData$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "  [Action click] Keyguard dismissed, calling default handler for intent ", logMessage.getStr1(), " at index ");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ActionClickLogger", logLevel, actionClickLogger$logKeyguardGone$2, null);
        ((LogMessageImpl) obtain).str1 = pendingIntent.toString();
        ((LogMessageImpl) obtain).int1 = num != null ? num.intValue() : Integer.MIN_VALUE;
        logBuffer.commit(obtain);
    }
}
