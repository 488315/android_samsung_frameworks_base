package com.android.server.alarm;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
