package com.android.systemui.logging;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class PanelScreenShotBufferLogger {
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

    public PanelScreenShotBufferLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
