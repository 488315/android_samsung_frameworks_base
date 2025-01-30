package com.android.server.am;

import android.app.IApplicationThread;
import android.content.pm.ApplicationInfo;
import android.os.Debug;
import android.os.SystemClock;
import android.util.DebugUtils;
import android.util.TimeUtils;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.server.power.stats.BatteryStatsImpl;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class ProcessProfileRecord {
  public final ProcessRecord mApp;
  public ProcessState mBaseProcessTracker;
  public BatteryStatsImpl.Uid.Proc mCurProcBatteryStats;
  public int mCurRawAdj;
  public long mInitialIdlePss;
  public long mLastCachedPss;
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
  public final ActivityManagerGlobalLock mProcLock;
  public final Object mProfilerLock;
  public int mPssStatType;
  public boolean mReportLowMemory;
  public long mRunningTrimMemoryTime;
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
  public AtomicInteger mCurrentHostingComponentTypes = new AtomicInteger(0);
  public AtomicInteger mHistoricalHostingComponentTypes = new AtomicInteger(0);

  public ProcessProfileRecord(ProcessRecord processRecord) {
    this.mApp = processRecord;
    ActivityManagerService activityManagerService = processRecord.mService;
    this.mService = activityManagerService;
    this.mProcLock = activityManagerService.mProcLock;
    this.mProfilerLock = activityManagerService.mAppProfiler.mProfilerLock;
  }

  public void init(long j) {
    this.mNextPssTime = j;
    this.mLastPssTime = j;
  }

  public ProcessState getBaseProcessTracker() {
    return this.mBaseProcessTracker;
  }

  public void setBaseProcessTracker(ProcessState processState) {
    this.mBaseProcessTracker = processState;
  }

  public void onProcessActive(
      IApplicationThread iApplicationThread, final ProcessStatsService processStatsService) {
    if (this.mThread == null) {
      synchronized (this.mProfilerLock) {
        synchronized (processStatsService.mLock) {
          final ProcessState baseProcessTracker = getBaseProcessTracker();
          PackageList pkgList = this.mApp.getPkgList();
          if (baseProcessTracker != null) {
            synchronized (pkgList) {
              baseProcessTracker.setState(
                  -1,
                  processStatsService.getMemFactorLocked(),
                  SystemClock.uptimeMillis(),
                  pkgList.getPackageListLocked());
            }
            baseProcessTracker.makeInactive();
          }
          ApplicationInfo applicationInfo = this.mApp.info;
          final ProcessState processStateLocked =
              processStatsService.getProcessStateLocked(
                  applicationInfo.packageName,
                  applicationInfo.uid,
                  applicationInfo.longVersionCode,
                  this.mApp.processName);
          setBaseProcessTracker(processStateLocked);
          processStateLocked.makeActive();
          pkgList.forEachPackage(
              new BiConsumer() { // from class:
                                 // com.android.server.am.ProcessProfileRecord$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                  ProcessProfileRecord.this.lambda$onProcessActive$0(
                      baseProcessTracker,
                      processStatsService,
                      processStateLocked,
                      (String) obj,
                      (ProcessStats.ProcessStateHolder) obj2);
                }
              });
          this.mThread = iApplicationThread;
        }
      }
      return;
    }
    synchronized (this.mProfilerLock) {
      this.mThread = iApplicationThread;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onProcessActive$0(
      ProcessState processState,
      ProcessStatsService processStatsService,
      ProcessState processState2,
      String str,
      ProcessStats.ProcessStateHolder processStateHolder) {
    ProcessState processState3 = processStateHolder.state;
    if (processState3 != null && processState3 != processState) {
      processState3.makeInactive();
    }
    processStatsService.updateProcessStateHolderLocked(
        processStateHolder,
        str,
        this.mApp.info.uid,
        this.mApp.info.longVersionCode,
        this.mApp.processName);
    ProcessState processState4 = processStateHolder.state;
    if (processState4 != processState2) {
      processState4.makeActive();
    }
  }

  public void onProcessInactive(ProcessStatsService processStatsService) {
    synchronized (this.mProfilerLock) {
      synchronized (processStatsService.mLock) {
        final ProcessState baseProcessTracker = getBaseProcessTracker();
        if (baseProcessTracker != null) {
          PackageList pkgList = this.mApp.getPkgList();
          synchronized (pkgList) {
            baseProcessTracker.setState(
                -1,
                processStatsService.getMemFactorLocked(),
                SystemClock.uptimeMillis(),
                pkgList.getPackageListLocked());
          }
          baseProcessTracker.makeInactive();
          setBaseProcessTracker(null);
          pkgList.forEachPackageProcessStats(
              new Consumer() { // from class:
                               // com.android.server.am.ProcessProfileRecord$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ProcessProfileRecord.lambda$onProcessInactive$1(
                      baseProcessTracker, (ProcessStats.ProcessStateHolder) obj);
                }
              });
        }
        this.mThread = null;
      }
    }
    this.mCurrentHostingComponentTypes.set(0);
    this.mHistoricalHostingComponentTypes.set(0);
  }

  public static /* synthetic */ void lambda$onProcessInactive$1(
      ProcessState processState, ProcessStats.ProcessStateHolder processStateHolder) {
    ProcessState processState2 = processStateHolder.state;
    if (processState2 != null && processState2 != processState) {
      processState2.makeInactive();
    }
    processStateHolder.pkg = null;
    processStateHolder.state = null;
  }

  public long getLastPssTime() {
    return this.mLastPssTime;
  }

  public void setLastPssTime(long j) {
    this.mLastPssTime = j;
  }

  public long getNextPssTime() {
    return this.mNextPssTime;
  }

  public void setNextPssTime(long j) {
    this.mNextPssTime = j;
  }

  public long getInitialIdlePss() {
    return this.mInitialIdlePss;
  }

  public void setInitialIdlePss(long j) {
    this.mInitialIdlePss = j;
  }

  public long getLastPss() {
    return this.mLastPss;
  }

  public void setLastPss(long j) {
    this.mLastPss = j;
  }

  public long getLastCachedPss() {
    return this.mLastCachedPss;
  }

  public void setLastCachedPss(long j) {
    this.mLastCachedPss = j;
  }

  public long getLastSwapPss() {
    return this.mLastSwapPss;
  }

  public void setLastSwapPss(long j) {
    this.mLastSwapPss = j;
  }

  public void setLastCachedSwapPss(long j) {
    this.mLastCachedSwapPss = j;
  }

  public long getLastRss() {
    return this.mLastRss;
  }

  public void setLastRss(long j) {
    this.mLastRss = j;
  }

  public Debug.MemoryInfo getLastMemInfo() {
    return this.mLastMemInfo;
  }

  public void setLastMemInfo(Debug.MemoryInfo memoryInfo) {
    this.mLastMemInfo = memoryInfo;
  }

  public long getLastMemInfoTime() {
    return this.mLastMemInfoTime;
  }

  public void setLastMemInfoTime(long j) {
    this.mLastMemInfoTime = j;
  }

  public int getPssProcState() {
    return this.mPssProcState;
  }

  public void setPssProcState(int i) {
    this.mPssProcState = i;
  }

  public int getPssStatType() {
    return this.mPssStatType;
  }

  public void setPssStatType(int i) {
    this.mPssStatType = i;
  }

  public int getTrimMemoryLevel() {
    return this.mTrimMemoryLevel;
  }

  public void setTrimMemoryLevel(int i) {
    this.mTrimMemoryLevel = i;
  }

  public long getRunningTrimMemoryTime() {
    return this.mRunningTrimMemoryTime;
  }

  public void setRunningTrimMemoryTime(long j) {
    this.mRunningTrimMemoryTime = j;
  }

  public boolean hasPendingUiClean() {
    return this.mPendingUiClean;
  }

  public void setPendingUiClean(boolean z) {
    this.mPendingUiClean = z;
    this.mApp.getWindowProcessController().setPendingUiClean(z);
  }

  public BatteryStatsImpl.Uid.Proc getCurProcBatteryStats() {
    return this.mCurProcBatteryStats;
  }

  public void setCurProcBatteryStats(BatteryStatsImpl.Uid.Proc proc) {
    this.mCurProcBatteryStats = proc;
  }

  public long getLastRequestedGc() {
    return this.mLastRequestedGc;
  }

  public void setLastRequestedGc(long j) {
    this.mLastRequestedGc = j;
  }

  public long getLastLowMemory() {
    return this.mLastLowMemory;
  }

  public void setLastLowMemory(long j) {
    this.mLastLowMemory = j;
  }

  public boolean getReportLowMemory() {
    return this.mReportLowMemory;
  }

  public void setReportLowMemory(boolean z) {
    this.mReportLowMemory = z;
  }

  public void addPss(long j, long j2, long j3, boolean z, int i, long j4) {
    synchronized (this.mService.mProcessStats.mLock) {
      ProcessState processState = this.mBaseProcessTracker;
      if (processState != null) {
        PackageList pkgList = this.mApp.getPkgList();
        synchronized (pkgList) {
          try {
            processState.addPss(j, j2, j3, z, i, j4, pkgList.getPackageListLocked());
          } catch (IndexOutOfBoundsException unused) {
            processState.resetSafely(SystemClock.uptimeMillis());
          }
        }
      }
    }
  }

  public void reportExcessiveCpu() {
    synchronized (this.mService.mProcessStats.mLock) {
      ProcessState processState = this.mBaseProcessTracker;
      if (processState != null) {
        PackageList pkgList = this.mApp.getPkgList();
        synchronized (pkgList) {
          processState.reportExcessiveCpu(pkgList.getPackageListLocked());
        }
      }
    }
  }

  public void setProcessTrackerState(int i, int i2) {
    synchronized (this.mService.mProcessStats.mLock) {
      ProcessState processState = this.mBaseProcessTracker;
      if (processState != null && i != 20) {
        PackageList pkgList = this.mApp.getPkgList();
        long uptimeMillis = SystemClock.uptimeMillis();
        synchronized (pkgList) {
          processState.setState(i, i2, uptimeMillis, pkgList.getPackageListLocked());
        }
      }
    }
  }

  public void commitNextPssTime() {
    commitNextPssTime(this.mProcStateMemTracker);
  }

  public void abortNextPssTime() {
    abortNextPssTime(this.mProcStateMemTracker);
  }

  public long computeNextPssTime(int i, boolean z, boolean z2, long j) {
    return ProcessList.computeNextPssTime(i, this.mProcStateMemTracker, z, z2, j);
  }

  public static void commitNextPssTime(ProcessList.ProcStateMemTracker procStateMemTracker) {
    int i = procStateMemTracker.mPendingMemState;
    if (i >= 0) {
      int[] iArr = procStateMemTracker.mHighestMem;
      int i2 = procStateMemTracker.mPendingHighestMemState;
      iArr[i] = i2;
      procStateMemTracker.mScalingFactor[i] = procStateMemTracker.mPendingScalingFactor;
      procStateMemTracker.mTotalHighestMem = i2;
      procStateMemTracker.mPendingMemState = -1;
    }
  }

  public static void abortNextPssTime(ProcessList.ProcStateMemTracker procStateMemTracker) {
    procStateMemTracker.mPendingMemState = -1;
  }

  public int getPid() {
    return this.mPid;
  }

  public void setPid(int i) {
    this.mPid = i;
  }

  public IApplicationThread getThread() {
    return this.mThread;
  }

  public int getSetProcState() {
    return this.mSetProcState;
  }

  public int getSetAdj() {
    return this.mSetAdj;
  }

  public int getCurRawAdj() {
    return this.mCurRawAdj;
  }

  public long getLastStateTime() {
    return this.mLastStateTime;
  }

  public void updateProcState(ProcessStateRecord processStateRecord) {
    this.mSetProcState = processStateRecord.getCurProcState();
    this.mSetAdj = processStateRecord.getCurAdj();
    this.mCurRawAdj = processStateRecord.getCurRawAdj();
    this.mLastStateTime = processStateRecord.getLastStateTime();
  }

  public void addHostingComponentType(int i) {
    AtomicInteger atomicInteger = this.mCurrentHostingComponentTypes;
    atomicInteger.set(atomicInteger.get() | i);
    AtomicInteger atomicInteger2 = this.mHistoricalHostingComponentTypes;
    atomicInteger2.set(i | atomicInteger2.get());
  }

  public void clearHostingComponentType(int i) {
    AtomicInteger atomicInteger = this.mCurrentHostingComponentTypes;
    atomicInteger.set((~i) & atomicInteger.get());
  }

  public int getCurrentHostingComponentTypes() {
    return this.mCurrentHostingComponentTypes.get();
  }

  public int getHistoricalHostingComponentTypes() {
    return this.mHistoricalHostingComponentTypes.get();
  }

  public void dumpPss(PrintWriter printWriter, String str, long j) {
    synchronized (this.mProfilerLock) {
      printWriter.print(str);
      printWriter.print("lastPssTime=");
      TimeUtils.formatDuration(this.mLastPssTime, j, printWriter);
      printWriter.print(" pssProcState=");
      printWriter.print(this.mPssProcState);
      printWriter.print(" pssStatType=");
      printWriter.print(this.mPssStatType);
      printWriter.print(" nextPssTime=");
      TimeUtils.formatDuration(this.mNextPssTime, j, printWriter);
      printWriter.println();
      printWriter.print(str);
      printWriter.print("lastPss=");
      DebugUtils.printSizeValue(printWriter, this.mLastPss * 1024);
      printWriter.print(" lastSwapPss=");
      DebugUtils.printSizeValue(printWriter, this.mLastSwapPss * 1024);
      printWriter.print(" lastCachedPss=");
      DebugUtils.printSizeValue(printWriter, this.mLastCachedPss * 1024);
      printWriter.print(" lastCachedSwapPss=");
      DebugUtils.printSizeValue(printWriter, this.mLastCachedSwapPss * 1024);
      printWriter.print(" lastRss=");
      DebugUtils.printSizeValue(printWriter, this.mLastRss * 1024);
      printWriter.println();
      printWriter.print(str);
      printWriter.print("trimMemoryLevel=");
      printWriter.println(this.mTrimMemoryLevel);
      printWriter.print(str);
      printWriter.print("procStateMemTracker: ");
      this.mProcStateMemTracker.dumpLine(printWriter);
      printWriter.print(str);
      printWriter.print("lastRequestedGc=");
      TimeUtils.formatDuration(this.mLastRequestedGc, j, printWriter);
      printWriter.print(" lastLowMemory=");
      TimeUtils.formatDuration(this.mLastLowMemory, j, printWriter);
      printWriter.print(" reportLowMemory=");
      printWriter.println(this.mReportLowMemory);
    }
    printWriter.print(str);
    printWriter.print("currentHostingComponentTypes=0x");
    printWriter.print(Integer.toHexString(getCurrentHostingComponentTypes()));
    printWriter.print(" historicalHostingComponentTypes=0x");
    printWriter.println(Integer.toHexString(getHistoricalHostingComponentTypes()));
  }

  public void dumpCputime(PrintWriter printWriter, String str) {
    long j = this.mLastCpuTime.get();
    printWriter.print(str);
    printWriter.print("lastCpuTime=");
    printWriter.print(j);
    if (j > 0) {
      printWriter.print(" timeUsed=");
      TimeUtils.formatDuration(this.mCurCpuTime.get() - j, printWriter);
    }
    printWriter.println();
  }
}
