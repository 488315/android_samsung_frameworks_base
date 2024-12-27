package com.android.systemui.media.controls.domain.pipeline;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MediaLoadingLogger {
    public final LogBuffer buffer;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MediaLoadingLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logMediaLoaded(InstanceId instanceId, boolean z, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLoadingLogger$logMediaLoaded$2 mediaLoadingLogger$logMediaLoaded$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaLoadingLogger$logMediaLoaded$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                String str2 = logMessage.getStr2();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("add media ", str1, ", active: ", ", reason: ", bool1);
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLoadingLog", logLevel, mediaLoadingLogger$logMediaLoaded$2, null);
        ((LogMessageImpl) obtain).str1 = instanceId.toString();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
    }

    public final void logMediaRemoved(InstanceId instanceId, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLoadingLogger$logMediaRemoved$2 mediaLoadingLogger$logMediaRemoved$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaLoadingLogger$logMediaRemoved$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("removing media ", logMessage.getStr1(), ", reason: ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLoadingLog", logLevel, mediaLoadingLogger$logMediaRemoved$2, null);
        ((LogMessageImpl) obtain).str1 = instanceId.toString();
        ((LogMessageImpl) obtain).str2 = str;
        logBuffer.commit(obtain);
    }
}
