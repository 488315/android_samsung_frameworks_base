package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeListBuilderLogger {
    public final LogBuffer buffer;

    public ShadeListBuilderLogger(NotifPipelineFlags notifPipelineFlags, LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logPluggableInvalidated(String str, Pluggable pluggable, int i, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeListBuilderLogger$$ExternalSyntheticLambda0 shadeListBuilderLogger$$ExternalSyntheticLambda0 = new ShadeListBuilderLogger$$ExternalSyntheticLambda0(9);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = pluggable.getName();
        logMessageImpl.int1 = i;
        logMessageImpl.str3 = str2;
        logBuffer.commit(obtain);
    }
}
