package com.android.server.stats.pull;

import android.os.Process;
import android.util.SparseArray;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

public abstract class ProcfsMemoryUtil {
    public static final int[] CMDLINE_OUT = {4096};
    public static final String[] STATUS_KEYS = {
        "Uid:", "VmHWM:", "VmRSS:", "RssAnon:", "RssShmem:", "VmSwap:"
    };
    public static final String[] VMSTAT_KEYS = {"oom_kill"};

    public final class MemorySnapshot {
        public int anonRssInKilobytes;
        public int rssHighWaterMarkInKilobytes;
        public int rssInKilobytes;
        public int rssShmemKilobytes;
        public int swapInKilobytes;
        public int uid;
    }

    public final class VmStat {
        public int oomKillCount;
    }

    public static SparseArray getProcessCmdlines() {
        int[] pids = Process.getPids("/proc", new int[1024]);
        SparseArray sparseArray = new SparseArray(pids.length);
        for (int i : pids) {
            if (i < 0) {
                break;
            }
            String readCmdlineFromProcfs = readCmdlineFromProcfs(i);
            if (!readCmdlineFromProcfs.isEmpty()) {
                sparseArray.append(i, readCmdlineFromProcfs);
            }
        }
        return sparseArray;
    }

    public static String readCmdlineFromProcfs(int i) {
        String[] strArr = new String[1];
        return !Process.readProcFile(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i, "/proc/", "/cmdline"),
                        CMDLINE_OUT,
                        strArr,
                        null,
                        null)
                ? ""
                : strArr[0];
    }

    public static MemorySnapshot readMemorySnapshotFromProcfs(int i) {
        long[] jArr = {-1, 0, 0, -1, -1, -1};
        Process.readProcLines(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "/proc/", "/status"),
                STATUS_KEYS,
                jArr);
        long j = jArr[0];
        if (j == -1) {
            return null;
        }
        long j2 = jArr[3];
        if (j2 == -1) {
            return null;
        }
        long j3 = jArr[4];
        if (j3 == -1) {
            return null;
        }
        long j4 = jArr[5];
        if (j4 == -1) {
            return null;
        }
        MemorySnapshot memorySnapshot = new MemorySnapshot();
        memorySnapshot.uid = (int) j;
        memorySnapshot.rssHighWaterMarkInKilobytes = (int) jArr[1];
        memorySnapshot.rssInKilobytes = (int) jArr[2];
        memorySnapshot.anonRssInKilobytes = (int) j2;
        memorySnapshot.rssShmemKilobytes = (int) j3;
        memorySnapshot.swapInKilobytes = (int) j4;
        return memorySnapshot;
    }
}
