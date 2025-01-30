package com.android.server.am;

import android.app.ActivityManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class UidRecord {
  public static int[] ORIG_ENUMS = {1, 2, 4, 8, 16, 32, Integer.MIN_VALUE};
  public static int[] PROTO_ENUMS = {0, 1, 2, 3, 4, 5, 6};
  public long curProcStateSeq;
  public volatile boolean hasInternetPermission;
  public long lastNetworkUpdatedProcStateSeq;
  public boolean mCurAllowList;
  public int mCurCapability;
  public int mCurProcState;
  public boolean mEphemeral;
  public boolean mForegroundServices;
  public boolean mIdle;
  public long mLastBackgroundTime;
  public long mLastIdleTime;
  public int mLastReportedChange;
  public int mNumProcs;
  public boolean mProcAdjChanged;
  public final ActivityManagerGlobalLock mProcLock;
  public final ActivityManagerService mService;
  public boolean mSetAllowList;
  public int mSetCapability;
  public boolean mSetIdle;
  public final int mUid;
  public boolean mUidIsFrozen;
  public volatile long procStateSeqWaitingForNetwork;
  public int mSetProcState = 20;
  public ArraySet mProcRecords = new ArraySet();
  public boolean mFGSFilter = false;
  public final Object networkStateLock = new Object();
  public final UidObserverController.ChangeRecord pendingChange =
      new UidObserverController.ChangeRecord();

  public UidRecord(int i, ActivityManagerService activityManagerService) {
    this.mUid = i;
    this.mService = activityManagerService;
    this.mProcLock = activityManagerService != null ? activityManagerService.mProcLock : null;
    this.mIdle = true;
    reset();
  }

  public int getUid() {
    return this.mUid;
  }

  public int getCurProcState() {
    return this.mCurProcState;
  }

  public void setCurProcState(int i) {
    this.mCurProcState = i;
  }

  public int getSetProcState() {
    return this.mSetProcState;
  }

  public void setSetProcState(int i) {
    this.mSetProcState = i;
  }

  public void noteProcAdjChanged() {
    this.mProcAdjChanged = true;
  }

  public void clearProcAdjChanged() {
    this.mProcAdjChanged = false;
  }

  public boolean getProcAdjChanged() {
    return this.mProcAdjChanged;
  }

  public int getMinProcAdj() {
    int i = 1001;
    for (int size = this.mProcRecords.size() - 1; size >= 0; size--) {
      int setAdj = ((ProcessRecord) this.mProcRecords.valueAt(size)).getSetAdj();
      if (setAdj < i) {
        i = setAdj;
      }
    }
    return i;
  }

  public int getCurCapability() {
    return this.mCurCapability;
  }

  public void setCurCapability(int i) {
    this.mCurCapability = i;
  }

  public int getSetCapability() {
    return this.mSetCapability;
  }

  public void setSetCapability(int i) {
    this.mSetCapability = i;
  }

  public long getLastBackgroundTime() {
    return this.mLastBackgroundTime;
  }

  public void setLastBackgroundTime(long j) {
    this.mLastBackgroundTime = j;
  }

  public long getLastIdleTime() {
    return this.mLastIdleTime;
  }

  public void setLastIdleTime(long j) {
    this.mLastIdleTime = j;
  }

  public boolean isEphemeral() {
    return this.mEphemeral;
  }

  public void setEphemeral(boolean z) {
    this.mEphemeral = z;
  }

  public boolean hasForegroundServices() {
    return this.mForegroundServices;
  }

  public void setForegroundServices(boolean z) {
    this.mForegroundServices = z;
  }

  public boolean isCurAllowListed() {
    return this.mCurAllowList;
  }

  public void setCurAllowListed(boolean z) {
    this.mCurAllowList = z;
  }

  public boolean isSetAllowListed() {
    return this.mSetAllowList;
  }

  public void setSetAllowListed(boolean z) {
    this.mSetAllowList = z;
  }

  public boolean isIdle() {
    return this.mIdle;
  }

  public void setIdle(boolean z) {
    this.mIdle = z;
  }

  public boolean isSetIdle() {
    return this.mSetIdle;
  }

  public void setSetIdle(boolean z) {
    this.mSetIdle = z;
  }

  public boolean getFGSFilterStatus() {
    return this.mFGSFilter;
  }

  public void setFGSFilterStatus(boolean z) {
    this.mFGSFilter = z;
  }

  public int getNumOfProcs() {
    return this.mProcRecords.size();
  }

  public void forEachProcess(Consumer consumer) {
    for (int size = this.mProcRecords.size() - 1; size >= 0; size--) {
      consumer.accept((ProcessRecord) this.mProcRecords.valueAt(size));
    }
  }

  public ProcessRecord getProcessRecordByIndex(int i) {
    return (ProcessRecord) this.mProcRecords.valueAt(i);
  }

  public ProcessRecord getProcessInPackage(String str) {
    for (int size = this.mProcRecords.size() - 1; size >= 0; size--) {
      ProcessRecord processRecord = (ProcessRecord) this.mProcRecords.valueAt(size);
      if (processRecord != null && TextUtils.equals(processRecord.info.packageName, str)) {
        return processRecord;
      }
    }
    return null;
  }

  public boolean areAllProcessesFrozen(ProcessRecord processRecord) {
    for (int size = this.mProcRecords.size() - 1; size >= 0; size--) {
      ProcessRecord processRecord2 = (ProcessRecord) this.mProcRecords.valueAt(size);
      ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord2.mOptRecord;
      if (processRecord != processRecord2 && !processCachedOptimizerRecord.isFrozen()) {
        return false;
      }
    }
    return true;
  }

  public boolean areAllProcessesFrozen() {
    return areAllProcessesFrozen(null);
  }

  public void setFrozen(boolean z) {
    this.mUidIsFrozen = z;
  }

  public boolean isFrozen() {
    return this.mUidIsFrozen;
  }

  public void addProcess(ProcessRecord processRecord) {
    this.mProcRecords.add(processRecord);
  }

  public void removeProcess(ProcessRecord processRecord) {
    this.mProcRecords.remove(processRecord);
  }

  public void setLastReportedChange(int i) {
    this.mLastReportedChange = i;
  }

  public void reset() {
    setCurProcState(19);
    this.mForegroundServices = false;
    this.mCurCapability = 0;
  }

  public void updateHasInternetPermission() {
    this.hasInternetPermission =
        ActivityManager.checkUidPermission("android.permission.INTERNET", this.mUid) == 0;
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
    long start = protoOutputStream.start(j);
    protoOutputStream.write(1120986464257L, this.mUid);
    protoOutputStream.write(1159641169922L, ProcessList.makeProcStateProtoEnum(this.mCurProcState));
    protoOutputStream.write(1133871366147L, this.mEphemeral);
    protoOutputStream.write(1133871366148L, this.mForegroundServices);
    protoOutputStream.write(1133871366149L, this.mCurAllowList);
    ProtoUtils.toDuration(
        protoOutputStream, 1146756268038L, this.mLastBackgroundTime, SystemClock.elapsedRealtime());
    protoOutputStream.write(1133871366151L, this.mIdle);
    int i = this.mLastReportedChange;
    if (i != 0) {
      ProtoUtils.writeBitWiseFlagsToProtoEnum(
          protoOutputStream, 2259152797704L, i, ORIG_ENUMS, PROTO_ENUMS);
    }
    protoOutputStream.write(1120986464265L, this.mNumProcs);
    long start2 = protoOutputStream.start(1146756268042L);
    protoOutputStream.write(1112396529665L, this.curProcStateSeq);
    protoOutputStream.write(1112396529666L, this.lastNetworkUpdatedProcStateSeq);
    protoOutputStream.end(start2);
    protoOutputStream.end(start);
  }

  public String toString() {
    boolean z;
    StringBuilder sb = new StringBuilder(128);
    sb.append("UidRecord{");
    sb.append(Integer.toHexString(System.identityHashCode(this)));
    sb.append(' ');
    UserHandle.formatUid(sb, this.mUid);
    sb.append(' ');
    sb.append(ProcessList.makeProcStateString(this.mCurProcState));
    if (this.mEphemeral) {
      sb.append(" ephemeral");
    }
    if (this.mForegroundServices) {
      sb.append(" fgServices");
    }
    if (this.mCurAllowList) {
      sb.append(" allowlist");
    }
    if (this.mLastBackgroundTime > 0) {
      sb.append(" bg:");
      TimeUtils.formatDuration(SystemClock.elapsedRealtime() - this.mLastBackgroundTime, sb);
    }
    if (this.mIdle) {
      sb.append(" idle");
    }
    if (this.mLastReportedChange != 0) {
      sb.append(" change:");
      boolean z2 = true;
      if ((this.mLastReportedChange & 1) != 0) {
        sb.append("gone");
        z = true;
      } else {
        z = false;
      }
      if ((this.mLastReportedChange & 2) != 0) {
        if (z) {
          sb.append("|");
        }
        sb.append("idle");
        z = true;
      }
      if ((this.mLastReportedChange & 4) != 0) {
        if (z) {
          sb.append("|");
        }
        sb.append("active");
        z = true;
      }
      if ((this.mLastReportedChange & 8) != 0) {
        if (z) {
          sb.append("|");
        }
        sb.append("cached");
      } else {
        z2 = z;
      }
      if ((this.mLastReportedChange & 16) != 0) {
        if (z2) {
          sb.append("|");
        }
        sb.append("uncached");
      }
      if ((this.mLastReportedChange & Integer.MIN_VALUE) != 0) {
        if (z2) {
          sb.append("|");
        }
        sb.append("procstate");
      }
      if ((this.mLastReportedChange & 64) != 0) {
        if (z2) {
          sb.append("|");
        }
        sb.append("procadj");
      }
    }
    sb.append(" procs:");
    sb.append(this.mNumProcs);
    sb.append(" seq(");
    sb.append(this.curProcStateSeq);
    sb.append(",");
    sb.append(this.lastNetworkUpdatedProcStateSeq);
    sb.append(")}");
    sb.append(" caps=");
    ActivityManager.printCapabilitiesSummary(sb, this.mCurCapability);
    return sb.toString();
  }
}
