package com.android.server.enterprise.application;

import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProcessStats {
    public long mBaseIdleTime;
    public long mBaseIrqTime;
    public long mBaseSystemTime;
    public long mBaseUserTime;
    public int[] mCurPids;
    public int[] mCurThreadPids;
    public long mCurrentSampleTime;
    public final boolean mIncludeThreads;
    public int mRelIdleTime;
    public int mRelIrqTime;
    public int mRelSystemTime;
    public int mRelUserTime;
    public boolean mWorkingProcsSorted;
    public static final int[] PROCESS_STATS_FORMAT = {
        32,
        FrameworkStatsLog.PACKAGE_MANAGER_SNAPSHOT_REPORTED,
        32,
        32,
        32,
        32,
        32,
        32,
        32,
        8224,
        32,
        8224,
        32,
        8224,
        8224
    };
    public static final int[] PROCESS_FULL_STATS_FORMAT = {
        32, 4640, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224, 32, 32, 32, 32, 32,
        32, 8224
    };
    public static final int[] SYSTEM_CPU_FORMAT = {288, 8224, 8224, 8224, 8224, 8224, 8224, 8224};
    public static final int[] LOAD_AVERAGE_FORMAT = {16416, 16416, 16416};
    public static final AnonymousClass1 sLoadComparator = new AnonymousClass1();
    public final long[] mProcessStatsData = new long[4];
    public final String[] mProcessFullStatsStringData = new String[6];
    public final long[] mProcessFullStatsData = new long[6];
    public final long[] mSystemCpuData = new long[7];
    public final float[] mLoadAverageData = new float[3];
    public float mLoad1 = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public float mLoad5 = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public float mLoad15 = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public final ArrayList mProcStats = new ArrayList();
    public final ArrayList mWorkingProcs = new ArrayList();
    public boolean mFirst = true;
    public final byte[] mBuffer = new byte[4096];

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.application.ProcessStats$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            Stats stats = (Stats) obj;
            Stats stats2 = (Stats) obj2;
            int i = stats.rel_utime + stats.rel_stime;
            int i2 = stats2.rel_utime + stats2.rel_stime;
            if (i == i2) {
                boolean z = stats.added;
                if (z == stats2.added) {
                    boolean z2 = stats.removed;
                    if (z2 == stats2.removed) {
                        return 0;
                    }
                    if (!z2) {
                        return 1;
                    }
                } else if (!z) {
                    return 1;
                }
            } else if (i <= i2) {
                return 1;
            }
            return -1;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Stats {
        public boolean active;
        public boolean added;
        public String baseName;
        public long base_majfaults;
        public long base_minfaults;
        public long base_stime;
        public long base_utime;
        public final String cmdlineFile;
        public boolean interesting;
        public String name;
        public final int pid;
        public int rel_stime;
        public int rel_utime;
        public boolean removed;
        public final String statFile;
        public final ArrayList threadStats;
        public final String threadsDir;
        public boolean working;
        public final ArrayList workingThreads;

        public Stats(int i, int i2) {
            this.pid = i;
            if (i2 >= 0) {
                this.statFile =
                        new File(
                                        new File(
                                                new File(
                                                        new File("/proc", Integer.toString(i2)),
                                                        "task"),
                                                Integer.toString(i)),
                                        "stat")
                                .toString();
                this.cmdlineFile = null;
                this.threadsDir = null;
                this.threadStats = null;
                this.workingThreads = null;
                return;
            }
            File file = new File("/proc", Integer.toString(i));
            this.statFile = new File(file, "stat").toString();
            this.cmdlineFile = new File(file, "cmdline").toString();
            this.threadsDir = new File(file, "task").toString();
            this.threadStats = null;
            this.workingThreads = null;
        }
    }

    public ProcessStats() {
        new SparseArray();
    }

    public final int[] collectStats(String str, int i, boolean z, int[] iArr, ArrayList arrayList) {
        int i2;
        int i3;
        String str2;
        Stats stats;
        int i4;
        String str3;
        String str4;
        int[] iArr2;
        int i5;
        String str5;
        String str6;
        int i6;
        int i7;
        Stats stats2;
        String str7;
        int i8;
        int i9;
        int i10;
        String str8;
        long j;
        String str9;
        String str10;
        long j2;
        long j3;
        String str11;
        String str12;
        int i11;
        Stats stats3;
        int i12 = i;
        ArrayList arrayList2 = arrayList;
        int[] pids = Process.getPids(str, iArr);
        int length = pids == null ? 0 : pids.length;
        int size = arrayList.size();
        int i13 = 0;
        int i14 = 0;
        while (i14 < length && (i2 = pids[i14]) >= 0) {
            Stats stats4 = i13 < size ? (Stats) arrayList2.get(i13) : null;
            if (stats4 != null) {
                int i15 = stats4.pid;
                if (i15 == i2) {
                    stats4.added = false;
                    stats4.working = false;
                    int i16 = i13 + 1;
                    StringBuilder sb = new StringBuilder("Existing ");
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            i2, i12 < 0 ? "process" : "thread", " pid ", ": ", sb);
                    sb.append(stats4);
                    Log.v("ProcessStats", sb.toString());
                    if (stats4.interesting) {
                        SystemClock.uptimeMillis();
                        String str13 = stats4.statFile.toString();
                        int[] iArr3 = PROCESS_STATS_FORMAT;
                        long[] jArr = this.mProcessStatsData;
                        if (Process.readProcFile(str13, iArr3, null, jArr, null)) {
                            long j4 = jArr[0];
                            long j5 = jArr[1];
                            long j6 = jArr[2];
                            long j7 = jArr[3];
                            if (j6 == stats4.base_utime && j7 == stats4.base_stime) {
                                stats4.rel_utime = 0;
                                stats4.rel_stime = 0;
                                if (stats4.active) {
                                    stats4.active = false;
                                }
                            } else {
                                if (!stats4.active) {
                                    stats4.active = true;
                                }
                                if (i12 < 0) {
                                    getName(stats4, stats4.cmdlineFile);
                                    ArrayList arrayList3 = stats4.threadStats;
                                    if (arrayList3 != null) {
                                        j2 = j7;
                                        j = j6;
                                        str9 = " stime=";
                                        j3 = j5;
                                        iArr2 = pids;
                                        i4 = length;
                                        str11 = " pid=";
                                        str12 = "Load";
                                        i10 = i14;
                                        i11 = i15;
                                        str8 = " majfaults=";
                                        str10 = " utime=";
                                        i7 = size;
                                        stats3 = stats4;
                                        this.mCurThreadPids =
                                                collectStats(
                                                        stats4.threadsDir,
                                                        i2,
                                                        false,
                                                        this.mCurThreadPids,
                                                        arrayList3);
                                        StringBuilder sb2 = new StringBuilder("Stats changed ");
                                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                                i11, stats3.name, str11, str10, sb2);
                                        sb2.append(j);
                                        sb2.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                                        sb2.append(stats3.base_utime);
                                        long j8 = j2;
                                        BootReceiver$$ExternalSyntheticOutline0.m(
                                                sb2,
                                                str9,
                                                j8,
                                                PackageManagerShellCommandDataLoader.STDIN_PATH);
                                        sb2.append(stats3.base_stime);
                                        BootReceiver$$ExternalSyntheticOutline0.m(
                                                sb2,
                                                " minfaults=",
                                                j4,
                                                PackageManagerShellCommandDataLoader.STDIN_PATH);
                                        sb2.append(stats3.base_minfaults);
                                        long j9 = j3;
                                        BootReceiver$$ExternalSyntheticOutline0.m(
                                                sb2,
                                                str8,
                                                j9,
                                                PackageManagerShellCommandDataLoader.STDIN_PATH);
                                        sb2.append(stats3.base_majfaults);
                                        Log.v(str12, sb2.toString());
                                        stats3.rel_utime = (int) (j - stats3.base_utime);
                                        stats3.rel_stime = (int) (j8 - stats3.base_stime);
                                        stats3.base_utime = j;
                                        stats3.base_stime = j8;
                                        stats3.base_minfaults = j4;
                                        stats3.base_majfaults = j9;
                                        stats3.working = true;
                                        i6 = i;
                                        arrayList2 = arrayList;
                                        i9 = i10;
                                        i13 = i16;
                                        i8 = 1;
                                        i14 = i9 + i8;
                                        i12 = i6;
                                        length = i4;
                                        pids = iArr2;
                                        size = i7;
                                    }
                                }
                                str8 = " majfaults=";
                                j = j6;
                                str9 = " stime=";
                                str10 = " utime=";
                                j2 = j7;
                                j3 = j5;
                                iArr2 = pids;
                                i4 = length;
                                str11 = " pid=";
                                str12 = "Load";
                                i7 = size;
                                i10 = i14;
                                i11 = i15;
                                stats3 = stats4;
                                StringBuilder sb22 = new StringBuilder("Stats changed ");
                                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                        i11, stats3.name, str11, str10, sb22);
                                sb22.append(j);
                                sb22.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                                sb22.append(stats3.base_utime);
                                long j82 = j2;
                                BootReceiver$$ExternalSyntheticOutline0.m(
                                        sb22,
                                        str9,
                                        j82,
                                        PackageManagerShellCommandDataLoader.STDIN_PATH);
                                sb22.append(stats3.base_stime);
                                BootReceiver$$ExternalSyntheticOutline0.m(
                                        sb22,
                                        " minfaults=",
                                        j4,
                                        PackageManagerShellCommandDataLoader.STDIN_PATH);
                                sb22.append(stats3.base_minfaults);
                                long j92 = j3;
                                BootReceiver$$ExternalSyntheticOutline0.m(
                                        sb22,
                                        str8,
                                        j92,
                                        PackageManagerShellCommandDataLoader.STDIN_PATH);
                                sb22.append(stats3.base_majfaults);
                                Log.v(str12, sb22.toString());
                                stats3.rel_utime = (int) (j - stats3.base_utime);
                                stats3.rel_stime = (int) (j82 - stats3.base_stime);
                                stats3.base_utime = j;
                                stats3.base_stime = j82;
                                stats3.base_minfaults = j4;
                                stats3.base_majfaults = j92;
                                stats3.working = true;
                                i6 = i;
                                arrayList2 = arrayList;
                                i9 = i10;
                                i13 = i16;
                                i8 = 1;
                                i14 = i9 + i8;
                                i12 = i6;
                                length = i4;
                                pids = iArr2;
                                size = i7;
                            }
                        }
                    }
                    iArr2 = pids;
                    i4 = length;
                    i7 = size;
                    i10 = i14;
                    i6 = i;
                    arrayList2 = arrayList;
                    i9 = i10;
                    i13 = i16;
                    i8 = 1;
                    i14 = i9 + i8;
                    i12 = i6;
                    length = i4;
                    pids = iArr2;
                    size = i7;
                } else {
                    str2 = " stime=";
                    i4 = length;
                    str4 = " pid=";
                    i5 = i14;
                    str6 = " majfaults=";
                    int i17 = size;
                    stats = stats4;
                    str3 = " utime=";
                    iArr2 = pids;
                    str5 = "Load";
                    i3 = i17;
                }
            } else {
                i3 = size;
                str2 = " stime=";
                stats = stats4;
                i4 = length;
                str3 = " utime=";
                str4 = " pid=";
                iArr2 = pids;
                i5 = i14;
                str5 = "Load";
                str6 = " majfaults=";
            }
            if (stats == null || stats.pid > i2) {
                i6 = i;
                arrayList2 = arrayList;
                Stats stats5 = new Stats(i2, i6);
                arrayList2.add(i13, stats5);
                int i18 = i13 + 1;
                i7 = i3 + 1;
                StringBuilder sb3 = new StringBuilder("New ");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i2, i6 < 0 ? "process" : "thread", " pid ", ": ", sb3);
                sb3.append(stats5);
                Log.v("ProcessStats", sb3.toString());
                SystemClock.uptimeMillis();
                String str14 = stats5.statFile.toString();
                int[] iArr4 = PROCESS_FULL_STATS_FORMAT;
                String[] strArr = this.mProcessFullStatsStringData;
                long[] jArr2 = this.mProcessFullStatsData;
                String str15 = str3;
                if (Process.readProcFile(str14, iArr4, strArr, jArr2, null)) {
                    stats5.interesting = true;
                    stats5.baseName = strArr[0];
                    stats5.base_minfaults = jArr2[1];
                    stats5.base_majfaults = jArr2[2];
                    stats5.base_utime = jArr2[3];
                    stats5.base_stime = jArr2[4];
                } else {
                    NetworkScoreService$$ExternalSyntheticOutline0.m(
                            i2, "Skipping unknown process pid ", "ProcessStats");
                    stats5.baseName = "<unknown>";
                    stats5.base_stime = 0L;
                    stats5.base_utime = 0L;
                    stats5.base_majfaults = 0L;
                    stats5.base_minfaults = 0L;
                }
                if (i6 < 0) {
                    getName(stats5, stats5.cmdlineFile);
                    ArrayList arrayList4 = stats5.threadStats;
                    if (arrayList4 != null) {
                        str7 = str15;
                        this.mCurThreadPids =
                                collectStats(
                                        stats5.threadsDir,
                                        i2,
                                        true,
                                        this.mCurThreadPids,
                                        arrayList4);
                        stats2 = stats5;
                    } else {
                        str7 = str15;
                        stats2 = stats5;
                    }
                } else {
                    stats2 = stats5;
                    str7 = str15;
                    if (stats2.interesting) {
                        stats2.name = stats2.baseName;
                    }
                }
                StringBuilder sb4 = new StringBuilder("Stats added ");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i2, stats2.name, str4, str7, sb4);
                sb4.append(stats2.base_utime);
                sb4.append(str2);
                sb4.append(stats2.base_stime);
                sb4.append(" minfaults=");
                sb4.append(stats2.base_minfaults);
                sb4.append(str6);
                sb4.append(stats2.base_majfaults);
                Log.v(str5, sb4.toString());
                stats2.rel_utime = 0;
                stats2.rel_stime = 0;
                i8 = 1;
                stats2.added = true;
                if (!z && stats2.interesting) {
                    stats2.working = true;
                }
                i9 = i5;
                i13 = i18;
                i14 = i9 + i8;
                i12 = i6;
                length = i4;
                pids = iArr2;
                size = i7;
            } else {
                stats.rel_utime = 0;
                stats.rel_stime = 0;
                stats.removed = true;
                stats.working = true;
                arrayList2 = arrayList;
                arrayList2.remove(i13);
                int i19 = i3 - 1;
                StringBuilder sb5 = new StringBuilder("Removed ");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i2, i < 0 ? "process" : "thread", " pid ", ": ", sb5);
                sb5.append(stats);
                Log.v("ProcessStats", sb5.toString());
                i9 = i5 - 1;
                i7 = i19;
                i6 = i;
                i8 = 1;
                i14 = i9 + i8;
                i12 = i6;
                length = i4;
                pids = iArr2;
                size = i7;
            }
        }
        int[] iArr5 = pids;
        int i20 = size;
        while (i13 < i20) {
            Stats stats6 = (Stats) arrayList2.get(i13);
            stats6.rel_utime = 0;
            stats6.rel_stime = 0;
            stats6.removed = true;
            stats6.working = true;
            arrayList2.remove(i13);
            i20--;
            Log.v("ProcessStats", "Removed pid " + stats6.pid + ": " + stats6);
        }
        return iArr5;
    }

    public final int countWorkingStats() {
        AnonymousClass1 anonymousClass1;
        if (!this.mWorkingProcsSorted) {
            this.mWorkingProcs.clear();
            int size = this.mProcStats.size();
            int i = 0;
            while (true) {
                anonymousClass1 = sLoadComparator;
                if (i >= size) {
                    break;
                }
                Stats stats = (Stats) this.mProcStats.get(i);
                if (stats.working) {
                    this.mWorkingProcs.add(stats);
                    ArrayList arrayList = stats.threadStats;
                    if (arrayList != null && arrayList.size() > 1) {
                        stats.workingThreads.clear();
                        int size2 = stats.threadStats.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            Stats stats2 = (Stats) stats.threadStats.get(i2);
                            if (stats2.working) {
                                stats.workingThreads.add(stats2);
                            }
                        }
                        Collections.sort(stats.workingThreads, anonymousClass1);
                    }
                }
                i++;
            }
            Collections.sort(this.mWorkingProcs, anonymousClass1);
            this.mWorkingProcsSorted = true;
        }
        return this.mWorkingProcs.size();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:15|16|17|18|19|(8:21|(1:25)|28|29|30|31|(2:35|(1:40)(1:39))|(1:42))|47|48|49|(4:33|35|(1:37)|40)|(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0061, code lost:

       if (r3 == null) goto L24;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x005e, code lost:

       if (r3 == null) goto L24;
    */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getName(
            com.android.server.enterprise.application.ProcessStats.Stats r8, java.lang.String r9) {
        /*
            r7 = this;
            java.lang.String r0 = r8.name
            if (r0 == 0) goto L17
            java.lang.String r1 = "app_process"
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto L17
            java.lang.String r1 = r8.name
            java.lang.String r2 = "<pre-initialized>"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L87
        L17:
            byte[] r7 = r7.mBuffer
            android.os.StrictMode$ThreadPolicy r1 = android.os.StrictMode.allowThreadDiskReads()
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L53
            r3.<init>(r9)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L53
            int r9 = r3.read(r7)     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L5e java.io.FileNotFoundException -> L61
            r3.close()     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L5e java.io.FileNotFoundException -> L61
            if (r9 <= 0) goto L48
            r4 = 0
            r5 = r4
        L2e:
            if (r5 >= r9) goto L3b
            r6 = r7[r5]     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L5e java.io.FileNotFoundException -> L61
            if (r6 != 0) goto L35
            goto L3b
        L35:
            int r5 = r5 + 1
            goto L2e
        L38:
            r7 = move-exception
            r2 = r3
            goto L55
        L3b:
            java.lang.String r9 = new java.lang.String     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L5e java.io.FileNotFoundException -> L61
            r9.<init>(r7, r4, r5)     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L5e java.io.FileNotFoundException -> L61
            r3.close()     // Catch: java.io.IOException -> L43
        L43:
            android.os.StrictMode.setThreadPolicy(r1)
            r2 = r9
            goto L64
        L48:
            r3.close()     // Catch: java.io.IOException -> L4b
        L4b:
            android.os.StrictMode.setThreadPolicy(r1)
            goto L64
        L4f:
            r7 = move-exception
            goto L55
        L51:
            r3 = r2
            goto L5e
        L53:
            r3 = r2
            goto L61
        L55:
            if (r2 == 0) goto L5a
            r2.close()     // Catch: java.io.IOException -> L5a
        L5a:
            android.os.StrictMode.setThreadPolicy(r1)
            throw r7
        L5e:
            if (r3 == 0) goto L4b
            goto L48
        L61:
            if (r3 == 0) goto L4b
            goto L48
        L64:
            if (r2 == 0) goto L83
            int r7 = r2.length()
            r9 = 1
            if (r7 <= r9) goto L83
            java.lang.String r7 = "/"
            int r7 = r2.lastIndexOf(r7)
            if (r7 <= 0) goto L82
            int r0 = r2.length()
            int r0 = r0 - r9
            if (r7 >= r0) goto L82
            int r7 = r7 + r9
            java.lang.String r0 = r2.substring(r7)
            goto L83
        L82:
            r0 = r2
        L83:
            if (r0 != 0) goto L87
            java.lang.String r0 = r8.baseName
        L87:
            java.lang.String r7 = r8.name
            if (r7 == 0) goto L91
            boolean r7 = r0.equals(r7)
            if (r7 != 0) goto L93
        L91:
            r8.name = r0
        L93:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.application.ProcessStats.getName(com.android.server.enterprise.application.ProcessStats$Stats,"
                    + " java.lang.String):void");
    }

    public final void update() {
        ProcessStats processStats;
        Log.v("ProcessStats", "Update: " + this);
        this.mCurrentSampleTime = SystemClock.uptimeMillis();
        SystemClock.elapsedRealtime();
        int[] iArr = SYSTEM_CPU_FORMAT;
        long[] jArr = this.mSystemCpuData;
        if (Process.readProcFile("/proc/stat", iArr, null, jArr, null)) {
            long j = jArr[0] + jArr[1];
            long j2 = jArr[2];
            long j3 = jArr[3];
            long j4 = jArr[4];
            long j5 = jArr[5];
            long j6 = jArr[6];
            this.mRelUserTime = (int) (j - this.mBaseUserTime);
            this.mRelSystemTime = (int) (j2 - this.mBaseSystemTime);
            this.mRelIrqTime = (int) (j5 - this.mBaseIrqTime);
            this.mRelIdleTime = (int) (j3 - this.mBaseIdleTime);
            Log.i(
                    "Load",
                    "Total U:"
                            + jArr[0]
                            + " N:"
                            + jArr[1]
                            + " S:"
                            + jArr[2]
                            + " I:"
                            + jArr[3]
                            + " W:"
                            + jArr[4]
                            + " Q:"
                            + jArr[5]
                            + " O:"
                            + jArr[6]);
            StringBuilder sb = new StringBuilder("Rel U:");
            processStats = this;
            sb.append(processStats.mRelUserTime);
            sb.append(" S:");
            sb.append(processStats.mRelSystemTime);
            sb.append(" I:");
            sb.append(processStats.mRelIdleTime);
            sb.append(" Q:");
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    sb, processStats.mRelIrqTime, "Load");
            processStats.mBaseUserTime = j;
            processStats.mBaseSystemTime = j2;
            processStats.mBaseIrqTime = j5;
            processStats.mBaseIdleTime = j3;
        } else {
            processStats = this;
        }
        processStats.mCurPids =
                collectStats(
                        "/proc",
                        -1,
                        processStats.mFirst,
                        processStats.mCurPids,
                        processStats.mProcStats);
        int[] iArr2 = LOAD_AVERAGE_FORMAT;
        float[] fArr = processStats.mLoadAverageData;
        if (Process.readProcFile("/proc/loadavg", iArr2, null, null, fArr)) {
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            if (f != processStats.mLoad1
                    || f2 != processStats.mLoad5
                    || f3 != processStats.mLoad15) {
                processStats.mLoad1 = f;
                processStats.mLoad5 = f2;
                processStats.mLoad15 = f3;
            }
        }
        Log.i(
                "ProcessStats",
                "*** TIME TO COLLECT STATS: "
                        + (SystemClock.uptimeMillis() - processStats.mCurrentSampleTime));
        processStats.mWorkingProcsSorted = false;
        processStats.mFirst = false;
    }
}
