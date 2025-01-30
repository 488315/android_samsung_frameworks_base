package com.android.server.am;

import android.app.BroadcastOptions;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.CompatibilityInfo;
import android.net.INetd;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.os.TimeoutRecord;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.am.ProcessList;
import com.android.server.pm.UserJourneyLogger;
import com.android.server.pm.UserManagerInternal;
import dalvik.annotation.optimization.NeverCompile;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;

/* loaded from: classes.dex */
public class BroadcastQueueImpl extends BroadcastQueue {
    public boolean mBroadcastsScheduled;
    public final BroadcastConstants mConstants;
    public final boolean mDelayBehindServices;
    public final BroadcastDispatcher mDispatcher;
    public final BroadcastHandler mHandler;
    public boolean mLogLatencyMetrics;
    public int mNextToken;
    public final ArrayList mParallelBroadcasts;
    public BroadcastRecord mPendingBroadcast;
    public int mPendingBroadcastRecvIndex;
    public boolean mPendingBroadcastTimeoutMessage;
    public final int mSchedGroup;
    public final SparseIntArray mSplitRefcounts;

    @Override // com.android.server.am.BroadcastQueue
    public void clearDelayedBroadcastLocked() {
    }

    @Override // com.android.server.am.BroadcastQueue
    public void enqueueDelayedBroadcastLocked(BroadcastRecord broadcastRecord) {
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onProcessFreezableChangedLocked(ProcessRecord processRecord) {
    }

    public final class BroadcastHandler extends Handler {
        public BroadcastHandler(Looper looper) {
            super(looper, null);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 200) {
                BroadcastQueueImpl.this.processNextBroadcast(true);
                return;
            }
            if (i != 201) {
                return;
            }
            ActivityManagerService activityManagerService = BroadcastQueueImpl.this.mService;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    BroadcastQueueImpl.this.broadcastTimeoutLocked(true);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    public BroadcastQueueImpl(ActivityManagerService activityManagerService, Handler handler, String str, BroadcastConstants broadcastConstants, boolean z, int i) {
        this(activityManagerService, handler, str, broadcastConstants, new BroadcastSkipPolicy(activityManagerService), new BroadcastHistory(activityManagerService, broadcastConstants), z, i);
    }

    public BroadcastQueueImpl(ActivityManagerService activityManagerService, Handler handler, String str, BroadcastConstants broadcastConstants, BroadcastSkipPolicy broadcastSkipPolicy, BroadcastHistory broadcastHistory, boolean z, int i) {
        super(activityManagerService, handler, str, broadcastSkipPolicy, broadcastHistory);
        this.mParallelBroadcasts = new ArrayList();
        this.mSplitRefcounts = new SparseIntArray();
        this.mNextToken = 0;
        this.mBroadcastsScheduled = false;
        this.mPendingBroadcast = null;
        this.mLogLatencyMetrics = true;
        BroadcastHandler broadcastHandler = new BroadcastHandler(handler.getLooper());
        this.mHandler = broadcastHandler;
        this.mConstants = broadcastConstants;
        this.mDelayBehindServices = z;
        this.mSchedGroup = i;
        this.mDispatcher = new BroadcastDispatcher(this, broadcastConstants, broadcastHandler, this.mService);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void start(ContentResolver contentResolver) {
        this.mDispatcher.start();
        this.mConstants.startObserving(this.mHandler, contentResolver);
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean isDelayBehindServices() {
        return this.mDelayBehindServices;
    }

    public BroadcastRecord getPendingBroadcastLocked() {
        return this.mPendingBroadcast;
    }

    public BroadcastRecord getActiveBroadcastLocked() {
        return this.mDispatcher.getActiveBroadcastLocked();
    }

    @Override // com.android.server.am.BroadcastQueue
    public int getPreferredSchedulingGroupLocked(ProcessRecord processRecord) {
        BroadcastRecord activeBroadcastLocked = getActiveBroadcastLocked();
        if (activeBroadcastLocked != null && activeBroadcastLocked.curApp == processRecord) {
            return this.mSchedGroup;
        }
        BroadcastRecord pendingBroadcastLocked = getPendingBroadcastLocked();
        if (pendingBroadcastLocked == null || pendingBroadcastLocked.curApp != processRecord) {
            return Integer.MIN_VALUE;
        }
        return this.mSchedGroup;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void enqueueBroadcastLocked(BroadcastRecord broadcastRecord) {
        BroadcastRecord broadcastRecord2;
        broadcastRecord.applySingletonPolicy(this.mService);
        boolean z = false;
        boolean z2 = (broadcastRecord.intent.getFlags() & 536870912) != 0;
        boolean z3 = broadcastRecord.ordered;
        if (!z3) {
            List list = broadcastRecord.receivers;
            int size = list != null ? list.size() : 0;
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (broadcastRecord.receivers.get(i) instanceof ResolveInfo) {
                    z3 = true;
                    break;
                }
                i++;
            }
        }
        if (z3) {
            BroadcastRecord replaceOrderedBroadcastLocked = z2 ? replaceOrderedBroadcastLocked(broadcastRecord) : null;
            if (replaceOrderedBroadcastLocked != null) {
                IIntentReceiver iIntentReceiver = replaceOrderedBroadcastLocked.resultTo;
                if (iIntentReceiver == null) {
                    return;
                }
                try {
                    replaceOrderedBroadcastLocked.mIsReceiverAppRunning = true;
                    ProcessRecord processRecord = replaceOrderedBroadcastLocked.resultToApp;
                    Intent intent = replaceOrderedBroadcastLocked.intent;
                    boolean z4 = replaceOrderedBroadcastLocked.shareIdentity;
                    int i2 = replaceOrderedBroadcastLocked.userId;
                    int i3 = replaceOrderedBroadcastLocked.callingUid;
                    int i4 = broadcastRecord.callingUid;
                    String str = broadcastRecord.callerPackage;
                    long uptimeMillis = SystemClock.uptimeMillis() - replaceOrderedBroadcastLocked.enqueueTime;
                    ProcessRecord processRecord2 = replaceOrderedBroadcastLocked.resultToApp;
                    broadcastRecord2 = replaceOrderedBroadcastLocked;
                    try {
                        performReceiveLocked(replaceOrderedBroadcastLocked, processRecord, iIntentReceiver, intent, 0, null, null, false, false, z4, i2, i3, i4, str, uptimeMillis, 0L, 0, processRecord2 != null ? processRecord2.mState.getCurProcState() : -1);
                    } catch (RemoteException e) {
                        e = e;
                        Slog.w("BroadcastQueue", "Failure [" + this.mQueueName + "] sending broadcast result of " + broadcastRecord2.intent, e);
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    broadcastRecord2 = replaceOrderedBroadcastLocked;
                }
            } else {
                enqueueOrderedBroadcastLocked(broadcastRecord);
                scheduleBroadcastsLocked();
            }
        } else {
            if (z2 && replaceParallelBroadcastLocked(broadcastRecord) != null) {
                z = true;
            }
            if (z) {
                return;
            }
            enqueueParallelBroadcastLocked(broadcastRecord);
            scheduleBroadcastsLocked();
        }
    }

    public void enqueueParallelBroadcastLocked(BroadcastRecord broadcastRecord) {
        broadcastRecord.enqueueClockTime = System.currentTimeMillis();
        broadcastRecord.enqueueTime = SystemClock.uptimeMillis();
        broadcastRecord.enqueueRealTime = SystemClock.elapsedRealtime();
        this.mParallelBroadcasts.add(broadcastRecord);
        enqueueBroadcastHelper(broadcastRecord);
    }

    public void enqueueOrderedBroadcastLocked(BroadcastRecord broadcastRecord) {
        broadcastRecord.enqueueClockTime = System.currentTimeMillis();
        broadcastRecord.enqueueTime = SystemClock.uptimeMillis();
        broadcastRecord.enqueueRealTime = SystemClock.elapsedRealtime();
        this.mDispatcher.enqueueOrderedBroadcastLocked(broadcastRecord);
        enqueueBroadcastHelper(broadcastRecord);
    }

    public final void enqueueBroadcastHelper(BroadcastRecord broadcastRecord) {
        if (Trace.isTagEnabled(64L)) {
            Trace.asyncTraceBegin(64L, createBroadcastTraceTitle(broadcastRecord, 0), System.identityHashCode(broadcastRecord));
        }
    }

    public final BroadcastRecord replaceParallelBroadcastLocked(BroadcastRecord broadcastRecord) {
        return replaceBroadcastLocked(this.mParallelBroadcasts, broadcastRecord, "PARALLEL");
    }

    public final BroadcastRecord replaceOrderedBroadcastLocked(BroadcastRecord broadcastRecord) {
        return this.mDispatcher.replaceBroadcastLocked(broadcastRecord, "ORDERED");
    }

    public final BroadcastRecord replaceBroadcastLocked(ArrayList arrayList, BroadcastRecord broadcastRecord, String str) {
        Intent intent = broadcastRecord.intent;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            BroadcastRecord broadcastRecord2 = (BroadcastRecord) arrayList.get(size);
            if (broadcastRecord2.userId == broadcastRecord.userId && intent.filterEquals(broadcastRecord2.intent)) {
                arrayList.set(size, broadcastRecord);
                return broadcastRecord2;
            }
        }
        return null;
    }

    public final void processCurBroadcastLocked(BroadcastRecord broadcastRecord, ProcessRecord processRecord) {
        ProcessReceiverRecord processReceiverRecord;
        IApplicationThread thread = processRecord.getThread();
        if (thread == null) {
            throw new RemoteException();
        }
        if (processRecord.isInFullBackup()) {
            skipReceiverLocked(broadcastRecord);
            return;
        }
        broadcastRecord.curApp = processRecord;
        broadcastRecord.curAppLastProcessState = processRecord.mState.getCurProcState();
        ProcessReceiverRecord processReceiverRecord2 = processRecord.mReceivers;
        processReceiverRecord2.addCurReceiver(broadcastRecord);
        processRecord.mState.forceProcessStateUpTo(11);
        if (this.mService.mInternal.getRestrictionLevel(processRecord.info.packageName, processRecord.userId) < 40) {
            this.mService.updateLruProcessLocked(processRecord, false, null);
        }
        this.mService.enqueueOomAdjTargetLocked(processRecord);
        this.mService.updateOomAdjPendingTargetsLocked(3);
        maybeReportBroadcastDispatchedEventLocked(broadcastRecord, broadcastRecord.curReceiver.applicationInfo.uid);
        broadcastRecord.intent.setComponent(broadcastRecord.curComponent);
        BroadcastOptions broadcastOptions = broadcastRecord.options;
        if (broadcastOptions != null && broadcastOptions.getTemporaryAppAllowlistDuration() > 0 && broadcastRecord.options.getTemporaryAppAllowlistType() == 4) {
            this.mService.mOomAdjuster.mCachedAppOptimizer.unfreezeTemporarily(processRecord, 3, broadcastRecord.options.getTemporaryAppAllowlistDuration());
        }
        try {
            broadcastRecord.receiversDispatchTime[broadcastRecord.nextReceiver - 1] = SystemClock.uptimeMillis();
            this.mService.notifyPackageUse(broadcastRecord.intent.getComponent().getPackageName(), 3);
            processReceiverRecord = processReceiverRecord2;
            try {
                thread.scheduleReceiver(prepareReceiverIntent(broadcastRecord.intent, broadcastRecord.curFilteredExtras), broadcastRecord.curReceiver, (CompatibilityInfo) null, broadcastRecord.resultCode, broadcastRecord.resultData, broadcastRecord.resultExtras, broadcastRecord.ordered, false, broadcastRecord.userId, broadcastRecord.shareIdentity ? broadcastRecord.callingUid : -1, processRecord.mState.getReportedProcState(), broadcastRecord.shareIdentity ? broadcastRecord.callerPackage : null);
                if (processRecord.isKilled()) {
                    throw new RemoteException("app gets killed during broadcasting");
                }
            } catch (Throwable th) {
                th = th;
                broadcastRecord.curApp = null;
                broadcastRecord.curAppLastProcessState = -1;
                processReceiverRecord.removeCurReceiver(broadcastRecord);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            processReceiverRecord = processReceiverRecord2;
        }
    }

    public void updateUidReadyForBootCompletedBroadcastLocked(int i) {
        this.mDispatcher.updateUidReadyForBootCompletedBroadcastLocked(i);
        scheduleBroadcastsLocked();
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean onApplicationAttachedLocked(ProcessRecord processRecord) {
        updateUidReadyForBootCompletedBroadcastLocked(processRecord.uid);
        BroadcastRecord broadcastRecord = this.mPendingBroadcast;
        if (broadcastRecord == null || broadcastRecord.curApp != processRecord) {
            return false;
        }
        return sendPendingBroadcastsLocked(processRecord);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onApplicationTimeoutLocked(ProcessRecord processRecord) {
        skipCurrentOrPendingReceiverLocked(processRecord);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onApplicationProblemLocked(ProcessRecord processRecord) {
        skipCurrentOrPendingReceiverLocked(processRecord);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onApplicationCleanupLocked(ProcessRecord processRecord) {
        skipCurrentOrPendingReceiverLocked(processRecord);
    }

    public boolean sendPendingBroadcastsLocked(ProcessRecord processRecord) {
        BroadcastRecord broadcastRecord = this.mPendingBroadcast;
        if (broadcastRecord == null || broadcastRecord.curApp.getPid() <= 0 || broadcastRecord.curApp.getPid() != processRecord.getPid()) {
            return false;
        }
        if (broadcastRecord.curApp != processRecord) {
            Slog.e("BroadcastQueue", "App mismatch when sending pending broadcast to " + processRecord.processName + ", intended target is " + broadcastRecord.curApp.processName);
            return false;
        }
        try {
            this.mPendingBroadcast = null;
            broadcastRecord.mIsReceiverAppRunning = false;
            processCurBroadcastLocked(broadcastRecord, processRecord);
            return true;
        } catch (Exception e) {
            Slog.w("BroadcastQueue", "Exception in new application when starting receiver " + broadcastRecord.curComponent.flattenToShortString(), e);
            logBroadcastReceiverDiscardLocked(broadcastRecord);
            finishReceiverLocked(broadcastRecord, broadcastRecord.resultCode, broadcastRecord.resultData, broadcastRecord.resultExtras, broadcastRecord.resultAbort, false);
            scheduleBroadcastsLocked();
            broadcastRecord.state = 0;
            throw new BroadcastDeliveryFailedException(e);
        }
    }

    public boolean skipCurrentOrPendingReceiverLocked(ProcessRecord processRecord) {
        BroadcastRecord broadcastRecord;
        BroadcastRecord activeBroadcastLocked = this.mDispatcher.getActiveBroadcastLocked();
        if (activeBroadcastLocked == null || activeBroadcastLocked.curApp != processRecord) {
            activeBroadcastLocked = null;
        }
        if (activeBroadcastLocked == null && (broadcastRecord = this.mPendingBroadcast) != null && broadcastRecord.curApp == processRecord) {
            activeBroadcastLocked = broadcastRecord;
        }
        if (activeBroadcastLocked == null) {
            return false;
        }
        skipReceiverLocked(activeBroadcastLocked);
        return true;
    }

    public final void skipReceiverLocked(BroadcastRecord broadcastRecord) {
        logBroadcastReceiverDiscardLocked(broadcastRecord);
        finishReceiverLocked(broadcastRecord, broadcastRecord.resultCode, broadcastRecord.resultData, broadcastRecord.resultExtras, broadcastRecord.resultAbort, false);
        scheduleBroadcastsLocked();
    }

    public void scheduleBroadcastsLocked() {
        if (this.mBroadcastsScheduled) {
            return;
        }
        BroadcastHandler broadcastHandler = this.mHandler;
        broadcastHandler.sendMessage(broadcastHandler.obtainMessage(200, this));
        this.mBroadcastsScheduled = true;
    }

    public BroadcastRecord getMatchingOrderedReceiver(ProcessRecord processRecord) {
        BroadcastRecord activeBroadcastLocked = this.mDispatcher.getActiveBroadcastLocked();
        if (activeBroadcastLocked == null) {
            Slog.w("BroadcastQueue", "getMatchingOrderedReceiver [" + this.mQueueName + "] no active broadcast");
            return null;
        }
        if (activeBroadcastLocked.curApp == processRecord) {
            return activeBroadcastLocked;
        }
        Slog.w("BroadcastQueue", "getMatchingOrderedReceiver [" + this.mQueueName + "] active broadcast " + activeBroadcastLocked.curApp + " doesn't match " + processRecord);
        return null;
    }

    public final int nextSplitTokenLocked() {
        int i = this.mNextToken + 1;
        int i2 = i > 0 ? i : 1;
        this.mNextToken = i2;
        return i2;
    }

    public final void postActivityStartTokenRemoval(final ProcessRecord processRecord, final BroadcastRecord broadcastRecord) {
        String intern = (processRecord.toShortString() + broadcastRecord.toString()).intern();
        this.mHandler.removeCallbacksAndMessages(intern);
        this.mHandler.postAtTime(new Runnable() { // from class: com.android.server.am.BroadcastQueueImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BroadcastQueueImpl.this.lambda$postActivityStartTokenRemoval$0(processRecord, broadcastRecord);
            }
        }, intern, broadcastRecord.receiverTime + this.mConstants.ALLOW_BG_ACTIVITY_START_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postActivityStartTokenRemoval$0(ProcessRecord processRecord, BroadcastRecord broadcastRecord) {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                processRecord.removeBackgroundStartPrivileges(broadcastRecord);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean finishReceiverLocked(ProcessRecord processRecord, int i, String str, Bundle bundle, boolean z, boolean z2) {
        BroadcastRecord matchingOrderedReceiver = getMatchingOrderedReceiver(processRecord);
        if (matchingOrderedReceiver != null) {
            return finishReceiverLocked(matchingOrderedReceiver, i, str, bundle, z, z2);
        }
        return false;
    }

    public boolean finishReceiverLocked(BroadcastRecord broadcastRecord, int i, String str, Bundle bundle, boolean z, boolean z2) {
        int i2;
        ActivityInfo activityInfo;
        ProcessRecord processRecord;
        ProcessRecord processRecord2;
        int i3;
        int i4;
        int i5 = broadcastRecord.state;
        ActivityInfo activityInfo2 = broadcastRecord.curReceiver;
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = uptimeMillis - broadcastRecord.receiverTime;
        broadcastRecord.state = 0;
        int i6 = broadcastRecord.nextReceiver - 1;
        int i7 = broadcastRecord.mWasReceiverAppStopped ? 2 : 1;
        if (i6 < 0 || i6 >= broadcastRecord.receivers.size() || broadcastRecord.curApp == null) {
            i2 = -1;
        } else {
            Object obj = broadcastRecord.receivers.get(i6);
            int i8 = broadcastRecord.curApp.uid;
            int i9 = broadcastRecord.callingUid;
            if (i9 == -1) {
                i9 = 1000;
            }
            String action = broadcastRecord.intent.getAction();
            int i10 = obj instanceof BroadcastFilter ? 1 : 2;
            if (broadcastRecord.mIsReceiverAppRunning) {
                i4 = 1;
                i3 = i9;
            } else {
                i3 = i9;
                i4 = 3;
            }
            long j2 = broadcastRecord.dispatchTime;
            long j3 = j2 - broadcastRecord.enqueueTime;
            long j4 = broadcastRecord.receiverTime;
            i2 = -1;
            FrameworkStatsLog.write(FrameworkStatsLog.BROADCAST_DELIVERY_EVENT_REPORTED, i8, i3, action, i10, i4, j3, j4 - j2, uptimeMillis - j4, i7, broadcastRecord.curApp.info.packageName, broadcastRecord.callerPackage, broadcastRecord.calculateTypeForLogging(), broadcastRecord.getDeliveryGroupPolicy(), broadcastRecord.intent.getFlags(), BroadcastRecord.getReceiverPriority(obj), broadcastRecord.callerProcState, broadcastRecord.curAppLastProcessState);
        }
        if (i5 == 0) {
            Slog.w("BroadcastQueue", "finishReceiver [" + this.mQueueName + "] called but state is IDLE");
        }
        if (broadcastRecord.mBackgroundStartPrivileges.allowsAny() && (processRecord2 = broadcastRecord.curApp) != null) {
            if (j > this.mConstants.ALLOW_BG_ACTIVITY_START_TIMEOUT) {
                processRecord2.removeBackgroundStartPrivileges(broadcastRecord);
            } else {
                postActivityStartTokenRemoval(processRecord2, broadcastRecord);
            }
        }
        int i11 = broadcastRecord.nextReceiver;
        if (i11 > 0) {
            broadcastRecord.terminalTime[i11 - 1] = uptimeMillis;
        }
        if (!broadcastRecord.timeoutExempt && (processRecord = broadcastRecord.curApp) != null) {
            long j5 = this.mConstants.SLOW_TIME;
            if (j5 > 0 && j > j5 && !UserHandle.isCore(processRecord.uid)) {
                this.mDispatcher.startDeferring(broadcastRecord.curApp.uid);
            }
        }
        broadcastRecord.intent.setComponent(null);
        ProcessRecord processRecord3 = broadcastRecord.curApp;
        if (processRecord3 != null && processRecord3.mReceivers.hasCurReceiver(broadcastRecord)) {
            broadcastRecord.curApp.mReceivers.removeCurReceiver(broadcastRecord);
            this.mService.enqueueOomAdjTargetLocked(broadcastRecord.curApp);
        }
        BroadcastFilter broadcastFilter = broadcastRecord.curFilter;
        if (broadcastFilter != null) {
            broadcastFilter.receiverList.curBroadcast = null;
        }
        broadcastRecord.curFilter = null;
        broadcastRecord.curReceiver = null;
        broadcastRecord.curApp = null;
        broadcastRecord.curAppLastProcessState = i2;
        broadcastRecord.curFilteredExtras = null;
        broadcastRecord.mWasReceiverAppStopped = false;
        this.mPendingBroadcast = null;
        broadcastRecord.resultCode = i;
        broadcastRecord.resultData = str;
        broadcastRecord.resultExtras = bundle;
        int i12 = broadcastRecord.nextReceiver;
        if (i12 > 0) {
            broadcastRecord.receiversFinishTime[i12 - 1] = SystemClock.uptimeMillis();
        }
        if (z && (broadcastRecord.intent.getFlags() & 134217728) == 0) {
            broadcastRecord.resultAbort = z;
            this.mService.mExt.addAbortedBroadcastToHistoryLocked(this.mHistory, broadcastRecord);
        } else {
            broadcastRecord.resultAbort = false;
        }
        if (z2 && broadcastRecord.curComponent != null && broadcastRecord.queue.isDelayBehindServices() && ((BroadcastQueueImpl) broadcastRecord.queue).getActiveBroadcastLocked() == broadcastRecord) {
            if (broadcastRecord.nextReceiver < broadcastRecord.receivers.size()) {
                Object obj2 = broadcastRecord.receivers.get(broadcastRecord.nextReceiver);
                if (obj2 instanceof ActivityInfo) {
                    activityInfo = (ActivityInfo) obj2;
                    if ((activityInfo2 != null || activityInfo == null || activityInfo2.applicationInfo.uid != activityInfo.applicationInfo.uid || !activityInfo2.processName.equals(activityInfo.processName)) && this.mService.mServices.hasBackgroundServicesLocked(broadcastRecord.userId)) {
                        Slog.i("BroadcastQueue", "Delay finish: " + broadcastRecord.curComponent.flattenToShortString());
                        broadcastRecord.state = 4;
                        return false;
                    }
                }
            }
            activityInfo = null;
            if (activityInfo2 != null) {
            }
            Slog.i("BroadcastQueue", "Delay finish: " + broadcastRecord.curComponent.flattenToShortString());
            broadcastRecord.state = 4;
            return false;
        }
        broadcastRecord.curComponent = null;
        boolean z3 = i5 == 1 || i5 == 3;
        if (z3) {
            processNextBroadcastLocked(false, true);
        }
        return z3;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void backgroundServicesFinishedLocked(int i) {
        BroadcastRecord activeBroadcastLocked = this.mDispatcher.getActiveBroadcastLocked();
        if (activeBroadcastLocked != null && activeBroadcastLocked.userId == i && activeBroadcastLocked.state == 4) {
            Slog.i("BroadcastQueue", "Resuming delayed broadcast");
            activeBroadcastLocked.curComponent = null;
            activeBroadcastLocked.state = 0;
            processNextBroadcastLocked(false, false);
        }
    }

    public void performReceiveLocked(BroadcastRecord broadcastRecord, ProcessRecord processRecord, IIntentReceiver iIntentReceiver, Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, String str2, long j, long j2, int i5, int i6) {
        int i7;
        if (z3) {
            this.mService.mPackageManagerInt.grantImplicitAccess(i2, intent, UserHandle.getAppId(i3), i4, true);
        }
        if (processRecord != null) {
            IApplicationThread thread = processRecord.getThread();
            if (thread != null) {
                try {
                    i7 = -1;
                    thread.scheduleRegisteredReceiver(iIntentReceiver, intent, i, str, bundle, z, z2, !z, i2, processRecord.mState.getReportedProcState(), z3 ? i4 : -1, z3 ? str2 : null);
                } catch (RemoteException e) {
                    ActivityManagerService activityManagerService = this.mService;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            Slog.w("BroadcastQueue", "Failed to schedule " + intent + " to " + iIntentReceiver + " via " + processRecord + ": " + e);
                            processRecord.killLocked("Can't deliver broadcast", 13, 26, true);
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            throw e;
                        } catch (Throwable th) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }
            } else {
                throw new RemoteException("app.thread must not be null");
            }
        } else {
            i7 = -1;
            iIntentReceiver.performReceive(intent, i, str, bundle, z, z2, i2);
        }
        if (z) {
            return;
        }
        int i8 = i3;
        int i9 = i4;
        if (i8 == i7) {
            i8 = 1000;
        }
        if (i9 == i7) {
            i9 = 1000;
        }
        FrameworkStatsLog.write(FrameworkStatsLog.BROADCAST_DELIVERY_EVENT_REPORTED, i8, i9, intent.getAction(), 1, 1, j, j2, 0L, 1, processRecord != null ? processRecord.info.packageName : null, str2, broadcastRecord.calculateTypeForLogging(), broadcastRecord.getDeliveryGroupPolicy(), broadcastRecord.intent.getFlags(), i5, broadcastRecord.callerProcState, i6);
    }

    public boolean isPendingBroadcastPackageLocked(int i) {
        BroadcastRecord broadcastRecord = this.mPendingBroadcast;
        return broadcastRecord != null && broadcastRecord.curApp.uid == i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:80:0x021b A[Catch: RemoteException -> 0x0217, TRY_LEAVE, TryCatch #6 {RemoteException -> 0x0217, blocks: (B:78:0x01de, B:80:0x021b, B:113:0x0213), top: B:47:0x0130 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r1v27 */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.os.Bundle] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.server.am.BroadcastRecord] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void deliverToRegisteredReceiverLocked(BroadcastRecord broadcastRecord, BroadcastFilter broadcastFilter, boolean z, int i) {
        ?? r2;
        String str;
        BroadcastFilter broadcastFilter2;
        BroadcastRecord broadcastRecord2;
        BroadcastQueueImpl broadcastQueueImpl;
        int i2;
        int i3;
        HostingRecord hostingRecord;
        String str2;
        Bundle extras;
        boolean shouldSkip = this.mSkipPolicy.shouldSkip(broadcastRecord, broadcastFilter);
        ?? r1 = 1;
        if (shouldSkip || broadcastRecord.filterExtrasForReceiver == null || (extras = broadcastRecord.intent.getExtras()) == null) {
            r2 = 0;
        } else {
            Bundle bundle = (Bundle) broadcastRecord.filterExtrasForReceiver.apply(Integer.valueOf(broadcastFilter.receiverList.uid), extras);
            r2 = bundle;
            if (bundle == null) {
                shouldSkip = true;
                r2 = bundle;
            }
        }
        boolean z2 = false;
        if (MARsPolicyManager.MARs_ENABLE) {
            int userId = this.mService.mContext.getUserId();
            ProcessRecord processRecord = broadcastRecord.callerApp;
            if (processRecord == null || processRecord.info == null) {
                i2 = userId;
                i3 = 0;
                hostingRecord = null;
                str2 = null;
            } else {
                String str3 = broadcastRecord.callerApp.info.packageName;
                ProcessRecord processRecord2 = broadcastRecord.callerApp;
                int i4 = processRecord2.userId;
                int pid = processRecord2.getPid();
                hostingRecord = broadcastRecord.callerApp.getHostingRecord();
                str2 = str3;
                i3 = pid;
                i2 = i4;
            }
            if (broadcastFilter.packageName != null) {
                if (BaseRestrictionMgr.getInstance().isRestrictedPackage(new ComponentName(broadcastFilter.packageName, ""), str2, i2, INetd.IF_FLAG_BROADCAST, broadcastRecord.intent, this.mQueueName, broadcastFilter.owningUserId, true, broadcastRecord.mBackgroundStartPrivileges.allowsAny(), hostingRecord != null ? hostingRecord.toStringForTracker() : null, i3, 0)) {
                    Slog.w("BroadcastQueue", "intent:" + broadcastRecord.intent.toString() + " is skipped in RestrictedPackage to " + broadcastFilter.receiverList.app);
                    shouldSkip = true;
                }
            }
        }
        if (shouldSkip) {
            broadcastRecord.delivery[i] = 2;
            return;
        }
        broadcastRecord.delivery[i] = 1;
        if (z) {
            broadcastRecord.curFilter = broadcastFilter;
            ReceiverList receiverList = broadcastFilter.receiverList;
            receiverList.curBroadcast = broadcastRecord;
            broadcastRecord.state = 2;
            ProcessRecord processRecord3 = receiverList.app;
            if (processRecord3 != null) {
                broadcastRecord.curApp = processRecord3;
                broadcastRecord.curAppLastProcessState = processRecord3.mState.getCurProcState();
                broadcastFilter.receiverList.app.mReceivers.addCurReceiver(broadcastRecord);
                this.mService.enqueueOomAdjTargetLocked(broadcastRecord.curApp);
                this.mService.updateOomAdjPendingTargetsLocked(3);
            }
        } else {
            ProcessRecord processRecord4 = broadcastFilter.receiverList.app;
            if (processRecord4 != null) {
                this.mService.mOomAdjuster.unfreezeTemporarily(processRecord4, 3);
            }
        }
        try {
            ProcessRecord processRecord5 = broadcastFilter.receiverList.app;
            BroadcastQueueImpl broadcastQueueImpl2 = (processRecord5 == null || !processRecord5.isInFullBackup()) ? null : 1;
            ProcessRecord processRecord6 = broadcastFilter.receiverList.app;
            if (processRecord6 != null && processRecord6.isKilled()) {
                z2 = true;
            }
            try {
                if (broadcastQueueImpl2 == null && !z2) {
                    int i5 = broadcastRecord.nextReceiver;
                    if (i5 > 0) {
                        broadcastRecord.receiversDispatchTime[i5 - 1] = SystemClock.uptimeMillis();
                    }
                    long uptimeMillis = SystemClock.uptimeMillis();
                    broadcastRecord.receiverTime = uptimeMillis;
                    broadcastRecord.scheduledTime[i] = uptimeMillis;
                    maybeAddBackgroundStartPrivileges(broadcastFilter.receiverList.app, broadcastRecord);
                    maybeScheduleTempAllowlistLocked(broadcastFilter.owningUid, broadcastRecord, broadcastRecord.options);
                    maybeReportBroadcastDispatchedEventLocked(broadcastRecord, broadcastFilter.owningUid);
                    ReceiverList receiverList2 = broadcastFilter.receiverList;
                    ProcessRecord processRecord7 = receiverList2.app;
                    IIntentReceiver iIntentReceiver = receiverList2.receiver;
                    Intent prepareReceiverIntent = prepareReceiverIntent(broadcastRecord.intent, r2);
                    int i6 = broadcastRecord.resultCode;
                    String str4 = broadcastRecord.resultData;
                    Bundle bundle2 = broadcastRecord.resultExtras;
                    boolean z3 = broadcastRecord.ordered;
                    boolean z4 = broadcastRecord.initialSticky;
                    boolean z5 = broadcastRecord.shareIdentity;
                    int i7 = broadcastRecord.userId;
                    int i8 = broadcastFilter.receiverList.uid;
                    int i9 = broadcastRecord.callingUid;
                    try {
                        String str5 = broadcastRecord.callerPackage;
                        long j = broadcastRecord.dispatchTime;
                        try {
                            long j2 = j - broadcastRecord.enqueueTime;
                            long j3 = broadcastRecord.receiverTime - j;
                            int priority = broadcastFilter.getPriority();
                            ProcessRecord processRecord8 = broadcastFilter.receiverList.app;
                            str = "BroadcastQueue";
                            try {
                                performReceiveLocked(broadcastRecord, processRecord7, iIntentReceiver, prepareReceiverIntent, i6, str4, bundle2, z3, z4, z5, i7, i8, i9, str5, j2, j3, priority, processRecord8 != null ? processRecord8.mState.getCurProcState() : -1);
                                BroadcastFilter broadcastFilter3 = broadcastFilter;
                                try {
                                    if (broadcastFilter3.receiverList.app != null) {
                                        BroadcastRecord broadcastRecord3 = broadcastRecord;
                                        try {
                                            if (!broadcastRecord3.mBackgroundStartPrivileges.allowsAny() || broadcastRecord3.ordered) {
                                                broadcastQueueImpl2 = this;
                                                r1 = broadcastFilter3;
                                                r2 = broadcastRecord3;
                                            } else {
                                                BroadcastQueueImpl broadcastQueueImpl3 = this;
                                                broadcastQueueImpl3.postActivityStartTokenRemoval(broadcastFilter3.receiverList.app, broadcastRecord3);
                                                r1 = broadcastFilter3;
                                                r2 = broadcastRecord3;
                                                broadcastQueueImpl2 = broadcastQueueImpl3;
                                            }
                                        } catch (RemoteException e) {
                                            e = e;
                                            broadcastQueueImpl = this;
                                            broadcastFilter2 = broadcastFilter3;
                                            broadcastRecord2 = broadcastRecord3;
                                            Slog.w(str, "Failure sending broadcast " + broadcastRecord2.intent, e);
                                            ProcessRecord processRecord9 = broadcastFilter2.receiverList.app;
                                            if (processRecord9 != null) {
                                                processRecord9.removeBackgroundStartPrivileges(broadcastRecord2);
                                                if (z) {
                                                    broadcastFilter2.receiverList.app.mReceivers.removeCurReceiver(broadcastRecord2);
                                                    broadcastQueueImpl.mService.enqueueOomAdjTargetLocked(broadcastRecord2.curApp);
                                                }
                                            }
                                            if (z) {
                                                broadcastRecord2.curFilter = null;
                                                broadcastFilter2.receiverList.curBroadcast = null;
                                                return;
                                            }
                                            return;
                                        }
                                    } else {
                                        broadcastQueueImpl2 = this;
                                        r2 = broadcastRecord;
                                        r1 = broadcastFilter3;
                                    }
                                    if (z) {
                                        return;
                                    }
                                    r2.state = 3;
                                    return;
                                } catch (RemoteException e2) {
                                    e = e2;
                                    broadcastQueueImpl = this;
                                    broadcastRecord2 = broadcastRecord;
                                    broadcastFilter2 = broadcastFilter3;
                                }
                            } catch (RemoteException e3) {
                                e = e3;
                                broadcastQueueImpl = this;
                                broadcastRecord2 = broadcastRecord;
                                broadcastFilter2 = broadcastFilter;
                            }
                        } catch (RemoteException e4) {
                            e = e4;
                            broadcastQueueImpl = this;
                            broadcastFilter2 = broadcastFilter;
                            broadcastRecord2 = broadcastRecord;
                            str = "BroadcastQueue";
                        }
                    } catch (RemoteException e5) {
                        e = e5;
                        broadcastQueueImpl = this;
                        str = "BroadcastQueue";
                        broadcastFilter2 = broadcastFilter;
                        broadcastRecord2 = broadcastRecord;
                    }
                }
                str = "BroadcastQueue";
                BroadcastFilter broadcastFilter4 = broadcastFilter;
                BroadcastRecord broadcastRecord4 = broadcastRecord;
                BroadcastQueueImpl broadcastQueueImpl4 = this;
                r1 = broadcastFilter4;
                r2 = broadcastRecord4;
                broadcastQueueImpl2 = broadcastQueueImpl4;
                if (z) {
                    skipReceiverLocked(broadcastRecord);
                    r1 = broadcastFilter4;
                    r2 = broadcastRecord4;
                    broadcastQueueImpl2 = broadcastQueueImpl4;
                }
                if (z) {
                }
            } catch (RemoteException e6) {
                e = e6;
                broadcastFilter2 = r1;
                broadcastRecord2 = r2;
                broadcastQueueImpl = broadcastQueueImpl2;
            }
        } catch (RemoteException e7) {
            e = e7;
            str = "BroadcastQueue";
            broadcastFilter2 = broadcastFilter;
            broadcastRecord2 = broadcastRecord;
            broadcastQueueImpl = this;
        }
    }

    public void maybeScheduleTempAllowlistLocked(int i, BroadcastRecord broadcastRecord, BroadcastOptions broadcastOptions) {
        if (broadcastOptions == null || broadcastOptions.getTemporaryAppAllowlistDuration() <= 0) {
            return;
        }
        long temporaryAppAllowlistDuration = broadcastOptions.getTemporaryAppAllowlistDuration();
        int temporaryAppAllowlistType = broadcastOptions.getTemporaryAppAllowlistType();
        int temporaryAppAllowlistReasonCode = broadcastOptions.getTemporaryAppAllowlistReasonCode();
        String temporaryAppAllowlistReason = broadcastOptions.getTemporaryAppAllowlistReason();
        long j = temporaryAppAllowlistDuration > 2147483647L ? 2147483647L : temporaryAppAllowlistDuration;
        StringBuilder sb = new StringBuilder();
        sb.append("broadcast:");
        UserHandle.formatUid(sb, broadcastRecord.callingUid);
        sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
        if (broadcastRecord.intent.getAction() != null) {
            sb.append(broadcastRecord.intent.getAction());
        } else if (broadcastRecord.intent.getComponent() != null) {
            broadcastRecord.intent.getComponent().appendShortString(sb);
        } else if (broadcastRecord.intent.getData() != null) {
            sb.append(broadcastRecord.intent.getData());
        }
        sb.append(",reason:");
        sb.append(temporaryAppAllowlistReason);
        if (temporaryAppAllowlistType != 4) {
            this.mService.tempAllowlistUidLocked(i, j, temporaryAppAllowlistReasonCode, sb.toString(), temporaryAppAllowlistType, broadcastRecord.callingUid);
        }
    }

    public final void processNextBroadcast(boolean z) {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                processNextBroadcastLocked(z, false);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public static Intent prepareReceiverIntent(Intent intent, Bundle bundle) {
        Intent intent2 = new Intent(intent);
        if (bundle != null) {
            intent2.replaceExtras(bundle);
        }
        return intent2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:108:0x03ef  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x01c8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x06c7 A[LOOP:2: B:47:0x00f9->B:80:0x06c7, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0361 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x03ca  */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v13 */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v22 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void processNextBroadcastLocked(boolean z, boolean z2) {
        ?? r11;
        boolean z3;
        IIntentReceiver iIntentReceiver;
        IIntentReceiver iIntentReceiver2;
        int i;
        BroadcastQueueImpl broadcastQueueImpl;
        long j;
        boolean z4;
        ProcessRecord processRecord;
        IIntentReceiver iIntentReceiver3;
        Intent intent;
        int i2;
        String str;
        Bundle bundle;
        boolean z5;
        int i3;
        int i4;
        boolean z6;
        int i5;
        Object obj;
        IIntentReceiver iIntentReceiver4;
        IIntentReceiver iIntentReceiver5;
        boolean z7;
        ComponentName componentName;
        IIntentReceiver iIntentReceiver6;
        IIntentReceiver iIntentReceiver7;
        int i6;
        int i7;
        Bundle extras;
        int i8;
        boolean z8;
        BroadcastQueueImpl broadcastQueueImpl2 = this;
        broadcastQueueImpl2.mService.updateCpuStats();
        ?? r14 = 0;
        if (z) {
            broadcastQueueImpl2.mBroadcastsScheduled = false;
        }
        while (true) {
            r11 = 1;
            if (broadcastQueueImpl2.mParallelBroadcasts.size() <= 0) {
                break;
            }
            BroadcastRecord broadcastRecord = (BroadcastRecord) broadcastQueueImpl2.mParallelBroadcasts.remove(0);
            broadcastRecord.dispatchTime = SystemClock.uptimeMillis();
            broadcastRecord.dispatchRealTime = SystemClock.elapsedRealtime();
            broadcastRecord.dispatchClockTime = System.currentTimeMillis();
            broadcastRecord.mIsReceiverAppRunning = true;
            if (Trace.isTagEnabled(64L)) {
                Trace.asyncTraceEnd(64L, broadcastQueueImpl2.createBroadcastTraceTitle(broadcastRecord, 0), System.identityHashCode(broadcastRecord));
                Trace.asyncTraceBegin(64L, broadcastQueueImpl2.createBroadcastTraceTitle(broadcastRecord, 1), System.identityHashCode(broadcastRecord));
            }
            int size = broadcastRecord.receivers.size();
            for (int i9 = 0; i9 < size; i9++) {
                broadcastQueueImpl2.deliverToRegisteredReceiverLocked(broadcastRecord, (BroadcastFilter) broadcastRecord.receivers.get(i9), false, i9);
            }
            broadcastQueueImpl2.addBroadcastToHistoryLocked(broadcastRecord);
        }
        BroadcastRecord broadcastRecord2 = broadcastQueueImpl2.mPendingBroadcast;
        IIntentReceiver iIntentReceiver8 = null;
        if (broadcastRecord2 != null) {
            if (broadcastRecord2.curApp.getPid() > 0) {
                synchronized (broadcastQueueImpl2.mService.mPidsSelfLocked) {
                    ProcessRecord processRecord2 = broadcastQueueImpl2.mService.mPidsSelfLocked.get(broadcastQueueImpl2.mPendingBroadcast.curApp.getPid());
                    z8 = processRecord2 == null || processRecord2.mErrorState.isCrashing();
                }
            } else {
                ProcessList.MyProcessMap processNamesLOSP = broadcastQueueImpl2.mService.mProcessList.getProcessNamesLOSP();
                ProcessRecord processRecord3 = broadcastQueueImpl2.mPendingBroadcast.curApp;
                ProcessRecord processRecord4 = (ProcessRecord) processNamesLOSP.get(processRecord3.processName, processRecord3.uid);
                z8 = processRecord4 == null || !processRecord4.isPendingStart();
            }
            if (!z8) {
                return;
            }
            Slog.w("BroadcastQueue", "pending app  [" + broadcastQueueImpl2.mQueueName + "]" + broadcastQueueImpl2.mPendingBroadcast.curApp + " died before responding to broadcast");
            BroadcastRecord broadcastRecord3 = broadcastQueueImpl2.mPendingBroadcast;
            broadcastRecord3.state = 0;
            broadcastRecord3.nextReceiver = broadcastQueueImpl2.mPendingBroadcastRecvIndex;
            broadcastQueueImpl2.mPendingBroadcast = null;
        }
        boolean z9 = false;
        BroadcastQueueImpl broadcastQueueImpl3 = broadcastQueueImpl2;
        while (true) {
            long uptimeMillis = SystemClock.uptimeMillis();
            IIntentReceiver nextBroadcastLocked = broadcastQueueImpl3.mDispatcher.getNextBroadcastLocked(uptimeMillis);
            if (nextBroadcastLocked == null) {
                broadcastQueueImpl3.mDispatcher.scheduleDeferralCheckLocked(r14);
                synchronized (broadcastQueueImpl3.mService.mAppProfiler.mProfilerLock) {
                    broadcastQueueImpl3.mService.mAppProfiler.scheduleAppGcsLPf();
                }
                if (z9 && !z2) {
                    broadcastQueueImpl3.mService.updateOomAdjPendingTargetsLocked(3);
                }
                if (broadcastQueueImpl3.mService.mUserController.mBootCompleted && broadcastQueueImpl3.mLogLatencyMetrics) {
                    broadcastQueueImpl3.mLogLatencyMetrics = r14;
                    return;
                }
                return;
            }
            List list = nextBroadcastLocked.receivers;
            int size2 = list != null ? list.size() : r14;
            if (broadcastQueueImpl3.mService.mProcessesReady && !nextBroadcastLocked.timeoutExempt) {
                long j2 = nextBroadcastLocked.dispatchTime;
                if (j2 > 0 && size2 > 0 && uptimeMillis > j2 + (broadcastQueueImpl3.mConstants.TIMEOUT * 2 * size2)) {
                    Slog.w("BroadcastQueue", "Hung broadcast [" + broadcastQueueImpl3.mQueueName + "] discarded after timeout failure: now=" + uptimeMillis + " dispatchTime=" + nextBroadcastLocked.dispatchTime + " startTime=" + nextBroadcastLocked.receiverTime + " intent=" + nextBroadcastLocked.intent + " numReceivers=" + size2 + " nextReceiver=" + nextBroadcastLocked.nextReceiver + " state=" + nextBroadcastLocked.state);
                    broadcastQueueImpl3.broadcastTimeoutLocked(r14);
                    nextBroadcastLocked.state = r14;
                    z3 = r11;
                    if (nextBroadcastLocked.state == 0) {
                        return;
                    }
                    List list2 = nextBroadcastLocked.receivers;
                    if (list2 == null || (i8 = nextBroadcastLocked.nextReceiver) >= size2 || nextBroadcastLocked.resultAbort || z3) {
                        if (nextBroadcastLocked.resultTo != null) {
                            int i10 = nextBroadcastLocked.splitToken;
                            if (i10 != 0) {
                                int i11 = broadcastQueueImpl3.mSplitRefcounts.get(i10) - r11;
                                if (i11 == 0) {
                                    broadcastQueueImpl3.mSplitRefcounts.delete(nextBroadcastLocked.splitToken);
                                } else {
                                    broadcastQueueImpl3.mSplitRefcounts.put(nextBroadcastLocked.splitToken, i11);
                                    z4 = r14;
                                    if (z4) {
                                        ProcessRecord processRecord5 = nextBroadcastLocked.callerApp;
                                        if (processRecord5 != null) {
                                            broadcastQueueImpl3.mService.mOomAdjuster.unfreezeTemporarily(processRecord5, 2);
                                        }
                                        try {
                                            if (nextBroadcastLocked.dispatchTime == 0) {
                                                nextBroadcastLocked.dispatchTime = uptimeMillis;
                                            }
                                            nextBroadcastLocked.mIsReceiverAppRunning = r11;
                                            processRecord = nextBroadcastLocked.resultToApp;
                                            iIntentReceiver3 = nextBroadcastLocked.resultTo;
                                            intent = new Intent(nextBroadcastLocked.intent);
                                            i2 = nextBroadcastLocked.resultCode;
                                            str = nextBroadcastLocked.resultData;
                                            bundle = nextBroadcastLocked.resultExtras;
                                            z5 = nextBroadcastLocked.shareIdentity;
                                            try {
                                                i3 = nextBroadcastLocked.userId;
                                                i4 = nextBroadcastLocked.callingUid;
                                            } catch (RemoteException e) {
                                                e = e;
                                                iIntentReceiver = nextBroadcastLocked;
                                                iIntentReceiver2 = iIntentReceiver8;
                                            }
                                        } catch (RemoteException e2) {
                                            e = e2;
                                            iIntentReceiver = nextBroadcastLocked;
                                            iIntentReceiver2 = iIntentReceiver8;
                                            i = 2;
                                        }
                                        try {
                                            String str2 = nextBroadcastLocked.callerPackage;
                                            long j3 = nextBroadcastLocked.dispatchTime;
                                            long j4 = j3 - nextBroadcastLocked.enqueueTime;
                                            long j5 = uptimeMillis - j3;
                                            ProcessRecord processRecord6 = nextBroadcastLocked.resultToApp;
                                            j = 64;
                                            i = 2;
                                            try {
                                                performReceiveLocked(nextBroadcastLocked, processRecord, iIntentReceiver3, intent, i2, str, bundle, false, false, z5, i3, i4, i4, str2, j4, j5, 0, processRecord6 != null ? processRecord6.mState.getCurProcState() : -1);
                                                logBootCompletedBroadcastCompletionLatencyIfPossible(nextBroadcastLocked);
                                                iIntentReceiver = nextBroadcastLocked;
                                                iIntentReceiver2 = null;
                                            } catch (RemoteException e3) {
                                                e = e3;
                                                iIntentReceiver = nextBroadcastLocked;
                                                iIntentReceiver2 = null;
                                            }
                                            try {
                                                iIntentReceiver.resultTo = null;
                                                broadcastQueueImpl = this;
                                            } catch (RemoteException e4) {
                                                e = e4;
                                                iIntentReceiver.resultTo = iIntentReceiver2;
                                                StringBuilder sb = new StringBuilder();
                                                sb.append("Failure [");
                                                broadcastQueueImpl = this;
                                                sb.append(broadcastQueueImpl.mQueueName);
                                                sb.append("] sending broadcast result of ");
                                                sb.append(iIntentReceiver.intent);
                                                Slog.w("BroadcastQueue", sb.toString(), e);
                                                cancelBroadcastTimeoutLocked();
                                                broadcastQueueImpl.mService.mExt.updateBrMap(iIntentReceiver.callerPackage, iIntentReceiver.intent);
                                                broadcastQueueImpl.addBroadcastToHistoryLocked(iIntentReceiver);
                                                if (iIntentReceiver.intent.getComponent() == null) {
                                                }
                                                broadcastQueueImpl.mDispatcher.retireBroadcastLocked(iIntentReceiver);
                                                iIntentReceiver8 = iIntentReceiver2;
                                                z9 = true;
                                                if (iIntentReceiver8 != null) {
                                                }
                                            }
                                        } catch (RemoteException e5) {
                                            e = e5;
                                            iIntentReceiver = nextBroadcastLocked;
                                            iIntentReceiver2 = null;
                                            i = 2;
                                            j = 64;
                                            iIntentReceiver.resultTo = iIntentReceiver2;
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append("Failure [");
                                            broadcastQueueImpl = this;
                                            sb2.append(broadcastQueueImpl.mQueueName);
                                            sb2.append("] sending broadcast result of ");
                                            sb2.append(iIntentReceiver.intent);
                                            Slog.w("BroadcastQueue", sb2.toString(), e);
                                            cancelBroadcastTimeoutLocked();
                                            broadcastQueueImpl.mService.mExt.updateBrMap(iIntentReceiver.callerPackage, iIntentReceiver.intent);
                                            broadcastQueueImpl.addBroadcastToHistoryLocked(iIntentReceiver);
                                            if (iIntentReceiver.intent.getComponent() == null) {
                                            }
                                            broadcastQueueImpl.mDispatcher.retireBroadcastLocked(iIntentReceiver);
                                            iIntentReceiver8 = iIntentReceiver2;
                                            z9 = true;
                                            if (iIntentReceiver8 != null) {
                                            }
                                        }
                                        cancelBroadcastTimeoutLocked();
                                        broadcastQueueImpl.mService.mExt.updateBrMap(iIntentReceiver.callerPackage, iIntentReceiver.intent);
                                        broadcastQueueImpl.addBroadcastToHistoryLocked(iIntentReceiver);
                                        if (iIntentReceiver.intent.getComponent() == null && iIntentReceiver.intent.getPackage() == null && (iIntentReceiver.intent.getFlags() & 1073741824) == 0) {
                                            broadcastQueueImpl.mService.addBroadcastStatLocked(iIntentReceiver.intent.getAction(), iIntentReceiver.callerPackage, iIntentReceiver.manifestCount, iIntentReceiver.manifestSkipCount, iIntentReceiver.finishTime - iIntentReceiver.dispatchTime);
                                        }
                                        broadcastQueueImpl.mDispatcher.retireBroadcastLocked(iIntentReceiver);
                                        iIntentReceiver8 = iIntentReceiver2;
                                        z9 = true;
                                    }
                                }
                            }
                            z4 = r11;
                            if (z4) {
                            }
                        }
                        iIntentReceiver = nextBroadcastLocked;
                        iIntentReceiver2 = iIntentReceiver8;
                        i = 2;
                        broadcastQueueImpl = broadcastQueueImpl3;
                        j = 64;
                        cancelBroadcastTimeoutLocked();
                        broadcastQueueImpl.mService.mExt.updateBrMap(iIntentReceiver.callerPackage, iIntentReceiver.intent);
                        broadcastQueueImpl.addBroadcastToHistoryLocked(iIntentReceiver);
                        if (iIntentReceiver.intent.getComponent() == null) {
                            broadcastQueueImpl.mService.addBroadcastStatLocked(iIntentReceiver.intent.getAction(), iIntentReceiver.callerPackage, iIntentReceiver.manifestCount, iIntentReceiver.manifestSkipCount, iIntentReceiver.finishTime - iIntentReceiver.dispatchTime);
                        }
                        broadcastQueueImpl.mDispatcher.retireBroadcastLocked(iIntentReceiver);
                        iIntentReceiver8 = iIntentReceiver2;
                        z9 = true;
                    } else {
                        if (!nextBroadcastLocked.deferred) {
                            int receiverUid = BroadcastRecord.getReceiverUid(list2.get(i8));
                            if (broadcastQueueImpl3.mDispatcher.isDeferringLocked(receiverUid)) {
                                int i12 = nextBroadcastLocked.nextReceiver;
                                if (i12 + 1 == size2) {
                                    broadcastQueueImpl3.mDispatcher.retireBroadcastLocked(nextBroadcastLocked);
                                } else {
                                    IIntentReceiver splitRecipientsLocked = nextBroadcastLocked.splitRecipientsLocked(receiverUid, i12);
                                    if (nextBroadcastLocked.resultTo != null) {
                                        int i13 = nextBroadcastLocked.splitToken;
                                        if (i13 == 0) {
                                            int nextSplitTokenLocked = nextSplitTokenLocked();
                                            splitRecipientsLocked.splitToken = nextSplitTokenLocked;
                                            nextBroadcastLocked.splitToken = nextSplitTokenLocked;
                                            broadcastQueueImpl3.mSplitRefcounts.put(nextSplitTokenLocked, 2);
                                        } else {
                                            broadcastQueueImpl3.mSplitRefcounts.put(i13, broadcastQueueImpl3.mSplitRefcounts.get(i13) + r11);
                                        }
                                    }
                                    nextBroadcastLocked = splitRecipientsLocked;
                                }
                                broadcastQueueImpl3.mDispatcher.addDeferredBroadcast(receiverUid, nextBroadcastLocked);
                                iIntentReceiver2 = iIntentReceiver8;
                                z9 = r11;
                                i = 2;
                                broadcastQueueImpl = broadcastQueueImpl3;
                                j = 64;
                            }
                        }
                        iIntentReceiver2 = iIntentReceiver8;
                        i = 2;
                        broadcastQueueImpl = broadcastQueueImpl3;
                        j = 64;
                        iIntentReceiver8 = nextBroadcastLocked;
                    }
                    if (iIntentReceiver8 != null) {
                        int i14 = iIntentReceiver8.nextReceiver;
                        iIntentReceiver8.nextReceiver = i14 + 1;
                        long uptimeMillis2 = SystemClock.uptimeMillis();
                        iIntentReceiver8.receiverTime = uptimeMillis2;
                        iIntentReceiver8.scheduledTime[i14] = uptimeMillis2;
                        if (i14 == 0) {
                            iIntentReceiver8.dispatchTime = uptimeMillis2;
                            iIntentReceiver8.dispatchRealTime = SystemClock.elapsedRealtime();
                            long currentTimeMillis = System.currentTimeMillis();
                            iIntentReceiver8.dispatchClockTime = currentTimeMillis;
                            if (broadcastQueueImpl.mLogLatencyMetrics) {
                                FrameworkStatsLog.write(142, currentTimeMillis - iIntentReceiver8.enqueueClockTime);
                            }
                            if (Trace.isTagEnabled(j)) {
                                i5 = 0;
                                long j6 = j;
                                Trace.asyncTraceEnd(j6, broadcastQueueImpl.createBroadcastTraceTitle(iIntentReceiver8, 0), System.identityHashCode(iIntentReceiver8));
                                z6 = true;
                                Trace.asyncTraceBegin(j6, broadcastQueueImpl.createBroadcastTraceTitle(iIntentReceiver8, 1), System.identityHashCode(iIntentReceiver8));
                                if (!broadcastQueueImpl.mPendingBroadcastTimeoutMessage) {
                                    broadcastQueueImpl.setBroadcastTimeoutLocked(iIntentReceiver8.receiverTime + broadcastQueueImpl.mConstants.TIMEOUT);
                                }
                                BroadcastOptions broadcastOptions = iIntentReceiver8.options;
                                obj = iIntentReceiver8.receivers.get(i14);
                                if (!(obj instanceof BroadcastFilter)) {
                                    BroadcastFilter broadcastFilter = (BroadcastFilter) obj;
                                    iIntentReceiver8.mIsReceiverAppRunning = z6;
                                    broadcastQueueImpl.deliverToRegisteredReceiverLocked(iIntentReceiver8, broadcastFilter, iIntentReceiver8.ordered, i14);
                                    if ((iIntentReceiver8.curReceiver == null && iIntentReceiver8.curFilter == null) || !iIntentReceiver8.ordered) {
                                        iIntentReceiver8.state = i5;
                                        scheduleBroadcastsLocked();
                                        return;
                                    } else {
                                        ReceiverList receiverList = broadcastFilter.receiverList;
                                        if (receiverList != null) {
                                            broadcastQueueImpl.maybeAddBackgroundStartPrivileges(receiverList.app, iIntentReceiver8);
                                            return;
                                        }
                                        return;
                                    }
                                }
                                ResolveInfo resolveInfo = (ResolveInfo) obj;
                                ActivityInfo activityInfo = resolveInfo.activityInfo;
                                ComponentName componentName2 = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                                ActivityInfo activityInfo2 = resolveInfo.activityInfo;
                                int i15 = activityInfo2.applicationInfo.uid;
                                String str3 = activityInfo2.processName;
                                ProcessRecord processRecordLocked = broadcastQueueImpl.mService.getProcessRecordLocked(str3, i15);
                                boolean shouldSkip = broadcastQueueImpl.mSkipPolicy.shouldSkip(iIntentReceiver8, resolveInfo);
                                if (shouldSkip || iIntentReceiver8.filterExtrasForReceiver == null || (extras = iIntentReceiver8.intent.getExtras()) == null) {
                                    iIntentReceiver4 = iIntentReceiver2;
                                } else {
                                    iIntentReceiver4 = (Bundle) iIntentReceiver8.filterExtrasForReceiver.apply(Integer.valueOf(i15), extras);
                                    if (iIntentReceiver4 == null) {
                                        shouldSkip = z6;
                                    }
                                }
                                if (shouldSkip || !MARsPolicyManager.MARs_ENABLE) {
                                    iIntentReceiver5 = iIntentReceiver2;
                                } else {
                                    MARsPolicyManager.getInstance().onSpecialIntentActions(componentName2.getPackageName(), iIntentReceiver8.intent, iIntentReceiver8.userId);
                                    int userId = broadcastQueueImpl.mService.mContext.getUserId();
                                    ProcessRecord processRecord7 = iIntentReceiver8.callerApp;
                                    if (processRecord7 == null || processRecord7.info == null) {
                                        iIntentReceiver6 = iIntentReceiver2;
                                        iIntentReceiver7 = iIntentReceiver6;
                                        i6 = userId;
                                        i7 = 0;
                                    } else {
                                        IIntentReceiver iIntentReceiver9 = iIntentReceiver8.callerApp.info.packageName;
                                        ProcessRecord processRecord8 = iIntentReceiver8.callerApp;
                                        int i16 = processRecord8.userId;
                                        int pid = processRecord8.getPid();
                                        iIntentReceiver6 = iIntentReceiver8.callerApp.getHostingRecord();
                                        iIntentReceiver7 = iIntentReceiver9;
                                        i7 = pid;
                                        i6 = i16;
                                    }
                                    int pid2 = processRecordLocked != null ? processRecordLocked.getPid() : 0;
                                    IIntentReceiver stringForTracker = iIntentReceiver6 != null ? iIntentReceiver6.toStringForTracker() : iIntentReceiver2;
                                    if (BaseRestrictionMgr.getInstance().isRestrictedPackage(componentName2, iIntentReceiver7, i6, INetd.IF_FLAG_BROADCAST, iIntentReceiver8.intent, broadcastQueueImpl.mQueueName, UserHandle.getUserId(resolveInfo.activityInfo.applicationInfo.uid), false, iIntentReceiver8.mBackgroundStartPrivileges.allowsAny(), stringForTracker, i7, pid2)) {
                                        Slog.w("BroadcastQueue", "intent:" + iIntentReceiver8.intent.toString() + " is skipped in RestrictedPackage to " + componentName2.flattenToShortString());
                                        iIntentReceiver5 = stringForTracker;
                                        shouldSkip = true;
                                    } else {
                                        iIntentReceiver5 = stringForTracker;
                                    }
                                }
                                if (shouldSkip) {
                                    iIntentReceiver8.delivery[i14] = i;
                                    iIntentReceiver8.curFilter = iIntentReceiver2;
                                    iIntentReceiver8.state = 0;
                                    iIntentReceiver8.manifestSkipCount++;
                                    scheduleBroadcastsLocked();
                                    return;
                                }
                                iIntentReceiver8.manifestCount++;
                                iIntentReceiver8.delivery[i14] = 1;
                                iIntentReceiver8.state = 1;
                                iIntentReceiver8.curComponent = componentName2;
                                iIntentReceiver8.curReceiver = resolveInfo.activityInfo;
                                iIntentReceiver8.curFilteredExtras = iIntentReceiver4;
                                int i17 = (broadcastOptions == null || broadcastOptions.getTemporaryAppAllowlistDuration() <= 0) ? 0 : 1;
                                broadcastQueueImpl.maybeScheduleTempAllowlistLocked(i15, iIntentReceiver8, broadcastOptions);
                                if (iIntentReceiver8.intent.getComponent() != null && (componentName = iIntentReceiver8.curComponent) != null && !TextUtils.equals(componentName.getPackageName(), iIntentReceiver8.callerPackage)) {
                                    broadcastQueueImpl.mService.mUsageStatsService.reportEvent(iIntentReceiver8.curComponent.getPackageName(), iIntentReceiver8.userId, 31);
                                }
                                try {
                                    broadcastQueueImpl.mService.mPackageManagerInt.setPackageStoppedState(iIntentReceiver8.curComponent.getPackageName(), false, iIntentReceiver8.userId);
                                } catch (IllegalArgumentException e6) {
                                    Slog.w("BroadcastQueue", "Failed trying to unstop package " + iIntentReceiver8.curComponent.getPackageName() + ": " + e6);
                                }
                                if (processRecordLocked == null || processRecordLocked.getThread() == null || processRecordLocked.isKilled()) {
                                    z7 = true;
                                } else {
                                    try {
                                        ActivityInfo activityInfo3 = resolveInfo.activityInfo;
                                        processRecordLocked.addPackage(activityInfo3.packageName, activityInfo3.applicationInfo.longVersionCode, broadcastQueueImpl.mService.mProcessStats);
                                        broadcastQueueImpl.maybeAddBackgroundStartPrivileges(processRecordLocked, iIntentReceiver8);
                                        iIntentReceiver8.mIsReceiverAppRunning = true;
                                        broadcastQueueImpl.processCurBroadcastLocked(iIntentReceiver8, processRecordLocked);
                                        return;
                                    } catch (RemoteException e7) {
                                        Slog.w("BroadcastQueue", "Failed to schedule " + iIntentReceiver8.intent + " to " + resolveInfo + " via " + processRecordLocked + ": " + e7);
                                        z7 = true;
                                        processRecordLocked.killLocked("Can't deliver broadcast", 13, 26, true);
                                    } catch (RuntimeException e8) {
                                        Slog.wtf("BroadcastQueue", "Failed sending broadcast to " + iIntentReceiver8.curComponent + " with " + iIntentReceiver8.intent, e8);
                                        broadcastQueueImpl.logBroadcastReceiverDiscardLocked(iIntentReceiver8);
                                        finishReceiverLocked((BroadcastRecord) iIntentReceiver8, iIntentReceiver8.resultCode, iIntentReceiver8.resultData, iIntentReceiver8.resultExtras, iIntentReceiver8.resultAbort, false);
                                        scheduleBroadcastsLocked();
                                        iIntentReceiver8.state = 0;
                                        return;
                                    }
                                }
                                ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
                                iIntentReceiver8.mWasReceiverAppStopped = (applicationInfo.flags & 2097152) != 0 ? z7 : false;
                                ProcessRecord startProcessLocked = broadcastQueueImpl.mService.startProcessLocked(str3, applicationInfo, true, iIntentReceiver8.intent.getFlags() | 4, new HostingRecord(INetd.IF_FLAG_BROADCAST, iIntentReceiver8.curComponent, iIntentReceiver8.intent.getAction(), iIntentReceiver8.getHostingRecordTriggerType(), iIntentReceiver5), i17, (iIntentReceiver8.intent.getFlags() & 33554432) != 0 ? z7 : false, false);
                                iIntentReceiver8.curApp = startProcessLocked;
                                iIntentReceiver8.curAppLastProcessState = 20;
                                if (startProcessLocked == null) {
                                    Slog.w("BroadcastQueue", "Unable to launch app " + resolveInfo.activityInfo.applicationInfo.packageName + "/" + i15 + " for broadcast " + iIntentReceiver8.intent + ": process is bad");
                                    broadcastQueueImpl.logBroadcastReceiverDiscardLocked(iIntentReceiver8);
                                    finishReceiverLocked((BroadcastRecord) iIntentReceiver8, iIntentReceiver8.resultCode, iIntentReceiver8.resultData, iIntentReceiver8.resultExtras, iIntentReceiver8.resultAbort, false);
                                    scheduleBroadcastsLocked();
                                    iIntentReceiver8.state = 0;
                                    return;
                                }
                                broadcastQueueImpl.maybeAddBackgroundStartPrivileges(startProcessLocked, iIntentReceiver8);
                                broadcastQueueImpl.mPendingBroadcast = iIntentReceiver8;
                                broadcastQueueImpl.mPendingBroadcastRecvIndex = i14;
                                return;
                            }
                        }
                        z6 = true;
                        i5 = 0;
                        if (!broadcastQueueImpl.mPendingBroadcastTimeoutMessage) {
                        }
                        BroadcastOptions broadcastOptions2 = iIntentReceiver8.options;
                        obj = iIntentReceiver8.receivers.get(i14);
                        if (!(obj instanceof BroadcastFilter)) {
                        }
                    } else {
                        iIntentReceiver8 = iIntentReceiver2;
                        broadcastQueueImpl3 = broadcastQueueImpl;
                        r11 = 1;
                        r14 = 0;
                    }
                }
            }
            z3 = r14;
            if (nextBroadcastLocked.state == 0) {
            }
        }
    }

    public final String getTargetPackage(BroadcastRecord broadcastRecord) {
        Intent intent = broadcastRecord.intent;
        if (intent == null) {
            return null;
        }
        if (intent.getPackage() != null) {
            return broadcastRecord.intent.getPackage();
        }
        if (broadcastRecord.intent.getComponent() != null) {
            return broadcastRecord.intent.getComponent().getPackageName();
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void logBootCompletedBroadcastCompletionLatencyIfPossible(BroadcastRecord broadcastRecord) {
        int i;
        int i2;
        List list = broadcastRecord.receivers;
        int size = list != null ? list.size() : 0;
        if (broadcastRecord.nextReceiver < size) {
            return;
        }
        String action = broadcastRecord.intent.getAction();
        if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(action)) {
            i2 = 1;
        } else {
            if (!"android.intent.action.BOOT_COMPLETED".equals(action)) {
                i = 0;
                if (i == 0) {
                    int i3 = (int) (broadcastRecord.dispatchTime - broadcastRecord.enqueueTime);
                    int uptimeMillis = (int) (SystemClock.uptimeMillis() - broadcastRecord.enqueueTime);
                    int i4 = (int) (broadcastRecord.dispatchRealTime - broadcastRecord.enqueueRealTime);
                    int elapsedRealtime = (int) (SystemClock.elapsedRealtime() - broadcastRecord.enqueueRealTime);
                    UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
                    UserInfo userInfo = userManagerInternal != null ? userManagerInternal.getUserInfo(broadcastRecord.userId) : null;
                    int userTypeForStatsd = userInfo != null ? UserJourneyLogger.getUserTypeForStatsd(userInfo.userType) : 0;
                    StringBuilder sb = new StringBuilder();
                    sb.append("BOOT_COMPLETED_BROADCAST_COMPLETION_LATENCY_REPORTED action:");
                    sb.append(action);
                    sb.append(" dispatchLatency:");
                    sb.append(i3);
                    sb.append(" completeLatency:");
                    sb.append(uptimeMillis);
                    sb.append(" dispatchRealLatency:");
                    sb.append(i4);
                    sb.append(" completeRealLatency:");
                    sb.append(elapsedRealtime);
                    sb.append(" receiversSize:");
                    sb.append(size);
                    sb.append(" userId:");
                    sb.append(broadcastRecord.userId);
                    sb.append(" userType:");
                    sb.append(userInfo != null ? userInfo.userType : null);
                    Slog.i("BroadcastQueue", sb.toString());
                    FrameworkStatsLog.write(FrameworkStatsLog.BOOT_COMPLETED_BROADCAST_COMPLETION_LATENCY_REPORTED, i, i3, uptimeMillis, i4, elapsedRealtime, broadcastRecord.userId, userTypeForStatsd);
                    return;
                }
                return;
            }
            i2 = 2;
        }
        i = i2;
        if (i == 0) {
        }
    }

    public final void maybeReportBroadcastDispatchedEventLocked(BroadcastRecord broadcastRecord, int i) {
        String targetPackage;
        BroadcastOptions broadcastOptions = broadcastRecord.options;
        if (broadcastOptions == null || broadcastOptions.getIdForResponseEvent() <= 0 || (targetPackage = getTargetPackage(broadcastRecord)) == null) {
            return;
        }
        this.mService.mUsageStatsService.reportBroadcastDispatched(broadcastRecord.callingUid, targetPackage, UserHandle.of(broadcastRecord.userId), broadcastRecord.options.getIdForResponseEvent(), SystemClock.elapsedRealtime(), this.mService.getUidStateLocked(i));
    }

    public final void maybeAddBackgroundStartPrivileges(ProcessRecord processRecord, BroadcastRecord broadcastRecord) {
        if (broadcastRecord == null || processRecord == null || !broadcastRecord.mBackgroundStartPrivileges.allowsAny()) {
            return;
        }
        this.mHandler.removeCallbacksAndMessages((processRecord.toShortString() + broadcastRecord.toString()).intern());
        processRecord.addOrUpdateBackgroundStartPrivileges(broadcastRecord, broadcastRecord.mBackgroundStartPrivileges);
    }

    public final void setBroadcastTimeoutLocked(long j) {
        if (this.mPendingBroadcastTimeoutMessage) {
            return;
        }
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(201, this), j);
        this.mPendingBroadcastTimeoutMessage = true;
    }

    public final void cancelBroadcastTimeoutLocked() {
        if (this.mPendingBroadcastTimeoutMessage) {
            this.mHandler.removeMessages(201, this);
            this.mPendingBroadcastTimeoutMessage = false;
        }
    }

    public final void broadcastTimeoutLocked(boolean z) {
        Object obj;
        ProcessRecord processRecord;
        boolean z2 = false;
        if (z) {
            this.mPendingBroadcastTimeoutMessage = false;
        }
        if (this.mDispatcher.isEmpty() || this.mDispatcher.getActiveBroadcastLocked() == null) {
            return;
        }
        Trace.traceBegin(64L, "broadcastTimeoutLocked()");
        try {
            long uptimeMillis = SystemClock.uptimeMillis();
            BroadcastRecord activeBroadcastLocked = this.mDispatcher.getActiveBroadcastLocked();
            if (z) {
                if (!this.mService.mProcessesReady) {
                    return;
                }
                if (activeBroadcastLocked.timeoutExempt) {
                    return;
                }
                long j = activeBroadcastLocked.receiverTime + this.mConstants.TIMEOUT;
                if (j > uptimeMillis) {
                    setBroadcastTimeoutLocked(j);
                    return;
                }
            }
            if (activeBroadcastLocked.state == 4) {
                StringBuilder sb = new StringBuilder();
                sb.append("Waited long enough for: ");
                ComponentName componentName = activeBroadcastLocked.curComponent;
                sb.append(componentName != null ? componentName.flattenToShortString() : "(null)");
                Slog.i("BroadcastQueue", sb.toString());
                activeBroadcastLocked.curComponent = null;
                activeBroadcastLocked.state = 0;
                processNextBroadcastLocked(false, false);
                return;
            }
            ProcessRecord processRecord2 = activeBroadcastLocked.curApp;
            if (processRecord2 != null && processRecord2.isDebugging()) {
                z2 = true;
            }
            long j2 = uptimeMillis - activeBroadcastLocked.receiverTime;
            Slog.w("BroadcastQueue", "Timeout of broadcast " + activeBroadcastLocked + " - curFilter=" + activeBroadcastLocked.curFilter + " curReceiver=" + activeBroadcastLocked.curReceiver + ", started " + j2 + "ms ago");
            activeBroadcastLocked.receiverTime = uptimeMillis;
            if (!z2) {
                activeBroadcastLocked.anrCount++;
            }
            int i = activeBroadcastLocked.nextReceiver;
            if (i > 0) {
                obj = activeBroadcastLocked.receivers.get(i - 1);
                activeBroadcastLocked.delivery[activeBroadcastLocked.nextReceiver - 1] = 3;
            } else {
                obj = activeBroadcastLocked.curReceiver;
            }
            Slog.w("BroadcastQueue", "Receiver during timeout of " + activeBroadcastLocked + " : " + obj);
            logBroadcastReceiverDiscardLocked(activeBroadcastLocked);
            TimeoutRecord forBroadcastReceiver = TimeoutRecord.forBroadcastReceiver(activeBroadcastLocked.intent, j2);
            if (obj == null || !(obj instanceof BroadcastFilter)) {
                processRecord = activeBroadcastLocked.curApp;
            } else {
                BroadcastFilter broadcastFilter = (BroadcastFilter) obj;
                int i2 = broadcastFilter.receiverList.pid;
                if (i2 == 0 || i2 == ActivityManagerService.MY_PID) {
                    processRecord = null;
                } else {
                    forBroadcastReceiver.mLatencyTracker.waitingOnPidLockStarted();
                    synchronized (this.mService.mPidsSelfLocked) {
                        forBroadcastReceiver.mLatencyTracker.waitingOnPidLockEnded();
                        processRecord = this.mService.mPidsSelfLocked.get(broadcastFilter.receiverList.pid);
                    }
                }
            }
            if (this.mPendingBroadcast == activeBroadcastLocked) {
                this.mPendingBroadcast = null;
            }
            finishReceiverLocked(activeBroadcastLocked, activeBroadcastLocked.resultCode, activeBroadcastLocked.resultData, activeBroadcastLocked.resultExtras, activeBroadcastLocked.resultAbort, false);
            scheduleBroadcastsLocked();
            if (!z2 && processRecord != null) {
                this.mService.appNotResponding(processRecord, forBroadcastReceiver);
            }
        } finally {
            Trace.traceEnd(64L);
        }
    }

    public final void addBroadcastToHistoryLocked(BroadcastRecord broadcastRecord) {
        if (broadcastRecord.callingUid < 0) {
            return;
        }
        broadcastRecord.finishTime = SystemClock.uptimeMillis();
        if (Trace.isTagEnabled(64L)) {
            Trace.asyncTraceEnd(64L, createBroadcastTraceTitle(broadcastRecord, 1), System.identityHashCode(broadcastRecord));
        }
        this.mService.notifyBroadcastFinishedLocked(broadcastRecord);
        this.mHistory.addBroadcastToHistoryLocked(broadcastRecord);
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean cleanupDisabledPackageReceiversLocked(String str, Set set, int i) {
        boolean z = false;
        for (int size = this.mParallelBroadcasts.size() - 1; size >= 0; size--) {
            z |= ((BroadcastRecord) this.mParallelBroadcasts.get(size)).cleanupDisabledPackageReceiversLocked(str, set, i, true);
        }
        return this.mDispatcher.cleanupDisabledPackageReceiversLocked(str, set, i, true) | z;
    }

    public final void logBroadcastReceiverDiscardLocked(BroadcastRecord broadcastRecord) {
        int i = broadcastRecord.nextReceiver - 1;
        if (i >= 0 && i < broadcastRecord.receivers.size()) {
            Object obj = broadcastRecord.receivers.get(i);
            if (obj instanceof BroadcastFilter) {
                BroadcastFilter broadcastFilter = (BroadcastFilter) obj;
                EventLog.writeEvent(30024, Integer.valueOf(broadcastFilter.owningUserId), Integer.valueOf(System.identityHashCode(broadcastRecord)), broadcastRecord.intent.getAction(), Integer.valueOf(i), Integer.valueOf(System.identityHashCode(broadcastFilter)));
                return;
            } else {
                ResolveInfo resolveInfo = (ResolveInfo) obj;
                EventLog.writeEvent(30025, Integer.valueOf(UserHandle.getUserId(resolveInfo.activityInfo.applicationInfo.uid)), Integer.valueOf(System.identityHashCode(broadcastRecord)), broadcastRecord.intent.getAction(), Integer.valueOf(i), resolveInfo.toString());
                return;
            }
        }
        if (i < 0) {
            Slog.w("BroadcastQueue", "Discarding broadcast before first receiver is invoked: " + broadcastRecord);
        }
        EventLog.writeEvent(30025, -1, Integer.valueOf(System.identityHashCode(broadcastRecord)), broadcastRecord.intent.getAction(), Integer.valueOf(broadcastRecord.nextReceiver), "NONE");
    }

    public final String createBroadcastTraceTitle(BroadcastRecord broadcastRecord, int i) {
        Object[] objArr = new Object[4];
        objArr[0] = i == 0 ? "in queue" : "dispatched";
        String str = broadcastRecord.callerPackage;
        if (str == null) {
            str = "";
        }
        objArr[1] = str;
        ProcessRecord processRecord = broadcastRecord.callerApp;
        objArr[2] = processRecord == null ? "process unknown" : processRecord.toShortString();
        Intent intent = broadcastRecord.intent;
        objArr[3] = intent != null ? intent.getAction() : "";
        return TextUtils.formatSimple("Broadcast %s from %s (%s) %s", objArr);
    }

    @Override // com.android.server.am.BroadcastQueue
    /* renamed from: isIdleLocked, reason: merged with bridge method [inline-methods] */
    public boolean lambda$waitForIdle$1() {
        return this.mParallelBroadcasts.isEmpty() && this.mDispatcher.isIdle() && this.mPendingBroadcast == null;
    }

    /* renamed from: isBeyondBarrierLocked, reason: merged with bridge method [inline-methods] */
    public boolean lambda$waitForBarrier$2(long j) {
        if (lambda$waitForIdle$1()) {
            return true;
        }
        for (int i = 0; i < this.mParallelBroadcasts.size(); i++) {
            if (((BroadcastRecord) this.mParallelBroadcasts.get(i)).enqueueTime <= j) {
                return false;
            }
        }
        BroadcastRecord pendingBroadcastLocked = getPendingBroadcastLocked();
        if (pendingBroadcastLocked == null || pendingBroadcastLocked.enqueueTime > j) {
            return this.mDispatcher.isBeyondBarrier(j);
        }
        return false;
    }

    /* renamed from: isDispatchedLocked, reason: merged with bridge method [inline-methods] */
    public boolean lambda$waitForDispatched$3(Intent intent) {
        if (lambda$waitForIdle$1()) {
            return true;
        }
        for (int i = 0; i < this.mParallelBroadcasts.size(); i++) {
            if (intent.filterEquals(((BroadcastRecord) this.mParallelBroadcasts.get(i)).intent)) {
                return false;
            }
        }
        BroadcastRecord pendingBroadcastLocked = getPendingBroadcastLocked();
        if (pendingBroadcastLocked == null || !intent.filterEquals(pendingBroadcastLocked.intent)) {
            return this.mDispatcher.isDispatched(intent);
        }
        return false;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void waitForIdle(PrintWriter printWriter) {
        waitFor(new BooleanSupplier() { // from class: com.android.server.am.BroadcastQueueImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$waitForIdle$1;
                lambda$waitForIdle$1 = BroadcastQueueImpl.this.lambda$waitForIdle$1();
                return lambda$waitForIdle$1;
            }
        }, printWriter, "idle");
    }

    @Override // com.android.server.am.BroadcastQueue
    public void waitForBarrier(PrintWriter printWriter) {
        final long uptimeMillis = SystemClock.uptimeMillis();
        waitFor(new BooleanSupplier() { // from class: com.android.server.am.BroadcastQueueImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$waitForBarrier$2;
                lambda$waitForBarrier$2 = BroadcastQueueImpl.this.lambda$waitForBarrier$2(uptimeMillis);
                return lambda$waitForBarrier$2;
            }
        }, printWriter, "barrier");
    }

    @Override // com.android.server.am.BroadcastQueue
    public void waitForDispatched(final Intent intent, PrintWriter printWriter) {
        waitFor(new BooleanSupplier() { // from class: com.android.server.am.BroadcastQueueImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$waitForDispatched$3;
                lambda$waitForDispatched$3 = BroadcastQueueImpl.this.lambda$waitForDispatched$3(intent);
                return lambda$waitForDispatched$3;
            }
        }, printWriter, "dispatch");
    }

    public final void waitFor(BooleanSupplier booleanSupplier, PrintWriter printWriter, String str) {
        long j = 0;
        while (true) {
            ActivityManagerService activityManagerService = this.mService;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    if (booleanSupplier.getAsBoolean()) {
                        break;
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            long uptimeMillis = SystemClock.uptimeMillis();
            if (uptimeMillis >= 1000 + j) {
                String str2 = "Queue [" + this.mQueueName + "] waiting for " + str + " condition; state is " + describeStateLocked();
                Slog.v("BroadcastQueue", str2);
                if (printWriter != null) {
                    printWriter.println(str2);
                    printWriter.flush();
                }
                j = uptimeMillis;
            }
            cancelDeferrals();
            SystemClock.sleep(100L);
        }
        String str3 = "Queue [" + this.mQueueName + "] reached " + str + " condition";
        Slog.v("BroadcastQueue", str3);
        if (printWriter != null) {
            printWriter.println(str3);
            printWriter.flush();
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public void cancelDeferrals() {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mDispatcher.cancelDeferralsLocked();
                scheduleBroadcastsLocked();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.am.BroadcastQueue
    public String describeStateLocked() {
        return this.mParallelBroadcasts.size() + " parallel; " + this.mDispatcher.describeStateLocked();
    }

    @Override // com.android.server.am.BroadcastQueue
    @NeverCompile
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mQueueName);
        for (int size = this.mParallelBroadcasts.size() - 1; size >= 0; size--) {
            ((BroadcastRecord) this.mParallelBroadcasts.get(size)).dumpDebug(protoOutputStream, 2246267895810L);
        }
        this.mDispatcher.dumpDebug(protoOutputStream, 2246267895811L);
        BroadcastRecord broadcastRecord = this.mPendingBroadcast;
        if (broadcastRecord != null) {
            broadcastRecord.dumpDebug(protoOutputStream, 1146756268036L);
        }
        this.mHistory.dumpDebug(protoOutputStream);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.am.BroadcastQueue
    @NeverCompile
    public boolean dumpLocked(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, int i, boolean z, boolean z2, boolean z3, String str, boolean z4) {
        boolean z5;
        BroadcastRecord broadcastRecord;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if (this.mParallelBroadcasts.isEmpty() && this.mDispatcher.isEmpty() && this.mPendingBroadcast == null) {
            z5 = z4;
        } else {
            boolean z6 = false;
            boolean z7 = z4;
            for (int size = this.mParallelBroadcasts.size() - 1; size >= 0; size--) {
                BroadcastRecord broadcastRecord2 = (BroadcastRecord) this.mParallelBroadcasts.get(size);
                if (str == null || str.equals(broadcastRecord2.callerPackage)) {
                    if (!z6) {
                        if (z7) {
                            printWriter.println();
                        }
                        printWriter.println("  Active broadcasts [" + this.mQueueName + "]:");
                        z7 = true;
                        z6 = true;
                    }
                    printWriter.println("  Active Broadcast " + this.mQueueName + " #" + size + XmlUtils.STRING_ARRAY_SEPARATOR);
                    broadcastRecord2.dump(printWriter, "    ", simpleDateFormat);
                }
            }
            this.mDispatcher.dumpLocked(printWriter, str, this.mQueueName, simpleDateFormat);
            if (str == null || ((broadcastRecord = this.mPendingBroadcast) != null && str.equals(broadcastRecord.callerPackage))) {
                printWriter.println();
                printWriter.println("  Pending broadcast [" + this.mQueueName + "]:");
                BroadcastRecord broadcastRecord3 = this.mPendingBroadcast;
                if (broadcastRecord3 != null) {
                    broadcastRecord3.dump(printWriter, "    ", simpleDateFormat);
                } else {
                    printWriter.println("    (null)");
                }
                z5 = true;
            } else {
                z5 = z7;
            }
        }
        if (z) {
            this.mConstants.dump(new IndentingPrintWriter(printWriter));
        }
        return z2 ? this.mHistory.dumpLocked(printWriter, str, this.mQueueName, simpleDateFormat, z3, z5) : z5;
    }
}
