package com.android.server.am;

import android.util.ArrayMap;
import android.util.TimeUtils;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class ProcessProviderRecord {
  public final ProcessRecord mApp;
  public long mLastProviderTime;
  public final ActivityManagerService mService;
  public final ArrayMap mPubProviders = new ArrayMap();
  public final ArrayList mConProviders = new ArrayList();

  public long getLastProviderTime() {
    return this.mLastProviderTime;
  }

  public void setLastProviderTime(long j) {
    this.mLastProviderTime = j;
  }

  public boolean hasProvider(String str) {
    return this.mPubProviders.containsKey(str);
  }

  public ContentProviderRecord getProvider(String str) {
    return (ContentProviderRecord) this.mPubProviders.get(str);
  }

  public int numberOfProviders() {
    return this.mPubProviders.size();
  }

  public ContentProviderRecord getProviderAt(int i) {
    return (ContentProviderRecord) this.mPubProviders.valueAt(i);
  }

  public void installProvider(String str, ContentProviderRecord contentProviderRecord) {
    this.mPubProviders.put(str, contentProviderRecord);
  }

  public void ensureProviderCapacity(int i) {
    this.mPubProviders.ensureCapacity(i);
  }

  public int numberOfProviderConnections() {
    return this.mConProviders.size();
  }

  public ContentProviderConnection getProviderConnectionAt(int i) {
    return (ContentProviderConnection) this.mConProviders.get(i);
  }

  public void addProviderConnection(ContentProviderConnection contentProviderConnection) {
    this.mConProviders.add(contentProviderConnection);
  }

  public boolean removeProviderConnection(ContentProviderConnection contentProviderConnection) {
    return this.mConProviders.remove(contentProviderConnection);
  }

  public ProcessProviderRecord(ProcessRecord processRecord) {
    this.mApp = processRecord;
    this.mService = processRecord.mService;
  }

  public boolean onCleanupApplicationRecordLocked(boolean z) {
    boolean z2 = false;
    for (int size = this.mPubProviders.size() - 1; size >= 0; size--) {
      ContentProviderRecord contentProviderRecord =
          (ContentProviderRecord) this.mPubProviders.valueAt(size);
      ProcessRecord processRecord = contentProviderRecord.proc;
      ProcessRecord processRecord2 = this.mApp;
      if (processRecord == processRecord2) {
        boolean z3 = processRecord2.mErrorState.isBad() || !z;
        boolean removeDyingProviderLocked =
            this.mService.mCpHelper.removeDyingProviderLocked(this.mApp, contentProviderRecord, z3);
        if (!z3 && removeDyingProviderLocked && contentProviderRecord.hasConnectionOrHandle()) {
          z2 = true;
        }
        contentProviderRecord.provider = null;
        contentProviderRecord.setProcess(null);
      }
    }
    this.mPubProviders.clear();
    if (this.mService.mCpHelper.cleanupAppInLaunchingProvidersLocked(this.mApp, false)) {
      this.mService.mProcessList.noteProcessDiedLocked(this.mApp);
      z2 = true;
    }
    if (!this.mConProviders.isEmpty()) {
      for (int size2 = this.mConProviders.size() - 1; size2 >= 0; size2--) {
        ContentProviderConnection contentProviderConnection =
            (ContentProviderConnection) this.mConProviders.get(size2);
        contentProviderConnection.provider.connections.remove(contentProviderConnection);
        ActivityManagerService activityManagerService = this.mService;
        ProcessRecord processRecord3 = this.mApp;
        int i = processRecord3.uid;
        String str = processRecord3.processName;
        ContentProviderRecord contentProviderRecord2 = contentProviderConnection.provider;
        activityManagerService.stopAssociationLocked(
            i,
            str,
            contentProviderRecord2.uid,
            contentProviderRecord2.appInfo.longVersionCode,
            contentProviderRecord2.name,
            contentProviderRecord2.info.processName);
      }
      this.mConProviders.clear();
    }
    return z2;
  }

  public void dump(PrintWriter printWriter, String str, long j) {
    if (this.mLastProviderTime > 0) {
      printWriter.print(str);
      printWriter.print("lastProviderTime=");
      TimeUtils.formatDuration(this.mLastProviderTime, j, printWriter);
      printWriter.println();
    }
    if (this.mPubProviders.size() > 0) {
      printWriter.print(str);
      printWriter.println("Published Providers:");
      int size = this.mPubProviders.size();
      for (int i = 0; i < size; i++) {
        printWriter.print(str);
        printWriter.print("  - ");
        printWriter.println((String) this.mPubProviders.keyAt(i));
        printWriter.print(str);
        printWriter.print("    -> ");
        printWriter.println(this.mPubProviders.valueAt(i));
      }
    }
    if (this.mConProviders.size() > 0) {
      printWriter.print(str);
      printWriter.println("Connected Providers:");
      int size2 = this.mConProviders.size();
      for (int i2 = 0; i2 < size2; i2++) {
        printWriter.print(str);
        printWriter.print("  - ");
        printWriter.println(
            ((ContentProviderConnection) this.mConProviders.get(i2)).toShortString());
      }
    }
  }
}
