package com.android.systemui.log;

import android.app.ActivityManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.echo.LogcatEchoTrackerAlways;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LogBufferFactory {
    public final DumpManager dumpManager;
    public final LogcatEchoTracker logcatEchoTracker;

    public LogBufferFactory(DumpManager dumpManager, LogcatEchoTracker logcatEchoTracker) {
        this.dumpManager = dumpManager;
        this.logcatEchoTracker = logcatEchoTracker;
    }

    public static LogBuffer create$default(LogBufferFactory logBufferFactory, String str, int i, boolean z, String str2, int i2) {
        LogcatEchoTracker logcatEchoTracker;
        boolean z2 = (i2 & 4) != 0 ? true : z;
        boolean z3 = (i2 & 8) == 0;
        if ((i2 & 16) != 0) {
            str2 = "UI Events";
        }
        String str3 = str2;
        if (z3) {
            logBufferFactory.getClass();
            logcatEchoTracker = LogcatEchoTrackerAlways.INSTANCE;
        } else {
            logcatEchoTracker = logBufferFactory.logcatEchoTracker;
        }
        LogcatEchoTracker logcatEchoTracker2 = logcatEchoTracker;
        LogBufferHelper.Companion.getClass();
        if (ActivityManager.isLowRamDeviceStatic()) {
            i = Math.min(i, 20);
        }
        LogBuffer logBuffer = new LogBuffer(str, i, logcatEchoTracker2, z2, str3);
        logBufferFactory.dumpManager.registerBuffer(logBuffer, str);
        return logBuffer;
    }

    public final LogBuffer create(int i, String str) {
        return create$default(this, str, i, false, null, 28);
    }

    public final LogBuffer create(int i, String str, boolean z) {
        return create$default(this, str, i, z, null, 24);
    }
}
