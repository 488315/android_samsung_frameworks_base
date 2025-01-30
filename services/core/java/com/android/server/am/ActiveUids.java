package com.android.server.am;

import android.app.ActivityManager;
import android.os.UserHandle;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class ActiveUids {
  public final SparseArray mActiveUids = new SparseArray();
  public final boolean mPostChangesToAtm;
  public final ActivityManagerGlobalLock mProcLock;
  public final ActivityManagerService mService;

  public ActiveUids(ActivityManagerService activityManagerService, boolean z) {
    this.mService = activityManagerService;
    this.mProcLock = activityManagerService != null ? activityManagerService.mProcLock : null;
    this.mPostChangesToAtm = z;
  }

  public void put(int i, UidRecord uidRecord) {
    this.mActiveUids.put(i, uidRecord);
    if (this.mPostChangesToAtm) {
      this.mService.mAtmInternal.onUidActive(i, uidRecord.getCurProcState());
    }
  }

  public void remove(int i) {
    this.mActiveUids.remove(i);
    if (this.mPostChangesToAtm) {
      this.mService.mAtmInternal.onUidInactive(i);
    }
  }

  public void clear() {
    this.mActiveUids.clear();
  }

  public UidRecord get(int i) {
    return (UidRecord) this.mActiveUids.get(i);
  }

  public int size() {
    return this.mActiveUids.size();
  }

  public UidRecord valueAt(int i) {
    return (UidRecord) this.mActiveUids.valueAt(i);
  }

  public int keyAt(int i) {
    return this.mActiveUids.keyAt(i);
  }

  public Map getAllUidRecords() {
    HashMap hashMap = new HashMap();
    for (int i = 0; i < this.mActiveUids.size(); i++) {
      UidRecord uidRecord = (UidRecord) this.mActiveUids.valueAt(i);
      hashMap.put(Integer.valueOf(uidRecord.getUid()), Boolean.valueOf(uidRecord.isIdle()));
    }
    return hashMap;
  }

  public boolean dump(final PrintWriter printWriter, String str, int i, String str2, boolean z) {
    boolean z2 = false;
    for (int i2 = 0; i2 < this.mActiveUids.size(); i2++) {
      UidRecord uidRecord = (UidRecord) this.mActiveUids.valueAt(i2);
      if (str == null || UserHandle.getAppId(uidRecord.getUid()) == i) {
        if (!z2) {
          if (z) {
            printWriter.println();
          }
          printWriter.print("  ");
          printWriter.println(str2);
          z2 = true;
        }
        printWriter.print("    UID ");
        UserHandle.formatUid(printWriter, uidRecord.getUid());
        printWriter.print(": ");
        printWriter.println(uidRecord);
        printWriter.print("      curProcState=");
        printWriter.print(uidRecord.getCurProcState());
        printWriter.print(" curCapability=");
        ActivityManager.printCapabilitiesFull(printWriter, uidRecord.getCurCapability());
        printWriter.println();
        uidRecord.forEachProcess(
            new Consumer() { // from class:
                             // com.android.server.am.ActiveUids$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ActiveUids.lambda$dump$0(printWriter, (ProcessRecord) obj);
              }
            });
      }
    }
    return z2;
  }

  public static /* synthetic */ void lambda$dump$0(
      PrintWriter printWriter, ProcessRecord processRecord) {
    printWriter.print("      proc=");
    printWriter.println(processRecord);
  }

  public void dumpProto(ProtoOutputStream protoOutputStream, String str, int i, long j) {
    for (int i2 = 0; i2 < this.mActiveUids.size(); i2++) {
      UidRecord uidRecord = (UidRecord) this.mActiveUids.valueAt(i2);
      if (str == null || UserHandle.getAppId(uidRecord.getUid()) == i) {
        uidRecord.dumpDebug(protoOutputStream, j);
      }
    }
  }
}
