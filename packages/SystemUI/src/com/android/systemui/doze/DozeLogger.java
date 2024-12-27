package com.android.systemui.doze;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import kotlin.jvm.functions.Function1;

public final class DozeLogger {
    public final LogBuffer buffer;

    public DozeLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logEmergencyCall() {
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logEmergencyCall$2 dozeLogger$logEmergencyCall$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logEmergencyCall$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Emergency call";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("DozeLog", logLevel, dozeLogger$logEmergencyCall$2, null));
    }
}
