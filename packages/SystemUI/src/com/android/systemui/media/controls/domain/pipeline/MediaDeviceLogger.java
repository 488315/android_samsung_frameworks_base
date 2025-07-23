package com.android.systemui.media.controls.domain.pipeline;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaDeviceLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer buffer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public MediaDeviceLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logBroadcastEvent(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaDeviceLogger$$ExternalSyntheticLambda0 mediaDeviceLogger$$ExternalSyntheticLambda0 = new MediaDeviceLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaDeviceLog", logLevel, mediaDeviceLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }
}
