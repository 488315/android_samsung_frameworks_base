package com.android.internal.os;

import android.os.Process;
import android.util.SparseArray;

/* loaded from: classes5.dex */
public final class ProcfsMemoryUtil {
    private static final int[] CMDLINE_OUT = {4096};
    private static final int[] OOM_SCORE_ADJ_OUT = {8202};
    private static final String[] STATUS_KEYS = {"Uid:", "VmHWM:", "VmRSS:", "RssAnon:", "RssShmem:", "VmSwap:"};
    private static final String[] VMSTAT_KEYS = {"oom_kill"};

    public static final class MemorySnapshot {
        public int anonRssInKilobytes;
        public int rssHighWaterMarkInKilobytes;
        public int rssInKilobytes;
        public int rssShmemKilobytes;
        public int swapInKilobytes;
        public int uid;
    }

    public static final class VmStat {
        public int oomKillCount;
    }

    private ProcfsMemoryUtil() {
    }

    public static MemorySnapshot readMemorySnapshotFromProcfs(int i) {
        return readMemorySnapshotFromProcfs("/proc/" + i + "/status");
    }

    public static MemorySnapshot readMemorySnapshotFromProcfs() {
        return readMemorySnapshotFromProcfs("/proc/self/status");
    }

    private static MemorySnapshot readMemorySnapshotFromProcfs(String str) {
        String[] strArr = STATUS_KEYS;
        long[] jArr = new long[strArr.length];
        jArr[0] = -1;
        jArr[3] = -1;
        jArr[4] = -1;
        jArr[5] = -1;
        Process.readProcLines(str, strArr, jArr);
        if (jArr[0] == -1 || jArr[3] == -1 || jArr[4] == -1 || jArr[5] == -1) {
            return null;
        }
        MemorySnapshot memorySnapshot = new MemorySnapshot();
        memorySnapshot.uid = (int) jArr[0];
        memorySnapshot.rssHighWaterMarkInKilobytes = (int) jArr[1];
        memorySnapshot.rssInKilobytes = (int) jArr[2];
        memorySnapshot.anonRssInKilobytes = (int) jArr[3];
        memorySnapshot.rssShmemKilobytes = (int) jArr[4];
        memorySnapshot.swapInKilobytes = (int) jArr[5];
        return memorySnapshot;
    }

    public static String readCmdlineFromProcfs(int i) {
        return readCmdlineFromProcfs("/proc/" + i + "/cmdline");
    }

    public static String readCmdlineFromProcfs() {
        return readCmdlineFromProcfs("/proc/self/cmdline");
    }

    private static String readCmdlineFromProcfs(String str) {
        String[] strArr = new String[1];
        if (!Process.readProcFile(str, CMDLINE_OUT, strArr, null, null)) {
            return "";
        }
        return strArr[0];
    }

    public static int readOomScoreAdjFromProcfs(int i) {
        return readOomScoreAdjFromProcfs("/proc/" + i + "/oom_score_adj");
    }

    public static int readOomScoreAdjFromProcfs() {
        return readOomScoreAdjFromProcfs("/proc/self/oom_score_adj");
    }

    private static int readOomScoreAdjFromProcfs(String str) {
        long[] jArr = new long[1];
        if (Process.readProcFile(str, OOM_SCORE_ADJ_OUT, null, jArr, null)) {
            return (int) jArr[0];
        }
        return 0;
    }

    public static SparseArray<String> getProcessCmdlines() {
        int[] pids = Process.getPids("/proc", new int[1024]);
        SparseArray<String> sparseArray = new SparseArray<>(pids.length);
        for (int i : pids) {
            if (i < 0) {
                break;
            }
            String cmdlineFromProcfs = readCmdlineFromProcfs(i);
            if (!cmdlineFromProcfs.isEmpty()) {
                sparseArray.append(i, cmdlineFromProcfs);
            }
        }
        return sparseArray;
    }

    public static VmStat readVmStat() {
        String[] strArr = VMSTAT_KEYS;
        long[] jArr = new long[strArr.length];
        jArr[0] = -1;
        Process.readProcLines("/proc/vmstat", strArr, jArr);
        if (jArr[0] == -1) {
            return null;
        }
        VmStat vmStat = new VmStat();
        vmStat.oomKillCount = (int) jArr[0];
        return vmStat;
    }
}
