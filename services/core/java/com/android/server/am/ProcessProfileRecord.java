package com.android.server.am;

import android.app.IApplicationThread;
import android.os.Debug;
import android.os.SystemClock;

import com.android.internal.app.procstats.ProcessState;
import com.android.server.power.stats.BatteryStatsImpl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProcessProfileRecord {
    public final ProcessRecord mApp;
    public ProcessState mBaseProcessTracker;
    public BatteryStatsImpl.Uid.Proc mCurProcBatteryStats;
    public int mCurRawAdj;
    public long mInitialIdlePssOrRss;
    public long mLastCachedPss;
    public long mLastCachedRss;
    public long mLastCachedSwapPss;
    public long mLastLowMemory;
    public Debug.MemoryInfo mLastMemInfo;
    public long mLastMemInfoTime;
    public long mLastPss;
    public long mLastPssTime;
    public long mLastRequestedGc;
    public long mLastRss;
    public long mLastStateTime;
    public long mLastSwapPss;
    public long mNextPssTime;
    public boolean mPendingUiClean;
    public int mPid;
    public final Object mProfilerLock;
    public int mPssStatType;
    public boolean mReportLowMemory;
    public final ActivityManagerService mService;
    public int mSetAdj;
    public int mSetProcState;
    public IApplicationThread mThread;
    public int mTrimMemoryLevel;
    public final ProcessList.ProcStateMemTracker mProcStateMemTracker =
            new ProcessList.ProcStateMemTracker();
    public int mPssProcState = 20;
    public final AtomicLong mLastCpuTime = new AtomicLong(0);
    public final AtomicLong mCurCpuTime = new AtomicLong(0);
    public final AtomicLong mLastCpuDelayTime = new AtomicLong(0);
    public final AtomicInteger mCurrentHostingComponentTypes = new AtomicInteger(0);
    public final AtomicInteger mHistoricalHostingComponentTypes = new AtomicInteger(0);

    public ProcessProfileRecord(ProcessRecord processRecord) {
        this.mApp = processRecord;
        ActivityManagerService activityManagerService = processRecord.mService;
        this.mService = activityManagerService;
        ActivityManagerProcLock activityManagerProcLock = activityManagerService.mProcLock;
        this.mProfilerLock = activityManagerService.mAppProfiler.mProfilerLock;
    }

    public final void abortNextPssTime() {
        this.mProcStateMemTracker.mPendingMemState = -1;
    }

    public final void addHostingComponentType(int i) {
        AtomicInteger atomicInteger = this.mCurrentHostingComponentTypes;
        atomicInteger.set(atomicInteger.get() | i);
        AtomicInteger atomicInteger2 = this.mHistoricalHostingComponentTypes;
        atomicInteger2.set(i | atomicInteger2.get());
    }

    public final void addPss(long j, long j2, long j3, boolean z, int i, long j4) {
        synchronized (this.mService.mProcessStats.mLock) {
            try {
                ProcessState processState = this.mBaseProcessTracker;
                if (processState != null) {
                    PackageList packageList = this.mApp.mPkgList;
                    synchronized (packageList) {
                        try {
                            processState.addPss(j, j2, j3, z, i, j4, packageList.mPkgList);
                        } catch (IndexOutOfBoundsException unused) {
                            processState.resetSafely(SystemClock.uptimeMillis());
                        }
                    }
                }
            } finally {
            }
        }
    }

    public final void clearHostingComponentType(int i) {
        AtomicInteger atomicInteger = this.mCurrentHostingComponentTypes;
        atomicInteger.set((~i) & atomicInteger.get());
    }

    public final void reportExcessiveCpu() {
        synchronized (this.mService.mProcessStats.mLock) {
            try {
                ProcessState processState = this.mBaseProcessTracker;
                if (processState != null) {
                    PackageList packageList = this.mApp.mPkgList;
                    synchronized (packageList) {
                        processState.reportExcessiveCpu(packageList.mPkgList);
                    }
                }
            } finally {
            }
        }
    }
}
