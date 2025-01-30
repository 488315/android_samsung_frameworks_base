package com.android.server.chimera.heimdall;

/* loaded from: classes.dex */
public final class HeimdallProcessData {
  public static HeimdallProcessData sPool;
  public static int sPoolSize;
  public static final Object sPoolSync = new Object();
  public int anonAfterGc;
  public int anonBeforeGc;
  public int countDownPhaseRetry;
  public long delayPhase;
  public String firstAppPackageName;
  public String firstAppVersionName;
  public int flags;
  public int graphicsAfterGc;
  public int graphicsBeforeGc;
  public long killTime;
  public int memTrackEgl;
  public int memTrackGl;
  public int memTrackOther;
  public HeimdallProcessData next;
  public int pid;
  public String processName;
  public long reportTime;
  public int rssAnon;
  public int rssFile;
  public long scanTimeAfterGc;
  public long scanTimeBeforeGc;
  public int vmRss;
  public int vmSwap;

  public String phaseToString(int i) {
    return i != 4
        ? i != 8
            ? i != 16 ? i != 32 ? i != 64 ? "None" : "StartPhase" : "ScanPhase" : "GCPhase"
            : "RescanPhase"
        : "KillAndReportPhase";
  }

  public static HeimdallProcessData obtainData() {
    synchronized (sPoolSync) {
      HeimdallProcessData heimdallProcessData = sPool;
      if (heimdallProcessData != null) {
        sPool = heimdallProcessData.next;
        heimdallProcessData.next = null;
        sPoolSize--;
        heimdallProcessData.markInUse();
        return heimdallProcessData;
      }
      HeimdallProcessData heimdallProcessData2 = new HeimdallProcessData();
      heimdallProcessData2.markInUse();
      return heimdallProcessData2;
    }
  }

  public void recycle() {
    if (isInUse()) {
      this.flags = 0;
      this.processName = null;
      this.pid = -1;
      this.firstAppPackageName = null;
      this.anonBeforeGc = 0;
      this.graphicsBeforeGc = 0;
      this.scanTimeBeforeGc = 0L;
      this.anonAfterGc = 0;
      this.graphicsAfterGc = 0;
      this.scanTimeAfterGc = 0L;
      this.vmRss = 0;
      this.rssFile = 0;
      this.rssAnon = 0;
      this.vmSwap = 0;
      this.memTrackGl = 0;
      this.memTrackEgl = 0;
      this.memTrackOther = 0;
      this.firstAppVersionName = null;
      this.reportTime = 0L;
      this.killTime = 0L;
      this.delayPhase = 0L;
      this.countDownPhaseRetry = 0;
      synchronized (sPoolSync) {
        int i = sPoolSize;
        if (i < 50) {
          this.next = sPool;
          sPool = this;
          sPoolSize = i + 1;
        }
      }
    }
  }

  public boolean isInUse() {
    return (this.flags & 128) == 128;
  }

  public void markInUse() {
    this.flags |= 128;
  }

  public boolean isGlobalKill() {
    return (this.flags & 256) == 256;
  }

  public boolean isSpecKill() {
    return (this.flags & 512) == 512;
  }

  public boolean shouldKill() {
    return (this.flags & 1024) == 1024;
  }

  public void setGlobalKill() {
    if (isSpecKill()) {
      Heimdall.log("This HPD cannot trigger global kill. Because spec kill is already triggered.");
    } else {
      this.flags |= 256;
    }
  }

  public void clearGlobalKill() {
    this.flags &= -257;
  }

  public void setSpecKill() {
    if (isGlobalKill()) {
      Heimdall.log("This HPD cannot trigger spec kill. Because global kill is already triggered.");
    } else {
      this.flags |= 512;
    }
  }

  public void clearSpecKill() {
    this.flags &= -513;
  }

  public void setShouldKill() {
    if (!isSpecKill() && !isGlobalKill()) {
      Heimdall.log("This HPD should not kill.");
    } else {
      this.flags |= 1024;
    }
  }

  public void clearShouldKill() {
    this.flags &= -1025;
  }

  public void moveNextPhaseIfNotRetry() {
    int i = this.countDownPhaseRetry;
    if (i > 0) {
      this.countDownPhaseRetry = i - 1;
    } else {
      moveNextPhase();
    }
    calcDelayTimePhase();
  }

  public int currentPhase() {
    int i = this.flags;
    return i & (-i);
  }

  public int nextPhase() {
    int currentPhase = currentPhase();
    return currentPhase == 4 ? currentPhase : currentPhase >> 1;
  }

  public final void calcDelayTimePhase() {
    if (this.countDownPhaseRetry > 0) {
      if (currentPhase() == 32) {
        this.delayPhase = 20000L;
        return;
      } else {
        this.delayPhase = 0L;
        return;
      }
    }
    if (currentPhase() == 16) {
      this.delayPhase = 5000L;
    } else {
      this.delayPhase = 0L;
    }
  }

  public final void moveNextPhase() {
    this.flags |= nextPhase();
    if (currentPhase() == 32) {
      this.countDownPhaseRetry = 3;
    } else {
      this.countDownPhaseRetry = 0;
    }
  }

  public String toDumpString() {
    StringBuilder sb = new StringBuilder();
    sb.append("HeimdallData{");
    sb.append(" processName=" + this.processName);
    sb.append(" pid=" + this.pid);
    sb.append(" packageName=" + this.firstAppPackageName);
    sb.append(" versionName=" + this.firstAppVersionName);
    sb.append(" anonBeforeGc=" + this.anonBeforeGc);
    sb.append(" graphicsBeforeGc=" + this.graphicsBeforeGc);
    sb.append(" scanTimeBeforeGc=" + this.scanTimeBeforeGc);
    sb.append(" anonAfterGc=" + this.anonAfterGc);
    sb.append(" graphicsAfterGc=" + this.graphicsAfterGc);
    sb.append(" scanTimeAfterGc=" + this.scanTimeAfterGc);
    sb.append(" vmRss=" + this.vmRss);
    sb.append(" rssFile=" + this.rssFile);
    sb.append(" rssAnon=" + this.rssAnon);
    sb.append(" vmSwap=" + this.vmSwap);
    sb.append(" memTrackGl=" + this.memTrackGl);
    sb.append(" memTrackEgl=" + this.memTrackEgl);
    sb.append(" memTrackOther=" + this.memTrackOther);
    sb.append(" reportTime=" + this.reportTime);
    sb.append(" killTime=" + this.killTime);
    sb.append(" currentPhase=" + phaseToString(currentPhase()));
    sb.append(" isGlobalKill=" + isGlobalKill());
    sb.append(" isSpecKill=" + isSpecKill());
    sb.append(" shouldKill=" + shouldKill());
    sb.append(" }");
    return sb.toString();
  }
}
