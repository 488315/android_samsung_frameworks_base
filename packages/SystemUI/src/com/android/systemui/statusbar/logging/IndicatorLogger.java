package com.android.systemui.statusbar.logging;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class IndicatorLogger {
    public final LogBuffer buffer;

    public IndicatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void log(String str) {
        LogLevel logLevel = LogLevel.INFO;
        IndicatorLogger$log$2 indicatorLogger$log$2 = new Function1() { // from class: com.android.systemui.statusbar.logging.IndicatorLogger$log$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("[", logMessage.getStr1(), "] ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("IndicatorLog", logLevel, indicatorLogger$log$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = "DesktopManager";
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
    }
}
