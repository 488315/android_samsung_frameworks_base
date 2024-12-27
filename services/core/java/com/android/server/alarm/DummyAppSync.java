package com.android.server.alarm;

import java.io.PrintWriter;

public final class DummyAppSync extends AppSyncWrapper {
    @Override // com.android.server.alarm.AppSyncWrapper
    public final void dump(PrintWriter printWriter) {
        printWriter.println("<AppSync Disabled>");
    }

    @Override // com.android.server.alarm.AppSyncWrapper
    public final long getWindowLength() {
        return -1L;
    }

    @Override // com.android.server.alarm.AppSyncWrapper
    public final boolean isAdjustableAlarm(int i, int i2, long j, long j2, long j3, String str) {
        return false;
    }
}
