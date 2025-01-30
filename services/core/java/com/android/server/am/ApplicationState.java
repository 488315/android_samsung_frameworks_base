package com.android.server.am;

import java.util.HashSet;

/* compiled from: AppStateBroadcaster.java */
/* loaded from: classes.dex */
public class ApplicationState {
  public int mStopReason = 0;
  public HashSet mProcessIds = new HashSet();

  public String getTermReason() {
    return AppStateBroadcaster.APP_TERM_REASONS[this.mStopReason];
  }

  public void addProcess(int i) {
    this.mProcessIds.add(Integer.valueOf(i));
  }

  public boolean removeProcess(int i, int i2) {
    this.mStopReason = Math.max(i2, this.mStopReason);
    this.mProcessIds.remove(Integer.valueOf(i));
    return this.mProcessIds.isEmpty();
  }
}
