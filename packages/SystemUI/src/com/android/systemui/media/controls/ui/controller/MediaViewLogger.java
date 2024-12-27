package com.android.systemui.media.controls.ui.controller;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

public final class MediaViewLogger {
    public final LogBuffer buffer;

    public MediaViewLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logMediaSize(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaViewLogger$logMediaSize$2 mediaViewLogger$logMediaSize$2 = new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewLogger$logMediaSize$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "size (", str1, "): ", " x ");
                m.append(int2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaView", logLevel, mediaViewLogger$logMediaSize$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }
}
