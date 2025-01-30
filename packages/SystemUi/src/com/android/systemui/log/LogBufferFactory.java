package com.android.systemui.log;

import android.app.ActivityManager;
import com.android.systemui.dump.DumpManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LogBufferFactory {
    public final DumpManager dumpManager;
    public final LogcatEchoTracker logcatEchoTracker;

    public LogBufferFactory(DumpManager dumpManager, LogcatEchoTracker logcatEchoTracker) {
        this.dumpManager = dumpManager;
        this.logcatEchoTracker = logcatEchoTracker;
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
