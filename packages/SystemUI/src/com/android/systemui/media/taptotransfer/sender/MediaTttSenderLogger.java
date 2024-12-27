package com.android.systemui.media.taptotransfer.sender;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MediaTttSenderLogger {
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

    public MediaTttSenderLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
