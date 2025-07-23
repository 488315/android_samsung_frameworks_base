package com.android.systemui.basic.util;

import android.util.Log;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LogWrapper {
    public final LogWrapper$logger$1 logger = new Object() { // from class: com.android.systemui.basic.util.LogWrapper$logger$1
    };
    public final ModuleType module;
    public final SamsungServiceLogger serviceLogger;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.basic.util.LogWrapper$logger$1] */
    public LogWrapper(ModuleType moduleType, SamsungServiceLogger samsungServiceLogger) {
        this.module = moduleType;
        this.serviceLogger = samsungServiceLogger;
    }

    public final void d(String str, String str2) {
        String moduleTag = toModuleTag(str);
        getClass();
        Log.d(moduleTag, str2);
    }

    public final void dp(String str, String str2) {
        d(str, str2);
        p(str2);
    }

    public final void e(String str, String str2) {
        String moduleTag = toModuleTag(str);
        getClass();
        Log.e(moduleTag, str2);
    }

    public final void i(String str) {
        String moduleTag = toModuleTag("AODTouchModeManager");
        getClass();
        Log.i(moduleTag, str);
    }

    public final void p(String str) {
        SamsungServiceLogger samsungServiceLogger = this.serviceLogger;
        if (samsungServiceLogger != null) {
            String moduleType = this.module.toString();
            LogLevel logLevel = LogLevel.DEBUG;
            str.getClass();
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7(str);
            LogBuffer logBuffer = ((SamsungServiceLoggerImpl) samsungServiceLogger).buffer;
            logBuffer.commit(logBuffer.obtain(moduleType, logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7, null));
        }
    }

    public final String toModuleTag(String str) {
        return this.module + str;
    }

    public final void v(String str) {
        toModuleTag(str);
        getClass();
    }
}
