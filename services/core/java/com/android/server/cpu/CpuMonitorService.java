package com.android.server.cpu;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseArrayMap;

import com.android.internal.util.DumpUtils;
import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.utils.PriorityDump;
import com.android.server.utils.Slogf;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CpuMonitorService extends SystemService {
    public static final long CACHE_DURATION_MILLISECONDS;
    public static final boolean DEBUG = Log.isLoggable("CpuMonitorService", 3);
    public static final long DEBUG_MONITORING_INTERVAL_MILLISECONDS;
    public static final long LATEST_AVAILABILITY_DURATION_MILLISECONDS;
    public static final long NORMAL_MONITORING_INTERVAL_MILLISECONDS;
    public final SparseArrayMap mAvailabilityCallbackInfosByCallbacksByCpuset;
    public final Context mContext;
    public final CpuInfoReader mCpuInfoReader;
    public final SparseArray mCpusetInfosByCpuset;
    public long mCurrentMonitoringIntervalMillis;
    public final long mDebugMonitoringIntervalMillis;
    public Handler mHandler;
    public final HandlerThread mHandlerThread;
    public final long mLatestAvailabilityDurationMillis;
    public final AnonymousClass1 mLocalService;
    public final Object mLock;
    public final CpuMonitorService$$ExternalSyntheticLambda0 mMonitorCpuStats;
    public final long mNormalMonitoringIntervalMillis;
    public final boolean mShouldDebugMonitor;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.cpu.CpuMonitorService$1, reason: invalid class name */
    public final class AnonymousClass1 {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CpuMonitorBinder extends Binder {
        public final AnonymousClass1 mPriorityDumper =
                new PriorityDump
                        .PriorityDumper() { // from class:
                                            // com.android.server.cpu.CpuMonitorService.CpuMonitorBinder.1
                    @Override // com.android.server.utils.PriorityDump.PriorityDumper
                    public final void dumpCritical(
                            FileDescriptor fileDescriptor,
                            PrintWriter printWriter,
                            String[] strArr,
                            boolean z) {
                        CpuMonitorBinder cpuMonitorBinder = CpuMonitorBinder.this;
                        Context context = CpuMonitorService.this.mContext;
                        boolean z2 = CpuMonitorService.DEBUG;
                        if (!DumpUtils.checkDumpAndUsageStatsPermission(
                                        context, "CpuMonitorService", printWriter)
                                || z) {
                            return;
                        }
                        IndentingPrintWriter indentingPrintWriter =
                                new IndentingPrintWriter(printWriter);
                        try {
                            CpuMonitorService.m387$$Nest$mdoDump(
                                    CpuMonitorService.this, indentingPrintWriter);
                            indentingPrintWriter.close();
                        } catch (Throwable th) {
                            try {
                                indentingPrintWriter.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                };

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.cpu.CpuMonitorService$CpuMonitorBinder$1] */
        public CpuMonitorBinder() {}

        @Override // android.os.Binder
        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            PriorityDump.dump(this.mPriorityDumper, fileDescriptor, printWriter, strArr);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CpusetInfo {
        public final int cpuset;
        public CpuAvailabilityInfo mLatestCpuAvailabilityInfo;
        public final LongSparseArray mSnapshotsByUptime = new LongSparseArray();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Snapshot {
            public long totalNormalizedAvailableCpuFreqKHz;
            public int totalOfflineCpus;
            public long totalOfflineMaxCpuFreqKHz;
            public int totalOnlineCpus;
            public long totalOnlineMaxCpuFreqKHz;
            public final long uptimeMillis;

            public Snapshot(long j) {
                this.uptimeMillis = j;
            }

            public final int getAverageAvailableCpuFreqPercent() {
                int i =
                        (int)
                                ((this.totalNormalizedAvailableCpuFreqKHz * 100.0d)
                                        / this.totalOnlineMaxCpuFreqKHz);
                if (i >= 0) {
                    return i;
                }
                boolean z = CpuMonitorService.DEBUG;
                Slogf.wtf(
                        "CpuMonitorService",
                        "Computed negative CPU availability percent(%d) for %s ",
                        Integer.valueOf(i),
                        toString());
                return 0;
            }

            public final String toString() {
                return "Snapshot{uptimeMillis = "
                        + this.uptimeMillis
                        + ", totalOnlineCpus = "
                        + this.totalOnlineCpus
                        + ", totalOfflineCpus = "
                        + this.totalOfflineCpus
                        + ", totalNormalizedAvailableCpuFreqKHz = "
                        + this.totalNormalizedAvailableCpuFreqKHz
                        + ", totalOnlineMaxCpuFreqKHz = "
                        + this.totalOnlineMaxCpuFreqKHz
                        + ", totalOfflineMaxCpuFreqKHz = "
                        + this.totalOfflineMaxCpuFreqKHz
                        + '}';
            }
        }

        public CpusetInfo(int i) {
            this.cpuset = i;
        }

        public final void appendCpuInfo(long j, CpuInfoReader.CpuInfo cpuInfo) {
            int i = cpuInfo.cpusetCategories;
            int i2 = this.cpuset;
            if (i2 != 1) {
                if (i2 != 2) {
                    Slogf.wtf(
                            "CpuMonitorService",
                            "Provided invalid expectedCpuset %d",
                            Integer.valueOf(i2));
                    return;
                } else if ((i & 2) == 0) {
                    return;
                }
            } else if ((i & 1) == 0) {
                return;
            }
            Snapshot snapshot = (Snapshot) this.mSnapshotsByUptime.get(j);
            if (snapshot == null) {
                snapshot = new Snapshot(j);
                this.mSnapshotsByUptime.append(j, snapshot);
                if (this.mSnapshotsByUptime.size() > 0
                        && j - ((Snapshot) this.mSnapshotsByUptime.valueAt(0)).uptimeMillis
                                > CpuMonitorService.CACHE_DURATION_MILLISECONDS) {
                    this.mSnapshotsByUptime.removeAt(0);
                }
            }
            boolean z = cpuInfo.isOnline;
            long j2 = cpuInfo.maxCpuFreqKHz;
            if (!z) {
                snapshot.totalOfflineCpus++;
                snapshot.totalOfflineMaxCpuFreqKHz += j2;
            } else {
                snapshot.totalOnlineCpus++;
                snapshot.totalNormalizedAvailableCpuFreqKHz +=
                        cpuInfo.mNormalizedAvailableCpuFreqKHz;
                snapshot.totalOnlineMaxCpuFreqKHz += j2;
            }
        }

        public final void populateLatestCpuAvailabilityInfo(long j, long j2) {
            long j3;
            int size = this.mSnapshotsByUptime.size();
            if (size == 0) {
                this.mLatestCpuAvailabilityInfo = null;
                return;
            }
            Snapshot snapshot = (Snapshot) this.mSnapshotsByUptime.valueAt(size - 1);
            long j4 = snapshot.uptimeMillis;
            if (j4 != j) {
                if (CpuMonitorService.DEBUG) {
                    int i = this.cpuset;
                    Slogf.d(
                            "CpuMonitorService",
                            "Skipping stale CPU availability information for cpuset %s",
                            i != 1
                                    ? i != 2
                                            ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                    i, "Invalid cpuset: ")
                                            : "CPUSET_BACKGROUND"
                                    : "CPUSET_ALL");
                }
                this.mLatestCpuAvailabilityInfo = null;
                return;
            }
            CpuAvailabilityInfo cpuAvailabilityInfo = this.mLatestCpuAvailabilityInfo;
            if (cpuAvailabilityInfo == null
                    || cpuAvailabilityInfo.dataTimestampUptimeMillis != j4) {
                long j5 = j - j2;
                int averageAvailableCpuFreqPercent = snapshot.getAverageAvailableCpuFreqPercent();
                int size2 = this.mSnapshotsByUptime.size() - 1;
                long j6 = 0;
                int i2 = 0;
                long j7 = Long.MAX_VALUE;
                long j8 = 0;
                while (true) {
                    if (size2 < 0) {
                        j3 = j4;
                        break;
                    }
                    Snapshot snapshot2 = (Snapshot) this.mSnapshotsByUptime.valueAt(size2);
                    j3 = j4;
                    long j9 = snapshot2.uptimeMillis;
                    if (j9 <= j5) {
                        j7 = j9;
                        break;
                    }
                    i2++;
                    j7 = j9;
                    j6 += snapshot2.totalNormalizedAvailableCpuFreqKHz;
                    j8 += snapshot2.totalOnlineMaxCpuFreqKHz;
                    size2--;
                    j4 = j3;
                }
                this.mLatestCpuAvailabilityInfo =
                        new CpuAvailabilityInfo(
                                this.cpuset,
                                averageAvailableCpuFreqPercent,
                                (j7 > j5 || i2 < 2) ? -1 : (int) ((j6 * 100.0d) / j8),
                                j3,
                                j2);
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CpusetInfo{cpuset = ");
            int i = this.cpuset;
            sb.append(
                    i != 1
                            ? i != 2
                                    ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                            i, "Invalid cpuset: ")
                                    : "CPUSET_BACKGROUND"
                            : "CPUSET_ALL");
            sb.append(", mSnapshotsByUptime = ");
            sb.append(this.mSnapshotsByUptime);
            sb.append(", mLatestCpuAvailabilityInfo = ");
            sb.append(this.mLatestCpuAvailabilityInfo);
            sb.append('}');
            return sb.toString();
        }
    }

    /* renamed from: -$$Nest$mdoDump, reason: not valid java name */
    public static void m387$$Nest$mdoDump(
            CpuMonitorService cpuMonitorService, final IndentingPrintWriter indentingPrintWriter) {
        cpuMonitorService.getClass();
        indentingPrintWriter.printf("*%s*\n", new Object[] {"CpuMonitorService"});
        indentingPrintWriter.increaseIndent();
        CpuInfoReader cpuInfoReader = cpuMonitorService.mCpuInfoReader;
        cpuInfoReader.getClass();
        indentingPrintWriter.printf("*%s*\n", new Object[] {"CpuInfoReader"});
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf(
                "mCpusetDir = %s\n", new Object[] {cpuInfoReader.mCpusetDir.getAbsolutePath()});
        indentingPrintWriter.printf(
                "mCpuFreqDir = %s\n", new Object[] {cpuInfoReader.mCpuFreqDir.getAbsolutePath()});
        indentingPrintWriter.printf(
                "mProcStatFile = %s\n",
                new Object[] {cpuInfoReader.mProcStatFile.getAbsolutePath()});
        indentingPrintWriter.printf(
                "mIsEnabled = %s\n", new Object[] {Boolean.valueOf(cpuInfoReader.mIsEnabled)});
        indentingPrintWriter.printf(
                "mHasTimeInStateFile = %s\n",
                new Object[] {Boolean.valueOf(cpuInfoReader.mHasTimeInStateFile)});
        indentingPrintWriter.printf(
                "mLastReadUptimeMillis = %d\n",
                new Object[] {Long.valueOf(cpuInfoReader.mLastReadUptimeMillis)});
        indentingPrintWriter.printf(
                "mMinReadIntervalMillis = %d\n",
                new Object[] {Long.valueOf(cpuInfoReader.mMinReadIntervalMillis)});
        indentingPrintWriter.printf("Cpuset categories by CPU core:\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < cpuInfoReader.mCpusetCategoriesByCpus.size(); i++) {
            indentingPrintWriter.printf(
                    "CPU core id = %d, %s\n",
                    new Object[] {
                        Integer.valueOf(cpuInfoReader.mCpusetCategoriesByCpus.keyAt(i)),
                        CpuInfoReader.toCpusetCategoriesStr(
                                cpuInfoReader.mCpusetCategoriesByCpus.valueAt(i))
                    });
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Cpu frequency policy directories by policy id:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < cpuInfoReader.mCpuFreqPolicyDirsById.size(); i2++) {
            indentingPrintWriter.printf(
                    "Policy id = %d, Dir = %s\n",
                    new Object[] {
                        Integer.valueOf(cpuInfoReader.mCpuFreqPolicyDirsById.keyAt(i2)),
                        cpuInfoReader.mCpuFreqPolicyDirsById.valueAt(i2)
                    });
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Static cpu frequency policy infos by policy id:");
        indentingPrintWriter.increaseIndent();
        for (int i3 = 0; i3 < cpuInfoReader.mStaticPolicyInfoById.size(); i3++) {
            indentingPrintWriter.printf(
                    "Policy id = %d, %s\n",
                    new Object[] {
                        Integer.valueOf(cpuInfoReader.mStaticPolicyInfoById.keyAt(i3)),
                        cpuInfoReader.mStaticPolicyInfoById.valueAt(i3)
                    });
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Cpu time in frequency state by policy id:");
        indentingPrintWriter.increaseIndent();
        for (int i4 = 0; i4 < cpuInfoReader.mTimeInStateByPolicyId.size(); i4++) {
            indentingPrintWriter.printf(
                    "Policy id = %d, Time(millis) in state by CPU frequency(KHz) = %s\n",
                    new Object[] {
                        Integer.valueOf(cpuInfoReader.mTimeInStateByPolicyId.keyAt(i4)),
                        cpuInfoReader.mTimeInStateByPolicyId.valueAt(i4)
                    });
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Last read CPU infos:");
        indentingPrintWriter.increaseIndent();
        for (int i5 = 0; i5 < cpuInfoReader.mLastReadCpuInfos.size(); i5++) {
            indentingPrintWriter.printf(
                    "%s\n", new Object[] {cpuInfoReader.mLastReadCpuInfos.valueAt(i5)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Latest cumulative CPU usage stats by CPU core:");
        indentingPrintWriter.increaseIndent();
        for (int i6 = 0; i6 < cpuInfoReader.mCumulativeCpuUsageStats.size(); i6++) {
            indentingPrintWriter.printf(
                    "CPU core id = %d, %s\n",
                    new Object[] {
                        Integer.valueOf(cpuInfoReader.mCumulativeCpuUsageStats.keyAt(i6)),
                        cpuInfoReader.mCumulativeCpuUsageStats.valueAt(i6)
                    });
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.printf(
                "mShouldDebugMonitor = %s\n",
                new Object[] {cpuMonitorService.mShouldDebugMonitor ? "Yes" : "No"});
        indentingPrintWriter.printf(
                "mNormalMonitoringIntervalMillis = %d\n",
                new Object[] {Long.valueOf(cpuMonitorService.mNormalMonitoringIntervalMillis)});
        indentingPrintWriter.printf(
                "mDebugMonitoringIntervalMillis = %d\n",
                new Object[] {Long.valueOf(cpuMonitorService.mDebugMonitoringIntervalMillis)});
        indentingPrintWriter.printf(
                "mLatestAvailabilityDurationMillis = %d\n",
                new Object[] {Long.valueOf(cpuMonitorService.mLatestAvailabilityDurationMillis)});
        synchronized (cpuMonitorService.mLock) {
            try {
                indentingPrintWriter.printf(
                        "mCurrentMonitoringIntervalMillis = %d\n",
                        new Object[] {
                            Long.valueOf(cpuMonitorService.mCurrentMonitoringIntervalMillis)
                        });
                int i7 = 0;
                while (true) {
                    if (i7
                            >= cpuMonitorService.mAvailabilityCallbackInfosByCallbacksByCpuset
                                    .numMaps()) {
                        break;
                    }
                    if (cpuMonitorService.mAvailabilityCallbackInfosByCallbacksByCpuset
                                    .numElementsForKeyAt(i7)
                            > 0) {
                        indentingPrintWriter.println("CPU availability change callbacks:");
                        indentingPrintWriter.increaseIndent();
                        cpuMonitorService.mAvailabilityCallbackInfosByCallbacksByCpuset.forEach(
                                new Consumer() { // from class:
                                                 // com.android.server.cpu.CpuMonitorService$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        IndentingPrintWriter indentingPrintWriter2 =
                                                indentingPrintWriter;
                                        DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                        indentingPrintWriter2.printf("%s\n", new Object[] {null});
                                    }
                                });
                        indentingPrintWriter.decreaseIndent();
                        break;
                    }
                    i7++;
                }
                if (cpuMonitorService.mCpusetInfosByCpuset.size() > 0) {
                    indentingPrintWriter.println("Cpuset infos:");
                    indentingPrintWriter.increaseIndent();
                    for (int i8 = 0; i8 < cpuMonitorService.mCpusetInfosByCpuset.size(); i8++) {
                        indentingPrintWriter.printf(
                                "%s\n",
                                new Object[] {cpuMonitorService.mCpusetInfosByCpuset.valueAt(i8)});
                    }
                    indentingPrintWriter.decreaseIndent();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        NORMAL_MONITORING_INTERVAL_MILLISECONDS = timeUnit.toMillis(5L);
        TimeUnit timeUnit2 = TimeUnit.MINUTES;
        DEBUG_MONITORING_INTERVAL_MILLISECONDS = timeUnit2.toMillis(1L);
        CACHE_DURATION_MILLISECONDS =
                (Build.IS_USERDEBUG || Build.IS_ENG)
                        ? timeUnit2.toMillis(30L)
                        : timeUnit2.toMillis(10L);
        LATEST_AVAILABILITY_DURATION_MILLISECONDS = timeUnit.toMillis(30L);
    }

    public CpuMonitorService(Context context) {
        this(
                context,
                new CpuInfoReader(
                        new File("/dev/cpuset"),
                        new File("/sys/devices/system/cpu/cpufreq"),
                        new File("/proc/stat"),
                        500L),
                new ServiceThread(10, "CpuMonitorService", true),
                Build.IS_USERDEBUG || Build.IS_ENG,
                NORMAL_MONITORING_INTERVAL_MILLISECONDS,
                DEBUG_MONITORING_INTERVAL_MILLISECONDS,
                LATEST_AVAILABILITY_DURATION_MILLISECONDS);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.cpu.CpuMonitorService$$ExternalSyntheticLambda0] */
    public CpuMonitorService(
            Context context,
            CpuInfoReader cpuInfoReader,
            HandlerThread handlerThread,
            boolean z,
            long j,
            long j2,
            long j3) {
        super(context);
        this.mLock = new Object();
        this.mMonitorCpuStats =
                new Runnable() { // from class:
                    // com.android.server.cpu.CpuMonitorService$$ExternalSyntheticLambda0
                    /* JADX WARN: Code restructure failed: missing block: B:48:0x0092, code lost:

                       if (r9.mShouldDebugMonitor != false) goto L38;
                    */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            r9 = this;
                            com.android.server.cpu.CpuMonitorService r9 = com.android.server.cpu.CpuMonitorService.this
                            r9.getClass()
                            long r0 = android.os.SystemClock.uptimeMillis()
                            android.os.Handler r2 = r9.mHandler
                            com.android.server.cpu.CpuMonitorService$$ExternalSyntheticLambda0 r3 = r9.mMonitorCpuStats
                            r2.removeCallbacks(r3)
                            com.android.server.cpu.CpuInfoReader r2 = r9.mCpuInfoReader
                            android.util.SparseArray r2 = r2.readCpuInfos()
                            if (r2 != 0) goto L2b
                            java.lang.String r0 = "CpuMonitorService"
                            java.lang.String r1 = "Failed to read CPU info from device"
                            com.android.server.utils.Slogf.wtf(r0, r1)
                            java.lang.Object r3 = r9.mLock
                            monitor-enter(r3)
                            r9.stopMonitoringCpuStatsLocked()     // Catch: java.lang.Throwable -> L28
                            monitor-exit(r3)     // Catch: java.lang.Throwable -> L28
                            goto La3
                        L28:
                            r9 = move-exception
                            monitor-exit(r3)     // Catch: java.lang.Throwable -> L28
                            throw r9
                        L2b:
                            java.lang.Object r3 = r9.mLock
                            monitor-enter(r3)
                            r4 = 0
                            r5 = r4
                        L30:
                            int r6 = r2.size()     // Catch: java.lang.Throwable -> L53
                            if (r5 >= r6) goto L58
                            java.lang.Object r6 = r2.valueAt(r5)     // Catch: java.lang.Throwable -> L53
                            com.android.server.cpu.CpuInfoReader$CpuInfo r6 = (com.android.server.cpu.CpuInfoReader.CpuInfo) r6     // Catch: java.lang.Throwable -> L53
                            r7 = r4
                        L3d:
                            android.util.SparseArray r8 = r9.mCpusetInfosByCpuset     // Catch: java.lang.Throwable -> L53
                            int r8 = r8.size()     // Catch: java.lang.Throwable -> L53
                            if (r7 >= r8) goto L55
                            android.util.SparseArray r8 = r9.mCpusetInfosByCpuset     // Catch: java.lang.Throwable -> L53
                            java.lang.Object r8 = r8.valueAt(r7)     // Catch: java.lang.Throwable -> L53
                            com.android.server.cpu.CpuMonitorService$CpusetInfo r8 = (com.android.server.cpu.CpuMonitorService.CpusetInfo) r8     // Catch: java.lang.Throwable -> L53
                            r8.appendCpuInfo(r0, r6)     // Catch: java.lang.Throwable -> L53
                            int r7 = r7 + 1
                            goto L3d
                        L53:
                            r9 = move-exception
                            goto La4
                        L55:
                            int r5 = r5 + 1
                            goto L30
                        L58:
                            r2 = r4
                        L59:
                            android.util.SparseArray r5 = r9.mCpusetInfosByCpuset     // Catch: java.lang.Throwable -> L53
                            int r5 = r5.size()     // Catch: java.lang.Throwable -> L53
                            if (r2 >= r5) goto L74
                            android.util.SparseArray r5 = r9.mCpusetInfosByCpuset     // Catch: java.lang.Throwable -> L53
                            java.lang.Object r5 = r5.valueAt(r2)     // Catch: java.lang.Throwable -> L53
                            com.android.server.cpu.CpuMonitorService$CpusetInfo r5 = (com.android.server.cpu.CpuMonitorService.CpusetInfo) r5     // Catch: java.lang.Throwable -> L53
                            long r6 = r9.mLatestAvailabilityDurationMillis     // Catch: java.lang.Throwable -> L53
                            r5.populateLatestCpuAvailabilityInfo(r0, r6)     // Catch: java.lang.Throwable -> L53
                            r9.checkClientThresholdsAndNotifyLocked(r5)     // Catch: java.lang.Throwable -> L53
                            int r2 = r2 + 1
                            goto L59
                        L74:
                            long r5 = r9.mCurrentMonitoringIntervalMillis     // Catch: java.lang.Throwable -> L53
                            r7 = 0
                            int r2 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                            if (r2 <= 0) goto L9f
                        L7c:
                            android.util.SparseArrayMap r2 = r9.mAvailabilityCallbackInfosByCallbacksByCpuset     // Catch: java.lang.Throwable -> L53
                            int r2 = r2.numMaps()     // Catch: java.lang.Throwable -> L53
                            if (r4 >= r2) goto L90
                            android.util.SparseArrayMap r2 = r9.mAvailabilityCallbackInfosByCallbacksByCpuset     // Catch: java.lang.Throwable -> L53
                            int r2 = r2.numElementsForKeyAt(r4)     // Catch: java.lang.Throwable -> L53
                            if (r2 <= 0) goto L8d
                            goto L94
                        L8d:
                            int r4 = r4 + 1
                            goto L7c
                        L90:
                            boolean r2 = r9.mShouldDebugMonitor     // Catch: java.lang.Throwable -> L53
                            if (r2 == 0) goto L9f
                        L94:
                            android.os.Handler r2 = r9.mHandler     // Catch: java.lang.Throwable -> L53
                            com.android.server.cpu.CpuMonitorService$$ExternalSyntheticLambda0 r4 = r9.mMonitorCpuStats     // Catch: java.lang.Throwable -> L53
                            long r5 = r9.mCurrentMonitoringIntervalMillis     // Catch: java.lang.Throwable -> L53
                            long r0 = r0 + r5
                            r2.postAtTime(r4, r0)     // Catch: java.lang.Throwable -> L53
                            goto La2
                        L9f:
                            r9.stopMonitoringCpuStatsLocked()     // Catch: java.lang.Throwable -> L53
                        La2:
                            monitor-exit(r3)     // Catch: java.lang.Throwable -> L53
                        La3:
                            return
                        La4:
                            monitor-exit(r3)     // Catch: java.lang.Throwable -> L53
                            throw r9
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.cpu.CpuMonitorService$$ExternalSyntheticLambda0.run():void");
                    }
                };
        this.mCurrentMonitoringIntervalMillis = -1L;
        this.mLocalService = new AnonymousClass1();
        this.mContext = context;
        this.mHandlerThread = handlerThread;
        this.mShouldDebugMonitor = z;
        this.mNormalMonitoringIntervalMillis = j;
        this.mDebugMonitoringIntervalMillis = j2;
        this.mLatestAvailabilityDurationMillis = j3;
        this.mCpuInfoReader = cpuInfoReader;
        SparseArray sparseArray = new SparseArray(2);
        this.mCpusetInfosByCpuset = sparseArray;
        sparseArray.append(1, new CpusetInfo(1));
        sparseArray.append(2, new CpusetInfo(2));
        this.mAvailabilityCallbackInfosByCallbacksByCpuset = new SparseArrayMap();
    }

    public final void checkClientThresholdsAndNotifyLocked(CpusetInfo cpusetInfo) {
        int size = cpusetInfo.mSnapshotsByUptime.size();
        int averageAvailableCpuFreqPercent =
                size < 2
                        ? -1
                        : ((CpusetInfo.Snapshot) cpusetInfo.mSnapshotsByUptime.valueAt(size - 2))
                                .getAverageAvailableCpuFreqPercent();
        if (cpusetInfo.mLatestCpuAvailabilityInfo == null
                || averageAvailableCpuFreqPercent < 0
                || this.mAvailabilityCallbackInfosByCallbacksByCpuset.numElementsForKey(
                                cpusetInfo.cpuset)
                        == 0) {
            return;
        }
        for (int i = 0; i < this.mAvailabilityCallbackInfosByCallbacksByCpuset.numMaps(); i++) {
            if (this.mAvailabilityCallbackInfosByCallbacksByCpuset.numElementsForKeyAt(i) > 0) {
                DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(
                        this.mAvailabilityCallbackInfosByCallbacksByCpuset.valueAt(i, 0));
                throw null;
            }
        }
    }

    public long getCurrentMonitoringIntervalMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.mCurrentMonitoringIntervalMillis;
        }
        return j;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        boolean z;
        int i;
        boolean z2;
        CpuInfoReader cpuInfoReader = this.mCpuInfoReader;
        if (cpuInfoReader.mCpuFreqPolicyDirsById.size() > 0) {
            Slogf.w("CpuMonitorService", "Ignoring duplicate CpuInfoReader init request");
            z = cpuInfoReader.mIsEnabled;
        } else {
            File[] listFiles =
                    cpuInfoReader.mCpuFreqDir.listFiles(
                            new CpuInfoReader$$ExternalSyntheticLambda0(1));
            if (listFiles == null || listFiles.length == 0) {
                Slogf.w(
                        "CpuMonitorService",
                        "Missing CPU frequency policy directories at %s",
                        cpuInfoReader.mCpuFreqDir.getAbsolutePath());
            } else {
                cpuInfoReader.populateCpuFreqPolicyDirsById(listFiles);
                if (cpuInfoReader.mCpuFreqPolicyDirsById.size() == 0) {
                    Slogf.e(
                            "CpuMonitorService",
                            "Failed to parse CPU frequency policy directory paths: %s",
                            Arrays.toString(listFiles));
                } else {
                    for (int i2 = 0; i2 < cpuInfoReader.mCpuFreqPolicyDirsById.size(); i2++) {
                        int keyAt = cpuInfoReader.mCpuFreqPolicyDirsById.keyAt(i2);
                        File file =
                                new File(
                                        (File) cpuInfoReader.mCpuFreqPolicyDirsById.valueAt(i2),
                                        "related_cpus");
                        IntArray readCpuCores = CpuInfoReader.readCpuCores(file);
                        if (readCpuCores == null || readCpuCores.size() == 0) {
                            Slogf.e(
                                    "CpuMonitorService",
                                    "Failed to read related CPU cores from %s",
                                    file.getAbsolutePath());
                        } else {
                            CpuInfoReader.StaticPolicyInfo staticPolicyInfo =
                                    new CpuInfoReader.StaticPolicyInfo(readCpuCores);
                            cpuInfoReader.mStaticPolicyInfoById.append(keyAt, staticPolicyInfo);
                            if (DEBUG) {
                                Slogf.d(
                                        "CpuMonitorService",
                                        "Added static policy info %s for policy id %d",
                                        staticPolicyInfo,
                                        Integer.valueOf(keyAt));
                            }
                        }
                    }
                    if (cpuInfoReader.mStaticPolicyInfoById.size() == 0) {
                        Slogf.e(
                                "CpuMonitorService",
                                "Failed to read static CPU frequency policy info from policy dirs:"
                                    + " %s",
                                Arrays.toString(listFiles));
                    } else if (cpuInfoReader.mProcStatFile.exists()) {
                        File[] listFiles2 =
                                cpuInfoReader.mCpusetDir.listFiles(
                                        new CpuInfoReader$$ExternalSyntheticLambda0(2));
                        if (listFiles2 == null) {
                            Slogf.e(
                                    "CpuMonitorService",
                                    "Missing cpuset directories at %s",
                                    cpuInfoReader.mCpusetDir.getAbsolutePath());
                        } else {
                            for (File file2 : listFiles2) {
                                String name = file2.getName();
                                name.getClass();
                                if (name.equals("background")) {
                                    i = 2;
                                } else if (name.equals("top-app")) {
                                    i = 1;
                                }
                                File file3 = new File(file2.getPath(), "cpus");
                                IntArray readCpuCores2 = CpuInfoReader.readCpuCores(file3);
                                if (readCpuCores2 == null || readCpuCores2.size() == 0) {
                                    Slogf.e(
                                            "CpuMonitorService",
                                            "Failed to read CPU cores from %s",
                                            file3.getAbsolutePath());
                                } else {
                                    for (int i3 = 0; i3 < readCpuCores2.size(); i3++) {
                                        int i4 =
                                                cpuInfoReader.mCpusetCategoriesByCpus.get(
                                                                readCpuCores2.get(i3))
                                                        | i;
                                        cpuInfoReader.mCpusetCategoriesByCpus.append(
                                                readCpuCores2.get(i3), i4);
                                        if (DEBUG) {
                                            Slogf.d(
                                                    "CpuMonitorService",
                                                    "Mapping CPU core id %d with cpuset categories"
                                                        + " [%s]",
                                                    Integer.valueOf(readCpuCores2.get(i3)),
                                                    CpuInfoReader.toCpusetCategoriesStr(i4));
                                        }
                                    }
                                }
                            }
                        }
                        if (cpuInfoReader.mCpusetCategoriesByCpus.size() == 0) {
                            Slogf.e(
                                    "CpuMonitorService",
                                    "Failed to read cpuset information from %s",
                                    cpuInfoReader.mCpusetDir.getAbsolutePath());
                        } else {
                            for (int i5 = 0;
                                    i5 < cpuInfoReader.mCpuFreqPolicyDirsById.size()
                                            && !(z2 = cpuInfoReader.mHasTimeInStateFile);
                                    i5++) {
                                cpuInfoReader.mHasTimeInStateFile =
                                        z2
                                                | new File(
                                                                (File)
                                                                        cpuInfoReader
                                                                                .mCpuFreqPolicyDirsById
                                                                                .valueAt(i5),
                                                                "stats/time_in_state")
                                                        .exists();
                            }
                            if (!cpuInfoReader.mHasTimeInStateFile) {
                                Slogf.e(
                                        "CpuMonitorService",
                                        "Time in state file not available for any cpufreq policy");
                            }
                            cpuInfoReader.mIsEnabled = true;
                            z = true;
                        }
                    } else {
                        Slogf.e(
                                "CpuMonitorService",
                                "Missing proc stat file at %s",
                                cpuInfoReader.mProcStatFile.getAbsolutePath());
                    }
                }
            }
            z = false;
        }
        if (!z || this.mCpuInfoReader.readCpuInfos() == null) {
            Slogf.wtf(
                    "CpuMonitorService",
                    "Failed to initialize CPU info reader. This happens when the CPU frequency"
                        + " stats are not available or the sysfs interface has changed in the"
                        + " Kernel. Cannot monitor CPU without these stats. Terminating CPU monitor"
                        + " service");
            return;
        }
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        publishLocalService(AnonymousClass1.class, this.mLocalService);
        publishBinderService("cpu_monitor", new CpuMonitorBinder(), false, 1);
        Watchdog.getInstance().addThread(this.mHandler);
        synchronized (this.mLock) {
            try {
                if (this.mShouldDebugMonitor
                        && !this.mHandler.hasCallbacks(this.mMonitorCpuStats)) {
                    this.mCurrentMonitoringIntervalMillis = this.mDebugMonitoringIntervalMillis;
                    Slogf.i("CpuMonitorService", "Starting debug monitoring");
                    this.mHandler.post(this.mMonitorCpuStats);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopMonitoringCpuStatsLocked() {
        this.mHandler.removeCallbacks(this.mMonitorCpuStats);
        this.mCurrentMonitoringIntervalMillis = -1L;
        for (int i = 0; i < this.mCpusetInfosByCpuset.size(); i++) {
            CpusetInfo cpusetInfo = (CpusetInfo) this.mCpusetInfosByCpuset.valueAt(i);
            cpusetInfo.mLatestCpuAvailabilityInfo = null;
            cpusetInfo.mSnapshotsByUptime.clear();
        }
    }
}
