package com.android.server.am;

import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.EventLog;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;

import com.android.internal.app.ProcessMap;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.util.DumpUtils;
import com.android.server.chimera.ChimeraManagerService;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import com.android.server.clipboard.ClipboardService;
import com.android.server.utils.PriorityDump;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;
import com.android.server.wm.WindowProcessController;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public final class AppProfiler {
    public boolean mAllowLowerMemLevel;
    public Map mAppAgentMap;
    public final BgHandler mBgHandler;
    public final CachedAppsWatermarkData mCachedAppsWatermarkData;
    public ChimeraManagerService mChimera;
    public boolean mFullPssOrRssPending;
    public final AtomicLong mLastCpuTime;
    public long mLastFullPssTime;
    public long mLastMemUsageReportTime;
    public int mLastMemoryLevel;
    public int mLastNumProcesses;
    public volatile long mLastWriteTime;
    public final LowMemDetector mLowMemDetector;
    public long mLowRamStartTime;
    public int mMemFactorOverride;
    public int mMemWatchDumpPid;
    public String mMemWatchDumpProcName;
    public int mMemWatchDumpUid;
    public Uri mMemWatchDumpUri;
    public boolean mMemWatchIsUserInitiated;
    public final ProcessMap mMemWatchProcesses;
    public final OnTrimReclaimer mOnTrimReclaimer;
    public final ActivityManagerGlobalLock mProcLock;
    public final CountDownLatch mProcessCpuInitLatch;
    public final AtomicBoolean mProcessCpuMutexFree;
    public final ProcessCpuThread mProcessCpuThread;
    public final ProcessCpuTracker mProcessCpuTracker;
    public final ArrayList mProcessesToGc;
    public final ProfileData mProfileData;
    public int mProfileType;
    public final Object mProfilerLock;
    public final AnonymousClass1 mPssDelayConfigListener;
    public final ActivityManagerService mService;
    public volatile boolean mTestPssOrRssMode;
    public volatile long mPssDeferralTime = 0;
    public final ArrayList mPendingPssOrRssProfiles = new ArrayList();
    public final AtomicInteger mActivityStartingNesting = new AtomicInteger(0);

    /* renamed from: com.android.server.am.AppProfiler$2, reason: invalid class name */
    public final class AnonymousClass2 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            ProcessMemInfo processMemInfo = (ProcessMemInfo) obj;
            ProcessMemInfo processMemInfo2 = (ProcessMemInfo) obj2;
            int i = processMemInfo.oomAdj;
            int i2 = processMemInfo2.oomAdj;
            if (i == i2) {
                long j = processMemInfo.pss;
                long j2 = processMemInfo2.pss;
                if (j == j2) {
                    return 0;
                }
                if (j < j2) {
                    return 1;
                }
            } else if (i >= i2) {
                return 1;
            }
            return -1;
        }
    }

    public final class BgHandler extends Handler {
        public BgHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                if (AppProfiler.this.isProfilingPss()) {
                    AppProfiler.m177$$Nest$mcollectPssInBackground(AppProfiler.this);
                    return;
                } else {
                    AppProfiler.m178$$Nest$mcollectRssInBackground(AppProfiler.this);
                    return;
                }
            }
            if (i == 2) {
                AppProfiler appProfiler = AppProfiler.this;
                if (appProfiler.mPssDeferralTime > 0) {
                    synchronized (appProfiler.mProfilerLock) {
                        if (appProfiler.mPendingPssOrRssProfiles.size() > 0) {
                            appProfiler.mBgHandler.removeMessages(1);
                            appProfiler.mBgHandler.sendEmptyMessageDelayed(
                                    1, appProfiler.mPssDeferralTime);
                        }
                    }
                    appProfiler.mActivityStartingNesting.getAndIncrement();
                    appProfiler.mBgHandler.sendEmptyMessageDelayed(3, appProfiler.mPssDeferralTime);
                    return;
                }
                return;
            }
            if (i == 3) {
                AppProfiler appProfiler2 = AppProfiler.this;
                int decrementAndGet = appProfiler2.mActivityStartingNesting.decrementAndGet();
                if (decrementAndGet > 0 || decrementAndGet >= 0) {
                    return;
                }
                Slog.wtf("ActivityManager", "Activity start nesting undercount!");
                appProfiler2.mActivityStartingNesting.incrementAndGet();
                return;
            }
            if (i != 4) {
                return;
            }
            ActivityManagerService activityManagerService = AppProfiler.this.mService;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    AppProfiler appProfiler3 = AppProfiler.this;
                    int i2 = message.arg1;
                    int i3 = message.arg2;
                    ActiveServices activeServices = appProfiler3.mService.mServices;
                    long uptimeMillis = SystemClock.uptimeMillis();
                    ActivityManagerConstants activityManagerConstants =
                            activeServices.mAm.mConstants;
                    if (activityManagerConstants.mEnableExtraServiceRestartDelayOnMemPressure) {
                        long[] jArr =
                                activityManagerConstants.mExtraServiceRestartDelayOnMemPressure;
                        activeServices.performRescheduleServiceRestartOnMemoryPressureLocked(
                                jArr[i2], jArr[i3], uptimeMillis, "mem-pressure-event");
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final class CachedAppsWatermarkData {
        public int mAverageFrozenTimeInSeconds;
        public int mBinderProxySnapshot;
        public long[] mCachedAppFrozenDurations;
        public int mCachedAppHighWatermark;
        public int mCachedInKb;
        public long mEarliestFrozenTimestamp;
        public int mFreeInKb;
        public int mKernelInKb;
        public long mLatestFrozenTimestamp;
        public int mLongestFrozenTimeInSeconds;
        public int mMeanFrozenTimeInSeconds;
        public int mNumOfFrozenApps;
        public int mShortestFrozenTimeInSeconds;
        public long mTotalFrozenDurations;
        public int mUptimeInSeconds;
        public int mZramInKb;

        public CachedAppsWatermarkData() {}

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateCachedAppsSnapshot(final long r8) {
            /*
                r7 = this;
                com.android.server.am.AppProfiler r0 = com.android.server.am.AppProfiler.this
                com.android.server.am.ActivityManagerGlobalLock r0 = r0.mProcLock
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection()
                monitor-enter(r0)
                r7.mEarliestFrozenTimestamp = r8     // Catch: java.lang.Throwable -> L27
                r1 = 0
                r7.mLatestFrozenTimestamp = r1     // Catch: java.lang.Throwable -> L27
                r7.mTotalFrozenDurations = r1     // Catch: java.lang.Throwable -> L27
                r1 = 0
                r7.mNumOfFrozenApps = r1     // Catch: java.lang.Throwable -> L27
                com.android.server.am.AppProfiler r2 = com.android.server.am.AppProfiler.this     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ActivityManagerService r2 = r2.mService     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ProcessList r2 = r2.mProcessList     // Catch: java.lang.Throwable -> L27
                java.util.ArrayList r2 = r2.mLruProcesses     // Catch: java.lang.Throwable -> L27
                int r2 = r2.size()     // Catch: java.lang.Throwable -> L27
                long[] r3 = r7.mCachedAppFrozenDurations     // Catch: java.lang.Throwable -> L27
                if (r3 == 0) goto L2a
                int r3 = r3.length     // Catch: java.lang.Throwable -> L27
                if (r3 >= r2) goto L3a
                goto L2a
            L27:
                r7 = move-exception
                goto Lc7
            L2a:
                com.android.server.am.AppProfiler r3 = com.android.server.am.AppProfiler.this     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ActivityManagerService r3 = r3.mService     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ActivityManagerConstants r3 = r3.mConstants     // Catch: java.lang.Throwable -> L27
                int r3 = r3.CUR_MAX_CACHED_PROCESSES     // Catch: java.lang.Throwable -> L27
                int r2 = java.lang.Math.max(r2, r3)     // Catch: java.lang.Throwable -> L27
                long[] r2 = new long[r2]     // Catch: java.lang.Throwable -> L27
                r7.mCachedAppFrozenDurations = r2     // Catch: java.lang.Throwable -> L27
            L3a:
                com.android.server.am.AppProfiler r2 = com.android.server.am.AppProfiler.this     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ActivityManagerService r2 = r2.mService     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ProcessList r2 = r2.mProcessList     // Catch: java.lang.Throwable -> L27
                com.android.server.am.AppProfiler$CachedAppsWatermarkData$$ExternalSyntheticLambda0 r3 = new com.android.server.am.AppProfiler$CachedAppsWatermarkData$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L27
                r3.<init>()     // Catch: java.lang.Throwable -> L27
                r4 = 1
                r2.forEachLruProcessesLOSP(r3, r4)     // Catch: java.lang.Throwable -> L27
                int r2 = r7.mNumOfFrozenApps     // Catch: java.lang.Throwable -> L27
                if (r2 <= 0) goto L72
                long r3 = r7.mEarliestFrozenTimestamp     // Catch: java.lang.Throwable -> L27
                long r3 = r8 - r3
                r5 = 1000(0x3e8, double:4.94E-321)
                long r3 = r3 / r5
                int r3 = (int) r3     // Catch: java.lang.Throwable -> L27
                r7.mLongestFrozenTimeInSeconds = r3     // Catch: java.lang.Throwable -> L27
                long r3 = r7.mLatestFrozenTimestamp     // Catch: java.lang.Throwable -> L27
                long r8 = r8 - r3
                long r8 = r8 / r5
                int r8 = (int) r8     // Catch: java.lang.Throwable -> L27
                r7.mShortestFrozenTimeInSeconds = r8     // Catch: java.lang.Throwable -> L27
                long r8 = r7.mTotalFrozenDurations     // Catch: java.lang.Throwable -> L27
                long r3 = (long) r2     // Catch: java.lang.Throwable -> L27
                long r8 = r8 / r3
                long r8 = r8 / r5
                int r8 = (int) r8     // Catch: java.lang.Throwable -> L27
                r7.mAverageFrozenTimeInSeconds = r8     // Catch: java.lang.Throwable -> L27
                long[] r8 = r7.mCachedAppFrozenDurations     // Catch: java.lang.Throwable -> L27
                int r9 = r2 / 2
                long r8 = com.android.internal.util.QuickSelect.select(r8, r1, r2, r9)     // Catch: java.lang.Throwable -> L27
                long r8 = r8 / r5
                int r8 = (int) r8     // Catch: java.lang.Throwable -> L27
                r7.mMeanFrozenTimeInSeconds = r8     // Catch: java.lang.Throwable -> L27
            L72:
                r7.mBinderProxySnapshot = r1     // Catch: java.lang.Throwable -> L27
                android.util.SparseIntArray r8 = com.android.internal.os.BinderInternal.nGetBinderProxyPerUidCounts()     // Catch: java.lang.Throwable -> L27
                if (r8 == 0) goto L9e
                int r9 = r8.size()     // Catch: java.lang.Throwable -> L27
            L7e:
                if (r1 >= r9) goto L9e
                int r2 = r8.keyAt(r1)     // Catch: java.lang.Throwable -> L27
                com.android.server.am.AppProfiler r3 = com.android.server.am.AppProfiler.this     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ActivityManagerService r3 = r3.mService     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ProcessList r3 = r3.mProcessList     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ActiveUids r3 = r3.mActiveUids     // Catch: java.lang.Throwable -> L27
                com.android.server.am.UidRecord r2 = r3.get(r2)     // Catch: java.lang.Throwable -> L27
                if (r2 == 0) goto L9b
                int r2 = r7.mBinderProxySnapshot     // Catch: java.lang.Throwable -> L27
                int r3 = r8.valueAt(r1)     // Catch: java.lang.Throwable -> L27
                int r2 = r2 + r3
                r7.mBinderProxySnapshot = r2     // Catch: java.lang.Throwable -> L27
            L9b:
                int r1 = r1 + 1
                goto L7e
            L9e:
                com.android.internal.util.MemInfoReader r8 = new com.android.internal.util.MemInfoReader     // Catch: java.lang.Throwable -> L27
                r8.<init>()     // Catch: java.lang.Throwable -> L27
                r8.readMemInfo()     // Catch: java.lang.Throwable -> L27
                long r1 = r8.getFreeSizeKb()     // Catch: java.lang.Throwable -> L27
                int r9 = (int) r1     // Catch: java.lang.Throwable -> L27
                r7.mFreeInKb = r9     // Catch: java.lang.Throwable -> L27
                long r1 = r8.getCachedSizeKb()     // Catch: java.lang.Throwable -> L27
                int r9 = (int) r1     // Catch: java.lang.Throwable -> L27
                r7.mCachedInKb = r9     // Catch: java.lang.Throwable -> L27
                long r1 = r8.getZramTotalSizeKb()     // Catch: java.lang.Throwable -> L27
                int r9 = (int) r1     // Catch: java.lang.Throwable -> L27
                r7.mZramInKb = r9     // Catch: java.lang.Throwable -> L27
                long r8 = r8.getKernelUsedSizeKb()     // Catch: java.lang.Throwable -> L27
                int r8 = (int) r8     // Catch: java.lang.Throwable -> L27
                r7.mKernelInKb = r8     // Catch: java.lang.Throwable -> L27
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection()
                return
            Lc7:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L27
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection()
                throw r7
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.am.AppProfiler.CachedAppsWatermarkData.updateCachedAppsSnapshot(long):void");
        }
    }

    public final class CpuBinder extends Binder {
        public final AnonymousClass1 mPriorityDumper =
                new PriorityDump
                        .PriorityDumper() { // from class:
                                            // com.android.server.am.AppProfiler.CpuBinder.1
                    @Override // com.android.server.utils.PriorityDump.PriorityDumper
                    public final void dumpCritical(
                            FileDescriptor fileDescriptor,
                            PrintWriter printWriter,
                            String[] strArr,
                            boolean z) {
                        if (DumpUtils.checkDumpAndUsageStatsPermission(
                                AppProfiler.this.mService.mContext, "cpuinfo", printWriter)) {
                            synchronized (AppProfiler.this.mProcessCpuTracker) {
                                try {
                                    if (z) {
                                        AppProfiler.this.mProcessCpuTracker.dumpProto(
                                                fileDescriptor);
                                    } else {
                                        printWriter.print(
                                                AppProfiler.this.mProcessCpuTracker
                                                        .printCurrentLoad());
                                        printWriter.print(
                                                AppProfiler.this.mProcessCpuTracker
                                                        .printCurrentState(
                                                                SystemClock.uptimeMillis()));
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }
                };

        public CpuBinder() {}

        @Override // android.os.Binder
        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            PriorityDump.dump(this.mPriorityDumper, fileDescriptor, printWriter, strArr);
        }
    }

    public final class OnTrimReclaimer extends UnifiedMemoryReclaimer.Reclaimer {}

    public final class ProcessCpuThread extends Thread {
        public ProcessCpuThread() {
            super("CpuTracker");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            synchronized (AppProfiler.this.mProcessCpuTracker) {
                AppProfiler.this.mProcessCpuInitLatch.countDown();
                AppProfiler.this.mProcessCpuTracker.init();
            }
            while (true) {
                try {
                    try {
                        synchronized (this) {
                            try {
                                long uptimeMillis = SystemClock.uptimeMillis();
                                long j =
                                        (AppProfiler.this.mLastCpuTime.get() + 268435455)
                                                - uptimeMillis;
                                long j2 =
                                        (AppProfiler.this.mLastWriteTime + 1800000) - uptimeMillis;
                                if (j2 < j) {
                                    j = j2;
                                }
                                if (j > 0) {
                                    AppProfiler.this.mProcessCpuMutexFree.set(true);
                                    wait(j);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    } catch (InterruptedException unused) {
                    }
                    AppProfiler.this.updateCpuStatsNow();
                } catch (Exception e) {
                    Slog.e("ActivityManager", "Unexpected exception collecting process stats", e);
                }
            }
        }
    }

    public final class ProfileData {
        public String mProfileApp = null;
        public ProcessRecord mProfileProc = null;
        public ProfilerInfo mProfilerInfo = null;

        public ProfileData() {}

        public final void setProfileApp(String str) {
            this.mProfileApp = str;
            ActivityTaskManagerInternal activityTaskManagerInternal =
                    AppProfiler.this.mService.mAtmInternal;
            if (activityTaskManagerInternal != null) {
                ActivityTaskManagerService.LocalService localService =
                        (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        ActivityTaskManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        ActivityTaskManagerService.this.mProfileApp = str;
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        public final void setProfileProc(ProcessRecord processRecord) {
            this.mProfileProc = processRecord;
            ActivityTaskManagerInternal activityTaskManagerInternal =
                    AppProfiler.this.mService.mAtmInternal;
            if (activityTaskManagerInternal != null) {
                WindowProcessController windowProcessController =
                        processRecord == null ? null : processRecord.mWindowProcessController;
                ActivityTaskManagerService.LocalService localService =
                        (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        ActivityTaskManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        ActivityTaskManagerService.this.mProfileProc = windowProcessController;
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        public final void setProfilerInfo(ProfilerInfo profilerInfo) {
            this.mProfilerInfo = profilerInfo;
            ActivityTaskManagerInternal activityTaskManagerInternal =
                    AppProfiler.this.mService.mAtmInternal;
            if (activityTaskManagerInternal != null) {
                ActivityTaskManagerService.LocalService localService =
                        (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        ActivityTaskManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        ActivityTaskManagerService.this.mProfilerInfo = profilerInfo;
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    public final class RecordPssRunnable implements Runnable {
        public final ContentResolver mContentResolver;
        public final Uri mDumpUri;
        public final ProcessProfileRecord mProfile;

        public RecordPssRunnable(
                ProcessProfileRecord processProfileRecord,
                Uri uri,
                ContentResolver contentResolver) {
            this.mProfile = processProfileRecord;
            this.mDumpUri = uri;
            this.mContentResolver = contentResolver;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                ParcelFileDescriptor openFileDescriptor =
                        this.mContentResolver.openFileDescriptor(this.mDumpUri, "rw");
                try {
                    IApplicationThread iApplicationThread = this.mProfile.mThread;
                    if (iApplicationThread != null) {
                        try {
                            iApplicationThread.dumpHeap(
                                    true,
                                    false,
                                    false,
                                    (String) null,
                                    this.mDumpUri.getPath(),
                                    openFileDescriptor,
                                    (RemoteCallback) null);
                        } catch (RemoteException unused) {
                        }
                    }
                    if (openFileDescriptor != null) {
                        openFileDescriptor.close();
                    }
                } finally {
                }
            } catch (IOException e) {
                Slog.e("ActivityManager", "Failed to dump heap", e);
                AppProfiler appProfiler = AppProfiler.this;
                String str = this.mProfile.mApp.processName;
                ActivityManagerService activityManagerService = appProfiler.mService;
                Message obtainMessage = activityManagerService.mHandler.obtainMessage(51);
                obtainMessage.obj = str;
                activityManagerService.mHandler.sendMessage(obtainMessage);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x0145, code lost:

       if (r15 == false) goto L80;
    */
    /* renamed from: -$$Nest$mcollectPssInBackground, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m177$$Nest$mcollectPssInBackground(com.android.server.am.AppProfiler r45) {
        /*
            Method dump skipped, instructions count: 618
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppProfiler.m177$$Nest$mcollectPssInBackground(com.android.server.am.AppProfiler):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x013f, code lost:

       if (r14 == false) goto L80;
    */
    /* renamed from: -$$Nest$mcollectRssInBackground, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m178$$Nest$mcollectRssInBackground(com.android.server.am.AppProfiler r38) {
        /*
            Method dump skipped, instructions count: 471
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppProfiler.m178$$Nest$mcollectRssInBackground(com.android.server.am.AppProfiler):void");
    }

    public AppProfiler(
            ActivityManagerService activityManagerService,
            Looper looper,
            LowMemDetector lowMemDetector) {
        SystemClock.uptimeMillis();
        this.mFullPssOrRssPending = false;
        this.mTestPssOrRssMode = false;
        this.mAllowLowerMemLevel = false;
        this.mLastMemoryLevel = 0;
        this.mMemFactorOverride = -1;
        this.mLowRamStartTime = 0L;
        this.mLastMemUsageReportTime = 0L;
        this.mProcessesToGc = new ArrayList();
        this.mAppAgentMap = null;
        this.mProfileType = 0;
        this.mProfileData = new ProfileData();
        this.mMemWatchProcesses = new ProcessMap();
        this.mProcessCpuTracker = new ProcessCpuTracker(false);
        this.mLastCpuTime = new AtomicLong(0L);
        this.mProcessCpuMutexFree = new AtomicBoolean(true);
        this.mProcessCpuInitLatch = new CountDownLatch(1);
        this.mLastWriteTime = 0L;
        this.mCachedAppsWatermarkData = new CachedAppsWatermarkData();
        this.mProfilerLock = new Object();
        this.mPssDelayConfigListener =
                new DeviceConfig
                        .OnPropertiesChangedListener() { // from class:
                                                         // com.android.server.am.AppProfiler.1
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        if (properties.getKeyset().contains("activity_start_pss_defer")) {
                            AppProfiler.this.mPssDeferralTime =
                                    properties.getLong("activity_start_pss_defer", 0L);
                        }
                    }
                };
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mBgHandler = new BgHandler(looper);
        this.mLowMemDetector = lowMemDetector;
        this.mProcessCpuThread = new ProcessCpuThread();
        this.mOnTrimReclaimer = new OnTrimReclaimer("onTrim");
        boolean z = UnifiedMemoryReclaimer.MODEL_UMR_ENABLED;
    }

    public final void addProcessToGcListLPf(ProcessRecord processRecord) {
        for (int size = this.mProcessesToGc.size() - 1; size >= 0; size--) {
            if (((ProcessRecord) this.mProcessesToGc.get(size)).mProfile.mLastRequestedGc
                    < processRecord.mProfile.mLastRequestedGc) {
                this.mProcessesToGc.add(size + 1, processRecord);
                return;
            }
        }
        this.mProcessesToGc.add(0, processRecord);
    }

    public final void clearProfilerLPf() {
        ParcelFileDescriptor parcelFileDescriptor;
        ProfileData profileData = this.mProfileData;
        ProfilerInfo profilerInfo = profileData.mProfilerInfo;
        if (profilerInfo != null && (parcelFileDescriptor = profilerInfo.profileFd) != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException unused) {
            }
        }
        profileData.setProfileApp(null);
        profileData.setProfileProc(null);
        profileData.setProfilerInfo(null);
    }

    public final void doLowMemReportIfNeededLocked(final ProcessRecord processRecord) {
        ProcessList processList = this.mService.mProcessList;
        int size = processList.mLruProcesses.size() - 1;
        while (true) {
            if (size >= 0) {
                ProcessRecord processRecord2 = (ProcessRecord) processList.mLruProcesses.get(size);
                if (processRecord2.mThread != null && processRecord2.mState.mSetProcState >= 16) {
                    break;
                } else {
                    size--;
                }
            } else {
                boolean z = Build.IS_DEBUGGABLE;
                final long uptimeMillis = SystemClock.uptimeMillis();
                if (z) {
                    if (uptimeMillis < this.mLastMemUsageReportTime + 300000) {
                        z = false;
                    } else {
                        this.mLastMemUsageReportTime = uptimeMillis;
                    }
                }
                int size2 = this.mService.mProcessList.mLruProcesses.size();
                final ArrayList arrayList = z ? new ArrayList(size2) : null;
                EventLog.writeEvent(30017, size2);
                this.mService.mProcessList.forEachLruProcessesLOSP(
                        new Consumer() { // from class:
                                         // com.android.server.am.AppProfiler$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                String sb;
                                AppProfiler appProfiler = AppProfiler.this;
                                ProcessRecord processRecord3 = processRecord;
                                ArrayList arrayList2 = arrayList;
                                long j = uptimeMillis;
                                ProcessRecord processRecord4 = (ProcessRecord) obj;
                                appProfiler.getClass();
                                if (processRecord4 == processRecord3
                                        || processRecord4.mThread == null) {
                                    return;
                                }
                                ProcessStateRecord processStateRecord = processRecord4.mState;
                                if (arrayList2 != null) {
                                    String str = processRecord4.processName;
                                    int i = processRecord4.mPid;
                                    int i2 = processStateRecord.mSetAdj;
                                    int i3 = processStateRecord.mSetProcState;
                                    String str2 = processStateRecord.mAdjType;
                                    if (processStateRecord.mAdjSource == null
                                            && processStateRecord.mAdjTarget == null) {
                                        sb = null;
                                    } else {
                                        StringBuilder sb2 = new StringBuilder(128);
                                        sb2.append(' ');
                                        Object obj2 = processStateRecord.mAdjTarget;
                                        if (obj2 instanceof ComponentName) {
                                            sb2.append(
                                                    ((ComponentName) obj2).flattenToShortString());
                                        } else if (obj2 != null) {
                                            sb2.append(obj2.toString());
                                        } else {
                                            sb2.append("{null}");
                                        }
                                        sb2.append("<=");
                                        Object obj3 = processStateRecord.mAdjSource;
                                        if (obj3 instanceof ProcessRecord) {
                                            sb2.append("Proc{");
                                            sb2.append(
                                                    ((ProcessRecord) processStateRecord.mAdjSource)
                                                            .toShortString());
                                            sb2.append("}");
                                        } else if (obj3 != null) {
                                            sb2.append(obj3.toString());
                                        } else {
                                            sb2.append("{null}");
                                        }
                                        sb = sb2.toString();
                                    }
                                    arrayList2.add(new ProcessMemInfo(i, i2, str, str2, sb, i3));
                                }
                                ProcessProfileRecord processProfileRecord = processRecord4.mProfile;
                                if (processProfileRecord.mLastLowMemory
                                                + appProfiler.mService.mConstants.GC_MIN_INTERVAL
                                        <= j) {
                                    synchronized (appProfiler.mProfilerLock) {
                                        if (processStateRecord.mSetAdj <= 400) {
                                            processProfileRecord.mLastRequestedGc = 0L;
                                        } else {
                                            processProfileRecord.mLastRequestedGc =
                                                    processProfileRecord.mLastLowMemory;
                                        }
                                        processProfileRecord.mReportLowMemory = true;
                                        processProfileRecord.mLastLowMemory = j;
                                        appProfiler.mProcessesToGc.remove(processRecord4);
                                        appProfiler.addProcessToGcListLPf(processRecord4);
                                    }
                                }
                            }
                        },
                        false);
                if (z) {
                    this.mService.mHandler.sendMessage(
                            this.mService.mHandler.obtainMessage(33, arrayList));
                }
            }
        }
        synchronized (this.mProfilerLock) {
            scheduleAppGcsLPf();
        }
    }

    public final boolean dumpMemWatchProcessesLPf(PrintWriter printWriter, boolean z) {
        if (this.mMemWatchProcesses.getMap().size() > 0) {
            printWriter.println("  Mem watch processes:");
            ArrayMap map = this.mMemWatchProcesses.getMap();
            for (int size = map.size() - 1; size >= 0; size--) {
                String str = (String) map.keyAt(size);
                SparseArray sparseArray = (SparseArray) map.valueAt(size);
                for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                    if (z) {
                        printWriter.println();
                        z = false;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("    ");
                    sb.append(str);
                    sb.append('/');
                    UserHandle.formatUid(sb, sparseArray.keyAt(size2));
                    Pair pair = (Pair) sparseArray.valueAt(size2);
                    sb.append(": ");
                    DebugUtils.sizeValueToString(((Long) pair.first).longValue(), sb);
                    if (pair.second != null) {
                        sb.append(", report to ");
                        sb.append((String) pair.second);
                    }
                    printWriter.println(sb.toString());
                }
            }
            printWriter.print("  mMemWatchDumpProcName=");
            printWriter.println(this.mMemWatchDumpProcName);
            printWriter.print("  mMemWatchDumpUri=");
            printWriter.println(this.mMemWatchDumpUri);
            printWriter.print("  mMemWatchDumpPid=");
            printWriter.println(this.mMemWatchDumpPid);
            printWriter.print("  mMemWatchDumpUid=");
            printWriter.println(this.mMemWatchDumpUid);
            printWriter.print("  mMemWatchIsUserInitiated=");
            printWriter.println(this.mMemWatchIsUserInitiated);
        }
        return z;
    }

    public final void dumpProcessesToGc(PrintWriter printWriter, String str, boolean z) {
        if (this.mProcessesToGc.size() > 0) {
            long uptimeMillis = SystemClock.uptimeMillis();
            int size = this.mProcessesToGc.size();
            boolean z2 = false;
            for (int i = 0; i < size; i++) {
                ProcessRecord processRecord = (ProcessRecord) this.mProcessesToGc.get(i);
                if (str == null || str.equals(processRecord.info.packageName)) {
                    if (!z2) {
                        if (z) {
                            printWriter.println();
                        }
                        printWriter.println("  Processes that are waiting to GC:");
                        z = true;
                        z2 = true;
                    }
                    printWriter.print("    Process ");
                    printWriter.println(processRecord);
                    ProcessProfileRecord processProfileRecord = processRecord.mProfile;
                    printWriter.print("      lowMem=");
                    printWriter.print(processProfileRecord.mReportLowMemory);
                    printWriter.print(", last gced=");
                    printWriter.print(uptimeMillis - processProfileRecord.mLastRequestedGc);
                    printWriter.print(" ms ago, last lowMem=");
                    printWriter.print(uptimeMillis - processProfileRecord.mLastLowMemory);
                    printWriter.println(" ms ago");
                }
            }
        }
    }

    public final void forAllCpuStats(Consumer consumer) {
        synchronized (this.mProcessCpuTracker) {
            try {
                int countStats = this.mProcessCpuTracker.countStats();
                for (int i = 0; i < countStats; i++) {
                    consumer.accept(this.mProcessCpuTracker.getStats(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getLastMemoryLevelLocked() {
        int i = this.mMemFactorOverride;
        return i != -1 ? i : this.mLastMemoryLevel;
    }

    public final long getLowRamTimeSinceIdleLPr(long j) {
        long j2 = this.mLowRamStartTime;
        if (j2 > 0) {
            return j - j2;
        }
        return 0L;
    }

    public final void handleAbortDumpHeap(String str) {
        if (str != null) {
            synchronized (this.mProfilerLock) {
                try {
                    if (str.equals(this.mMemWatchDumpProcName)) {
                        this.mMemWatchDumpProcName = null;
                        this.mMemWatchDumpUri = null;
                        this.mMemWatchDumpPid = -1;
                        this.mMemWatchDumpUid = -1;
                    }
                } finally {
                }
            }
        }
    }

    public final void handlePostDumpHeapNotification() {
        int i;
        String str;
        long j;
        String str2;
        boolean z;
        synchronized (this.mProfilerLock) {
            try {
                i = this.mMemWatchDumpUid;
                str = this.mMemWatchDumpProcName;
                Pair pair = (Pair) this.mMemWatchProcesses.get(str, i);
                if (pair == null) {
                    pair = (Pair) this.mMemWatchProcesses.get(str, 0);
                }
                if (pair != null) {
                    j = ((Long) pair.first).longValue();
                    str2 = (String) pair.second;
                } else {
                    j = 0;
                    str2 = null;
                }
                z = this.mMemWatchIsUserInitiated;
                this.mMemWatchDumpUri = null;
                this.mMemWatchDumpProcName = null;
                this.mMemWatchDumpPid = -1;
                this.mMemWatchDumpUid = -1;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (str == null) {
            return;
        }
        Intent intent = new Intent("com.android.internal.intent.action.HEAP_DUMP_FINISHED");
        intent.setPackage("com.android.shell");
        intent.putExtra("android.intent.extra.UID", i);
        intent.putExtra("com.android.internal.extra.heap_dump.IS_USER_INITIATED", z);
        intent.putExtra("com.android.internal.extra.heap_dump.SIZE_BYTES", j);
        intent.putExtra("com.android.internal.extra.heap_dump.REPORT_PACKAGE", str2);
        intent.putExtra("com.android.internal.extra.heap_dump.PROCESS_NAME", str);
        this.mService.mContext.sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(i));
    }

    public final boolean isProfilingPss() {
        return !com.android.internal.hidden_from_bootclasspath.android.os.Flags
                        .removeAppProfilerPssCollection()
                || this.mService.mConstants.mForceEnablePssProfiling;
    }

    public final void performAppGcsIfAppropriateLocked() {
        synchronized (this.mProfilerLock) {
            try {
                if (this.mService.canGcNowLocked()) {
                    performAppGcsLPf();
                } else {
                    scheduleAppGcsLPf();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void performAppGcsLPf() {
        if (this.mProcessesToGc.size() <= 0) {
            return;
        }
        while (this.mProcessesToGc.size() > 0) {
            ProcessRecord processRecord = (ProcessRecord) this.mProcessesToGc.remove(0);
            ProcessProfileRecord processProfileRecord = processRecord.mProfile;
            if (processProfileRecord.mCurRawAdj > 200 || processProfileRecord.mReportLowMemory) {
                if (processProfileRecord.mLastRequestedGc + this.mService.mConstants.GC_MIN_INTERVAL
                        > SystemClock.uptimeMillis()) {
                    addProcessToGcListLPf(processRecord);
                    scheduleAppGcsLPf();
                }
                try {
                    ProcessProfileRecord processProfileRecord2 = processRecord.mProfile;
                    processProfileRecord2.mLastRequestedGc = SystemClock.uptimeMillis();
                    IApplicationThread iApplicationThread = processProfileRecord2.mThread;
                    if (iApplicationThread != null) {
                        if (processProfileRecord2.mReportLowMemory) {
                            processProfileRecord2.mReportLowMemory = false;
                            iApplicationThread.scheduleLowMemory();
                        } else {
                            iApplicationThread.processInBackground();
                        }
                    }
                } catch (Exception unused) {
                }
                scheduleAppGcsLPf();
                return;
            }
        }
        scheduleAppGcsLPf();
    }

    public final void printCurrentCpuState(StringBuilder sb, long j) {
        synchronized (this.mProcessCpuTracker) {
            sb.append(this.mProcessCpuTracker.printCurrentState(j, 10));
        }
    }

    public final void profileControlLPf(
            ProcessRecord processRecord, boolean z, ProfilerInfo profilerInfo, int i) {
        ParcelFileDescriptor parcelFileDescriptor;
        ParcelFileDescriptor parcelFileDescriptor2;
        ParcelFileDescriptor parcelFileDescriptor3;
        try {
            try {
                if (z) {
                    stopProfilerLPf(0, null);
                    this.mService.setProfileApp(
                            processRecord.info,
                            processRecord.processName,
                            profilerInfo,
                            processRecord.isSdkSandbox
                                    ? processRecord.getClientInfoForSdkSandbox()
                                    : null);
                    this.mProfileData.setProfileProc(processRecord);
                    this.mProfileType = i;
                    try {
                        parcelFileDescriptor3 = profilerInfo.profileFd.dup();
                    } catch (IOException unused) {
                        parcelFileDescriptor3 = null;
                    }
                    profilerInfo.profileFd = parcelFileDescriptor3;
                    processRecord.mProfile.mThread.profilerControl(z, profilerInfo, i);
                    try {
                        this.mProfileData.mProfilerInfo.profileFd.close();
                    } catch (IOException unused2) {
                    }
                    this.mProfileData.mProfilerInfo.profileFd = null;
                    if (processRecord.mPid == ActivityManagerService.MY_PID) {
                        profilerInfo = null;
                    }
                } else {
                    stopProfilerLPf(i, processRecord);
                    if (profilerInfo != null
                            && (parcelFileDescriptor2 = profilerInfo.profileFd) != null) {
                        try {
                            parcelFileDescriptor2.close();
                        } catch (IOException unused3) {
                        }
                    }
                }
                if (profilerInfo == null || r5 == null) {
                    return;
                }
                try {
                    profilerInfo.profileFd.close();
                } catch (IOException unused4) {
                }
            } finally {
                if (profilerInfo != null
                        && (parcelFileDescriptor = profilerInfo.profileFd) != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException unused5) {
                    }
                }
            }
        } catch (RemoteException unused6) {
            throw new IllegalStateException("Process disappeared");
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void recordPssSampleLPf(
            com.android.server.am.ProcessProfileRecord r34,
            int r35,
            long r36,
            long r38,
            long r40,
            long r42,
            int r44,
            long r45,
            long r47) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppProfiler.recordPssSampleLPf(com.android.server.am.ProcessProfileRecord,"
                    + " int, long, long, long, long, int, long, long):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void recordRssSampleLPf(
            com.android.server.am.ProcessProfileRecord r34,
            int r35,
            long r36,
            int r38,
            long r39,
            long r41) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppProfiler.recordRssSampleLPf(com.android.server.am.ProcessProfileRecord,"
                    + " int, long, int, long, long):void");
    }

    public final void requestPssAllProcsLPr(final long j, final boolean z) {
        synchronized (this.mProfilerLock) {
            try {
                this.mFullPssOrRssPending = true;
                for (int size = this.mPendingPssOrRssProfiles.size() - 1; size >= 0; size--) {
                    ((ProcessProfileRecord) this.mPendingPssOrRssProfiles.get(size))
                            .abortNextPssTime();
                }
                this.mPendingPssOrRssProfiles.ensureCapacity(
                        this.mService.mProcessList.mLruProcesses.size());
                this.mPendingPssOrRssProfiles.clear();
                this.mService.mProcessList.forEachLruProcessesLOSP(
                        new Consumer() { // from class:
                                         // com.android.server.am.AppProfiler$$ExternalSyntheticLambda2
                            public final /* synthetic */ boolean f$2 = true;

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i;
                                AppProfiler appProfiler = AppProfiler.this;
                                boolean z2 = z;
                                boolean z3 = this.f$2;
                                long j2 = j;
                                appProfiler.getClass();
                                ProcessProfileRecord processProfileRecord =
                                        ((ProcessRecord) obj).mProfile;
                                if (processProfileRecord.mThread == null
                                        || (i = processProfileRecord.mSetProcState) == 20) {
                                    return;
                                }
                                long j3 = processProfileRecord.mLastStateTime;
                                if (z2 || ((z3 && j2 > 1000 + j3) || j2 > j3 + 1200000)) {
                                    processProfileRecord.mPssProcState = i;
                                    processProfileRecord.mPssStatType = z3 ? 2 : 1;
                                    appProfiler.updateNextPssTimeLPf(
                                            i, processProfileRecord, j2, true);
                                    appProfiler.mPendingPssOrRssProfiles.add(processProfileRecord);
                                }
                            }
                        },
                        false);
                if (!this.mBgHandler.hasMessages(1)) {
                    this.mBgHandler.sendEmptyMessage(1);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void scheduleAppGcsLPf() {
        ActivityManagerService activityManagerService = this.mService;
        activityManagerService.mHandler.removeMessages(5);
        if (this.mProcessesToGc.size() > 0) {
            ProcessRecord processRecord = (ProcessRecord) this.mProcessesToGc.get(0);
            Message obtainMessage = activityManagerService.mHandler.obtainMessage(5);
            long j =
                    processRecord.mProfile.mLastRequestedGc
                            + activityManagerService.mConstants.GC_MIN_INTERVAL;
            long uptimeMillis = SystemClock.uptimeMillis();
            long j2 = activityManagerService.mConstants.GC_TIMEOUT;
            if (j < uptimeMillis + j2) {
                j = uptimeMillis + j2;
            }
            activityManagerService.mHandler.sendMessageAtTime(obtainMessage, j);
        }
    }

    public final void setAgentAppLPf(String str, String str2) {
        if (str2 == null) {
            Map map = this.mAppAgentMap;
            if (map != null) {
                map.remove(str);
                if (this.mAppAgentMap.isEmpty()) {
                    this.mAppAgentMap = null;
                    return;
                }
                return;
            }
            return;
        }
        if (this.mAppAgentMap == null) {
            this.mAppAgentMap = new HashMap();
        }
        if (this.mAppAgentMap.size() < 100) {
            this.mAppAgentMap.put(str, str2);
            return;
        }
        Slog.e(
                "ActivityManager",
                "App agent map has too many entries, cannot add " + str + "/" + str2);
    }

    public final void setTestPssMode(boolean z) {
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                this.mTestPssOrRssMode = z;
                if (z) {
                    requestPssAllProcsLPr(SystemClock.uptimeMillis(), true);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.ProfilerInfo setupProfilerInfoLocked(
            android.app.IApplicationThread r22,
            com.android.server.am.ProcessRecord r23,
            com.android.server.am.ActiveInstrumentation r24) {
        /*
            Method dump skipped, instructions count: 357
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppProfiler.setupProfilerInfoLocked(android.app.IApplicationThread,"
                    + " com.android.server.am.ProcessRecord,"
                    + " com.android.server.am.ActiveInstrumentation):android.app.ProfilerInfo");
    }

    public final void startHeapDumpLPf(ProcessProfileRecord processProfileRecord, boolean z) {
        ProcessRecord processRecord = processProfileRecord.mApp;
        String str = processRecord.processName;
        this.mMemWatchDumpProcName = str;
        this.mMemWatchDumpUri =
                Uri.parse("content://com.android.shell.heapdump/" + str + "_javaheap.bin");
        this.mMemWatchDumpPid = processProfileRecord.mPid;
        int i = processRecord.uid;
        this.mMemWatchDumpUid = i;
        this.mMemWatchIsUserInitiated = z;
        try {
            BackgroundThread.getHandler()
                    .post(
                            new RecordPssRunnable(
                                    processProfileRecord,
                                    this.mMemWatchDumpUri,
                                    this.mService
                                            .mContext
                                            .createPackageContextAsUser(
                                                    "android", 0, UserHandle.getUserHandleForUid(i))
                                            .getContentResolver()));
        } catch (PackageManager.NameNotFoundException unused) {
            throw new RuntimeException("android package not found.");
        }
    }

    public final void stopProfilerLPf(int i, ProcessRecord processRecord) {
        IApplicationThread iApplicationThread;
        ProfileData profileData = this.mProfileData;
        if (processRecord == null || processRecord == profileData.mProfileProc) {
            processRecord = profileData.mProfileProc;
            i = this.mProfileType;
            clearProfilerLPf();
        }
        if (processRecord == null
                || (iApplicationThread = processRecord.mProfile.mThread) == null) {
            return;
        }
        try {
            iApplicationThread.profilerControl(false, (ProfilerInfo) null, i);
        } catch (RemoteException unused) {
            throw new IllegalStateException("Process disappeared");
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCpuStatsNow() {
        /*
            Method dump skipped, instructions count: 606
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppProfiler.updateCpuStatsNow():void");
    }

    public final void updateNextPssTimeLPf(
            int i, ProcessProfileRecord processProfileRecord, long j, boolean z) {
        long j2 = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        if (!z) {
            if (j <= processProfileRecord.mNextPssTime) {
                long j3 =
                        processProfileRecord.mLastPssTime
                                + ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
                long j4 = processProfileRecord.mLastStateTime;
                boolean z2 = this.mTestPssOrRssMode;
                int i2 = ProcessList.PAGE_SIZE;
                if (j <= Math.max(j3, j4 + (z2 ? 10000L : 15000L))) {
                    return;
                }
            }
            if (this.mPendingPssOrRssProfiles.contains(processProfileRecord)) {
                return;
            }
            if (this.mPendingPssOrRssProfiles.size() == 0) {
                long j5 = 0;
                if (this.mPssDeferralTime > 0 && this.mActivityStartingNesting.get() > 0) {
                    j5 = this.mPssDeferralTime;
                }
                this.mBgHandler.sendEmptyMessageDelayed(1, j5);
            }
            processProfileRecord.mPssProcState = i;
            processProfileRecord.mPssStatType = 0;
            this.mPendingPssOrRssProfiles.add(processProfileRecord);
        }
        boolean z3 = this.mTestPssOrRssMode;
        boolean z4 = ActivityTaskManagerService.this.mSleeping;
        ProcessList.ProcStateMemTracker procStateMemTracker =
                processProfileRecord.mProcStateMemTracker;
        long max =
                Math.max(
                                processProfileRecord.mService.mBootCompletedTimestamp,
                                processProfileRecord.mService.mLastIdleTime)
                        + processProfileRecord.mService.mConstants.FULL_PSS_MIN_INTERVAL;
        int i3 = ProcessList.sProcStateToProcMem[i];
        float f = 1.0f;
        if (procStateMemTracker != null) {
            int i4 = procStateMemTracker.mTotalHighestMem;
            if (i3 < i4) {
                i4 = i3;
            }
            r0 = i4 < procStateMemTracker.mHighestMem[i3];
            procStateMemTracker.mPendingMemState = i3;
            procStateMemTracker.mPendingHighestMemState = i4;
            if (r0) {
                procStateMemTracker.mPendingScalingFactor = 1.0f;
            } else {
                f = procStateMemTracker.mScalingFactor[i3];
                procStateMemTracker.mPendingScalingFactor = 1.5f * f;
            }
        }
        long j6 =
                (long)
                        ((z3
                                                ? r0
                                                        ? ProcessList.sTestFirstPssTimes
                                                        : ProcessList.sTestSamePssTimes
                                                : r0
                                                        ? z4
                                                                ? ProcessList.sFirstAsleepPssTimes
                                                                : ProcessList.sFirstAwakePssTimes
                                                        : z4
                                                                ? ProcessList.sSameAsleepPssTimes
                                                                : ProcessList.sSameAwakePssTimes)
                                        [i3]
                                * f);
        if (j6 <= ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
            j2 = j6;
        }
        processProfileRecord.mNextPssTime = Math.max(j + j2, max);
    }

    public final void writeMemWatchProcessToProtoLPf(ProtoOutputStream protoOutputStream) {
        if (this.mMemWatchProcesses.getMap().size() > 0) {
            long start = protoOutputStream.start(1146756268064L);
            ArrayMap map = this.mMemWatchProcesses.getMap();
            for (int i = 0; i < map.size(); i++) {
                String str = (String) map.keyAt(i);
                SparseArray sparseArray = (SparseArray) map.valueAt(i);
                long start2 = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1138166333441L, str);
                for (int size = sparseArray.size() - 1; size >= 0; size--) {
                    long start3 = protoOutputStream.start(2246267895810L);
                    Pair pair = (Pair) sparseArray.valueAt(size);
                    protoOutputStream.write(1120986464257L, sparseArray.keyAt(size));
                    protoOutputStream.write(
                            1138166333442L,
                            DebugUtils.sizeValueToString(
                                    ((Long) pair.first).longValue(), new StringBuilder()));
                    protoOutputStream.write(1138166333443L, (String) pair.second);
                    protoOutputStream.end(start3);
                }
                protoOutputStream.end(start2);
            }
            long start4 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1138166333441L, this.mMemWatchDumpProcName);
            protoOutputStream.write(1138166333446L, this.mMemWatchDumpUri.toString());
            protoOutputStream.write(1120986464259L, this.mMemWatchDumpPid);
            protoOutputStream.write(1120986464260L, this.mMemWatchDumpUid);
            protoOutputStream.write(1133871366149L, this.mMemWatchIsUserInitiated);
            protoOutputStream.end(start4);
            protoOutputStream.end(start);
        }
    }

    public final void writeProcessesToGcToProto(ProtoOutputStream protoOutputStream, String str) {
        if (this.mProcessesToGc.size() > 0) {
            long uptimeMillis = SystemClock.uptimeMillis();
            int size = this.mProcessesToGc.size();
            for (int i = 0; i < size; i++) {
                ProcessRecord processRecord = (ProcessRecord) this.mProcessesToGc.get(i);
                if (str == null || str.equals(processRecord.info.packageName)) {
                    long start = protoOutputStream.start(2246267895820L);
                    ProcessProfileRecord processProfileRecord = processRecord.mProfile;
                    processRecord.dumpDebug(protoOutputStream, 1146756268033L, -1);
                    protoOutputStream.write(1133871366146L, processProfileRecord.mReportLowMemory);
                    protoOutputStream.write(1112396529667L, uptimeMillis);
                    protoOutputStream.write(1112396529668L, processProfileRecord.mLastRequestedGc);
                    protoOutputStream.write(1112396529669L, processProfileRecord.mLastLowMemory);
                    protoOutputStream.end(start);
                }
            }
        }
    }
}
