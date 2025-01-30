package com.android.server.alarm;

import java.io.PrintWriter;

/* compiled from: AppSyncInfo.java */
/* loaded from: classes.dex */
public class DummyAppSync extends AppSyncWrapper {
  @Override // com.android.server.alarm.AppSyncWrapper
  public long getWindowLength() {
    return -1L;
  }

  @Override // com.android.server.alarm.AppSyncWrapper
  public boolean isAdjustableAlarm(int i, long j, long j2, long j3, int i2, String str) {
    return false;
  }

  @Override // com.android.server.alarm.AppSyncWrapper
  public void dump(PrintWriter printWriter, String str) {
    printWriter.println("<AppSync Disabled>");
  }
}
