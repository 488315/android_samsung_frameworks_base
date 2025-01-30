package com.android.server.chimera.psitracker;

/* loaded from: classes.dex */
public class PSIAvailableMemRecord {
  public long availMem = 0;
  public long running = 0;
  public long cached = 0;
  public long checkTime = 0;

  public String toString() {
    return "<availMem:"
        + this.availMem
        + " running:"
        + this.running
        + " cached:"
        + this.cached
        + " checkTime:"
        + this.checkTime
        + ">";
  }
}
