package com.android.systemui.qs;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;

public final class QSDisableFlagsLogger {
    public final LogBuffer buffer;
    public final DisableFlagsLogger disableFlagsLogger;

    public QSDisableFlagsLogger(LogBuffer logBuffer, DisableFlagsLogger disableFlagsLogger) {
        this.buffer = logBuffer;
        this.disableFlagsLogger = disableFlagsLogger;
    }
}
