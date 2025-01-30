package com.android.server.am;

import android.os.Binder;
import android.os.SystemClock;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.internal.app.procstats.AssociationState;
import com.android.internal.app.procstats.ProcessStats;

/* loaded from: classes.dex */
public final class ContentProviderConnection extends Binder {
  public AssociationState.SourceState association;
  public final ProcessRecord client;
  public final String clientPackage;
  public boolean dead;
  public final int mExpectedUserId;
  public int mNumStableIncs;
  public int mNumUnstableIncs;
  public Object mProcStatsLock;
  public int mStableCount;
  public int mUnstableCount;
  public final ContentProviderRecord provider;
  public boolean waiting;
  public final Object mLock = new Object();
  public final long createTime = SystemClock.elapsedRealtime();

  public int getStableCount() {
    return this.mStableCount;
  }

  public int getUnstableCount() {
    return this.mUnstableCount;
  }

  public ContentProviderConnection(
      ContentProviderRecord contentProviderRecord, ProcessRecord processRecord, String str, int i) {
    this.provider = contentProviderRecord;
    this.client = processRecord;
    this.clientPackage = str;
    this.mExpectedUserId = i;
  }

  public void startAssociationIfNeeded() {
    if (this.association == null) {
      ContentProviderRecord contentProviderRecord = this.provider;
      if (contentProviderRecord.proc != null) {
        int i = contentProviderRecord.appInfo.uid;
        ProcessRecord processRecord = this.client;
        if (i == processRecord.uid
            && contentProviderRecord.info.processName.equals(processRecord.processName)) {
          return;
        }
        ProcessStats.ProcessStateHolder processStateHolder =
            this.provider.proc.getPkgList().get(this.provider.name.getPackageName());
        if (processStateHolder == null) {
          Slog.wtf(
              "ActivityManager",
              "No package in referenced provider "
                  + this.provider.name.toShortString()
                  + ": proc="
                  + this.provider.proc);
          return;
        }
        if (processStateHolder.pkg == null) {
          Slog.wtf(
              "ActivityManager",
              "Inactive holder in referenced provider "
                  + this.provider.name.toShortString()
                  + ": proc="
                  + this.provider.proc);
          return;
        }
        Object obj = this.provider.proc.mService.mProcessStats.mLock;
        this.mProcStatsLock = obj;
        synchronized (obj) {
          AssociationState associationStateLocked =
              processStateHolder.pkg.getAssociationStateLocked(
                  processStateHolder.state, this.provider.name.getClassName());
          ProcessRecord processRecord2 = this.client;
          this.association =
              associationStateLocked.startSource(
                  processRecord2.uid, processRecord2.processName, this.clientPackage);
        }
      }
    }
  }

  public void trackProcState(int i, int i2) {
    if (this.association != null) {
      synchronized (this.mProcStatsLock) {
        this.association.trackProcState(i, i2, SystemClock.uptimeMillis());
      }
    }
  }

  public void stopAssociation() {
    if (this.association != null) {
      synchronized (this.mProcStatsLock) {
        this.association.stop();
      }
      this.association = null;
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(128);
    sb.append("ContentProviderConnection{");
    toShortString(sb);
    sb.append('}');
    return sb.toString();
  }

  public String toShortString() {
    StringBuilder sb = new StringBuilder(128);
    toShortString(sb);
    return sb.toString();
  }

  public String toClientString() {
    StringBuilder sb = new StringBuilder(128);
    toClientString(sb);
    return sb.toString();
  }

  public void toShortString(StringBuilder sb) {
    sb.append(this.provider.toShortString());
    sb.append("->");
    toClientString(sb);
  }

  public void toClientString(StringBuilder sb) {
    sb.append(this.client.toShortString());
    synchronized (this.mLock) {
      sb.append(" s");
      sb.append(this.mStableCount);
      sb.append("/");
      sb.append(this.mNumStableIncs);
      sb.append(" u");
      sb.append(this.mUnstableCount);
      sb.append("/");
      sb.append(this.mNumUnstableIncs);
    }
    if (this.waiting) {
      sb.append(" WAITING");
    }
    if (this.dead) {
      sb.append(" DEAD");
    }
    long elapsedRealtime = SystemClock.elapsedRealtime();
    sb.append(" ");
    TimeUtils.formatDuration(elapsedRealtime - this.createTime, sb);
  }

  public void initializeCount(boolean z) {
    synchronized (this.mLock) {
      if (z) {
        this.mStableCount = 1;
        this.mNumStableIncs = 1;
        this.mUnstableCount = 0;
        this.mNumUnstableIncs = 0;
      } else {
        this.mStableCount = 0;
        this.mNumStableIncs = 0;
        this.mUnstableCount = 1;
        this.mNumUnstableIncs = 1;
      }
    }
  }

  public int incrementCount(boolean z) {
    int i;
    synchronized (this.mLock) {
      if (z) {
        this.mStableCount++;
        this.mNumStableIncs++;
      } else {
        this.mUnstableCount++;
        this.mNumUnstableIncs++;
      }
      i = this.mStableCount + this.mUnstableCount;
    }
    return i;
  }

  public int decrementCount(boolean z) {
    int i;
    synchronized (this.mLock) {
      if (z) {
        this.mStableCount--;
      } else {
        this.mUnstableCount--;
      }
      i = this.mStableCount + this.mUnstableCount;
    }
    return i;
  }

  public void adjustCounts(int i, int i2) {
    synchronized (this.mLock) {
      if (i > 0) {
        this.mNumStableIncs += i;
      }
      int i3 = this.mStableCount + i;
      if (i3 < 0) {
        throw new IllegalStateException("stableCount < 0: " + i3);
      }
      if (i2 > 0) {
        this.mNumUnstableIncs += i2;
      }
      int i4 = this.mUnstableCount + i2;
      if (i4 < 0) {
        throw new IllegalStateException("unstableCount < 0: " + i4);
      }
      if (i3 + i4 <= 0) {
        throw new IllegalStateException(
            "ref counts can't go to zero here: stable=" + i3 + " unstable=" + i4);
      }
      this.mStableCount = i3;
      this.mUnstableCount = i4;
    }
  }

  public int stableCount() {
    int i;
    synchronized (this.mLock) {
      i = this.mStableCount;
    }
    return i;
  }

  public int totalRefCount() {
    int i;
    synchronized (this.mLock) {
      i = this.mStableCount + this.mUnstableCount;
    }
    return i;
  }
}
