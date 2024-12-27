package com.android.systemui.log;

import android.app.ActivityManager;
import com.android.systemui.dump.DumpManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LogBufferFactory {
    public final DumpManager dumpManager;
    public final LogcatEchoTracker logcatEchoTracker;

    public LogBufferFactory(DumpManager dumpManager, LogcatEchoTracker logcatEchoTracker) {
        this.dumpManager = dumpManager;
        this.logcatEchoTracker = logcatEchoTracker;
    }

    public final LogBuffer create(int i, String str) {
        return create(i, str, true);
    }

    public final LogBuffer create(int i, String str, boolean z) {
        LogBufferHelper.Companion.getClass();
        if (ActivityManager.isLowRamDeviceStatic()) {
            i = Math.min(i, 20);
        }
        LogBuffer logBuffer = new LogBuffer(str, i, this.logcatEchoTracker, z);
        this.dumpManager.registerBuffer(logBuffer, str);
        return logBuffer;
    }
}
