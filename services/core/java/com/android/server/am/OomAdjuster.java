package com.android.server.am;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.net.INetd;
import android.net.NetworkPolicyManager;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Message;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.p005os.IInstalld;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.chimera.GPUMemoryReclaimer;
import com.android.server.chimera.PerProcessNandswap;
import com.android.server.chimera.umr.DamonReclaimer;
import com.android.server.am.ActivityManagerService;
import com.android.server.wm.ActivityServiceConnectionsHolder;
import com.android.server.wm.WindowProcessController;
import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class OomAdjuster {
    public String VENDING_PKG;
    public ActiveUids mActiveUids;
    public int mAdjSeq;
    public ICustomFrequencyManager mCFMS;
    public CacheOomRanker mCacheOomRanker;
    public CachedAppOptimizer mCachedAppOptimizer;
    public ActivityManagerConstants mConstants;
    public DynamicHiddenApp mDynamicHiddenApp;
    public GPUMemoryReclaimer mGPUMemoryReclaimer;
    public double mLastFreeSwapPercent;
    public PowerManagerInternal mLocalPowerManager;
    public int mNewNumAServiceProcs;
    public int mNewNumServiceProcs;
    public long mNextNoKillDebugMessageTime;
    public int mNumCachedHiddenProcs;
    public int mNumCachedProcessCount;
    public int mNumCachedSlotCount;
    public int mNumEmptyProcessCount;
    public int mNumEmptySlotCount;
    public int mNumNonCachedProcs;
    public int mNumServiceProcs;
    public final int mNumSlots;
    public boolean mOomAdjUpdateOngoing;
    public boolean mPendingFullOomAdjUpdate;
    public final ArraySet mPendingProcessSet;
    public PerProcessNandswap mPerProcessNandswap;
    public final ActivityManagerGlobalLock mProcLock;
    public final Handler mProcessGroupHandler;
    public final ProcessList mProcessList;
    public final ArraySet mProcessesInCycle;
    public final ActivityManagerService mService;
    public final ArrayList mTmpBecameIdle;
    public final ComputeOomAdjWindowCallback mTmpComputeOomAdjWindowCallback;
    public final long[] mTmpLong;
    public final ArrayList mTmpProcessList;
    public final ArraySet mTmpProcessSet;
    public final ArrayDeque mTmpQueue;
    public final int[] mTmpSchedGroup;
    public final ActiveUids mTmpUidRecords;

    public static final String oomAdjReasonToString(int i) {
        switch (i) {
            case 0:
                return "updateOomAdj_meh";
            case 1:
                return "updateOomAdj_activityChange";
            case 2:
                return "updateOomAdj_finishReceiver";
            case 3:
                return "updateOomAdj_startReceiver";
            case 4:
                return "updateOomAdj_bindService";
            case 5:
                return "updateOomAdj_unbindService";
            case 6:
                return "updateOomAdj_startService";
            case 7:
                return "updateOomAdj_getProvider";
            case 8:
                return "updateOomAdj_removeProvider";
            case 9:
                return "updateOomAdj_uiVisibility";
            case 10:
                return "updateOomAdj_allowlistChange";
            case 11:
                return "updateOomAdj_processBegin";
            case 12:
                return "updateOomAdj_processEnd";
            case 13:
                return "updateOomAdj_shortFgs";
            case 14:
                return "updateOomAdj_systemInit";
            case 15:
                return "updateOomAdj_backup";
            case 16:
                return "updateOomAdj_shell";
            case 17:
                return "updateOomAdj_removeTask";
            case 18:
                return "updateOomAdj_uidIdle";
            case 19:
                return "updateOomAdj_stopService";
            case 20:
                return "updateOomAdj_executingService";
            case 21:
                return "updateOomAdj_restrictionChange";
            case 22:
                return "updateOomAdj_componentDisabled";
            case 23:
                return "updateOomAdj_slowdown";
            case 24:
                return "updateOomAdj_fgsfilter";
            default:
                return "_unknown";
        }
    }

    public boolean isChangeEnabled(int i, ApplicationInfo applicationInfo, boolean z) {
        PlatformCompatCache.getInstance();
        return PlatformCompatCache.isChangeEnabled(i, applicationInfo, z);
    }

    public OomAdjuster(ActivityManagerService activityManagerService, ProcessList processList, ActiveUids activeUids) {
        this(activityManagerService, processList, activeUids, createAdjusterThread());
    }

    public static ServiceThread createAdjusterThread() {
        final ServiceThread serviceThread = new ServiceThread("OomAdjuster", -10, false);
        serviceThread.start();
        serviceThread.getThreadHandler().post(new Runnable() { // from class: com.android.server.am.OomAdjuster$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                OomAdjuster.lambda$createAdjusterThread$0(ServiceThread.this);
            }
        });
        return serviceThread;
    }

    public static /* synthetic */ void lambda$createAdjusterThread$0(ServiceThread serviceThread) {
        Process.setThreadGroupAndCpuset(serviceThread.getThreadId(), CoreRune.SYSPERF_BOOST_OPT ? 10 : 5);
    }

    public OomAdjuster(ActivityManagerService activityManagerService, ProcessList processList, ActiveUids activeUids, ServiceThread serviceThread) {
        this.mCFMS = null;
        this.mTmpLong = new long[3];
        this.mAdjSeq = 0;
        this.mNumServiceProcs = 0;
        this.mNewNumAServiceProcs = 0;
        this.mNewNumServiceProcs = 0;
        this.mNumNonCachedProcs = 0;
        this.mNumCachedHiddenProcs = 0;
        this.mTmpSchedGroup = new int[1];
        this.mDynamicHiddenApp = null;
        this.mNumCachedProcessCount = 0;
        this.mNumEmptyProcessCount = 0;
        this.mNumCachedSlotCount = 0;
        this.mNumEmptySlotCount = 0;
        this.mTmpProcessList = new ArrayList();
        this.mTmpBecameIdle = new ArrayList();
        this.mTmpProcessSet = new ArraySet();
        this.mPendingProcessSet = new ArraySet();
        this.mProcessesInCycle = new ArraySet();
        this.mOomAdjUpdateOngoing = false;
        this.mPendingFullOomAdjUpdate = false;
        this.VENDING_PKG = "com.android.vending";
        this.mLastFreeSwapPercent = 1.0d;
        this.mTmpComputeOomAdjWindowCallback = new ComputeOomAdjWindowCallback();
        this.mService = activityManagerService;
        this.mProcessList = processList;
        this.mProcLock = activityManagerService.mProcLock;
        this.mActiveUids = activeUids;
        this.mLocalPowerManager = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        this.mConstants = activityManagerService.mConstants;
        this.mCachedAppOptimizer = new CachedAppOptimizer(activityManagerService);
        this.mCacheOomRanker = new CacheOomRanker(activityManagerService);
        this.mGPUMemoryReclaimer = GPUMemoryReclaimer.getInstance();
        this.mPerProcessNandswap = PerProcessNandswap.getInstance();
        this.mProcessGroupHandler = new Handler(serviceThread.getLooper(), new Handler.Callback() { // from class: com.android.server.am.OomAdjuster$$ExternalSyntheticLambda2
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean lambda$new$1;
                lambda$new$1 = OomAdjuster.lambda$new$1(message);
                return lambda$new$1;
            }
        });
        this.mTmpUidRecords = new ActiveUids(activityManagerService, false);
        this.mTmpQueue = new ArrayDeque(this.mConstants.CUR_MAX_CACHED_PROCESSES << 1);
        this.mNumSlots = 10;
        DynamicHiddenApp dynamicHiddenApp = DynamicHiddenApp.getInstance();
        this.mDynamicHiddenApp = dynamicHiddenApp;
        dynamicHiddenApp.initDynamicHiddenApp(activityManagerService, processList, this.mConstants);
    }

    public static /* synthetic */ boolean lambda$new$1(Message message) {
        int i = message.arg1;
        int i2 = message.arg2;
        if (i == ActivityManagerService.MY_PID) {
            return true;
        }
        if (Trace.isTagEnabled(64L)) {
            Trace.traceBegin(64L, "setProcessGroup " + message.obj + " to " + i2);
        }
        try {
            Process.setProcessGroup(i, i2);
        } catch (Exception unused) {
        } catch (Throwable th) {
            Trace.traceEnd(64L);
            throw th;
        }
        Trace.traceEnd(64L);
        return true;
    }

    public void initSettings() {
        this.mCachedAppOptimizer.init();
        this.mCacheOomRanker.init(ActivityThread.currentApplication().getMainExecutor());
        if (this.mService.mConstants.KEEP_WARMING_SERVICES.size() > 0) {
            this.mService.mContext.registerReceiverForAllUsers(new BroadcastReceiver() { // from class: com.android.server.am.OomAdjuster.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    ActivityManagerService activityManagerService = OomAdjuster.this.mService;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            OomAdjuster.this.handleUserSwitchedLocked();
                        } catch (Throwable th) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }, new IntentFilter("android.intent.action.USER_SWITCHED"), null, this.mService.mHandler);
        }
    }

    public void handleUserSwitchedLocked() {
        this.mProcessList.forEachLruProcessesLOSP(false, new Consumer() { // from class: com.android.server.am.OomAdjuster$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                OomAdjuster.this.updateKeepWarmIfNecessaryForProcessLocked((ProcessRecord) obj);
            }
        });
    }

    public final void updateKeepWarmIfNecessaryForProcessLocked(ProcessRecord processRecord) {
        boolean z;
        ArraySet arraySet = this.mService.mConstants.KEEP_WARMING_SERVICES;
        PackageList pkgList = processRecord.getPkgList();
        int size = arraySet.size() - 1;
        while (true) {
            if (size < 0) {
                z = false;
                break;
            } else {
                if (pkgList.containsKey(((ComponentName) arraySet.valueAt(size)).getPackageName())) {
                    z = true;
                    break;
                }
                size--;
            }
        }
        if (z) {
            ProcessServiceRecord processServiceRecord = processRecord.mServices;
            for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
                processServiceRecord.getRunningServiceAt(numberOfRunningServices).updateKeepWarmLocked();
            }
        }
    }

    public final boolean performUpdateOomAdjLSP(ProcessRecord processRecord, int i, ProcessRecord processRecord2, long j, int i2) {
        if (processRecord.getThread() == null) {
            return false;
        }
        processRecord.mState.resetCachedInfo();
        processRecord.mState.setCurBoundByNonBgRestrictedApp(false);
        UidRecord uidRecord = processRecord.getUidRecord();
        if (uidRecord != null) {
            uidRecord.reset();
        }
        this.mPendingProcessSet.remove(processRecord);
        this.mProcessesInCycle.clear();
        computeOomAdjLSP(processRecord, i, processRecord2, false, j, false, true);
        if (!this.mProcessesInCycle.isEmpty()) {
            for (int size = this.mProcessesInCycle.size() - 1; size >= 0; size--) {
                ((ProcessRecord) this.mProcessesInCycle.valueAt(size)).mState.setCompletedAdjSeq(this.mAdjSeq - 1);
            }
            return true;
        }
        if (uidRecord != null) {
            uidRecord.forEachProcess(new Consumer() { // from class: com.android.server.am.OomAdjuster$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    OomAdjuster.this.updateAppUidRecIfNecessaryLSP((ProcessRecord) obj);
                }
            });
            if (uidRecord.getCurProcState() != 20 && (uidRecord.getSetProcState() != uidRecord.getCurProcState() || uidRecord.getSetCapability() != uidRecord.getCurCapability() || uidRecord.isSetAllowListed() != uidRecord.isCurAllowListed())) {
                ActiveUids activeUids = this.mTmpUidRecords;
                activeUids.clear();
                activeUids.put(uidRecord.getUid(), uidRecord);
                updateUidsLSP(activeUids, SystemClock.elapsedRealtime());
            }
        }
        return applyOomAdjLSP(processRecord, false, j, SystemClock.elapsedRealtime(), i2);
    }

    public void updateOomAdjLocked(int i) {
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                updateOomAdjLSP(i);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    public final void updateOomAdjLSP(int i) {
        if (checkAndEnqueueOomAdjTargetLocked(null)) {
            return;
        }
        try {
            this.mOomAdjUpdateOngoing = true;
            performUpdateOomAdjLSP(i);
        } finally {
            this.mOomAdjUpdateOngoing = false;
            updateOomAdjPendingTargetsLocked(i);
        }
    }

    public final void performUpdateOomAdjLSP(int i) {
        ProcessRecord topApp = this.mService.getTopApp();
        this.mPendingProcessSet.clear();
        AppProfiler appProfiler = this.mService.mAppProfiler;
        appProfiler.mHasHomeProcess = false;
        appProfiler.mHasPreviousProcess = false;
        updateOomAdjInnerLSP(i, topApp, null, null, true, true);
    }

    public boolean updateOomAdjLocked(ProcessRecord processRecord, int i) {
        boolean updateOomAdjLSP;
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                updateOomAdjLSP = updateOomAdjLSP(processRecord, i);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        return updateOomAdjLSP;
    }

    public final boolean updateOomAdjLSP(ProcessRecord processRecord, int i) {
        if (processRecord == null || !this.mConstants.OOMADJ_UPDATE_QUICK) {
            updateOomAdjLSP(i);
            return true;
        }
        if (checkAndEnqueueOomAdjTargetLocked(processRecord)) {
            return true;
        }
        try {
            this.mOomAdjUpdateOngoing = true;
            return performUpdateOomAdjLSP(processRecord, i);
        } finally {
            this.mOomAdjUpdateOngoing = false;
            updateOomAdjPendingTargetsLocked(i);
        }
    }

    public final boolean performUpdateOomAdjLSP(ProcessRecord processRecord, int i) {
        ProcessRecord topApp = this.mService.getTopApp();
        Trace.traceBegin(64L, oomAdjReasonToString(i));
        this.mService.mOomAdjProfiler.oomAdjStarted();
        this.mAdjSeq++;
        ProcessStateRecord processStateRecord = processRecord.mState;
        boolean isCached = processStateRecord.isCached();
        int curRawAdj = processStateRecord.getCurRawAdj();
        int i2 = curRawAdj >= 900 ? curRawAdj : 1001;
        boolean isProcStateBackground = ActivityManager.isProcStateBackground(processStateRecord.getSetProcState());
        int setCapability = processStateRecord.getSetCapability();
        processStateRecord.setContainsCycle(false);
        processStateRecord.setProcStateChanged(false);
        processStateRecord.resetCachedInfo();
        processStateRecord.setCurBoundByNonBgRestrictedApp(false);
        this.mPendingProcessSet.remove(processRecord);
        processRecord.mOptRecord.setLastOomAdjChangeReason(i);
        boolean performUpdateOomAdjLSP = performUpdateOomAdjLSP(processRecord, i2, topApp, SystemClock.uptimeMillis(), i);
        if (!performUpdateOomAdjLSP || (isCached == processStateRecord.isCached() && curRawAdj != -10000 && this.mProcessesInCycle.isEmpty() && setCapability == processStateRecord.getCurCapability() && isProcStateBackground == ActivityManager.isProcStateBackground(processStateRecord.getSetProcState()))) {
            this.mProcessesInCycle.clear();
            this.mService.mOomAdjProfiler.oomAdjEnded();
            Trace.traceEnd(64L);
            return performUpdateOomAdjLSP;
        }
        ArrayList arrayList = this.mTmpProcessList;
        ActiveUids activeUids = this.mTmpUidRecords;
        this.mPendingProcessSet.add(processRecord);
        for (int size = this.mProcessesInCycle.size() - 1; size >= 0; size--) {
            this.mPendingProcessSet.add((ProcessRecord) this.mProcessesInCycle.valueAt(size));
        }
        this.mProcessesInCycle.clear();
        boolean collectReachableProcessesLocked = collectReachableProcessesLocked(this.mPendingProcessSet, arrayList, activeUids);
        this.mPendingProcessSet.clear();
        if (!collectReachableProcessesLocked) {
            processStateRecord.setReachable(false);
            arrayList.remove(processRecord);
        }
        if (arrayList.size() > 0) {
            this.mAdjSeq--;
            updateOomAdjInnerLSP(i, topApp, arrayList, activeUids, collectReachableProcessesLocked, false);
        } else if (processStateRecord.getCurRawAdj() == 1001) {
            arrayList.add(processRecord);
            assignCachedAdjIfNecessary(arrayList);
            applyOomAdjLSP(processRecord, false, SystemClock.uptimeMillis(), SystemClock.elapsedRealtime(), i);
        }
        this.mTmpProcessList.clear();
        this.mService.mOomAdjProfiler.oomAdjEnded();
        Trace.traceEnd(64L);
        return true;
    }

    public final boolean collectReachableProcessesLocked(ArraySet arraySet, ArrayList arrayList, ActiveUids activeUids) {
        ArrayDeque arrayDeque = this.mTmpQueue;
        arrayDeque.clear();
        arrayList.clear();
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            ProcessRecord processRecord = (ProcessRecord) arraySet.valueAt(i);
            processRecord.mState.setReachable(true);
            arrayDeque.offer(processRecord);
        }
        activeUids.clear();
        boolean z = false;
        for (ProcessRecord processRecord2 = (ProcessRecord) arrayDeque.poll(); processRecord2 != null; processRecord2 = (ProcessRecord) arrayDeque.poll()) {
            arrayList.add(processRecord2);
            UidRecord uidRecord = processRecord2.getUidRecord();
            if (uidRecord != null) {
                activeUids.put(uidRecord.getUid(), uidRecord);
            }
            ProcessServiceRecord processServiceRecord = processRecord2.mServices;
            for (int numberOfConnections = processServiceRecord.numberOfConnections() - 1; numberOfConnections >= 0; numberOfConnections--) {
                ConnectionRecord connectionAt = processServiceRecord.getConnectionAt(numberOfConnections);
                ProcessRecord processRecord3 = connectionAt.hasFlag(2) ? connectionAt.binding.service.isolationHostProc : connectionAt.binding.service.app;
                if (processRecord3 != null && processRecord3 != processRecord2 && (processRecord3.mState.getMaxAdj() < -900 || processRecord3.mState.getMaxAdj() >= 0)) {
                    z |= processRecord3.mState.isReachable();
                    if (!processRecord3.mState.isReachable() && (!connectionAt.hasFlag(32) || !connectionAt.notHasFlag(134217856))) {
                        arrayDeque.offer(processRecord3);
                        processRecord3.mState.setReachable(true);
                    }
                }
            }
            ProcessProviderRecord processProviderRecord = processRecord2.mProviders;
            for (int numberOfProviderConnections = processProviderRecord.numberOfProviderConnections() - 1; numberOfProviderConnections >= 0; numberOfProviderConnections--) {
                ProcessRecord processRecord4 = processProviderRecord.getProviderConnectionAt(numberOfProviderConnections).provider.proc;
                if (processRecord4 != null && processRecord4 != processRecord2 && (processRecord4.mState.getMaxAdj() < -900 || processRecord4.mState.getMaxAdj() >= 0)) {
                    z |= processRecord4.mState.isReachable();
                    if (!processRecord4.mState.isReachable()) {
                        arrayDeque.offer(processRecord4);
                        processRecord4.mState.setReachable(true);
                    }
                }
            }
            List sdkSandboxProcessesForAppLocked = this.mProcessList.getSdkSandboxProcessesForAppLocked(processRecord2.uid);
            for (int size2 = (sdkSandboxProcessesForAppLocked != null ? sdkSandboxProcessesForAppLocked.size() : 0) - 1; size2 >= 0; size2--) {
                ProcessRecord processRecord5 = (ProcessRecord) sdkSandboxProcessesForAppLocked.get(size2);
                z |= processRecord5.mState.isReachable();
                if (!processRecord5.mState.isReachable()) {
                    arrayDeque.offer(processRecord5);
                    processRecord5.mState.setReachable(true);
                }
            }
            if (processRecord2.isSdkSandbox) {
                for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
                    ArrayMap connections = processServiceRecord.getRunningServiceAt(numberOfRunningServices).getConnections();
                    for (int size3 = connections.size() - 1; size3 >= 0; size3--) {
                        ArrayList arrayList2 = (ArrayList) connections.valueAt(size3);
                        for (int size4 = arrayList2.size() - 1; size4 >= 0; size4--) {
                            ProcessRecord processRecord6 = ((ConnectionRecord) arrayList2.get(size4)).binding.attributedClient;
                            if (processRecord6 != null && processRecord6 != processRecord2 && ((processRecord6.mState.getMaxAdj() < -900 || processRecord6.mState.getMaxAdj() >= 0) && !processRecord6.mState.isReachable())) {
                                arrayDeque.offer(processRecord6);
                                processRecord6.mState.setReachable(true);
                            }
                        }
                    }
                }
            }
        }
        int size5 = arrayList.size();
        if (size5 > 0) {
            int i2 = 0;
            for (int i3 = size5 - 1; i2 < i3; i3--) {
                ProcessRecord processRecord7 = (ProcessRecord) arrayList.get(i2);
                arrayList.set(i2, (ProcessRecord) arrayList.get(i3));
                arrayList.set(i3, processRecord7);
                i2++;
            }
        }
        return z;
    }

    public void enqueueOomAdjTargetLocked(ProcessRecord processRecord) {
        if (processRecord == null || processRecord.mState.getMaxAdj() <= 0) {
            return;
        }
        this.mPendingProcessSet.add(processRecord);
    }

    public void removeOomAdjTargetLocked(ProcessRecord processRecord, boolean z) {
        if (processRecord != null) {
            this.mPendingProcessSet.remove(processRecord);
            if (z) {
                PlatformCompatCache.getInstance().invalidate(processRecord.info);
            }
        }
    }

    public final boolean checkAndEnqueueOomAdjTargetLocked(ProcessRecord processRecord) {
        if (!this.mOomAdjUpdateOngoing) {
            return false;
        }
        if (processRecord != null) {
            this.mPendingProcessSet.add(processRecord);
        } else {
            this.mPendingFullOomAdjUpdate = true;
        }
        return true;
    }

    public void updateOomAdjPendingTargetsLocked(int i) {
        if (this.mPendingFullOomAdjUpdate) {
            this.mPendingFullOomAdjUpdate = false;
            this.mPendingProcessSet.clear();
            updateOomAdjLocked(i);
        } else {
            if (this.mPendingProcessSet.isEmpty() || this.mOomAdjUpdateOngoing) {
                return;
            }
            try {
                this.mOomAdjUpdateOngoing = true;
                performUpdateOomAdjPendingTargetsLocked(i);
            } finally {
                this.mOomAdjUpdateOngoing = false;
                updateOomAdjPendingTargetsLocked(i);
            }
        }
    }

    public final void performUpdateOomAdjPendingTargetsLocked(int i) {
        ProcessRecord topApp = this.mService.getTopApp();
        Trace.traceBegin(64L, oomAdjReasonToString(i));
        this.mService.mOomAdjProfiler.oomAdjStarted();
        ArrayList arrayList = this.mTmpProcessList;
        ActiveUids activeUids = this.mTmpUidRecords;
        collectReachableProcessesLocked(this.mPendingProcessSet, arrayList, activeUids);
        this.mPendingProcessSet.clear();
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                updateOomAdjInnerLSP(i, topApp, arrayList, activeUids, true, false);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        arrayList.clear();
        this.mService.mOomAdjProfiler.oomAdjEnded();
        Trace.traceEnd(64L);
    }

    public final void updateOomAdjInnerLSP(int i, ProcessRecord processRecord, ArrayList arrayList, ActiveUids activeUids, boolean z, boolean z2) {
        ActiveUids activeUids2;
        ArrayList arrayList2;
        int i2;
        int i3;
        int i4;
        ActiveUids activeUids3;
        ArrayList arrayList3;
        boolean z3;
        if (z2) {
            Trace.traceBegin(64L, oomAdjReasonToString(i));
            this.mService.mOomAdjProfiler.oomAdjStarted();
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = uptimeMillis - this.mConstants.mMaxEmptyTimeMillis;
        boolean z4 = false;
        boolean z5 = arrayList == null;
        ArrayList lruProcessesLOSP = z5 ? this.mProcessList.getLruProcessesLOSP() : arrayList;
        int size = lruProcessesLOSP.size();
        if (activeUids == null) {
            int size2 = this.mActiveUids.size();
            ActiveUids activeUids4 = this.mTmpUidRecords;
            activeUids4.clear();
            for (int i5 = 0; i5 < size2; i5++) {
                UidRecord valueAt = this.mActiveUids.valueAt(i5);
                activeUids4.put(valueAt.getUid(), valueAt);
            }
            activeUids2 = activeUids4;
        } else {
            activeUids2 = activeUids;
        }
        for (int size3 = activeUids2.size() - 1; size3 >= 0; size3--) {
            activeUids2.valueAt(size3).reset();
        }
        this.mAdjSeq++;
        if (z5) {
            this.mNewNumServiceProcs = 0;
            this.mNewNumAServiceProcs = 0;
        }
        boolean z6 = z5 || z;
        int i6 = size - 1;
        for (int i7 = i6; i7 >= 0; i7--) {
            ProcessStateRecord processStateRecord = ((ProcessRecord) lruProcessesLOSP.get(i7)).mState;
            processStateRecord.setReachable(false);
            if (processStateRecord.getAdjSeq() != this.mAdjSeq) {
                processStateRecord.setContainsCycle(false);
                processStateRecord.setCurRawProcState(19);
                processStateRecord.setCurRawAdj(1001);
                processStateRecord.setSetCapability(0);
                processStateRecord.resetCachedInfo();
                processStateRecord.setCurBoundByNonBgRestrictedApp(false);
            }
        }
        this.mProcessesInCycle.clear();
        int i8 = i6;
        int i9 = 0;
        while (i8 >= 0) {
            ProcessRecord processRecord2 = (ProcessRecord) lruProcessesLOSP.get(i8);
            ProcessStateRecord processStateRecord2 = processRecord2.mState;
            if (processRecord2.isKilledByAm() || processRecord2.getThread() == null) {
                i3 = i8;
                i4 = size;
                activeUids3 = activeUids2;
                arrayList3 = lruProcessesLOSP;
                z3 = z4;
            } else {
                processStateRecord2.setProcStateChanged(z4);
                processRecord2.mOptRecord.setLastOomAdjChangeReason(i);
                i3 = i8;
                i4 = size;
                arrayList3 = lruProcessesLOSP;
                activeUids3 = activeUids2;
                z3 = z4;
                computeOomAdjLSP(processRecord2, 1001, processRecord, z5, uptimeMillis, false, z6);
                int i10 = i9 | (processStateRecord2.containsCycle() ? 1 : 0);
                processStateRecord2.setCompletedAdjSeq(this.mAdjSeq);
                i9 = i10;
            }
            i8 = i3 - 1;
            activeUids2 = activeUids3;
            size = i4;
            z4 = z3;
            lruProcessesLOSP = arrayList3;
        }
        int i11 = size;
        ActiveUids activeUids5 = activeUids2;
        ArrayList arrayList4 = lruProcessesLOSP;
        boolean z7 = z4;
        if (this.mCacheOomRanker.useOomReranking()) {
            this.mCacheOomRanker.reRankLruCachedAppsLSP(this.mProcessList.getLruProcessesLSP(), this.mProcessList.getLruProcessServiceStartLOSP());
        }
        if (z6) {
            int i12 = z7 ? 1 : 0;
            while (i9 != 0 && i12 < 10) {
                int i13 = i12 + 1;
                int i14 = z7 ? 1 : 0;
                while (i14 < i11) {
                    ArrayList arrayList5 = arrayList4;
                    ProcessRecord processRecord3 = (ProcessRecord) arrayList5.get(i14);
                    ProcessStateRecord processStateRecord3 = processRecord3.mState;
                    if (!processRecord3.isKilledByAm() && processRecord3.getThread() != null && processStateRecord3.containsCycle()) {
                        processStateRecord3.decAdjSeq();
                        processStateRecord3.decCompletedAdjSeq();
                    }
                    i14++;
                    arrayList4 = arrayList5;
                }
                ArrayList arrayList6 = arrayList4;
                int i15 = z7 ? 1 : 0;
                i9 = i15;
                while (i15 < i11) {
                    ProcessRecord processRecord4 = (ProcessRecord) arrayList6.get(i15);
                    ProcessStateRecord processStateRecord4 = processRecord4.mState;
                    if (processRecord4.isKilledByAm() || processRecord4.getThread() == null || !processStateRecord4.containsCycle()) {
                        arrayList2 = arrayList6;
                        i2 = i15;
                    } else {
                        arrayList2 = arrayList6;
                        i2 = i15;
                        if (computeOomAdjLSP(processRecord4, 1001, processRecord, true, uptimeMillis, true, true)) {
                            i9 = 1;
                        }
                    }
                    i15 = i2 + 1;
                    arrayList6 = arrayList2;
                }
                arrayList4 = arrayList6;
                i12 = i13;
            }
        }
        this.mProcessesInCycle.clear();
        assignCachedAdjIfNecessary(this.mProcessList.getLruProcessesLOSP());
        this.mNumNonCachedProcs = z7 ? 1 : 0;
        this.mNumCachedHiddenProcs = z7 ? 1 : 0;
        boolean updateAndTrimProcessLSP = updateAndTrimProcessLSP(uptimeMillis, elapsedRealtime, j, activeUids5, i);
        this.mNumServiceProcs = this.mNewNumServiceProcs;
        ActivityManagerService activityManagerService = this.mService;
        if (activityManagerService.mAlwaysFinishActivities) {
            activityManagerService.mAtmInternal.scheduleDestroyAllActivities("always-finish");
        }
        if (updateAndTrimProcessLSP) {
            ActivityManagerService activityManagerService2 = this.mService;
            activityManagerService2.mAppProfiler.requestPssAllProcsLPr(uptimeMillis, z7, activityManagerService2.mProcessStats.isMemFactorLowered());
        }
        updateUidsLSP(activeUids5, elapsedRealtime);
        synchronized (this.mService.mProcessStats.mLock) {
            long uptimeMillis2 = SystemClock.uptimeMillis();
            if (this.mService.mProcessStats.shouldWriteNowLocked(uptimeMillis2)) {
                ActivityManagerService activityManagerService3 = this.mService;
                activityManagerService3.mHandler.post(new ActivityManagerService.ProcStatsRunnable(activityManagerService3, activityManagerService3.mProcessStats));
            }
            this.mService.mProcessStats.updateTrackingAssociationsLocked(this.mAdjSeq, uptimeMillis2);
        }
        if (z2) {
            this.mService.mOomAdjProfiler.oomAdjEnded();
            Trace.traceEnd(64L);
        }
    }

    public final void assignCachedAdjIfNecessary(ArrayList arrayList) {
        int i;
        int i2;
        int i3;
        boolean z;
        int i4;
        DynamicHiddenApp dynamicHiddenApp;
        boolean z2;
        DynamicHiddenApp dynamicHiddenApp2;
        int i5;
        ArrayList arrayList2 = arrayList;
        int size = arrayList.size();
        ActivityManagerConstants activityManagerConstants = this.mConstants;
        if (activityManagerConstants.USE_TIERED_CACHED_ADJ) {
            long uptimeMillis = SystemClock.uptimeMillis();
            for (int i6 = size - 1; i6 >= 0; i6--) {
                ProcessRecord processRecord = (ProcessRecord) arrayList2.get(i6);
                ProcessStateRecord processStateRecord = processRecord.mState;
                ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
                if (!processRecord.isKilledByAm() && processRecord.getThread() != null && processStateRecord.getCurAdj() >= 1001) {
                    ProcessServiceRecord processServiceRecord = processRecord.mServices;
                    if (processCachedOptimizerRecord == null || !processCachedOptimizerRecord.isFreezeExempt()) {
                        i5 = (processStateRecord.getSetAdj() < 900 || processStateRecord.getLastStateTime() + this.mConstants.TIERED_CACHED_ADJ_DECAY_TIME >= uptimeMillis) ? 910 : 950;
                    } else {
                        i5 = 900;
                    }
                    processStateRecord.setCurRawAdj(i5);
                    processStateRecord.setCurAdj(processServiceRecord.modifyRawOomAdj(i5));
                }
            }
            return;
        }
        int i7 = activityManagerConstants.CUR_MAX_CACHED_PROCESSES - activityManagerConstants.CUR_MAX_EMPTY_PROCESSES;
        int i8 = size - this.mNumNonCachedProcs;
        int i9 = this.mNumCachedHiddenProcs;
        int i10 = i8 - i9;
        if (i10 <= i7) {
            i7 = i10;
        }
        int i11 = i9 > 0 ? (i9 + this.mNumSlots) - 1 : 1;
        int i12 = this.mNumSlots;
        int i13 = i11 / i12;
        if (i13 < 1) {
            i13 = 1;
        }
        int i14 = ((i7 + i12) - 1) / i12;
        if (i14 < 1) {
            i14 = 1;
        }
        DynamicHiddenApp dynamicHiddenApp3 = this.mDynamicHiddenApp;
        if (dynamicHiddenApp3 != null && DynamicHiddenApp.BORA_POLICY_ENABLE) {
            dynamicHiddenApp3.clearRecentActivityProcess();
        }
        int i15 = size - 1;
        int i16 = -1;
        int i17 = 915;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        int i22 = 905;
        int i23 = 900;
        int i24 = 910;
        int i25 = -1;
        while (i15 >= 0) {
            ProcessRecord processRecord2 = (ProcessRecord) arrayList2.get(i15);
            ProcessStateRecord processStateRecord2 = processRecord2.mState;
            if (processRecord2.isKilledByAm() || processRecord2.getThread() == null) {
                i = i15;
            } else {
                i = i15;
                if (processStateRecord2.getCurAdj() >= 1001) {
                    ProcessServiceRecord processServiceRecord2 = processRecord2.mServices;
                    switch (processStateRecord2.getCurProcState()) {
                        case 16:
                        case 17:
                        case 18:
                            int connectionGroup = processServiceRecord2.getConnectionGroup();
                            i2 = i14;
                            if (connectionGroup != 0) {
                                int connectionImportance = processServiceRecord2.getConnectionImportance();
                                i3 = i16;
                                int i26 = processRecord2.uid;
                                if (i19 == i26 && i20 == connectionGroup) {
                                    if (connectionImportance > i18) {
                                        if (i23 < i24 && i23 < 999) {
                                            i21++;
                                        }
                                        i18 = connectionImportance;
                                    }
                                    z = true;
                                    if (!z || i23 == i24) {
                                        i4 = i24;
                                        i24 = i23;
                                    } else {
                                        i25++;
                                        if (i25 >= i13) {
                                            i4 = i24 + 10;
                                            if (i4 > 999) {
                                                i4 = 999;
                                            }
                                            i25 = 0;
                                        } else {
                                            i4 = i24;
                                            i24 = i23;
                                        }
                                        i21 = 0;
                                    }
                                    int i27 = i24 + i21;
                                    processStateRecord2.setCurRawAdj(i27);
                                    processStateRecord2.setCurAdj(processServiceRecord2.modifyRawOomAdj(i27));
                                    dynamicHiddenApp = this.mDynamicHiddenApp;
                                    if (dynamicHiddenApp != null && DynamicHiddenApp.BORA_POLICY_ENABLE) {
                                        dynamicHiddenApp.addRecentActivityProcess(processRecord2);
                                    }
                                    i23 = i24;
                                    i14 = i2;
                                    i16 = i3;
                                    i24 = i4;
                                    break;
                                } else {
                                    i20 = connectionGroup;
                                    i18 = connectionImportance;
                                    i19 = i26;
                                }
                            } else {
                                i3 = i16;
                            }
                            z = false;
                            if (z) {
                            }
                            i4 = i24;
                            i24 = i23;
                            int i272 = i24 + i21;
                            processStateRecord2.setCurRawAdj(i272);
                            processStateRecord2.setCurAdj(processServiceRecord2.modifyRawOomAdj(i272));
                            dynamicHiddenApp = this.mDynamicHiddenApp;
                            if (dynamicHiddenApp != null) {
                                dynamicHiddenApp.addRecentActivityProcess(processRecord2);
                            }
                            i23 = i24;
                            i14 = i2;
                            i16 = i3;
                            i24 = i4;
                            break;
                        default:
                            i2 = i14;
                            i3 = i16;
                            if (DynamicHiddenApp.LMK_ENABLE_USERSPACE_LMK && processRecord2.hasActivities()) {
                                int connectionGroup2 = processServiceRecord2.getConnectionGroup();
                                if (connectionGroup2 != 0) {
                                    int connectionImportance2 = processServiceRecord2.getConnectionImportance();
                                    int i28 = processRecord2.uid;
                                    if (i19 == i28 && i20 == connectionGroup2) {
                                        if (connectionImportance2 > i18) {
                                            if (i23 < i24 && i23 < 999) {
                                                i21++;
                                            }
                                            i18 = connectionImportance2;
                                        }
                                        z2 = true;
                                        if (!z2 || i23 == i24) {
                                            i4 = i24;
                                            i24 = i23;
                                        } else {
                                            i25++;
                                            if (i25 >= i13) {
                                                i4 = i24 + 10;
                                                if (i4 > 999) {
                                                    i4 = 999;
                                                }
                                                i25 = 0;
                                            } else {
                                                i4 = i24;
                                                i24 = i23;
                                            }
                                            i21 = 0;
                                        }
                                        int i29 = i24 + i21;
                                        processStateRecord2.setCurRawAdj(i29);
                                        processStateRecord2.setCurAdj(processServiceRecord2.modifyRawOomAdj(i29));
                                        dynamicHiddenApp2 = this.mDynamicHiddenApp;
                                        if (dynamicHiddenApp2 != null && DynamicHiddenApp.BORA_POLICY_ENABLE) {
                                            dynamicHiddenApp2.addRecentActivityProcess(processRecord2);
                                        }
                                        i23 = i24;
                                        i14 = i2;
                                        i16 = i3;
                                        i24 = i4;
                                        break;
                                    } else {
                                        i20 = connectionGroup2;
                                        i18 = connectionImportance2;
                                        i19 = i28;
                                    }
                                }
                                z2 = false;
                                if (z2) {
                                }
                                i4 = i24;
                                i24 = i23;
                                int i292 = i24 + i21;
                                processStateRecord2.setCurRawAdj(i292);
                                processStateRecord2.setCurAdj(processServiceRecord2.modifyRawOomAdj(i292));
                                dynamicHiddenApp2 = this.mDynamicHiddenApp;
                                if (dynamicHiddenApp2 != null) {
                                    dynamicHiddenApp2.addRecentActivityProcess(processRecord2);
                                }
                                i23 = i24;
                                i14 = i2;
                                i16 = i3;
                                i24 = i4;
                            } else {
                                if (i22 != i17) {
                                    i16 = i3 + 1;
                                    i14 = i2;
                                    if (i16 >= i14) {
                                        int i30 = i17 + 10;
                                        i22 = i17;
                                        if (i30 > 999) {
                                            i17 = 999;
                                            i16 = 0;
                                        } else {
                                            i16 = 0;
                                            i17 = i30;
                                        }
                                    }
                                } else {
                                    i14 = i2;
                                    i16 = i3;
                                }
                                processStateRecord2.setCurRawAdj(i22);
                                processStateRecord2.setCurAdj(processServiceRecord2.modifyRawOomAdj(i22));
                                break;
                            }
                            break;
                    }
                    i15 = i - 1;
                    arrayList2 = arrayList;
                }
            }
            i16 = i16;
            i15 = i - 1;
            arrayList2 = arrayList;
        }
    }

    public static double getFreeSwapPercent() {
        return CachedAppOptimizer.getFreeSwapPercent();
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x018d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateAndTrimProcessLSP(long j, long j2, long j3, ActiveUids activeUids, int i) {
        double d;
        ProcessRecord processRecord;
        int i2;
        double d2;
        double d3;
        long j4;
        boolean z;
        String str;
        int i3;
        int i4;
        boolean z2;
        int i5;
        String str2;
        int i6;
        int i7;
        int i8;
        ProcessRecord processRecord2;
        int i9;
        int i10;
        int checkKeptProcess;
        int i11;
        int i12;
        int i13;
        long j5 = j3;
        ArrayList lruProcessesLOSP = this.mProcessList.getLruProcessesLOSP();
        int size = lruProcessesLOSP.size();
        boolean shouldKillExcessiveProcesses = shouldKillExcessiveProcesses(j);
        String str3 = "OomAdjuster";
        if (!shouldKillExcessiveProcesses && this.mNextNoKillDebugMessageTime < j) {
            Slog.d("OomAdjuster", "Not killing cached processes");
            this.mNextNoKillDebugMessageTime = j + 5000;
        }
        int i14 = shouldKillExcessiveProcesses ? this.mConstants.CUR_MAX_EMPTY_PROCESSES : Integer.MAX_VALUE;
        int i15 = shouldKillExcessiveProcesses ? this.mConstants.CUR_MAX_CACHED_PROCESSES - i14 : Integer.MAX_VALUE;
        DynamicHiddenApp dynamicHiddenApp = this.mDynamicHiddenApp;
        if (dynamicHiddenApp != null) {
            dynamicHiddenApp.initActiveLaunchParam();
            this.mDynamicHiddenApp.initMLLaunchCountParam();
            this.mDynamicHiddenApp.initDhaProcessesLocked();
        }
        boolean z3 = ActivityManagerConstants.PROACTIVE_KILLS_ENABLED;
        double d4 = ActivityManagerConstants.LOW_SWAP_THRESHOLD_PERCENT;
        double freeSwapPercent = z3 ? getFreeSwapPercent() : 1.0d;
        boolean z4 = true;
        int i16 = size - 1;
        ProcessRecord processRecord3 = null;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        int i23 = 0;
        while (i16 >= 0) {
            ProcessRecord processRecord4 = (ProcessRecord) lruProcessesLOSP.get(i16);
            ArrayList arrayList = lruProcessesLOSP;
            ProcessStateRecord processStateRecord = processRecord4.mState;
            if (processRecord4.isKilledByAm() || processRecord4.getThread() == null || processRecord4.isPendingFinishAttach()) {
                i2 = i16;
                d2 = freeSwapPercent;
                d3 = d4;
                j4 = j5;
                z = shouldKillExcessiveProcesses;
                str = str3;
                i3 = i14;
                i4 = i17;
                z2 = z4;
                i23 = i23;
                i22 = i22;
                i18 = i18;
            } else {
                if (processRecord4.isExcessiveResourceUsage()) {
                    StringBuilder sb = new StringBuilder();
                    i5 = i16;
                    sb.append("Excessive Resource Usage detectd task: ");
                    sb.append(ActivityManager.procStateToString(processStateRecord.getCurProcState()));
                    sb.append(" : ");
                    sb.append(processRecord4.toShortString());
                    Slog.i(str3, sb.toString());
                } else {
                    i5 = i16;
                }
                if (processStateRecord.getCompletedAdjSeq() == this.mAdjSeq) {
                    i2 = i5;
                    z = shouldKillExcessiveProcesses;
                    i7 = i18;
                    str2 = str3;
                    i6 = 2;
                    i4 = i17;
                    i8 = i14;
                    processRecord2 = processRecord4;
                    d2 = freeSwapPercent;
                    d3 = d4;
                    z2 = true;
                    applyOomAdjLSP(processRecord4, true, j, j2, i);
                } else {
                    d2 = freeSwapPercent;
                    d3 = d4;
                    z = shouldKillExcessiveProcesses;
                    str2 = str3;
                    i2 = i5;
                    i6 = 2;
                    z2 = true;
                    i4 = i17;
                    i7 = i18;
                    i8 = i14;
                    processRecord2 = processRecord4;
                }
                ProcessServiceRecord processServiceRecord = processRecord2.mServices;
                DynamicHiddenApp dynamicHiddenApp2 = this.mDynamicHiddenApp;
                if (dynamicHiddenApp2 != null && (checkKeptProcess = dynamicHiddenApp2.checkKeptProcess(processRecord2)) > 0) {
                    if (processRecord2.isActiveLaunch == z2) {
                        this.mDynamicHiddenApp.activeLaunchKillCheck(processRecord2);
                    } else if (processRecord2.getIpmLaunchtype() == 0) {
                        this.mDynamicHiddenApp.updateNapProcessProtection(processRecord2);
                    } else {
                        int curProcState = processStateRecord.getCurProcState();
                        if (curProcState != 16) {
                            if (curProcState != 17) {
                                if (curProcState != 19) {
                                    this.mNumNonCachedProcs += z2 ? 1 : 0;
                                }
                                if (checkKeptProcess == i6) {
                                    j4 = j3;
                                    i11 = i22;
                                    i12 = i23;
                                } else if (this.mDynamicHiddenApp.destroyKeptProcessActivity(processRecord2, i4, i15)) {
                                    int i24 = i4 + 1;
                                    int connectionGroup = processServiceRecord.getConnectionGroup();
                                    if (connectionGroup != 0) {
                                        int i25 = i22;
                                        if (i25 == processRecord2.info.uid && (i13 = i23) == connectionGroup) {
                                            i21++;
                                            i22 = i25;
                                            i23 = i13;
                                        } else {
                                            i23 = connectionGroup;
                                            i22 = processRecord2.info.uid;
                                        }
                                    } else {
                                        i22 = 0;
                                        i23 = 0;
                                    }
                                    j4 = j3;
                                    i3 = i8;
                                    i4 = i24;
                                    i18 = i7;
                                } else {
                                    i11 = i22;
                                    i12 = i23;
                                    j4 = j3;
                                    this.mDynamicHiddenApp.killTimeOverEmptyProcess(processRecord2, i7, j4);
                                }
                                i3 = i8;
                                i22 = i11;
                                i23 = i12;
                                i18 = i7;
                            }
                        } else if (checkKeptProcess == z2) {
                            i20++;
                        }
                        this.mNumCachedHiddenProcs += z2 ? 1 : 0;
                        if (checkKeptProcess == i6) {
                        }
                        i3 = i8;
                        i22 = i11;
                        i23 = i12;
                        i18 = i7;
                    }
                    j4 = j3;
                    i10 = i22;
                    i9 = i23;
                    i3 = i8;
                    i23 = i9;
                    i22 = i10;
                    i18 = i7;
                } else {
                    int i26 = i22;
                    i9 = i23;
                    j4 = j3;
                    int curProcState2 = processStateRecord.getCurProcState();
                    if (curProcState2 == 16 || curProcState2 == 17) {
                        i3 = i8;
                        this.mNumCachedHiddenProcs += z2 ? 1 : 0;
                        int i27 = i4 + 1;
                        int connectionGroup2 = processServiceRecord.getConnectionGroup();
                        if (connectionGroup2 == 0) {
                            connectionGroup2 = 0;
                            i22 = 0;
                        } else if (i26 == processRecord2.info.uid && i9 == connectionGroup2) {
                            i21++;
                            connectionGroup2 = i9;
                            i22 = i26;
                        } else {
                            i22 = processRecord2.info.uid;
                        }
                        if (i27 - i21 > i15) {
                            if (processStateRecord.getCurProcState() == 16) {
                                if (SemGateConfig.isGateEnabled() && this.VENDING_PKG.equals(processRecord2.processName)) {
                                    Log.i("GATE", "<GATE-M> MARKET_LAUNCHED_FAIL </GATE-M>");
                                }
                                processRecord2.killLocked("cached #" + i27, "too many cached", 13, 2, true);
                            }
                        } else if (z3) {
                            if (processStateRecord.getCurProcState() == 16) {
                                i4 = i27;
                                i23 = connectionGroup2;
                                i18 = i7;
                            }
                        } else if (this.mDynamicHiddenApp.isForceKillHeavyCondition(processRecord2, (i27 + i20) - i21)) {
                            processRecord2.killLocked("bg_restriction", 13, 2, z2);
                        }
                        i4 = i27;
                        i23 = connectionGroup2;
                        i18 = i7;
                    } else {
                        if (curProcState2 == 19) {
                            if (!processRecord2.mDedicated) {
                                if (i7 <= this.mConstants.CUR_TRIM_EMPTY_PROCESSES || processRecord2.getLastActivityTime() >= j4) {
                                    i18 = i7 + 1;
                                    i3 = i8;
                                    if (i18 > i3) {
                                        if (!this.mDynamicHiddenApp.isBEKCondition(processRecord2)) {
                                            processRecord2.killLocked("empty #" + i18, "too many empty", 13, 3, true);
                                        }
                                    } else if (z3 && !this.mDynamicHiddenApp.isBEKCondition(processRecord2)) {
                                        i23 = i9;
                                        i22 = i26;
                                    }
                                    i23 = i9;
                                    i22 = i26;
                                } else {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("empty for ");
                                    i10 = i26;
                                    sb2.append((j - processRecord2.getLastActivityTime()) / 1000);
                                    sb2.append("s");
                                    processRecord2.killLocked(sb2.toString(), "empty for too long", 13, 4, true);
                                    i3 = i8;
                                    i23 = i9;
                                    i22 = i10;
                                    i18 = i7;
                                }
                            }
                        } else {
                            if (this.mDynamicHiddenApp.isForceKillHeavyCondition(processRecord2, (i4 + i20) - i21) && processRecord2.hasActivities() && processStateRecord.getSetAdj() >= 200 && processStateRecord.getCurAdj() >= 200) {
                                processRecord2.killLocked("bg_restriction", 13, 2, z2);
                            }
                            this.mNumNonCachedProcs += z2 ? 1 : 0;
                        }
                        i10 = i26;
                        i3 = i8;
                        i23 = i9;
                        i22 = i10;
                        i18 = i7;
                    }
                    processRecord3 = processRecord2;
                }
                if (processRecord2.isExcessiveResourceUsage()) {
                    switch (processStateRecord.getCurProcState()) {
                        case 16:
                        case 17:
                        case 18:
                            str = str2;
                            Slog.d(str, "ERU task kill: " + processRecord2.toShortString());
                            processRecord2.killLocked("Excessive resource usage", 9, z2);
                            break;
                    }
                    if (!processRecord2.isolated && processServiceRecord.numberOfRunningServices() <= 0 && processRecord2.getIsolatedEntryPoint() == null) {
                        processRecord2.killLocked("isolated not needed", 13, 17, z2);
                    } else if (!processRecord2.isSdkSandbox && processServiceRecord.numberOfRunningServices() <= 0 && processRecord2.getActiveInstrumentation() == null) {
                        processRecord2.killLocked("sandbox not needed", 13, 28, z2);
                    } else {
                        updateAppUidRecLSP(processRecord2);
                    }
                    if (processStateRecord.getCurProcState() >= 14 && !processRecord2.isKilledByAm()) {
                        i19++;
                    }
                }
                str = str2;
                if (!processRecord2.isolated) {
                }
                if (!processRecord2.isSdkSandbox) {
                }
                updateAppUidRecLSP(processRecord2);
                if (processStateRecord.getCurProcState() >= 14) {
                    i19++;
                }
            }
            i16 = i2 - 1;
            i14 = i3;
            z4 = z2;
            i17 = i4;
            lruProcessesLOSP = arrayList;
            shouldKillExcessiveProcesses = z;
            d4 = d3;
            str3 = str;
            j5 = j4;
            freeSwapPercent = d2;
        }
        int i28 = i17;
        double d5 = freeSwapPercent;
        double d6 = d4;
        boolean z5 = z4;
        boolean z6 = shouldKillExcessiveProcesses;
        int i29 = i18;
        if (z3 && z6) {
            d = d5;
            if (d < d6 && (processRecord = processRecord3) != null && d < this.mLastFreeSwapPercent) {
                processRecord.killLocked("swap low and too many cached", 13, 2, z5);
            }
        } else {
            d = d5;
        }
        if (this.mConstants.getOverrideMaxCachedProcesses() < 0) {
            this.mNumCachedProcessCount = i28;
            this.mNumEmptyProcessCount = i29;
            this.mNumCachedSlotCount = this.mDynamicHiddenApp.getCachedMax();
            this.mNumEmptySlotCount = this.mDynamicHiddenApp.getEmptyMax();
        }
        this.mLastFreeSwapPercent = d;
        return this.mService.mAppProfiler.updateLowMemStateLSP(i28, i29, i19, j);
    }

    public final void updateAppUidRecIfNecessaryLSP(ProcessRecord processRecord) {
        if (processRecord.isKilledByAm() || processRecord.getThread() == null) {
            return;
        }
        if (processRecord.isolated && processRecord.mServices.numberOfRunningServices() <= 0 && processRecord.getIsolatedEntryPoint() == null) {
            return;
        }
        updateAppUidRecLSP(processRecord);
    }

    public final void updateAppUidRecLSP(ProcessRecord processRecord) {
        UidRecord uidRecord = processRecord.getUidRecord();
        if (uidRecord != null) {
            ProcessStateRecord processStateRecord = processRecord.mState;
            uidRecord.setEphemeral(processRecord.info.isInstantApp());
            if (uidRecord.getCurProcState() > processStateRecord.getCurProcState()) {
                uidRecord.setCurProcState(processStateRecord.getCurProcState());
            }
            if (processRecord.mServices.hasForegroundServices()) {
                uidRecord.setForegroundServices(true);
            }
            uidRecord.setCurCapability(uidRecord.getCurCapability() | processStateRecord.getCurCapability());
        }
    }

    public final void updateUidsLSP(ActiveUids activeUids, long j) {
        int i;
        this.mProcessList.incrementProcStateSeqAndNotifyAppsLOSP(activeUids);
        ArrayList arrayList = this.mTmpBecameIdle;
        arrayList.clear();
        PowerManagerInternal powerManagerInternal = this.mLocalPowerManager;
        if (powerManagerInternal != null) {
            powerManagerInternal.startUidChanges();
        }
        for (int size = activeUids.size() - 1; size >= 0; size--) {
            UidRecord valueAt = activeUids.valueAt(size);
            if (valueAt.getCurProcState() != 20 && (valueAt.getSetProcState() != valueAt.getCurProcState() || valueAt.getSetCapability() != valueAt.getCurCapability() || valueAt.isSetAllowListed() != valueAt.isCurAllowListed() || valueAt.getProcAdjChanged())) {
                if (valueAt.getSetCapability() != valueAt.getCurCapability()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Changes in ");
                    sb.append(valueAt.getUid());
                    sb.append(" ");
                    sb.append(valueAt.getSetProcState());
                    sb.append(" to ");
                    sb.append(valueAt.getCurProcState());
                    sb.append(", ");
                    sb.append(valueAt.getSetCapability());
                    sb.append(" to ");
                    sb.append(valueAt.getCurCapability());
                    sb.append(CoreRune.SAFE_DEBUG ? " Caller=" + Debug.getCallers(7) : "");
                    Slog.i("ActivityManager", sb.toString());
                }
                if (ActivityManager.isProcStateBackground(valueAt.getCurProcState()) && !valueAt.isCurAllowListed()) {
                    if (!ActivityManager.isProcStateBackground(valueAt.getSetProcState()) || valueAt.isSetAllowListed() || valueAt.getLastBackgroundTime() == 0) {
                        valueAt.setLastBackgroundTime(j);
                        if (this.mService.mDeterministicUidIdle || !this.mService.mHandler.hasMessages(58)) {
                            this.mService.mHandler.sendEmptyMessageDelayed(58, this.mConstants.BACKGROUND_SETTLE_TIME);
                        }
                    }
                    if (!valueAt.isIdle() || valueAt.isSetIdle()) {
                        i = 0;
                    } else {
                        if (valueAt.getSetProcState() != 20) {
                            arrayList.add(valueAt);
                        }
                        i = 2;
                    }
                } else {
                    if (valueAt.isIdle()) {
                        EventLogTags.writeAmUidActive(valueAt.getUid());
                        valueAt.setIdle(false);
                        i = 4;
                    } else {
                        i = 0;
                    }
                    valueAt.setLastBackgroundTime(0L);
                    valueAt.setLastIdleTime(0L);
                }
                boolean z = valueAt.getSetProcState() > 11;
                boolean z2 = valueAt.getCurProcState() > 11;
                if (z != z2 || valueAt.getSetProcState() == 20) {
                    i |= z2 ? 8 : 16;
                }
                if (valueAt.getSetCapability() != valueAt.getCurCapability()) {
                    i |= 32;
                }
                if (valueAt.getSetProcState() != valueAt.getCurProcState()) {
                    i |= Integer.MIN_VALUE;
                }
                if (valueAt.getProcAdjChanged()) {
                    i |= 64;
                }
                valueAt.setSetProcState(valueAt.getCurProcState());
                valueAt.setSetCapability(valueAt.getCurCapability());
                valueAt.setSetAllowListed(valueAt.isCurAllowListed());
                valueAt.setSetIdle(valueAt.isIdle());
                valueAt.clearProcAdjChanged();
                int i2 = i & Integer.MIN_VALUE;
                if (i2 != 0 || (i & 32) != 0) {
                    this.mService.mAtmInternal.onUidProcStateChanged(valueAt.getUid(), valueAt.getSetProcState());
                }
                if (i != 0) {
                    this.mService.enqueueUidChangeLocked(valueAt, -1, i);
                }
                if (i2 != 0 || (i & 32) != 0) {
                    this.mService.noteUidProcessState(valueAt.getUid(), valueAt.getCurProcState(), valueAt.getCurCapability());
                }
                if (valueAt.hasForegroundServices()) {
                    this.mService.mServices.foregroundServiceProcStateChangedLocked(valueAt);
                }
            }
            this.mService.mInternal.deletePendingTopUid(valueAt.getUid(), j);
        }
        PowerManagerInternal powerManagerInternal2 = this.mLocalPowerManager;
        if (powerManagerInternal2 != null) {
            powerManagerInternal2.finishUidChanges();
        }
        if (arrayList.size() > 0) {
            for (int i3 = r12 - 1; i3 >= 0; i3--) {
                this.mService.mServices.stopInBackgroundLocked(((UidRecord) arrayList.get(i3)).getUid());
            }
        }
    }

    public final boolean shouldKillExcessiveProcesses(long j) {
        long lastUserUnlockingUptime = this.mService.mUserController.getLastUserUnlockingUptime();
        if (lastUserUnlockingUptime == 0) {
            return !this.mConstants.mNoKillCachedProcessesUntilBootCompleted;
        }
        return lastUserUnlockingUptime + this.mConstants.mNoKillCachedProcessesPostBootCompletedDurationMillis <= j;
    }

    public final class ComputeOomAdjWindowCallback implements WindowProcessController.ComputeOomAdjCallback {
        public int adj;
        public ProcessRecord app;
        public int appUid;
        public boolean foregroundActivities;
        public int logUid;
        public boolean mHasVisibleActivities;
        public ProcessStateRecord mState;
        public int procState;
        public int processStateCurTop;
        public int schedGroup;

        public ComputeOomAdjWindowCallback() {
        }

        public void initialize(ProcessRecord processRecord, int i, boolean z, boolean z2, int i2, int i3, int i4, int i5, int i6) {
            this.app = processRecord;
            this.adj = i;
            this.foregroundActivities = z;
            this.mHasVisibleActivities = z2;
            this.procState = i2;
            this.schedGroup = i3;
            this.appUid = i4;
            this.logUid = i5;
            this.processStateCurTop = i6;
            this.mState = processRecord.mState;
        }

        @Override // com.android.server.wm.WindowProcessController.ComputeOomAdjCallback
        public void onVisibleActivity() {
            if (this.adj > 100) {
                this.adj = 100;
                this.mState.setAdjType("vis-activity");
                if (this.logUid == this.appUid) {
                    OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise adj to vis-activity: " + this.app);
                }
            }
            int i = this.procState;
            int i2 = this.processStateCurTop;
            if (i > i2) {
                this.procState = i2;
                this.mState.setAdjType("vis-activity");
                if (this.logUid == this.appUid) {
                    OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise procstate to vis-activity (top): " + this.app);
                }
            }
            if (this.schedGroup < 2) {
                this.schedGroup = 2;
            }
            this.mState.setCached(false);
            this.mState.setEmpty(false);
            this.foregroundActivities = true;
            this.mHasVisibleActivities = true;
        }

        @Override // com.android.server.wm.WindowProcessController.ComputeOomAdjCallback
        public void onPausedActivity() {
            if (this.adj > 200) {
                this.adj = 200;
                this.mState.setAdjType("pause-activity");
                if (this.logUid == this.appUid) {
                    OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise adj to pause-activity: " + this.app);
                }
            }
            int i = this.procState;
            int i2 = this.processStateCurTop;
            if (i > i2) {
                this.procState = i2;
                this.mState.setAdjType("pause-activity");
                if (this.logUid == this.appUid) {
                    OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise procstate to pause-activity (top): " + this.app);
                }
            }
            if (this.schedGroup < 2) {
                this.schedGroup = 2;
            }
            this.mState.setCached(false);
            this.mState.setEmpty(false);
            this.foregroundActivities = true;
            this.mHasVisibleActivities = false;
        }

        @Override // com.android.server.wm.WindowProcessController.ComputeOomAdjCallback
        public void onStoppingActivity(boolean z) {
            if (this.adj > 200) {
                this.adj = 200;
                this.mState.setAdjType("stop-activity");
                if (this.logUid == this.appUid) {
                    OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise adj to stop-activity: " + this.app);
                }
            }
            if (!z && this.procState > 15) {
                this.procState = 15;
                this.mState.setAdjType("stop-activity");
                if (this.logUid == this.appUid) {
                    OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise procstate to stop-activity: " + this.app);
                }
            }
            this.mState.setCached(false);
            this.mState.setEmpty(false);
            this.foregroundActivities = true;
            this.mHasVisibleActivities = false;
        }

        @Override // com.android.server.wm.WindowProcessController.ComputeOomAdjCallback
        public void onOtherActivity() {
            if (this.procState > 16) {
                this.procState = 16;
                this.mState.setAdjType("cch-act");
                if (this.logUid == this.appUid) {
                    OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise procstate to cached activity: " + this.app);
                }
            }
            this.mHasVisibleActivities = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:413:0x0701, code lost:
    
        if (r67 < (r11.lastActivity + r62.mConstants.MAX_SERVICE_INACTIVITY)) goto L311;
     */
    /* JADX WARN: Code restructure failed: missing block: B:665:0x0a1b, code lost:
    
        if (r1 >= 200) goto L493;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0490 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x04c9  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0517  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0571  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x05f7  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x062c  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x069a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0cac  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0f24  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0f8c  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0faf  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x1010  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x101f  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x1037  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x1041  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x1047  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x1054  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x106b  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x1078  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x10b5  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x10c3  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x10d7  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x1030  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x06ab  */
    /* JADX WARN: Removed duplicated region for block: B:416:0x073a  */
    /* JADX WARN: Removed duplicated region for block: B:420:0x074d  */
    /* JADX WARN: Removed duplicated region for block: B:441:0x0789  */
    /* JADX WARN: Removed duplicated region for block: B:483:0x08a6  */
    /* JADX WARN: Removed duplicated region for block: B:486:0x08bc  */
    /* JADX WARN: Removed duplicated region for block: B:523:0x0985  */
    /* JADX WARN: Removed duplicated region for block: B:534:0x0a6c  */
    /* JADX WARN: Removed duplicated region for block: B:548:0x0ae3  */
    /* JADX WARN: Removed duplicated region for block: B:553:0x0af2  */
    /* JADX WARN: Removed duplicated region for block: B:555:0x0af9  */
    /* JADX WARN: Removed duplicated region for block: B:561:0x0b05  */
    /* JADX WARN: Removed duplicated region for block: B:565:0x0b13  */
    /* JADX WARN: Removed duplicated region for block: B:571:0x0b9d  */
    /* JADX WARN: Removed duplicated region for block: B:591:0x0ba3  */
    /* JADX WARN: Removed duplicated region for block: B:594:0x0b6a  */
    /* JADX WARN: Removed duplicated region for block: B:612:0x0aca  */
    /* JADX WARN: Removed duplicated region for block: B:630:0x0a48  */
    /* JADX WARN: Removed duplicated region for block: B:632:0x0a4e  */
    /* JADX WARN: Removed duplicated region for block: B:692:0x0b78  */
    /* JADX WARN: Removed duplicated region for block: B:697:0x08ad  */
    /* JADX WARN: Removed duplicated region for block: B:707:0x0c5e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:723:0x05b0  */
    /* JADX WARN: Removed duplicated region for block: B:728:0x05d6  */
    /* JADX WARN: Removed duplicated region for block: B:737:0x04aa  */
    /* JADX WARN: Removed duplicated region for block: B:756:0x03cb  */
    /* JADX WARN: Removed duplicated region for block: B:761:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:763:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:767:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0397  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x03fd  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x044f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean computeOomAdjLSP(ProcessRecord processRecord, int i, ProcessRecord processRecord2, boolean z, long j, boolean z2, boolean z3) {
        int i2;
        int i3;
        int i4;
        boolean z4;
        boolean z5;
        boolean z6;
        int i5;
        int i6;
        boolean z7;
        ProcessServiceRecord processServiceRecord;
        int i7;
        int i8;
        int i9;
        boolean hasForegroundServices;
        int i10;
        boolean z8;
        int i11;
        String str;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        BackupRecord backupRecord;
        int i19;
        int numberOfRunningServices;
        boolean z9;
        boolean z10;
        int i20;
        int i21;
        int numberOfProviders;
        ProcessProviderRecord processProviderRecord;
        ProcessStateRecord processStateRecord;
        ProcessServiceRecord processServiceRecord2;
        int i22;
        int i23;
        int i24;
        int i25;
        boolean z11;
        int i26;
        int i27;
        int i28;
        String str2;
        ContentProviderConnection contentProviderConnection;
        int i29;
        int i30;
        ContentProviderRecord contentProviderRecord;
        int i31;
        ProcessStateRecord processStateRecord2;
        int i32;
        ProcessServiceRecord processServiceRecord3;
        String str3;
        String str4;
        String str5;
        int i33;
        ProcessRecord processRecord3;
        ProcessStateRecord processStateRecord3;
        String str6;
        int i34;
        String str7;
        String str8;
        ContentProviderRecord contentProviderRecord2;
        String str9;
        ServiceRecord runningServiceAt;
        ProcessServiceRecord processServiceRecord4;
        String str10;
        int i35;
        int i36;
        ArrayMap connections;
        int i37;
        int size;
        int i38;
        String str11;
        int i39;
        int i40;
        String str12;
        ProcessServiceRecord processServiceRecord5;
        int i41;
        ProcessStateRecord processStateRecord4;
        ProcessRecord processRecord4;
        int i42;
        int i43;
        ArrayList arrayList;
        int i44;
        String str13;
        ArrayMap arrayMap;
        int i45;
        int i46;
        int i47;
        ProcessServiceRecord processServiceRecord6;
        int i48;
        String str14;
        int i49;
        ProcessStateRecord processStateRecord5;
        ProcessStateRecord processStateRecord6;
        boolean z12;
        ProcessRecord processRecord5;
        ConnectionRecord connectionRecord;
        ProcessStateRecord processStateRecord7;
        int i50;
        String str15;
        int i51;
        int i52;
        String str16;
        boolean z13;
        ProcessServiceRecord processServiceRecord7;
        ProcessRecord processRecord6;
        int i53;
        ConnectionRecord connectionRecord2;
        String str17;
        int i54;
        boolean z14;
        int i55;
        int curCapability;
        int i56;
        int i57;
        int i58;
        int i59;
        boolean z15;
        ProcessRecord processRecord7;
        int i60;
        int i61;
        int i62;
        int i63;
        int i64;
        ProcessStateRecord processStateRecord8 = processRecord.mState;
        if (this.mAdjSeq == processStateRecord8.getAdjSeq()) {
            if (processStateRecord8.getAdjSeq() == processStateRecord8.getCompletedAdjSeq()) {
                return false;
            }
            processStateRecord8.setContainsCycle(true);
            this.mProcessesInCycle.add(processRecord);
            return false;
        }
        if (processRecord.getThread() == null) {
            processStateRecord8.setAdjSeq(this.mAdjSeq);
            processStateRecord8.setCurrentSchedulingGroup(0);
            processStateRecord8.setCurProcState(19);
            processStateRecord8.setCurAdj(999);
            processStateRecord8.setCurRawAdj(999);
            processStateRecord8.setCompletedAdjSeq(processStateRecord8.getAdjSeq());
            processStateRecord8.setCurCapability(0);
            return false;
        }
        processStateRecord8.setAdjTypeCode(0);
        processStateRecord8.setAdjSource(null);
        processStateRecord8.setAdjTarget(null);
        processStateRecord8.setEmpty(false);
        processStateRecord8.setCached(false);
        if (!z2) {
            processStateRecord8.setNoKillOnBgRestrictedAndIdle(false);
            UidRecord uidRecord = processRecord.getUidRecord();
            processRecord.mOptRecord.setShouldNotFreeze(uidRecord != null && uidRecord.isCurAllowListed());
        }
        int i65 = processRecord.info.uid;
        int i66 = this.mService.mCurOomAdjUid;
        int curAdj = processStateRecord8.getCurAdj();
        int curProcState = processStateRecord8.getCurProcState();
        int curCapability2 = processStateRecord8.getCurCapability();
        ProcessServiceRecord processServiceRecord8 = processRecord.mServices;
        String str18 = "ActivityManager";
        if (processStateRecord8.getMaxAdj() <= 0) {
            if (i66 == i65) {
                reportOomAdjMessageLocked("ActivityManager", "Making fixed: " + processRecord);
            }
            processStateRecord8.setAdjType("fixed");
            processStateRecord8.setAdjSeq(this.mAdjSeq);
            processStateRecord8.setCurRawAdj(processStateRecord8.getMaxAdj());
            processStateRecord8.setHasForegroundActivities(false);
            processStateRecord8.setCurrentSchedulingGroup(2);
            processStateRecord8.setCurCapability(63);
            processStateRecord8.setCurProcState(0);
            processStateRecord8.setSystemNoUi(true);
            if (processRecord == processRecord2) {
                processStateRecord8.setSystemNoUi(false);
                processStateRecord8.setCurrentSchedulingGroup(3);
                processStateRecord8.setAdjType("pers-top-activity");
            } else if (processStateRecord8.hasTopUi()) {
                processStateRecord8.setSystemNoUi(false);
                processStateRecord8.setAdjType("pers-top-ui");
            } else if (processStateRecord8.getCachedHasVisibleActivities()) {
                processStateRecord8.setSystemNoUi(false);
            }
            if (!processStateRecord8.isSystemNoUi()) {
                if (this.mService.mWakefulness.get() == 1 || processStateRecord8.isRunningRemoteAnimation()) {
                    processStateRecord8.setCurProcState(1);
                    processStateRecord8.setCurrentSchedulingGroup(3);
                } else {
                    processStateRecord8.setCurProcState(5);
                    processStateRecord8.setCurrentSchedulingGroup(1);
                }
            }
            processStateRecord8.setCurRawProcState(processStateRecord8.getCurProcState());
            processStateRecord8.setCurAdj(processStateRecord8.getMaxAdj());
            processStateRecord8.setCompletedAdjSeq(processStateRecord8.getAdjSeq());
            if (processRecord.isActiveLaunch) {
                processStateRecord8.setCurrentSchedulingGroup(2);
            }
            return processStateRecord8.getCurAdj() < curAdj || processStateRecord8.getCurProcState() < curProcState;
        }
        int i67 = 3;
        processStateRecord8.setSystemNoUi(false);
        int topProcessState = this.mService.mAtmInternal.getTopProcessState();
        int curCapability3 = z2 ? processRecord.mState.getCurCapability() : 0;
        if (processRecord == processRecord2 && topProcessState == 2) {
            if (this.mService.mAtmInternal.useTopSchedGroupForTopProcess()) {
                processStateRecord8.setAdjType("top-activity");
                i64 = 3;
            } else {
                processStateRecord8.setAdjType("intermediate-top-activity");
                i64 = 2;
            }
            i2 = curProcState;
            if (i66 == i65) {
                StringBuilder sb = new StringBuilder();
                i3 = curAdj;
                sb.append("Making top: ");
                sb.append(processRecord);
                reportOomAdjMessageLocked("ActivityManager", sb.toString());
            } else {
                i3 = curAdj;
            }
            DamonReclaimer.notifyAppStart(processRecord.processName);
            i6 = i64;
            z4 = true;
            i4 = 0;
            z5 = true;
            z6 = true;
            i5 = 2;
        } else {
            i2 = curProcState;
            i3 = curAdj;
            if (processStateRecord8.isRunningRemoteAnimation()) {
                processStateRecord8.setAdjType("running-remote-anim");
                if (i66 == i65) {
                    reportOomAdjMessageLocked("ActivityManager", "Making running remote anim: " + processRecord);
                }
                i5 = topProcessState;
                i6 = 3;
                z4 = true;
                i4 = 100;
            } else {
                if (processRecord.getActiveInstrumentation() != null) {
                    processStateRecord8.setAdjType("instrumentation");
                    curCapability3 |= 16;
                    if (i66 == i65) {
                        reportOomAdjMessageLocked("ActivityManager", "Making instrumentation: " + processRecord);
                    }
                    z4 = false;
                    i4 = 0;
                    z5 = false;
                    z6 = false;
                    i5 = 4;
                    i6 = 2;
                    z7 = false;
                    if (z5 && processStateRecord8.getCachedHasActivities()) {
                        processServiceRecord = processServiceRecord8;
                        i7 = curCapability2;
                        processStateRecord8.computeOomAdjFromActivitiesIfNecessary(this.mTmpComputeOomAdjWindowCallback, i4, z5, z6, i5, i6, i65, i66, topProcessState);
                        i4 = processStateRecord8.getCachedAdj();
                        z5 = processStateRecord8.getCachedForegroundActivities();
                        z6 = processStateRecord8.getCachedHasVisibleActivities();
                        i5 = processStateRecord8.getCachedProcState();
                        i6 = processStateRecord8.getCachedSchedGroup();
                    } else {
                        processServiceRecord = processServiceRecord8;
                        i7 = curCapability2;
                    }
                    i8 = i4;
                    boolean z16 = z5;
                    boolean z17 = z6;
                    i9 = i5;
                    boolean z18 = z4;
                    if (i9 > 18 && processStateRecord8.getCachedHasRecentTasks()) {
                        processStateRecord8.setAdjType("cch-rec");
                        if (i66 == i65) {
                            reportOomAdjMessageLocked("ActivityManager", "Raise procstate to cached recent: " + processRecord);
                        }
                        i9 = 18;
                    }
                    hasForegroundServices = processServiceRecord.hasForegroundServices();
                    boolean hasNonShortForegroundServices = processServiceRecord.hasNonShortForegroundServices();
                    if (hasForegroundServices || processServiceRecord.areAllShortForegroundServicesProcstateTimedOut(j)) {
                        i10 = i7;
                        z8 = false;
                    } else {
                        i10 = i7;
                        z8 = true;
                    }
                    String str19 = "Raise to ";
                    if (i8 > 200) {
                        i11 = 4;
                        if (i9 <= 4) {
                            i14 = 0;
                            if (processServiceRecord.hasForegroundServices() || i8 <= 50) {
                                i15 = i8;
                            } else {
                                i15 = i8;
                                if (processStateRecord8.getLastTopTime() + this.mConstants.TOP_TO_FGS_GRACE_DURATION > j || processStateRecord8.getSetProcState() <= 2) {
                                    if (processServiceRecord.hasNonShortForegroundServices()) {
                                        processStateRecord8.setAdjType("fg-service-act");
                                        i63 = 50;
                                    } else {
                                        processStateRecord8.setAdjType("fg-service-short-act");
                                        i63 = 51;
                                    }
                                    i16 = i63;
                                    if (i66 == i65) {
                                        reportOomAdjMessageLocked("ActivityManager", "Raise to recent fg: " + processRecord);
                                    }
                                    if (processServiceRecord.hasTopStartedAlmostPerceptibleServices() || i16 <= 52) {
                                        i17 = i16;
                                    } else {
                                        i17 = i16;
                                        if (processStateRecord8.getLastTopTime() + this.mConstants.TOP_TO_ALMOST_PERCEPTIBLE_GRACE_DURATION > j || processStateRecord8.getSetProcState() <= 2) {
                                            processStateRecord8.setAdjType("top-ej-act");
                                            if (i66 == i65) {
                                                reportOomAdjMessageLocked("ActivityManager", "Raise to recent fg for EJ: " + processRecord);
                                            }
                                            i17 = 52;
                                        }
                                    }
                                    if ((i17 <= 200 || i9 > 8) && processStateRecord8.getForcingToImportant() != null) {
                                        processStateRecord8.setCached(false);
                                        processStateRecord8.setAdjType("force-imp");
                                        processStateRecord8.setAdjSource(processStateRecord8.getForcingToImportant());
                                        if (i66 == i65) {
                                            reportOomAdjMessageLocked("ActivityManager", "Raise to force imp: " + processRecord);
                                        }
                                        i9 = 8;
                                        i17 = 200;
                                        i6 = 2;
                                    }
                                    if (processStateRecord8.getCachedIsHeavyWeight()) {
                                        if (i17 > 400) {
                                            processStateRecord8.setCached(false);
                                            processStateRecord8.setAdjType("heavy");
                                            if (i66 == i65) {
                                                reportOomAdjMessageLocked("ActivityManager", "Raise adj to heavy: " + processRecord);
                                            }
                                            i17 = 400;
                                            i6 = 0;
                                        }
                                        if (i9 > 13) {
                                            processStateRecord8.setAdjType("heavy");
                                            if (i66 == i65) {
                                                reportOomAdjMessageLocked("ActivityManager", "Raise procstate to heavy: " + processRecord);
                                            }
                                            i9 = 13;
                                        }
                                    }
                                    if (processStateRecord8.getCachedIsHomeProcess()) {
                                        if (i17 > 600) {
                                            processStateRecord8.setCached(false);
                                            processStateRecord8.setAdjType("home");
                                            this.mService.currentLauncherName = processRecord.info.packageName;
                                            if (i66 == i65) {
                                                reportOomAdjMessageLocked("ActivityManager", "Raise adj to home: " + processRecord);
                                            }
                                            i17 = 600;
                                            i6 = 0;
                                        }
                                        if (i9 > 14) {
                                            processStateRecord8.setAdjType("home");
                                            if (i66 == i65) {
                                                reportOomAdjMessageLocked("ActivityManager", "Raise procstate to home: " + processRecord);
                                            }
                                            i9 = 14;
                                        }
                                    }
                                    String str20 = ": ";
                                    if (processStateRecord8.getCachedIsPreviousProcess() && processStateRecord8.getCachedHasActivities()) {
                                        if (i9 < 15 && processStateRecord8.getSetProcState() == 15 && processStateRecord8.getLastStateTime() + ActivityManagerConstants.MAX_PREVIOUS_TIME < j) {
                                            processStateRecord8.setAdjType("previous-expired");
                                            if (i66 == i65) {
                                                reportOomAdjMessageLocked("ActivityManager", "Expire prev adj: " + processRecord);
                                            }
                                            i9 = 15;
                                            i18 = 0;
                                            i17 = 900;
                                            if (z2) {
                                                i9 = Math.min(i9, processStateRecord8.getCurRawProcState());
                                                i17 = Math.min(i17, processStateRecord8.getCurRawAdj());
                                                i18 = Math.max(i18, processStateRecord8.getCurrentSchedulingGroup());
                                            }
                                            processStateRecord8.setCurRawAdj(i17);
                                            processStateRecord8.setCurRawProcState(i9);
                                            processStateRecord8.setHasStartedServices(false);
                                            processStateRecord8.setAdjSeq(this.mAdjSeq);
                                            backupRecord = (BackupRecord) this.mService.mBackupTargets.get(processRecord.userId);
                                            if (backupRecord == null && processRecord == backupRecord.app) {
                                                if (i17 > 300) {
                                                    if (i9 > 8) {
                                                        i9 = 8;
                                                    }
                                                    processStateRecord8.setAdjType("backup");
                                                    if (i66 == i65) {
                                                        reportOomAdjMessageLocked("ActivityManager", "Raise adj to backup: " + processRecord);
                                                    }
                                                    processStateRecord8.setCached(false);
                                                    i17 = 300;
                                                }
                                                if (i9 > 9) {
                                                    processStateRecord8.setAdjType("backup");
                                                    if (i66 == i65) {
                                                        reportOomAdjMessageLocked("ActivityManager", "Raise procstate to backup: " + processRecord);
                                                    }
                                                    i9 = 9;
                                                }
                                                z18 = false;
                                            }
                                            boolean isCurBoundByNonBgRestrictedApp = processStateRecord8.isCurBoundByNonBgRestrictedApp();
                                            i19 = i17;
                                            numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1;
                                            int i68 = i14;
                                            z9 = false;
                                            z10 = isCurBoundByNonBgRestrictedApp;
                                            while (numberOfRunningServices >= 0 && (i19 > 0 || i18 == 0 || i9 > 2)) {
                                                runningServiceAt = processServiceRecord.getRunningServiceAt(numberOfRunningServices);
                                                int i69 = i18;
                                                if (runningServiceAt.startRequested) {
                                                    processStateRecord8.setHasStartedServices(true);
                                                    if (i9 > 10) {
                                                        processStateRecord8.setAdjType("started-services");
                                                        if (i66 == i65) {
                                                            reportOomAdjMessageLocked(str18, "Raise procstate to started service: " + processRecord);
                                                        }
                                                        i9 = 10;
                                                    }
                                                    if (runningServiceAt.mKeepWarming || !processStateRecord8.hasShownUi() || processStateRecord8.getCachedIsHomeProcess()) {
                                                        if (runningServiceAt.mKeepWarming) {
                                                            i61 = i9;
                                                            processServiceRecord4 = processServiceRecord;
                                                            str10 = str19;
                                                            i35 = numberOfRunningServices;
                                                        } else {
                                                            processServiceRecord4 = processServiceRecord;
                                                            i61 = i9;
                                                            str10 = str19;
                                                            i35 = numberOfRunningServices;
                                                        }
                                                        if (i19 > 500) {
                                                            processStateRecord8.setAdjType("started-services");
                                                            if (i66 == i65) {
                                                                reportOomAdjMessageLocked(str18, "Raise adj to started service: " + processRecord);
                                                            }
                                                            i36 = 0;
                                                            processStateRecord8.setCached(false);
                                                            i62 = 500;
                                                            i19 = 500;
                                                            if (i19 > i62) {
                                                                processStateRecord8.setAdjType("cch-started-services");
                                                            }
                                                            i9 = i61;
                                                            if (runningServiceAt.isForeground) {
                                                                int i70 = runningServiceAt.foregroundServiceType;
                                                                if (runningServiceAt.mAllowWhileInUsePermissionInFgs) {
                                                                    int i71 = i68 | ((i70 & 8) != 0 ? 1 : i36);
                                                                    if (processStateRecord8.getCachedCompatChange(1)) {
                                                                        i60 = ((i70 & 128) != 0 ? 4 : i36) | i71 | ((i70 & 64) != 0 ? 2 : i36);
                                                                    } else {
                                                                        i60 = i71 | 6;
                                                                    }
                                                                    i68 = i60;
                                                                }
                                                            }
                                                            connections = runningServiceAt.getConnections();
                                                            i37 = curCapability3;
                                                            size = connections.size() - 1;
                                                            i18 = i69;
                                                            while (true) {
                                                                if (size >= 0) {
                                                                    i38 = i65;
                                                                    str11 = str18;
                                                                    i39 = i2;
                                                                    i40 = i3;
                                                                    str12 = str20;
                                                                    processServiceRecord5 = processServiceRecord4;
                                                                    str19 = str10;
                                                                    ProcessStateRecord processStateRecord9 = processStateRecord8;
                                                                    i41 = i66;
                                                                    processStateRecord4 = processStateRecord9;
                                                                    break;
                                                                }
                                                                if (i19 <= 0 && i18 != 0 && i9 <= 2) {
                                                                    i38 = i65;
                                                                    str11 = str18;
                                                                    processStateRecord4 = processStateRecord8;
                                                                    i39 = i2;
                                                                    i40 = i3;
                                                                    str12 = str20;
                                                                    processServiceRecord5 = processServiceRecord4;
                                                                    str19 = str10;
                                                                    i41 = i66;
                                                                    break;
                                                                }
                                                                boolean z19 = z9;
                                                                boolean z20 = z10;
                                                                int i72 = i37;
                                                                int i73 = i18;
                                                                int i74 = i9;
                                                                int i75 = i19;
                                                                int i76 = 0;
                                                                for (ArrayList arrayList2 = (ArrayList) connections.valueAt(size); i76 < arrayList2.size(); arrayList2 = arrayList) {
                                                                    if (i75 <= 0 && i73 != 0) {
                                                                        if (i74 <= 2) {
                                                                            break;
                                                                        }
                                                                    }
                                                                    ConnectionRecord connectionRecord3 = (ConnectionRecord) arrayList2.get(i76);
                                                                    int i77 = i74;
                                                                    AppBindRecord appBindRecord = connectionRecord3.binding;
                                                                    int i78 = i76;
                                                                    ProcessRecord processRecord8 = appBindRecord.client;
                                                                    if (processRecord8 == processRecord) {
                                                                        i52 = i65;
                                                                        i50 = i75;
                                                                        arrayList = arrayList2;
                                                                        i44 = size;
                                                                        str16 = str18;
                                                                        processStateRecord7 = processStateRecord8;
                                                                        arrayMap = connections;
                                                                        i74 = i77;
                                                                        i46 = i2;
                                                                        i47 = i3;
                                                                        processServiceRecord7 = processServiceRecord4;
                                                                        str15 = str10;
                                                                        i48 = i78;
                                                                        i51 = i66;
                                                                    } else {
                                                                        ProcessRecord processRecord9 = (!processRecord.isSdkSandbox || (processRecord7 = appBindRecord.attributedClient) == null) ? processRecord8 : processRecord7;
                                                                        ProcessStateRecord processStateRecord10 = processRecord9.mState;
                                                                        if (z3) {
                                                                            i46 = i2;
                                                                            processRecord4 = processRecord9;
                                                                            i47 = i3;
                                                                            i48 = i78;
                                                                            i42 = i66;
                                                                            i43 = i65;
                                                                            i49 = i75;
                                                                            str14 = null;
                                                                            arrayList = arrayList2;
                                                                            i44 = size;
                                                                            processServiceRecord6 = processServiceRecord4;
                                                                            str13 = str18;
                                                                            arrayMap = connections;
                                                                            i45 = i67;
                                                                            processStateRecord5 = processStateRecord8;
                                                                            computeOomAdjLSP(processRecord9, i, processRecord2, z, j, z2, true);
                                                                            processStateRecord6 = processStateRecord10;
                                                                        } else {
                                                                            processRecord4 = processRecord9;
                                                                            i42 = i66;
                                                                            i43 = i65;
                                                                            arrayList = arrayList2;
                                                                            i44 = size;
                                                                            str13 = str18;
                                                                            arrayMap = connections;
                                                                            i45 = i67;
                                                                            i46 = i2;
                                                                            i47 = i3;
                                                                            processServiceRecord6 = processServiceRecord4;
                                                                            i48 = i78;
                                                                            str14 = null;
                                                                            i49 = i75;
                                                                            processStateRecord5 = processStateRecord8;
                                                                            processStateRecord6 = processStateRecord10;
                                                                            processStateRecord6.setCurRawAdj(processStateRecord10.getCurAdj());
                                                                            processStateRecord6.setCurRawProcState(processStateRecord6.getCurProcState());
                                                                        }
                                                                        int curRawAdj = processStateRecord6.getCurRawAdj();
                                                                        int curRawProcState = processStateRecord6.getCurRawProcState();
                                                                        boolean z21 = curRawProcState < 2;
                                                                        if (!processStateRecord6.isCurBoundByNonBgRestrictedApp() && curRawProcState > i45) {
                                                                            if (curRawProcState != 4 || processStateRecord6.isBackgroundRestricted()) {
                                                                                z12 = false;
                                                                                boolean z22 = z20 | z12;
                                                                                processRecord5 = processRecord4;
                                                                                if (!processRecord5.mOptRecord.shouldNotFreeze()) {
                                                                                    processRecord.mOptRecord.setShouldNotFreeze(true);
                                                                                }
                                                                                int bfslCapabilityFromClient = i72 | getBfslCapabilityFromClient(processRecord5);
                                                                                if (connectionRecord3.notHasFlag(32)) {
                                                                                    connectionRecord = connectionRecord3;
                                                                                    processStateRecord7 = processStateRecord5;
                                                                                    i50 = i49;
                                                                                    str15 = str10;
                                                                                    i51 = i42;
                                                                                    i52 = i43;
                                                                                    str16 = str13;
                                                                                    if (curRawAdj < 900) {
                                                                                        z13 = true;
                                                                                        processRecord.mOptRecord.setShouldNotFreeze(true);
                                                                                    } else {
                                                                                        z13 = true;
                                                                                    }
                                                                                    i72 = bfslCapabilityFromClient;
                                                                                    i74 = i77;
                                                                                } else {
                                                                                    if (connectionRecord3.hasFlag(IInstalld.FLAG_USE_QUOTA)) {
                                                                                        bfslCapabilityFromClient |= processStateRecord6.getCurCapability();
                                                                                    }
                                                                                    if ((processStateRecord6.getCurCapability() & 8) != 0 && (curRawProcState > 5 || connectionRecord3.hasFlag(IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES))) {
                                                                                        bfslCapabilityFromClient |= 8;
                                                                                    }
                                                                                    if ((processStateRecord6.getCurCapability() & 32) == 0 || curRawProcState > 6) {
                                                                                        processRecord6 = processRecord5;
                                                                                    } else {
                                                                                        processRecord6 = processRecord5;
                                                                                        if (connectionRecord3.hasFlag(4294967296L)) {
                                                                                            bfslCapabilityFromClient |= 32;
                                                                                        }
                                                                                    }
                                                                                    i72 = bfslCapabilityFromClient;
                                                                                    ProcessRecord processRecord10 = processRecord6;
                                                                                    if (shouldSkipDueToCycle(processRecord, processStateRecord6, i77, i49, z2)) {
                                                                                        processStateRecord7 = processStateRecord5;
                                                                                        i74 = i77;
                                                                                        z20 = z22;
                                                                                        i50 = i49;
                                                                                        str15 = str10;
                                                                                        i51 = i42;
                                                                                        i52 = i43;
                                                                                        processServiceRecord7 = processServiceRecord6;
                                                                                        str16 = str13;
                                                                                    } else {
                                                                                        if (curRawProcState >= 16) {
                                                                                            curRawProcState = 19;
                                                                                        }
                                                                                        if (connectionRecord3.hasFlag(16)) {
                                                                                            i53 = 900;
                                                                                            if (curRawAdj < 900) {
                                                                                                processRecord.mOptRecord.setShouldNotFreeze(true);
                                                                                            }
                                                                                            if (!processStateRecord5.hasShownUi() || processStateRecord5.getCachedIsHomeProcess()) {
                                                                                                processStateRecord7 = processStateRecord5;
                                                                                                i50 = i49;
                                                                                                connectionRecord2 = connectionRecord3;
                                                                                                if (j >= runningServiceAt.lastActivity + this.mConstants.MAX_SERVICE_INACTIVITY) {
                                                                                                    str17 = i50 > curRawAdj ? "cch-bound-services" : str14;
                                                                                                    curRawAdj = i50;
                                                                                                }
                                                                                            } else {
                                                                                                i50 = i49;
                                                                                                if (i50 > curRawAdj) {
                                                                                                    str17 = "cch-bound-ui-services";
                                                                                                    processStateRecord7 = processStateRecord5;
                                                                                                } else {
                                                                                                    processStateRecord7 = processStateRecord5;
                                                                                                    str17 = str14;
                                                                                                }
                                                                                                processStateRecord7.setCached(false);
                                                                                                curRawAdj = i50;
                                                                                                connectionRecord2 = connectionRecord3;
                                                                                                curRawProcState = i77;
                                                                                            }
                                                                                            if (i50 > curRawAdj) {
                                                                                                if (!processStateRecord7.hasShownUi() || processStateRecord7.getCachedIsHomeProcess() || curRawAdj <= 200) {
                                                                                                    connectionRecord = connectionRecord2;
                                                                                                    if (!connectionRecord.hasFlag(72)) {
                                                                                                        if (!connectionRecord.hasFlag(256) || curRawAdj > 200) {
                                                                                                            i56 = 100;
                                                                                                        } else {
                                                                                                            i56 = FrameworkStatsLog.f446x58c6c32;
                                                                                                            if (i50 >= 250) {
                                                                                                                i56 = FrameworkStatsLog.f446x58c6c32;
                                                                                                                z15 = false;
                                                                                                                if (!processStateRecord6.isCached()) {
                                                                                                                }
                                                                                                                if (i50 > i56) {
                                                                                                                }
                                                                                                                z14 = z15;
                                                                                                                i54 = i73;
                                                                                                                i74 = i77;
                                                                                                            }
                                                                                                        }
                                                                                                        if (connectionRecord.hasFlag(65536)) {
                                                                                                            i57 = 4;
                                                                                                            if (connectionRecord.notHasFlag(4) && curRawAdj < 200) {
                                                                                                                if (i50 >= 200) {
                                                                                                                    i56 = 201;
                                                                                                                    z15 = false;
                                                                                                                    if (!processStateRecord6.isCached()) {
                                                                                                                    }
                                                                                                                    if (i50 > i56) {
                                                                                                                    }
                                                                                                                    z14 = z15;
                                                                                                                    i54 = i73;
                                                                                                                    i74 = i77;
                                                                                                                } else {
                                                                                                                    i56 = 200;
                                                                                                                }
                                                                                                            }
                                                                                                        } else {
                                                                                                            i57 = 4;
                                                                                                        }
                                                                                                        if (connectionRecord.hasFlag(65536) && connectionRecord.hasFlag(i57) && curRawAdj < 200) {
                                                                                                            i56 = FrameworkStatsLog.CAMERA_ACTION_EVENT;
                                                                                                            if (i50 >= 227) {
                                                                                                                i56 = FrameworkStatsLog.CAMERA_ACTION_EVENT;
                                                                                                                z15 = false;
                                                                                                                if (!processStateRecord6.isCached()) {
                                                                                                                }
                                                                                                                if (i50 > i56) {
                                                                                                                }
                                                                                                                z14 = z15;
                                                                                                                i54 = i73;
                                                                                                                i74 = i77;
                                                                                                            }
                                                                                                        }
                                                                                                        if (connectionRecord.hasFlag(1073741824)) {
                                                                                                            i58 = 200;
                                                                                                            if (curRawAdj < 200) {
                                                                                                                i56 = 200;
                                                                                                            }
                                                                                                        } else {
                                                                                                            i58 = 200;
                                                                                                        }
                                                                                                        if (curRawAdj < i58) {
                                                                                                            if (connectionRecord.hasFlag(268435456)) {
                                                                                                                i59 = 100;
                                                                                                                if (curRawAdj <= 100 && i50 > 100) {
                                                                                                                    i56 = 100;
                                                                                                                    z15 = false;
                                                                                                                    if (!processStateRecord6.isCached()) {
                                                                                                                    }
                                                                                                                    if (i50 > i56) {
                                                                                                                    }
                                                                                                                    z14 = z15;
                                                                                                                    i54 = i73;
                                                                                                                    i74 = i77;
                                                                                                                }
                                                                                                            } else {
                                                                                                                i59 = 100;
                                                                                                            }
                                                                                                            i56 = i50 > i59 ? Math.max(curRawAdj, i56) : i50;
                                                                                                            z15 = false;
                                                                                                            if (!processStateRecord6.isCached()) {
                                                                                                            }
                                                                                                            if (i50 > i56) {
                                                                                                            }
                                                                                                            z14 = z15;
                                                                                                            i54 = i73;
                                                                                                            i74 = i77;
                                                                                                        }
                                                                                                        i56 = curRawAdj;
                                                                                                        z15 = false;
                                                                                                        if (!processStateRecord6.isCached()) {
                                                                                                        }
                                                                                                        if (i50 > i56) {
                                                                                                        }
                                                                                                        z14 = z15;
                                                                                                        i54 = i73;
                                                                                                        i74 = i77;
                                                                                                    } else if (curRawAdj < -700) {
                                                                                                        connectionRecord.trackProcState(0, this.mAdjSeq);
                                                                                                        i56 = -700;
                                                                                                        z15 = true;
                                                                                                        i73 = 2;
                                                                                                        i77 = 0;
                                                                                                        if (!processStateRecord6.isCached()) {
                                                                                                        }
                                                                                                        if (i50 > i56) {
                                                                                                        }
                                                                                                        z14 = z15;
                                                                                                        i54 = i73;
                                                                                                        i74 = i77;
                                                                                                    } else {
                                                                                                        i56 = curRawAdj;
                                                                                                        z15 = false;
                                                                                                        if (!processStateRecord6.isCached()) {
                                                                                                            processStateRecord7.setCached(false);
                                                                                                        }
                                                                                                        if (i50 > i56) {
                                                                                                            processStateRecord7.setCurRawAdj(i56);
                                                                                                            str17 = "service";
                                                                                                            i50 = i56;
                                                                                                        }
                                                                                                        z14 = z15;
                                                                                                        i54 = i73;
                                                                                                        i74 = i77;
                                                                                                    }
                                                                                                    if (!connectionRecord.notHasFlag(8388612)) {
                                                                                                        int currentSchedulingGroup = processStateRecord6.getCurrentSchedulingGroup();
                                                                                                        if (currentSchedulingGroup > i54) {
                                                                                                            i54 = connectionRecord.hasFlag(64) ? currentSchedulingGroup : 2;
                                                                                                        }
                                                                                                        i55 = 2;
                                                                                                        if (curRawProcState < 2) {
                                                                                                            if (connectionRecord.hasFlag(268435456)) {
                                                                                                                curRawProcState = 4;
                                                                                                            } else {
                                                                                                                curRawProcState = (connectionRecord.hasFlag(67108864) || (this.mService.mWakefulness.get() == 1 && connectionRecord.hasFlag(33554432))) ? 5 : 6;
                                                                                                            }
                                                                                                        } else if (curRawProcState == 2) {
                                                                                                            if (processStateRecord6.getCachedCompatChange(0)) {
                                                                                                                if (connectionRecord.hasFlag(IInstalld.FLAG_USE_QUOTA)) {
                                                                                                                    curCapability = processStateRecord6.getCurCapability();
                                                                                                                }
                                                                                                                curRawProcState = 3;
                                                                                                            } else {
                                                                                                                curCapability = processStateRecord6.getCurCapability();
                                                                                                            }
                                                                                                            i72 |= curCapability;
                                                                                                            curRawProcState = 3;
                                                                                                        }
                                                                                                    } else {
                                                                                                        i55 = 2;
                                                                                                        if (connectionRecord.notHasFlag(8388608)) {
                                                                                                            if (curRawProcState < 8) {
                                                                                                                curRawProcState = 8;
                                                                                                            }
                                                                                                        } else if (curRawProcState < 7) {
                                                                                                            curRawProcState = 7;
                                                                                                        }
                                                                                                    }
                                                                                                    if (i54 < 3 && connectionRecord.hasFlag(524288) && z21) {
                                                                                                        i54 = 3;
                                                                                                        z19 = true;
                                                                                                    }
                                                                                                    if (!z14) {
                                                                                                        connectionRecord.trackProcState(curRawProcState, this.mAdjSeq);
                                                                                                    }
                                                                                                    if (i74 > curRawProcState) {
                                                                                                        processStateRecord7.setCurRawProcState(curRawProcState);
                                                                                                        if (str17 == null) {
                                                                                                            str17 = "service";
                                                                                                        }
                                                                                                        i74 = curRawProcState;
                                                                                                    }
                                                                                                    if (i74 < 7 && connectionRecord.hasFlag(536870912)) {
                                                                                                        processRecord.setPendingUiClean(true);
                                                                                                    }
                                                                                                    if (str17 == null) {
                                                                                                        processStateRecord7.setAdjType(str17);
                                                                                                        processStateRecord7.setAdjTypeCode(i55);
                                                                                                        processStateRecord7.setAdjSource(processRecord10);
                                                                                                        processStateRecord7.setAdjSourceProcState(curRawProcState);
                                                                                                        processStateRecord7.setAdjTarget(runningServiceAt.instanceName);
                                                                                                        i51 = i42;
                                                                                                        i52 = i43;
                                                                                                        if (i51 == i52) {
                                                                                                            StringBuilder sb2 = new StringBuilder();
                                                                                                            str15 = str10;
                                                                                                            sb2.append(str15);
                                                                                                            sb2.append(str17);
                                                                                                            sb2.append(str20);
                                                                                                            sb2.append(processRecord);
                                                                                                            sb2.append(", due to ");
                                                                                                            sb2.append(processRecord10);
                                                                                                            sb2.append(" adj=");
                                                                                                            sb2.append(i50);
                                                                                                            sb2.append(" procState=");
                                                                                                            sb2.append(ProcessList.makeProcStateString(i74));
                                                                                                            str16 = str13;
                                                                                                            reportOomAdjMessageLocked(str16, sb2.toString());
                                                                                                            i73 = i54;
                                                                                                            z13 = true;
                                                                                                        } else {
                                                                                                            str15 = str10;
                                                                                                        }
                                                                                                    } else {
                                                                                                        str15 = str10;
                                                                                                        i51 = i42;
                                                                                                        i52 = i43;
                                                                                                    }
                                                                                                    str16 = str13;
                                                                                                    i73 = i54;
                                                                                                    z13 = true;
                                                                                                } else if (i50 >= i53) {
                                                                                                    str17 = "cch-bound-ui-services";
                                                                                                    i54 = i73;
                                                                                                    connectionRecord = connectionRecord2;
                                                                                                    i74 = i77;
                                                                                                    z14 = false;
                                                                                                    if (!connectionRecord.notHasFlag(8388612)) {
                                                                                                    }
                                                                                                    if (i54 < 3) {
                                                                                                        i54 = 3;
                                                                                                        z19 = true;
                                                                                                    }
                                                                                                    if (!z14) {
                                                                                                    }
                                                                                                    if (i74 > curRawProcState) {
                                                                                                    }
                                                                                                    if (i74 < 7) {
                                                                                                        processRecord.setPendingUiClean(true);
                                                                                                    }
                                                                                                    if (str17 == null) {
                                                                                                    }
                                                                                                    str16 = str13;
                                                                                                    i73 = i54;
                                                                                                    z13 = true;
                                                                                                }
                                                                                            }
                                                                                            connectionRecord = connectionRecord2;
                                                                                            i54 = i73;
                                                                                            i74 = i77;
                                                                                            z14 = false;
                                                                                            if (!connectionRecord.notHasFlag(8388612)) {
                                                                                            }
                                                                                            if (i54 < 3) {
                                                                                            }
                                                                                            if (!z14) {
                                                                                            }
                                                                                            if (i74 > curRawProcState) {
                                                                                            }
                                                                                            if (i74 < 7) {
                                                                                            }
                                                                                            if (str17 == null) {
                                                                                            }
                                                                                            str16 = str13;
                                                                                            i73 = i54;
                                                                                            z13 = true;
                                                                                        } else {
                                                                                            processStateRecord7 = processStateRecord5;
                                                                                            i50 = i49;
                                                                                            i53 = 900;
                                                                                            connectionRecord2 = connectionRecord3;
                                                                                        }
                                                                                        str17 = str14;
                                                                                        if (i50 > curRawAdj) {
                                                                                        }
                                                                                        connectionRecord = connectionRecord2;
                                                                                        i54 = i73;
                                                                                        i74 = i77;
                                                                                        z14 = false;
                                                                                        if (!connectionRecord.notHasFlag(8388612)) {
                                                                                        }
                                                                                        if (i54 < 3) {
                                                                                        }
                                                                                        if (!z14) {
                                                                                        }
                                                                                        if (i74 > curRawProcState) {
                                                                                        }
                                                                                        if (i74 < 7) {
                                                                                        }
                                                                                        if (str17 == null) {
                                                                                        }
                                                                                        str16 = str13;
                                                                                        i73 = i54;
                                                                                        z13 = true;
                                                                                    }
                                                                                }
                                                                                if (connectionRecord.hasFlag(134217728)) {
                                                                                    processServiceRecord7 = processServiceRecord6;
                                                                                } else {
                                                                                    processServiceRecord7 = processServiceRecord6;
                                                                                    processServiceRecord7.setTreatLikeActivity(z13);
                                                                                }
                                                                                ActivityServiceConnectionsHolder activityServiceConnectionsHolder = connectionRecord.activity;
                                                                                if (!connectionRecord.hasFlag(128) && activityServiceConnectionsHolder != null && i50 > 0 && activityServiceConnectionsHolder.isActivityVisible()) {
                                                                                    processStateRecord7.setCurRawAdj(0);
                                                                                    if (connectionRecord.notHasFlag(4)) {
                                                                                        i73 = connectionRecord.hasFlag(64) ? 4 : 2;
                                                                                    }
                                                                                    processStateRecord7.setCached(false);
                                                                                    processStateRecord7.setAdjType("service");
                                                                                    processStateRecord7.setAdjTypeCode(2);
                                                                                    processStateRecord7.setAdjSource(activityServiceConnectionsHolder);
                                                                                    processStateRecord7.setAdjSourceProcState(i74);
                                                                                    processStateRecord7.setAdjTarget(runningServiceAt.instanceName);
                                                                                    if (i51 == i52) {
                                                                                        reportOomAdjMessageLocked(str16, "Raise to service w/activity: " + processRecord);
                                                                                    }
                                                                                    i50 = 0;
                                                                                }
                                                                                z20 = z22;
                                                                                processServiceRecord4 = processServiceRecord7;
                                                                                str18 = str16;
                                                                                i66 = i51;
                                                                                str10 = str15;
                                                                                size = i44;
                                                                                connections = arrayMap;
                                                                                i2 = i46;
                                                                                i3 = i47;
                                                                                i67 = 3;
                                                                                i75 = i50;
                                                                                i76 = i48 + 1;
                                                                                processStateRecord8 = processStateRecord7;
                                                                                i65 = i52;
                                                                            }
                                                                        }
                                                                        z12 = true;
                                                                        boolean z222 = z20 | z12;
                                                                        processRecord5 = processRecord4;
                                                                        if (!processRecord5.mOptRecord.shouldNotFreeze()) {
                                                                        }
                                                                        int bfslCapabilityFromClient2 = i72 | getBfslCapabilityFromClient(processRecord5);
                                                                        if (connectionRecord3.notHasFlag(32)) {
                                                                        }
                                                                        if (connectionRecord.hasFlag(134217728)) {
                                                                        }
                                                                        ActivityServiceConnectionsHolder activityServiceConnectionsHolder2 = connectionRecord.activity;
                                                                        if (!connectionRecord.hasFlag(128)) {
                                                                        }
                                                                        z20 = z222;
                                                                        processServiceRecord4 = processServiceRecord7;
                                                                        str18 = str16;
                                                                        i66 = i51;
                                                                        str10 = str15;
                                                                        size = i44;
                                                                        connections = arrayMap;
                                                                        i2 = i46;
                                                                        i3 = i47;
                                                                        i67 = 3;
                                                                        i75 = i50;
                                                                        i76 = i48 + 1;
                                                                        processStateRecord8 = processStateRecord7;
                                                                        i65 = i52;
                                                                    }
                                                                    processServiceRecord4 = processServiceRecord7;
                                                                    str18 = str16;
                                                                    i66 = i51;
                                                                    str10 = str15;
                                                                    size = i44;
                                                                    connections = arrayMap;
                                                                    i2 = i46;
                                                                    i3 = i47;
                                                                    i67 = 3;
                                                                    i75 = i50;
                                                                    i76 = i48 + 1;
                                                                    processStateRecord8 = processStateRecord7;
                                                                    i65 = i52;
                                                                }
                                                                i19 = i75;
                                                                processServiceRecord4 = processServiceRecord4;
                                                                str18 = str18;
                                                                str10 = str10;
                                                                i65 = i65;
                                                                i9 = i74;
                                                                connections = connections;
                                                                i2 = i2;
                                                                i3 = i3;
                                                                i67 = 3;
                                                                size--;
                                                                i18 = i73;
                                                                i37 = i72;
                                                                z10 = z20;
                                                                z9 = z19;
                                                                i36 = 0;
                                                                i66 = i66;
                                                                processStateRecord8 = processStateRecord8;
                                                            }
                                                            str20 = str12;
                                                            i66 = i41;
                                                            i65 = i38;
                                                            i2 = i39;
                                                            i3 = i40;
                                                            i67 = 3;
                                                            processStateRecord8 = processStateRecord4;
                                                            processServiceRecord = processServiceRecord5;
                                                            numberOfRunningServices = i35 - 1;
                                                            str18 = str11;
                                                            curCapability3 = i37;
                                                        }
                                                        i36 = 0;
                                                        i62 = 500;
                                                        if (i19 > i62) {
                                                        }
                                                        i9 = i61;
                                                        if (runningServiceAt.isForeground) {
                                                        }
                                                        connections = runningServiceAt.getConnections();
                                                        i37 = curCapability3;
                                                        size = connections.size() - 1;
                                                        i18 = i69;
                                                        while (true) {
                                                            if (size >= 0) {
                                                            }
                                                            i19 = i75;
                                                            processServiceRecord4 = processServiceRecord4;
                                                            str18 = str18;
                                                            str10 = str10;
                                                            i65 = i65;
                                                            i9 = i74;
                                                            connections = connections;
                                                            i2 = i2;
                                                            i3 = i3;
                                                            i67 = 3;
                                                            size--;
                                                            i18 = i73;
                                                            i37 = i72;
                                                            z10 = z20;
                                                            z9 = z19;
                                                            i36 = 0;
                                                            i66 = i66;
                                                            processStateRecord8 = processStateRecord8;
                                                        }
                                                        str20 = str12;
                                                        i66 = i41;
                                                        i65 = i38;
                                                        i2 = i39;
                                                        i3 = i40;
                                                        i67 = 3;
                                                        processStateRecord8 = processStateRecord4;
                                                        processServiceRecord = processServiceRecord5;
                                                        numberOfRunningServices = i35 - 1;
                                                        str18 = str11;
                                                        curCapability3 = i37;
                                                    } else if (i19 > 500) {
                                                        processStateRecord8.setAdjType("cch-started-ui-services");
                                                    }
                                                }
                                                processServiceRecord4 = processServiceRecord;
                                                str10 = str19;
                                                i35 = numberOfRunningServices;
                                                i36 = 0;
                                                if (runningServiceAt.isForeground) {
                                                }
                                                connections = runningServiceAt.getConnections();
                                                i37 = curCapability3;
                                                size = connections.size() - 1;
                                                i18 = i69;
                                                while (true) {
                                                    if (size >= 0) {
                                                    }
                                                    i19 = i75;
                                                    processServiceRecord4 = processServiceRecord4;
                                                    str18 = str18;
                                                    str10 = str10;
                                                    i65 = i65;
                                                    i9 = i74;
                                                    connections = connections;
                                                    i2 = i2;
                                                    i3 = i3;
                                                    i67 = 3;
                                                    size--;
                                                    i18 = i73;
                                                    i37 = i72;
                                                    z10 = z20;
                                                    z9 = z19;
                                                    i36 = 0;
                                                    i66 = i66;
                                                    processStateRecord8 = processStateRecord8;
                                                }
                                                str20 = str12;
                                                i66 = i41;
                                                i65 = i38;
                                                i2 = i39;
                                                i3 = i40;
                                                i67 = 3;
                                                processStateRecord8 = processStateRecord4;
                                                processServiceRecord = processServiceRecord5;
                                                numberOfRunningServices = i35 - 1;
                                                str18 = str11;
                                                curCapability3 = i37;
                                            }
                                            i20 = i18;
                                            int i79 = i65;
                                            String str21 = str18;
                                            int i80 = i2;
                                            i21 = i3;
                                            String str22 = str20;
                                            ProcessServiceRecord processServiceRecord9 = processServiceRecord;
                                            ProcessStateRecord processStateRecord11 = processStateRecord8;
                                            ProcessProviderRecord processProviderRecord2 = processRecord.mProviders;
                                            int i81 = i66;
                                            numberOfProviders = processProviderRecord2.numberOfProviders() - 1;
                                            boolean z23 = z10;
                                            while (numberOfProviders >= 0) {
                                                if (i19 <= 0 && i20 != 0) {
                                                    str2 = str22;
                                                    if (i9 <= 2) {
                                                        break;
                                                    }
                                                } else {
                                                    str2 = str22;
                                                }
                                                ContentProviderRecord providerAt = processProviderRecord2.getProviderAt(numberOfProviders);
                                                ProcessProviderRecord processProviderRecord3 = processProviderRecord2;
                                                boolean z24 = z23;
                                                int i82 = curCapability3;
                                                int i83 = i19;
                                                int i84 = i20;
                                                for (int size2 = providerAt.connections.size() - 1; size2 >= 0 && (i83 > 0 || i84 == 0 || i9 > 2); size2 = i32 - 1) {
                                                    ContentProviderConnection contentProviderConnection2 = (ContentProviderConnection) providerAt.connections.get(size2);
                                                    int i85 = numberOfProviders;
                                                    ProcessRecord processRecord11 = contentProviderConnection2.client;
                                                    ProcessStateRecord processStateRecord12 = processRecord11.mState;
                                                    if (processRecord11 == processRecord) {
                                                        i29 = i84;
                                                        i30 = i83;
                                                        contentProviderRecord = providerAt;
                                                        i31 = i9;
                                                        processStateRecord2 = processStateRecord11;
                                                        i32 = size2;
                                                        processServiceRecord3 = processServiceRecord9;
                                                        str3 = str21;
                                                        str4 = str19;
                                                        str5 = str2;
                                                        i33 = i81;
                                                    } else {
                                                        if (z3) {
                                                            contentProviderConnection = contentProviderConnection2;
                                                            i29 = i84;
                                                            i30 = i83;
                                                            contentProviderRecord = providerAt;
                                                            str5 = str2;
                                                            i31 = i9;
                                                            processStateRecord2 = processStateRecord11;
                                                            i32 = size2;
                                                            processServiceRecord3 = processServiceRecord9;
                                                            str3 = str21;
                                                            str4 = str19;
                                                            i33 = i81;
                                                            processRecord3 = processRecord11;
                                                            computeOomAdjLSP(processRecord11, i, processRecord2, z, j, z2, true);
                                                        } else {
                                                            contentProviderConnection = contentProviderConnection2;
                                                            i29 = i84;
                                                            i30 = i83;
                                                            contentProviderRecord = providerAt;
                                                            i31 = i9;
                                                            processStateRecord2 = processStateRecord11;
                                                            i32 = size2;
                                                            processServiceRecord3 = processServiceRecord9;
                                                            str3 = str21;
                                                            str4 = str19;
                                                            str5 = str2;
                                                            i33 = i81;
                                                            processRecord3 = processRecord11;
                                                            processStateRecord12.setCurRawAdj(processStateRecord12.getCurAdj());
                                                            processStateRecord12.setCurRawProcState(processStateRecord12.getCurProcState());
                                                        }
                                                        if (!shouldSkipDueToCycle(processRecord, processStateRecord12, i31, i30, z2)) {
                                                            int curRawAdj2 = processStateRecord12.getCurRawAdj();
                                                            int curRawProcState2 = processStateRecord12.getCurRawProcState();
                                                            i82 |= getBfslCapabilityFromClient(processRecord3);
                                                            if (curRawProcState2 >= 16) {
                                                                curRawProcState2 = 19;
                                                            }
                                                            if (processRecord3.mOptRecord.shouldNotFreeze()) {
                                                                processRecord.mOptRecord.setShouldNotFreeze(true);
                                                            }
                                                            z24 |= processStateRecord12.isCurBoundByNonBgRestrictedApp() || curRawProcState2 <= 3 || (curRawProcState2 == 4 && !processStateRecord12.isBackgroundRestricted());
                                                            int i86 = i30;
                                                            if (i86 > curRawAdj2) {
                                                                if (processStateRecord2.hasShownUi() && !processStateRecord2.getCachedIsHomeProcess()) {
                                                                    if (curRawAdj2 > 200) {
                                                                        str6 = "cch-ui-provider";
                                                                        i83 = i86;
                                                                        processStateRecord3 = processStateRecord2;
                                                                        processStateRecord3.setCached(processStateRecord3.isCached() & processStateRecord12.isCached());
                                                                    }
                                                                }
                                                                i83 = Math.max(curRawAdj2, 0);
                                                                processStateRecord3 = processStateRecord2;
                                                                processStateRecord3.setCurRawAdj(i83);
                                                                str6 = "provider";
                                                                processStateRecord3.setCached(processStateRecord3.isCached() & processStateRecord12.isCached());
                                                            } else {
                                                                processStateRecord3 = processStateRecord2;
                                                                i83 = i86;
                                                                str6 = null;
                                                            }
                                                            if (curRawProcState2 <= 4) {
                                                                if (str6 == null) {
                                                                    str6 = "provider";
                                                                }
                                                                curRawProcState2 = curRawProcState2 == 2 ? 3 : 5;
                                                            }
                                                            contentProviderConnection.trackProcState(curRawProcState2, this.mAdjSeq);
                                                            i34 = i31;
                                                            if (i34 > curRawProcState2) {
                                                                processStateRecord3.setCurRawProcState(curRawProcState2);
                                                                i34 = curRawProcState2;
                                                            }
                                                            int currentSchedulingGroup2 = processStateRecord12.getCurrentSchedulingGroup();
                                                            int i87 = i29;
                                                            if (currentSchedulingGroup2 > i87) {
                                                                i87 = 2;
                                                            }
                                                            if (str6 != null) {
                                                                processStateRecord3.setAdjType(str6);
                                                                processStateRecord3.setAdjTypeCode(1);
                                                                processStateRecord3.setAdjSource(processRecord3);
                                                                processStateRecord3.setAdjSourceProcState(curRawProcState2);
                                                                contentProviderRecord2 = contentProviderRecord;
                                                                processStateRecord3.setAdjTarget(contentProviderRecord2.name);
                                                                if (i33 == i79) {
                                                                    StringBuilder sb3 = new StringBuilder();
                                                                    str7 = str4;
                                                                    sb3.append(str7);
                                                                    sb3.append(str6);
                                                                    str9 = str5;
                                                                    sb3.append(str9);
                                                                    sb3.append(processRecord);
                                                                    sb3.append(", due to ");
                                                                    sb3.append(processRecord3);
                                                                    sb3.append(" adj=");
                                                                    sb3.append(i83);
                                                                    sb3.append(" procState=");
                                                                    sb3.append(ProcessList.makeProcStateString(i34));
                                                                    str8 = str3;
                                                                    reportOomAdjMessageLocked(str8, sb3.toString());
                                                                    i84 = i87;
                                                                    processStateRecord11 = processStateRecord3;
                                                                    str2 = str9;
                                                                    i9 = i34;
                                                                    i81 = i33;
                                                                    str19 = str7;
                                                                    numberOfProviders = i85;
                                                                    processServiceRecord9 = processServiceRecord3;
                                                                    str21 = str8;
                                                                    providerAt = contentProviderRecord2;
                                                                } else {
                                                                    str7 = str4;
                                                                    str8 = str3;
                                                                }
                                                            } else {
                                                                str7 = str4;
                                                                str8 = str3;
                                                                contentProviderRecord2 = contentProviderRecord;
                                                            }
                                                            str9 = str5;
                                                            i84 = i87;
                                                            processStateRecord11 = processStateRecord3;
                                                            str2 = str9;
                                                            i9 = i34;
                                                            i81 = i33;
                                                            str19 = str7;
                                                            numberOfProviders = i85;
                                                            processServiceRecord9 = processServiceRecord3;
                                                            str21 = str8;
                                                            providerAt = contentProviderRecord2;
                                                        }
                                                    }
                                                    i83 = i30;
                                                    i34 = i31;
                                                    processStateRecord3 = processStateRecord2;
                                                    str7 = str4;
                                                    str8 = str3;
                                                    i84 = i29;
                                                    contentProviderRecord2 = contentProviderRecord;
                                                    str9 = str5;
                                                    processStateRecord11 = processStateRecord3;
                                                    str2 = str9;
                                                    i9 = i34;
                                                    i81 = i33;
                                                    str19 = str7;
                                                    numberOfProviders = i85;
                                                    processServiceRecord9 = processServiceRecord3;
                                                    str21 = str8;
                                                    providerAt = contentProviderRecord2;
                                                }
                                                int i88 = i84;
                                                i19 = i83;
                                                ContentProviderRecord contentProviderRecord3 = providerAt;
                                                ProcessStateRecord processStateRecord13 = processStateRecord11;
                                                ProcessServiceRecord processServiceRecord10 = processServiceRecord9;
                                                int i89 = numberOfProviders;
                                                String str23 = str21;
                                                String str24 = str19;
                                                int i90 = i81;
                                                int i91 = i9;
                                                String str25 = str2;
                                                if (contentProviderRecord3.hasExternalProcessHandles()) {
                                                    if (i19 > 0) {
                                                        processStateRecord13.setCurRawAdj(0);
                                                        processStateRecord13.setCached(false);
                                                        processStateRecord13.setAdjType("ext-provider");
                                                        processStateRecord13.setAdjTarget(contentProviderRecord3.name);
                                                        if (i90 == i79) {
                                                            reportOomAdjMessageLocked(str23, "Raise adj to external provider: " + processRecord);
                                                        }
                                                        i19 = 0;
                                                        i88 = 2;
                                                    }
                                                    if (i91 > 6) {
                                                        processStateRecord13.setCurRawProcState(6);
                                                        if (i90 == i79) {
                                                            reportOomAdjMessageLocked(str23, "Raise procstate to external provider: " + processRecord);
                                                        }
                                                        i20 = i88;
                                                        i91 = 6;
                                                        processStateRecord11 = processStateRecord13;
                                                        i81 = i90;
                                                        str19 = str24;
                                                        processProviderRecord2 = processProviderRecord3;
                                                        curCapability3 = i82;
                                                        processServiceRecord9 = processServiceRecord10;
                                                        str21 = str23;
                                                        str22 = str25;
                                                        i9 = i91;
                                                        numberOfProviders = i89 - 1;
                                                        z23 = z24;
                                                    }
                                                }
                                                i20 = i88;
                                                processStateRecord11 = processStateRecord13;
                                                i81 = i90;
                                                str19 = str24;
                                                processProviderRecord2 = processProviderRecord3;
                                                curCapability3 = i82;
                                                processServiceRecord9 = processServiceRecord10;
                                                str21 = str23;
                                                str22 = str25;
                                                i9 = i91;
                                                numberOfProviders = i89 - 1;
                                                z23 = z24;
                                            }
                                            processProviderRecord = processProviderRecord2;
                                            processStateRecord = processStateRecord11;
                                            processServiceRecord2 = processServiceRecord9;
                                            String str26 = str21;
                                            int i92 = i81;
                                            if (processProviderRecord.getLastProviderTime() > 0 || processProviderRecord.getLastProviderTime() + this.mConstants.CONTENT_PROVIDER_RETAIN_TIME <= j) {
                                                i22 = i9;
                                            } else {
                                                if (i19 > 700) {
                                                    processStateRecord.setCached(false);
                                                    processStateRecord.setAdjType("recent-provider");
                                                    i27 = i79;
                                                    if (i92 == i27) {
                                                        reportOomAdjMessageLocked(str26, "Raise adj to recent provider: " + processRecord);
                                                    }
                                                    i19 = 700;
                                                    i28 = 0;
                                                } else {
                                                    i27 = i79;
                                                    i28 = i20;
                                                }
                                                if (i9 > 15) {
                                                    processStateRecord.setAdjType("recent-provider");
                                                    if (i92 == i27) {
                                                        reportOomAdjMessageLocked(str26, "Raise procstate to recent provider: " + processRecord);
                                                    }
                                                    i20 = i28;
                                                    i22 = 15;
                                                } else {
                                                    i22 = i9;
                                                    i20 = i28;
                                                }
                                            }
                                            if (i22 >= 19) {
                                                if (processServiceRecord2.hasClientActivities()) {
                                                    processStateRecord.setAdjType("cch-client-act");
                                                    i23 = 17;
                                                    i24 = 500;
                                                    if (i19 == i24) {
                                                        if (z && !z2) {
                                                            processStateRecord.setServiceB(this.mNewNumAServiceProcs > this.mNumServiceProcs / 3);
                                                            this.mNewNumServiceProcs++;
                                                            if (!processStateRecord.isServiceB()) {
                                                                if (!this.mService.mAppProfiler.isLastMemoryLevelNormal() && processRecord.mProfile.getLastPss() >= this.mProcessList.getCachedRestoreThresholdKb()) {
                                                                    processStateRecord.setServiceHighRam(true);
                                                                    processStateRecord.setServiceB(true);
                                                                } else {
                                                                    this.mNewNumAServiceProcs++;
                                                                }
                                                            } else {
                                                                processStateRecord.setServiceHighRam(false);
                                                            }
                                                        }
                                                        if (processStateRecord.isServiceB()) {
                                                            i19 = 800;
                                                        }
                                                    }
                                                    processStateRecord.setCurRawAdj(i19);
                                                    int modifyRawOomAdj = processServiceRecord2.modifyRawOomAdj(i19);
                                                    i25 = (modifyRawOomAdj > processStateRecord.getMaxAdj() || (modifyRawOomAdj = processStateRecord.getMaxAdj()) > 250) ? i20 : 2;
                                                    if (i23 >= 5) {
                                                        z11 = true;
                                                        if (this.mService.mWakefulness.get() != 1 && !z9 && i25 > 1) {
                                                            i25 = 1;
                                                        }
                                                    } else {
                                                        z11 = true;
                                                    }
                                                    if (processServiceRecord2.hasForegroundServices()) {
                                                        curCapability3 |= i68;
                                                    }
                                                    int defaultCapability = getDefaultCapability(processRecord, i23) | curCapability3;
                                                    if (i23 > 5) {
                                                        defaultCapability &= -17;
                                                    }
                                                    if (CoreRune.SYSPERF_BOOST_OPT && promoteSchedGroupIfNecessary(i25, i23, processStateRecord)) {
                                                        i25 = 6;
                                                    }
                                                    if (processRecord.getUidRecord() != null && processRecord.getUidRecord().getFGSFilterStatus()) {
                                                        if (i23 == 4 || !z7) {
                                                            i26 = i80;
                                                            if (i26 == 4) {
                                                                processRecord.getUidRecord().setFGSFilterStatus(false);
                                                            }
                                                            if (processStateRecord.getAbnormalStatus()) {
                                                                if (i23 <= 3 || i25 == 3 || processStateRecord.getCurAdj() >= 850) {
                                                                    processStateRecord.setAbnormalStatus(false);
                                                                } else if (z18) {
                                                                    i25 = -2;
                                                                } else {
                                                                    processStateRecord.setAbnormalStatus(false);
                                                                }
                                                            }
                                                            processStateRecord.setCurAdj(modifyRawOomAdj);
                                                            processStateRecord.setCurCapability(defaultCapability);
                                                            processStateRecord.setCurrentSchedulingGroup(i25);
                                                            processStateRecord.setCurProcState(i23);
                                                            processStateRecord.setCurRawProcState(i23);
                                                            processStateRecord.updateLastInvisibleTime(z17);
                                                            processStateRecord.setHasForegroundActivities(z16);
                                                            processStateRecord.setCompletedAdjSeq(this.mAdjSeq);
                                                            processStateRecord.setCurBoundByNonBgRestrictedApp(z23);
                                                            if (processRecord.isActiveLaunch) {
                                                                processStateRecord.setCurrentSchedulingGroup(CoreRune.SYSPERF_BOOST_OPT ? 6 : 2);
                                                            }
                                                            if (CoreRune.SYSFW_APP_PREL && processRecord.getWindowProcessController().mIsPrelScheduleGroupOverride) {
                                                                processStateRecord.setCurrentSchedulingGroup(2);
                                                            }
                                                            if (processStateRecord.getCurAdj() >= i21 || processStateRecord.getCurProcState() < i26 || processStateRecord.getCurCapability() != i10) {
                                                                return z11;
                                                            }
                                                            return false;
                                                        }
                                                        i25 = 5;
                                                    }
                                                    i26 = i80;
                                                    if (processStateRecord.getAbnormalStatus()) {
                                                    }
                                                    processStateRecord.setCurAdj(modifyRawOomAdj);
                                                    processStateRecord.setCurCapability(defaultCapability);
                                                    processStateRecord.setCurrentSchedulingGroup(i25);
                                                    processStateRecord.setCurProcState(i23);
                                                    processStateRecord.setCurRawProcState(i23);
                                                    processStateRecord.updateLastInvisibleTime(z17);
                                                    processStateRecord.setHasForegroundActivities(z16);
                                                    processStateRecord.setCompletedAdjSeq(this.mAdjSeq);
                                                    processStateRecord.setCurBoundByNonBgRestrictedApp(z23);
                                                    if (processRecord.isActiveLaunch) {
                                                    }
                                                    if (CoreRune.SYSFW_APP_PREL) {
                                                        processStateRecord.setCurrentSchedulingGroup(2);
                                                    }
                                                    if (processStateRecord.getCurAdj() >= i21) {
                                                    }
                                                    return z11;
                                                }
                                                if (processServiceRecord2.isTreatedLikeActivity()) {
                                                    processStateRecord.setAdjType("cch-as-act");
                                                    i24 = 500;
                                                    i23 = 16;
                                                    if (i19 == i24) {
                                                    }
                                                    processStateRecord.setCurRawAdj(i19);
                                                    int modifyRawOomAdj2 = processServiceRecord2.modifyRawOomAdj(i19);
                                                    if (modifyRawOomAdj2 > processStateRecord.getMaxAdj()) {
                                                    }
                                                    if (i23 >= 5) {
                                                    }
                                                    if (processServiceRecord2.hasForegroundServices()) {
                                                    }
                                                    int defaultCapability2 = getDefaultCapability(processRecord, i23) | curCapability3;
                                                    if (i23 > 5) {
                                                    }
                                                    if (CoreRune.SYSPERF_BOOST_OPT) {
                                                        i25 = 6;
                                                    }
                                                    if (processRecord.getUidRecord() != null) {
                                                        if (i23 == 4) {
                                                        }
                                                        i26 = i80;
                                                        if (i26 == 4) {
                                                        }
                                                        if (processStateRecord.getAbnormalStatus()) {
                                                        }
                                                        processStateRecord.setCurAdj(modifyRawOomAdj2);
                                                        processStateRecord.setCurCapability(defaultCapability2);
                                                        processStateRecord.setCurrentSchedulingGroup(i25);
                                                        processStateRecord.setCurProcState(i23);
                                                        processStateRecord.setCurRawProcState(i23);
                                                        processStateRecord.updateLastInvisibleTime(z17);
                                                        processStateRecord.setHasForegroundActivities(z16);
                                                        processStateRecord.setCompletedAdjSeq(this.mAdjSeq);
                                                        processStateRecord.setCurBoundByNonBgRestrictedApp(z23);
                                                        if (processRecord.isActiveLaunch) {
                                                        }
                                                        if (CoreRune.SYSFW_APP_PREL) {
                                                        }
                                                        if (processStateRecord.getCurAdj() >= i21) {
                                                        }
                                                        return z11;
                                                    }
                                                    i26 = i80;
                                                    if (processStateRecord.getAbnormalStatus()) {
                                                    }
                                                    processStateRecord.setCurAdj(modifyRawOomAdj2);
                                                    processStateRecord.setCurCapability(defaultCapability2);
                                                    processStateRecord.setCurrentSchedulingGroup(i25);
                                                    processStateRecord.setCurProcState(i23);
                                                    processStateRecord.setCurRawProcState(i23);
                                                    processStateRecord.updateLastInvisibleTime(z17);
                                                    processStateRecord.setHasForegroundActivities(z16);
                                                    processStateRecord.setCompletedAdjSeq(this.mAdjSeq);
                                                    processStateRecord.setCurBoundByNonBgRestrictedApp(z23);
                                                    if (processRecord.isActiveLaunch) {
                                                    }
                                                    if (CoreRune.SYSFW_APP_PREL) {
                                                    }
                                                    if (processStateRecord.getCurAdj() >= i21) {
                                                    }
                                                    return z11;
                                                }
                                            }
                                            i23 = i22;
                                            i24 = 500;
                                            if (i19 == i24) {
                                            }
                                            processStateRecord.setCurRawAdj(i19);
                                            int modifyRawOomAdj22 = processServiceRecord2.modifyRawOomAdj(i19);
                                            if (modifyRawOomAdj22 > processStateRecord.getMaxAdj()) {
                                            }
                                            if (i23 >= 5) {
                                            }
                                            if (processServiceRecord2.hasForegroundServices()) {
                                            }
                                            int defaultCapability22 = getDefaultCapability(processRecord, i23) | curCapability3;
                                            if (i23 > 5) {
                                            }
                                            if (CoreRune.SYSPERF_BOOST_OPT) {
                                            }
                                            if (processRecord.getUidRecord() != null) {
                                            }
                                            i26 = i80;
                                            if (processStateRecord.getAbnormalStatus()) {
                                            }
                                            processStateRecord.setCurAdj(modifyRawOomAdj22);
                                            processStateRecord.setCurCapability(defaultCapability22);
                                            processStateRecord.setCurrentSchedulingGroup(i25);
                                            processStateRecord.setCurProcState(i23);
                                            processStateRecord.setCurRawProcState(i23);
                                            processStateRecord.updateLastInvisibleTime(z17);
                                            processStateRecord.setHasForegroundActivities(z16);
                                            processStateRecord.setCompletedAdjSeq(this.mAdjSeq);
                                            processStateRecord.setCurBoundByNonBgRestrictedApp(z23);
                                            if (processRecord.isActiveLaunch) {
                                            }
                                            if (CoreRune.SYSFW_APP_PREL) {
                                            }
                                            if (processStateRecord.getCurAdj() >= i21) {
                                            }
                                            return z11;
                                        }
                                        if (i17 > 700) {
                                            processStateRecord8.setCached(false);
                                            processStateRecord8.setAdjType("previous");
                                            if (i66 == i65) {
                                                reportOomAdjMessageLocked("ActivityManager", "Raise adj to prev: " + processRecord);
                                            }
                                            i17 = 700;
                                            i6 = 0;
                                        }
                                        if (i9 > 15) {
                                            processStateRecord8.setAdjType("previous");
                                            if (i66 == i65) {
                                                reportOomAdjMessageLocked("ActivityManager", "Raise procstate to prev: " + processRecord);
                                            }
                                            i9 = 15;
                                        }
                                    }
                                    i18 = i6;
                                    if (z2) {
                                    }
                                    processStateRecord8.setCurRawAdj(i17);
                                    processStateRecord8.setCurRawProcState(i9);
                                    processStateRecord8.setHasStartedServices(false);
                                    processStateRecord8.setAdjSeq(this.mAdjSeq);
                                    backupRecord = (BackupRecord) this.mService.mBackupTargets.get(processRecord.userId);
                                    if (backupRecord == null) {
                                    }
                                    boolean isCurBoundByNonBgRestrictedApp2 = processStateRecord8.isCurBoundByNonBgRestrictedApp();
                                    i19 = i17;
                                    numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1;
                                    int i682 = i14;
                                    z9 = false;
                                    z10 = isCurBoundByNonBgRestrictedApp2;
                                    while (numberOfRunningServices >= 0) {
                                        runningServiceAt = processServiceRecord.getRunningServiceAt(numberOfRunningServices);
                                        int i692 = i18;
                                        if (runningServiceAt.startRequested) {
                                        }
                                        processServiceRecord4 = processServiceRecord;
                                        str10 = str19;
                                        i35 = numberOfRunningServices;
                                        i36 = 0;
                                        if (runningServiceAt.isForeground) {
                                        }
                                        connections = runningServiceAt.getConnections();
                                        i37 = curCapability3;
                                        size = connections.size() - 1;
                                        i18 = i692;
                                        while (true) {
                                            if (size >= 0) {
                                            }
                                            i19 = i75;
                                            processServiceRecord4 = processServiceRecord4;
                                            str18 = str18;
                                            str10 = str10;
                                            i65 = i65;
                                            i9 = i74;
                                            connections = connections;
                                            i2 = i2;
                                            i3 = i3;
                                            i67 = 3;
                                            size--;
                                            i18 = i73;
                                            i37 = i72;
                                            z10 = z20;
                                            z9 = z19;
                                            i36 = 0;
                                            i66 = i66;
                                            processStateRecord8 = processStateRecord8;
                                        }
                                        str20 = str12;
                                        i66 = i41;
                                        i65 = i38;
                                        i2 = i39;
                                        i3 = i40;
                                        i67 = 3;
                                        processStateRecord8 = processStateRecord4;
                                        processServiceRecord = processServiceRecord5;
                                        numberOfRunningServices = i35 - 1;
                                        str18 = str11;
                                        curCapability3 = i37;
                                    }
                                    i20 = i18;
                                    int i792 = i65;
                                    String str212 = str18;
                                    int i802 = i2;
                                    i21 = i3;
                                    String str222 = str20;
                                    ProcessServiceRecord processServiceRecord92 = processServiceRecord;
                                    ProcessStateRecord processStateRecord112 = processStateRecord8;
                                    ProcessProviderRecord processProviderRecord22 = processRecord.mProviders;
                                    int i812 = i66;
                                    numberOfProviders = processProviderRecord22.numberOfProviders() - 1;
                                    boolean z232 = z10;
                                    while (numberOfProviders >= 0) {
                                    }
                                    processProviderRecord = processProviderRecord22;
                                    processStateRecord = processStateRecord112;
                                    processServiceRecord2 = processServiceRecord92;
                                    String str262 = str212;
                                    int i922 = i812;
                                    if (processProviderRecord.getLastProviderTime() > 0) {
                                    }
                                    i22 = i9;
                                    if (i22 >= 19) {
                                    }
                                    i23 = i22;
                                    i24 = 500;
                                    if (i19 == i24) {
                                    }
                                    processStateRecord.setCurRawAdj(i19);
                                    int modifyRawOomAdj222 = processServiceRecord2.modifyRawOomAdj(i19);
                                    if (modifyRawOomAdj222 > processStateRecord.getMaxAdj()) {
                                    }
                                    if (i23 >= 5) {
                                    }
                                    if (processServiceRecord2.hasForegroundServices()) {
                                    }
                                    int defaultCapability222 = getDefaultCapability(processRecord, i23) | curCapability3;
                                    if (i23 > 5) {
                                    }
                                    if (CoreRune.SYSPERF_BOOST_OPT) {
                                    }
                                    if (processRecord.getUidRecord() != null) {
                                    }
                                    i26 = i802;
                                    if (processStateRecord.getAbnormalStatus()) {
                                    }
                                    processStateRecord.setCurAdj(modifyRawOomAdj222);
                                    processStateRecord.setCurCapability(defaultCapability222);
                                    processStateRecord.setCurrentSchedulingGroup(i25);
                                    processStateRecord.setCurProcState(i23);
                                    processStateRecord.setCurRawProcState(i23);
                                    processStateRecord.updateLastInvisibleTime(z17);
                                    processStateRecord.setHasForegroundActivities(z16);
                                    processStateRecord.setCompletedAdjSeq(this.mAdjSeq);
                                    processStateRecord.setCurBoundByNonBgRestrictedApp(z232);
                                    if (processRecord.isActiveLaunch) {
                                    }
                                    if (CoreRune.SYSFW_APP_PREL) {
                                    }
                                    if (processStateRecord.getCurAdj() >= i21) {
                                    }
                                    return z11;
                                }
                            }
                            i16 = i15;
                            if (processServiceRecord.hasTopStartedAlmostPerceptibleServices()) {
                            }
                            i17 = i16;
                            if (i17 <= 200) {
                            }
                            processStateRecord8.setCached(false);
                            processStateRecord8.setAdjType("force-imp");
                            processStateRecord8.setAdjSource(processStateRecord8.getForcingToImportant());
                            if (i66 == i65) {
                            }
                            i9 = 8;
                            i17 = 200;
                            i6 = 2;
                            if (processStateRecord8.getCachedIsHeavyWeight()) {
                            }
                            if (processStateRecord8.getCachedIsHomeProcess()) {
                            }
                            String str202 = ": ";
                            if (processStateRecord8.getCachedIsPreviousProcess()) {
                                if (i9 < 15) {
                                }
                                if (i17 > 700) {
                                }
                                if (i9 > 15) {
                                }
                            }
                            i18 = i6;
                            if (z2) {
                            }
                            processStateRecord8.setCurRawAdj(i17);
                            processStateRecord8.setCurRawProcState(i9);
                            processStateRecord8.setHasStartedServices(false);
                            processStateRecord8.setAdjSeq(this.mAdjSeq);
                            backupRecord = (BackupRecord) this.mService.mBackupTargets.get(processRecord.userId);
                            if (backupRecord == null) {
                            }
                            boolean isCurBoundByNonBgRestrictedApp22 = processStateRecord8.isCurBoundByNonBgRestrictedApp();
                            i19 = i17;
                            numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1;
                            int i6822 = i14;
                            z9 = false;
                            z10 = isCurBoundByNonBgRestrictedApp22;
                            while (numberOfRunningServices >= 0) {
                            }
                            i20 = i18;
                            int i7922 = i65;
                            String str2122 = str18;
                            int i8022 = i2;
                            i21 = i3;
                            String str2222 = str202;
                            ProcessServiceRecord processServiceRecord922 = processServiceRecord;
                            ProcessStateRecord processStateRecord1122 = processStateRecord8;
                            ProcessProviderRecord processProviderRecord222 = processRecord.mProviders;
                            int i8122 = i66;
                            numberOfProviders = processProviderRecord222.numberOfProviders() - 1;
                            boolean z2322 = z10;
                            while (numberOfProviders >= 0) {
                            }
                            processProviderRecord = processProviderRecord222;
                            processStateRecord = processStateRecord1122;
                            processServiceRecord2 = processServiceRecord922;
                            String str2622 = str2122;
                            int i9222 = i8122;
                            if (processProviderRecord.getLastProviderTime() > 0) {
                            }
                            i22 = i9;
                            if (i22 >= 19) {
                            }
                            i23 = i22;
                            i24 = 500;
                            if (i19 == i24) {
                            }
                            processStateRecord.setCurRawAdj(i19);
                            int modifyRawOomAdj2222 = processServiceRecord2.modifyRawOomAdj(i19);
                            if (modifyRawOomAdj2222 > processStateRecord.getMaxAdj()) {
                            }
                            if (i23 >= 5) {
                            }
                            if (processServiceRecord2.hasForegroundServices()) {
                            }
                            int defaultCapability2222 = getDefaultCapability(processRecord, i23) | curCapability3;
                            if (i23 > 5) {
                            }
                            if (CoreRune.SYSPERF_BOOST_OPT) {
                            }
                            if (processRecord.getUidRecord() != null) {
                            }
                            i26 = i8022;
                            if (processStateRecord.getAbnormalStatus()) {
                            }
                            processStateRecord.setCurAdj(modifyRawOomAdj2222);
                            processStateRecord.setCurCapability(defaultCapability2222);
                            processStateRecord.setCurrentSchedulingGroup(i25);
                            processStateRecord.setCurProcState(i23);
                            processStateRecord.setCurRawProcState(i23);
                            processStateRecord.updateLastInvisibleTime(z17);
                            processStateRecord.setHasForegroundActivities(z16);
                            processStateRecord.setCompletedAdjSeq(this.mAdjSeq);
                            processStateRecord.setCurBoundByNonBgRestrictedApp(z2322);
                            if (processRecord.isActiveLaunch) {
                            }
                            if (CoreRune.SYSFW_APP_PREL) {
                            }
                            if (processStateRecord.getCurAdj() >= i21) {
                            }
                            return z11;
                        }
                    } else {
                        i11 = 4;
                    }
                    if (hasForegroundServices || !hasNonShortForegroundServices) {
                        if (!z8) {
                            str = "fg-service-short";
                            i12 = 226;
                            i13 = i11;
                        } else if (processStateRecord8.hasOverlayUi()) {
                            str = "has-overlay-ui";
                            i12 = 200;
                            i13 = 6;
                        } else {
                            str = null;
                            i12 = 0;
                            i13 = 0;
                        }
                        i14 = 0;
                    } else {
                        str = "fg-service";
                        i13 = i11;
                        i12 = 200;
                        i14 = 16;
                    }
                    if (str != null) {
                        processStateRecord8.setAdjType(str);
                        processStateRecord8.setCached(false);
                        if (i66 == i65) {
                            reportOomAdjMessageLocked("ActivityManager", "Raise to " + str + ": " + processRecord + " ");
                        }
                        i8 = i12;
                        i9 = i13;
                        i6 = 2;
                    }
                    if (processServiceRecord.hasForegroundServices()) {
                    }
                    i15 = i8;
                    i16 = i15;
                    if (processServiceRecord.hasTopStartedAlmostPerceptibleServices()) {
                    }
                    i17 = i16;
                    if (i17 <= 200) {
                    }
                    processStateRecord8.setCached(false);
                    processStateRecord8.setAdjType("force-imp");
                    processStateRecord8.setAdjSource(processStateRecord8.getForcingToImportant());
                    if (i66 == i65) {
                    }
                    i9 = 8;
                    i17 = 200;
                    i6 = 2;
                    if (processStateRecord8.getCachedIsHeavyWeight()) {
                    }
                    if (processStateRecord8.getCachedIsHomeProcess()) {
                    }
                    String str2022 = ": ";
                    if (processStateRecord8.getCachedIsPreviousProcess()) {
                    }
                    i18 = i6;
                    if (z2) {
                    }
                    processStateRecord8.setCurRawAdj(i17);
                    processStateRecord8.setCurRawProcState(i9);
                    processStateRecord8.setHasStartedServices(false);
                    processStateRecord8.setAdjSeq(this.mAdjSeq);
                    backupRecord = (BackupRecord) this.mService.mBackupTargets.get(processRecord.userId);
                    if (backupRecord == null) {
                    }
                    boolean isCurBoundByNonBgRestrictedApp222 = processStateRecord8.isCurBoundByNonBgRestrictedApp();
                    i19 = i17;
                    numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1;
                    int i68222 = i14;
                    z9 = false;
                    z10 = isCurBoundByNonBgRestrictedApp222;
                    while (numberOfRunningServices >= 0) {
                    }
                    i20 = i18;
                    int i79222 = i65;
                    String str21222 = str18;
                    int i80222 = i2;
                    i21 = i3;
                    String str22222 = str2022;
                    ProcessServiceRecord processServiceRecord9222 = processServiceRecord;
                    ProcessStateRecord processStateRecord11222 = processStateRecord8;
                    ProcessProviderRecord processProviderRecord2222 = processRecord.mProviders;
                    int i81222 = i66;
                    numberOfProviders = processProviderRecord2222.numberOfProviders() - 1;
                    boolean z23222 = z10;
                    while (numberOfProviders >= 0) {
                    }
                    processProviderRecord = processProviderRecord2222;
                    processStateRecord = processStateRecord11222;
                    processServiceRecord2 = processServiceRecord9222;
                    String str26222 = str21222;
                    int i92222 = i81222;
                    if (processProviderRecord.getLastProviderTime() > 0) {
                    }
                    i22 = i9;
                    if (i22 >= 19) {
                    }
                    i23 = i22;
                    i24 = 500;
                    if (i19 == i24) {
                    }
                    processStateRecord.setCurRawAdj(i19);
                    int modifyRawOomAdj22222 = processServiceRecord2.modifyRawOomAdj(i19);
                    if (modifyRawOomAdj22222 > processStateRecord.getMaxAdj()) {
                    }
                    if (i23 >= 5) {
                    }
                    if (processServiceRecord2.hasForegroundServices()) {
                    }
                    int defaultCapability22222 = getDefaultCapability(processRecord, i23) | curCapability3;
                    if (i23 > 5) {
                    }
                    if (CoreRune.SYSPERF_BOOST_OPT) {
                    }
                    if (processRecord.getUidRecord() != null) {
                    }
                    i26 = i80222;
                    if (processStateRecord.getAbnormalStatus()) {
                    }
                    processStateRecord.setCurAdj(modifyRawOomAdj22222);
                    processStateRecord.setCurCapability(defaultCapability22222);
                    processStateRecord.setCurrentSchedulingGroup(i25);
                    processStateRecord.setCurProcState(i23);
                    processStateRecord.setCurRawProcState(i23);
                    processStateRecord.updateLastInvisibleTime(z17);
                    processStateRecord.setHasForegroundActivities(z16);
                    processStateRecord.setCompletedAdjSeq(this.mAdjSeq);
                    processStateRecord.setCurBoundByNonBgRestrictedApp(z23222);
                    if (processRecord.isActiveLaunch) {
                    }
                    if (CoreRune.SYSFW_APP_PREL) {
                    }
                    if (processStateRecord.getCurAdj() >= i21) {
                    }
                    return z11;
                }
                if (processStateRecord8.getCachedIsReceivingBroadcast(this.mTmpSchedGroup)) {
                    int i93 = this.mTmpSchedGroup[0];
                    processStateRecord8.setAdjType(INetd.IF_FLAG_BROADCAST);
                    if (i66 == i65) {
                        reportOomAdjMessageLocked("ActivityManager", "Making broadcast: " + processRecord);
                    }
                    i6 = i93;
                    i5 = 11;
                    z4 = false;
                    i4 = 0;
                } else if (processServiceRecord8.numberOfExecutingServices() > 0) {
                    int i94 = processServiceRecord8.shouldExecServicesFg() ? 2 : 0;
                    processStateRecord8.setAdjType("exec-service");
                    if (i66 == i65) {
                        reportOomAdjMessageLocked("ActivityManager", "Making exec-service: " + processRecord);
                    }
                    i6 = i94;
                    z4 = false;
                    i4 = 0;
                    z5 = false;
                    z6 = false;
                    i5 = 10;
                } else {
                    if (processRecord == processRecord2) {
                        processStateRecord8.setAdjType("top-sleeping");
                        if (i66 == i65) {
                            reportOomAdjMessageLocked("ActivityManager", "Making top (sleeping): " + processRecord);
                        }
                        i5 = topProcessState;
                        z4 = true;
                        i4 = 0;
                        z5 = true;
                    } else {
                        if (this.mService.mActivityTaskManager.mExt.hasKeepAliveActivities(processRecord.getWindowProcessController())) {
                            processStateRecord8.setAdjType("force-keep-alive");
                        } else {
                            if (processRecord.mDedicated) {
                                processStateRecord8.setAdjType("dedicated");
                                if (!processRecord.hasActivities()) {
                                    z4 = true;
                                    i4 = 100;
                                }
                            } else {
                                if (!processStateRecord8.containsCycle()) {
                                    processStateRecord8.setCached(true);
                                    processStateRecord8.setEmpty(true);
                                    processStateRecord8.setAdjType("cch-empty");
                                }
                                if (i66 == i65) {
                                    reportOomAdjMessageLocked("ActivityManager", "Making empty: " + processRecord);
                                }
                                i4 = i;
                                z4 = true;
                            }
                            z5 = false;
                            z6 = false;
                            i5 = 19;
                            i6 = 0;
                        }
                        i5 = topProcessState;
                        z4 = true;
                        i4 = 100;
                        z5 = false;
                    }
                    z6 = false;
                    i6 = 0;
                }
            }
            z5 = false;
            z6 = false;
        }
        z7 = true;
        if (z5) {
        }
        processServiceRecord = processServiceRecord8;
        i7 = curCapability2;
        i8 = i4;
        boolean z162 = z5;
        boolean z172 = z6;
        i9 = i5;
        boolean z182 = z4;
        if (i9 > 18) {
            processStateRecord8.setAdjType("cch-rec");
            if (i66 == i65) {
            }
            i9 = 18;
        }
        hasForegroundServices = processServiceRecord.hasForegroundServices();
        boolean hasNonShortForegroundServices2 = processServiceRecord.hasNonShortForegroundServices();
        if (hasForegroundServices) {
        }
        i10 = i7;
        z8 = false;
        String str192 = "Raise to ";
        if (i8 > 200) {
        }
        if (hasForegroundServices) {
        }
        if (!z8) {
        }
        i14 = 0;
        if (str != null) {
        }
        if (processServiceRecord.hasForegroundServices()) {
        }
        i15 = i8;
        i16 = i15;
        if (processServiceRecord.hasTopStartedAlmostPerceptibleServices()) {
        }
        i17 = i16;
        if (i17 <= 200) {
        }
        processStateRecord8.setCached(false);
        processStateRecord8.setAdjType("force-imp");
        processStateRecord8.setAdjSource(processStateRecord8.getForcingToImportant());
        if (i66 == i65) {
        }
        i9 = 8;
        i17 = 200;
        i6 = 2;
        if (processStateRecord8.getCachedIsHeavyWeight()) {
        }
        if (processStateRecord8.getCachedIsHomeProcess()) {
        }
        String str20222 = ": ";
        if (processStateRecord8.getCachedIsPreviousProcess()) {
        }
        i18 = i6;
        if (z2) {
        }
        processStateRecord8.setCurRawAdj(i17);
        processStateRecord8.setCurRawProcState(i9);
        processStateRecord8.setHasStartedServices(false);
        processStateRecord8.setAdjSeq(this.mAdjSeq);
        backupRecord = (BackupRecord) this.mService.mBackupTargets.get(processRecord.userId);
        if (backupRecord == null) {
        }
        boolean isCurBoundByNonBgRestrictedApp2222 = processStateRecord8.isCurBoundByNonBgRestrictedApp();
        i19 = i17;
        numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1;
        int i682222 = i14;
        z9 = false;
        z10 = isCurBoundByNonBgRestrictedApp2222;
        while (numberOfRunningServices >= 0) {
        }
        i20 = i18;
        int i792222 = i65;
        String str212222 = str18;
        int i802222 = i2;
        i21 = i3;
        String str222222 = str20222;
        ProcessServiceRecord processServiceRecord92222 = processServiceRecord;
        ProcessStateRecord processStateRecord112222 = processStateRecord8;
        ProcessProviderRecord processProviderRecord22222 = processRecord.mProviders;
        int i812222 = i66;
        numberOfProviders = processProviderRecord22222.numberOfProviders() - 1;
        boolean z232222 = z10;
        while (numberOfProviders >= 0) {
        }
        processProviderRecord = processProviderRecord22222;
        processStateRecord = processStateRecord112222;
        processServiceRecord2 = processServiceRecord92222;
        String str262222 = str212222;
        int i922222 = i812222;
        if (processProviderRecord.getLastProviderTime() > 0) {
        }
        i22 = i9;
        if (i22 >= 19) {
        }
        i23 = i22;
        i24 = 500;
        if (i19 == i24) {
        }
        processStateRecord.setCurRawAdj(i19);
        int modifyRawOomAdj222222 = processServiceRecord2.modifyRawOomAdj(i19);
        if (modifyRawOomAdj222222 > processStateRecord.getMaxAdj()) {
        }
        if (i23 >= 5) {
        }
        if (processServiceRecord2.hasForegroundServices()) {
        }
        int defaultCapability222222 = getDefaultCapability(processRecord, i23) | curCapability3;
        if (i23 > 5) {
        }
        if (CoreRune.SYSPERF_BOOST_OPT) {
        }
        if (processRecord.getUidRecord() != null) {
        }
        i26 = i802222;
        if (processStateRecord.getAbnormalStatus()) {
        }
        processStateRecord.setCurAdj(modifyRawOomAdj222222);
        processStateRecord.setCurCapability(defaultCapability222222);
        processStateRecord.setCurrentSchedulingGroup(i25);
        processStateRecord.setCurProcState(i23);
        processStateRecord.setCurRawProcState(i23);
        processStateRecord.updateLastInvisibleTime(z172);
        processStateRecord.setHasForegroundActivities(z162);
        processStateRecord.setCompletedAdjSeq(this.mAdjSeq);
        processStateRecord.setCurBoundByNonBgRestrictedApp(z232222);
        if (processRecord.isActiveLaunch) {
        }
        if (CoreRune.SYSFW_APP_PREL) {
        }
        if (processStateRecord.getCurAdj() >= i21) {
        }
        return z11;
    }

    public final boolean promoteSchedGroupIfNecessary(int i, int i2, ProcessStateRecord processStateRecord) {
        if (i != 2) {
            return false;
        }
        if ("fg-service-act".equals(processStateRecord.getAdjType()) || "vis-activity".equals(processStateRecord.getAdjType())) {
            return true;
        }
        if (i2 == 0 && "service".equals(processStateRecord.getAdjType())) {
            return true;
        }
        return i2 == 3 && "provider".equals(processStateRecord.getAdjType());
    }

    public final int getDefaultCapability(ProcessRecord processRecord, int i) {
        int i2;
        int defaultProcessNetworkCapabilities = NetworkPolicyManager.getDefaultProcessNetworkCapabilities(i);
        if (i == 0 || i == 1 || i == 2) {
            i2 = 63;
        } else if (i != 3) {
            i2 = 0;
            if (i == 4 && processRecord.getActiveInstrumentation() != null) {
                i2 = 6;
            }
        } else {
            i2 = 16;
        }
        return defaultProcessNetworkCapabilities | i2;
    }

    public int getBfslCapabilityFromClient(ProcessRecord processRecord) {
        if (processRecord.mState.getCurProcState() < 4) {
            return 16;
        }
        return processRecord.mState.getCurCapability() & 16;
    }

    public final boolean shouldSkipDueToCycle(ProcessRecord processRecord, ProcessStateRecord processStateRecord, int i, int i2, boolean z) {
        if (!processStateRecord.containsCycle()) {
            return false;
        }
        processRecord.mState.setContainsCycle(true);
        this.mProcessesInCycle.add(processRecord);
        if (processStateRecord.getCompletedAdjSeq() < this.mAdjSeq) {
            return !z || (processStateRecord.getCurRawProcState() >= i && processStateRecord.getCurRawAdj() >= i2);
        }
        return false;
    }

    public final void reportOomAdjMessageLocked(String str, String str2) {
        Slog.d(str, str2);
        synchronized (this.mService.mOomAdjObserverLock) {
            ActivityManagerService activityManagerService = this.mService;
            if (activityManagerService.mCurOomAdjObserver != null) {
                activityManagerService.mUiHandler.obtainMessage(70, str2).sendToTarget();
            }
        }
    }

    public void onWakefulnessChanged(int i) {
        this.mCachedAppOptimizer.onWakefulnessChanged(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:149:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0321 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v7 */
    /* JADX WARN: Type inference failed for: r16v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean applyOomAdjLSP(final ProcessRecord processRecord, boolean z, long j, long j2, int i) {
        int i2;
        boolean z2;
        int i3;
        int i4;
        long j3;
        boolean z3;
        Object obj;
        long j4;
        long j5;
        long j6;
        long j7;
        int i5;
        IBinder service;
        PerProcessNandswap perProcessNandswap;
        boolean hasActivities;
        ProcessStateRecord processStateRecord = processRecord.mState;
        UidRecord uidRecord = processRecord.getUidRecord();
        if (processStateRecord.getCurRawAdj() != processStateRecord.getSetRawAdj()) {
            processStateRecord.setSetRawAdj(processStateRecord.getCurRawAdj());
        }
        if (processStateRecord.getCurAdj() != processStateRecord.getSetAdj()) {
            this.mCachedAppOptimizer.onOomAdjustChanged(processStateRecord.getSetAdj(), processStateRecord.getCurAdj(), processRecord);
        }
        processStateRecord.getCurProcState();
        int customADJAndGetProcState = this.mDynamicHiddenApp.setCustomADJAndGetProcState(processRecord);
        if (!this.mService.mBooted || (perProcessNandswap = this.mPerProcessNandswap) == null) {
            i2 = customADJAndGetProcState;
        } else {
            int pid = processRecord.getPid();
            String str = processRecord.processName;
            int setAdj = processStateRecord.getSetAdj();
            int curAdj = processStateRecord.getCurAdj();
            int setProcState = processStateRecord.getSetProcState();
            int curProcState = processStateRecord.getCurProcState();
            hasActivities = processRecord.hasActivities();
            i2 = customADJAndGetProcState;
            perProcessNandswap.onOomAdjChanged(pid, str, setAdj, curAdj, setProcState, curProcState, hasActivities, j);
        }
        GPUMemoryReclaimer gPUMemoryReclaimer = this.mGPUMemoryReclaimer;
        ?? r16 = hasActivities;
        if (gPUMemoryReclaimer != null) {
            GPUMemoryReclaimer gPUMemoryReclaimer2 = gPUMemoryReclaimer;
            gPUMemoryReclaimer2.onOomAdjChanged(processRecord.getPid(), processRecord.processName, processStateRecord.getCurAdj(), processStateRecord.getSetAdj(), processRecord.hasActivities(), processStateRecord.hasForegroundActivities());
            r16 = gPUMemoryReclaimer2;
        }
        if (processStateRecord.getCurAdj() != processStateRecord.getSetAdj()) {
            ProcessList.setOomAdj(processRecord.getPid(), processRecord.uid, processStateRecord.getCurAdj(), i2);
            if (this.mService.mCurOomAdjUid == processRecord.info.uid) {
                reportOomAdjMessageLocked("ActivityManager", "Set " + processRecord.getPid() + " " + processRecord.processName + " adj " + processStateRecord.getCurAdj() + ": " + processStateRecord.getAdjType());
            }
            processStateRecord.setSetAdj(processStateRecord.getCurAdj());
            if (uidRecord != null) {
                uidRecord.noteProcAdjChanged();
            }
            processStateRecord.setVerifiedAdj(-10000);
        } else if (i2 >= 180 && i2 < 200) {
            ProcessList.setOomAdj(processRecord.getPid(), processRecord.uid, processStateRecord.getCurAdj(), i2);
        }
        if (processRecord.mInfant) {
            processRecord.mInfant = false;
        }
        int currentSchedulingGroup = processStateRecord.getCurrentSchedulingGroup();
        if (processStateRecord.getSetSchedGroup() != currentSchedulingGroup) {
            int setSchedGroup = processStateRecord.getSetSchedGroup();
            processStateRecord.setSetSchedGroup(currentSchedulingGroup);
            if (this.mService.mCurOomAdjUid == processRecord.uid) {
                reportOomAdjMessageLocked("ActivityManager", "Setting sched group of " + processRecord.processName + " to " + currentSchedulingGroup + ": " + processStateRecord.getAdjType());
            }
            if (processRecord.getWaitingToKill() != null && processRecord.mReceivers.numberOfCurReceivers() == 0 && ActivityManager.isProcStateBackground(processStateRecord.getSetProcState()) && !processRecord.mClearedWaitingToKill && !processRecord.mKeepSEMPrcp) {
                processRecord.killLocked(processRecord.getWaitingToKill(), 10, 22, true);
                z2 = false;
                if (processStateRecord.hasRepForegroundActivities() == processStateRecord.hasForegroundActivities()) {
                    processStateRecord.setRepForegroundActivities(processStateRecord.hasForegroundActivities());
                    i3 = i;
                    i4 = 1;
                } else {
                    i3 = i;
                    i4 = 0;
                }
                updateAppFreezeStateLSP(processRecord, i3);
                if (processStateRecord.getReportedProcState() != processStateRecord.getCurProcState()) {
                    processStateRecord.setReportedProcState(processStateRecord.getCurProcState());
                    if (processRecord.getThread() != null) {
                        try {
                            processRecord.getThread().setProcessState(processStateRecord.getReportedProcState());
                        } catch (RemoteException unused) {
                        }
                    }
                }
                if (processStateRecord.getSetProcState() != 20 || ProcessList.procStatesDifferForMem(processStateRecord.getCurProcState(), processStateRecord.getSetProcState())) {
                    j3 = j;
                    processStateRecord.setLastStateTime(j3);
                    z3 = true;
                } else {
                    j3 = j;
                    z3 = false;
                }
                obj = this.mService.mAppProfiler.mProfilerLock;
                synchronized (obj) {
                    try {
                        try {
                            processRecord.mProfile.updateProcState(processRecord.mState);
                            this.mService.mAppProfiler.updateNextPssTimeLPf(processStateRecord.getCurProcState(), processRecord.mProfile, j, z3);
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        r16 = obj;
                        throw th;
                    }
                }
                if (processStateRecord.getSetProcState() != processStateRecord.getCurProcState()) {
                    if (this.mService.mCurOomAdjUid == processRecord.uid) {
                        reportOomAdjMessageLocked("ActivityManager", "Proc state change of " + processRecord.processName + " to " + ProcessList.makeProcStateString(processStateRecord.getCurProcState()) + " (" + processStateRecord.getCurProcState() + "): " + processStateRecord.getAdjType());
                    }
                    boolean z4 = processStateRecord.getSetProcState() < 10;
                    boolean z5 = processStateRecord.getCurProcState() < 10;
                    if (z4 && !z5) {
                        processStateRecord.setWhenUnimportant(j3);
                        processRecord.mProfile.mLastCpuTime.set(0L);
                    }
                    j4 = j2;
                    maybeUpdateUsageStatsLSP(processRecord, j4);
                    maybeUpdateLastTopTime(processStateRecord, j3);
                    processStateRecord.setSetProcState(processStateRecord.getCurProcState());
                    if (processStateRecord.getSetProcState() >= 14) {
                        processStateRecord.setNotCachedSinceIdle(false);
                    }
                    if (!z) {
                        synchronized (this.mService.mProcessStats.mLock) {
                            ActivityManagerService activityManagerService = this.mService;
                            activityManagerService.setProcessTrackerStateLOSP(processRecord, activityManagerService.mProcessStats.getMemFactorLocked());
                        }
                    } else {
                        processStateRecord.setProcStateChanged(true);
                    }
                } else {
                    j4 = j2;
                    if (processStateRecord.hasReportedInteraction()) {
                        if (processStateRecord.getCachedCompatChange(2)) {
                            j6 = this.mConstants.USAGE_STATS_INTERACTION_INTERVAL_POST_S;
                        } else {
                            j6 = this.mConstants.USAGE_STATS_INTERACTION_INTERVAL_PRE_S;
                        }
                        if (j4 - processStateRecord.getInteractionEventTime() > j6) {
                            maybeUpdateUsageStatsLSP(processRecord, j4);
                        }
                    } else {
                        if (processStateRecord.getCachedCompatChange(2)) {
                            j5 = this.mConstants.SERVICE_USAGE_INTERACTION_TIME_POST_S;
                        } else {
                            j5 = this.mConstants.SERVICE_USAGE_INTERACTION_TIME_PRE_S;
                        }
                        if (j4 - processStateRecord.getFgInteractionTime() > j5) {
                            maybeUpdateUsageStatsLSP(processRecord, j4);
                        }
                    }
                }
                if (processStateRecord.getCurCapability() != processStateRecord.getSetCapability()) {
                    processStateRecord.setSetCapability(processStateRecord.getCurCapability());
                }
                boolean isCurBoundByNonBgRestrictedApp = processStateRecord.isCurBoundByNonBgRestrictedApp();
                if (isCurBoundByNonBgRestrictedApp != processStateRecord.isSetBoundByNonBgRestrictedApp()) {
                    processStateRecord.setSetBoundByNonBgRestrictedApp(isCurBoundByNonBgRestrictedApp);
                    if (!isCurBoundByNonBgRestrictedApp && processStateRecord.isBackgroundRestricted()) {
                        this.mService.mHandler.post(new Runnable() { // from class: com.android.server.am.OomAdjuster$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                OomAdjuster.this.lambda$applyOomAdjLSP$2(processRecord);
                            }
                        });
                    }
                }
                if (i4 != 0) {
                    j7 = j4;
                    this.mProcessList.enqueueProcessChangeItemLocked(processRecord.getPid(), processRecord.info.uid, i4, processStateRecord.hasRepForegroundActivities(), processStateRecord.getSetCapability());
                } else {
                    j7 = j4;
                }
                if (processStateRecord.isCached() && !processStateRecord.shouldNotKillOnBgRestrictedAndIdle() && (!processStateRecord.isSetCached() || processStateRecord.isSetNoKillOnBgRestrictedAndIdle())) {
                    processStateRecord.setLastCanKillOnBgRestrictedAndIdleTime(j7);
                    if (this.mService.mDeterministicUidIdle || !this.mService.mHandler.hasMessages(58)) {
                        this.mService.mHandler.sendEmptyMessageDelayed(58, this.mConstants.mKillBgRestrictedAndCachedIdleSettleTimeMs);
                    }
                }
                processStateRecord.setSetCached(processStateRecord.isCached());
                processStateRecord.setSetNoKillOnBgRestrictedAndIdle(processStateRecord.shouldNotKillOnBgRestrictedAndIdle());
                return z2;
            }
            if (currentSchedulingGroup == -2) {
                i5 = 8;
            } else if (currentSchedulingGroup == 0) {
                i5 = 0;
            } else if (currentSchedulingGroup != 1) {
                i5 = 5;
                if (currentSchedulingGroup != 3 && currentSchedulingGroup != 4) {
                    if (currentSchedulingGroup != 5) {
                        i5 = (currentSchedulingGroup == 6 && CoreRune.SYSPERF_BOOST_OPT) ? 10 : -1;
                    } else {
                        i5 = 9;
                    }
                }
            } else {
                i5 = 7;
            }
            if (currentSchedulingGroup == -2 || setSchedGroup == -2) {
                if (this.mCFMS == null && (service = ServiceManager.getService("CustomFrequencyManagerService")) != null) {
                    this.mCFMS = ICustomFrequencyManager.Stub.asInterface(service);
                }
                if (this.mCFMS != null) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append("SLOWDOWN::OOM Request PID = ");
                        sb.append(processRecord.getPid());
                        sb.append(", slowdown = ");
                        sb.append(currentSchedulingGroup == -2);
                        Slog.d("OomAdjuster", sb.toString());
                        this.mCFMS.requestFreezeSlowdown(processRecord.getPid(), currentSchedulingGroup == -2, "slowdown");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            Handler handler = this.mProcessGroupHandler;
            handler.sendMessage(handler.obtainMessage(0, processRecord.getPid(), i5, processRecord.processName));
            try {
                int renderThreadTid = processRecord.getRenderThreadTid();
                if (currentSchedulingGroup == 3) {
                    if (setSchedGroup != 3) {
                        processRecord.getWindowProcessController().onTopProcChanged();
                        if (this.mService.mUseFifoUiScheduling) {
                            processStateRecord.setSavedPriority(Process.getThreadPriority(processRecord.getPid()));
                            ActivityManagerService.scheduleAsFifoPriority(processRecord.getPid(), true);
                            if (renderThreadTid != 0) {
                                ActivityManagerService.scheduleAsFifoPriority(renderThreadTid, true);
                            }
                        } else {
                            Process.setThreadPriority(processRecord.getPid(), -10);
                            if (renderThreadTid != 0) {
                                Process.setThreadPriority(renderThreadTid, -10);
                            }
                        }
                    }
                } else if (setSchedGroup == 3 && currentSchedulingGroup != 3) {
                    processRecord.getWindowProcessController().onTopProcChanged();
                    if (this.mService.mUseFifoUiScheduling) {
                        try {
                            try {
                                Process.setThreadScheduler(processRecord.getPid(), 0, 0);
                                Process.setThreadPriority(processRecord.getPid(), processStateRecord.getSavedPriority());
                                if (renderThreadTid != 0) {
                                    Process.setThreadScheduler(renderThreadTid, 0, 0);
                                }
                            } catch (IllegalArgumentException e2) {
                                Slog.w("OomAdjuster", "Failed to set scheduling policy, thread does not exist:\n" + e2);
                            }
                        } catch (SecurityException e3) {
                            Slog.w("OomAdjuster", "Failed to set scheduling policy, not allowed:\n" + e3);
                        }
                    } else {
                        Process.setThreadPriority(processRecord.getPid(), 0);
                    }
                    if (renderThreadTid != 0) {
                        Process.setThreadPriority(renderThreadTid, -4);
                    }
                }
            } catch (IllegalArgumentException | Exception unused2) {
            }
        }
        z2 = true;
        if (processStateRecord.hasRepForegroundActivities() == processStateRecord.hasForegroundActivities()) {
        }
        updateAppFreezeStateLSP(processRecord, i3);
        if (processStateRecord.getReportedProcState() != processStateRecord.getCurProcState()) {
        }
        if (processStateRecord.getSetProcState() != 20) {
        }
        j3 = j;
        processStateRecord.setLastStateTime(j3);
        z3 = true;
        obj = this.mService.mAppProfiler.mProfilerLock;
        synchronized (obj) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyOomAdjLSP$2(ProcessRecord processRecord) {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mService.mServices.stopAllForegroundServicesLocked(processRecord.uid, processRecord.info.packageName);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public void setAttachingSchedGroupLSP(ProcessRecord processRecord) {
        int i = CoreRune.SYSPERF_BOOST_OPT ? 6 : 2;
        ProcessStateRecord processStateRecord = processRecord.mState;
        if (processStateRecord.hasForegroundActivities()) {
            try {
                processRecord.getWindowProcessController().onTopProcChanged();
                if (this.mService.mUseFifoUiScheduling) {
                    ActivityManagerService.scheduleAsFifoPriority(processRecord.getPid(), true);
                } else {
                    Process.setThreadPriority(processRecord.getPid(), -10);
                }
                i = 3;
            } catch (Exception e) {
                Slog.w("OomAdjuster", "Failed to pre-set top priority to " + processRecord + " " + e);
            }
        }
        processStateRecord.setSetSchedGroup(i);
        processStateRecord.setCurrentSchedulingGroup(i);
    }

    public void maybeUpdateUsageStats(ProcessRecord processRecord, long j) {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        maybeUpdateUsageStatsLSP(processRecord, j);
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (Throwable th2) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0047, code lost:
    
        if (r14 > (r0.getFgInteractionTime() + r8)) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void maybeUpdateUsageStatsLSP(ProcessRecord processRecord, long j) {
        long j2;
        long j3;
        ProcessStateRecord processStateRecord = processRecord.mState;
        if (this.mService.mUsageStatsService == null) {
            return;
        }
        boolean cachedCompatChange = processStateRecord.getCachedCompatChange(2);
        if (ActivityManager.isProcStateConsideredInteraction(processStateRecord.getCurProcState())) {
            processStateRecord.setFgInteractionTime(0L);
        } else if (processStateRecord.getCurProcState() <= 4) {
            if (processStateRecord.getFgInteractionTime() == 0) {
                processStateRecord.setFgInteractionTime(j);
            } else if (cachedCompatChange) {
                j2 = this.mConstants.SERVICE_USAGE_INTERACTION_TIME_POST_S;
            } else {
                j2 = this.mConstants.SERVICE_USAGE_INTERACTION_TIME_PRE_S;
            }
            r7 = false;
        } else {
            r7 = processStateRecord.getCurProcState() <= 6;
            processStateRecord.setFgInteractionTime(0L);
        }
        if (cachedCompatChange) {
            j3 = this.mConstants.USAGE_STATS_INTERACTION_INTERVAL_POST_S;
        } else {
            j3 = this.mConstants.USAGE_STATS_INTERACTION_INTERVAL_PRE_S;
        }
        if (r7 && (!processStateRecord.hasReportedInteraction() || j - processStateRecord.getInteractionEventTime() > j3)) {
            processStateRecord.setInteractionEventTime(j);
            String[] packageList = processRecord.getPackageList();
            if (packageList != null) {
                for (String str : packageList) {
                    this.mService.mUsageStatsService.reportEvent(str, processRecord.userId, 6);
                }
            }
        }
        processStateRecord.setReportedInteraction(r7);
        if (r7) {
            return;
        }
        processStateRecord.setInteractionEventTime(0L);
    }

    public final void maybeUpdateLastTopTime(ProcessStateRecord processStateRecord, long j) {
        if (processStateRecord.getSetProcState() > 2 || processStateRecord.getCurProcState() <= 2) {
            return;
        }
        processStateRecord.setLastTopTime(j);
    }

    public void idleUidsLocked() {
        int size = this.mActiveUids.size();
        this.mService.mHandler.removeMessages(58);
        if (size <= 0) {
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = elapsedRealtime - this.mConstants.BACKGROUND_SETTLE_TIME;
        PowerManagerInternal powerManagerInternal = this.mLocalPowerManager;
        if (powerManagerInternal != null) {
            powerManagerInternal.startUidChanges();
        }
        long j2 = 0;
        for (int i = size - 1; i >= 0; i--) {
            UidRecord valueAt = this.mActiveUids.valueAt(i);
            long lastBackgroundTime = valueAt.getLastBackgroundTime();
            long lastIdleTime = valueAt.getLastIdleTime();
            if (lastBackgroundTime > 0 && (!valueAt.isIdle() || lastIdleTime == 0)) {
                if (lastBackgroundTime <= j) {
                    EventLogTags.writeAmUidIdle(valueAt.getUid());
                    ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                    ActivityManagerService.boostPriorityForProcLockedSection();
                    synchronized (activityManagerGlobalLock) {
                        try {
                            valueAt.setIdle(true);
                            valueAt.setSetIdle(true);
                            valueAt.setLastIdleTime(elapsedRealtime);
                        } catch (Throwable th) {
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            throw th;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    this.mService.doStopUidLocked(valueAt.getUid(), valueAt);
                } else if (j2 == 0 || j2 > lastBackgroundTime) {
                    j2 = lastBackgroundTime;
                }
            }
        }
        PowerManagerInternal powerManagerInternal2 = this.mLocalPowerManager;
        if (powerManagerInternal2 != null) {
            powerManagerInternal2.finishUidChanges();
        }
        if (this.mService.mConstants.mKillBgRestrictedAndCachedIdle) {
            ArraySet arraySet = this.mProcessList.mAppsInBackgroundRestricted;
            int size2 = arraySet.size();
            for (int i2 = 0; i2 < size2; i2++) {
                long lambda$killAppIfBgRestrictedAndCachedIdleLocked$4 = this.mProcessList.lambda$killAppIfBgRestrictedAndCachedIdleLocked$4((ProcessRecord) arraySet.valueAt(i2), elapsedRealtime) - this.mConstants.BACKGROUND_SETTLE_TIME;
                if (lambda$killAppIfBgRestrictedAndCachedIdleLocked$4 > 0 && (j2 == 0 || j2 > lambda$killAppIfBgRestrictedAndCachedIdleLocked$4)) {
                    j2 = lambda$killAppIfBgRestrictedAndCachedIdleLocked$4;
                }
            }
        }
        if (j2 > 0) {
            this.mService.mHandler.sendEmptyMessageDelayed(58, (j2 + this.mConstants.BACKGROUND_SETTLE_TIME) - elapsedRealtime);
        }
    }

    public void setAppIdTempAllowlistStateLSP(int i, boolean z) {
        boolean z2 = false;
        for (int size = this.mActiveUids.size() - 1; size >= 0; size--) {
            UidRecord valueAt = this.mActiveUids.valueAt(size);
            if (valueAt.getUid() == i && valueAt.isCurAllowListed() != z) {
                valueAt.setCurAllowListed(z);
                z2 = true;
            }
        }
        if (z2) {
            updateOomAdjLSP(10);
        }
    }

    public void setUidTempAllowlistStateLSP(int i, boolean z) {
        UidRecord uidRecord = this.mActiveUids.get(i);
        if (uidRecord == null || uidRecord.isCurAllowListed() == z) {
            return;
        }
        uidRecord.setCurAllowListed(z);
        updateOomAdjLSP(10);
    }

    public void dumpProcessListVariablesLocked(ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1120986464305L, this.mAdjSeq);
        protoOutputStream.write(1120986464306L, this.mProcessList.getLruSeqLOSP());
        protoOutputStream.write(1120986464307L, this.mNumNonCachedProcs);
        protoOutputStream.write(1120986464309L, this.mNumServiceProcs);
        protoOutputStream.write(1120986464310L, this.mNewNumServiceProcs);
    }

    public void dumpSequenceNumbersLocked(PrintWriter printWriter) {
        printWriter.println("  mAdjSeq=" + this.mAdjSeq + " mLruSeq=" + this.mProcessList.getLruSeqLOSP());
    }

    public void dumpProcCountsLocked(PrintWriter printWriter) {
        printWriter.println("  mNumNonCachedProcs=" + this.mNumNonCachedProcs + " (" + this.mProcessList.getLruSizeLOSP() + " total) mNumCachedHiddenProcs=" + this.mNumCachedHiddenProcs + " mNumServiceProcs=" + this.mNumServiceProcs + " mNewNumServiceProcs=" + this.mNewNumServiceProcs);
    }

    public void dumpCachedAppOptimizerSettings(PrintWriter printWriter) {
        this.mCachedAppOptimizer.dump(printWriter);
    }

    public void dumpCacheOomRankerSettings(PrintWriter printWriter) {
        this.mCacheOomRanker.dump(printWriter);
    }

    public final void updateAppFreezeStateLSP(ProcessRecord processRecord, int i) {
        if (this.mCachedAppOptimizer.useFreezer() && !processRecord.mOptRecord.isFreezeExempt()) {
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            if (processCachedOptimizerRecord.isFrozen() && processCachedOptimizerRecord.shouldNotFreeze()) {
                this.mCachedAppOptimizer.unfreezeAppLSP(processRecord, CachedAppOptimizer.getUnfreezeReasonCodeFromOomAdjReason(i));
                return;
            }
            ProcessStateRecord processStateRecord = processRecord.mState;
            if (processStateRecord.getCurAdj() >= 830 && !processCachedOptimizerRecord.isFrozen() && !processCachedOptimizerRecord.shouldNotFreeze()) {
                this.mCachedAppOptimizer.freezeAppAsyncLSP(processRecord);
            } else if (processStateRecord.getSetAdj() < 830) {
                this.mCachedAppOptimizer.unfreezeAppLSP(processRecord, CachedAppOptimizer.getUnfreezeReasonCodeFromOomAdjReason(i));
            }
        }
    }

    public void unfreezeTemporarily(ProcessRecord processRecord, int i) {
        if (this.mCachedAppOptimizer.useFreezer()) {
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            if (processCachedOptimizerRecord.isFrozen() || processCachedOptimizerRecord.isPendingFreeze()) {
                ArrayList arrayList = this.mTmpProcessList;
                ActiveUids activeUids = this.mTmpUidRecords;
                this.mTmpProcessSet.add(processRecord);
                collectReachableProcessesLocked(this.mTmpProcessSet, arrayList, activeUids);
                this.mTmpProcessSet.clear();
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mCachedAppOptimizer.unfreezeTemporarily((ProcessRecord) arrayList.get(i2), i);
                }
                arrayList.clear();
            }
        }
    }
}
