package com.android.server.cpu;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.system.Os;
import android.system.OsConstants;
import android.util.IntArray;
import android.util.LongSparseLongArray;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.utils.Slogf;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CpuInfoReader {
    public File mCpuFreqDir;
    public final File mCpusetDir;
    public boolean mHasTimeInStateFile;
    public boolean mIsEnabled;
    public SparseArray mLastReadCpuInfos;
    public long mLastReadUptimeMillis;
    public final long mMinReadIntervalMillis;
    public File mProcStatFile;
    public static final Pattern PROC_STAT_PATTERN =
            Pattern.compile(
                    "cpu(?<core>[0-9]+)\\s(?<userClockTicks>[0-9]+)\\s(?<niceClockTicks>[0-9]+)\\s(?<sysClockTicks>[0-9]+)\\s(?<idleClockTicks>[0-9]+)\\s(?<iowaitClockTicks>[0-9]+)\\s(?<irqClockTicks>[0-9]+)\\s(?<softirqClockTicks>[0-9]+)\\s(?<stealClockTicks>[0-9]+)\\s(?<guestClockTicks>[0-9]+)\\s(?<guestNiceClockTicks>[0-9]+)");
    public static final Pattern TIME_IN_STATE_PATTERN =
            Pattern.compile("(?<freqKHz>[0-9]+)\\s(?<time>[0-9]+)");
    public static final long MILLIS_PER_CLOCK_TICK = 1000 / Os.sysconf(OsConstants._SC_CLK_TCK);
    public final SparseIntArray mCpusetCategoriesByCpus = new SparseIntArray();
    public final SparseArray mCpuFreqPolicyDirsById = new SparseArray();
    public final SparseArray mStaticPolicyInfoById = new SparseArray();
    public final SparseArray mTimeInStateByPolicyId = new SparseArray();
    public SparseArray mCumulativeCpuUsageStats = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CpuInfo {
        public final long avgTimeInStateCpuFreqKHz;
        public final int cpuCore;
        public final int cpusetCategories;
        public final long curCpuFreqKHz;
        public final boolean isOnline;
        public final CpuUsageStats latestCpuUsageStats;
        public final long mNormalizedAvailableCpuFreqKHz;
        public final long maxCpuFreqKHz;

        public CpuInfo(
                int i,
                int i2,
                boolean z,
                long j,
                long j2,
                long j3,
                long j4,
                CpuUsageStats cpuUsageStats) {
            this.cpuCore = i;
            this.cpusetCategories = i2;
            this.isOnline = z;
            this.curCpuFreqKHz = j;
            this.maxCpuFreqKHz = j2;
            this.avgTimeInStateCpuFreqKHz = j3;
            this.latestCpuUsageStats = cpuUsageStats;
            this.mNormalizedAvailableCpuFreqKHz = j4;
        }

        public CpuInfo(
                int i, int i2, boolean z, long j, long j2, long j3, CpuUsageStats cpuUsageStats) {
            this(i, i2, z, j, j2, j3, 0L, cpuUsageStats);
            long j4 = 0;
            if (this.isOnline) {
                CpuUsageStats cpuUsageStats2 = this.latestCpuUsageStats;
                long j5 =
                        cpuUsageStats2.userTimeMillis
                                + cpuUsageStats2.niceTimeMillis
                                + cpuUsageStats2.systemTimeMillis;
                long j6 = cpuUsageStats2.idleTimeMillis;
                long j7 =
                        j5
                                + j6
                                + cpuUsageStats2.iowaitTimeMillis
                                + cpuUsageStats2.irqTimeMillis
                                + cpuUsageStats2.softirqTimeMillis
                                + cpuUsageStats2.stealTimeMillis
                                + cpuUsageStats2.guestTimeMillis
                                + cpuUsageStats2.guestNiceTimeMillis;
                if (j7 == 0) {
                    boolean z2 = CpuMonitorService.DEBUG;
                    Slogf.wtf(
                            "CpuMonitorService",
                            "Total CPU time millis is 0. This shouldn't happen unless stats are"
                                + " polled too frequently");
                } else {
                    double d = j7;
                    double d2 = this.maxCpuFreqKHz;
                    j4 =
                            (long)
                                    (((100.0d
                                                            - (((((d - j6) * 100.0d) / d)
                                                                            * (this
                                                                                                    .avgTimeInStateCpuFreqKHz
                                                                                            == 0
                                                                                    ? this
                                                                                            .curCpuFreqKHz
                                                                                    : r3))
                                                                    / d2))
                                                    * d2)
                                            / 100.0d);
                }
            }
            this.mNormalizedAvailableCpuFreqKHz = j4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CpuInfo)) {
                return false;
            }
            CpuInfo cpuInfo = (CpuInfo) obj;
            return this.cpuCore == cpuInfo.cpuCore
                    && this.cpusetCategories == cpuInfo.cpusetCategories
                    && this.isOnline == cpuInfo.isOnline
                    && this.curCpuFreqKHz == cpuInfo.curCpuFreqKHz
                    && this.maxCpuFreqKHz == cpuInfo.maxCpuFreqKHz
                    && this.avgTimeInStateCpuFreqKHz == cpuInfo.avgTimeInStateCpuFreqKHz
                    && this.latestCpuUsageStats.equals(cpuInfo.latestCpuUsageStats)
                    && this.mNormalizedAvailableCpuFreqKHz
                            == cpuInfo.mNormalizedAvailableCpuFreqKHz;
        }

        public final int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.cpuCore),
                    Integer.valueOf(this.cpusetCategories),
                    Boolean.valueOf(this.isOnline),
                    Long.valueOf(this.curCpuFreqKHz),
                    Long.valueOf(this.maxCpuFreqKHz),
                    Long.valueOf(this.avgTimeInStateCpuFreqKHz),
                    this.latestCpuUsageStats,
                    Long.valueOf(this.mNormalizedAvailableCpuFreqKHz));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CpuInfo{ cpuCore = ");
            sb.append(this.cpuCore);
            sb.append(", cpusetCategories = [");
            sb.append(CpuInfoReader.toCpusetCategoriesStr(this.cpusetCategories));
            sb.append("], isOnline = ");
            sb.append(this.isOnline ? "Yes" : "No");
            sb.append(", curCpuFreqKHz = ");
            long j = this.curCpuFreqKHz;
            sb.append(j == 0 ? "missing" : Long.valueOf(j));
            sb.append(", maxCpuFreqKHz = ");
            long j2 = this.maxCpuFreqKHz;
            sb.append(j2 == 0 ? "missing" : Long.valueOf(j2));
            sb.append(", avgTimeInStateCpuFreqKHz = ");
            long j3 = this.avgTimeInStateCpuFreqKHz;
            sb.append(j3 != 0 ? Long.valueOf(j3) : "missing");
            sb.append(", latestCpuUsageStats = ");
            sb.append(this.latestCpuUsageStats);
            sb.append(", mNormalizedAvailableCpuFreqKHz = ");
            return AudioConfig$$ExternalSyntheticOutline0.m(
                    sb, this.mNormalizedAvailableCpuFreqKHz, " }");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CpuUsageStats {
        public final long guestNiceTimeMillis;
        public final long guestTimeMillis;
        public final long idleTimeMillis;
        public final long iowaitTimeMillis;
        public final long irqTimeMillis;
        public final long niceTimeMillis;
        public final long softirqTimeMillis;
        public final long stealTimeMillis;
        public final long systemTimeMillis;
        public final long userTimeMillis;

        public CpuUsageStats(
                long j,
                long j2,
                long j3,
                long j4,
                long j5,
                long j6,
                long j7,
                long j8,
                long j9,
                long j10) {
            this.userTimeMillis = j;
            this.niceTimeMillis = j2;
            this.systemTimeMillis = j3;
            this.idleTimeMillis = j4;
            this.iowaitTimeMillis = j5;
            this.irqTimeMillis = j6;
            this.softirqTimeMillis = j7;
            this.stealTimeMillis = j8;
            this.guestTimeMillis = j9;
            this.guestNiceTimeMillis = j10;
        }

        public static long diff(long j, long j2) {
            if (j > j2) {
                return j - j2;
            }
            return 0L;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CpuUsageStats)) {
                return false;
            }
            CpuUsageStats cpuUsageStats = (CpuUsageStats) obj;
            return this.userTimeMillis == cpuUsageStats.userTimeMillis
                    && this.niceTimeMillis == cpuUsageStats.niceTimeMillis
                    && this.systemTimeMillis == cpuUsageStats.systemTimeMillis
                    && this.idleTimeMillis == cpuUsageStats.idleTimeMillis
                    && this.iowaitTimeMillis == cpuUsageStats.iowaitTimeMillis
                    && this.irqTimeMillis == cpuUsageStats.irqTimeMillis
                    && this.softirqTimeMillis == cpuUsageStats.softirqTimeMillis
                    && this.stealTimeMillis == cpuUsageStats.stealTimeMillis
                    && this.guestTimeMillis == cpuUsageStats.guestTimeMillis
                    && this.guestNiceTimeMillis == cpuUsageStats.guestNiceTimeMillis;
        }

        public final int hashCode() {
            return Objects.hash(
                    Long.valueOf(this.userTimeMillis),
                    Long.valueOf(this.niceTimeMillis),
                    Long.valueOf(this.systemTimeMillis),
                    Long.valueOf(this.idleTimeMillis),
                    Long.valueOf(this.iowaitTimeMillis),
                    Long.valueOf(this.irqTimeMillis),
                    Long.valueOf(this.softirqTimeMillis),
                    Long.valueOf(this.stealTimeMillis),
                    Long.valueOf(this.guestTimeMillis),
                    Long.valueOf(this.guestNiceTimeMillis));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CpuUsageStats{ userTimeMillis = ");
            sb.append(this.userTimeMillis);
            sb.append(", niceTimeMillis = ");
            sb.append(this.niceTimeMillis);
            sb.append(", systemTimeMillis = ");
            sb.append(this.systemTimeMillis);
            sb.append(", idleTimeMillis = ");
            sb.append(this.idleTimeMillis);
            sb.append(", iowaitTimeMillis = ");
            sb.append(this.iowaitTimeMillis);
            sb.append(", irqTimeMillis = ");
            sb.append(this.irqTimeMillis);
            sb.append(", softirqTimeMillis = ");
            sb.append(this.softirqTimeMillis);
            sb.append(", stealTimeMillis = ");
            sb.append(this.stealTimeMillis);
            sb.append(", guestTimeMillis = ");
            sb.append(this.guestTimeMillis);
            sb.append(", guestNiceTimeMillis = ");
            return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.guestNiceTimeMillis, " }");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DynamicPolicyInfo {
        public final IntArray affectedCpuCores;
        public final long avgTimeInStateCpuFreqKHz;
        public final long curCpuFreqKHz;
        public final long maxCpuFreqKHz;

        public DynamicPolicyInfo(long j, long j2, long j3, IntArray intArray) {
            this.curCpuFreqKHz = j;
            this.maxCpuFreqKHz = j2;
            this.avgTimeInStateCpuFreqKHz = j3;
            this.affectedCpuCores = intArray;
        }

        public final String toString() {
            return "DynamicPolicyInfo{curCpuFreqKHz = "
                    + this.curCpuFreqKHz
                    + ", maxCpuFreqKHz = "
                    + this.maxCpuFreqKHz
                    + ", avgTimeInStateCpuFreqKHz = "
                    + this.avgTimeInStateCpuFreqKHz
                    + ", affectedCpuCores = "
                    + this.affectedCpuCores
                    + '}';
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StaticPolicyInfo {
        public final IntArray relatedCpuCores;

        public StaticPolicyInfo(IntArray intArray) {
            this.relatedCpuCores = intArray;
        }

        public final String toString() {
            return "StaticPolicyInfo{relatedCpuCores = " + this.relatedCpuCores + '}';
        }
    }

    public CpuInfoReader(File file, File file2, File file3, long j) {
        this.mCpusetDir = file;
        this.mCpuFreqDir = file2;
        this.mProcStatFile = file3;
        this.mMinReadIntervalMillis = j;
    }

    public static long calculateAvgCpuFreq(LongSparseLongArray longSparseLongArray) {
        double d = 0.0d;
        double d2 = 0.0d;
        for (int i = 0; i < longSparseLongArray.size(); i++) {
            d2 += longSparseLongArray.valueAt(i);
        }
        if (d2 == 0.0d) {
            return 0L;
        }
        for (int i2 = 0; i2 < longSparseLongArray.size(); i2++) {
            d += (longSparseLongArray.valueAt(i2) * longSparseLongArray.keyAt(i2)) / d2;
        }
        return (long) d;
    }

    public static long clockTickStrToMillis(String str) {
        return Long.parseLong(str) * MILLIS_PER_CLOCK_TICK;
    }

    public static IntArray readCpuCores(File file) {
        if (!file.exists()) {
            boolean z = CpuMonitorService.DEBUG;
            Slogf.e(
                    "CpuMonitorService",
                    "Failed to read CPU cores as the file '%s' doesn't exist",
                    file.getAbsolutePath());
            return null;
        }
        try {
            List<String> readAllLines = Files.readAllLines(file.toPath());
            IntArray intArray = new IntArray(0);
            for (int i = 0; i < readAllLines.size(); i++) {
                String trim = readAllLines.get(i).trim();
                if (!trim.isEmpty()) {
                    String[] split = trim.contains(",") ? trim.split(",") : trim.split(" ");
                    for (int i2 = 0; i2 < split.length; i2++) {
                        String[] split2 =
                                split[i2].split(PackageManagerShellCommandDataLoader.STDIN_PATH);
                        if (split2.length >= 2) {
                            int parseInt = Integer.parseInt(split2[0]);
                            int parseInt2 = Integer.parseInt(split2[1]);
                            if (parseInt <= parseInt2) {
                                while (parseInt <= parseInt2) {
                                    intArray.add(parseInt);
                                    parseInt++;
                                }
                            }
                        } else if (split2.length == 1) {
                            intArray.add(Integer.parseInt(split2[0]));
                        } else {
                            boolean z2 = CpuMonitorService.DEBUG;
                            Slogf.w(
                                    "CpuMonitorService",
                                    "Invalid CPU core range format %s",
                                    split[i2]);
                        }
                    }
                }
            }
            return intArray;
        } catch (NumberFormatException e) {
            boolean z3 = CpuMonitorService.DEBUG;
            Slogf.e(
                    "CpuMonitorService",
                    e,
                    "Failed to read CPU cores from %s due to incorrect file format",
                    file.getAbsolutePath());
            return null;
        } catch (Exception e2) {
            boolean z4 = CpuMonitorService.DEBUG;
            Slogf.e(
                    "CpuMonitorService",
                    e2,
                    "Failed to read CPU cores from %s",
                    file.getAbsolutePath());
            return null;
        }
    }

    public static long readCpuFreqKHz(File file) {
        if (!file.exists()) {
            boolean z = CpuMonitorService.DEBUG;
            Slogf.e(
                    "CpuMonitorService",
                    "CPU frequency file %s doesn't exist",
                    file.getAbsolutePath());
            return 0L;
        }
        try {
            List<String> readAllLines = Files.readAllLines(file.toPath());
            if (!readAllLines.isEmpty()) {
                long parseLong = Long.parseLong(readAllLines.get(0).trim());
                if (parseLong > 0) {
                    return parseLong;
                }
                return 0L;
            }
        } catch (Exception e) {
            boolean z2 = CpuMonitorService.DEBUG;
            Slogf.e(
                    "CpuMonitorService",
                    e,
                    "Failed to read integer content from file: %s",
                    file.getAbsolutePath());
        }
        return 0L;
    }

    public static String toCpusetCategoriesStr(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            sb.append("FLAG_CPUSET_CATEGORY_TOP_APP");
        }
        if ((i & 2) != 0) {
            if (sb.length() > 0) {
                sb.append('|');
            }
            sb.append("FLAG_CPUSET_CATEGORY_BACKGROUND");
        }
        return sb.toString();
    }

    public final void populateCpuFreqPolicyDirsById(File[] fileArr) {
        this.mCpuFreqPolicyDirsById.clear();
        for (File file : fileArr) {
            String substring = file.getName().substring(6);
            if (!substring.isEmpty()) {
                this.mCpuFreqPolicyDirsById.append(Integer.parseInt(substring), file);
                if (CpuMonitorService.DEBUG) {
                    Slogf.d(
                            "CpuMonitorService",
                            "Cached policy directory %s for policy id %s",
                            file,
                            substring);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:135:0x0423, code lost:

       if (r8.isOnline != false) goto L129;
    */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0322  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.util.SparseArray readCpuInfos() {
        /*
            Method dump skipped, instructions count: 1242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.cpu.CpuInfoReader.readCpuInfos():android.util.SparseArray");
    }

    public boolean setCpuFreqDir(File file) {
        File[] listFiles = file.listFiles(new CpuInfoReader$$ExternalSyntheticLambda0(0));
        if (listFiles == null || listFiles.length == 0) {
            boolean z = CpuMonitorService.DEBUG;
            Slogf.w(
                    "CpuMonitorService",
                    "Failed to set CPU frequency directory. Missing policy directories at %s",
                    file.getAbsolutePath());
            return false;
        }
        populateCpuFreqPolicyDirsById(listFiles);
        int size = this.mCpuFreqPolicyDirsById.size();
        int size2 = this.mStaticPolicyInfoById.size();
        if (size != 0 && size == size2) {
            this.mCpuFreqDir = file;
            return true;
        }
        boolean z2 = CpuMonitorService.DEBUG;
        Slogf.e(
                "CpuMonitorService",
                "Failed to set CPU frequency directory to %s. Total CPU frequency policies (%d)"
                    + " under new path is either 0 or not equal to initial total CPU frequency"
                    + " policies. Clearing CPU frequency policy directories",
                file.getAbsolutePath(),
                Integer.valueOf(size),
                Integer.valueOf(size2));
        this.mCpuFreqPolicyDirsById.clear();
        return false;
    }

    public boolean setProcStatFile(File file) {
        if (file.exists()) {
            this.mProcStatFile = file;
            return true;
        }
        boolean z = CpuMonitorService.DEBUG;
        Slogf.e("CpuMonitorService", "Missing proc stat file at %s", file.getAbsolutePath());
        return false;
    }
}
