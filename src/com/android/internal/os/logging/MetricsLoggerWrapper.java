package com.android.internal.os.logging;

import android.os.Process;
import com.android.internal.os.ProcfsMemoryUtil;
import com.android.internal.util.FrameworkStatsLog;
import com.android.libcore.readonly.Flags;
import java.util.Collection;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes5.dex */
public class MetricsLoggerWrapper {
    public static void logAppOverlayEnter(int i, String str, boolean z, int i2, boolean z2) {
        if (z) {
            if (i2 != 2038) {
                FrameworkStatsLog.write(59, i, str, true, 1);
            } else {
                if (z2) {
                    return;
                }
                FrameworkStatsLog.write(59, i, str, false, 1);
            }
        }
    }

    public static void logAppOverlayExit(int i, String str, boolean z, int i2, boolean z2) {
        if (z) {
            if (i2 != 2038) {
                FrameworkStatsLog.write(59, i, str, true, 2);
            } else {
                if (z2) {
                    return;
                }
                FrameworkStatsLog.write(59, i, str, false, 2);
            }
        }
    }

    public static void logPostGcMemorySnapshot() {
        if (Flags.nativeMetrics()) {
            int iMyPid = Process.myPid();
            String strMyProcessName = Process.myProcessName();
            Collection<NativeAllocationRegistry.Metrics> metrics = NativeAllocationRegistry.getMetrics();
            int size = metrics.size();
            String[] strArr = new String[size];
            long[] jArr = new long[size];
            long[] jArr2 = new long[size];
            long[] jArr3 = new long[size];
            long[] jArr4 = new long[size];
            int i = 0;
            for (NativeAllocationRegistry.Metrics metrics2 : metrics) {
                strArr[i] = metrics2.getClassName();
                jArr[i] = metrics2.getMallocedCount();
                jArr2[i] = metrics2.getMallocedBytes();
                jArr3[i] = metrics2.getNonmallocedCount();
                jArr4[i] = metrics2.getNonmallocedBytes();
                i++;
            }
            ProcfsMemoryUtil.MemorySnapshot memorySnapshotFromProcfs = ProcfsMemoryUtil.readMemorySnapshotFromProcfs();
            FrameworkStatsLog.write(924, memorySnapshotFromProcfs.uid, strMyProcessName, iMyPid, ProcfsMemoryUtil.readOomScoreAdjFromProcfs(), memorySnapshotFromProcfs.rssInKilobytes, memorySnapshotFromProcfs.anonRssInKilobytes, memorySnapshotFromProcfs.swapInKilobytes, memorySnapshotFromProcfs.anonRssInKilobytes + memorySnapshotFromProcfs.swapInKilobytes, strArr, jArr, jArr2, jArr3, jArr4);
        }
    }
}
