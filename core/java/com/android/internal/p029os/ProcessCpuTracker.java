package com.android.internal.p029os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.BatteryStats;
import android.p009os.Process;
import android.p009os.StrictMode;
import android.p009os.SystemClock;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.FastPrintWriter;
import java.io.File;
import java.io.FileDescriptor;
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
    private final long mJiffyMillis;
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
        public final int compare(Stats sta, Stats stb) {
            int ta = sta.rel_utime + sta.rel_stime;
            int tb = stb.rel_utime + stb.rel_stime;
            if (ta != tb) {
                return ta > tb ? -1 : 1;
            }
            if (sta.added != stb.added) {
                return sta.added ? -1 : 1;
            }
            if (sta.removed != stb.removed) {
                return sta.added ? -1 : 1;
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

    public interface FilterStats {
        boolean needed(Stats stats);
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

        Stats(int _pid, int parentPid, boolean includeThreads) {
            this.pid = _pid;
            if (parentPid < 0) {
                File procDir = new File("/proc", Integer.toString(_pid));
                this.uid = getUid(procDir.toString());
                this.statFile = new File(procDir, "stat").toString();
                this.cmdlineFile = new File(procDir, "cmdline").toString();
                this.threadsDir = new File(procDir, "task").toString();
                if (includeThreads) {
                    this.threadStats = new ArrayList<>();
                    this.workingThreads = new ArrayList<>();
                    return;
                } else {
                    this.threadStats = null;
                    this.workingThreads = null;
                    return;
                }
            }
            File taskDir = new File(new File(new File("/proc", Integer.toString(parentPid)), "task"), Integer.toString(_pid));
            this.uid = getUid(taskDir.toString());
            this.statFile = new File(taskDir, "stat").toString();
            this.cmdlineFile = null;
            this.threadsDir = null;
            this.threadStats = null;
            this.workingThreads = null;
        }

        private static int getUid(String path) {
            try {
                return Os.stat(path).st_uid;
            } catch (ErrnoException e) {
                Slog.m121w(ProcessCpuTracker.TAG, "Failed to stat(" + path + "): " + e);
                return -1;
            }
        }
    }

    public ProcessCpuTracker(boolean includeThreads) {
        this.mIncludeThreads = includeThreads;
        long jiffyHz = Os.sysconf(OsConstants._SC_CLK_TCK);
        this.mJiffyMillis = 1000 / jiffyHz;
    }

    public void onLoadChanged(float load1, float load5, float load15) {
    }

    public int onMeasureProcessName(String name) {
        return 0;
    }

    public void init() {
        this.mFirst = true;
        update();
    }

    public void update() {
        long nowUptime;
        long nowRealtime;
        long nowWallTime;
        char c;
        long nowUptime2 = SystemClock.uptimeMillis();
        long nowRealtime2 = SystemClock.elapsedRealtime();
        long nowWallTime2 = System.currentTimeMillis();
        long[] sysCpu = this.mSystemCpuData;
        if (!Process.readProcFile("/proc/stat", SYSTEM_CPU_FORMAT, null, sysCpu, null)) {
            nowUptime = nowUptime2;
            nowRealtime = nowRealtime2;
            nowWallTime = nowWallTime2;
            c = 1;
        } else {
            long j = sysCpu[0] + sysCpu[1];
            long j2 = this.mJiffyMillis;
            long usertime = j * j2;
            long systemtime = sysCpu[2] * j2;
            nowWallTime = nowWallTime2;
            long nowWallTime3 = sysCpu[3] * j2;
            nowRealtime = nowRealtime2;
            long nowRealtime3 = sysCpu[4] * j2;
            nowUptime = nowUptime2;
            long nowUptime3 = sysCpu[5] * j2;
            long softirqtime = j2 * sysCpu[6];
            this.mRelUserTime = (int) (usertime - this.mBaseUserTime);
            this.mRelSystemTime = (int) (systemtime - this.mBaseSystemTime);
            this.mRelIoWaitTime = (int) (nowRealtime3 - this.mBaseIoWaitTime);
            this.mRelIrqTime = (int) (nowUptime3 - this.mBaseIrqTime);
            this.mRelSoftIrqTime = (int) (softirqtime - this.mBaseSoftIrqTime);
            this.mRelIdleTime = (int) (nowWallTime3 - this.mBaseIdleTime);
            c = 1;
            this.mRelStatsAreGood = true;
            this.mBaseUserTime = usertime;
            this.mBaseSystemTime = systemtime;
            this.mBaseIoWaitTime = nowRealtime3;
            this.mBaseIrqTime = nowUptime3;
            this.mBaseSoftIrqTime = softirqtime;
            this.mBaseIdleTime = nowWallTime3;
        }
        this.mLastSampleTime = this.mCurrentSampleTime;
        this.mCurrentSampleTime = nowUptime;
        this.mLastSampleRealTime = this.mCurrentSampleRealTime;
        this.mCurrentSampleRealTime = nowRealtime;
        this.mLastSampleWallTime = this.mCurrentSampleWallTime;
        this.mCurrentSampleWallTime = nowWallTime;
        StrictMode.ThreadPolicy savedPolicy = StrictMode.allowThreadDiskReads();
        try {
            this.mCurPids = collectStats("/proc", -1, this.mFirst, this.mCurPids, this.mProcStats);
            StrictMode.setThreadPolicy(savedPolicy);
            float[] loadAverages = this.mLoadAverageData;
            if (Process.readProcFile("/proc/loadavg", LOAD_AVERAGE_FORMAT, null, null, loadAverages)) {
                float load1 = loadAverages[0];
                float load5 = loadAverages[c];
                float load15 = loadAverages[2];
                if (load1 != this.mLoad1 || load5 != this.mLoad5 || load15 != this.mLoad15) {
                    this.mLoad1 = load1;
                    this.mLoad5 = load5;
                    this.mLoad15 = load15;
                    onLoadChanged(load1, load5, load15);
                }
            }
            this.mWorkingProcsSorted = false;
            this.mFirst = false;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(savedPolicy);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int[] collectStats(String str, int i, boolean z, int[] iArr, ArrayList<Stats> arrayList) {
        int[] iArr2;
        boolean z2;
        int[] iArr3;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z3;
        Stats stats;
        boolean z4;
        long j;
        long j2;
        long j3;
        int i9 = i;
        ArrayList<Stats> arrayList2 = arrayList;
        int[] pids = Process.getPids(str, iArr);
        boolean z5 = false;
        int length = pids == null ? 0 : pids.length;
        int i10 = 0;
        int size = arrayList.size();
        int i11 = 0;
        while (true) {
            if (i11 >= length) {
                iArr2 = pids;
                z2 = true;
                break;
            }
            int i12 = pids[i11];
            if (i12 < 0) {
                iArr2 = pids;
                z2 = true;
                break;
            }
            Stats stats2 = i10 < size ? arrayList2.get(i10) : null;
            if (stats2 != null && stats2.pid == i12) {
                stats2.added = z5;
                stats2.working = z5;
                int i13 = i10 + 1;
                if (stats2.interesting) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long[] jArr = this.mProcessStatsData;
                    if (!Process.readProcFile(stats2.statFile.toString(), PROCESS_STATS_FORMAT, null, jArr, null)) {
                        iArr3 = pids;
                        i2 = length;
                        i7 = size;
                        i8 = i11;
                        i3 = 1;
                    } else {
                        long j4 = jArr[0];
                        long j5 = jArr[1];
                        long j6 = jArr[2];
                        long j7 = this.mJiffyMillis;
                        i2 = length;
                        long j8 = j6 * j7;
                        long j9 = jArr[3] * j7;
                        i7 = size;
                        i8 = i11;
                        if (j8 == stats2.base_utime && j9 == stats2.base_stime) {
                            stats2.rel_utime = 0;
                            stats2.rel_stime = 0;
                            stats2.rel_minfaults = 0;
                            stats2.rel_majfaults = 0;
                            if (!stats2.active) {
                                iArr3 = pids;
                                i3 = 1;
                            } else {
                                stats2.active = false;
                                iArr3 = pids;
                                i3 = 1;
                            }
                        } else {
                            if (stats2.active) {
                                z3 = true;
                            } else {
                                z3 = true;
                                stats2.active = true;
                            }
                            if (i9 < 0) {
                                getName(stats2, stats2.cmdlineFile);
                                if (stats2.threadStats != null) {
                                    j = uptimeMillis;
                                    j2 = j5;
                                    j3 = j9;
                                    stats = stats2;
                                    iArr3 = pids;
                                    z4 = 1;
                                    this.mCurThreadPids = collectStats(stats2.threadsDir, i12, false, this.mCurThreadPids, stats2.threadStats);
                                } else {
                                    stats = stats2;
                                    iArr3 = pids;
                                    z4 = z3;
                                    j = uptimeMillis;
                                    j2 = j5;
                                    j3 = j9;
                                }
                            } else {
                                stats = stats2;
                                iArr3 = pids;
                                z4 = z3;
                                j = uptimeMillis;
                                j2 = j5;
                                j3 = j9;
                            }
                            stats.rel_uptime = j - stats.base_uptime;
                            stats.base_uptime = j;
                            stats.rel_utime = (int) (j8 - stats.base_utime);
                            stats.rel_stime = (int) (j3 - stats.base_stime);
                            stats.base_utime = j8;
                            stats.base_stime = j3;
                            stats.rel_minfaults = (int) (j4 - stats.base_minfaults);
                            stats.rel_majfaults = (int) (j2 - stats.base_majfaults);
                            stats.base_minfaults = j4;
                            stats.base_majfaults = j2;
                            stats.working = z4;
                            i3 = z4;
                        }
                    }
                } else {
                    iArr3 = pids;
                    i2 = length;
                    i7 = size;
                    i8 = i11;
                    i3 = 1;
                }
                i5 = i;
                arrayList2 = arrayList;
                i10 = i13;
                size = i7;
                i6 = i8;
            } else {
                iArr3 = pids;
                i2 = length;
                int i14 = size;
                int i15 = i11;
                i3 = 1;
                i3 = 1;
                if (stats2 != null) {
                    i4 = i12;
                    if (stats2.pid > i4) {
                        arrayList2 = arrayList;
                    } else {
                        stats2.rel_utime = 0;
                        stats2.rel_stime = 0;
                        stats2.rel_minfaults = 0;
                        stats2.rel_majfaults = 0;
                        stats2.removed = true;
                        stats2.working = true;
                        arrayList2 = arrayList;
                        arrayList2.remove(i10);
                        size = i14 - 1;
                        i5 = i;
                        i6 = i15 - 1;
                    }
                } else {
                    arrayList2 = arrayList;
                    i4 = i12;
                }
                i5 = i;
                Stats stats3 = new Stats(i4, i5, this.mIncludeThreads);
                arrayList2.add(i10, stats3);
                int i16 = i10 + 1;
                size = i14 + 1;
                String[] strArr = this.mProcessFullStatsStringData;
                long[] jArr2 = this.mProcessFullStatsData;
                stats3.base_uptime = SystemClock.uptimeMillis();
                if (Process.readProcFile(stats3.statFile.toString(), PROCESS_FULL_STATS_FORMAT, strArr, jArr2, null)) {
                    stats3.vsize = jArr2[5];
                    stats3.interesting = true;
                    stats3.baseName = strArr[0];
                    stats3.base_minfaults = jArr2[1];
                    stats3.base_majfaults = jArr2[2];
                    stats3.base_utime = jArr2[3] * this.mJiffyMillis;
                    stats3.base_stime = jArr2[4] * this.mJiffyMillis;
                } else {
                    Slog.m121w(TAG, "Skipping unknown process pid " + i4);
                    stats3.baseName = "<unknown>";
                    stats3.base_stime = 0L;
                    stats3.base_utime = 0L;
                    stats3.base_majfaults = 0L;
                    stats3.base_minfaults = 0L;
                }
                if (i5 < 0) {
                    getName(stats3, stats3.cmdlineFile);
                    if (stats3.threadStats != null) {
                        this.mCurThreadPids = collectStats(stats3.threadsDir, i4, true, this.mCurThreadPids, stats3.threadStats);
                    }
                } else if (stats3.interesting) {
                    stats3.name = stats3.baseName;
                    stats3.nameWidth = onMeasureProcessName(stats3.name);
                }
                stats3.rel_utime = 0;
                stats3.rel_stime = 0;
                stats3.rel_minfaults = 0;
                stats3.rel_majfaults = 0;
                stats3.added = true;
                if (!z && stats3.interesting) {
                    stats3.working = true;
                }
                i10 = i16;
                i6 = i15;
            }
            i11 = i6 + i3;
            i9 = i5;
            pids = iArr3;
            length = i2;
            z5 = false;
        }
        while (i10 < size) {
            Stats stats4 = arrayList2.get(i10);
            stats4.rel_utime = 0;
            stats4.rel_stime = 0;
            stats4.rel_minfaults = 0;
            stats4.rel_majfaults = 0;
            stats4.removed = z2;
            stats4.working = z2;
            arrayList2.remove(i10);
            size--;
        }
        return iArr2;
    }

    public long getCpuTimeForPid(int pid) {
        String statFile = "/proc/" + pid + "/stat";
        long[] statsData = new long[4];
        if (Process.readProcFile(statFile, PROCESS_STATS_FORMAT, null, statsData, null)) {
            long time = statsData[2] + statsData[3];
            return this.mJiffyMillis * time;
        }
        return 0L;
    }

    public long getCpuDelayTimeForPid(int pid) {
        String statFile = "/proc/" + pid + "/schedstat";
        long[] statsData = new long[4];
        if (Process.readProcFile(statFile, PROCESS_SCHEDSTATS_FORMAT, null, statsData, null)) {
            return statsData[1] / 1000000;
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
        int denom = i + i2 + this.mRelIrqTime + this.mRelIdleTime;
        if (denom <= 0) {
            return 0.0f;
        }
        return (((i + i2) + r3) * 100.0f) / denom;
    }

    final void buildWorkingProcs() {
        if (!this.mWorkingProcsSorted) {
            this.mWorkingProcs.clear();
            int N = this.mProcStats.size();
            for (int i = 0; i < N; i++) {
                Stats stats = this.mProcStats.get(i);
                if (stats.working) {
                    this.mWorkingProcs.add(stats);
                    if (stats.threadStats != null && stats.threadStats.size() > 1) {
                        stats.workingThreads.clear();
                        int M = stats.threadStats.size();
                        for (int j = 0; j < M; j++) {
                            Stats tstats = stats.threadStats.get(j);
                            if (tstats.working) {
                                stats.workingThreads.add(tstats);
                            }
                        }
                        Collections.sort(stats.workingThreads, sLoadComparator);
                    }
                }
            }
            Collections.sort(this.mWorkingProcs, sLoadComparator);
            this.mWorkingProcsSorted = true;
        }
    }

    public final int countStats() {
        return this.mProcStats.size();
    }

    public final Stats getStats(int index) {
        return this.mProcStats.get(index);
    }

    public final List<Stats> getStats(FilterStats filter) {
        ArrayList<Stats> statses = new ArrayList<>(this.mProcStats.size());
        int N = this.mProcStats.size();
        for (int p = 0; p < N; p++) {
            Stats stats = this.mProcStats.get(p);
            if (filter.needed(stats)) {
                statses.add(stats);
            }
        }
        return statses;
    }

    public final int countWorkingStats() {
        buildWorkingProcs();
        return this.mWorkingProcs.size();
    }

    public final Stats getWorkingStats(int index) {
        return this.mWorkingProcs.get(index);
    }

    public final void dumpProto(FileDescriptor fd) {
        long now = SystemClock.uptimeMillis();
        ProtoOutputStream proto = new ProtoOutputStream(fd);
        long currentLoadToken = proto.start(1146756268033L);
        proto.write(1108101562369L, this.mLoad1);
        proto.write(1108101562370L, this.mLoad5);
        proto.write(1108101562371L, this.mLoad15);
        proto.end(currentLoadToken);
        buildWorkingProcs();
        proto.write(1112396529666L, now);
        proto.write(1112396529667L, this.mLastSampleTime);
        proto.write(1112396529668L, this.mCurrentSampleTime);
        proto.write(1112396529669L, this.mLastSampleRealTime);
        proto.write(1112396529670L, this.mCurrentSampleRealTime);
        proto.write(1112396529671L, this.mLastSampleWallTime);
        proto.write(1112396529672L, this.mCurrentSampleWallTime);
        proto.write(1120986464265L, this.mRelUserTime);
        proto.write(1120986464266L, this.mRelSystemTime);
        proto.write(1120986464267L, this.mRelIoWaitTime);
        proto.write(1120986464268L, this.mRelIrqTime);
        proto.write(1120986464269L, this.mRelSoftIrqTime);
        proto.write(1120986464270L, this.mRelIdleTime);
        int totalTime = this.mRelUserTime + this.mRelSystemTime + this.mRelIoWaitTime + this.mRelIrqTime + this.mRelSoftIrqTime + this.mRelIdleTime;
        proto.write(1120986464271L, totalTime);
        Iterator<Stats> it = this.mWorkingProcs.iterator();
        while (it.hasNext()) {
            Stats st = it.next();
            dumpProcessCpuProto(proto, st, null);
            if (!st.removed && st.workingThreads != null) {
                Iterator<Stats> it2 = st.workingThreads.iterator();
                while (it2.hasNext()) {
                    Stats tst = it2.next();
                    dumpProcessCpuProto(proto, tst, st);
                }
            }
        }
        proto.flush();
    }

    private static void dumpProcessCpuProto(ProtoOutputStream proto, Stats st, Stats proc) {
        long statToken = proto.start(2246267895824L);
        proto.write(1120986464257L, st.uid);
        proto.write(1120986464258L, st.pid);
        proto.write(1138166333443L, st.name);
        proto.write(1133871366148L, st.added);
        proto.write(1133871366149L, st.removed);
        proto.write(1120986464262L, st.rel_uptime);
        proto.write(1120986464263L, st.rel_utime);
        proto.write(1120986464264L, st.rel_stime);
        proto.write(1120986464265L, st.rel_minfaults);
        proto.write(1120986464266L, st.rel_majfaults);
        if (proc != null) {
            proto.write(1120986464267L, proc.pid);
        }
        proto.end(statToken);
    }

    public final String printCurrentLoad() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new FastPrintWriter((Writer) sw, false, 128);
        pw.print("Load: ");
        pw.print(this.mLoad1);
        pw.print(" / ");
        pw.print(this.mLoad5);
        pw.print(" / ");
        pw.println(this.mLoad15);
        pw.flush();
        return sw.toString();
    }

    public final String printCurrentState(long now) {
        return printCurrentState(now, Integer.MAX_VALUE);
    }

    public final String printCurrentState(long now, int maxProcessesToDump) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        buildWorkingProcs();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new FastPrintWriter((Writer) sw, false, 1024);
        pw.print("CPU usage from ");
        long j = this.mLastSampleTime;
        if (now > j) {
            pw.print(now - j);
            pw.print("ms to ");
            pw.print(now - this.mCurrentSampleTime);
            pw.print("ms ago");
        } else {
            pw.print(j - now);
            pw.print("ms to ");
            pw.print(this.mCurrentSampleTime - now);
            pw.print("ms later");
        }
        pw.print(" (");
        pw.print(sdf.format(new Date(this.mLastSampleWallTime)));
        pw.print(" to ");
        pw.print(sdf.format(new Date(this.mCurrentSampleWallTime)));
        pw.print(NavigationBarInflaterView.KEY_CODE_END);
        long sampleTime = this.mCurrentSampleTime - this.mLastSampleTime;
        long sampleRealTime = this.mCurrentSampleRealTime - this.mLastSampleRealTime;
        long percAwake = sampleRealTime > 0 ? (sampleTime * 100) / sampleRealTime : 0L;
        if (percAwake != 100) {
            pw.print(" with ");
            pw.print(percAwake);
            pw.print("% awake");
        }
        pw.println(":");
        int totalTime = this.mRelUserTime + this.mRelSystemTime + this.mRelIoWaitTime + this.mRelIrqTime + this.mRelSoftIrqTime + this.mRelIdleTime;
        int dumpedProcessCount = Math.min(maxProcessesToDump, this.mWorkingProcs.size());
        int i = 0;
        while (i < dumpedProcessCount) {
            Stats st = this.mWorkingProcs.get(i);
            long percAwake2 = percAwake;
            int i2 = i;
            int dumpedProcessCount2 = dumpedProcessCount;
            PrintWriter pw2 = pw;
            printProcessCPU(pw, st.added ? " +" : st.removed ? " -" : "  ", st.pid, st.name, (int) st.rel_uptime, st.rel_utime, st.rel_stime, 0, 0, 0, st.rel_minfaults, st.rel_majfaults);
            Stats st2 = st;
            if (!st2.removed && st2.workingThreads != null) {
                int M = st2.workingThreads.size();
                int j2 = 0;
                while (j2 < M) {
                    Stats tst = st2.workingThreads.get(j2);
                    printProcessCPU(pw2, tst.added ? "   +" : tst.removed ? "   -" : "    ", tst.pid, tst.name, (int) st2.rel_uptime, tst.rel_utime, tst.rel_stime, 0, 0, 0, 0, 0);
                    j2++;
                    M = M;
                    st2 = st2;
                }
            }
            i = i2 + 1;
            percAwake = percAwake2;
            pw = pw2;
            dumpedProcessCount = dumpedProcessCount2;
        }
        PrintWriter pw3 = pw;
        printProcessCPU(pw3, "", -1, "TOTAL", totalTime, this.mRelUserTime, this.mRelSystemTime, this.mRelIoWaitTime, this.mRelIrqTime, this.mRelSoftIrqTime, 0, 0);
        pw3.flush();
        return sw.toString();
    }

    private void printRatio(PrintWriter pw, long numerator, long denominator) {
        long thousands = (1000 * numerator) / denominator;
        long hundreds = thousands / 10;
        pw.print(hundreds);
        if (hundreds < 10) {
            long remainder = thousands - (10 * hundreds);
            if (remainder != 0) {
                pw.print('.');
                pw.print(remainder);
            }
        }
    }

    private void printProcessCPU(PrintWriter pw, String prefix, int pid, String label, int totalTime, int user, int system, int iowait, int irq, int softIrq, int minFaults, int majFaults) {
        String str;
        pw.print(prefix);
        int totalTime2 = totalTime == 0 ? 1 : totalTime;
        printRatio(pw, user + system + iowait + irq + softIrq, totalTime2);
        pw.print("% ");
        if (pid >= 0) {
            pw.print(pid);
            pw.print("/");
        }
        pw.print(label);
        pw.print(": ");
        printRatio(pw, user, totalTime2);
        pw.print("% user + ");
        printRatio(pw, system, totalTime2);
        pw.print("% kernel");
        if (iowait <= 0) {
            str = " + ";
        } else {
            pw.print(" + ");
            str = " + ";
            printRatio(pw, iowait, totalTime2);
            pw.print("% iowait");
        }
        if (irq > 0) {
            pw.print(str);
            printRatio(pw, irq, totalTime2);
            pw.print("% irq");
        }
        if (softIrq > 0) {
            pw.print(str);
            printRatio(pw, softIrq, totalTime2);
            pw.print("% softirq");
        }
        if (minFaults > 0 || majFaults > 0) {
            pw.print(" / faults:");
            if (minFaults > 0) {
                pw.print(" ");
                pw.print(minFaults);
                pw.print(" minor");
            }
            if (majFaults > 0) {
                pw.print(" ");
                pw.print(majFaults);
                pw.print(" major");
            }
        }
        pw.println();
    }

    private void getName(Stats st, String cmdlineFile) {
        int i;
        String newName = st.name;
        if (st.name == null || st.name.equals("app_process") || st.name.equals("<pre-initialized>") || st.name.equals("usap32") || st.name.equals("usap64")) {
            String cmdName = ProcStatsUtil.readTerminatedProcFile(cmdlineFile, (byte) 0);
            if (cmdName != null && cmdName.length() > 1 && (i = (newName = cmdName).lastIndexOf("/")) > 0 && i < newName.length() - 1) {
                newName = newName.substring(i + 1);
            }
            if (newName == null) {
                newName = st.baseName;
            }
        }
        if (st.name == null || !newName.equals(st.name)) {
            st.name = newName;
            st.nameWidth = onMeasureProcessName(st.name);
        }
    }

    public String printCpuCoreInfo() {
        int i;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new FastPrintWriter((Writer) sw, false, 128);
        int[] SINGLE_STRING_FORMAT = {4128};
        int[] SINGLE_LONG_FORMAT = {8224};
        String[] CPUINFO_PATH = {"/sys/devices/system/cpu/offline", "/sys/devices/system/cpu/online"};
        String[] CPUINFO_EACH_PATH = {"/sys/devices/system/cpu/cpu%d/cpufreq/scaling_cur_freq", "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_governor", "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_max_freq"};
        String[] AP_THERMISTOR_PATH = {"/sys/class/sec/sec-thermistor/temperature", "/sys/devices/platform/sec-thermistor/temperature", "/sys/class/sec/sec-ap-thermistor/temperature"};
        String[] cpuinfo = new String[1];
        long[] thermistor = new long[1];
        int core_num = 0;
        pw.println("------ Current CPU Core Info ------");
        int i2 = 0;
        while (true) {
            i = 47;
            if (i2 >= CPUINFO_PATH.length) {
                break;
            }
            pw.print("- ");
            pw.print(CPUINFO_PATH[i2].substring(CPUINFO_PATH[i2].lastIndexOf(47) + 1));
            pw.print(" : ");
            if (Process.readProcFile(CPUINFO_PATH[i2], SINGLE_STRING_FORMAT, cpuinfo, null, null)) {
                pw.print(cpuinfo[0]);
            } else {
                pw.println(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            i2++;
        }
        int i3 = 0;
        while (true) {
            if (i3 >= AP_THERMISTOR_PATH.length) {
                break;
            }
            if (!Process.readProcFile(String.format(AP_THERMISTOR_PATH[i3], Integer.valueOf(i3)), SINGLE_LONG_FORMAT, null, thermistor, null)) {
                i3++;
            } else {
                pw.print(String.format("- AP Temp = %d\n", Long.valueOf(thermistor[0])));
                break;
            }
        }
        if (Process.readProcFile("/sys/devices/system/cpu/possible", SINGLE_STRING_FORMAT, cpuinfo, null, null)) {
            core_num = Integer.parseInt(cpuinfo[0].substring(cpuinfo[0].length() - 2).trim());
        }
        if (core_num > 0) {
            pw.print("                  ");
            for (int i4 = 0; i4 <= core_num; i4++) {
                pw.print(String.format("%12d", Integer.valueOf(i4)));
            }
            pw.print("\n------------------");
            for (int i5 = 0; i5 <= core_num; i5++) {
                pw.print("------------");
            }
            int i6 = 0;
            while (i6 < CPUINFO_EACH_PATH.length) {
                int[] SINGLE_LONG_FORMAT2 = SINGLE_LONG_FORMAT;
                pw.print(String.format("\n%-18s", CPUINFO_EACH_PATH[i6].substring(CPUINFO_EACH_PATH[i6].lastIndexOf(i) + 1)));
                for (int j = 0; j <= core_num; j++) {
                    if (Process.readProcFile(String.format(CPUINFO_EACH_PATH[i6], Integer.valueOf(j)), SINGLE_STRING_FORMAT, cpuinfo, null, null)) {
                        pw.print(String.format("%12s", cpuinfo[0].trim()));
                    } else {
                        pw.print("           -");
                    }
                }
                i6++;
                SINGLE_LONG_FORMAT = SINGLE_LONG_FORMAT2;
                i = 47;
            }
            pw.print("\n------------------");
            for (int i7 = 0; i7 <= core_num; i7++) {
                pw.print("------------");
            }
            pw.println();
        }
        pw.flush();
        return sw.toString();
    }
}
