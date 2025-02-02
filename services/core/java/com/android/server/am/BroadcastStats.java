package com.android.server.am;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.TimeUtils;
import com.android.internal.util.jobs.XmlUtils;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* loaded from: classes.dex */
public final class BroadcastStats {
  public static final Comparator ACTIONS_COMPARATOR =
      new Comparator() { // from class: com.android.server.am.BroadcastStats.1
        @Override // java.util.Comparator
        public int compare(ActionEntry actionEntry, ActionEntry actionEntry2) {
          long j = actionEntry.mTotalDispatchTime;
          long j2 = actionEntry2.mTotalDispatchTime;
          if (j < j2) {
            return -1;
          }
          return j > j2 ? 1 : 0;
        }
      };
  public long mEndRealtime;
  public long mEndUptime;
  public final ArrayMap mActions = new ArrayMap();
  public final long mStartRealtime = SystemClock.elapsedRealtime();
  public final long mStartUptime = SystemClock.uptimeMillis();

  public final class PackageEntry {
    public int mSendCount;
  }

  public final class ViolationEntry {
    public int mCount;
  }

  public final class ActionEntry {
    public final String mAction;
    public long mMaxDispatchTime;
    public int mReceiveCount;
    public int mSkipCount;
    public long mTotalDispatchTime;
    public final ArrayMap mPackages = new ArrayMap();
    public final ArrayMap mBackgroundCheckViolations = new ArrayMap();

    public ActionEntry(String str) {
      this.mAction = str;
    }
  }

  public void addBroadcast(String str, String str2, int i, int i2, long j) {
    ActionEntry actionEntry = (ActionEntry) this.mActions.get(str);
    if (actionEntry == null) {
      actionEntry = new ActionEntry(str);
      this.mActions.put(str, actionEntry);
    }
    actionEntry.mReceiveCount += i;
    actionEntry.mSkipCount += i2;
    actionEntry.mTotalDispatchTime += j;
    if (actionEntry.mMaxDispatchTime < j) {
      actionEntry.mMaxDispatchTime = j;
    }
    PackageEntry packageEntry = (PackageEntry) actionEntry.mPackages.get(str2);
    if (packageEntry == null) {
      packageEntry = new PackageEntry();
      actionEntry.mPackages.put(str2, packageEntry);
    }
    packageEntry.mSendCount++;
  }

  public void addBackgroundCheckViolation(String str, String str2) {
    ActionEntry actionEntry = (ActionEntry) this.mActions.get(str);
    if (actionEntry == null) {
      actionEntry = new ActionEntry(str);
      this.mActions.put(str, actionEntry);
    }
    ViolationEntry violationEntry =
        (ViolationEntry) actionEntry.mBackgroundCheckViolations.get(str2);
    if (violationEntry == null) {
      violationEntry = new ViolationEntry();
      actionEntry.mBackgroundCheckViolations.put(str2, violationEntry);
    }
    violationEntry.mCount++;
  }

  @NeverCompile
  public boolean dumpStats(PrintWriter printWriter, String str, String str2) {
    ArrayList arrayList = new ArrayList(this.mActions.size());
    for (int size = this.mActions.size() - 1; size >= 0; size--) {
      arrayList.add((ActionEntry) this.mActions.valueAt(size));
    }
    Collections.sort(arrayList, ACTIONS_COMPARATOR);
    boolean z = false;
    for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
      ActionEntry actionEntry = (ActionEntry) arrayList.get(size2);
      if (str2 == null || actionEntry.mPackages.containsKey(str2)) {
        printWriter.print(str);
        printWriter.print(actionEntry.mAction);
        printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
        printWriter.print(str);
        printWriter.print("  Number received: ");
        printWriter.print(actionEntry.mReceiveCount);
        printWriter.print(", skipped: ");
        printWriter.println(actionEntry.mSkipCount);
        printWriter.print(str);
        printWriter.print("  Total dispatch time: ");
        TimeUtils.formatDuration(actionEntry.mTotalDispatchTime, printWriter);
        printWriter.print(", max: ");
        TimeUtils.formatDuration(actionEntry.mMaxDispatchTime, printWriter);
        printWriter.println();
        for (int size3 = actionEntry.mPackages.size() - 1; size3 >= 0; size3--) {
          printWriter.print(str);
          printWriter.print("  Package ");
          printWriter.print((String) actionEntry.mPackages.keyAt(size3));
          printWriter.print(": ");
          printWriter.print(((PackageEntry) actionEntry.mPackages.valueAt(size3)).mSendCount);
          printWriter.println(" times");
        }
        for (int size4 = actionEntry.mBackgroundCheckViolations.size() - 1; size4 >= 0; size4--) {
          printWriter.print(str);
          printWriter.print("  Bg Check Violation ");
          printWriter.print((String) actionEntry.mBackgroundCheckViolations.keyAt(size4));
          printWriter.print(": ");
          printWriter.print(
              ((ViolationEntry) actionEntry.mBackgroundCheckViolations.valueAt(size4)).mCount);
          printWriter.println(" times");
        }
        z = true;
      }
    }
    return z;
  }

  @NeverCompile
  public void dumpCheckinStats(PrintWriter printWriter, String str) {
    printWriter.print("broadcast-stats,1,");
    printWriter.print(this.mStartRealtime);
    printWriter.print(",");
    long j = this.mEndRealtime;
    if (j == 0) {
      j = SystemClock.elapsedRealtime();
    }
    printWriter.print(j);
    printWriter.print(",");
    long j2 = this.mEndUptime;
    if (j2 == 0) {
      j2 = SystemClock.uptimeMillis();
    }
    printWriter.println(j2 - this.mStartUptime);
    for (int size = this.mActions.size() - 1; size >= 0; size--) {
      ActionEntry actionEntry = (ActionEntry) this.mActions.valueAt(size);
      if (str == null || actionEntry.mPackages.containsKey(str)) {
        printWriter.print("a,");
        printWriter.print((String) this.mActions.keyAt(size));
        printWriter.print(",");
        printWriter.print(actionEntry.mReceiveCount);
        printWriter.print(",");
        printWriter.print(actionEntry.mSkipCount);
        printWriter.print(",");
        printWriter.print(actionEntry.mTotalDispatchTime);
        printWriter.print(",");
        printWriter.print(actionEntry.mMaxDispatchTime);
        printWriter.println();
        for (int size2 = actionEntry.mPackages.size() - 1; size2 >= 0; size2--) {
          printWriter.print("p,");
          printWriter.print((String) actionEntry.mPackages.keyAt(size2));
          PackageEntry packageEntry = (PackageEntry) actionEntry.mPackages.valueAt(size2);
          printWriter.print(",");
          printWriter.print(packageEntry.mSendCount);
          printWriter.println();
        }
        for (int size3 = actionEntry.mBackgroundCheckViolations.size() - 1; size3 >= 0; size3--) {
          printWriter.print("v,");
          printWriter.print((String) actionEntry.mBackgroundCheckViolations.keyAt(size3));
          ViolationEntry violationEntry =
              (ViolationEntry) actionEntry.mBackgroundCheckViolations.valueAt(size3);
          printWriter.print(",");
          printWriter.print(violationEntry.mCount);
          printWriter.println();
        }
      }
    }
  }
}
