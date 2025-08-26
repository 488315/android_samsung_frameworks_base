package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* loaded from: classes3.dex */
public final class ShadeViewDifferLogger {
    public final LogBuffer buffer;

    public ShadeViewDifferLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logDetachingChild(String str, String str2, String str3, boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeViewDifferLogger$$ExternalSyntheticLambda0 shadeViewDifferLogger$$ExternalSyntheticLambda0 = new ShadeViewDifferLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = str3;
        logBuffer.commit(logMessageObtain);
    }
}
