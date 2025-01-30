package com.android.systemui.statusbar.logging;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                return FontProvider$$ExternalSyntheticOutline0.m32m("[", logMessage.getStr1(), "] ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("IndicatorLog", logLevel, indicatorLogger$log$2, null);
        CarrierTextManagerLogger$$ExternalSyntheticOutline0.m83m(obtain, "DesktopManager", str, logBuffer, obtain);
    }
}
