package com.android.server.am;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.TimeUtils;

import dalvik.annotation.optimization.NeverCompile;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BroadcastStats {
    public static final AnonymousClass1 ACTIONS_COMPARATOR = new AnonymousClass1();
    public long mEndRealtime;
    public long mEndUptime;
    public final ArrayMap mActions = new ArrayMap();
    public final long mStartRealtime = SystemClock.elapsedRealtime();
    public final long mStartUptime = SystemClock.uptimeMillis();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.am.BroadcastStats$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            long j = ((ActionEntry) obj).mTotalDispatchTime;
            long j2 = ((ActionEntry) obj2).mTotalDispatchTime;
            if (j < j2) {
                return -1;
            }
            return j > j2 ? 1 : 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageEntry {
        public int mSendCount;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ViolationEntry {
        public int mCount;
    }

    @NeverCompile
    public final void dumpCheckinStats(PrintWriter printWriter, String str) {
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
                for (int size3 = actionEntry.mBackgroundCheckViolations.size() - 1;
                        size3 >= 0;
                        size3--) {
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

    @NeverCompile
    public final boolean dumpStats(PrintWriter printWriter, String str) {
        ArrayList arrayList = new ArrayList(this.mActions.size());
        for (int size = this.mActions.size() - 1; size >= 0; size--) {
            arrayList.add((ActionEntry) this.mActions.valueAt(size));
        }
        Collections.sort(arrayList, ACTIONS_COMPARATOR);
        boolean z = false;
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            ActionEntry actionEntry = (ActionEntry) arrayList.get(size2);
            if (str == null || actionEntry.mPackages.containsKey(str)) {
                printWriter.print("    ");
                printWriter.print(actionEntry.mAction);
                printWriter.println(":");
                printWriter.print("    ");
                printWriter.print("  Number received: ");
                printWriter.print(actionEntry.mReceiveCount);
                printWriter.print(", skipped: ");
                BroadcastStats$$ExternalSyntheticOutline0.m(
                        actionEntry.mSkipCount, printWriter, "    ", "  Total dispatch time: ");
                TimeUtils.formatDuration(actionEntry.mTotalDispatchTime, printWriter);
                printWriter.print(", max: ");
                TimeUtils.formatDuration(actionEntry.mMaxDispatchTime, printWriter);
                printWriter.println();
                for (int size3 = actionEntry.mPackages.size() - 1; size3 >= 0; size3--) {
                    printWriter.print("    ");
                    printWriter.print("  Package ");
                    printWriter.print((String) actionEntry.mPackages.keyAt(size3));
                    printWriter.print(": ");
                    printWriter.print(
                            ((PackageEntry) actionEntry.mPackages.valueAt(size3)).mSendCount);
                    printWriter.println(" times");
                }
                for (int size4 = actionEntry.mBackgroundCheckViolations.size() - 1;
                        size4 >= 0;
                        size4--) {
                    printWriter.print("    ");
                    printWriter.print("  Bg Check Violation ");
                    printWriter.print((String) actionEntry.mBackgroundCheckViolations.keyAt(size4));
                    printWriter.print(": ");
                    printWriter.print(
                            ((ViolationEntry) actionEntry.mBackgroundCheckViolations.valueAt(size4))
                                    .mCount);
                    printWriter.println(" times");
                }
                z = true;
            }
        }
        return z;
    }
}
