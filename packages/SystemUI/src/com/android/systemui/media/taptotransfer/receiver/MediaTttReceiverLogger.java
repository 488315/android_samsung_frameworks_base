package com.android.systemui.media.taptotransfer.receiver;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.temporarydisplay.TemporaryViewLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaTttReceiverLogger extends TemporaryViewLogger {
    public static final /* synthetic */ int $r8$clinit = 0;

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

    public MediaTttReceiverLogger(LogBuffer logBuffer) {
        super(logBuffer, "MediaTttReceiver");
    }

    public final void logRippleAnimationEnd(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaTttReceiverLogger$$ExternalSyntheticLambda0 mediaTttReceiverLogger$$ExternalSyntheticLambda0 = new MediaTttReceiverLogger$$ExternalSyntheticLambda0(1);
        String str2 = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str2, logLevel, mediaTttReceiverLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logRippleAnimationStart(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaTttReceiverLogger$$ExternalSyntheticLambda0 mediaTttReceiverLogger$$ExternalSyntheticLambda0 = new MediaTttReceiverLogger$$ExternalSyntheticLambda0(0);
        String str2 = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str2, logLevel, mediaTttReceiverLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = str;
        logBuffer.commit(obtain);
    }
}
