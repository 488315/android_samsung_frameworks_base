package com.android.systemui.basic.util;

import android.util.Log;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public final void i(String str, String str2) {
        String moduleTag = toModuleTag(str);
        getClass();
        Log.i(moduleTag, str2);
    }

    public final void p(String str) {
        SamsungServiceLogger samsungServiceLogger = this.serviceLogger;
        if (samsungServiceLogger != null) {
            String moduleType = this.module.toString();
            LogLevel logLevel = LogLevel.DEBUG;
            Intrinsics.checkNotNull(str);
            ((SamsungServiceLoggerImpl) samsungServiceLogger).log(moduleType, logLevel, str);
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
