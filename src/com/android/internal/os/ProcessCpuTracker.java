package com.android.internal.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.BatteryStats;
import android.os.Process;
import android.os.StrictMode;
import android.os.SystemClock;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.FastPrintWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class ProcessCpuTracker {
    private static final boolean DEBUG = false;
    static final int PROCESS_FULL_STAT_MAJOR_FAULTS = 2;
    static final int PROCESS_FULL_STAT_MINOR_FAULTS = 1;
    static final int PROCESS_FULL_STAT_STIME = 4;
    static final int PROCESS_FULL_STAT_UTIME = 3;
    static final int PROCESS_FULL_STAT_VSIZE = 5;
    static final int PROCESS_SCHEDSTAT_CPU_DELAY_TIME = 1;
    static final int PROCESS_SCHEDSTAT_CPU_TIME = 0;
    static final int PROCESS_STAT_MAJOR_FAULTS = 1;
    static final int PROCESS_STAT_MINOR_FAULTS = 0;
    static final int PROCESS_STAT_STIME = 3;
    static final int PROCESS_STAT_UTIME = 2;
    private static final String TAG = "ProcessCpuTracker";
    private static final boolean localLOGV = false;
    private long mBaseIdleTime;
    private long mBaseIoWaitTime;
    private long mBaseIrqTime;
    private long mBaseSoftIrqTime;
    private long mBaseSystemTime;
    private long mBaseUserTime;
    private int[] mCurPids;
    private int[] mCurThreadPids;
    private long mCurrentSampleRealTime;
    private long mCurrentSampleTime;
    private long mCurrentSampleWallTime;
    private final boolean mIncludeThreads;
    private long mLastSampleRealTime;
    private long mLastSampleTime;
    private long mLastSampleWallTime;
    private int mRelIdleTime;
    private int mRelIoWaitTime;
    private int mRelIrqTime;
    private int mRelSoftIrqTime;
    private boolean mRelStatsAreGood;
    private int mRelSystemTime;
    private int mRelUserTime;
    private boolean mWorkingProcsSorted;
    private static final int[] PROCESS_STATS_FORMAT = {32, 544, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224};
    private static final int[] PROCESS_FULL_STATS_FORMAT = {32, 4640, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224, 32, 32, 32, 32, 32, 32, 32, 8224};
    private static final int[] PROCESS_SCHEDSTATS_FORMAT = {8224, 8224};
    private static final int[] SYSTEM_CPU_FORMAT = {288, 8224, 8224, 8224, 8224, 8224, 8224, 8224};
    private static final int[] LOAD_AVERAGE_FORMAT = {16416, 16416, 16416};
    private static final Comparator<Stats> sLoadComparator = new Comparator<Stats>() { // from class: com.android.internal.os.ProcessCpuTracker.1
        @Override // java.util.Comparator
        public final int compare(Stats stats, Stats stats2) {
            int i = stats.rel_utime + stats.rel_stime;
            int i2 = stats2.rel_utime + stats2.rel_stime;
            if (i != i2) {
                return i > i2 ? -1 : 1;
            }
            if (stats.added != stats2.added) {
                return stats.added ? -1 : 1;
            }
            if (stats.removed != stats2.removed) {
                return stats.added ? -1 : 1;
            }
            return 0;
        }
    };
    private final long[] mProcessStatsData = new long[4];
    private final String[] mProcessFullStatsStringData = new String[6];
    private final long[] mProcessFullStatsData = new long[6];
    private final long[] mSystemCpuData = new long[7];
    private final float[] mLoadAverageData = new float[3];
    private float mLoad1 = 0.0f;
    private float mLoad5 = 0.0f;
    private float mLoad15 = 0.0f;
    private final ArrayList<Stats> mProcStats = new ArrayList<>();
    private final ArrayList<Stats> mWorkingProcs = new ArrayList<>();
    private boolean mFirst = true;
    private final long mJiffyMillis = 1000 / Os.sysconf(OsConstants._SC_CLK_TCK);

    public interface FilterStats {
        boolean needed(Stats stats);
    }

    public void onLoadChanged(float f, float f2, float f3) {
    }

    public int onMeasureProcessName(String str) {
        return 0;
    }

    public static class Stats {
        public boolean active;
        public boolean added;
        public String baseName;
        public long base_majfaults;
        public long base_minfaults;
        public long base_stime;
        public long base_uptime;
        public long base_utime;
        public BatteryStats.Uid.Proc batteryStats;
        final String cmdlineFile;
        public boolean interesting;
        public String name;
        public int nameWidth;
        public final int pid;
        public int rel_majfaults;
        public int rel_minfaults;
        public int rel_stime;
        public long rel_uptime;
        public int rel_utime;
        public boolean removed;
        final String statFile;
        final ArrayList<Stats> threadStats;
        final String threadsDir;
        public final int uid;
        public long vsize;
        public boolean working;
        final ArrayList<Stats> workingThreads;

        Stats(int i, int i2, boolean z) {
            this.pid = i;
            if (i2 < 0) {
                File file = new File("/proc", Integer.toString(i));
                this.uid = getUid(file.toString());
                this.statFile = new File(file, "stat").toString();
                this.cmdlineFile = new File(file, "cmdline").toString();
                this.threadsDir = new File(file, "task").toString();
                if (z) {
                    this.threadStats = new ArrayList<>();
                    this.workingThreads = new ArrayList<>();
                    return;
                } else {
                    this.threadStats = null;
                    this.workingThreads = null;
                    return;
                }
            }
            File file2 = new File(new File(new File("/proc", Integer.toString(i2)), "task"), Integer.toString(i));
            this.uid = getUid(file2.toString());
            this.statFile = new File(file2, "stat").toString();
            this.cmdlineFile = null;
            this.threadsDir = null;
            this.threadStats = null;
            this.workingThreads = null;
        }

        private static int getUid(String str) {
            try {
                return Os.stat(str).st_uid;
            } catch (ErrnoException e) {
                Slog.w(ProcessCpuTracker.TAG, "Failed to stat(" + str + "): " + e);
                return -1;
            }
        }
    }

    public ProcessCpuTracker(boolean z) {
        this.mIncludeThreads = z;
    }

    public void init() {
        this.mFirst = true;
        update();
    }

    public void update() {
        synchronized (this) {
            updateLocked();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r19v0 */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r19v2 */
    private void updateLocked() {
        long j;
        long j2;
        long j3;
        char c;
        ?? r19;
        long jUptimeMillis = SystemClock.uptimeMillis();
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long jCurrentTimeMillis = System.currentTimeMillis();
        long[] jArr = this.mSystemCpuData;
        if (Process.readProcFile("/proc/stat", SYSTEM_CPU_FORMAT, null, jArr, null)) {
            long j4 = jArr[0] + jArr[1];
            long j5 = this.mJiffyMillis;
            long j6 = j4 * j5;
            r19 = 0;
            c = 2;
            long j7 = jArr[2] * j5;
            long j8 = jArr[3] * j5;
            long j9 = jArr[4] * j5;
            j3 = jCurrentTimeMillis;
            long j10 = jArr[5] * j5;
            j2 = jElapsedRealtime;
            long j11 = jArr[6] * j5;
            j = jUptimeMillis;
            this.mRelUserTime = (int) (j6 - this.mBaseUserTime);
            this.mRelSystemTime = (int) (j7 - this.mBaseSystemTime);
            this.mRelIoWaitTime = (int) (j9 - this.mBaseIoWaitTime);
            this.mRelIrqTime = (int) (j10 - this.mBaseIrqTime);
            this.mRelSoftIrqTime = (int) (j11 - this.mBaseSoftIrqTime);
            this.mRelIdleTime = (int) (j8 - this.mBaseIdleTime);
            this.mRelStatsAreGood = true;
            this.mBaseUserTime = j6;
            this.mBaseSystemTime = j7;
            this.mBaseIoWaitTime = j9;
            this.mBaseIrqTime = j10;
            this.mBaseSoftIrqTime = j11;
            this.mBaseIdleTime = j8;
        } else {
            j = jUptimeMillis;
            j2 = jElapsedRealtime;
            j3 = jCurrentTimeMillis;
            c = 2;
            r19 = 0;
        }
        this.mLastSampleTime = this.mCurrentSampleTime;
        this.mCurrentSampleTime = j;
        this.mLastSampleRealTime = this.mCurrentSampleRealTime;
        this.mCurrentSampleRealTime = j2;
        this.mLastSampleWallTime = this.mCurrentSampleWallTime;
        this.mCurrentSampleWallTime = j3;
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            this.mCurPids = collectStats("/proc", -1, this.mFirst, this.mCurPids, this.mProcStats);
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            float[] fArr = this.mLoadAverageData;
            if (Process.readProcFile("/proc/loadavg", LOAD_AVERAGE_FORMAT, null, null, fArr)) {
                float f = fArr[r19];
                float f2 = fArr[1];
                float f3 = fArr[c];
                if (f != this.mLoad1 || f2 != this.mLoad5 || f3 != this.mLoad15) {
                    this.mLoad1 = f;
                    this.mLoad5 = f2;
                    this.mLoad15 = f3;
                    onLoadChanged(f, f2, f3);
                }
            }
            boolean z = r19;
            this.mWorkingProcsSorted = z;
            this.mFirst = z;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int[] collectStats(String str, int i, boolean z, int[] iArr, ArrayList<Stats> arrayList) {
        int i2;
        int[] iArr2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        long j;
        Stats stats;
        long j2;
        ArrayList<Stats> arrayList2 = arrayList;
        int[] pids = Process.getPids(str, iArr);
        boolean z2 = false;
        int length = pids == null ? 0 : pids.length;
        int size = arrayList2.size();
        int i8 = 0;
        int i9 = 0;
        while (i9 < length && (i2 = pids[i9]) >= 0) {
            Stats stats2 = i8 < size ? arrayList2.get(i8) : null;
            if (stats2 != null && stats2.pid == i2) {
                stats2.added = z2;
                stats2.working = z2;
                int i10 = i8 + 1;
                if (stats2.interesting) {
                    boolean z3 = z2;
                    i3 = length;
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    long[] jArr = this.mProcessStatsData;
                    if (Process.readProcFile(stats2.statFile.toString(), PROCESS_STATS_FORMAT, null, jArr, null)) {
                        long j3 = jArr[z3 ? 1 : 0];
                        long j4 = jArr[1];
                        long j5 = jArr[2];
                        Stats stats3 = stats2;
                        long j6 = this.mJiffyMillis;
                        long j7 = j5 * j6;
                        long j8 = j6 * jArr[3];
                        if (j7 == stats3.base_utime && j8 == stats3.base_stime) {
                            stats3.rel_utime = z3 ? 1 : 0;
                            stats3.rel_stime = z3 ? 1 : 0;
                            stats3.rel_minfaults = z3 ? 1 : 0;
                            stats3.rel_majfaults = z3 ? 1 : 0;
                            if (stats3.active) {
                                stats3.active = z3;
                            }
                        } else {
                            if (!stats3.active) {
                                stats3.active = true;
                            }
                            if (i < 0) {
                                getName(stats3, stats3.cmdlineFile);
                                if (stats3.threadStats != null) {
                                    iArr2 = pids;
                                    j = j7;
                                    stats = stats3;
                                    i7 = size;
                                    i6 = i9;
                                    j2 = j3;
                                    this.mCurThreadPids = collectStats(stats3.threadsDir, i2, false, this.mCurThreadPids, stats3.threadStats);
                                } else {
                                    iArr2 = pids;
                                    j = j7;
                                    stats = stats3;
                                    i7 = size;
                                    i6 = i9;
                                    j2 = j3;
                                }
                                stats.rel_uptime = jUptimeMillis - stats.base_uptime;
                                stats.base_uptime = jUptimeMillis;
                                stats.rel_utime = (int) (j - stats.base_utime);
                                stats.rel_stime = (int) (j8 - stats.base_stime);
                                stats.base_utime = j;
                                stats.base_stime = j8;
                                stats.rel_minfaults = (int) (j2 - stats.base_minfaults);
                                stats.rel_majfaults = (int) (j4 - stats.base_majfaults);
                                stats.base_minfaults = j2;
                                stats.base_majfaults = j4;
                                stats.working = true;
                                arrayList2 = arrayList;
                                i8 = i10;
                                size = i7;
                                i5 = i6;
                            }
                            i9 = i5 + i4;
                            length = i3;
                            pids = iArr2;
                            z2 = false;
                        }
                    }
                    iArr2 = pids;
                } else {
                    iArr2 = pids;
                    i3 = length;
                }
                i7 = size;
                i6 = i9;
                arrayList2 = arrayList;
                i8 = i10;
                size = i7;
                i5 = i6;
            } else {
                iArr2 = pids;
                i3 = length;
                int i11 = size;
                int i12 = i9;
                Stats stats4 = stats2;
                if (stats4 == null || stats4.pid > i2) {
                    arrayList2 = arrayList;
                    Stats stats5 = new Stats(i2, i, this.mIncludeThreads);
                    arrayList2.add(i8, stats5);
                    int i13 = i8 + 1;
                    size = i11 + 1;
                    String[] strArr = this.mProcessFullStatsStringData;
                    long[] jArr2 = this.mProcessFullStatsData;
                    stats5.base_uptime = SystemClock.uptimeMillis();
                    if (Process.readProcFile(stats5.statFile.toString(), PROCESS_FULL_STATS_FORMAT, strArr, jArr2, null)) {
                        stats5.vsize = jArr2[5];
                        stats5.interesting = true;
                        stats5.baseName = strArr[0];
                        stats5.base_minfaults = jArr2[1];
                        stats5.base_majfaults = jArr2[2];
                        stats5.base_utime = jArr2[3] * this.mJiffyMillis;
                        stats5.base_stime = jArr2[4] * this.mJiffyMillis;
                    } else {
                        Slog.w(TAG, "Skipping unknown process pid " + i2);
                        stats5.baseName = "<unknown>";
                        stats5.base_stime = 0L;
                        stats5.base_utime = 0L;
                        stats5.base_majfaults = 0L;
                        stats5.base_minfaults = 0L;
                    }
                    if (i < 0) {
                        getName(stats5, stats5.cmdlineFile);
                        if (stats5.threadStats != null) {
                            this.mCurThreadPids = collectStats(stats5.threadsDir, i2, true, this.mCurThreadPids, stats5.threadStats);
                        }
                    } else if (stats5.interesting) {
                        stats5.name = stats5.baseName;
                        stats5.nameWidth = onMeasureProcessName(stats5.name);
                    }
                    stats5.rel_utime = 0;
                    stats5.rel_stime = 0;
                    stats5.rel_minfaults = 0;
                    stats5.rel_majfaults = 0;
                    i4 = 1;
                    stats5.added = true;
                    if (!z && stats5.interesting) {
                        stats5.working = true;
                    }
                    i8 = i13;
                    i5 = i12;
                    i9 = i5 + i4;
                    length = i3;
                    pids = iArr2;
                    z2 = false;
                } else {
                    stats4.rel_utime = 0;
                    stats4.rel_stime = 0;
                    stats4.rel_minfaults = 0;
                    stats4.rel_majfaults = 0;
                    stats4.removed = true;
                    stats4.working = true;
                    arrayList2 = arrayList;
                    arrayList2.remove(i8);
                    size = i11 - 1;
                    i5 = i12 - 1;
                }
            }
            i4 = 1;
            i9 = i5 + i4;
            length = i3;
            pids = iArr2;
            z2 = false;
        }
        int[] iArr3 = pids;
        for (int i14 = size; i8 < i14; i14--) {
            Stats stats6 = arrayList2.get(i8);
            stats6.rel_utime = 0;
            stats6.rel_stime = 0;
            stats6.rel_minfaults = 0;
            stats6.rel_majfaults = 0;
            stats6.removed = true;
            stats6.working = true;
            arrayList2.remove(i8);
        }
        return iArr3;
    }

    public long getCpuTimeForPid(int i) {
        long[] jArr = new long[4];
        if (Process.readProcFile("/proc/" + i + "/stat", PROCESS_STATS_FORMAT, null, jArr, null)) {
            return (jArr[2] + jArr[3]) * this.mJiffyMillis;
        }
        return 0L;
    }

    public long getCpuDelayTimeForPid(int i) {
        long[] jArr = new long[4];
        if (Process.readProcFile("/proc/" + i + "/schedstat", PROCESS_SCHEDSTATS_FORMAT, null, jArr, null)) {
            return jArr[1] / 1000000;
        }
        return 0L;
    }

    public final int getLastUserTime() {
        return this.mRelUserTime;
    }

    public final int getLastSystemTime() {
        return this.mRelSystemTime;
    }

    public final int getLastIoWaitTime() {
        return this.mRelIoWaitTime;
    }

    public final int getLastIrqTime() {
        return this.mRelIrqTime;
    }

    public final int getLastSoftIrqTime() {
        return this.mRelSoftIrqTime;
    }

    public final int getLastIdleTime() {
        return this.mRelIdleTime;
    }

    public final boolean hasGoodLastStats() {
        return this.mRelStatsAreGood;
    }

    public final float getTotalCpuPercent() {
        int i = this.mRelUserTime;
        int i2 = this.mRelSystemTime;
        int i3 = i + i2 + this.mRelIrqTime + this.mRelIdleTime;
        if (i3 <= 0) {
            return 0.0f;
        }
        return (((i + i2) + r3) * 100.0f) / i3;
    }

    final void buildWorkingProcs() {
        if (this.mWorkingProcsSorted) {
            return;
        }
        this.mWorkingProcs.clear();
        int size = this.mProcStats.size();
        for (int i = 0; i < size; i++) {
            Stats stats = this.mProcStats.get(i);
            if (stats.working) {
                this.mWorkingProcs.add(stats);
                if (stats.threadStats != null && stats.threadStats.size() > 1) {
                    stats.workingThreads.clear();
                    int size2 = stats.threadStats.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        Stats stats2 = stats.threadStats.get(i2);
                        if (stats2.working) {
                            stats.workingThreads.add(stats2);
                        }
                    }
                    Collections.sort(stats.workingThreads, sLoadComparator);
                }
            }
        }
        Collections.sort(this.mWorkingProcs, sLoadComparator);
        this.mWorkingProcsSorted = true;
    }

    public final int countStats() {
        return this.mProcStats.size();
    }

    public final Stats getStats(int i) {
        return this.mProcStats.get(i);
    }

    public final List<Stats> getStats(FilterStats filterStats) {
        ArrayList arrayList = new ArrayList(this.mProcStats.size());
        int size = this.mProcStats.size();
        for (int i = 0; i < size; i++) {
            Stats stats = this.mProcStats.get(i);
            if (filterStats.needed(stats)) {
                arrayList.add(stats);
            }
        }
        return arrayList;
    }

    public final int countWorkingStats() {
        buildWorkingProcs();
        return this.mWorkingProcs.size();
    }

    public final Stats getWorkingStats(int i) {
        return this.mWorkingProcs.get(i);
    }

    public final void dumpProto(FileDescriptor fileDescriptor) throws IOException {
        long jUptimeMillis = SystemClock.uptimeMillis();
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        long jStart = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1108101562369L, this.mLoad1);
        protoOutputStream.write(1108101562370L, this.mLoad5);
        protoOutputStream.write(1108101562371L, this.mLoad15);
        protoOutputStream.end(jStart);
        buildWorkingProcs();
        protoOutputStream.write(1112396529666L, jUptimeMillis);
        protoOutputStream.write(1112396529667L, this.mLastSampleTime);
        protoOutputStream.write(1112396529668L, this.mCurrentSampleTime);
        protoOutputStream.write(1112396529669L, this.mLastSampleRealTime);
        protoOutputStream.write(1112396529670L, this.mCurrentSampleRealTime);
        protoOutputStream.write(1112396529671L, this.mLastSampleWallTime);
        protoOutputStream.write(1112396529672L, this.mCurrentSampleWallTime);
        protoOutputStream.write(1120986464265L, this.mRelUserTime);
        protoOutputStream.write(1120986464266L, this.mRelSystemTime);
        protoOutputStream.write(1120986464267L, this.mRelIoWaitTime);
        protoOutputStream.write(1120986464268L, this.mRelIrqTime);
        protoOutputStream.write(1120986464269L, this.mRelSoftIrqTime);
        protoOutputStream.write(1120986464270L, this.mRelIdleTime);
        protoOutputStream.write(1120986464271L, this.mRelUserTime + this.mRelSystemTime + this.mRelIoWaitTime + this.mRelIrqTime + this.mRelSoftIrqTime + this.mRelIdleTime);
        Iterator<Stats> it = this.mWorkingProcs.iterator();
        while (it.hasNext()) {
            Stats next = it.next();
            dumpProcessCpuProto(protoOutputStream, next, null);
            if (!next.removed && next.workingThreads != null) {
                Iterator<Stats> it2 = next.workingThreads.iterator();
                while (it2.hasNext()) {
                    dumpProcessCpuProto(protoOutputStream, it2.next(), next);
                }
            }
        }
        protoOutputStream.flush();
    }

    private static void dumpProcessCpuProto(ProtoOutputStream protoOutputStream, Stats stats, Stats stats2) {
        long jStart = protoOutputStream.start(2246267895824L);
        protoOutputStream.write(1120986464257L, stats.uid);
        protoOutputStream.write(1120986464258L, stats.pid);
        protoOutputStream.write(1138166333443L, stats.name);
        protoOutputStream.write(1133871366148L, stats.added);
        protoOutputStream.write(1133871366149L, stats.removed);
        protoOutputStream.write(1120986464262L, stats.rel_uptime);
        protoOutputStream.write(1120986464263L, stats.rel_utime);
        protoOutputStream.write(1120986464264L, stats.rel_stime);
        protoOutputStream.write(1120986464265L, stats.rel_minfaults);
        protoOutputStream.write(1120986464266L, stats.rel_majfaults);
        if (stats2 != null) {
            protoOutputStream.write(1120986464267L, stats2.pid);
        }
        protoOutputStream.end(jStart);
    }

    public final String printCurrentLoad() {
        StringWriter stringWriter = new StringWriter();
        FastPrintWriter fastPrintWriter = new FastPrintWriter((Writer) stringWriter, false, 128);
        fastPrintWriter.print("Load: ");
        fastPrintWriter.print(this.mLoad1);
        fastPrintWriter.print(" / ");
        fastPrintWriter.print(this.mLoad5);
        fastPrintWriter.print(" / ");
        fastPrintWriter.println(this.mLoad15);
        fastPrintWriter.flush();
        return stringWriter.toString();
    }

    public final String printCurrentState(long j) {
        return printCurrentState(j, Integer.MAX_VALUE);
    }

    public final String printCurrentState(long j, int i) {
        ProcessCpuTracker processCpuTracker = this;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        processCpuTracker.buildWorkingProcs();
        StringWriter stringWriter = new StringWriter();
        FastPrintWriter fastPrintWriter = new FastPrintWriter((Writer) stringWriter, false, 1024);
        fastPrintWriter.print("CPU usage from ");
        long j2 = processCpuTracker.mLastSampleTime;
        if (j > j2) {
            fastPrintWriter.print(j - j2);
            fastPrintWriter.print("ms to ");
            fastPrintWriter.print(j - processCpuTracker.mCurrentSampleTime);
            fastPrintWriter.print("ms ago");
        } else {
            fastPrintWriter.print(j2 - j);
            fastPrintWriter.print("ms to ");
            fastPrintWriter.print(processCpuTracker.mCurrentSampleTime - j);
            fastPrintWriter.print("ms later");
        }
        fastPrintWriter.print(" (");
        fastPrintWriter.print(simpleDateFormat.format(new Date(processCpuTracker.mLastSampleWallTime)));
        fastPrintWriter.print(" to ");
        fastPrintWriter.print(simpleDateFormat.format(new Date(processCpuTracker.mCurrentSampleWallTime)));
        fastPrintWriter.print(NavigationBarInflaterView.KEY_CODE_END);
        long j3 = processCpuTracker.mCurrentSampleTime - processCpuTracker.mLastSampleTime;
        long j4 = processCpuTracker.mCurrentSampleRealTime - processCpuTracker.mLastSampleRealTime;
        long j5 = j4 > 0 ? (j3 * 100) / j4 : 0L;
        if (j5 != 100) {
            fastPrintWriter.print(" with ");
            fastPrintWriter.print(j5);
            fastPrintWriter.print("% awake");
        }
        fastPrintWriter.println(":");
        int i2 = processCpuTracker.mRelUserTime + processCpuTracker.mRelSystemTime + processCpuTracker.mRelIoWaitTime + processCpuTracker.mRelIrqTime + processCpuTracker.mRelSoftIrqTime + processCpuTracker.mRelIdleTime;
        int iMin = Math.min(i, processCpuTracker.mWorkingProcs.size());
        int i3 = 0;
        while (i3 < iMin) {
            Stats stats = processCpuTracker.mWorkingProcs.get(i3);
            int i4 = i3;
            int i5 = iMin;
            FastPrintWriter fastPrintWriter2 = fastPrintWriter;
            processCpuTracker.printProcessCPU(fastPrintWriter2, stats.added ? " +" : stats.removed ? " -" : "  ", stats.pid, stats.name, (int) stats.rel_uptime, stats.rel_utime, stats.rel_stime, 0, 0, 0, stats.rel_minfaults, stats.rel_majfaults);
            if (!stats.removed && stats.workingThreads != null) {
                int i6 = 0;
                for (int size = stats.workingThreads.size(); i6 < size; size = size) {
                    Stats stats2 = stats.workingThreads.get(i6);
                    printProcessCPU(fastPrintWriter2, stats2.added ? "   +" : stats2.removed ? "   -" : "    ", stats2.pid, stats2.name, (int) stats.rel_uptime, stats2.rel_utime, stats2.rel_stime, 0, 0, 0, 0, 0);
                    i6++;
                }
            }
            processCpuTracker = this;
            i3 = i4 + 1;
            fastPrintWriter = fastPrintWriter2;
            iMin = i5;
        }
        FastPrintWriter fastPrintWriter3 = fastPrintWriter;
        processCpuTracker.printProcessCPU(fastPrintWriter3, "", -1, "TOTAL", i2, processCpuTracker.mRelUserTime, processCpuTracker.mRelSystemTime, processCpuTracker.mRelIoWaitTime, processCpuTracker.mRelIrqTime, processCpuTracker.mRelSoftIrqTime, 0, 0);
        fastPrintWriter3.flush();
        return stringWriter.toString();
    }

    private void printRatio(PrintWriter printWriter, long j, long j2) {
        long j3 = (j * 1000) / j2;
        long j4 = j3 / 10;
        printWriter.print(j4);
        if (j4 < 10) {
            long j5 = j3 - (j4 * 10);
            if (j5 != 0) {
                printWriter.print('.');
                printWriter.print(j5);
            }
        }
    }

    private void printProcessCPU(PrintWriter printWriter, String str, int i, String str2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        printWriter.print(str);
        long j = i2 == 0 ? 1 : i2;
        printRatio(printWriter, i3 + i4 + i5 + i6 + i7, j);
        printWriter.print("% ");
        if (i >= 0) {
            printWriter.print(i);
            printWriter.print("/");
        }
        printWriter.print(str2);
        printWriter.print(": ");
        printRatio(printWriter, i3, j);
        printWriter.print("% user + ");
        printRatio(printWriter, i4, j);
        printWriter.print("% kernel");
        if (i5 > 0) {
            printWriter.print(" + ");
            printRatio(printWriter, i5, j);
            printWriter.print("% iowait");
        }
        if (i6 > 0) {
            printWriter.print(" + ");
            printRatio(printWriter, i6, j);
            printWriter.print("% irq");
        }
        if (i7 > 0) {
            printWriter.print(" + ");
            printRatio(printWriter, i7, j);
            printWriter.print("% softirq");
        }
        if (i8 > 0 || i9 > 0) {
            printWriter.print(" / faults:");
            if (i8 > 0) {
                printWriter.print(" ");
                printWriter.print(i8);
                printWriter.print(" minor");
            }
            if (i9 > 0) {
                printWriter.print(" ");
                printWriter.print(i9);
                printWriter.print(" major");
            }
        }
        printWriter.println();
    }

    private void getName(Stats stats, String str) {
        String str2 = stats.name;
        if (stats.name == null || stats.name.equals("app_process") || stats.name.equals("<pre-initialized>") || stats.name.equals("usap32") || stats.name.equals("usap64")) {
            String terminatedProcFile = ProcStatsUtil.readTerminatedProcFile(str, (byte) 0);
            if (terminatedProcFile != null && terminatedProcFile.length() > 1) {
                int iLastIndexOf = terminatedProcFile.lastIndexOf("/");
                if (iLastIndexOf > 0 && iLastIndexOf < terminatedProcFile.length() - 1) {
                    terminatedProcFile = terminatedProcFile.substring(iLastIndexOf + 1);
                }
                str2 = terminatedProcFile;
            }
            if (str2 == null) {
                str2 = stats.baseName;
            }
        }
        if (stats.name == null || !str2.equals(stats.name)) {
            stats.name = str2;
            stats.nameWidth = onMeasureProcessName(stats.name);
        }
    }

    public String printCpuCoreInfo() {
        int i;
        StringWriter stringWriter = new StringWriter();
        int i2 = 0;
        FastPrintWriter fastPrintWriter = new FastPrintWriter((Writer) stringWriter, false, 128);
        int[] iArr = {4128};
        int[] iArr2 = {8224};
        String[] strArr = {"/sys/devices/system/cpu/offline", "/sys/devices/system/cpu/online"};
        String[] strArr2 = {"/sys/devices/system/cpu/cpu%d/cpufreq/scaling_cur_freq", "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_governor", "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_max_freq"};
        String[] strArr3 = {"/sys/class/sec/sec-thermistor/temperature", "/sys/devices/platform/sec-thermistor/temperature", "/sys/class/sec/sec-ap-thermistor/temperature"};
        boolean z = true;
        String[] strArr4 = new String[1];
        long[] jArr = new long[1];
        fastPrintWriter.println("------ Current CPU Core Info ------");
        int i3 = 0;
        while (true) {
            i = i2;
            if (i3 >= 2) {
                break;
            }
            fastPrintWriter.print("- ");
            String str = strArr[i3];
            fastPrintWriter.print(str.substring(str.lastIndexOf(47) + 1));
            fastPrintWriter.print(" : ");
            if (Process.readProcFile(strArr[i3], iArr, strArr4, null, null)) {
                fastPrintWriter.println(strArr4[i].replace("?", "").trim());
            } else {
                fastPrintWriter.println(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            i3++;
            i2 = i;
        }
        int i4 = i;
        while (true) {
            boolean z2 = z;
            if (i4 >= 3) {
                break;
            }
            if (Process.readProcFile(String.format(strArr3[i4], Integer.valueOf(i4)), iArr2, null, jArr, null)) {
                fastPrintWriter.print(String.format("- AP Temp = %d%n", Long.valueOf(jArr[i])));
                break;
            }
            i4++;
            z = z2;
        }
        int i5 = Process.readProcFile("/sys/devices/system/cpu/possible", iArr, strArr4, null, null) ? Integer.parseInt(strArr4[i].replace("?", "").trim().substring(2).trim()) : i;
        if (i5 > 0) {
            fastPrintWriter.print("                  ");
            for (int i6 = i; i6 <= i5; i6++) {
                fastPrintWriter.print(String.format("%12d", Integer.valueOf(i6)));
            }
            fastPrintWriter.print("\n------------------");
            for (int i7 = i; i7 <= i5; i7++) {
                fastPrintWriter.print("------------");
            }
            for (int i8 = i; i8 < 3; i8++) {
                String str2 = strArr2[i8];
                fastPrintWriter.print(String.format("%n%-18s", str2.substring(str2.lastIndexOf(47) + 1)));
                for (int i9 = i; i9 <= i5; i9++) {
                    if (Process.readProcFile(String.format(strArr2[i8], Integer.valueOf(i9)), iArr, strArr4, null, null)) {
                        fastPrintWriter.print(String.format("%12s", strArr4[i].replace("?", "").trim()));
                    } else {
                        fastPrintWriter.print("           -");
                    }
                }
            }
            fastPrintWriter.print("\n------------------");
            for (int i10 = i; i10 <= i5; i10++) {
                fastPrintWriter.print("------------");
            }
            fastPrintWriter.println();
        }
        fastPrintWriter.flush();
        return stringWriter.toString();
    }
}
