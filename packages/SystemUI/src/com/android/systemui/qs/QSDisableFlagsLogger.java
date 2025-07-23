package com.android.systemui.qs;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSDisableFlagsLogger {
    public final LogBuffer buffer;
    public final DisableFlagsLogger disableFlagsLogger;

    public QSDisableFlagsLogger(LogBuffer logBuffer, DisableFlagsLogger disableFlagsLogger) {
        this.buffer = logBuffer;
        this.disableFlagsLogger = disableFlagsLogger;
    }
}
