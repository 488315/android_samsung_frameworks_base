package com.android.systemui.qs;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSDisableFlagsLogger {
    public final LogBuffer buffer;
    public final DisableFlagsLogger disableFlagsLogger;

    public QSDisableFlagsLogger(LogBuffer logBuffer, DisableFlagsLogger disableFlagsLogger) {
        this.buffer = logBuffer;
        this.disableFlagsLogger = disableFlagsLogger;
    }
}
