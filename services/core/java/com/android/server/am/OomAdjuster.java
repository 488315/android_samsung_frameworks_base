package com.android.server.am;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.net.NetworkPolicyManager;
import android.os.Handler;
import android.os.ICustomFrequencyManager;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.am.ActiveServices;
import com.android.server.am.CacheOomRanker;
import com.android.server.am.CachedAppOptimizer;
import com.android.server.am.DynamicHiddenApp;
import com.android.server.chimera.GPUMemoryReclaimer;
import com.android.server.chimera.heimdall.HeimdallAlwaysRunningMonitor;
import com.android.server.chimera.ppn.PerProcessNandswap;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.MirrorActiveUids;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class OomAdjuster {
    public final String VENDING_PKG;
    public final ActiveUids mActiveUids;
    public int mAdjSeq;
    public ICustomFrequencyManager mCFMS;
    public final CacheOomRanker mCacheOomRanker;
    public final CachedAppOptimizer mCachedAppOptimizer;
    public final ActivityManagerConstants mConstants;
    public final DynamicHiddenApp mDynamicHiddenApp;
    public final ArraySet mFollowUpUpdateSet;
    public final GPUMemoryReclaimer mGPUMemoryReclaimer;
    public final HeimdallAlwaysRunningMonitor mHeimdallAlwaysRunningMonitor;
    public final Injector mInjector;
    public double mLastFreeSwapPercent;
    public int mLastReason;
    public final OomAdjusterDebugLogger mLogger;
    public int mNewNumAServiceProcs;
    public int mNewNumServiceProcs;
    public long mNextFollowUpUpdateUptimeMs;
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
    public final PerProcessNandswap mPerProcessNandswap;
    public final ActivityManagerGlobalLock mProcLock;
    public final Handler mProcessGroupHandler;
    public final ProcessList mProcessList;
    public int mProcessStateCurTop;
    public final ArraySet mProcessesInCycle;
    public final ArrayList mProcsToOomAdj;
    public final ActivityManagerService mService;
    public final ArrayList mTmpBecameIdle;
    public final ComputeOomAdjWindowCallback mTmpComputeOomAdjWindowCallback;
    public final ArrayList mTmpProcessList;
    public final ArraySet mTmpProcessSet;
    public final ArrayDeque mTmpQueue;
    public final int[] mTmpSchedGroup;
    public final ActiveUids mTmpUidRecords;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ComputeOomAdjWindowCallback {
        public int adj;
        public ProcessRecord app;
        public int appUid;
        public boolean foregroundActivities;
        public int logUid;
        public String mAdjType;
        public boolean mHasVisibleActivities;
        public int procState;
        public int processStateCurTop;
        public int schedGroup;

        public ComputeOomAdjWindowCallback() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Injector {
    }

    public OomAdjuster(ActivityManagerService activityManagerService, ProcessList processList, ActiveUids activeUids, ServiceThread serviceThread) {
        Injector injector = new Injector();
        this.mCFMS = null;
        this.mAdjSeq = 0;
        this.mNumServiceProcs = 0;
        this.mNewNumAServiceProcs = 0;
        this.mNewNumServiceProcs = 0;
        this.mNumNonCachedProcs = 0;
        this.mNumCachedHiddenProcs = 0;
        this.mTmpSchedGroup = new int[1];
        this.mDynamicHiddenApp = null;
        this.mNumCachedProcessCount = 0;
        this.mTmpProcessList = new ArrayList();
        new ArrayList();
        this.mTmpBecameIdle = new ArrayList();
        this.mTmpProcessSet = new ArraySet();
        this.mPendingProcessSet = new ArraySet();
        this.mProcessesInCycle = new ArraySet();
        this.mProcsToOomAdj = new ArrayList();
        this.mOomAdjUpdateOngoing = false;
        this.mPendingFullOomAdjUpdate = false;
        this.mProcessStateCurTop = 2;
        this.mFollowUpUpdateSet = new ArraySet();
        this.mNextFollowUpUpdateUptimeMs = Long.MAX_VALUE;
        this.VENDING_PKG = "com.android.vending";
        this.mLastFreeSwapPercent = 1.0d;
        this.mTmpComputeOomAdjWindowCallback = new ComputeOomAdjWindowCallback();
        this.mService = activityManagerService;
        this.mInjector = injector;
        this.mProcessList = processList;
        this.mProcLock = activityManagerService.mProcLock;
        this.mActiveUids = activeUids;
        ActivityManagerConstants activityManagerConstants = activityManagerService.mConstants;
        this.mConstants = activityManagerConstants;
        this.mCachedAppOptimizer = new CachedAppOptimizer(activityManagerService, null, new CachedAppOptimizer.DefaultProcessDependencies());
        this.mCacheOomRanker = new CacheOomRanker(activityManagerService, new CacheOomRanker.ProcessDependenciesImpl());
        this.mLogger = new OomAdjusterDebugLogger(this, activityManagerService.mConstants);
        this.mGPUMemoryReclaimer = GPUMemoryReclaimer.getInstance();
        this.mHeimdallAlwaysRunningMonitor = HeimdallAlwaysRunningMonitor.INSTANCE;
        this.mPerProcessNandswap = PerProcessNandswap.getInstance();
        this.mProcessGroupHandler = new Handler(serviceThread.getLooper(), new OomAdjuster$$ExternalSyntheticLambda2());
        this.mTmpUidRecords = new ActiveUids(activityManagerService, false);
        this.mTmpQueue = new ArrayDeque(activityManagerConstants.CUR_MAX_CACHED_PROCESSES << 1);
        this.mNumSlots = 10;
        boolean z = DynamicHiddenApp.DEBUG;
        DynamicHiddenApp dynamicHiddenApp = DynamicHiddenApp.DhaClassLazyHolder.INSTANCE;
        this.mDynamicHiddenApp = dynamicHiddenApp;
        dynamicHiddenApp.initDynamicHiddenApp(activityManagerService, processList, activityManagerConstants);
    }

    public static boolean evaluateConnectionPrelude(ProcessRecord processRecord, ProcessRecord processRecord2) {
        return processRecord == null || processRecord2 == null || processRecord2.isSdkSandbox || processRecord2.isolated || processRecord2.mKilledByAm || processRecord2.mKilled;
    }

    public static int getDefaultCapability(int i, ProcessRecord processRecord) {
        int i2;
        int defaultProcessNetworkCapabilities = NetworkPolicyManager.getDefaultProcessNetworkCapabilities(i);
        if (i == 0 || i == 1 || i == 2) {
            i2 = 127;
        } else if (i != 3) {
            i2 = 0;
            if (i == 4 && processRecord.mInstr != null) {
                i2 = 6;
            }
        } else {
            i2 = 16;
        }
        return i2 | defaultProcessNetworkCapabilities;
    }

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
                return "updateOomAdj_followUp";
            case 24:
                return "updateOomAdj_slowdown";
            case 25:
                return "updateOomAdj_fgsfilter";
            default:
                return "_unknown";
        }
    }

    public static boolean promoteSchedGroupIfNecessary(int i, int i2, ProcessStateRecord processStateRecord) {
        if (i != 2) {
            return false;
        }
        if ("fg-service-act".equals(processStateRecord.mAdjType) || "vis-activity".equals(processStateRecord.mAdjType)) {
            return true;
        }
        if (i2 == 0 && "service".equals(processStateRecord.mAdjType)) {
            return true;
        }
        return i2 == 3 && "provider".equals(processStateRecord.mAdjType);
    }

    public static int setIntermediateAdjLSP(ProcessRecord processRecord, int i, int i2) {
        ProcessStateRecord processStateRecord = processRecord.mState;
        processStateRecord.setCurRawAdj(i, false);
        int modifyRawOomAdj = processRecord.mServices.modifyRawOomAdj(i);
        int i3 = processStateRecord.mMaxAdj;
        if (modifyRawOomAdj > i3) {
            if (i3 <= 250) {
                i2 = 2;
            }
            modifyRawOomAdj = i3;
        }
        processStateRecord.setCurAdj(modifyRawOomAdj);
        return i2;
    }

    public static void updateAppUidRecLSP(ProcessRecord processRecord) {
        UidRecord uidRecord = processRecord.mUidRecord;
        if (uidRecord != null) {
            ProcessStateRecord processStateRecord = processRecord.mState;
            uidRecord.mEphemeral = processRecord.info.isInstantApp();
            int i = uidRecord.mCurProcState;
            int i2 = processStateRecord.mCurProcState;
            if (i > i2) {
                uidRecord.mCurProcState = i2;
            }
            if (processRecord.mServices.mHasForegroundServices) {
                uidRecord.mForegroundServices = true;
            }
            uidRecord.mCurCapability |= processStateRecord.mCurCapability;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:113:0x0363, code lost:
    
        if (r8 <= 250) goto L197;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x036f, code lost:
    
        if (com.android.server.chimera.heimdall.HeimdallAlwaysRunningMonitor.isAlwaysRunningAdj(r8) != false) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0371, code lost:
    
        if (r11 == 0) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0375, code lost:
    
        if (r11 > 250) goto L202;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x037c, code lost:
    
        r0.mHandler.sendMessage(r0.mHandler.obtainMessage(1, r8, r11, new com.android.server.chimera.heimdall.HeimdallAlwaysRunningProcInfo(r3, r4, r11, r6, r7)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0378, code lost:
    
        if (r11 == 0) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x037a, code lost:
    
        if (r8 == 0) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0369, code lost:
    
        if (com.android.server.chimera.heimdall.HeimdallAlwaysRunningMonitor.isAlwaysRunningAdj(r11) == false) goto L197;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x03ff  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0407  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0542  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0554  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0566  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0581 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0548  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x043f  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x03e8  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01e9  */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v58 */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r15v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean applyOomAdjLSP(final com.android.server.am.ProcessRecord r27, boolean r28, long r29, long r31, int r33, boolean r34) {
        /*
            Method dump skipped, instructions count: 1981
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.OomAdjuster.applyOomAdjLSP(com.android.server.am.ProcessRecord, boolean, long, long, int, boolean):boolean");
    }

    public final void assignCachedAdjIfNecessary(ArrayList arrayList) {
        int i;
        int i2;
        int i3;
        boolean z;
        boolean z2;
        int i4;
        BGProtectManager bGProtectManager;
        boolean z3;
        ArrayList arrayList2 = arrayList;
        int size = arrayList.size();
        ActivityManagerConstants activityManagerConstants = this.mConstants;
        if (activityManagerConstants.USE_TIERED_CACHED_ADJ) {
            this.mInjector.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            for (int i5 = size - 1; i5 >= 0; i5--) {
                ProcessRecord processRecord = (ProcessRecord) arrayList2.get(i5);
                ProcessStateRecord processStateRecord = processRecord.mState;
                if (!processRecord.mKilledByAm && processRecord.mThread != null && processStateRecord.mCurAdj >= 1001) {
                    ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
                    int i6 = (processCachedOptimizerRecord == null || !processCachedOptimizerRecord.mFreezeExempt) ? (processStateRecord.mSetAdj < 900 || processStateRecord.mLastStateTime + activityManagerConstants.TIERED_CACHED_ADJ_DECAY_TIME >= uptimeMillis) ? 910 : 950 : 900;
                    processStateRecord.setCurRawAdj(i6, false);
                    processStateRecord.setCurAdj(processRecord.mServices.modifyRawOomAdj(i6));
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
        int i11 = this.mNumSlots;
        int i12 = (i9 > 0 ? (i9 + i11) - 1 : 1) / i11;
        if (i12 < 1) {
            i12 = 1;
        }
        int i13 = ((i7 + i11) - 1) / i11;
        if (i13 < 1) {
            i13 = 1;
        }
        DynamicHiddenApp dynamicHiddenApp = this.mDynamicHiddenApp;
        if (dynamicHiddenApp != null && DynamicHiddenApp.BORA_POLICY_ENABLE) {
            dynamicHiddenApp.mBGProtectManager.recentActivityProcessList.clear();
        }
        int i14 = size - 1;
        int i15 = -1;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 915;
        int i21 = 905;
        int i22 = 900;
        int i23 = 910;
        int i24 = -1;
        while (i14 >= 0) {
            ProcessRecord processRecord2 = (ProcessRecord) arrayList2.get(i14);
            ProcessStateRecord processStateRecord2 = processRecord2.mState;
            if (!processRecord2.mKilledByAm && processRecord2.mThread != null) {
                i = i14;
                if (processStateRecord2.mCurAdj >= 1001) {
                    int i25 = processStateRecord2.mCurProcState;
                    ProcessServiceRecord processServiceRecord = processRecord2.mServices;
                    switch (i25) {
                        case 16:
                        case 17:
                        case 18:
                            int i26 = processServiceRecord.mConnectionGroup;
                            i2 = i13;
                            if (i26 != 0) {
                                int i27 = processServiceRecord.mConnectionImportance;
                                i3 = i15;
                                int i28 = processRecord2.uid;
                                if (i17 == i28 && i18 == i26) {
                                    if (i27 > i16) {
                                        if (i22 < i23 && i22 < 999) {
                                            i19++;
                                        }
                                        i16 = i27;
                                    }
                                    z2 = true;
                                    if (!z2 || i22 == i23) {
                                        i4 = i23;
                                        i23 = i22;
                                    } else {
                                        i24++;
                                        if (i24 >= i12) {
                                            i4 = i23 + 10;
                                            if (i4 > 999) {
                                                i4 = 999;
                                            }
                                            i24 = 0;
                                        } else {
                                            i4 = i23;
                                            i23 = i22;
                                        }
                                        i19 = 0;
                                    }
                                    int i29 = i23 + i19;
                                    z = false;
                                    processStateRecord2.setCurRawAdj(i29, false);
                                    processStateRecord2.setCurAdj(processServiceRecord.modifyRawOomAdj(i29));
                                    if (dynamicHiddenApp != null && DynamicHiddenApp.BORA_POLICY_ENABLE) {
                                        bGProtectManager = dynamicHiddenApp.mBGProtectManager;
                                        if (bGProtectManager.recentActivityProcessList.size() < bGProtectManager.recentActivityProcessLimit && BGProtectManager.isCachedOrPickedActivityProcess(processRecord2)) {
                                            bGProtectManager.recentActivityProcessList.add(processRecord2);
                                        }
                                    }
                                    i22 = i23;
                                    i23 = i4;
                                    continue;
                                } else {
                                    i18 = i26;
                                    i16 = i27;
                                    i17 = i28;
                                }
                            } else {
                                i3 = i15;
                            }
                            z2 = false;
                            if (z2) {
                            }
                            i4 = i23;
                            i23 = i22;
                            int i292 = i23 + i19;
                            z = false;
                            processStateRecord2.setCurRawAdj(i292, false);
                            processStateRecord2.setCurAdj(processServiceRecord.modifyRawOomAdj(i292));
                            if (dynamicHiddenApp != null) {
                                bGProtectManager = dynamicHiddenApp.mBGProtectManager;
                                if (bGProtectManager.recentActivityProcessList.size() < bGProtectManager.recentActivityProcessLimit) {
                                    bGProtectManager.recentActivityProcessList.add(processRecord2);
                                }
                            }
                            i22 = i23;
                            i23 = i4;
                            continue;
                        default:
                            if (i21 != i20 && (i15 = i15 + 1) >= i13) {
                                int i30 = i20 + 10;
                                i21 = i20;
                                if (i30 > 999) {
                                    z3 = false;
                                    i15 = 0;
                                    i20 = 999;
                                    processStateRecord2.setCurRawAdj(i21, z3);
                                    processStateRecord2.setCurAdj(processServiceRecord.modifyRawOomAdj(i21));
                                    break;
                                } else {
                                    i15 = 0;
                                    i20 = i30;
                                }
                            }
                            z3 = false;
                            processStateRecord2.setCurRawAdj(i21, z3);
                            processStateRecord2.setCurAdj(processServiceRecord.modifyRawOomAdj(i21));
                            break;
                    }
                }
            } else {
                i = i14;
            }
            i2 = i13;
            i3 = i15;
            z = false;
            i14 = i - 1;
            arrayList2 = arrayList;
            i13 = i2;
            i15 = i3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v5, types: [int] */
    /* JADX WARN: Type inference failed for: r7v7 */
    public final boolean collectReachableProcessesLocked(ArraySet arraySet, ArrayList arrayList, ActiveUids activeUids) {
        boolean z;
        ProcessStateRecord processStateRecord;
        int i;
        ProcessStateRecord processStateRecord2;
        int i2;
        ProcessStateRecord processStateRecord3;
        int i3;
        ArrayDeque arrayDeque = this.mTmpQueue;
        arrayDeque.clear();
        arrayList.clear();
        int size = arraySet.size();
        int i4 = 0;
        while (true) {
            z = true;
            if (i4 >= size) {
                break;
            }
            ProcessRecord processRecord = (ProcessRecord) arraySet.valueAt(i4);
            processRecord.mState.mReachable = true;
            arrayDeque.offer(processRecord);
            i4++;
        }
        activeUids.mActiveUids.clear();
        ProcessRecord processRecord2 = (ProcessRecord) arrayDeque.poll();
        boolean z2 = false;
        while (processRecord2 != null) {
            arrayList.add(processRecord2);
            UidRecord uidRecord = processRecord2.mUidRecord;
            if (uidRecord != null) {
                activeUids.put(uidRecord.mUid, uidRecord);
            }
            ProcessServiceRecord processServiceRecord = processRecord2.mServices;
            for (int size2 = processServiceRecord.mConnections.size() - (z ? 1 : 0); size2 >= 0; size2--) {
                ConnectionRecord connectionAt = processServiceRecord.getConnectionAt(size2);
                boolean hasFlag = connectionAt.hasFlag(2);
                AppBindRecord appBindRecord = connectionAt.binding;
                ProcessRecord processRecord3 = hasFlag ? appBindRecord.service.isolationHostProc : appBindRecord.service.app;
                if (processRecord3 != null && processRecord3 != processRecord2 && ((i3 = (processStateRecord3 = processRecord3.mState).mMaxAdj) < -900 || i3 >= 0)) {
                    boolean z3 = processStateRecord3.mReachable;
                    z2 |= z3;
                    if (!z3 && (!connectionAt.hasFlag(32) || !connectionAt.notHasFlag(134217856))) {
                        arrayDeque.offer(processRecord3);
                        processStateRecord3.mReachable = z;
                    }
                }
            }
            ProcessProviderRecord processProviderRecord = processRecord2.mProviders;
            for (int size3 = processProviderRecord.mConProviders.size() - (z ? 1 : 0); size3 >= 0; size3--) {
                ProcessRecord processRecord4 = ((ContentProviderConnection) processProviderRecord.mConProviders.get(size3)).provider.proc;
                if (processRecord4 != null && processRecord4 != processRecord2 && ((i2 = (processStateRecord2 = processRecord4.mState).mMaxAdj) < -900 || i2 >= 0)) {
                    boolean z4 = processStateRecord2.mReachable;
                    z2 |= z4;
                    if (!z4) {
                        arrayDeque.offer(processRecord4);
                        processStateRecord2.mReachable = z;
                    }
                }
            }
            List list = (List) this.mProcessList.mSdkSandboxes.get(processRecord2.uid);
            for (int size4 = (list != null ? list.size() : 0) - (z ? 1 : 0); size4 >= 0; size4--) {
                ProcessRecord processRecord5 = (ProcessRecord) list.get(size4);
                boolean z5 = processRecord5.mState.mReachable;
                z2 |= z5;
                if (!z5) {
                    arrayDeque.offer(processRecord5);
                    processRecord5.mState.mReachable = z;
                }
            }
            if (processRecord2.isSdkSandbox) {
                int size5 = processServiceRecord.mServices.size() - (z ? 1 : 0);
                boolean z6 = z;
                while (size5 >= 0) {
                    ArrayMap arrayMap = processServiceRecord.getRunningServiceAt(size5).connections;
                    int size6 = arrayMap.size() - (z6 ? 1 : 0);
                    ?? r7 = z6;
                    while (size6 >= 0) {
                        ArrayList arrayList2 = (ArrayList) arrayMap.valueAt(size6);
                        for (int size7 = arrayList2.size() - r7; size7 >= 0; size7--) {
                            ProcessRecord processRecord6 = ((ConnectionRecord) arrayList2.get(size7)).binding.attributedClient;
                            if (processRecord6 != null && processRecord6 != processRecord2 && (((i = (processStateRecord = processRecord6.mState).mMaxAdj) < -900 || i >= 0) && !processStateRecord.mReachable)) {
                                arrayDeque.offer(processRecord6);
                                processStateRecord.mReachable = true;
                            }
                        }
                        size6--;
                        r7 = 1;
                    }
                    size5--;
                    z6 = true;
                }
            }
            processRecord2 = (ProcessRecord) arrayDeque.poll();
            z = true;
        }
        int size8 = arrayList.size();
        if (size8 > 0) {
            int i5 = 0;
            for (int i6 = size8 - 1; i5 < i6; i6--) {
                ProcessRecord processRecord7 = (ProcessRecord) arrayList.get(i5);
                ProcessRecord processRecord8 = (ProcessRecord) arrayList.get(i6);
                processRecord7.mState.mReachable = false;
                processRecord8.mState.mReachable = false;
                arrayList.set(i5, processRecord8);
                arrayList.set(i6, processRecord7);
                i5++;
            }
        }
        return z2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:417:0x0838, code lost:
    
        if (r54 < (r9.lastActivity + r0.mConstants.MAX_SERVICE_INACTIVITY)) goto L500;
     */
    /* JADX WARN: Removed duplicated region for block: B:108:0x04f0  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x053c  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0547 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x05a1  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x05e4  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0603  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0633  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0653  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0667  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x066c  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0693  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x06c5  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x06e4  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x06eb  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0746  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0780  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x078b  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x079f  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x07c3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0a64  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0b88  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0bc3  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x0be4  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x0c5a  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x0c64  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0c6a  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0c75  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x0c9c  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0cd0  */
    /* JADX WARN: Removed duplicated region for block: B:378:0x0cdf  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x0bdf  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x0bbf  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x0b71 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:399:0x07ec  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x086a  */
    /* JADX WARN: Removed duplicated region for block: B:443:0x08b0  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x08cf  */
    /* JADX WARN: Removed duplicated region for block: B:508:0x06e6  */
    /* JADX WARN: Removed duplicated region for block: B:510:0x06df  */
    /* JADX WARN: Removed duplicated region for block: B:512:0x0669  */
    /* JADX WARN: Removed duplicated region for block: B:517:0x0644  */
    /* JADX WARN: Removed duplicated region for block: B:530:0x054e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:533:0x0574  */
    /* JADX WARN: Removed duplicated region for block: B:537:0x059c  */
    /* JADX WARN: Removed duplicated region for block: B:539:0x055b  */
    /* JADX WARN: Removed duplicated region for block: B:541:0x0563  */
    /* JADX WARN: Removed duplicated region for block: B:562:0x0540  */
    /* JADX WARN: Removed duplicated region for block: B:583:0x0486  */
    /* JADX WARN: Removed duplicated region for block: B:681:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:683:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x04c4  */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean computeOomAdjLSP(com.android.server.am.ProcessRecord r50, int r51, com.android.server.am.ProcessRecord r52, boolean r53, long r54, boolean r56, boolean r57, int r58, boolean r59) {
        /*
            Method dump skipped, instructions count: 3315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.OomAdjuster.computeOomAdjLSP(com.android.server.am.ProcessRecord, int, com.android.server.am.ProcessRecord, boolean, long, boolean, boolean, int, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean computeProviderHostOomAdjLSP(com.android.server.am.ContentProviderConnection r22, com.android.server.am.ProcessRecord r23, com.android.server.am.ProcessRecord r24, long r25, com.android.server.am.ProcessRecord r27, boolean r28, boolean r29, boolean r30, int r31, int r32, boolean r33, boolean r34) {
        /*
            Method dump skipped, instructions count: 491
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.OomAdjuster.computeProviderHostOomAdjLSP(com.android.server.am.ContentProviderConnection, com.android.server.am.ProcessRecord, com.android.server.am.ProcessRecord, long, com.android.server.am.ProcessRecord, boolean, boolean, boolean, int, int, boolean, boolean):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:249:0x01eb, code lost:
    
        if (r3 >= 250) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x021f, code lost:
    
        if (r3 >= 227) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x0245, code lost:
    
        if (r3 > 100) goto L153;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02f6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x030c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0315  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0327  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0337 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0430  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x044a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:184:0x044b  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x02c0  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x019d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean computeServiceHostOomAdjLSP(com.android.server.am.ConnectionRecord r29, com.android.server.am.ProcessRecord r30, com.android.server.am.ProcessRecord r31, long r32, com.android.server.am.ProcessRecord r34, boolean r35, boolean r36, boolean r37, int r38, int r39, boolean r40, boolean r41) {
        /*
            Method dump skipped, instructions count: 1122
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.OomAdjuster.computeServiceHostOomAdjLSP(com.android.server.am.ConnectionRecord, com.android.server.am.ProcessRecord, com.android.server.am.ProcessRecord, long, com.android.server.am.ProcessRecord, boolean, boolean, boolean, int, int, boolean, boolean):boolean");
    }

    public final void dumpCacheOomRankerSettings(PrintWriter printWriter) {
        CacheOomRanker cacheOomRanker = this.mCacheOomRanker;
        cacheOomRanker.getClass();
        printWriter.println("CacheOomRanker settings");
        synchronized (cacheOomRanker.mPhenotypeFlagLock) {
            printWriter.println("  use_oom_re_ranking=" + cacheOomRanker.mUseOomReRanking);
            printWriter.println("  oom_re_ranking_number_to_re_rank=" + cacheOomRanker.getNumberToReRank());
            printWriter.println("  oom_re_ranking_lru_weight=" + cacheOomRanker.mLruWeight);
            printWriter.println("  oom_re_ranking_uses_weight=" + cacheOomRanker.mUsesWeight);
            printWriter.println("  oom_re_ranking_rss_weight=" + cacheOomRanker.mRssWeight);
        }
    }

    public final void dumpCachedAppOptimizerSettings(PrintWriter printWriter) {
        CachedAppOptimizer cachedAppOptimizer = this.mCachedAppOptimizer;
        cachedAppOptimizer.getClass();
        printWriter.println("CachedAppOptimizer settings");
        synchronized (cachedAppOptimizer.mPhenotypeFlagLock) {
            try {
                printWriter.println("  use_compaction=" + cachedAppOptimizer.mUseCompaction);
                printWriter.println("  compact_throttle_1=" + cachedAppOptimizer.mCompactThrottleSomeSome);
                printWriter.println("  compact_throttle_2=" + cachedAppOptimizer.mCompactThrottleSomeFull);
                printWriter.println("  compact_throttle_3=" + cachedAppOptimizer.mCompactThrottleFullSome);
                printWriter.println("  compact_throttle_4=" + cachedAppOptimizer.mCompactThrottleFullFull);
                printWriter.println("  compact_throttle_min_oom_adj=" + cachedAppOptimizer.mCompactThrottleMinOomAdj);
                printWriter.println("  compact_throttle_max_oom_adj=" + cachedAppOptimizer.mCompactThrottleMaxOomAdj);
                printWriter.println("  compact_statsd_sample_rate=" + cachedAppOptimizer.mCompactStatsdSampleRate);
                printWriter.println("  compact_full_rss_throttle_kb=" + cachedAppOptimizer.mFullAnonRssThrottleKb);
                printWriter.println("  compact_full_delta_rss_throttle_kb=" + cachedAppOptimizer.mFullDeltaRssThrottleKb);
                StringBuilder sb = new StringBuilder("  compact_proc_state_throttle=");
                sb.append(Arrays.toString(cachedAppOptimizer.mProcStateThrottle.toArray(new Integer[0])));
                printWriter.println(sb.toString());
                printWriter.println(" Per-Process Compaction Stats");
                long j = 0;
                long j2 = 0;
                for (CachedAppOptimizer.AggregatedProcessCompactionStats aggregatedProcessCompactionStats : cachedAppOptimizer.mPerProcessCompactStats.values()) {
                    printWriter.println("-----" + aggregatedProcessCompactionStats.processName + "-----");
                    j += aggregatedProcessCompactionStats.mSomeCompactPerformed;
                    j2 += aggregatedProcessCompactionStats.mFullCompactPerformed;
                    aggregatedProcessCompactionStats.dump(printWriter);
                    printWriter.println();
                }
                printWriter.println();
                printWriter.println(" Per-Source Compaction Stats");
                for (CachedAppOptimizer.AggregatedSourceCompactionStats aggregatedSourceCompactionStats : cachedAppOptimizer.mPerSourceCompactStats.values()) {
                    printWriter.println("-----" + aggregatedSourceCompactionStats.sourceType + "-----");
                    aggregatedSourceCompactionStats.dump(printWriter);
                    printWriter.println();
                }
                printWriter.println();
                printWriter.println("Total Compactions Performed by profile: " + j + " some, " + j2 + " full");
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Total compactions downgraded: ");
                sb2.append(cachedAppOptimizer.mTotalCompactionDowngrades);
                printWriter.println(sb2.toString());
                printWriter.println("Total compactions cancelled by reason: ");
                for (CachedAppOptimizer.CancelCompactReason cancelCompactReason : cachedAppOptimizer.mTotalCompactionsCancelled.keySet()) {
                    printWriter.println("    " + cancelCompactReason + ": " + cachedAppOptimizer.mTotalCompactionsCancelled.get(cancelCompactReason));
                }
                printWriter.println();
                printWriter.println(" System Compaction Memory Stats");
                printWriter.println("    Compactions Performed: 0");
                printWriter.println("    Total Memory Freed (KB): 0");
                printWriter.println("    Avg Mem Freed per Compact (KB): 0.0");
                printWriter.println();
                printWriter.println("  Tracking last compaction stats for " + cachedAppOptimizer.mLastCompactionStats.size() + " processes.");
                printWriter.println("Last Compaction per process stats:");
                printWriter.println("    (ProcessName,Source,DeltaAnonRssKBs,ZramConsumedKBs,AnonMemFreedKBs,CompactEfficiency,CompactCost(ms/MB),procState,oomAdj,oomAdjReason)");
                Iterator it = cachedAppOptimizer.mLastCompactionStats.entrySet().iterator();
                while (it.hasNext()) {
                    ((CachedAppOptimizer.SingleCompactionStats) ((Map.Entry) it.next()).getValue()).dump(printWriter);
                }
                printWriter.println();
                printWriter.println("Last 20 Compactions Stats:");
                printWriter.println("    (ProcessName,Source,DeltaAnonRssKBs,ZramConsumedKBs,AnonMemFreedKBs,CompactEfficiency,CompactCost(ms/MB),procState,oomAdj,oomAdjReason)");
                Iterator it2 = cachedAppOptimizer.mCompactionStatsHistory.iterator();
                while (it2.hasNext()) {
                    ((CachedAppOptimizer.SingleCompactionStats) it2.next()).dump(printWriter);
                }
                printWriter.println();
                printWriter.println("  use_freezer=" + cachedAppOptimizer.mUseFreezer);
                printWriter.println("  freeze_statsd_sample_rate=" + cachedAppOptimizer.mFreezerStatsdSampleRate);
                printWriter.println("  freeze_debounce_timeout=" + cachedAppOptimizer.mFreezerDebounceTimeout);
                printWriter.println("  freeze_exempt_inst_pkg=" + cachedAppOptimizer.mFreezerExemptInstPkg);
                printWriter.println("  freeze_binder_enabled=" + cachedAppOptimizer.mFreezerBinderEnabled);
                printWriter.println("  freeze_binder_threshold=" + cachedAppOptimizer.mFreezerBinderThreshold);
                printWriter.println("  freeze_binder_divisor=" + cachedAppOptimizer.mFreezerBinderDivisor);
                printWriter.println("  freeze_binder_offset=" + cachedAppOptimizer.mFreezerBinderOffset);
                printWriter.println("  freeze_binder_callback_enabled=" + cachedAppOptimizer.mFreezerBinderCallbackEnabled);
                printWriter.println("  freeze_binder_callback_throttle=" + cachedAppOptimizer.mFreezerBinderCallbackThrottle);
                printWriter.println("  freeze_binder_async_threshold=" + cachedAppOptimizer.mFreezerBinderAsyncThreshold);
                ActivityManagerGlobalLock activityManagerGlobalLock = cachedAppOptimizer.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        int size = cachedAppOptimizer.mFrozenProcesses.size();
                        printWriter.println("  Apps frozen: " + size);
                        for (int i = 0; i < size; i++) {
                            ProcessRecord processRecord = (ProcessRecord) cachedAppOptimizer.mFrozenProcesses.valueAt(i);
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("    ");
                            sb3.append(processRecord.mOptRecord.mFreezeUnfreezeTime);
                            sb3.append(": ");
                            sb3.append(processRecord.mPid);
                            sb3.append(" ");
                            sb3.append(processRecord.processName);
                            sb3.append(processRecord.mOptRecord.mFreezeSticky ? " (sticky)" : "");
                            printWriter.println(sb3.toString());
                        }
                        if (!cachedAppOptimizer.mPendingCompactionProcesses.isEmpty()) {
                            printWriter.println("  Pending compactions:");
                            int size2 = cachedAppOptimizer.mPendingCompactionProcesses.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                ProcessRecord processRecord2 = (ProcessRecord) cachedAppOptimizer.mPendingCompactionProcesses.get(i2);
                                printWriter.println("    pid: " + processRecord2.mPid + ". name: " + processRecord2.processName + ". hasPendingCompact: " + processRecord2.mOptRecord.mPendingCompact);
                            }
                        }
                    } finally {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println("FCA Feature enable:true");
    }

    public final int enqueuePendingTopAppIfNecessaryLSP() {
        ActivityManagerService activityManagerService = this.mService;
        int topProcessState = activityManagerService.mAtmInternal.getTopProcessState();
        activityManagerService.enqueuePendingTopAppIfNecessaryLocked();
        int topProcessState2 = activityManagerService.mAtmInternal.getTopProcessState();
        if (topProcessState != topProcessState2) {
            activityManagerService.enqueuePendingTopAppIfNecessaryLocked();
        }
        return topProcessState2;
    }

    public final boolean evaluateProviderConnectionAdd(ProcessRecord processRecord, ProcessRecord processRecord2) {
        if (evaluateConnectionPrelude(processRecord, processRecord2)) {
            return true;
        }
        ProcessStateRecord processStateRecord = processRecord2.mState;
        int i = processStateRecord.mSetAdj;
        ProcessStateRecord processStateRecord2 = processRecord.mState;
        if (i <= processStateRecord2.mSetAdj && processStateRecord.mSetProcState <= processStateRecord2.mSetProcState) {
            return false;
        }
        this.mInjector.getClass();
        return computeProviderHostOomAdjLSP(null, processRecord2, processRecord, SystemClock.uptimeMillis(), this.mService.getTopApp(), false, false, false, 0, FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, false, true);
    }

    public int getInitialAdj(ProcessRecord processRecord) {
        return processRecord.mState.mCurAdj;
    }

    public int getInitialCapability(ProcessRecord processRecord) {
        return processRecord.mState.mCurCapability;
    }

    public boolean getInitialIsCurBoundByNonBgRestrictedApp(ProcessRecord processRecord) {
        return processRecord.mState.mCurBoundByNonBgRestrictedApp;
    }

    public int getInitialProcState(ProcessRecord processRecord) {
        return processRecord.mState.mCurProcState;
    }

    public void handleUserSwitchedLocked() {
        this.mProcessList.forEachLruProcessesLOSP(new Consumer() { // from class: com.android.server.am.OomAdjuster$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ProcessRecord processRecord = (ProcessRecord) obj;
                ArraySet arraySet = OomAdjuster.this.mService.mConstants.KEEP_WARMING_SERVICES;
                PackageList packageList = processRecord.mPkgList;
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    if (packageList.containsKey(((ComponentName) arraySet.valueAt(size)).getPackageName())) {
                        ProcessServiceRecord processServiceRecord = processRecord.mServices;
                        for (int size2 = processServiceRecord.mServices.size() - 1; size2 >= 0; size2--) {
                            processServiceRecord.getRunningServiceAt(size2).updateKeepWarmLocked();
                        }
                        return;
                    }
                }
            }
        }, false);
    }

    public final void idleUidsLocked() {
        int size = this.mActiveUids.mActiveUids.size();
        this.mService.mHandler.removeMessages(58);
        if (size <= 0) {
            return;
        }
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = elapsedRealtime - this.mConstants.BACKGROUND_SETTLE_TIME;
        PowerManagerInternal powerManagerInternal = this.mService.mLocalPowerManager;
        if (powerManagerInternal != null) {
            powerManagerInternal.startUidChanges();
        }
        int i = size - 1;
        long j2 = 0;
        boolean z = false;
        while (i >= 0) {
            UidRecord valueAt = this.mActiveUids.valueAt(i);
            long j3 = j2;
            long j4 = valueAt.mLastBackgroundTime;
            long j5 = elapsedRealtime;
            long j6 = valueAt.mLastIdleTimeIfStillIdle;
            if (j4 <= 0 || (valueAt.mIdle && j6 != 0)) {
                elapsedRealtime = j5;
            } else if (j4 <= j) {
                EventLog.writeEvent(30055, valueAt.mUid);
                ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        valueAt.mIdle = true;
                        valueAt.mSetIdle = true;
                        elapsedRealtime = j5;
                        valueAt.mLastIdleTimeIfStillIdle = elapsedRealtime;
                        if (elapsedRealtime > 0) {
                            valueAt.mRealLastIdleTime = elapsedRealtime;
                        }
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                this.mService.doStopUidLocked(valueAt.mUid, valueAt);
            } else {
                elapsedRealtime = j5;
                if (j3 == 0 || j3 > j4) {
                    j3 = j4;
                }
                if (this.mLogger.shouldLog(valueAt.mUid)) {
                    z = true;
                }
            }
            i--;
            j2 = j3;
        }
        long j7 = j2;
        PowerManagerInternal powerManagerInternal2 = this.mService.mLocalPowerManager;
        if (powerManagerInternal2 != null) {
            powerManagerInternal2.finishUidChanges();
        }
        if (this.mService.mConstants.mKillBgRestrictedAndCachedIdle) {
            ArraySet arraySet = this.mProcessList.mAppsInBackgroundRestricted;
            int size2 = arraySet.size();
            for (int i2 = 0; i2 < size2; i2++) {
                long killAppIfBgRestrictedAndCachedIdleLocked = this.mProcessList.killAppIfBgRestrictedAndCachedIdleLocked((ProcessRecord) arraySet.valueAt(i2), elapsedRealtime) - this.mConstants.BACKGROUND_SETTLE_TIME;
                if (killAppIfBgRestrictedAndCachedIdleLocked > 0 && (j7 == 0 || j7 > killAppIfBgRestrictedAndCachedIdleLocked)) {
                    j7 = killAppIfBgRestrictedAndCachedIdleLocked;
                }
            }
        }
        if (j7 > 0) {
            long j8 = (j7 + this.mConstants.BACKGROUND_SETTLE_TIME) - elapsedRealtime;
            if (z) {
                EventLogTags.writeAmOomAdjMisc(3, 0, 0, this.mLogger.mOomAdjuster.mAdjSeq, (int) j8, "");
            }
            this.mService.mHandler.sendEmptyMessageDelayed(58, j8);
        }
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
    
        if (r14 > (r8 + (r1 ? r12.mConstants.SERVICE_USAGE_INTERACTION_TIME_POST_S : r12.mConstants.SERVICE_USAGE_INTERACTION_TIME_PRE_S))) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void maybeUpdateUsageStatsLSP(com.android.server.am.ProcessRecord r13, long r14) {
        /*
            r12 = this;
            com.android.server.am.ProcessStateRecord r0 = r13.mState
            com.android.server.am.ActivityManagerService r1 = r12.mService
            android.app.usage.UsageStatsManagerInternal r1 = r1.mUsageStatsService
            if (r1 != 0) goto L9
            return
        L9:
            r1 = 2
            boolean r1 = r0.getCachedCompatChange(r1)
            int r2 = r0.mCurProcState
            boolean r2 = android.app.ActivityManager.isProcStateConsideredInteraction(r2)
            r3 = 1
            r4 = 6
            r5 = 0
            r6 = 0
            if (r2 == 0) goto L24
            r0.mFgInteractionTime = r6
            com.android.server.am.ProcessRecord r2 = r0.mApp
            com.android.server.wm.WindowProcessController r2 = r2.mWindowProcessController
            r2.mFgInteractionTime = r6
            goto L56
        L24:
            int r2 = r0.mCurProcState
            r8 = 4
            if (r2 > r8) goto L4a
            long r8 = r0.mFgInteractionTime
            int r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r2 != 0) goto L39
            r0.mFgInteractionTime = r14
            com.android.server.am.ProcessRecord r2 = r0.mApp
            com.android.server.wm.WindowProcessController r2 = r2.mWindowProcessController
            r2.mFgInteractionTime = r14
        L37:
            r3 = r5
            goto L56
        L39:
            if (r1 == 0) goto L40
            com.android.server.am.ActivityManagerConstants r2 = r12.mConstants
            long r10 = r2.SERVICE_USAGE_INTERACTION_TIME_POST_S
            goto L44
        L40:
            com.android.server.am.ActivityManagerConstants r2 = r12.mConstants
            long r10 = r2.SERVICE_USAGE_INTERACTION_TIME_PRE_S
        L44:
            long r8 = r8 + r10
            int r2 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
            if (r2 <= 0) goto L37
            goto L56
        L4a:
            if (r2 > r4) goto L4d
            goto L4e
        L4d:
            r3 = r5
        L4e:
            r0.mFgInteractionTime = r6
            com.android.server.am.ProcessRecord r2 = r0.mApp
            com.android.server.wm.WindowProcessController r2 = r2.mWindowProcessController
            r2.mFgInteractionTime = r6
        L56:
            if (r1 == 0) goto L5d
            com.android.server.am.ActivityManagerConstants r1 = r12.mConstants
            long r1 = r1.USAGE_STATS_INTERACTION_INTERVAL_POST_S
            goto L61
        L5d:
            com.android.server.am.ActivityManagerConstants r1 = r12.mConstants
            long r1 = r1.USAGE_STATS_INTERACTION_INTERVAL_PRE_S
        L61:
            if (r3 == 0) goto L90
            boolean r8 = r0.mReportedInteraction
            if (r8 == 0) goto L6f
            long r8 = r0.mInteractionEventTime
            long r8 = r14 - r8
            int r1 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r1 <= 0) goto L90
        L6f:
            r0.mInteractionEventTime = r14
            com.android.server.am.ProcessRecord r1 = r0.mApp
            com.android.server.wm.WindowProcessController r1 = r1.mWindowProcessController
            r1.mInteractionEventTime = r14
            com.android.server.am.PackageList r14 = r13.mPkgList
            java.lang.String[] r14 = r14.getPackageList()
            if (r14 == 0) goto L90
        L7f:
            int r15 = r14.length
            if (r5 >= r15) goto L90
            com.android.server.am.ActivityManagerService r15 = r12.mService
            android.app.usage.UsageStatsManagerInternal r15 = r15.mUsageStatsService
            r1 = r14[r5]
            int r2 = r13.userId
            r15.reportEvent(r2, r4, r1)
            int r5 = r5 + 1
            goto L7f
        L90:
            r0.mReportedInteraction = r3
            if (r3 != 0) goto L9c
            r0.mInteractionEventTime = r6
            com.android.server.am.ProcessRecord r12 = r0.mApp
            com.android.server.wm.WindowProcessController r12 = r12.mWindowProcessController
            r12.mInteractionEventTime = r6
        L9c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.OomAdjuster.maybeUpdateUsageStatsLSP(com.android.server.am.ProcessRecord, long):void");
    }

    public void onProcessEndLocked(ProcessRecord processRecord) {
    }

    public void onProcessOomAdjChanged(int i, ProcessRecord processRecord) {
    }

    public void onProcessStateChanged(int i, ProcessRecord processRecord) {
    }

    public final void onWakefulnessChanged(int i) {
        CachedAppOptimizer cachedAppOptimizer = this.mCachedAppOptimizer;
        if (i != 1) {
            cachedAppOptimizer.getClass();
            return;
        }
        if (cachedAppOptimizer.useCompaction()) {
            CachedAppOptimizer.CancelCompactReason cancelCompactReason = CachedAppOptimizer.CancelCompactReason.SCREEN_ON;
            ActivityManagerGlobalLock activityManagerGlobalLock = cachedAppOptimizer.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                while (!cachedAppOptimizer.mPendingCompactionProcesses.isEmpty()) {
                    try {
                        cachedAppOptimizer.cancelCompactionForProcess((ProcessRecord) cachedAppOptimizer.mPendingCompactionProcesses.get(0), cancelCompactReason);
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                cachedAppOptimizer.mPendingCompactionProcesses.clear();
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
    }

    public void performUpdateOomAdjLSP(int i) {
        ActivityManagerService activityManagerService = this.mService;
        ProcessRecord topApp = activityManagerService.getTopApp();
        this.mProcessStateCurTop = activityManagerService.mAtmInternal.getTopProcessState();
        this.mPendingProcessSet.clear();
        AppProfiler appProfiler = activityManagerService.mAppProfiler;
        appProfiler.getClass();
        appProfiler.getClass();
        updateOomAdjInnerLSP(i, topApp, null, null, true, true);
    }

    public void performUpdateOomAdjLSP(int i, ProcessRecord processRecord) {
        ActivityManagerService activityManagerService = this.mService;
        ProcessRecord topApp = activityManagerService.getTopApp();
        this.mLastReason = i;
        Trace.traceBegin(64L, oomAdjReasonToString(i));
        ProcessStateRecord processStateRecord = processRecord.mState;
        ArrayList arrayList = this.mTmpProcessList;
        this.mPendingProcessSet.add(processRecord);
        this.mProcessStateCurTop = enqueuePendingTopAppIfNecessaryLSP();
        ArraySet arraySet = this.mPendingProcessSet;
        ActiveUids activeUids = this.mTmpUidRecords;
        boolean collectReachableProcessesLocked = collectReachableProcessesLocked(arraySet, arrayList, activeUids);
        this.mPendingProcessSet.clear();
        if (arrayList.size() > 0) {
            updateOomAdjInnerLSP(i, topApp, arrayList, activeUids, collectReachableProcessesLocked, false);
        } else if (processStateRecord.mCurRawAdj == 1001) {
            arrayList.add(processRecord);
            assignCachedAdjIfNecessary(arrayList);
            this.mInjector.getClass();
            applyOomAdjLSP(processRecord, false, SystemClock.uptimeMillis(), SystemClock.elapsedRealtime(), i, false);
        }
        this.mTmpProcessList.clear();
        PendingStartActivityUids pendingStartActivityUids = activityManagerService.mPendingStartActivityUids;
        synchronized (pendingStartActivityUids) {
            pendingStartActivityUids.mPendingUids.clear();
        }
        Trace.traceEnd(64L);
    }

    public void performUpdateOomAdjPendingTargetsLocked(int i) {
        ProcessRecord topApp = this.mService.getTopApp();
        this.mLastReason = i;
        Trace.traceBegin(64L, oomAdjReasonToString(i));
        this.mProcessStateCurTop = enqueuePendingTopAppIfNecessaryLSP();
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
        PendingStartActivityUids pendingStartActivityUids = this.mService.mPendingStartActivityUids;
        synchronized (pendingStartActivityUids) {
            pendingStartActivityUids.mPendingUids.clear();
        }
        Trace.traceEnd(64L);
    }

    /* JADX WARN: Code restructure failed: missing block: B:244:0x0186, code lost:
    
        if (com.android.server.am.DynamicHiddenApp.sHH_AMSExceptionEnable != false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x018f, code lost:
    
        if (r10.AMSExceptionFlag == r7.getValue()) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x01bc, code lost:
    
        if (r10.AMSExceptionFlag == r7.getValue()) goto L70;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:131:0x059e  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x05c6  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0611  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x05e9  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01e9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x021e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void postUpdateOomAdjInnerLSP(int r43, com.android.server.am.ActiveUids r44, long r45, long r47, long r49, boolean r51) {
        /*
            Method dump skipped, instructions count: 2160
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.OomAdjuster.postUpdateOomAdjInnerLSP(int, com.android.server.am.ActiveUids, long, long, long, boolean):void");
    }

    public final void reportOomAdjMessageLocked(String str) {
        Slog.d("ActivityManager", str);
        synchronized (this.mService.mOomAdjObserverLock) {
            try {
                ActivityManagerService activityManagerService = this.mService;
                if (activityManagerService.mCurOomAdjObserver != null) {
                    activityManagerService.mUiHandler.obtainMessage(70, str).sendToTarget();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void resetInternal() {
    }

    public final void setAttachingProcessStatesLSP(ProcessRecord processRecord) {
        int i;
        int i2 = 2;
        int i3 = CoreRune.SYSPERF_BOOST_OPT ? 6 : 2;
        ProcessStateRecord processStateRecord = processRecord.mState;
        int i4 = processStateRecord.mCurProcState;
        int i5 = processStateRecord.mCurRawAdj;
        int i6 = processRecord.isActiveLaunch ? 7 : 19;
        if (processStateRecord.mHasForegroundActivities) {
            try {
                processRecord.mWindowProcessController.onTopProcChanged();
                if (processRecord.useFifoUiScheduling()) {
                    ActivityManagerService.scheduleAsFifoPriority(processRecord.mPid, true);
                } else {
                    Process.setThreadPriority(processRecord.mPid, -10);
                }
            } catch (Exception e) {
                Slog.w("OomAdjuster", "Failed to pre-set top priority to " + processRecord + " " + e);
            }
            if (this.mService.mWakefulness.get() != 1) {
                if (!processStateRecord.mRunningRemoteAnimation) {
                    i2 = i6;
                    i6 = i2;
                    i = 127;
                    processStateRecord.setCurrentSchedulingGroup(i3);
                    processStateRecord.setCurProcState(i6);
                    processStateRecord.mCurRawProcState = i6;
                    processStateRecord.mCurCapability = i;
                    processStateRecord.setCurAdj(0);
                    processStateRecord.setCurRawAdj(0, false);
                    processStateRecord.mForcingToImportant = null;
                    processStateRecord.mHasShownUi = false;
                    onProcessStateChanged(i4, processRecord);
                    onProcessOomAdjChanged(i5, processRecord);
                }
            }
            i3 = 3;
            i6 = i2;
            i = 127;
            processStateRecord.setCurrentSchedulingGroup(i3);
            processStateRecord.setCurProcState(i6);
            processStateRecord.mCurRawProcState = i6;
            processStateRecord.mCurCapability = i;
            processStateRecord.setCurAdj(0);
            processStateRecord.setCurRawAdj(0, false);
            processStateRecord.mForcingToImportant = null;
            processStateRecord.mHasShownUi = false;
            onProcessStateChanged(i4, processRecord);
            onProcessOomAdjChanged(i5, processRecord);
        }
        i = 0;
        processStateRecord.setCurrentSchedulingGroup(i3);
        processStateRecord.setCurProcState(i6);
        processStateRecord.mCurRawProcState = i6;
        processStateRecord.mCurCapability = i;
        processStateRecord.setCurAdj(0);
        processStateRecord.setCurRawAdj(0, false);
        processStateRecord.mForcingToImportant = null;
        processStateRecord.mHasShownUi = false;
        onProcessStateChanged(i4, processRecord);
        onProcessOomAdjChanged(i5, processRecord);
    }

    public final void setIntermediateSchedGroupLSP(ProcessStateRecord processStateRecord, int i) {
        if (processStateRecord.mCurProcState >= 5 && this.mService.mWakefulness.get() != 1 && !processStateRecord.mScheduleLikeTopApp && i > 1) {
            i = 1;
        }
        processStateRecord.setCurrentSchedulingGroup(i);
    }

    public final boolean shouldSkipDueToCycle(ProcessRecord processRecord, ProcessStateRecord processStateRecord, int i, int i2, boolean z) {
        if (processStateRecord.mContainsCycle) {
            processRecord.mState.mContainsCycle = true;
            this.mProcessesInCycle.add(processRecord);
            if (processStateRecord.mCompletedAdjSeq < this.mAdjSeq && z && processStateRecord.mCurRawProcState >= i && processStateRecord.mCurRawAdj >= i2) {
                int i3 = processStateRecord.mCurCapability;
                if ((processRecord.mState.mCurCapability & i3) == i3) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void unfreezeTemporarily(int i, ProcessRecord processRecord) {
        CachedAppOptimizer cachedAppOptimizer = this.mCachedAppOptimizer;
        if (cachedAppOptimizer.useFreezer()) {
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            if (processCachedOptimizerRecord.mFrozen || processCachedOptimizerRecord.mPendingFreeze) {
                ArrayList arrayList = this.mTmpProcessList;
                this.mTmpProcessSet.add(processRecord);
                collectReachableProcessesLocked(this.mTmpProcessSet, arrayList, this.mTmpUidRecords);
                this.mTmpProcessSet.clear();
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    cachedAppOptimizer.unfreezeTemporarily(i, cachedAppOptimizer.mFreezerDebounceTimeout, (ProcessRecord) arrayList.get(i2));
                }
                arrayList.clear();
            }
        }
    }

    public final void updateAppFreezeStateLSP(ProcessRecord processRecord, int i, boolean z) {
        CachedAppOptimizer cachedAppOptimizer = this.mCachedAppOptimizer;
        if (cachedAppOptimizer.useFreezer()) {
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            if (processCachedOptimizerRecord.mFreezeExempt) {
                return;
            }
            boolean z2 = processCachedOptimizerRecord.mFrozen;
            if (z2 && processCachedOptimizerRecord.mShouldNotFreeze) {
                cachedAppOptimizer.unfreezeAppLSP(CachedAppOptimizer.getUnfreezeReasonCodeFromOomAdjReason(i), processRecord);
                return;
            }
            ProcessStateRecord processStateRecord = processRecord.mState;
            if (processStateRecord.mCurAdj < 850 || z2 || processCachedOptimizerRecord.mShouldNotFreeze) {
                if (processStateRecord.mSetAdj < 850) {
                    cachedAppOptimizer.unfreezeAppLSP(CachedAppOptimizer.getUnfreezeReasonCodeFromOomAdjReason(i), processRecord);
                }
            } else if (z) {
                cachedAppOptimizer.freezeAppAsyncInternalLSP(CachedAppOptimizer.updateEarliestFreezableTime(processRecord, 0L), processRecord, false);
            } else {
                cachedAppOptimizer.freezeAppAsyncInternalLSP(CachedAppOptimizer.updateEarliestFreezableTime(processRecord, cachedAppOptimizer.mFreezerDebounceTimeout), processRecord, false);
            }
        }
    }

    public final void updateOomAdjFollowUpTargetsLocked() {
        this.mInjector.getClass();
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mNextFollowUpUpdateUptimeMs = Long.MAX_VALUE;
        long j = Long.MAX_VALUE;
        for (int size = this.mFollowUpUpdateSet.size() - 1; size >= 0; size--) {
            ProcessRecord processRecord = (ProcessRecord) this.mFollowUpUpdateSet.valueAtUnchecked(size);
            long j2 = processRecord.mState.mFollowupUpdateUptimeMs;
            if (processRecord.mKilled) {
                this.mFollowUpUpdateSet.removeAt(size);
            } else if (j2 <= uptimeMillis) {
                this.mPendingProcessSet.add(processRecord);
                processRecord.mState.mFollowupUpdateUptimeMs = Long.MAX_VALUE;
                this.mFollowUpUpdateSet.removeAt(size);
            } else if (j2 < j) {
                j = j2;
            } else if (j2 == Long.MAX_VALUE) {
                this.mFollowUpUpdateSet.removeAt(size);
            }
        }
        if (j != Long.MAX_VALUE) {
            long j3 = this.mConstants.FOLLOW_UP_OOMADJ_UPDATE_WAIT_DURATION;
            if (j + j3 < this.mNextFollowUpUpdateUptimeMs) {
                long j4 = uptimeMillis + j3;
                if (j < j4) {
                    j = j4;
                }
                this.mNextFollowUpUpdateUptimeMs = j;
                this.mService.mHandler.sendEmptyMessageAtTime(86, j);
            }
        }
        updateOomAdjPendingTargetsLocked(23);
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x032c  */
    /* JADX WARN: Removed duplicated region for block: B:160:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateOomAdjInnerLSP(int r34, com.android.server.am.ProcessRecord r35, java.util.ArrayList r36, com.android.server.am.ActiveUids r37, boolean r38, boolean r39) {
        /*
            Method dump skipped, instructions count: 819
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.OomAdjuster.updateOomAdjInnerLSP(int, com.android.server.am.ProcessRecord, java.util.ArrayList, com.android.server.am.ActiveUids, boolean, boolean):void");
    }

    public final void updateOomAdjLSP(int i) {
        if (this.mOomAdjUpdateOngoing) {
            this.mPendingFullOomAdjUpdate = true;
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

    public final void updateOomAdjPendingTargetsLocked(int i) {
        if (!this.mPendingFullOomAdjUpdate) {
            if (this.mPendingProcessSet.isEmpty() || this.mOomAdjUpdateOngoing) {
                return;
            }
            try {
                this.mOomAdjUpdateOngoing = true;
                performUpdateOomAdjPendingTargetsLocked(i);
                return;
            } finally {
                this.mOomAdjUpdateOngoing = false;
                updateOomAdjPendingTargetsLocked(i);
            }
        }
        this.mPendingFullOomAdjUpdate = false;
        this.mPendingProcessSet.clear();
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

    public final void updateUidsLSP(ActiveUids activeUids, long j) {
        int i;
        int i2;
        int i3;
        ActiveServices.ServiceMap serviceMap;
        boolean z;
        ActiveUids activeUids2 = activeUids;
        this.mProcessList.incrementProcStateSeqAndNotifyAppsLOSP(activeUids2);
        ArrayList arrayList = this.mTmpBecameIdle;
        arrayList.clear();
        PowerManagerInternal powerManagerInternal = this.mService.mLocalPowerManager;
        if (powerManagerInternal != null) {
            powerManagerInternal.startUidChanges();
        }
        boolean z2 = true;
        int size = activeUids2.mActiveUids.size() - 1;
        while (size >= 0) {
            UidRecord valueAt = activeUids2.valueAt(size);
            int i4 = valueAt.mCurProcState;
            if (i4 != 20 && (valueAt.mSetProcState != i4 || valueAt.mSetCapability != valueAt.mCurCapability || valueAt.mSetAllowList != valueAt.mCurAllowList || valueAt.mProcAdjChanged)) {
                boolean shouldLog = this.mLogger.shouldLog(valueAt.mUid);
                if (valueAt.mSetCapability != valueAt.mCurCapability) {
                    StringBuilder sb = new StringBuilder("Changes in ");
                    sb.append(valueAt.mUid);
                    sb.append(" ");
                    sb.append(valueAt.mSetProcState);
                    sb.append(" to ");
                    sb.append(valueAt.mCurProcState);
                    sb.append(", ");
                    sb.append(valueAt.mSetCapability);
                    sb.append(" to ");
                    CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(sb, valueAt.mCurCapability, "", "ActivityManager");
                }
                if (!ActivityManager.isProcStateBackground(valueAt.mCurProcState) || valueAt.mCurAllowList) {
                    if (valueAt.mIdle) {
                        EventLog.writeEvent(30054, valueAt.mUid);
                        valueAt.mIdle = false;
                        i = 4;
                    } else {
                        i = 0;
                    }
                    valueAt.mLastBackgroundTime = 0L;
                    valueAt.mLastIdleTimeIfStillIdle = 0L;
                    if (shouldLog) {
                        OomAdjusterDebugLogger oomAdjusterDebugLogger = this.mLogger;
                        int i5 = valueAt.mUid;
                        OomAdjuster oomAdjuster = oomAdjusterDebugLogger.mOomAdjuster;
                        EventLogTags.writeAmOomAdjMisc(11, i5, 0, oomAdjuster.mAdjSeq, 0, oomAdjReasonToString(oomAdjuster.mLastReason));
                    }
                    i2 = i;
                } else {
                    if (!ActivityManager.isProcStateBackground(valueAt.mSetProcState) || valueAt.mSetAllowList || valueAt.mLastBackgroundTime == 0) {
                        valueAt.mLastBackgroundTime = j;
                        if (shouldLog) {
                            OomAdjusterDebugLogger oomAdjusterDebugLogger2 = this.mLogger;
                            int i6 = valueAt.mUid;
                            OomAdjuster oomAdjuster2 = oomAdjusterDebugLogger2.mOomAdjuster;
                            EventLogTags.writeAmOomAdjMisc(10, i6, 0, oomAdjuster2.mAdjSeq, (int) j, oomAdjReasonToString(oomAdjuster2.mLastReason));
                        }
                        ActivityManagerService activityManagerService = this.mService;
                        if (activityManagerService.mDeterministicUidIdle || !activityManagerService.mHandler.hasMessages(58)) {
                            if (shouldLog) {
                                EventLogTags.writeAmOomAdjMisc(1, valueAt.mUid, 0, this.mLogger.mOomAdjuster.mAdjSeq, (int) this.mConstants.BACKGROUND_SETTLE_TIME, "");
                            }
                            this.mService.mHandler.sendEmptyMessageDelayed(58, this.mConstants.BACKGROUND_SETTLE_TIME);
                        }
                    }
                    if (!valueAt.mIdle || valueAt.mSetIdle) {
                        i2 = 0;
                    } else {
                        if (valueAt.mSetProcState != 20) {
                            arrayList.add(valueAt);
                        }
                        i2 = 2;
                    }
                }
                int i7 = valueAt.mSetProcState;
                boolean z3 = i7 > 11 ? z2 : false;
                int i8 = valueAt.mCurProcState;
                boolean z4 = i8 > 11 ? z2 : false;
                if (z3 != z4 || i7 == 20) {
                    i2 |= z4 ? 8 : 16;
                }
                int i9 = valueAt.mSetCapability;
                int i10 = valueAt.mCurCapability;
                if (i9 != i10) {
                    i2 |= 32;
                }
                if (i7 != i8) {
                    i2 |= Integer.MIN_VALUE;
                }
                if (valueAt.mProcAdjChanged) {
                    i2 |= 64;
                }
                int i11 = i2;
                valueAt.mSetProcState = i8;
                valueAt.mSetCapability = i10;
                boolean z5 = valueAt.mCurAllowList;
                valueAt.mSetAllowList = z5;
                valueAt.mSetIdle = valueAt.mIdle;
                valueAt.mProcAdjChanged = false;
                if (!shouldLog || (i8 == i7 && i10 == i9)) {
                    i3 = Integer.MIN_VALUE;
                } else {
                    OomAdjusterDebugLogger oomAdjusterDebugLogger3 = this.mLogger;
                    int i12 = valueAt.mUid;
                    OomAdjuster oomAdjuster3 = oomAdjusterDebugLogger3.mOomAdjuster;
                    EventLog.writeEvent(30111, Integer.valueOf(i12), Integer.valueOf(oomAdjuster3.mAdjSeq), Integer.valueOf(i8), Integer.valueOf(i7), Integer.valueOf(i10), Integer.valueOf(i9), Integer.valueOf(z5 ? 1 : 0), oomAdjReasonToString(oomAdjuster3.mLastReason));
                    oomAdjusterDebugLogger3.maybeLogStacktrace("uidStateChanged");
                    int i13 = oomAdjusterDebugLogger3.mConstants.mProcStateDebugSetUidStateDelay;
                    if (i13 != 0) {
                        try {
                            Thread.sleep(i13);
                        } catch (InterruptedException unused) {
                        }
                    }
                    i3 = Integer.MIN_VALUE;
                }
                int i14 = i3 & i11;
                if (i14 != 0 || (i11 & 32) != 0) {
                    ActivityTaskManagerInternal activityTaskManagerInternal = this.mService.mAtmInternal;
                    int i15 = valueAt.mUid;
                    int i16 = valueAt.mSetProcState;
                    MirrorActiveUids mirrorActiveUids = ActivityTaskManagerService.this.mActiveUids;
                    synchronized (mirrorActiveUids) {
                        int indexOfKey = mirrorActiveUids.mUidStates.indexOfKey(i15);
                        if (indexOfKey >= 0) {
                            mirrorActiveUids.mUidStates.setValueAt(indexOfKey, i16);
                        }
                    }
                }
                if (i11 != 0) {
                    this.mService.enqueueUidChangeLocked(valueAt, -1, i11);
                }
                if (i14 != 0 || (i11 & 32) != 0) {
                    this.mService.noteUidProcessState(valueAt.mUid, valueAt.mCurProcState, valueAt.mCurCapability);
                }
                if (valueAt.mForegroundServices && (serviceMap = (ActiveServices.ServiceMap) this.mService.mServices.mServiceMap.get(UserHandle.getUserId(valueAt.mUid))) != null) {
                    boolean z6 = false;
                    for (int size2 = serviceMap.mActiveForegroundApps.size() - 1; size2 >= 0; size2--) {
                        ActiveServices.ActiveForegroundApp activeForegroundApp = (ActiveServices.ActiveForegroundApp) serviceMap.mActiveForegroundApps.valueAt(size2);
                        if (activeForegroundApp.mUid == valueAt.mUid) {
                            if (valueAt.mCurProcState <= 2) {
                                if (activeForegroundApp.mAppOnTop) {
                                    z = true;
                                } else {
                                    z = true;
                                    activeForegroundApp.mAppOnTop = true;
                                    z6 = true;
                                }
                                activeForegroundApp.mShownWhileTop = z;
                            } else if (activeForegroundApp.mAppOnTop) {
                                activeForegroundApp.mAppOnTop = false;
                                z6 = true;
                            }
                        }
                    }
                    if (z6) {
                        ActiveServices.requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
                    }
                }
            }
            this.mService.mInternal.deletePendingTopUid(valueAt.mUid, j);
            size--;
            activeUids2 = activeUids;
            z2 = true;
        }
        PowerManagerInternal powerManagerInternal2 = this.mService.mLocalPowerManager;
        if (powerManagerInternal2 != null) {
            powerManagerInternal2.finishUidChanges();
        }
        int size3 = arrayList.size();
        if (size3 > 0) {
            for (int i17 = size3 - 1; i17 >= 0; i17--) {
                this.mService.mServices.stopInBackgroundLocked(((UidRecord) arrayList.get(i17)).mUid);
            }
        }
    }
}
