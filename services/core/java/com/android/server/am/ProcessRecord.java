package com.android.server.am;

import android.app.ActivityManager;
import android.app.BackgroundStartPrivileges;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.ProcessInfo;
import android.content.pm.VersionedPackage;
import android.content.res.CompatibilityInfo;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.system.OsConstants;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.EventLog;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.os.Zygote;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.chimera.umr.KernelMemoryProxy$ReclaimerLog;
import com.android.server.wm.BackgroundLaunchProcessController;
import com.android.server.wm.WindowProcessController;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProcessRecord {
    public int AMSExceptionFlag;
    public long activeLaunchTime;
    public long appKeepingTime;
    public final boolean appZygote;
    public String callStack;
    public int dhaKeepEmptyFlag;
    public boolean frozenMARs;
    public volatile ApplicationInfo info;
    public boolean isAMSException;
    public boolean isActiveLaunch;
    public boolean isForKeeping;
    public boolean isNeverKillException;
    public final boolean isSdkSandbox;
    public final boolean isolated;
    public final ArrayMap mBackgroundStartPrivileges;
    public BackgroundStartPrivileges mBackgroundStartPrivilegesMerged;
    public volatile long mBindApplicationTime;
    public volatile boolean mBindMountPending;
    public volatile boolean mClearedWaitingToKill;
    public CompatibilityInfo mCompat;
    public IBinder.DeathRecipient mDeathRecipient;
    public boolean mDebugging;
    public boolean mDedicated;
    public long[] mDisabledCompatChanges;
    public int mDyingPid;
    public final ProcessErrorStateRecord mErrorState;
    public boolean mExcessiveResourceUsage = false;
    public boolean mFixedAppContextDisplay;
    public int[] mGids;
    public volatile HostingRecord mHostingRecord;
    public boolean mInFullBackup;
    public boolean mInfant;
    public ActiveInstrumentation mInstr;
    public String mInstructionSet;
    public int mIpmLaunchType;
    public volatile boolean mIsCancelFromSeq;
    public boolean mIsRemovedName;
    public String mIsolatedEntryPoint;
    public String[] mIsolatedEntryPointArgs;
    public boolean mKeepSEMPrcp;
    public int mKillProcessTimeout;
    public long mKillTime;
    public boolean mKilled;
    public boolean mKilledByAm;
    public long mLastActivityTime;
    public final OomAdjusterModernImpl.ProcessRecordNode[] mLinkedNodes;
    public long[] mLoggableCompatChanges;
    public int mLruSeq;
    public volatile int mMountMode;
    public IApplicationThread mOnewayThread;
    public final ProcessCachedOptimizerRecord mOptRecord;
    public boolean mPendingFinishAttach;
    public boolean mPendingStart;
    public volatile boolean mPersistent;
    public int mPid;
    public ArraySet mPkgDeps;
    public final PackageList mPkgList;
    public volatile ProcessRecord mPredecessor;
    public final ActivityManagerGlobalLock mProcLock;
    public volatile boolean mProcessGroupCreated;
    public final ProcessProfileRecord mProfile;
    public final ProcessProviderRecord mProviders;
    public long mRSSresiduePostFCA;
    public final ProcessReceiverRecord mReceivers;
    public volatile boolean mRemoved;
    public int mRenderThreadTid;
    public String mRequiredAbi;
    public volatile String mSeInfo;
    public final ActivityManagerService mService;
    public final ProcessServiceRecord mServices;
    public String mShortStringName;
    public volatile boolean mSkipProcessGroupCreation;
    public volatile long mStartElapsedTime;
    public long mStartSeq;
    public volatile int mStartUid;
    public volatile long mStartUptime;
    public final ProcessStateRecord mState;
    public String mStringName;
    public volatile ProcessRecord mSuccessor;
    public Runnable mSuccessorStartRunnable;
    public boolean mTGLCallbackPosted;
    public IApplicationThread mThread;
    public UidRecord mUidRecord;
    public boolean mUnlocked;
    public boolean mUsingWrapper;
    public boolean mWaitedForDebugger;
    public String mWaitingToKill;
    public volatile boolean mWasForceStopped;
    public final WindowProcessController mWindowProcessController;
    public long mlLaunchTime;
    public final ProcessInfo processInfo;
    public final String processName;
    public final String sdkSandboxClientAppPackage;
    public final String sdkSandboxClientAppVolumeUuid;
    public final int uid;
    public final int userId;

    /* JADX WARN: Code restructure failed: missing block: B:15:0x009b, code lost:

       if (r1.nativeHeapZeroInitialized == (-1)) goto L21;
    */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ProcessRecord(
            com.android.server.am.ActivityManagerService r14,
            android.content.pm.ApplicationInfo r15,
            java.lang.String r16,
            int r17,
            java.lang.String r18,
            int r19,
            java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 323
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ProcessRecord.<init>(com.android.server.am.ActivityManagerService,"
                    + " android.content.pm.ApplicationInfo, java.lang.String, int,"
                    + " java.lang.String, int, java.lang.String):void");
    }

    public static void updateProcessRecordNodes(ProcessRecord processRecord) {
        if (!processRecord.mService.mConstants.ENABLE_NEW_OOMADJ) {
            return;
        }
        int i = 0;
        while (true) {
            OomAdjusterModernImpl.ProcessRecordNode[] processRecordNodeArr =
                    processRecord.mLinkedNodes;
            if (i >= processRecordNodeArr.length) {
                return;
            }
            processRecordNodeArr[i] = new OomAdjusterModernImpl.ProcessRecordNode(processRecord);
            i++;
        }
    }

    public final void addOrUpdateBackgroundStartPrivileges(
            Binder binder, BackgroundStartPrivileges backgroundStartPrivileges) {
        Objects.requireNonNull(binder, "entity");
        Objects.requireNonNull(backgroundStartPrivileges, "backgroundStartPrivileges");
        Preconditions.checkArgument(
                backgroundStartPrivileges.allowsAny(),
                "backgroundStartPrivileges does not allow anything");
        WindowProcessController windowProcessController = this.mWindowProcessController;
        windowProcessController.getClass();
        Preconditions.checkArgument(
                backgroundStartPrivileges.allowsAny(),
                "backgroundStartPrivileges does not allow anything");
        BackgroundLaunchProcessController backgroundLaunchProcessController =
                windowProcessController.mBgLaunchController;
        backgroundLaunchProcessController.getClass();
        Preconditions.checkArgument(
                backgroundStartPrivileges.allowsAny(),
                "backgroundStartPrivileges does not allow anything");
        synchronized (backgroundLaunchProcessController) {
            try {
                if (backgroundLaunchProcessController.mBackgroundStartPrivileges == null) {
                    backgroundLaunchProcessController.mBackgroundStartPrivileges = new ArrayMap();
                }
                backgroundLaunchProcessController.mBackgroundStartPrivileges.put(
                        binder, backgroundStartPrivileges);
            } catch (Throwable th) {
                throw th;
            }
        }
        setBackgroundStartPrivileges(binder, backgroundStartPrivileges);
    }

    public final void addPackage(String str, long j, ProcessStatsService processStatsService) {
        synchronized (processStatsService.mLock) {
            synchronized (this.mPkgList) {
                try {
                    if (this.mPkgList.containsKey(str)) {
                        return;
                    }
                    ProcessStats.ProcessStateHolder processStateHolder =
                            new ProcessStats.ProcessStateHolder(j);
                    ProcessState processState = this.mProfile.mBaseProcessTracker;
                    if (processState != null) {
                        int i = this.info.uid;
                        String str2 = this.processName;
                        ProcessStats.PackageState packageStateLocked =
                                processStatsService.mProcessStats.getPackageStateLocked(str, i, j);
                        processStateHolder.pkg = packageStateLocked;
                        processStateHolder.state =
                                processStatsService.mProcessStats.getProcessStateLocked(
                                        packageStateLocked, str2);
                        this.mPkgList.put(str, processStateHolder);
                        ProcessState processState2 = processStateHolder.state;
                        if (processState2 != processState) {
                            processState2.makeActive();
                        }
                    } else {
                        this.mPkgList.put(str, processStateHolder);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void dump(PrintWriter printWriter) {
        long uptimeMillis = SystemClock.uptimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        printWriter.print("    ");
        printWriter.print("user #");
        printWriter.print(this.userId);
        printWriter.print(" uid=");
        printWriter.print(this.info.uid);
        if (this.uid != this.info.uid) {
            printWriter.print(" ISOLATED uid=");
            printWriter.print(this.uid);
        }
        printWriter.print(" gids={");
        if (this.mGids != null) {
            for (int i = 0; i < this.mGids.length; i++) {
                if (i != 0) {
                    printWriter.print(", ");
                }
                printWriter.print(this.mGids[i]);
            }
        }
        printWriter.println("}");
        if (this.processInfo != null) {
            printWriter.print("    ");
            printWriter.println("processInfo:");
            if (this.processInfo.deniedPermissions != null) {
                for (int i2 = 0; i2 < this.processInfo.deniedPermissions.size(); i2++) {
                    printWriter.print("    ");
                    printWriter.print("  deny: ");
                    printWriter.println((String) this.processInfo.deniedPermissions.valueAt(i2));
                }
            }
            if (this.processInfo.gwpAsanMode != -1) {
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter, "    ", "  gwpAsanMode="),
                        this.processInfo.gwpAsanMode,
                        printWriter);
            }
            if (this.processInfo.memtagMode != -1) {
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter, "    ", "  memtagMode="),
                        this.processInfo.memtagMode,
                        printWriter);
            }
        }
        printWriter.print("    ");
        printWriter.print("mRequiredAbi=");
        printWriter.print(this.mRequiredAbi);
        printWriter.print(" instructionSet=");
        printWriter.println(this.mInstructionSet);
        if (this.info.className != null) {
            printWriter.print("    ");
            printWriter.print("class=");
            printWriter.println(this.info.className);
        }
        if (this.info.manageSpaceActivityName != null) {
            printWriter.print("    ");
            printWriter.print("manageSpaceActivityName=");
            printWriter.println(this.info.manageSpaceActivityName);
        }
        printWriter.print("    ");
        printWriter.print("dir=");
        printWriter.print(this.info.sourceDir);
        printWriter.print(" publicDir=");
        printWriter.print(this.info.publicSourceDir);
        printWriter.print(" data=");
        printWriter.println(this.info.dataDir);
        PackageList packageList = this.mPkgList;
        synchronized (packageList) {
            try {
                printWriter.print("    ");
                printWriter.print("packageList={");
                int size = packageList.mPkgList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    if (i3 > 0) {
                        printWriter.print(", ");
                    }
                    printWriter.print((String) packageList.mPkgList.keyAt(i3));
                }
                printWriter.println("}");
            } finally {
            }
        }
        if (this.mPkgDeps != null) {
            printWriter.print("    ");
            printWriter.print("packageDependencies={");
            for (int i4 = 0; i4 < this.mPkgDeps.size(); i4++) {
                if (i4 > 0) {
                    printWriter.print(", ");
                }
                printWriter.print((String) this.mPkgDeps.valueAt(i4));
            }
            printWriter.println("}");
        }
        printWriter.print("    ");
        printWriter.print("compat=");
        printWriter.println(this.mCompat);
        if (this.mInstr != null) {
            printWriter.print("    ");
            printWriter.print("mInstr=");
            printWriter.println(this.mInstr);
        }
        printWriter.print("    ");
        printWriter.print("thread=");
        printWriter.println(this.mThread);
        printWriter.print("    ");
        printWriter.print("pid=");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                this.mPid, printWriter, "    ", "lastActivityTime=");
        TimeUtils.formatDuration(this.mLastActivityTime, uptimeMillis, printWriter);
        printWriter.print("    ");
        printWriter.print("startUpTime=");
        TimeUtils.formatDuration(this.mStartUptime, uptimeMillis, printWriter);
        printWriter.print("    ");
        printWriter.print("startElapsedTime=");
        TimeUtils.formatDuration(this.mStartElapsedTime, elapsedRealtime, printWriter);
        printWriter.println();
        if (this.mPersistent || this.mRemoved) {
            printWriter.print("    ");
            printWriter.print("persistent=");
            printWriter.print(this.mPersistent);
            printWriter.print(" removed=");
            printWriter.println(this.mRemoved);
        }
        if (this.mDebugging) {
            printWriter.print("    ");
            printWriter.print("mDebugging=");
            printWriter.println(this.mDebugging);
        }
        if (this.mPendingStart) {
            printWriter.print("    ");
            printWriter.print("pendingStart=");
            printWriter.println(this.mPendingStart);
        }
        printWriter.print("    ");
        printWriter.print("startSeq=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mStartSeq, printWriter, "    ", "mountMode=");
        printWriter.println(
                DebugUtils.valueToString(Zygote.class, "MOUNT_EXTERNAL_", this.mMountMode));
        if (this.mKilled || this.mKilledByAm || this.mWaitingToKill != null) {
            printWriter.print("    ");
            printWriter.print("killed=");
            printWriter.print(this.mKilled);
            printWriter.print(" killedByAm=");
            printWriter.print(this.mKilledByAm);
            printWriter.print(" waitingToKill=");
            printWriter.println(this.mWaitingToKill);
        }
        if (this.mIsolatedEntryPoint != null || this.mIsolatedEntryPointArgs != null) {
            printWriter.print("    ");
            printWriter.print("isolatedEntryPoint=");
            ProcessList$$ExternalSyntheticOutline0.m(
                    printWriter, this.mIsolatedEntryPoint, "    ", "isolatedEntryPointArgs=");
            printWriter.println(Arrays.toString(this.mIsolatedEntryPointArgs));
        }
        if (this.mState.mSetProcState > 10) {
            ProcessProfileRecord processProfileRecord = this.mProfile;
            long j = processProfileRecord.mLastCpuTime.get();
            printWriter.print("    ");
            printWriter.print("lastCpuTime=");
            printWriter.print(j);
            if (j > 0) {
                printWriter.print(" timeUsed=");
                TimeUtils.formatDuration(processProfileRecord.mCurCpuTime.get() - j, printWriter);
            }
            printWriter.println();
        }
        ProcessProfileRecord processProfileRecord2 = this.mProfile;
        synchronized (processProfileRecord2.mProfilerLock) {
            try {
                if (processProfileRecord2.mService.mAppProfiler.isProfilingPss()) {
                    printWriter.print("    ");
                    printWriter.print("lastPssTime=");
                    TimeUtils.formatDuration(
                            processProfileRecord2.mLastPssTime, uptimeMillis, printWriter);
                    printWriter.print(" pssProcState=");
                    printWriter.print(processProfileRecord2.mPssProcState);
                    printWriter.print(" pssStatType=");
                    printWriter.print(processProfileRecord2.mPssStatType);
                    printWriter.print(" nextPssTime=");
                    TimeUtils.formatDuration(
                            processProfileRecord2.mNextPssTime, uptimeMillis, printWriter);
                    printWriter.println();
                    printWriter.print("    ");
                    printWriter.print("lastPss=");
                    DebugUtils.printSizeValue(printWriter, processProfileRecord2.mLastPss * 1024);
                    printWriter.print(" lastSwapPss=");
                    DebugUtils.printSizeValue(
                            printWriter, processProfileRecord2.mLastSwapPss * 1024);
                    printWriter.print(" lastCachedPss=");
                    DebugUtils.printSizeValue(
                            printWriter, processProfileRecord2.mLastCachedPss * 1024);
                    printWriter.print(" lastCachedSwapPss=");
                    DebugUtils.printSizeValue(
                            printWriter, processProfileRecord2.mLastCachedSwapPss * 1024);
                    printWriter.print(" lastRss=");
                    DebugUtils.printSizeValue(printWriter, processProfileRecord2.mLastRss * 1024);
                } else {
                    printWriter.print("    ");
                    printWriter.print("lastRssTime=");
                    TimeUtils.formatDuration(
                            processProfileRecord2.mLastPssTime, uptimeMillis, printWriter);
                    printWriter.print(" rssProcState=");
                    printWriter.print(processProfileRecord2.mPssProcState);
                    printWriter.print(" rssStatType=");
                    printWriter.print(processProfileRecord2.mPssStatType);
                    printWriter.print(" nextRssTime=");
                    TimeUtils.formatDuration(
                            processProfileRecord2.mNextPssTime, uptimeMillis, printWriter);
                    printWriter.println();
                    printWriter.print("    ");
                    printWriter.print("lastRss=");
                    DebugUtils.printSizeValue(printWriter, processProfileRecord2.mLastRss * 1024);
                    printWriter.print(" lastCachedRss=");
                    DebugUtils.printSizeValue(
                            printWriter, processProfileRecord2.mLastCachedRss * 1024);
                }
                printWriter.println();
                printWriter.print("    ");
                printWriter.print("trimMemoryLevel=");
                printWriter.println(processProfileRecord2.mTrimMemoryLevel);
                printWriter.print("    ");
                printWriter.print("procStateMemTracker: ");
                processProfileRecord2.mProcStateMemTracker.dumpLine(printWriter);
                printWriter.print("    ");
                printWriter.print("lastRequestedGc=");
                TimeUtils.formatDuration(
                        processProfileRecord2.mLastRequestedGc, uptimeMillis, printWriter);
                printWriter.print(" lastLowMemory=");
                TimeUtils.formatDuration(
                        processProfileRecord2.mLastLowMemory, uptimeMillis, printWriter);
                printWriter.print(" reportLowMemory=");
                printWriter.println(processProfileRecord2.mReportLowMemory);
            } finally {
            }
        }
        printWriter.print("    ");
        printWriter.print("currentHostingComponentTypes=0x");
        printWriter.print(
                Integer.toHexString(processProfileRecord2.mCurrentHostingComponentTypes.get()));
        printWriter.print(" historicalHostingComponentTypes=0x");
        printWriter.println(
                Integer.toHexString(processProfileRecord2.mHistoricalHostingComponentTypes.get()));
        ProcessStateRecord processStateRecord = this.mState;
        if (processStateRecord.mReportedInteraction || processStateRecord.mFgInteractionTime != 0) {
            printWriter.print("    ");
            printWriter.print("reportedInteraction=");
            printWriter.print(processStateRecord.mReportedInteraction);
            if (processStateRecord.mInteractionEventTime != 0) {
                printWriter.print(" time=");
                TimeUtils.formatDuration(
                        processStateRecord.mInteractionEventTime,
                        SystemClock.elapsedRealtime(),
                        printWriter);
            }
            if (processStateRecord.mFgInteractionTime != 0) {
                printWriter.print(" fgInteractionTime=");
                TimeUtils.formatDuration(
                        processStateRecord.mFgInteractionTime,
                        SystemClock.elapsedRealtime(),
                        printWriter);
            }
            printWriter.println();
        }
        printWriter.print("    ");
        printWriter.print("adjSeq=");
        printWriter.print(processStateRecord.mAdjSeq);
        printWriter.print(" lruSeq=");
        ProcessRecord processRecord = processStateRecord.mApp;
        BroadcastStats$$ExternalSyntheticOutline0.m(
                processRecord.mLruSeq, printWriter, "    ", "oom adj: max=");
        printWriter.print(processStateRecord.mMaxAdj);
        printWriter.print(" curRaw=");
        printWriter.print(processStateRecord.mCurRawAdj);
        printWriter.print(" setRaw=");
        printWriter.print(processStateRecord.mSetRawAdj);
        printWriter.print(" cur=");
        printWriter.print(processStateRecord.mCurAdj);
        printWriter.print(" set=");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                processStateRecord.mSetAdj, printWriter, "    ", "mCurSchedGroup=");
        printWriter.print(processStateRecord.mCurSchedGroup);
        printWriter.print(" setSchedGroup=");
        printWriter.print(processStateRecord.mSetSchedGroup);
        printWriter.print(" systemNoUi=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, "    ", "curProcState=", processStateRecord.mSystemNoUi);
        printWriter.print(processStateRecord.mCurProcState);
        printWriter.print(" mRepProcState=");
        printWriter.print(processStateRecord.mRepProcState);
        printWriter.print(" setProcState=");
        printWriter.print(processStateRecord.mSetProcState);
        printWriter.print(" lastStateTime=");
        TimeUtils.formatDuration(processStateRecord.mLastStateTime, uptimeMillis, printWriter);
        printWriter.println();
        printWriter.print("    ");
        printWriter.print("curCapability=");
        ActivityManager.printCapabilitiesFull(printWriter, processStateRecord.mCurCapability);
        printWriter.print(" setCapability=");
        ActivityManager.printCapabilitiesFull(printWriter, processStateRecord.mSetCapability);
        printWriter.println();
        if (processStateRecord.mBackgroundRestricted) {
            printWriter.print(" backgroundRestricted=");
            printWriter.print(processStateRecord.mBackgroundRestricted);
            printWriter.print(" boundByNonBgRestrictedApp=");
            printWriter.print(processStateRecord.mSetBoundByNonBgRestrictedApp);
        }
        printWriter.println();
        if (processStateRecord.mHasShownUi || processRecord.mProfile.mPendingUiClean) {
            printWriter.print("    ");
            printWriter.print("hasShownUi=");
            printWriter.print(processStateRecord.mHasShownUi);
            printWriter.print(" pendingUiClean=");
            printWriter.println(processRecord.mProfile.mPendingUiClean);
        }
        printWriter.print("    ");
        printWriter.print("cached=");
        printWriter.print(processStateRecord.isCached());
        printWriter.print(" empty=");
        printWriter.println(processStateRecord.mCurProcState >= 19);
        if (processStateRecord.mServiceB) {
            printWriter.print("    ");
            printWriter.print("serviceb=");
            printWriter.print(processStateRecord.mServiceB);
            printWriter.print(" serviceHighRam=");
            printWriter.println(processStateRecord.mServiceHighRam);
        }
        if (processStateRecord.mNotCachedSinceIdle) {
            printWriter.print("    ");
            printWriter.print("notCachedSinceIdle=");
            printWriter.print(processStateRecord.mNotCachedSinceIdle);
            if (processStateRecord.mService.mAppProfiler.isProfilingPss()) {
                printWriter.print(" initialIdlePss=");
            } else {
                printWriter.print(" initialIdleRss=");
            }
            printWriter.println(processRecord.mProfile.mInitialIdlePssOrRss);
        }
        if (processStateRecord.mHasTopUi
                || processStateRecord.mHasOverlayUi
                || processStateRecord.mRunningRemoteAnimation) {
            printWriter.print("    ");
            printWriter.print("hasTopUi=");
            printWriter.print(processStateRecord.mHasTopUi);
            printWriter.print(" hasOverlayUi=");
            printWriter.print(processStateRecord.mHasOverlayUi);
            printWriter.print(" runningRemoteAnimation=");
            printWriter.println(processStateRecord.mRunningRemoteAnimation);
        }
        if (processStateRecord.mHasForegroundActivities
                || processStateRecord.mRepForegroundActivities) {
            printWriter.print("    ");
            printWriter.print("foregroundActivities=");
            printWriter.print(processStateRecord.mHasForegroundActivities);
            printWriter.print(" (rep=");
            printWriter.print(processStateRecord.mRepForegroundActivities);
            printWriter.println(")");
        }
        if (processStateRecord.mSetProcState > 10) {
            printWriter.print("    ");
            printWriter.print("whenUnimportant=");
            TimeUtils.formatDuration(
                    processStateRecord.mWhenUnimportant - uptimeMillis, printWriter);
            printWriter.println();
        }
        if (processStateRecord.mLastTopTime > 0) {
            printWriter.print("    ");
            printWriter.print("lastTopTime=");
            TimeUtils.formatDuration(processStateRecord.mLastTopTime, uptimeMillis, printWriter);
            printWriter.println();
        }
        long j2 = processStateRecord.mLastInvisibleTime;
        if (j2 > 0 && j2 < Long.MAX_VALUE) {
            printWriter.print("    ");
            printWriter.print("lastInvisibleTime=");
            long elapsedRealtime2 = SystemClock.elapsedRealtime();
            long currentTimeMillis = System.currentTimeMillis();
            TimeUtils.dumpTimeWithDelta(
                    printWriter,
                    (currentTimeMillis - elapsedRealtime2) + processStateRecord.mLastInvisibleTime,
                    currentTimeMillis);
            printWriter.println();
        }
        if (processStateRecord.mHasStartedServices) {
            printWriter.print("    ");
            printWriter.print("hasStartedServices=");
            printWriter.println(processStateRecord.mHasStartedServices);
        }
        ProcessErrorStateRecord processErrorStateRecord = this.mErrorState;
        ActivityManagerGlobalLock activityManagerGlobalLock = processErrorStateRecord.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                if (!processErrorStateRecord.mCrashing) {
                    ErrorDialogController errorDialogController =
                            processErrorStateRecord.mDialogController;
                    if (errorDialogController.mCrashDialogs == null) {
                        if (!processErrorStateRecord.mNotResponding) {
                            if (errorDialogController.mAnrDialogs == null) {
                                if (processErrorStateRecord.mBad) {}
                            }
                        }
                    }
                }
                printWriter.print("    ");
                printWriter.print(" mCrashing=" + processErrorStateRecord.mCrashing);
                printWriter.print(" " + processErrorStateRecord.mDialogController.mCrashDialogs);
                printWriter.print(" mNotResponding=" + processErrorStateRecord.mNotResponding);
                printWriter.print(" " + processErrorStateRecord.mDialogController.mAnrDialogs);
                printWriter.print(" bad=" + processErrorStateRecord.mBad);
                if (processErrorStateRecord.mErrorReportReceiver != null) {
                    printWriter.print(" errorReportReceiver=");
                    printWriter.print(
                            processErrorStateRecord.mErrorReportReceiver.flattenToShortString());
                }
                printWriter.println();
            } finally {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        ProcessServiceRecord processServiceRecord = this.mServices;
        boolean z = processServiceRecord.mHasForegroundServices;
        ProcessRecord processRecord2 = processServiceRecord.mApp;
        if (z || processRecord2.mState.mForcingToImportant != null) {
            printWriter.print("    ");
            printWriter.print("mHasForegroundServices=");
            printWriter.print(processServiceRecord.mHasForegroundServices);
            printWriter.print(" forcingToImportant=");
            printWriter.println(processRecord2.mState.mForcingToImportant);
        }
        if (processServiceRecord.mHasTopStartedAlmostPerceptibleServices
                || processServiceRecord.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs > 0) {
            printWriter.print("    ");
            printWriter.print("mHasTopStartedAlmostPerceptibleServices=");
            printWriter.print(processServiceRecord.mHasTopStartedAlmostPerceptibleServices);
            printWriter.print(" mLastTopStartedAlmostPerceptibleBindRequestUptimeMs=");
            printWriter.println(
                    processServiceRecord.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs);
        }
        if (processServiceRecord.mHasClientActivities
                || processServiceRecord.mHasAboveClient
                || processServiceRecord.mTreatLikeActivity) {
            printWriter.print("    ");
            printWriter.print("hasClientActivities=");
            printWriter.print(processServiceRecord.mHasClientActivities);
            printWriter.print(" hasAboveClient=");
            printWriter.print(processServiceRecord.mHasAboveClient);
            printWriter.print(" treatLikeActivity=");
            printWriter.println(processServiceRecord.mTreatLikeActivity);
        }
        if (processServiceRecord.mConnectionService != null
                || processServiceRecord.mConnectionGroup != 0) {
            printWriter.print("    ");
            printWriter.print("connectionGroup=");
            printWriter.print(processServiceRecord.mConnectionGroup);
            printWriter.print(" Importance=");
            printWriter.print(processServiceRecord.mConnectionImportance);
            printWriter.print(" Service=");
            printWriter.println(processServiceRecord.mConnectionService);
        }
        if (processServiceRecord.mAllowlistManager) {
            printWriter.print("    ");
            printWriter.print("allowlistManager=");
            printWriter.println(processServiceRecord.mAllowlistManager);
        }
        if (processServiceRecord.mServices.size() > 0) {
            printWriter.print("    ");
            printWriter.println("Services:");
            int size2 = processServiceRecord.mServices.size();
            for (int i5 = 0; i5 < size2; i5++) {
                printWriter.print("    ");
                printWriter.print("  - ");
                printWriter.println(processServiceRecord.mServices.valueAt(i5));
            }
        }
        if (processServiceRecord.mExecutingServices.size() > 0) {
            printWriter.print("    ");
            printWriter.print("Executing Services (fg=");
            printWriter.print(processServiceRecord.mExecServicesFg);
            printWriter.println(")");
            int size3 = processServiceRecord.mExecutingServices.size();
            for (int i6 = 0; i6 < size3; i6++) {
                printWriter.print("    ");
                printWriter.print("  - ");
                printWriter.println(processServiceRecord.mExecutingServices.valueAt(i6));
            }
        }
        if (processServiceRecord.mConnections.size() > 0) {
            printWriter.print("    ");
            printWriter.println("mConnections:");
            int size4 = processServiceRecord.mConnections.size();
            for (int i7 = 0; i7 < size4; i7++) {
                printWriter.print("    ");
                printWriter.print("  - ");
                printWriter.println(processServiceRecord.mConnections.valueAt(i7));
            }
        }
        Flags.serviceBindingOomAdjPolicy();
        printWriter.print("    ");
        printWriter.print("scheduleServiceTimeoutPending=");
        printWriter.println(processServiceRecord.mScheduleServiceTimeoutPending);
        ProcessProviderRecord processProviderRecord = this.mProviders;
        if (processProviderRecord.mLastProviderTime > 0) {
            printWriter.print("    ");
            printWriter.print("lastProviderTime=");
            TimeUtils.formatDuration(
                    processProviderRecord.mLastProviderTime, uptimeMillis, printWriter);
            printWriter.println();
        }
        if (processProviderRecord.mPubProviders.size() > 0) {
            printWriter.print("    ");
            printWriter.println("Published Providers:");
            int size5 = processProviderRecord.mPubProviders.size();
            for (int i8 = 0; i8 < size5; i8++) {
                printWriter.print("    ");
                printWriter.print("  - ");
                ProcessList$$ExternalSyntheticOutline0.m(
                        printWriter,
                        (String) processProviderRecord.mPubProviders.keyAt(i8),
                        "    ",
                        "    -> ");
                printWriter.println(processProviderRecord.mPubProviders.valueAt(i8));
            }
        }
        if (processProviderRecord.mConProviders.size() > 0) {
            printWriter.print("    ");
            printWriter.println("Connected Providers:");
            int size6 = processProviderRecord.mConProviders.size();
            for (int i9 = 0; i9 < size6; i9++) {
                printWriter.print("    ");
                printWriter.print("  - ");
                ContentProviderConnection contentProviderConnection =
                        (ContentProviderConnection) processProviderRecord.mConProviders.get(i9);
                StringBuilder sb = new StringBuilder(128);
                sb.append(contentProviderConnection.provider.toShortString());
                sb.append("->");
                contentProviderConnection.toClientString(sb);
                printWriter.println(sb.toString());
            }
        }
        ProcessReceiverRecord processReceiverRecord = this.mReceivers;
        if (!processReceiverRecord.mCurReceivers.isEmpty()) {
            printWriter.print("    ");
            printWriter.println("Current mReceivers:");
            int size7 = processReceiverRecord.mCurReceivers.size();
            for (int i10 = 0; i10 < size7; i10++) {
                printWriter.print("    ");
                printWriter.print("  - ");
                printWriter.println(processReceiverRecord.mCurReceivers.valueAt(i10));
            }
        }
        if (processReceiverRecord.mReceivers.size() > 0) {
            printWriter.print("    ");
            printWriter.println("mReceivers:");
            int size8 = processReceiverRecord.mReceivers.size();
            for (int i11 = 0; i11 < size8; i11++) {
                printWriter.print("    ");
                printWriter.print("  - ");
                printWriter.println(processReceiverRecord.mReceivers.valueAt(i11));
            }
        }
        ProcessCachedOptimizerRecord processCachedOptimizerRecord = this.mOptRecord;
        processCachedOptimizerRecord.getClass();
        printWriter.print("    ");
        printWriter.print("lastCompactTime=");
        printWriter.print(processCachedOptimizerRecord.mLastCompactTime);
        printWriter.print(" lastCompactProfile=");
        printWriter.println(processCachedOptimizerRecord.mLastCompactProfile);
        printWriter.print("    ");
        printWriter.print("hasPendingCompaction=");
        printWriter.print(processCachedOptimizerRecord.mPendingCompact);
        printWriter.print("    ");
        printWriter.print("isFreezeExempt=");
        printWriter.print(processCachedOptimizerRecord.mFreezeExempt);
        printWriter.print(" isPendingFreeze=");
        printWriter.print(processCachedOptimizerRecord.mPendingFreeze);
        printWriter.print(" isFrozen=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter,
                "    ",
                "earliestFreezableTimeMs=",
                processCachedOptimizerRecord.mFrozen);
        TimeUtils.formatDuration(
                processCachedOptimizerRecord.mEarliestFreezableTimeMillis,
                uptimeMillis,
                printWriter);
        printWriter.println();
        this.mWindowProcessController.dump(printWriter, "    ");
        if (!"<empty>".equals(this.callStack)) {
            printWriter.print("    ");
            printWriter.print("removed from: ");
            printWriter.println(this.callStack);
        }
        if (DynamicHiddenApp.PICKED_ADJ_ENABLE) {
            printWriter.print("    ");
            printWriter.println("isMLException=");
            printWriter.println(false);
        }
    }

    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mPid);
        protoOutputStream.write(1138166333442L, this.processName);
        protoOutputStream.write(1120986464259L, this.info.uid);
        if (UserHandle.getAppId(this.info.uid) >= 10000) {
            protoOutputStream.write(1120986464260L, this.userId);
            protoOutputStream.write(1120986464261L, UserHandle.getAppId(this.info.uid));
        }
        if (this.uid != this.info.uid) {
            protoOutputStream.write(1120986464262L, UserHandle.getAppId(this.uid));
        }
        protoOutputStream.write(1133871366151L, this.mPersistent);
        if (i >= 0) {
            protoOutputStream.write(1120986464264L, i);
        }
        protoOutputStream.end(start);
    }

    public final BackgroundStartPrivileges getBackgroundStartPrivileges() {
        BackgroundStartPrivileges backgroundStartPrivileges;
        synchronized (this.mBackgroundStartPrivileges) {
            try {
                if (this.mBackgroundStartPrivilegesMerged == null) {
                    this.mBackgroundStartPrivilegesMerged = BackgroundStartPrivileges.NONE;
                    for (int size = this.mBackgroundStartPrivileges.size() - 1; size >= 0; size--) {
                        this.mBackgroundStartPrivilegesMerged =
                                this.mBackgroundStartPrivilegesMerged.merge(
                                        (BackgroundStartPrivileges)
                                                this.mBackgroundStartPrivileges.valueAt(size));
                    }
                }
                backgroundStartPrivileges = this.mBackgroundStartPrivilegesMerged;
            } catch (Throwable th) {
                throw th;
            }
        }
        return backgroundStartPrivileges;
    }

    public final ApplicationInfo getClientInfoForSdkSandbox() {
        if (!this.isSdkSandbox || this.sdkSandboxClientAppPackage == null) {
            throw new IllegalStateException(
                    "getClientInfoForSdkSandbox called for non-sandbox process");
        }
        return this.mService
                .getPackageManagerInternal()
                .getApplicationInfo(1000, this.userId, 0L, this.sdkSandboxClientAppPackage);
    }

    public List getLruProcessList() {
        return this.mService.mProcessList.mLruProcesses;
    }

    public final List getPackageListWithVersionCode() {
        PackageList packageList = this.mPkgList;
        synchronized (packageList) {
            try {
                int size = packageList.mPkgList.size();
                if (size == 0) {
                    return null;
                }
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < size; i++) {
                    arrayList.add(
                            new VersionedPackage(
                                    (String) packageList.mPkgList.keyAt(i),
                                    ((ProcessStats.ProcessStateHolder)
                                                    packageList.mPkgList.valueAt(i))
                                            .appVersion));
                }
                return arrayList;
            } finally {
            }
        }
    }

    public final int getPid() {
        return this.mPid;
    }

    public final int getProcessClassEnum() {
        if (this.mPid == ActivityManagerService.MY_PID) {
            return 3;
        }
        if (this.info == null) {
            return 0;
        }
        return (this.info.flags & 1) != 0 ? 2 : 1;
    }

    public final boolean hasActivitiesOrRecentTasks() {
        WindowProcessController windowProcessController = this.mWindowProcessController;
        return windowProcessController.mHasActivities || windowProcessController.mHasRecentTasks;
    }

    public final boolean isDebuggable() {
        if ((this.info.flags & 2) != 0) {
            return true;
        }
        if (!this.isSdkSandbox) {
            return false;
        }
        ApplicationInfo clientInfoForSdkSandbox = getClientInfoForSdkSandbox();
        return (clientInfoForSdkSandbox == null || (clientInfoForSdkSandbox.flags & 2) == 0)
                ? false
                : true;
    }

    public final boolean isFreezable() {
        if (this.mService.mOomAdjuster.mCachedAppOptimizer.useFreezer()) {
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = this.mOptRecord;
            if (!processCachedOptimizerRecord.mFreezeExempt
                    && !processCachedOptimizerRecord.mShouldNotFreeze
                    && this.mState.mCurAdj >= 850) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0059, code lost:

       return true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isInterestingToUserLocked() {
        /*
            r7 = this;
            com.android.server.wm.WindowProcessController r0 = r7.mWindowProcessController
            com.android.server.wm.ActivityTaskManagerService r1 = r0.mAtm
            com.android.server.wm.WindowManagerGlobalLock r1 = r1.mGlobalLock
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
            monitor-enter(r1)
            java.util.ArrayList r2 = r0.mActivities     // Catch: java.lang.Throwable -> L28
            int r2 = r2.size()     // Catch: java.lang.Throwable -> L28
            r3 = 0
            r4 = r3
        L12:
            r5 = 1
            if (r4 >= r2) goto L2d
            java.util.ArrayList r6 = r0.mActivities     // Catch: java.lang.Throwable -> L28
            java.lang.Object r6 = r6.get(r4)     // Catch: java.lang.Throwable -> L28
            com.android.server.wm.ActivityRecord r6 = (com.android.server.wm.ActivityRecord) r6     // Catch: java.lang.Throwable -> L28
            boolean r6 = r6.isInterestingToUserLocked()     // Catch: java.lang.Throwable -> L28
            if (r6 == 0) goto L2a
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L28
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            goto L59
        L28:
            r7 = move-exception
            goto L66
        L2a:
            int r4 = r4 + 1
            goto L12
        L2d:
            android.util.ArrayMap r2 = r0.mRemoteActivities     // Catch: java.lang.Throwable -> L28
            if (r2 != 0) goto L32
            goto L5d
        L32:
            int r2 = r2.size()     // Catch: java.lang.Throwable -> L28
            int r2 = r2 - r5
        L37:
            if (r2 < 0) goto L5d
            android.util.ArrayMap r4 = r0.mRemoteActivities     // Catch: java.lang.Throwable -> L28
            java.lang.Object r4 = r4.valueAt(r2)     // Catch: java.lang.Throwable -> L28
            int[] r4 = (int[]) r4     // Catch: java.lang.Throwable -> L28
            r4 = r4[r3]     // Catch: java.lang.Throwable -> L28
            r4 = r4 & r5
            if (r4 != 0) goto L47
            goto L5a
        L47:
            android.util.ArrayMap r4 = r0.mRemoteActivities     // Catch: java.lang.Throwable -> L28
            java.lang.Object r4 = r4.keyAt(r2)     // Catch: java.lang.Throwable -> L28
            com.android.server.wm.ActivityRecord r4 = (com.android.server.wm.ActivityRecord) r4     // Catch: java.lang.Throwable -> L28
            boolean r4 = r4.isInterestingToUserLocked()     // Catch: java.lang.Throwable -> L28
            if (r4 == 0) goto L5a
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L28
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
        L59:
            return r5
        L5a:
            int r2 = r2 + (-1)
            goto L37
        L5d:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L28
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            com.android.server.am.ProcessServiceRecord r7 = r7.mServices
            boolean r7 = r7.mHasForegroundServices
            return r7
        L66:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L28
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ProcessRecord.isInterestingToUserLocked():boolean");
    }

    public final boolean isThreadReady() {
        return (this.mThread == null || this.mPendingFinishAttach) ? false : true;
    }

    public final void killLocked(int i, int i2, String str, String str2, boolean z, boolean z2) {
        String str3;
        int i3 = this.mPid;
        if (i3 > 0 && Process.getThreadGroupLeader(i3) != this.mPid) {
            Slog.w("ActivityManager", "Not TGL " + this.mPid + ":" + Debug.getCallers(2));
            if (this.mTGLCallbackPosted) {
                return;
            }
            final int i4 = this.mPid;
            this.mTGLCallbackPosted = true;
            this.mService.mHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.server.am.ProcessRecord$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ProcessRecord processRecord = this;
                            int i5 = i4;
                            ActivityManagerService activityManagerService = processRecord.mService;
                            ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService) {
                                try {
                                    if (!processRecord.mKilled && processRecord.mPid == i5) {
                                        ActivityManagerService activityManagerService2 =
                                                processRecord.mService;
                                        activityManagerService2.getClass();
                                        activityManagerService2.appDiedLocked(
                                                processRecord,
                                                processRecord.mPid,
                                                processRecord.mThread,
                                                false,
                                                "TGL@");
                                    }
                                } catch (Throwable th) {
                                    ActivityManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                            ActivityManagerService.resetPriorityAfterLockedSection();
                        }
                    },
                    1000L);
            return;
        }
        if (this.mKilledByAm) {
            return;
        }
        Trace.traceBegin(64L, "kill");
        if (i != 6 || this.mErrorState.mAnrAnnotation == null) {
            str3 = str2;
        } else {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str2, ": ");
            m.append(this.mErrorState.mAnrAnnotation);
            str3 = m.toString();
        }
        if (this.mService != null && (z || this.info.uid == this.mService.mCurOomAdjUid)) {
            this.mService.reportUidInfoMessageLocked(
                    this.info.uid,
                    "Killing " + toShortString() + " (adj " + this.mState.mSetAdj + "): " + str);
        }
        ProcessCachedOptimizerRecord processCachedOptimizerRecord = this.mOptRecord;
        processCachedOptimizerRecord.mPendingFreeze = false;
        processCachedOptimizerRecord.mFrozen = false;
        if (this.mPid > 0) {
            this.mService.mProcessList.noteAppKill(this, i, i2, str3);
            Integer valueOf = Integer.valueOf(this.userId);
            Integer valueOf2 = Integer.valueOf(this.mPid);
            String str4 = this.processName;
            Integer valueOf3 = Integer.valueOf(this.mState.mSetAdj);
            long[] rss = Process.getRss(this.mPid);
            EventLog.writeEvent(
                    30023,
                    valueOf,
                    valueOf2,
                    str4,
                    valueOf3,
                    str,
                    Long.valueOf((rss == null || rss.length <= 0) ? 0L : rss[0]));
            KernelMemoryProxy$ReclaimerLog.write(
                    "B|killProcessQuiet comm=" + this.processName + "(" + this.mPid + ")", false);
            long j = this.mProfile.mLastPss;
            Process.killProcessQuiet(this.mPid);
            killProcessGroupIfNecessaryLocked(z2);
            KernelMemoryProxy$ReclaimerLog.write("E|killProcessQuiet pss=" + j, false);
        } else {
            this.mPendingStart = false;
        }
        if (!this.mPersistent) {
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    this.mKilled = true;
                    this.mKilledByAm = true;
                    this.mKillTime = SystemClock.uptimeMillis();
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
        Trace.traceEnd(64L);
    }

    public final void killLocked(int i, String str) {
        killLocked(i, 0, str, str, true, true);
    }

    public final void killProcessGroupIfNecessaryLocked(boolean z) {
        boolean z2;
        boolean z3 = true;
        if (this.mHostingRecord != null
                && (this.mHostingRecord.mHostingZygote == 1
                        || this.mHostingRecord.mHostingZygote == 2)) {
            synchronized (this) {
                try {
                    z2 = this.mProcessGroupCreated;
                    if (!z2) {
                        this.mSkipProcessGroupCreation = true;
                    }
                } finally {
                }
            }
            z3 = z2;
        }
        if (z3) {
            if (!z) {
                Process.sendSignalToProcessGroup(this.uid, this.mPid, OsConstants.SIGKILL);
            }
            ProcessList.killProcessGroup(this.uid, this.mPid);
        }
    }

    public final void makeActive(
            IApplicationThread iApplicationThread, final ProcessStatsService processStatsService) {
        final ProcessProfileRecord processProfileRecord = this.mProfile;
        if (processProfileRecord.mThread == null) {
            synchronized (processProfileRecord.mProfilerLock) {
                synchronized (processStatsService.mLock) {
                    final ProcessState processState = processProfileRecord.mBaseProcessTracker;
                    PackageList packageList = processProfileRecord.mApp.mPkgList;
                    if (processState != null) {
                        synchronized (packageList) {
                            processState.setState(
                                    -1,
                                    processStatsService.getMemFactorLocked(),
                                    SystemClock.uptimeMillis(),
                                    packageList.mPkgList);
                        }
                        processState.makeInactive();
                    }
                    ApplicationInfo applicationInfo = processProfileRecord.mApp.info;
                    ProcessRecord processRecord = processProfileRecord.mApp;
                    final int i =
                            Process.isIsolatedUid(processRecord.uid)
                                    ? processRecord.info.uid
                                    : processRecord.uid;
                    final ProcessState processStateLocked =
                            processStatsService.mProcessStats.getProcessStateLocked(
                                    applicationInfo.packageName,
                                    i,
                                    applicationInfo.longVersionCode,
                                    processProfileRecord.mApp.processName);
                    processProfileRecord.mBaseProcessTracker = processStateLocked;
                    processStateLocked.makeActive();
                    BiConsumer biConsumer =
                            new BiConsumer() { // from class:
                                               // com.android.server.am.ProcessProfileRecord$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ProcessProfileRecord processProfileRecord2 =
                                            ProcessProfileRecord.this;
                                    ProcessState processState2 = processState;
                                    ProcessStatsService processStatsService2 = processStatsService;
                                    int i2 = i;
                                    ProcessState processState3 = processStateLocked;
                                    String str = (String) obj;
                                    ProcessStats.ProcessStateHolder processStateHolder =
                                            (ProcessStats.ProcessStateHolder) obj2;
                                    processProfileRecord2.getClass();
                                    ProcessState processState4 = processStateHolder.state;
                                    if (processState4 != null && processState4 != processState2) {
                                        processState4.makeInactive();
                                    }
                                    long j = processProfileRecord2.mApp.info.longVersionCode;
                                    String str2 = processProfileRecord2.mApp.processName;
                                    ProcessStats.PackageState packageStateLocked =
                                            processStatsService2.mProcessStats
                                                    .getPackageStateLocked(str, i2, j);
                                    processStateHolder.pkg = packageStateLocked;
                                    ProcessState processStateLocked2 =
                                            processStatsService2.mProcessStats
                                                    .getProcessStateLocked(
                                                            packageStateLocked, str2);
                                    processStateHolder.state = processStateLocked2;
                                    if (processStateLocked2 != processState3) {
                                        processStateLocked2.makeActive();
                                    }
                                }
                            };
                    synchronized (packageList) {
                        try {
                            int size = packageList.mPkgList.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                biConsumer.accept(
                                        (String) packageList.mPkgList.keyAt(i2),
                                        (ProcessStats.ProcessStateHolder)
                                                packageList.mPkgList.valueAt(i2));
                            }
                        } finally {
                        }
                    }
                    processProfileRecord.mThread = iApplicationThread;
                }
            }
        } else {
            synchronized (processProfileRecord.mProfilerLock) {
                processProfileRecord.mThread = iApplicationThread;
            }
        }
        this.mThread = iApplicationThread;
        if (this.mPid == Process.myPid()) {
            this.mOnewayThread =
                    new SameProcessApplicationThread(iApplicationThread, FgThread.getHandler());
        } else {
            this.mOnewayThread = iApplicationThread;
        }
        this.mWindowProcessController.setThread(iApplicationThread);
        if (this.mWindowProcessController.mUseFifoUiScheduling) {
            this.mService.mSpecifiedFifoProcesses.add(this);
        }
    }

    public final boolean onCleanupApplicationRecordLSP(
            ProcessStatsService processStatsService, boolean z, boolean z2) {
        ActivityManagerService activityManagerService;
        ProcessRecord processRecord;
        ProcessErrorStateRecord processErrorStateRecord = this.mErrorState;
        ErrorDialogController errorDialogController = processErrorStateRecord.mDialogController;
        List list = errorDialogController.mCrashDialogs;
        if (list != null) {
            errorDialogController.scheduleForAllDialogs(
                    list, new ErrorDialogController$$ExternalSyntheticLambda0(0));
            errorDialogController.mCrashDialogs = null;
        }
        errorDialogController.clearAnrDialogs();
        List list2 = errorDialogController.mViolationDialogs;
        if (list2 != null) {
            errorDialogController.scheduleForAllDialogs(
                    list2, new ErrorDialogController$$ExternalSyntheticLambda0(0));
            errorDialogController.mViolationDialogs = null;
        }
        AppWaitingForDebuggerDialog appWaitingForDebuggerDialog = errorDialogController.mWaitDialog;
        if (appWaitingForDebuggerDialog != null) {
            errorDialogController.mService.mUiHandler.post(
                    new ErrorDialogController$$ExternalSyntheticLambda2(
                            2, appWaitingForDebuggerDialog));
            errorDialogController.mWaitDialog = null;
        }
        processErrorStateRecord.mCrashing = false;
        processErrorStateRecord.mApp.mWindowProcessController.mCrashing = false;
        processErrorStateRecord.setNotResponding(false);
        resetPackageList(processStatsService);
        if (z2) {
            unlinkDeathRecipient();
        }
        this.mThread = null;
        this.mOnewayThread = null;
        this.mWindowProcessController.setThread(null);
        if (this.mWindowProcessController.mUseFifoUiScheduling) {
            this.mService.mSpecifiedFifoProcesses.remove(this);
        }
        ProcessProfileRecord processProfileRecord = this.mProfile;
        synchronized (processProfileRecord.mProfilerLock) {
            synchronized (processStatsService.mLock) {
                final ProcessState processState = processProfileRecord.mBaseProcessTracker;
                if (processState != null) {
                    PackageList packageList = processProfileRecord.mApp.mPkgList;
                    synchronized (packageList) {
                        processState.setState(
                                -1,
                                processStatsService.getMemFactorLocked(),
                                SystemClock.uptimeMillis(),
                                packageList.mPkgList);
                    }
                    processState.makeInactive();
                    processProfileRecord.mBaseProcessTracker = null;
                    packageList.forEachPackageProcessStats(
                            new Consumer() { // from class:
                                             // com.android.server.am.ProcessProfileRecord$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    ProcessState processState2 = processState;
                                    ProcessStats.ProcessStateHolder processStateHolder =
                                            (ProcessStats.ProcessStateHolder) obj;
                                    ProcessState processState3 = processStateHolder.state;
                                    if (processState3 != null && processState3 != processState2) {
                                        processState3.makeInactive();
                                    }
                                    processStateHolder.pkg = null;
                                    processStateHolder.state = null;
                                }
                            });
                }
                processProfileRecord.mThread = null;
            }
        }
        processProfileRecord.mCurrentHostingComponentTypes.set(0);
        processProfileRecord.mHistoricalHostingComponentTypes.set(0);
        this.mWaitingToKill = null;
        this.mClearedWaitingToKill = false;
        ProcessStateRecord processStateRecord = this.mState;
        processStateRecord.mHasForegroundActivities = false;
        processStateRecord.mHasShownUi = false;
        processStateRecord.mForcingToImportant = null;
        processStateRecord.mVerifiedAdj = -10000;
        processStateRecord.mSetAdj = -10000;
        processStateRecord.mCurAdj = -10000;
        processStateRecord.mSetRawAdj = -10000;
        processStateRecord.mCurRawAdj = -10000;
        processStateRecord.mSetCapability = 0;
        processStateRecord.mCurCapability = 0;
        processStateRecord.mSetSchedGroup = 0;
        processStateRecord.mCurSchedGroup = 0;
        processStateRecord.mSetProcState = 20;
        processStateRecord.mCurRawProcState = 20;
        processStateRecord.mCurProcState = 20;
        int i = 0;
        while (true) {
            int[] iArr = processStateRecord.mCachedCompatChanges;
            if (i >= iArr.length) {
                break;
            }
            iArr[i] = -1;
            i++;
        }
        ProcessServiceRecord processServiceRecord = this.mServices;
        processServiceRecord.mTreatLikeActivity = false;
        processServiceRecord.mHasAboveClient = false;
        processServiceRecord.mHasClientActivities = false;
        processServiceRecord.mApp.mWindowProcessController.mHasClientActivities = false;
        ProcessReceiverRecord processReceiverRecord = this.mReceivers;
        for (int size = processReceiverRecord.mReceivers.size() - 1; size >= 0; size--) {
            processReceiverRecord.mService.removeReceiverLocked(
                    (ReceiverList) processReceiverRecord.mReceivers.valueAt(size));
        }
        processReceiverRecord.mReceivers.clear();
        this.mService.mOomAdjuster.onProcessEndLocked(this);
        ProcessProviderRecord processProviderRecord = this.mProviders;
        int size2 = processProviderRecord.mPubProviders.size() - 1;
        boolean z3 = false;
        while (true) {
            activityManagerService = processProviderRecord.mService;
            processRecord = processProviderRecord.mApp;
            if (size2 < 0) {
                break;
            }
            ContentProviderRecord contentProviderRecord =
                    (ContentProviderRecord) processProviderRecord.mPubProviders.valueAt(size2);
            if (contentProviderRecord.proc == processRecord) {
                boolean z4 = processRecord.mErrorState.mBad || !z;
                boolean removeDyingProviderLocked =
                        activityManagerService.mCpHelper.removeDyingProviderLocked(
                                processRecord, contentProviderRecord, z4);
                if (!z4
                        && removeDyingProviderLocked
                        && (!contentProviderRecord.connections.isEmpty()
                                || contentProviderRecord.hasExternalProcessHandles())) {
                    z3 = true;
                }
                contentProviderRecord.provider = null;
                contentProviderRecord.setProcess(null);
            }
            size2--;
        }
        processProviderRecord.mPubProviders.clear();
        if (activityManagerService.mCpHelper.cleanupAppInLaunchingProvidersLocked(
                processRecord, false)) {
            activityManagerService.mProcessList.noteProcessDiedLocked(processRecord);
            z3 = true;
        }
        if (!processProviderRecord.mConProviders.isEmpty()) {
            for (int size3 = processProviderRecord.mConProviders.size() - 1; size3 >= 0; size3--) {
                ContentProviderConnection contentProviderConnection =
                        (ContentProviderConnection) processProviderRecord.mConProviders.get(size3);
                contentProviderConnection.provider.connections.remove(contentProviderConnection);
                int i2 = processRecord.uid;
                String str = processRecord.processName;
                ContentProviderRecord contentProviderRecord2 = contentProviderConnection.provider;
                int i3 = contentProviderRecord2.uid;
                long j = contentProviderRecord2.appInfo.longVersionCode;
                ComponentName componentName = contentProviderRecord2.name;
                String str2 = contentProviderRecord2.info.processName;
                processProviderRecord.mService.stopAssociationLocked(componentName, str, i2, i3);
            }
            processProviderRecord.mConProviders.clear();
        }
        return z3;
    }

    public final void removeBackgroundStartPrivileges(Binder binder) {
        Objects.requireNonNull(binder, "entity");
        WindowProcessController windowProcessController = this.mWindowProcessController;
        windowProcessController.getClass();
        BackgroundLaunchProcessController backgroundLaunchProcessController =
                windowProcessController.mBgLaunchController;
        backgroundLaunchProcessController.getClass();
        synchronized (backgroundLaunchProcessController) {
            try {
                ArrayMap arrayMap = backgroundLaunchProcessController.mBackgroundStartPrivileges;
                if (arrayMap != null) {
                    arrayMap.remove(binder);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        setBackgroundStartPrivileges(binder, null);
    }

    public final void resetPackageList(ProcessStatsService processStatsService) {
        synchronized (processStatsService.mLock) {
            final ProcessState processState = this.mProfile.mBaseProcessTracker;
            synchronized (this.mPkgList) {
                try {
                    int size = this.mPkgList.size();
                    if (processState != null) {
                        processState.setState(
                                -1,
                                processStatsService.getMemFactorLocked(),
                                SystemClock.uptimeMillis(),
                                this.mPkgList.mPkgList);
                        if (size != 1) {
                            this.mPkgList.forEachPackageProcessStats(
                                    new Consumer() { // from class:
                                                     // com.android.server.am.ProcessRecord$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            ProcessState processState2 = processState;
                                            ProcessState processState3 =
                                                    ((ProcessStats.ProcessStateHolder) obj).state;
                                            if (processState3 == null
                                                    || processState3 == processState2) {
                                                return;
                                            }
                                            processState3.makeInactive();
                                        }
                                    });
                            this.mPkgList.clear();
                            ProcessStats.ProcessStateHolder processStateHolder =
                                    new ProcessStats.ProcessStateHolder(this.info.longVersionCode);
                            String str = this.info.packageName;
                            int i = this.info.uid;
                            long j = this.info.longVersionCode;
                            String str2 = this.processName;
                            ProcessStats.PackageState packageStateLocked =
                                    processStatsService.mProcessStats.getPackageStateLocked(
                                            str, i, j);
                            processStateHolder.pkg = packageStateLocked;
                            processStateHolder.state =
                                    processStatsService.mProcessStats.getProcessStateLocked(
                                            packageStateLocked, str2);
                            this.mPkgList.put(this.info.packageName, processStateHolder);
                            ProcessState processState2 = processStateHolder.state;
                            if (processState2 != processState) {
                                processState2.makeActive();
                            }
                        }
                    } else if (size != 1) {
                        this.mPkgList.clear();
                        this.mPkgList.put(
                                this.info.packageName,
                                new ProcessStats.ProcessStateHolder(this.info.longVersionCode));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void setActiveInstrumentation(ActiveInstrumentation activeInstrumentation) {
        this.mInstr = activeInstrumentation;
        boolean z = activeInstrumentation != null;
        WindowProcessController windowProcessController = this.mWindowProcessController;
        int i = z ? activeInstrumentation.mSourceUid : -1;
        boolean z2 = z && activeInstrumentation.mHasBackgroundActivityStartsPermission;
        windowProcessController.getClass();
        Preconditions.checkArgument(z || i == -1);
        windowProcessController.mInstrumenting = z;
        windowProcessController.mInstrumentationSourceUid = i;
        windowProcessController.mInstrumentingWithBackgroundActivityStartPrivileges = z2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0018, code lost:

       if (r4 != ((android.app.BackgroundStartPrivileges) r2.mBackgroundStartPrivileges.put(r3, r4))) goto L12;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setBackgroundStartPrivileges(
            android.os.Binder r3, android.app.BackgroundStartPrivileges r4) {
        /*
            r2 = this;
            android.util.ArrayMap r0 = r2.mBackgroundStartPrivileges
            monitor-enter(r0)
            if (r4 != 0) goto L10
            android.util.ArrayMap r4 = r2.mBackgroundStartPrivileges     // Catch: java.lang.Throwable -> Le
            java.lang.Object r3 = r4.remove(r3)     // Catch: java.lang.Throwable -> Le
            if (r3 == 0) goto L1d
            goto L1a
        Le:
            r2 = move-exception
            goto L1f
        L10:
            android.util.ArrayMap r1 = r2.mBackgroundStartPrivileges     // Catch: java.lang.Throwable -> Le
            java.lang.Object r3 = r1.put(r3, r4)     // Catch: java.lang.Throwable -> Le
            android.app.BackgroundStartPrivileges r3 = (android.app.BackgroundStartPrivileges) r3     // Catch: java.lang.Throwable -> Le
            if (r4 == r3) goto L1d
        L1a:
            r3 = 0
            r2.mBackgroundStartPrivilegesMerged = r3     // Catch: java.lang.Throwable -> Le
        L1d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Le
            return
        L1f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Le
            throw r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ProcessRecord.setBackgroundStartPrivileges(android.os.Binder,"
                    + " android.app.BackgroundStartPrivileges):void");
    }

    public final void setPendingUiClean(boolean z) {
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                ProcessProfileRecord processProfileRecord = this.mProfile;
                processProfileRecord.mPendingUiClean = z;
                processProfileRecord.mApp.mWindowProcessController.mPendingUiClean = z;
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    public final void setPid(int i) {
        int i2 = this.mPid;
        if (i != i2 && i2 != 0) {
            this.mWasForceStopped = false;
        }
        this.mPid = i;
        this.mWindowProcessController.mPid = i;
        this.mShortStringName = null;
        this.mStringName = null;
        synchronized (this.mProfile.mProfilerLock) {
            this.mProfile.mPid = i;
        }
    }

    public final void setRunningRemoteAnimation(boolean z) {
        if (this.mPid == Process.myPid()) {
            Slog.wtf("ActivityManager", "system can't run remote animation");
            return;
        }
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ProcessStateRecord processStateRecord = this.mState;
                if (processStateRecord.mRunningRemoteAnimation != z) {
                    processStateRecord.mRunningRemoteAnimation = z;
                    processStateRecord.mService.updateOomAdjLocked(9, processStateRecord.mApp);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public final String toShortString() {
        String str = this.mShortStringName;
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder(128);
        toShortString(sb);
        String sb2 = sb.toString();
        this.mShortStringName = sb2;
        return sb2;
    }

    public final void toShortString(StringBuilder sb) {
        sb.append(this.mPid);
        sb.append(':');
        sb.append(this.processName);
        sb.append('/');
        if (this.info.uid < 10000) {
            sb.append(this.uid);
            return;
        }
        sb.append('u');
        sb.append(this.userId);
        int appId = UserHandle.getAppId(this.info.uid);
        if (appId >= 10000) {
            sb.append('a');
            sb.append(appId - 10000);
        } else {
            sb.append('s');
            sb.append(appId);
        }
        if (this.uid != this.info.uid) {
            sb.append('i');
            sb.append(UserHandle.getAppId(this.uid) - 99000);
        }
    }

    public final String toString() {
        String str = this.mStringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "ProcessRecord{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(' ');
        toShortString(m);
        m.append('}');
        String sb = m.toString();
        this.mStringName = sb;
        return sb;
    }

    public final void unlinkDeathRecipient() {
        IApplicationThread iApplicationThread;
        if (this.mDeathRecipient != null && (iApplicationThread = this.mThread) != null) {
            iApplicationThread.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
        }
        this.mDeathRecipient = null;
    }

    public final void updateProcessInfo(boolean z, boolean z2, boolean z3) {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            if (z) {
                try {
                    this.mService.mServices.updateServiceConnectionActivitiesLocked(this.mServices);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            if (this.mThread == null) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            }
            this.mService.mProcessList.updateLruProcessLocked(this, null, z2);
            if (z3) {
                this.mService.updateOomAdjLocked(1, this);
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final boolean useFifoUiScheduling() {
        ActivityManagerService activityManagerService = this.mService;
        return activityManagerService.mUseFifoUiScheduling
                || (activityManagerService.mAllowSpecifiedFifoScheduling
                        && this.mWindowProcessController.mUseFifoUiScheduling);
    }
}
