package com.android.server.cpu;

import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseArrayMap;
import com.android.internal.util.DumpUtils;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.audio.CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.cpu.CpuInfoReader;
import com.android.server.utils.PriorityDump;
import com.android.server.utils.Slogf;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class CpuMonitorService extends SystemService {
    public static final long CACHE_DURATION_MILLISECONDS;
    public static final boolean DEBUG = Log.isLoggable(CpuMonitorService.class.getSimpleName(), 3);
    public static final long DEBUG_MONITORING_INTERVAL_MILLISECONDS;
    public static final long LATEST_AVAILABILITY_DURATION_MILLISECONDS;
    public static final long NORMAL_MONITORING_INTERVAL_MILLISECONDS;
    public static final String TAG = "CpuMonitorService";
    public final SparseArrayMap mAvailabilityCallbackInfosByCallbacksByCpuset;
    public final Context mContext;
    public final CpuInfoReader mCpuInfoReader;
    public final SparseArray mCpusetInfosByCpuset;
    public long mCurrentMonitoringIntervalMillis;
    public final long mDebugMonitoringIntervalMillis;
    public Handler mHandler;
    public final HandlerThread mHandlerThread;
    public final long mLatestAvailabilityDurationMillis;
    public final CpuMonitorInternal mLocalService;
    public final Object mLock;
    public final Runnable mMonitorCpuStats;
    public final long mNormalMonitoringIntervalMillis;
    public final boolean mShouldDebugMonitor;

    public abstract class CpuAvailabilityCallbackInfo {
    }

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        NORMAL_MONITORING_INTERVAL_MILLISECONDS = timeUnit.toMillis(5L);
        TimeUnit timeUnit2 = TimeUnit.MINUTES;
        DEBUG_MONITORING_INTERVAL_MILLISECONDS = timeUnit2.toMillis(1L);
        CACHE_DURATION_MILLISECONDS = (Build.IS_USERDEBUG || Build.IS_ENG) ? timeUnit2.toMillis(30L) : timeUnit2.toMillis(10L);
        LATEST_AVAILABILITY_DURATION_MILLISECONDS = timeUnit.toMillis(30L);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CpuMonitorService(Context context) {
        this(context, r2, r3, r4, NORMAL_MONITORING_INTERVAL_MILLISECONDS, DEBUG_MONITORING_INTERVAL_MILLISECONDS, LATEST_AVAILABILITY_DURATION_MILLISECONDS);
        CpuInfoReader cpuInfoReader = new CpuInfoReader();
        boolean z = true;
        ServiceThread serviceThread = new ServiceThread(TAG, 10, true);
        if (!Build.IS_USERDEBUG && !Build.IS_ENG) {
            z = false;
        }
    }

    public CpuMonitorService(Context context, CpuInfoReader cpuInfoReader, HandlerThread handlerThread, boolean z, long j, long j2, long j3) {
        super(context);
        this.mLock = new Object();
        this.mMonitorCpuStats = new Runnable() { // from class: com.android.server.cpu.CpuMonitorService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CpuMonitorService.this.monitorCpuStats();
            }
        };
        this.mCurrentMonitoringIntervalMillis = -1L;
        this.mLocalService = new CpuMonitorInternal() { // from class: com.android.server.cpu.CpuMonitorService.1
        };
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

    @Override // com.android.server.SystemService
    public void onStart() {
        if (!this.mCpuInfoReader.init() || this.mCpuInfoReader.readCpuInfos() == null) {
            Slogf.wtf(TAG, "Failed to initialize CPU info reader. This happens when the CPU frequency stats are not available or the sysfs interface has changed in the Kernel. Cannot monitor CPU without these stats. Terminating CPU monitor service");
            return;
        }
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        publishLocalService(CpuMonitorInternal.class, this.mLocalService);
        publishBinderService("cpu_monitor", new CpuMonitorBinder(), false, 1);
        Watchdog.getInstance().addThread(this.mHandler);
        synchronized (this.mLock) {
            if (this.mShouldDebugMonitor && !this.mHandler.hasCallbacks(this.mMonitorCpuStats)) {
                this.mCurrentMonitoringIntervalMillis = this.mDebugMonitoringIntervalMillis;
                Slogf.m94i(TAG, "Starting debug monitoring");
                this.mHandler.post(this.mMonitorCpuStats);
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

    public final void doDump(final IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("*%s*\n", new Object[]{CpuMonitorService.class.getSimpleName()});
        indentingPrintWriter.increaseIndent();
        this.mCpuInfoReader.dump(indentingPrintWriter);
        Object[] objArr = new Object[1];
        objArr[0] = this.mShouldDebugMonitor ? "Yes" : "No";
        indentingPrintWriter.printf("mShouldDebugMonitor = %s\n", objArr);
        indentingPrintWriter.printf("mNormalMonitoringIntervalMillis = %d\n", new Object[]{Long.valueOf(this.mNormalMonitoringIntervalMillis)});
        indentingPrintWriter.printf("mDebugMonitoringIntervalMillis = %d\n", new Object[]{Long.valueOf(this.mDebugMonitoringIntervalMillis)});
        indentingPrintWriter.printf("mLatestAvailabilityDurationMillis = %d\n", new Object[]{Long.valueOf(this.mLatestAvailabilityDurationMillis)});
        synchronized (this.mLock) {
            indentingPrintWriter.printf("mCurrentMonitoringIntervalMillis = %d\n", new Object[]{Long.valueOf(this.mCurrentMonitoringIntervalMillis)});
            if (hasClientCallbacksLocked()) {
                indentingPrintWriter.println("CPU availability change callbacks:");
                indentingPrintWriter.increaseIndent();
                this.mAvailabilityCallbackInfosByCallbacksByCpuset.forEach(new Consumer() { // from class: com.android.server.cpu.CpuMonitorService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                        CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(obj);
                        CpuMonitorService.lambda$doDump$0(indentingPrintWriter2, null);
                    }
                });
                indentingPrintWriter.decreaseIndent();
            }
            if (this.mCpusetInfosByCpuset.size() > 0) {
                indentingPrintWriter.println("Cpuset infos:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mCpusetInfosByCpuset.size(); i++) {
                    indentingPrintWriter.printf("%s\n", new Object[]{this.mCpusetInfosByCpuset.valueAt(i)});
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static /* synthetic */ void lambda$doDump$0(IndentingPrintWriter indentingPrintWriter, CpuAvailabilityCallbackInfo cpuAvailabilityCallbackInfo) {
        indentingPrintWriter.printf("%s\n", new Object[]{cpuAvailabilityCallbackInfo});
    }

    public final void monitorCpuStats() {
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mHandler.removeCallbacks(this.mMonitorCpuStats);
        SparseArray readCpuInfos = this.mCpuInfoReader.readCpuInfos();
        if (readCpuInfos == null) {
            Slogf.wtf(TAG, "Failed to read CPU info from device");
            synchronized (this.mLock) {
                stopMonitoringCpuStatsLocked();
            }
            return;
        }
        synchronized (this.mLock) {
            for (int i = 0; i < readCpuInfos.size(); i++) {
                CpuInfoReader.CpuInfo cpuInfo = (CpuInfoReader.CpuInfo) readCpuInfos.valueAt(i);
                for (int i2 = 0; i2 < this.mCpusetInfosByCpuset.size(); i2++) {
                    ((CpusetInfo) this.mCpusetInfosByCpuset.valueAt(i2)).appendCpuInfo(uptimeMillis, cpuInfo);
                }
            }
            for (int i3 = 0; i3 < this.mCpusetInfosByCpuset.size(); i3++) {
                CpusetInfo cpusetInfo = (CpusetInfo) this.mCpusetInfosByCpuset.valueAt(i3);
                cpusetInfo.populateLatestCpuAvailabilityInfo(uptimeMillis, this.mLatestAvailabilityDurationMillis);
                checkClientThresholdsAndNotifyLocked(cpusetInfo);
            }
            if (this.mCurrentMonitoringIntervalMillis > 0 && (hasClientCallbacksLocked() || this.mShouldDebugMonitor)) {
                this.mHandler.postAtTime(this.mMonitorCpuStats, uptimeMillis + this.mCurrentMonitoringIntervalMillis);
            } else {
                stopMonitoringCpuStatsLocked();
            }
        }
    }

    public final void checkClientThresholdsAndNotifyLocked(CpusetInfo cpusetInfo) {
        int prevCpuAvailabilityPercent = cpusetInfo.getPrevCpuAvailabilityPercent();
        if (cpusetInfo.getLatestCpuAvailabilityInfo() == null || prevCpuAvailabilityPercent < 0 || this.mAvailabilityCallbackInfosByCallbacksByCpuset.numElementsForKey(cpusetInfo.cpuset) == 0) {
            return;
        }
        for (int i = 0; i < this.mAvailabilityCallbackInfosByCallbacksByCpuset.numMaps(); i++) {
            if (this.mAvailabilityCallbackInfosByCallbacksByCpuset.numElementsForKeyAt(i) > 0) {
                CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(this.mAvailabilityCallbackInfosByCallbacksByCpuset.valueAt(i, 0));
                throw null;
            }
        }
    }

    public final boolean hasClientCallbacksLocked() {
        for (int i = 0; i < this.mAvailabilityCallbackInfosByCallbacksByCpuset.numMaps(); i++) {
            if (this.mAvailabilityCallbackInfosByCallbacksByCpuset.numElementsForKeyAt(i) > 0) {
                return true;
            }
        }
        return false;
    }

    public final void stopMonitoringCpuStatsLocked() {
        this.mHandler.removeCallbacks(this.mMonitorCpuStats);
        this.mCurrentMonitoringIntervalMillis = -1L;
        for (int i = 0; i < this.mCpusetInfosByCpuset.size(); i++) {
            ((CpusetInfo) this.mCpusetInfosByCpuset.valueAt(i)).clear();
        }
    }

    public static boolean containsCpuset(int i, int i2) {
        if (i2 == 1) {
            return (i & 1) != 0;
        }
        if (i2 == 2) {
            return (i & 2) != 0;
        }
        Slogf.wtf(TAG, "Provided invalid expectedCpuset %d", Integer.valueOf(i2));
        return false;
    }

    public final class CpuMonitorBinder extends Binder {
        public final PriorityDump.PriorityDumper mPriorityDumper;

        public CpuMonitorBinder() {
            this.mPriorityDumper = new PriorityDump.PriorityDumper() { // from class: com.android.server.cpu.CpuMonitorService.CpuMonitorBinder.1
                @Override // com.android.server.utils.PriorityDump.PriorityDumper
                public void dumpCritical(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
                    if (!DumpUtils.checkDumpAndUsageStatsPermission(CpuMonitorService.this.mContext, CpuMonitorService.TAG, printWriter) || z) {
                        return;
                    }
                    IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
                    try {
                        CpuMonitorService.this.doDump(indentingPrintWriter);
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
        }

        @Override // android.os.Binder
        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            PriorityDump.dump(this.mPriorityDumper, fileDescriptor, printWriter, strArr);
        }
    }

    public final class CpusetInfo {
        public final int cpuset;
        public CpuAvailabilityInfo mLatestCpuAvailabilityInfo;
        public final LongSparseArray mSnapshotsByUptime = new LongSparseArray();

        public CpusetInfo(int i) {
            this.cpuset = i;
        }

        public void appendCpuInfo(long j, CpuInfoReader.CpuInfo cpuInfo) {
            if (CpuMonitorService.containsCpuset(cpuInfo.cpusetCategories, this.cpuset)) {
                Snapshot snapshot = (Snapshot) this.mSnapshotsByUptime.get(j);
                if (snapshot == null) {
                    snapshot = new Snapshot(j);
                    this.mSnapshotsByUptime.append(j, snapshot);
                    if (this.mSnapshotsByUptime.size() > 0 && j - ((Snapshot) this.mSnapshotsByUptime.valueAt(0)).uptimeMillis > CpuMonitorService.CACHE_DURATION_MILLISECONDS) {
                        this.mSnapshotsByUptime.removeAt(0);
                    }
                }
                snapshot.appendCpuInfo(cpuInfo);
            }
        }

        public CpuAvailabilityInfo getLatestCpuAvailabilityInfo() {
            return this.mLatestCpuAvailabilityInfo;
        }

        public void populateLatestCpuAvailabilityInfo(long j, long j2) {
            int size = this.mSnapshotsByUptime.size();
            if (size == 0) {
                this.mLatestCpuAvailabilityInfo = null;
                return;
            }
            Snapshot snapshot = (Snapshot) this.mSnapshotsByUptime.valueAt(size - 1);
            long j3 = snapshot.uptimeMillis;
            if (j3 != j) {
                if (CpuMonitorService.DEBUG) {
                    Slogf.m88d(CpuMonitorService.TAG, "Skipping stale CPU availability information for cpuset %s", CpuAvailabilityMonitoringConfig.toCpusetString(this.cpuset));
                }
                this.mLatestCpuAvailabilityInfo = null;
            } else {
                CpuAvailabilityInfo cpuAvailabilityInfo = this.mLatestCpuAvailabilityInfo;
                if (cpuAvailabilityInfo == null || cpuAvailabilityInfo.dataTimestampUptimeMillis != j3) {
                    this.mLatestCpuAvailabilityInfo = new CpuAvailabilityInfo(this.cpuset, j3, snapshot.getAverageAvailableCpuFreqPercent(), getCumulativeAvgAvailabilityPercent(j - j2), j2);
                }
            }
        }

        public int getPrevCpuAvailabilityPercent() {
            int size = this.mSnapshotsByUptime.size();
            if (size < 2) {
                return -1;
            }
            return ((Snapshot) this.mSnapshotsByUptime.valueAt(size - 2)).getAverageAvailableCpuFreqPercent();
        }

        public final int getCumulativeAvgAvailabilityPercent(long j) {
            int size = this.mSnapshotsByUptime.size() - 1;
            long j2 = 0;
            long j3 = Long.MAX_VALUE;
            int i = 0;
            long j4 = 0;
            while (true) {
                if (size < 0) {
                    break;
                }
                Snapshot snapshot = (Snapshot) this.mSnapshotsByUptime.valueAt(size);
                long j5 = snapshot.uptimeMillis;
                if (j5 <= j) {
                    j3 = j5;
                    break;
                }
                i++;
                j2 += snapshot.totalNormalizedAvailableCpuFreqKHz;
                j4 += snapshot.totalOnlineMaxCpuFreqKHz;
                size--;
                j3 = j5;
            }
            if (j3 > j || i < 2) {
                return -1;
            }
            return (int) ((j2 * 100.0d) / j4);
        }

        public void clear() {
            this.mLatestCpuAvailabilityInfo = null;
            this.mSnapshotsByUptime.clear();
        }

        public String toString() {
            return "CpusetInfo{cpuset = " + CpuAvailabilityMonitoringConfig.toCpusetString(this.cpuset) + ", mSnapshotsByUptime = " + this.mSnapshotsByUptime + ", mLatestCpuAvailabilityInfo = " + this.mLatestCpuAvailabilityInfo + '}';
        }

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

            public void appendCpuInfo(CpuInfoReader.CpuInfo cpuInfo) {
                if (!cpuInfo.isOnline) {
                    this.totalOfflineCpus++;
                    this.totalOfflineMaxCpuFreqKHz += cpuInfo.maxCpuFreqKHz;
                } else {
                    this.totalOnlineCpus++;
                    this.totalNormalizedAvailableCpuFreqKHz += cpuInfo.getNormalizedAvailableCpuFreqKHz();
                    this.totalOnlineMaxCpuFreqKHz += cpuInfo.maxCpuFreqKHz;
                }
            }

            public int getAverageAvailableCpuFreqPercent() {
                int i = (int) ((this.totalNormalizedAvailableCpuFreqKHz * 100.0d) / this.totalOnlineMaxCpuFreqKHz);
                if (i >= 0) {
                    return i;
                }
                Slogf.wtf(CpuMonitorService.TAG, "Computed negative CPU availability percent(%d) for %s ", Integer.valueOf(i), toString());
                return 0;
            }

            public String toString() {
                return "Snapshot{uptimeMillis = " + this.uptimeMillis + ", totalOnlineCpus = " + this.totalOnlineCpus + ", totalOfflineCpus = " + this.totalOfflineCpus + ", totalNormalizedAvailableCpuFreqKHz = " + this.totalNormalizedAvailableCpuFreqKHz + ", totalOnlineMaxCpuFreqKHz = " + this.totalOnlineMaxCpuFreqKHz + ", totalOfflineMaxCpuFreqKHz = " + this.totalOfflineMaxCpuFreqKHz + '}';
            }
        }
    }
}
