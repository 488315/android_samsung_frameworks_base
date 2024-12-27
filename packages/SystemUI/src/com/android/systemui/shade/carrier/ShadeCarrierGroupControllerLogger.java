package com.android.systemui.shade.carrier;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ShadeCarrierGroupControllerLogger {
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

    public ShadeCarrierGroupControllerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
