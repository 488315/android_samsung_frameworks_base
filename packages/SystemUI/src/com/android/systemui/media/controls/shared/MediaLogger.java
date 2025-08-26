package com.android.systemui.media.controls.shared;

import com.android.internal.logging.InstanceId;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class MediaLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer buffer;

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

    public MediaLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logDuplicateMediaNotification(String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogger$$ExternalSyntheticLambda0 mediaLogger$$ExternalSyntheticLambda0 = new MediaLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MediaLog", logLevel, mediaLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        logBuffer.commit(logMessageObtain);
    }

    public final void logMediaLoaded(InstanceId instanceId, boolean z, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogger$$ExternalSyntheticLambda0 mediaLogger$$ExternalSyntheticLambda0 = new MediaLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MediaLog", logLevel, mediaLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = instanceId.toString();
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.str2 = str;
        logBuffer.commit(logMessageObtain);
    }

    public final void logMediaRemoved(InstanceId instanceId, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogger$$ExternalSyntheticLambda0 mediaLogger$$ExternalSyntheticLambda0 = new MediaLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MediaLog", logLevel, mediaLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = instanceId.toString();
        ((LogMessageImpl) logMessageObtain).str2 = str;
        logBuffer.commit(logMessageObtain);
    }
}
