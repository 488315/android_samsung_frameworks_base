package com.android.systemui.statusbar.policy;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class BatteryControllerLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer logBuffer;

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

    public BatteryControllerLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }
}
