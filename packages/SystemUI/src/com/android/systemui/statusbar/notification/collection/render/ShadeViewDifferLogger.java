package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = str3;
        logBuffer.commit(obtain);
    }
}
