package com.android.systemui.media.taptotransfer.receiver;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.temporarydisplay.TemporaryViewLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MediaTttReceiverLogger extends TemporaryViewLogger {

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

    public MediaTttReceiverLogger(LogBuffer logBuffer) {
        super(logBuffer, "MediaTttReceiver");
    }
}
