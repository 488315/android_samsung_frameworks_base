package com.android.server.am;

import android.app.ActivityManager;
import android.app.BroadcastOptions;
import android.app.IApplicationThread;
import android.app.usage.UsageStatsManagerInternal;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.net.INetd;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.MathUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;

import com.android.internal.os.SomeArgs;
import com.android.internal.os.TimeoutRecord;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DropBoxManagerInternal$EntrySource;
import com.android.server.DropBoxManagerService;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.mars.filter.filter.RunningBroadcastFilter;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserJourneyLogger;
import com.android.server.pm.UserManagerInternal;
import com.android.server.usage.BroadcastEvent;
import com.android.server.usage.BroadcastResponseStatsLogger;
import com.android.server.usage.BroadcastResponseStatsTracker;
import com.android.server.usage.UsageStatsService;
import com.android.server.utils.AnrTimer;

import java.io.CharArrayWriter;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class BroadcastQueueModernImpl extends BroadcastQueue {
    public final BroadcastAnrTimer mAnrTimer;
    public final BroadcastConstants mBgConstants;
    final BroadcastProcessQueue.BroadcastConsumer mBroadcastConsumerDeferApply;
    final BroadcastProcessQueue.BroadcastConsumer mBroadcastConsumerDeferClear;
    public final BroadcastQueueModernImpl$$ExternalSyntheticLambda4 mBroadcastConsumerSkip;
    public final BroadcastQueueModernImpl$$ExternalSyntheticLambda4
            mBroadcastConsumerSkipAndCanceled;
    public final BroadcastQueueModernImpl$$ExternalSyntheticLambda4 mBroadcastRecordConsumerEnqueue;
    public boolean mCheckPendingColdStartQueued;
    public final BroadcastConstants mConstants;
    public final ArrayList mDelayedBroadcasts;
    public final BroadcastConstants mFgConstants;
    public long mLastTestFailureTime;
    public final BroadcastQueueModernImpl$$ExternalSyntheticLambda12 mLocalCallback;
    public final Handler mLocalHandler;
    public final AtomicReference mMatchingRecordsCache;
    public final SparseArray mProcessQueues;
    public final AtomicReference mRecordsLookupCache;
    public final AtomicReference mReplacedBroadcastsCache;
    public BroadcastProcessQueue mRunnableHead;
    public final BroadcastProcessQueue[] mRunning;
    public BroadcastProcessQueue mRunningColdStart;
    public final SparseBooleanArray mUidForeground;
    public final ArrayList mWaitingFor;
    public static final BroadcastQueueModernImpl$$ExternalSyntheticLambda1 QUEUE_PREDICATE_ANY =
            new BroadcastQueueModernImpl$$ExternalSyntheticLambda1(2);
    public static final BroadcastQueueModernImpl$$ExternalSyntheticLambda7 BROADCAST_PREDICATE_ANY =
            new BroadcastQueueModernImpl$$ExternalSyntheticLambda7(0);

    public final class BroadcastAnrTimer extends AnrTimer {
        @Override // com.android.server.utils.AnrTimer
        public final int getPid(Object obj) {
            ProcessRecord processRecord = ((BroadcastProcessQueue) obj).app;
            if (processRecord != null) {
                return processRecord.mPid;
            }
            return 0;
        }

        @Override // com.android.server.utils.AnrTimer
        public final int getUid(Object obj) {
            ProcessRecord processRecord = ((BroadcastProcessQueue) obj).app;
            if (processRecord != null) {
                return processRecord.uid;
            }
            return 0;
        }
    }

    public BroadcastQueueModernImpl(
            ActivityManagerService activityManagerService,
            Handler handler,
            BroadcastConstants broadcastConstants,
            BroadcastConstants broadcastConstants2) {
        super(
                activityManagerService,
                handler,
                new BroadcastSkipPolicy(activityManagerService),
                new BroadcastHistory(activityManagerService, broadcastConstants));
        this.mDelayedBroadcasts = new ArrayList();
        this.mProcessQueues = new SparseArray();
        this.mRunnableHead = null;
        this.mWaitingFor = new ArrayList();
        this.mReplacedBroadcastsCache = new AtomicReference();
        this.mRecordsLookupCache = new AtomicReference();
        this.mMatchingRecordsCache = new AtomicReference();
        this.mUidForeground = new SparseBooleanArray();
        Handler.Callback callback =
                new Handler
                        .Callback() { // from class:
                                      // com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda12
                    @Override // android.os.Handler.Callback
                    public final boolean handleMessage(Message message) {
                        BroadcastQueueModernImpl broadcastQueueModernImpl =
                                BroadcastQueueModernImpl.this;
                        broadcastQueueModernImpl.getClass();
                        switch (message.what) {
                            case 1:
                                broadcastQueueModernImpl.updateRunningList();
                                break;
                            case 2:
                                broadcastQueueModernImpl.deliveryTimeout(
                                        (BroadcastProcessQueue) message.obj);
                                break;
                            case 3:
                                ActivityManagerService activityManagerService2 =
                                        broadcastQueueModernImpl.mService;
                                ActivityManagerService.boostPriorityForLockedSection();
                                synchronized (activityManagerService2) {
                                    try {
                                        SomeArgs someArgs = (SomeArgs) message.obj;
                                        ProcessRecord processRecord = (ProcessRecord) someArgs.arg1;
                                        BroadcastRecord broadcastRecord =
                                                (BroadcastRecord) someArgs.arg2;
                                        someArgs.recycle();
                                        processRecord.removeBackgroundStartPrivileges(
                                                broadcastRecord);
                                    } finally {
                                        ActivityManagerService.resetPriorityAfterLockedSection();
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                                break;
                            case 4:
                                broadcastQueueModernImpl.checkHealth();
                                break;
                            case 5:
                                ActivityManagerService activityManagerService3 =
                                        broadcastQueueModernImpl.mService;
                                ActivityManagerService.boostPriorityForLockedSection();
                                synchronized (activityManagerService3) {
                                    try {
                                        broadcastQueueModernImpl.mCheckPendingColdStartQueued =
                                                false;
                                        broadcastQueueModernImpl
                                                .checkPendingColdStartValidityLocked();
                                    } finally {
                                        ActivityManagerService.resetPriorityAfterLockedSection();
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                                break;
                            case 6:
                                broadcastQueueModernImpl.handleProcessFreezableChanged(
                                        (ProcessRecord) message.obj);
                                break;
                            case 7:
                                int intValue = ((Integer) message.obj).intValue();
                                int i = message.arg1;
                                ActivityManagerService activityManagerService4 =
                                        broadcastQueueModernImpl.mService;
                                ActivityManagerService.boostPriorityForLockedSection();
                                synchronized (activityManagerService4) {
                                    try {
                                        if (i == 2) {
                                            broadcastQueueModernImpl.mUidForeground.put(
                                                    intValue, true);
                                        } else {
                                            broadcastQueueModernImpl.mUidForeground.delete(
                                                    intValue);
                                        }
                                        for (BroadcastProcessQueue broadcastProcessQueue =
                                                        (BroadcastProcessQueue)
                                                                broadcastQueueModernImpl
                                                                        .mProcessQueues.get(
                                                                        intValue);
                                                broadcastProcessQueue != null;
                                                broadcastProcessQueue =
                                                        broadcastProcessQueue.processNameNext) {
                                            broadcastQueueModernImpl.setQueueProcess(
                                                    broadcastProcessQueue,
                                                    broadcastProcessQueue.app);
                                        }
                                        broadcastQueueModernImpl.enqueueUpdateRunningList();
                                    } finally {
                                        ActivityManagerService.resetPriorityAfterLockedSection();
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                                break;
                            case 8:
                                ActivityManagerService activityManagerService5 =
                                        broadcastQueueModernImpl.mService;
                                ActivityManagerService.boostPriorityForLockedSection();
                                synchronized (activityManagerService5) {
                                    try {
                                        BroadcastProcessQueue broadcastProcessQueue2 =
                                                (BroadcastProcessQueue) message.obj;
                                        int i2 = message.arg1;
                                        ProcessRecord processRecord2 = broadcastProcessQueue2.app;
                                        if (processRecord2 != null) {
                                            broadcastQueueModernImpl.mAnrTimer.start(
                                                    MathUtils.constrain(
                                                            processRecord2.mService.mAppProfiler
                                                                            .mProcessCpuTracker
                                                                            .getCpuDelayTimeForPid(
                                                                                    processRecord2
                                                                                            .mPid)
                                                                    - broadcastProcessQueue2
                                                                            .lastCpuDelayTime,
                                                            0L,
                                                            i2),
                                                    broadcastProcessQueue2);
                                        } else {
                                            broadcastQueueModernImpl.finishReceiverActiveLocked(
                                                    broadcastProcessQueue2,
                                                    3,
                                                    "deliveryTimeoutLocked");
                                            broadcastQueueModernImpl.demoteFromRunningLocked(
                                                    broadcastProcessQueue2);
                                        }
                                    } finally {
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                                break;
                            case 9:
                                ActivityManagerService activityManagerService6 =
                                        broadcastQueueModernImpl.mService;
                                ActivityManagerService.boostPriorityForLockedSection();
                                synchronized (activityManagerService6) {
                                    while (broadcastQueueModernImpl.mDelayedBroadcasts.size()
                                            != 0) {
                                        try {
                                            broadcastQueueModernImpl.enqueueBroadcastLocked(
                                                    (BroadcastRecord)
                                                            broadcastQueueModernImpl
                                                                    .mDelayedBroadcasts.remove(0));
                                        } finally {
                                        }
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                                return false;
                            default:
                                return false;
                        }
                        return true;
                    }
                };
        this.mBroadcastConsumerSkip =
                new BroadcastQueueModernImpl$$ExternalSyntheticLambda4(1, this);
        this.mBroadcastConsumerSkipAndCanceled =
                new BroadcastQueueModernImpl$$ExternalSyntheticLambda4(2, this);
        this.mBroadcastConsumerDeferApply =
                new BroadcastQueueModernImpl$$ExternalSyntheticLambda4(3, this);
        this.mBroadcastConsumerDeferClear =
                new BroadcastQueueModernImpl$$ExternalSyntheticLambda4(4, this);
        this.mBroadcastRecordConsumerEnqueue =
                new BroadcastQueueModernImpl$$ExternalSyntheticLambda4(5, this);
        this.mConstants = broadcastConstants;
        this.mFgConstants = broadcastConstants;
        this.mBgConstants = broadcastConstants2;
        Handler handler2 = new Handler(handler.getLooper(), callback);
        this.mLocalHandler = handler2;
        this.mRunning =
                new BroadcastProcessQueue
                        [broadcastConstants.MAX_RUNNING_PROCESS_QUEUES
                                + broadcastConstants.EXTRA_RUNNING_URGENT_PROCESS_QUEUES];
        this.mAnrTimer =
                new BroadcastAnrTimer(handler2, 2, "BROADCAST_TIMEOUT", new AnrTimer.Args());
    }

    public static boolean containsAllReceivers(
            BroadcastRecord broadcastRecord, BroadcastRecord broadcastRecord2, ArrayMap arrayMap) {
        int indexOfKey = arrayMap.indexOfKey(broadcastRecord2);
        if (indexOfKey > 0) {
            return ((Boolean) arrayMap.valueAt(indexOfKey)).booleanValue();
        }
        List list = broadcastRecord2.receivers;
        broadcastRecord.getClass();
        boolean z = true;
        int size = list.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (!broadcastRecord.containsReceiver(list.get(size))) {
                z = false;
                break;
            }
            size--;
        }
        arrayMap.put(broadcastRecord2, Boolean.valueOf(z));
        return z;
    }

    public void assertHealthLocked() {
        int i;
        BroadcastProcessQueue broadcastProcessQueue = this.mRunnableHead;
        BroadcastProcessQueue broadcastProcessQueue2 = null;
        while (true) {
            if (broadcastProcessQueue == null) {
                break;
            }
            BroadcastQueue.checkState(
                    broadcastProcessQueue.runnableAtPrev == broadcastProcessQueue2,
                    "runnableAtPrev");
            BroadcastQueue.checkState(
                    broadcastProcessQueue.isRunnable(), "isRunnable " + broadcastProcessQueue);
            if (broadcastProcessQueue2 != null) {
                BroadcastQueue.checkState(
                        broadcastProcessQueue.getRunnableAt()
                                >= broadcastProcessQueue2.getRunnableAt(),
                        "getRunnableAt " + broadcastProcessQueue + " vs " + broadcastProcessQueue2);
            }
            broadcastProcessQueue2 = broadcastProcessQueue;
            broadcastProcessQueue = broadcastProcessQueue.runnableAtNext;
        }
        for (BroadcastProcessQueue broadcastProcessQueue3 : this.mRunning) {
            if (broadcastProcessQueue3 != null) {
                BroadcastQueue.checkState(
                        broadcastProcessQueue3.isActive(), "isActive " + broadcastProcessQueue3);
            }
        }
        BroadcastProcessQueue broadcastProcessQueue4 = this.mRunningColdStart;
        if (broadcastProcessQueue4 != null) {
            BroadcastQueue.checkState(
                    getRunningIndexOf(broadcastProcessQueue4) >= 0,
                    "isOrphaned " + this.mRunningColdStart);
        }
        for (i = 0; i < this.mProcessQueues.size(); i++) {
            for (BroadcastProcessQueue broadcastProcessQueue5 =
                            (BroadcastProcessQueue) this.mProcessQueues.valueAt(i);
                    broadcastProcessQueue5 != null;
                    broadcastProcessQueue5 = broadcastProcessQueue5.processNameNext) {
                if (!broadcastProcessQueue5.isActive()) {
                    Preconditions.checkState(
                            !broadcastProcessQueue5.mRunnableAtInvalidated,
                            "mRunnableAtInvalidated");
                }
                BroadcastProcessQueue.assertHealthLocked(broadcastProcessQueue5.mPending);
                BroadcastProcessQueue.assertHealthLocked(broadcastProcessQueue5.mPendingUrgent);
                BroadcastProcessQueue.assertHealthLocked(broadcastProcessQueue5.mPendingOffload);
            }
        }
    }

    public final void checkHealth() {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                try {
                    assertHealthLocked();
                    this.mLocalHandler.sendEmptyMessageDelayed(4, 60000L);
                } catch (Exception e) {
                    Slog.wtf("BroadcastQueue", e);
                    final String exc = e.toString();
                    DropBoxManagerService.DropBoxManagerInternalImpl dropBoxManagerInternalImpl =
                            (DropBoxManagerService.DropBoxManagerInternalImpl)
                                    LocalServices.getService(
                                            DropBoxManagerService.DropBoxManagerInternalImpl.class);
                    DropBoxManagerService.this.addEntry(
                            "broadcast_queue_dump",
                            new DropBoxManagerInternal$EntrySource() { // from class:
                                                                       // com.android.server.am.BroadcastQueue$$ExternalSyntheticLambda0
                                @Override // com.android.server.DropBoxManagerInternal$EntrySource
                                public final void writeTo(FileDescriptor fileDescriptor) {
                                    BroadcastQueue broadcastQueue = BroadcastQueue.this;
                                    String str = exc;
                                    broadcastQueue.getClass();
                                    FileOutputStream fileOutputStream =
                                            new FileOutputStream(fileDescriptor);
                                    try {
                                        PrintWriter printWriter = new PrintWriter(fileOutputStream);
                                        try {
                                            printWriter.print("Message: ");
                                            printWriter.println(str);
                                            broadcastQueue.dumpLocked(
                                                    printWriter, false, false, false, null, false);
                                            printWriter.flush();
                                            printWriter.close();
                                            fileOutputStream.close();
                                        } finally {
                                        }
                                    } catch (Throwable th) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                        throw th;
                                    }
                                }
                            },
                            2);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public final void checkPendingColdStartValidityLocked() {
        BroadcastProcessQueue broadcastProcessQueue = this.mRunningColdStart;
        if (broadcastProcessQueue == null) {
            return;
        }
        ProcessRecord processRecord = broadcastProcessQueue.app;
        if (!(processRecord.mPid > 0 ? !processRecord.mKilled : processRecord.mPendingStart)) {
            clearInvalidPendingColdStart();
        } else {
            if (this.mCheckPendingColdStartQueued) {
                return;
            }
            this.mLocalHandler.sendEmptyMessageDelayed(
                    5, this.mConstants.PENDING_COLD_START_CHECK_INTERVAL_MILLIS);
            this.mCheckPendingColdStartQueued = true;
        }
    }

    public final boolean cleanupDisabledPackageReceiversLocked(
            final int i, final String str, final Set set) {
        Predicate predicate;
        BroadcastProcessQueue.BroadcastPredicate broadcastPredicate;
        if (str != null) {
            final int packageUid = this.mService.mPackageManagerInt.getPackageUid(str, 8192L, i);
            final int i2 = 0;
            predicate =
                    new Predicate() { // from class:
                                      // com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            int i3 = i2;
                            int i4 = packageUid;
                            BroadcastProcessQueue broadcastProcessQueue =
                                    (BroadcastProcessQueue) obj;
                            switch (i3) {
                                case 0:
                                    if (broadcastProcessQueue.uid == i4) {}
                                    break;
                                default:
                                    if (UserHandle.getUserId(broadcastProcessQueue.uid) == i4) {}
                                    break;
                            }
                            return false;
                        }
                    };
            broadcastPredicate =
                    set != null
                            ? new BroadcastProcessQueue
                                    .BroadcastPredicate() { // from class:
                                                            // com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda3
                                @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
                                public final boolean test(BroadcastRecord broadcastRecord, int i3) {
                                    Set set2 = set;
                                    Object obj = broadcastRecord.receivers.get(i3);
                                    if (!(obj instanceof ResolveInfo)) {
                                        return false;
                                    }
                                    ActivityInfo activityInfo = ((ResolveInfo) obj).activityInfo;
                                    return str.equals(activityInfo.packageName)
                                            && set2.contains(activityInfo.name);
                                }
                            }
                            : new BroadcastQueueModernImpl$$ExternalSyntheticLambda4(0, str);
        } else {
            final int i3 = 1;
            predicate =
                    new Predicate() { // from class:
                                      // com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            int i32 = i3;
                            int i4 = i;
                            BroadcastProcessQueue broadcastProcessQueue =
                                    (BroadcastProcessQueue) obj;
                            switch (i32) {
                                case 0:
                                    if (broadcastProcessQueue.uid == i4) {}
                                    break;
                                default:
                                    if (UserHandle.getUserId(broadcastProcessQueue.uid) == i4) {}
                                    break;
                            }
                            return false;
                        }
                    };
            BroadcastQueueModernImpl$$ExternalSyntheticLambda7
                    broadcastQueueModernImpl$$ExternalSyntheticLambda7 = BROADCAST_PREDICATE_ANY;
            SparseBooleanArray sparseBooleanArray = this.mUidForeground;
            for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
                if (UserHandle.getUserId(sparseBooleanArray.keyAt(size)) == i) {
                    sparseBooleanArray.removeAt(size);
                }
            }
            broadcastPredicate = broadcastQueueModernImpl$$ExternalSyntheticLambda7;
        }
        return forEachMatchingBroadcast(predicate, broadcastPredicate, this.mBroadcastConsumerSkip);
    }

    public final void clearInvalidPendingColdStart() {
        BroadcastQueue.logw("Clearing invalid pending cold start: " + this.mRunningColdStart);
        BroadcastProcessQueue broadcastProcessQueue = this.mRunningColdStart;
        broadcastProcessQueue.getClass();
        Flags.avoidRepeatedBcastReEnqueues();
        if (broadcastProcessQueue.mActiveReEnqueued) {
            finishReceiverActiveLocked(
                    this.mRunningColdStart, 5, "invalid start with re-enqueued broadcast");
        } else {
            BroadcastProcessQueue broadcastProcessQueue2 = this.mRunningColdStart;
            BroadcastRecord active = broadcastProcessQueue2.getActive();
            int activeIndex = broadcastProcessQueue2.getActiveIndex();
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = active;
            obtain.argi1 = activeIndex;
            obtain.argi2 = 1;
            broadcastProcessQueue2.getQueueForBroadcast(active).addFirst(obtain);
            broadcastProcessQueue2.onBroadcastEnqueued(active, activeIndex);
        }
        demoteFromRunningLocked(this.mRunningColdStart);
        BroadcastProcessQueue broadcastProcessQueue3 = this.mRunningColdStart;
        Trace.asyncTraceForTrackEnd(
                64L,
                broadcastProcessQueue3.runningTraceTrackName,
                broadcastProcessQueue3.hashCode());
        this.mRunningColdStart = null;
        enqueueUpdateRunningList();
        enqueueUpdateRunningList();
    }

    public final void deliveryTimeout(BroadcastProcessQueue broadcastProcessQueue) {
        int traceBegin = BroadcastQueue.traceBegin("deliveryTimeout");
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                finishReceiverActiveLocked(broadcastProcessQueue, 3, "deliveryTimeoutLocked");
                demoteFromRunningLocked(broadcastProcessQueue);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        BroadcastQueue.traceEnd(traceBegin);
    }

    public final void demoteFromRunningLocked(BroadcastProcessQueue broadcastProcessQueue) {
        if (!UserHandle.isCore(broadcastProcessQueue.uid)) {
            RunningBroadcastFilter runningBroadcastFilter =
                    RunningBroadcastFilter.RunningBroadcastFilterHolder.INSTANCE;
            Integer valueOf = Integer.valueOf(broadcastProcessQueue.uid);
            synchronized (runningBroadcastFilter.mRunningBroadcastList) {
                try {
                    if (runningBroadcastFilter.mRunningBroadcastList.contains(valueOf)) {
                        runningBroadcastFilter.mRunningBroadcastList.remove(valueOf);
                    }
                } finally {
                }
            }
        }
        if (!broadcastProcessQueue.isActive()) {
            BroadcastQueue.logw(
                    "Ignoring demoteFromRunning; no active broadcast for " + broadcastProcessQueue);
            return;
        }
        int traceBegin = BroadcastQueue.traceBegin("demoteFromRunning");
        broadcastProcessQueue.mActive = null;
        broadcastProcessQueue.mActiveIndex = 0;
        broadcastProcessQueue.mActiveReEnqueued = false;
        broadcastProcessQueue.mActiveCountSinceIdle = 0;
        broadcastProcessQueue.mActiveAssumedDeliveryCountSinceIdle = 0;
        broadcastProcessQueue.mActiveViaColdStart = false;
        broadcastProcessQueue.mRunnableAtInvalidated = true;
        Trace.asyncTraceForTrackEnd(
                64L, broadcastProcessQueue.runningTraceTrackName, broadcastProcessQueue.hashCode());
        this.mRunning[getRunningIndexOf(broadcastProcessQueue)] = null;
        updateRunnableList(broadcastProcessQueue);
        enqueueUpdateRunningList();
        ProcessRecord processRecord = broadcastProcessQueue.app;
        if (processRecord != null) {
            processRecord.mReceivers.mCurReceiversSize--;
            if (broadcastProcessQueue.runningOomAdjusted) {
                this.mService.enqueueOomAdjTargetLocked(processRecord);
            }
        }
        BroadcastQueue.traceEnd(traceBegin);
    }

    @Override // com.android.server.am.BroadcastQueue
    @dalvik.annotation.optimization.NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dumpLocked(
            java.io.PrintWriter r26,
            boolean r27,
            boolean r28,
            boolean r29,
            java.lang.String r30,
            boolean r31) {
        /*
            Method dump skipped, instructions count: 1766
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BroadcastQueueModernImpl.dumpLocked(java.io.PrintWriter,"
                    + " boolean, boolean, boolean, java.lang.String, boolean):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:98:0x01e0, code lost:

       if (r2 == false) goto L125;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void enqueueBroadcastLocked(com.android.server.am.BroadcastRecord r23) {
        /*
            Method dump skipped, instructions count: 1027
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BroadcastQueueModernImpl.enqueueBroadcastLocked(com.android.server.am.BroadcastRecord):void");
    }

    public final void enqueueDelayedBroadcastLocked(BroadcastRecord broadcastRecord) {
        this.mDelayedBroadcasts.add(broadcastRecord);
        Handler handler = this.mLocalHandler;
        handler.sendMessageDelayed(handler.obtainMessage(9, this), 1000L);
    }

    public final void enqueueUpdateRunningList() {
        Handler handler = this.mLocalHandler;
        handler.removeMessages(1);
        handler.sendEmptyMessage(1);
    }

    public final void finishOrReEnqueueActiveBroadcast(
            BroadcastProcessQueue broadcastProcessQueue) {
        BroadcastQueue.checkState(broadcastProcessQueue.isActive(), "isActive");
        Flags.avoidRepeatedBcastReEnqueues();
        if (broadcastProcessQueue.mActiveReEnqueued) {
            finishReceiverActiveLocked(
                    broadcastProcessQueue, 5, "re-enqueued broadcast delivery failed");
            return;
        }
        BroadcastRecord active = broadcastProcessQueue.getActive();
        int activeIndex = broadcastProcessQueue.getActiveIndex();
        setDeliveryState(
                broadcastProcessQueue,
                broadcastProcessQueue.app,
                active,
                activeIndex,
                active.receivers.get(activeIndex),
                0,
                "reEnqueueActiveBroadcast");
        BroadcastRecord active2 = broadcastProcessQueue.getActive();
        int activeIndex2 = broadcastProcessQueue.getActiveIndex();
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = active2;
        obtain.argi1 = activeIndex2;
        obtain.argi2 = 1;
        broadcastProcessQueue.getQueueForBroadcast(active2).addFirst(obtain);
        broadcastProcessQueue.onBroadcastEnqueued(active2, activeIndex2);
    }

    public final void finishReceiverActiveLocked(
            BroadcastProcessQueue broadcastProcessQueue, int i, String str) {
        String str2;
        int lastIndexOf;
        if (!broadcastProcessQueue.isActive()) {
            BroadcastQueue.logw(
                    "Ignoring finishReceiverActiveLocked; no active broadcast for "
                            + broadcastProcessQueue);
            return;
        }
        int traceBegin = BroadcastQueue.traceBegin("finishReceiver");
        ProcessRecord processRecord = broadcastProcessQueue.app;
        BroadcastRecord active = broadcastProcessQueue.getActive();
        int activeIndex = broadcastProcessQueue.getActiveIndex();
        Object obj = active.receivers.get(activeIndex);
        setDeliveryState(broadcastProcessQueue, processRecord, active, activeIndex, obj, i, str);
        BroadcastAnrTimer broadcastAnrTimer = this.mAnrTimer;
        if (i == 3) {
            active.anrCount++;
            if (processRecord == null || processRecord.mDebugging) {
                broadcastAnrTimer.discard();
            } else {
                broadcastAnrTimer.mFeature.getClass();
                String receiverPackageName = BroadcastRecord.getReceiverPackageName(obj);
                if (obj instanceof BroadcastFilter) {
                    BroadcastFilter broadcastFilter = (BroadcastFilter) obj;
                    String str3 = broadcastFilter.receiverId;
                    str2 =
                            (str3 == null || (lastIndexOf = str3.lastIndexOf(64)) <= 0)
                                    ? null
                                    : broadcastFilter.receiverId.substring(0, lastIndexOf);
                } else {
                    str2 = ((ResolveInfo) obj).activityInfo.name;
                }
                this.mService.mAnrHelper.appNotResponding(
                        broadcastProcessQueue.app,
                        TimeoutRecord.forBroadcastReceiver(active.intent, receiverPackageName, str2)
                                .setExpiredTimer((AutoCloseable) null));
            }
        } else if (broadcastProcessQueue.mTimeoutScheduled) {
            broadcastAnrTimer.cancel(broadcastProcessQueue);
            broadcastAnrTimer.mFeature.getClass();
            this.mLocalHandler.removeMessages(8, broadcastProcessQueue);
        }
        if (!this.mWaitingFor.isEmpty()) {
            this.mWaitingFor.removeIf(new BroadcastQueueModernImpl$$ExternalSyntheticLambda1(0));
        }
        BroadcastQueue.traceEnd(traceBegin);
    }

    @Override // com.android.server.am.BroadcastQueue
    public final boolean finishReceiverLocked(
            ProcessRecord processRecord, int i, String str, Bundle bundle, boolean z) {
        BroadcastProcessQueue processQueue = getProcessQueue(processRecord);
        if (processQueue == null || !processQueue.isActive()) {
            BroadcastQueue.logw(
                    "Ignoring finishReceiverLocked; no active broadcast for " + processQueue);
            return false;
        }
        BroadcastRecord active = processQueue.getActive();
        int activeIndex = processQueue.getActiveIndex();
        if (active.ordered) {
            active.resultCode = i;
            active.resultData = str;
            active.resultExtras = bundle;
            if ((active.intent.getFlags() & 134217728) == 0) {
                active.resultAbort = z;
            }
        }
        finishReceiverActiveLocked(processQueue, 1, "remote app");
        if (active.resultAbort) {
            for (int i2 = activeIndex + 1; i2 < active.receivers.size(); i2++) {
                setDeliveryState(
                        null, null, active, i2, active.receivers.get(i2), 2, "resultAbort");
            }
            this.mService.mExt.getClass();
            BroadcastHistory broadcastHistory = this.mHistory;
            String[] strArr = broadcastHistory.mAbortedBroadcastHistory;
            int i3 = broadcastHistory.mAbortedHistoryNext;
            CharArrayWriter charArrayWriter = new CharArrayWriter();
            active.dump(
                    new PrintWriter(charArrayWriter),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
            strArr[i3] = charArrayWriter.toString();
            broadcastHistory.mAbortedHistoryNext =
                    BroadcastHistory.ringAdvance(
                            broadcastHistory.mAbortedHistoryNext,
                            1,
                            BroadcastHistory.MAX_ABORTED_BROADCAST_HISTORY);
        }
        if (shouldRetire(processQueue)) {
            demoteFromRunningLocked(processQueue);
            return true;
        }
        processQueue.makeActiveNextPending();
        try {
            if (!scheduleReceiverWarmLocked(processQueue)) {
                return false;
            }
            demoteFromRunningLocked(processQueue);
            return true;
        } catch (BroadcastRetryException unused) {
            finishOrReEnqueueActiveBroadcast(processQueue);
            demoteFromRunningLocked(processQueue);
            return true;
        }
    }

    public final boolean forEachMatchingBroadcast(
            Predicate predicate,
            BroadcastProcessQueue.BroadcastPredicate broadcastPredicate,
            BroadcastProcessQueue.BroadcastConsumer broadcastConsumer) {
        boolean z = false;
        for (int size = this.mProcessQueues.size() - 1; size >= 0; size--) {
            for (BroadcastProcessQueue broadcastProcessQueue =
                            (BroadcastProcessQueue) this.mProcessQueues.valueAt(size);
                    broadcastProcessQueue != null;
                    broadcastProcessQueue = broadcastProcessQueue.processNameNext) {
                if (predicate.test(broadcastProcessQueue)
                        && broadcastProcessQueue.forEachMatchingBroadcast(
                                broadcastPredicate, broadcastConsumer, true)) {
                    updateRunnableList(broadcastProcessQueue);
                    z = true;
                }
            }
        }
        if (z) {
            enqueueUpdateRunningList();
        }
        return z;
    }

    public final void forEachMatchingQueue(Predicate predicate, Consumer consumer) {
        boolean z = false;
        for (int size = this.mProcessQueues.size() - 1; size >= 0; size--) {
            for (BroadcastProcessQueue broadcastProcessQueue =
                            (BroadcastProcessQueue) this.mProcessQueues.valueAt(size);
                    broadcastProcessQueue != null;
                    broadcastProcessQueue = broadcastProcessQueue.processNameNext) {
                if (predicate.test(broadcastProcessQueue)) {
                    consumer.accept(broadcastProcessQueue);
                    updateRunnableList(broadcastProcessQueue);
                    z = true;
                }
            }
        }
        if (z) {
            enqueueUpdateRunningList();
        }
    }

    public BroadcastProcessQueue getOrCreateProcessQueue(ProcessRecord processRecord) {
        return getOrCreateProcessQueue(processRecord.processName, processRecord.info.uid);
    }

    public BroadcastProcessQueue getOrCreateProcessQueue(String str, int i) {
        BroadcastProcessQueue broadcastProcessQueue =
                (BroadcastProcessQueue) this.mProcessQueues.get(i);
        while (broadcastProcessQueue != null) {
            if (Objects.equals(broadcastProcessQueue.processName, str)) {
                return broadcastProcessQueue;
            }
            BroadcastProcessQueue broadcastProcessQueue2 = broadcastProcessQueue.processNameNext;
            if (broadcastProcessQueue2 == null) {
                break;
            }
            broadcastProcessQueue = broadcastProcessQueue2;
        }
        BroadcastProcessQueue broadcastProcessQueue3 =
                new BroadcastProcessQueue(this.mConstants, str, i);
        setQueueProcess(
                broadcastProcessQueue3, this.mService.mProcessList.getProcessRecordLocked(i, str));
        if (broadcastProcessQueue == null) {
            this.mProcessQueues.put(i, broadcastProcessQueue3);
        } else {
            broadcastProcessQueue.processNameNext = broadcastProcessQueue3;
        }
        return broadcastProcessQueue3;
    }

    public BroadcastProcessQueue getProcessQueue(ProcessRecord processRecord) {
        return getProcessQueue(processRecord.processName, processRecord.info.uid);
    }

    public BroadcastProcessQueue getProcessQueue(String str, int i) {
        for (BroadcastProcessQueue broadcastProcessQueue =
                        (BroadcastProcessQueue) this.mProcessQueues.get(i);
                broadcastProcessQueue != null;
                broadcastProcessQueue = broadcastProcessQueue.processNameNext) {
            if (Objects.equals(broadcastProcessQueue.processName, str)) {
                return broadcastProcessQueue;
            }
        }
        return null;
    }

    public final int getRunningIndexOf(BroadcastProcessQueue broadcastProcessQueue) {
        int i = 0;
        while (true) {
            BroadcastProcessQueue[] broadcastProcessQueueArr = this.mRunning;
            if (i >= broadcastProcessQueueArr.length) {
                return -1;
            }
            if (broadcastProcessQueueArr[i] == broadcastProcessQueue) {
                return i;
            }
            i++;
        }
    }

    public final int getRunningSize() {
        int i = 0;
        int i2 = 0;
        while (true) {
            BroadcastProcessQueue[] broadcastProcessQueueArr = this.mRunning;
            if (i >= broadcastProcessQueueArr.length) {
                return i2;
            }
            if (broadcastProcessQueueArr[i] != null) {
                i2++;
            }
            i++;
        }
    }

    public final void handleProcessFreezableChanged(ProcessRecord processRecord) {
        ProcessRecord processRecord2;
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                BroadcastProcessQueue processQueue =
                        getProcessQueue(processRecord.processName, processRecord.uid);
                if (processQueue != null
                        && (processRecord2 = processQueue.app) != null
                        && processRecord2.mPid == processRecord.mPid) {
                    if (!isProcessFreezable(processRecord)) {
                        BroadcastQueueModernImpl$$ExternalSyntheticLambda4
                                broadcastQueueModernImpl$$ExternalSyntheticLambda4 =
                                        this.mBroadcastRecordConsumerEnqueue;
                        for (int i = 0; i < processQueue.mOutgoingBroadcasts.size(); i++) {
                            ((BroadcastQueueModernImpl)
                                            broadcastQueueModernImpl$$ExternalSyntheticLambda4.f$0)
                                    .enqueueBroadcastLocked(
                                            (BroadcastRecord)
                                                    processQueue.mOutgoingBroadcasts.get(i));
                        }
                        processQueue.mOutgoingBroadcasts.clear();
                    }
                    setQueueProcess(processQueue, processQueue.app);
                    enqueueUpdateRunningList();
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean isProcessFreezable(ProcessRecord processRecord) {
        boolean z = false;
        if (processRecord == null) {
            return false;
        }
        ActivityManagerProcLock activityManagerProcLock = this.mService.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerProcLock) {
            try {
                ProcessCachedOptimizerRecord processCachedOptimizerRecord =
                        processRecord.mOptRecord;
                if (!processCachedOptimizerRecord.mPendingFreeze) {
                    if (!processCachedOptimizerRecord.mFrozen) {
                        if (processRecord.frozenMARs) {}
                    }
                }
                z = true;
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        return z;
    }

    public final void notifyFinishBroadcast(BroadcastRecord broadcastRecord) {
        ActivityManagerService activityManagerService = this.mService;
        activityManagerService.getClass();
        ProcessRecord processRecord = broadcastRecord.callerApp;
        ApplicationInfo applicationInfo = processRecord != null ? processRecord.info : null;
        String str =
                applicationInfo != null
                        ? applicationInfo.packageName
                        : broadcastRecord.callerPackage;
        if (str != null) {
            activityManagerService
                    .mHandler
                    .obtainMessage(74, broadcastRecord.callingUid, 0, str)
                    .sendToTarget();
        }
        broadcastRecord.finishTime = SystemClock.uptimeMillis();
        broadcastRecord.nextReceiver = broadcastRecord.receivers.size();
        BroadcastHistory broadcastHistory = this.mHistory;
        broadcastHistory.mPendingBroadcasts.remove(broadcastRecord);
        BroadcastRecord broadcastRecord2 =
                !broadcastRecord.intent.canStripForHistory()
                        ? broadcastRecord
                        : new BroadcastRecord(
                                broadcastRecord, broadcastRecord.intent.maybeStripForHistory());
        int i = broadcastHistory.mHistoryNext;
        broadcastHistory.mBroadcastHistory[i] = broadcastRecord2;
        broadcastHistory.mHistoryNext =
                BroadcastHistory.ringAdvance(i, 1, broadcastHistory.MAX_BROADCAST_HISTORY);
        broadcastHistory.mService.mExt.getClass();
        broadcastHistory.mBroadcastSummaryHistoryToString[broadcastHistory.mSummaryHistoryNext] =
                broadcastRecord2.intent.toShortString(true, true, true, false);
        if ("android.intent.action.BOOT_COMPLETED".equals(broadcastRecord2.intent.getAction())
                && broadcastHistory.mBCBrHistoryRef == null) {
            CharArrayWriter charArrayWriter = new CharArrayWriter();
            broadcastRecord2.dump(
                    new PrintWriter(charArrayWriter),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
            broadcastHistory.mBCBrHistoryRef = new SoftReference(charArrayWriter.toString());
        }
        int i2 = broadcastHistory.mSummaryHistoryNext;
        broadcastHistory.mSummaryHistoryEnqueueTime[i2] = broadcastRecord2.enqueueClockTime;
        broadcastHistory.mSummaryHistoryDispatchTime[i2] = broadcastRecord2.dispatchClockTime;
        broadcastHistory.mSummaryHistoryFinishTime[i2] = System.currentTimeMillis();
        broadcastHistory.mSummaryHistoryNext =
                BroadcastHistory.ringAdvance(
                        broadcastHistory.mSummaryHistoryNext,
                        1,
                        broadcastHistory.MAX_BROADCAST_SUMMARY_HISTORY);
        List list = broadcastRecord.receivers;
        int size = list != null ? list.size() : 0;
        if (broadcastRecord.nextReceiver >= size) {
            String action = broadcastRecord.intent.getAction();
            int i3 =
                    "android.intent.action.LOCKED_BOOT_COMPLETED".equals(action)
                            ? 1
                            : "android.intent.action.BOOT_COMPLETED".equals(action) ? 2 : 0;
            if (i3 != 0) {
                int i4 = (int) (broadcastRecord.dispatchTime - broadcastRecord.enqueueTime);
                int uptimeMillis = (int) (SystemClock.uptimeMillis() - broadcastRecord.enqueueTime);
                int i5 = (int) (broadcastRecord.dispatchRealTime - broadcastRecord.enqueueRealTime);
                int elapsedRealtime =
                        (int) (SystemClock.elapsedRealtime() - broadcastRecord.enqueueRealTime);
                UserManagerInternal userManagerInternal =
                        (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
                UserInfo userInfo =
                        userManagerInternal != null
                                ? userManagerInternal.getUserInfo(broadcastRecord.userId)
                                : null;
                int userTypeForStatsd =
                        userInfo != null
                                ? UserJourneyLogger.getUserTypeForStatsd(userInfo.userType)
                                : 0;
                StringBuilder m =
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                i4,
                                "BOOT_COMPLETED_BROADCAST_COMPLETION_LATENCY_REPORTED action:",
                                action,
                                " dispatchLatency:",
                                " completeLatency:");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        uptimeMillis, i5, " dispatchRealLatency:", " completeRealLatency:", m);
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        elapsedRealtime, size, " receiversSize:", " userId:", m);
                m.append(broadcastRecord.userId);
                m.append(" userType:");
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        m, userInfo != null ? userInfo.userType : null, "BroadcastQueue");
                FrameworkStatsLog.write(
                        FrameworkStatsLog.BOOT_COMPLETED_BROADCAST_COMPLETION_LATENCY_REPORTED,
                        i3,
                        i4,
                        uptimeMillis,
                        i5,
                        elapsedRealtime,
                        broadcastRecord.userId,
                        userTypeForStatsd);
            }
        }
        if (broadcastRecord.mCounted) {
            broadcastRecord.mCounted = false;
            ActivityManagerServiceExt activityManagerServiceExt = this.mService.mExt;
            String str2 = broadcastRecord.callerPackage;
            Intent intent = broadcastRecord.intent;
            activityManagerServiceExt.getClass();
            if (str2 == null) {
                str2 = "android";
            }
            ArrayMap arrayMap = (ArrayMap) activityManagerServiceExt.mBrMap.get(str2);
            if (arrayMap != null) {
                String action2 = intent.getAction();
                if (TextUtils.isEmpty(action2)) {
                    action2 = "EMPTY_ACTION";
                }
                ActivityManagerServiceExt.BrCountInfo brCountInfo =
                        (ActivityManagerServiceExt.BrCountInfo) arrayMap.get(action2);
                if (brCountInfo != null) {
                    int i6 = brCountInfo.mMaxCnt;
                    int i7 = brCountInfo.mCnt;
                    if (i6 < i7) {
                        brCountInfo.mMaxCnt = i7;
                    }
                    int i8 = i7 - 1;
                    brCountInfo.mCnt = i8;
                    if (i8 < 0) {
                        brCountInfo.mCnt = 0;
                    }
                    arrayMap.put(action2, brCountInfo);
                }
            }
        }
        if (broadcastRecord.intent.getComponent() == null
                && broadcastRecord.intent.getPackage() == null
                && (broadcastRecord.intent.getFlags() & 1073741824) == 0) {
            int i9 = 0;
            int i10 = 0;
            for (int i11 = 0; i11 < broadcastRecord.receivers.size(); i11++) {
                if (broadcastRecord.receivers.get(i11) instanceof ResolveInfo) {
                    i9++;
                    if (broadcastRecord.delivery[i11] == 2) {
                        i10++;
                    }
                }
            }
            this.mService.addBroadcastStatLocked(
                    i9,
                    i10,
                    broadcastRecord.intent.getAction(),
                    broadcastRecord.callerPackage,
                    SystemClock.uptimeMillis() - broadcastRecord.enqueueTime);
        }
    }

    public final void notifyScheduleReceiver(
            ProcessRecord processRecord, BroadcastRecord broadcastRecord, ResolveInfo resolveInfo) {
        reportUsageStatsBroadcastDispatched(processRecord, broadcastRecord);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        String str = activityInfo.packageName;
        processRecord.addPackage(
                str, activityInfo.applicationInfo.longVersionCode, this.mService.mProcessStats);
        boolean z = broadcastRecord.intent.getComponent() != null;
        boolean equals = Objects.equals(broadcastRecord.callerPackage, str);
        if (z && !equals) {
            if (broadcastRecord.userId == -1) {
                Slog.w(
                        "BroadcastQueue",
                        "If sending to USER ALL, that is not explicit broadcast ["
                                + broadcastRecord
                                + "]");
            } else {
                this.mService.mUsageStatsService.reportEvent(broadcastRecord.userId, 31, str);
            }
        }
        this.mService.notifyPackageUse(str, 3);
        this.mService.mPackageManagerInt.notifyComponentUsed(
                broadcastRecord.userId,
                str,
                broadcastRecord.callerPackage,
                broadcastRecord.toString());
    }

    public final void notifyStartedRunning(BroadcastProcessQueue broadcastProcessQueue) {
        ProcessRecord processRecord = broadcastProcessQueue.app;
        if (processRecord != null) {
            processRecord.mReceivers.mCurReceiversSize++;
            ActivityManagerService activityManagerService = this.mService;
            if (activityManagerService.mInternal.getRestrictionLevel(broadcastProcessQueue.uid)
                    < 40) {
                activityManagerService.updateLruProcessLocked(
                        broadcastProcessQueue.app, null, false);
            }
            activityManagerService.mOomAdjuster.unfreezeTemporarily(3, broadcastProcessQueue.app);
            if (broadcastProcessQueue.runningOomAdjusted) {
                broadcastProcessQueue.app.mState.forceProcessStateUpTo(11);
                activityManagerService.enqueueOomAdjTargetLocked(broadcastProcessQueue.app);
            }
        }
    }

    public final boolean onApplicationAttachedLocked(ProcessRecord processRecord) {
        BroadcastProcessQueue processQueue = getProcessQueue(processRecord);
        if (processQueue != null) {
            setQueueProcess(processQueue, processRecord);
            processQueue.mOutgoingBroadcasts.clear();
        }
        BroadcastProcessQueue broadcastProcessQueue = this.mRunningColdStart;
        if (broadcastProcessQueue == null || broadcastProcessQueue != processQueue) {
            return false;
        }
        this.mRunningColdStart = null;
        notifyStartedRunning(processQueue);
        this.mService.updateOomAdjPendingTargetsLocked(3);
        Trace.asyncTraceForTrackEnd(
                64L, processQueue.runningTraceTrackName, processQueue.hashCode());
        processQueue.traceProcessRunningBegin();
        try {
            if (scheduleReceiverWarmLocked(processQueue)) {
                demoteFromRunningLocked(processQueue);
            }
            enqueueUpdateRunningList();
            return true;
        } catch (BroadcastRetryException e) {
            finishOrReEnqueueActiveBroadcast(processQueue);
            demoteFromRunningLocked(processQueue);
            throw e;
        }
    }

    public final void onApplicationCleanupLocked(ProcessRecord processRecord) {
        BroadcastProcessQueue processQueue = getProcessQueue(processRecord);
        BroadcastProcessQueue broadcastProcessQueue = this.mRunningColdStart;
        if (broadcastProcessQueue != null
                && broadcastProcessQueue == processQueue
                && broadcastProcessQueue.app == processRecord) {
            Trace.asyncTraceForTrackEnd(
                    64L,
                    broadcastProcessQueue.runningTraceTrackName,
                    broadcastProcessQueue.hashCode());
            this.mRunningColdStart = null;
            enqueueUpdateRunningList();
        }
        if (processQueue == null || processQueue.app != processRecord) {
            return;
        }
        setQueueProcess(processQueue, null);
        if (processQueue.isActive()) {
            finishReceiverActiveLocked(processQueue, 5, "onApplicationCleanupLocked");
            demoteFromRunningLocked(processQueue);
        }
        processQueue.mOutgoingBroadcasts.clear();
        if (processQueue.forEachMatchingBroadcast(
                        new BroadcastQueueModernImpl$$ExternalSyntheticLambda7(1),
                        this.mBroadcastConsumerSkip,
                        true)
                || processQueue.isEmpty()) {
            updateRunnableList(processQueue);
            enqueueUpdateRunningList();
        }
    }

    public BroadcastProcessQueue removeProcessQueue(ProcessRecord processRecord) {
        return removeProcessQueue(processRecord.processName, processRecord.info.uid);
    }

    public BroadcastProcessQueue removeProcessQueue(String str, int i) {
        BroadcastProcessQueue broadcastProcessQueue = null;
        for (BroadcastProcessQueue broadcastProcessQueue2 =
                        (BroadcastProcessQueue) this.mProcessQueues.get(i);
                broadcastProcessQueue2 != null;
                broadcastProcessQueue2 = broadcastProcessQueue2.processNameNext) {
            if (Objects.equals(broadcastProcessQueue2.processName, str)) {
                if (broadcastProcessQueue != null) {
                    broadcastProcessQueue.processNameNext = broadcastProcessQueue2.processNameNext;
                } else {
                    BroadcastProcessQueue broadcastProcessQueue3 =
                            broadcastProcessQueue2.processNameNext;
                    if (broadcastProcessQueue3 != null) {
                        this.mProcessQueues.put(i, broadcastProcessQueue3);
                    } else {
                        this.mProcessQueues.remove(i);
                    }
                }
                return broadcastProcessQueue2;
            }
            broadcastProcessQueue = broadcastProcessQueue2;
        }
        return null;
    }

    public final void reportUsageStatsBroadcastDispatched(
            ProcessRecord processRecord, BroadcastRecord broadcastRecord) {
        BroadcastResponseStatsTracker broadcastResponseStatsTracker;
        Object obj;
        BroadcastOptions broadcastOptions = broadcastRecord.options;
        long idForResponseEvent =
                broadcastOptions != null ? broadcastOptions.getIdForResponseEvent() : 0L;
        if (idForResponseEvent <= 0) {
            return;
        }
        String str =
                broadcastRecord.intent.getPackage() != null
                        ? broadcastRecord.intent.getPackage()
                        : broadcastRecord.intent.getComponent() != null
                                ? broadcastRecord.intent.getComponent().getPackageName()
                                : null;
        if (str == null) {
            return;
        }
        UsageStatsManagerInternal usageStatsManagerInternal = this.mService.mUsageStatsService;
        int i = broadcastRecord.callingUid;
        UserHandle of = UserHandle.of(broadcastRecord.userId);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int uidProcStateLOSP = this.mService.mProcessList.getUidProcStateLOSP(processRecord.uid);
        BroadcastResponseStatsTracker broadcastResponseStatsTracker2 =
                UsageStatsService.this.mResponseStatsTracker;
        BroadcastResponseStatsLogger broadcastResponseStatsLogger =
                broadcastResponseStatsTracker2.mLogger;
        Object obj2 = broadcastResponseStatsLogger.mLock;
        synchronized (obj2) {
            try {
            } catch (Throwable th) {
                th = th;
            }
            try {
                if (UsageStatsService.DEBUG_RESPONSE_STATS) {
                    broadcastResponseStatsTracker = broadcastResponseStatsTracker2;
                    obj = obj2;
                    Slog.d(
                            "ResponseStatsTracker",
                            TextUtils.formatSimple(
                                    "broadcast:%s; srcUid=%d, tgtPkg=%s, tgtUsr=%d, id=%d,"
                                        + " state=%s",
                                    new Object[] {
                                        TimeUtils.formatDuration(elapsedRealtime),
                                        Integer.valueOf(i),
                                        str,
                                        Integer.valueOf(of.getIdentifier()),
                                        Long.valueOf(idForResponseEvent),
                                        ActivityManager.procStateToString(uidProcStateLOSP)
                                    }));
                } else {
                    broadcastResponseStatsTracker = broadcastResponseStatsTracker2;
                    obj = obj2;
                }
                BroadcastResponseStatsLogger.Data data =
                        (BroadcastResponseStatsLogger.Data)
                                broadcastResponseStatsLogger.mBroadcastEventsBuffer.getNextSlot();
                if (data != null) {
                    data.reset();
                    BroadcastResponseStatsLogger.BroadcastEvent broadcastEvent =
                            (BroadcastResponseStatsLogger.BroadcastEvent) data;
                    broadcastEvent.sourceUid = i;
                    broadcastEvent.targetUserId = of.getIdentifier();
                    broadcastEvent.targetUidProcessState = uidProcStateLOSP;
                    broadcastEvent.targetPackage = str;
                    broadcastEvent.idForResponseEvent = idForResponseEvent;
                    broadcastEvent.timestampMs = elapsedRealtime;
                }
                BroadcastResponseStatsTracker broadcastResponseStatsTracker3 =
                        broadcastResponseStatsTracker;
                if (uidProcStateLOSP
                                <= broadcastResponseStatsTracker3.mAppStandby
                                        .getBroadcastResponseFgThresholdState()
                        || broadcastResponseStatsTracker3.doesPackageHoldExemptedRole(str, of)
                        || broadcastResponseStatsTracker3.doesPackageHoldExemptedPermission(
                                str, of)) {
                    return;
                }
                synchronized (broadcastResponseStatsTracker3.mLock) {
                    ArraySet orCreateBroadcastEventsLocked =
                            broadcastResponseStatsTracker3.getOrCreateBroadcastEventsLocked(
                                    str, of);
                    BroadcastEvent broadcastEvent2 =
                            new BroadcastEvent(i, of.getIdentifier(), idForResponseEvent, str);
                    int indexOf = orCreateBroadcastEventsLocked.indexOf(broadcastEvent2);
                    if (indexOf >= 0) {
                        broadcastEvent2 =
                                (BroadcastEvent) orCreateBroadcastEventsLocked.valueAt(indexOf);
                    } else {
                        orCreateBroadcastEventsLocked.add(broadcastEvent2);
                    }
                    broadcastEvent2.mTimestampsMs.addLast(elapsedRealtime);
                    broadcastResponseStatsTracker3.recordAndPruneOldBroadcastDispatchTimestamps(
                            broadcastEvent2);
                }
                return;
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
        throw th;
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0088, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ec, code lost:

       throw r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0372, code lost:

       com.android.server.am.BroadcastQueue.traceEnd(r29);
    */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0376, code lost:

       return true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean scheduleReceiverWarmLocked(
            com.android.server.am.BroadcastProcessQueue r33) {
        /*
            Method dump skipped, instructions count: 887
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BroadcastQueueModernImpl.scheduleReceiverWarmLocked(com.android.server.am.BroadcastProcessQueue):boolean");
    }

    public final void scheduleResultTo(BroadcastRecord broadcastRecord) {
        int i;
        if (broadcastRecord.resultTo == null) {
            return;
        }
        ProcessRecord processRecord = broadcastRecord.resultToApp;
        IApplicationThread iApplicationThread =
                processRecord != null ? processRecord.mOnewayThread : null;
        if (iApplicationThread != null) {
            ActivityManagerService activityManagerService = this.mService;
            activityManagerService.mOomAdjuster.unfreezeTemporarily(2, processRecord);
            if (broadcastRecord.shareIdentity
                    && (i = processRecord.uid) != broadcastRecord.callingUid) {
                ((PackageManagerService.PackageManagerInternalImpl)
                                activityManagerService.mPackageManagerInt)
                        .grantImplicitAccess(
                                broadcastRecord.userId,
                                broadcastRecord.intent,
                                UserHandle.getAppId(i),
                                broadcastRecord.callingUid,
                                true,
                                false);
            }
            try {
                IIntentReceiver iIntentReceiver = broadcastRecord.resultTo;
                Intent intent = broadcastRecord.intent;
                int i2 = broadcastRecord.resultCode;
                String str = broadcastRecord.resultData;
                Bundle bundle = broadcastRecord.resultExtras;
                boolean z = broadcastRecord.initialSticky;
                int i3 = broadcastRecord.userId;
                int i4 = processRecord.mState.mRepProcState;
                boolean z2 = broadcastRecord.shareIdentity;
                iApplicationThread.scheduleRegisteredReceiver(
                        iIntentReceiver,
                        intent,
                        i2,
                        str,
                        bundle,
                        false,
                        z,
                        true,
                        i3,
                        i4,
                        z2 ? broadcastRecord.callingUid : -1,
                        z2 ? broadcastRecord.callerPackage : null);
            } catch (RemoteException e) {
                BroadcastQueue.logw(
                        "Failed to schedule result of "
                                + broadcastRecord
                                + " via "
                                + processRecord
                                + ": "
                                + e);
                processRecord.killLocked(
                        13, 26, "Can't deliver broadcast", "Can't deliver broadcast", true, true);
            }
        }
        broadcastRecord.resultTo = null;
        broadcastRecord.hadResultTo = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0140, code lost:

       if (r5 != 5) goto L58;
    */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0033, code lost:

       if (r48 != 5) goto L16;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setDeliveryState(
            com.android.server.am.BroadcastProcessQueue r43,
            com.android.server.am.ProcessRecord r44,
            com.android.server.am.BroadcastRecord r45,
            int r46,
            java.lang.Object r47,
            int r48,
            java.lang.String r49) {
        /*
            Method dump skipped, instructions count: 666
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BroadcastQueueModernImpl.setDeliveryState(com.android.server.am.BroadcastProcessQueue,"
                    + " com.android.server.am.ProcessRecord, com.android.server.am.BroadcastRecord,"
                    + " int, java.lang.Object, int, java.lang.String):void");
    }

    public final void setQueueProcess(
            BroadcastProcessQueue broadcastProcessQueue, ProcessRecord processRecord) {
        if (broadcastProcessQueue.setProcessAndUidState(
                processRecord,
                this.mUidForeground.get(broadcastProcessQueue.uid, false),
                isProcessFreezable(processRecord))) {
            updateRunnableList(broadcastProcessQueue);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0022, code lost:

       if (r6.mActiveCountSinceIdle >= r5.MAX_RUNNING_ACTIVE_BROADCASTS) goto L10;
    */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0017, code lost:

       if (r0 < r5.MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS) goto L9;
    */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001a, code lost:

       r5 = false;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldRetire(com.android.server.am.BroadcastProcessQueue r6) {
        /*
            r5 = this;
            int r0 = r6.uid
            boolean r0 = android.os.UserHandle.isCore(r0)
            com.android.server.am.BroadcastConstants r5 = r5.mConstants
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L1e
            int r0 = r6.mActiveAssumedDeliveryCountSinceIdle
            int r3 = r6.mActiveCountSinceIdle
            int r3 = r3 - r0
            int r4 = r5.MAX_CORE_RUNNING_BLOCKING_BROADCASTS
            if (r3 >= r4) goto L1c
            int r5 = r5.MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS
            if (r0 < r5) goto L1a
            goto L1c
        L1a:
            r5 = r2
            goto L25
        L1c:
            r5 = r1
            goto L25
        L1e:
            int r0 = r6.mActiveCountSinceIdle
            int r5 = r5.MAX_RUNNING_ACTIVE_BROADCASTS
            if (r0 < r5) goto L1a
            goto L1c
        L25:
            boolean r0 = r6.isRunnable()
            if (r0 == 0) goto L35
            boolean r6 = r6.isProcessWarm()
            if (r6 == 0) goto L35
            if (r5 == 0) goto L34
            goto L35
        L34:
            r1 = r2
        L35:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BroadcastQueueModernImpl.shouldRetire(com.android.server.am.BroadcastProcessQueue):boolean");
    }

    public final String shouldSkipReceiver(
            BroadcastProcessQueue broadcastProcessQueue, BroadcastRecord broadcastRecord, int i) {
        int i2;
        HostingRecord hostingRecord;
        String str;
        int i3;
        int i4;
        HostingRecord hostingRecord2;
        String str2;
        int i5;
        int i6 = broadcastRecord.delivery[i];
        ProcessRecord processRecord = broadcastProcessQueue.app;
        Object obj = broadcastRecord.receivers.get(i);
        if (BroadcastRecord.isDeliveryStateTerminal(i6)) {
            return "already terminal state";
        }
        if (processRecord != null && processRecord.mInFullBackup) {
            return "isInFullBackup";
        }
        String shouldSkipMessage = this.mSkipPolicy.shouldSkipMessage(broadcastRecord, obj);
        if (shouldSkipMessage != null) {
            return shouldSkipMessage;
        }
        if (broadcastRecord.getReceiverIntent(obj) == null) {
            return "getReceiverIntent";
        }
        boolean z = obj instanceof BroadcastFilter;
        if (z && ((BroadcastFilter) obj).receiverList.pid != processRecord.mPid) {
            return "BroadcastFilter for mismatched PID";
        }
        if (MARsPolicyManager.MARs_ENABLE) {
            if (z) {
                int userId = this.mService.mContext.getUserId();
                ProcessRecord processRecord2 = broadcastRecord.callerApp;
                if (processRecord2 == null || processRecord2.info == null) {
                    i4 = userId;
                    hostingRecord2 = null;
                    str2 = null;
                    i5 = 0;
                } else {
                    String str3 = broadcastRecord.callerApp.info.packageName;
                    ProcessRecord processRecord3 = broadcastRecord.callerApp;
                    int i7 = processRecord3.userId;
                    int i8 = processRecord3.mPid;
                    hostingRecord2 = processRecord3.mHostingRecord;
                    str2 = str3;
                    i4 = i7;
                    i5 = i8;
                }
                BroadcastFilter broadcastFilter = (BroadcastFilter) obj;
                if (broadcastFilter.packageName != null) {
                    String stringForTracker =
                            hostingRecord2 != null ? hostingRecord2.toStringForTracker() : null;
                    String str4 = broadcastRecord.alarm ? "alarm" : INetd.IF_FLAG_BROADCAST;
                    BaseRestrictionMgr baseRestrictionMgr =
                            BaseRestrictionMgr.BaseRestrictionMgrHolder.INSTANCE;
                    ComponentName componentName =
                            new ComponentName(broadcastFilter.packageName, "");
                    Intent intent = broadcastRecord.intent;
                    int i9 = broadcastFilter.owningUserId;
                    broadcastRecord.mBackgroundStartPrivileges.allowsAny();
                    if (baseRestrictionMgr.isRestrictedPackage(
                            componentName,
                            str2,
                            i4,
                            str4,
                            intent,
                            i9,
                            true,
                            null,
                            stringForTracker,
                            i5,
                            processRecord.mPid)) {
                        Slog.w(
                                "BroadcastQueue",
                                "intent:"
                                        + broadcastRecord.intent.toString()
                                        + " is skipped in RestrictedPackage to "
                                        + broadcastFilter.receiverList.app);
                        return "To dynamic Broadcast receiver from Restricted UID";
                    }
                }
            } else {
                ResolveInfo resolveInfo = (ResolveInfo) obj;
                int userId2 = this.mService.mContext.getUserId();
                ProcessRecord processRecord4 = broadcastRecord.callerApp;
                if (processRecord4 == null || processRecord4.info == null) {
                    i2 = userId2;
                    hostingRecord = null;
                    str = null;
                    i3 = 0;
                } else {
                    String str5 = broadcastRecord.callerApp.info.packageName;
                    ProcessRecord processRecord5 = broadcastRecord.callerApp;
                    int i10 = processRecord5.userId;
                    int i11 = processRecord5.mPid;
                    hostingRecord = processRecord5.mHostingRecord;
                    str = str5;
                    i2 = i10;
                    i3 = i11;
                }
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                ProcessRecord processRecordLocked =
                        this.mService.mProcessList.getProcessRecordLocked(
                                activityInfo.applicationInfo.uid, activityInfo.processName);
                int i12 = processRecordLocked != null ? processRecordLocked.mPid : 0;
                String stringForTracker2 =
                        hostingRecord != null ? hostingRecord.toStringForTracker() : null;
                ActivityInfo activityInfo2 = resolveInfo.activityInfo;
                ComponentName componentName2 =
                        new ComponentName(
                                activityInfo2.applicationInfo.packageName, activityInfo2.name);
                int i13 = resolveInfo.activityInfo.applicationInfo.uid;
                BaseRestrictionMgr baseRestrictionMgr2 =
                        BaseRestrictionMgr.BaseRestrictionMgrHolder.INSTANCE;
                Intent intent2 = broadcastRecord.intent;
                int userId3 = UserHandle.getUserId(i13);
                broadcastRecord.mBackgroundStartPrivileges.allowsAny();
                if (baseRestrictionMgr2.isRestrictedPackage(
                        componentName2,
                        str,
                        i2,
                        INetd.IF_FLAG_BROADCAST,
                        intent2,
                        userId3,
                        false,
                        null,
                        stringForTracker2,
                        i3,
                        i12)) {
                    Slog.w(
                            "BroadcastQueue",
                            "intent:"
                                    + broadcastRecord.intent.toString()
                                    + " is skipped in RestrictedPackage to "
                                    + componentName2.flattenToShortString());
                    return "To manifest broadcast receiver from Restricted UID";
                }
            }
        }
        return null;
    }

    public final boolean testAllProcessQueues(
            Predicate predicate, String str, PrintWriter printWriter) {
        for (int i = 0; i < this.mProcessQueues.size(); i++) {
            for (BroadcastProcessQueue broadcastProcessQueue =
                            (BroadcastProcessQueue) this.mProcessQueues.valueAt(i);
                    broadcastProcessQueue != null;
                    broadcastProcessQueue = broadcastProcessQueue.processNameNext) {
                if (!predicate.test(broadcastProcessQueue)) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    if (uptimeMillis > this.mLastTestFailureTime + 1000) {
                        this.mLastTestFailureTime = uptimeMillis;
                        StringBuilder m =
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Test ", str, " failed due to ");
                        m.append(broadcastProcessQueue.toShortString());
                        m.append(" ");
                        m.append(
                                broadcastProcessQueue.describeStateLocked(
                                        SystemClock.uptimeMillis()));
                        printWriter.println(m.toString());
                        printWriter.flush();
                    }
                    return false;
                }
            }
        }
        printWriter.println("Test " + str + " passed");
        printWriter.flush();
        return true;
    }

    public final void updateRunnableList(BroadcastProcessQueue broadcastProcessQueue) {
        if (getRunningIndexOf(broadcastProcessQueue) >= 0) {
            return;
        }
        BroadcastProcessQueue.BroadcastConsumer broadcastConsumer =
                this.mBroadcastConsumerDeferApply;
        BroadcastProcessQueue.BroadcastConsumer broadcastConsumer2 =
                this.mBroadcastConsumerDeferClear;
        boolean shouldBeDeferred = broadcastProcessQueue.shouldBeDeferred();
        if (broadcastProcessQueue.mLastDeferredStates != shouldBeDeferred) {
            broadcastProcessQueue.mLastDeferredStates = shouldBeDeferred;
            if (shouldBeDeferred) {
                broadcastProcessQueue.forEachMatchingBroadcast(
                        new BroadcastProcessQueue$$ExternalSyntheticLambda0(0),
                        broadcastConsumer,
                        false);
            } else {
                broadcastProcessQueue.forEachMatchingBroadcast(
                        new BroadcastProcessQueue$$ExternalSyntheticLambda0(1),
                        broadcastConsumer2,
                        false);
            }
        }
        broadcastProcessQueue.updateRunnableAt();
        boolean isRunnable = broadcastProcessQueue.isRunnable();
        BroadcastProcessQueue broadcastProcessQueue2 = this.mRunnableHead;
        boolean z =
                (broadcastProcessQueue != broadcastProcessQueue2
                                && broadcastProcessQueue.runnableAtPrev == null
                                && broadcastProcessQueue.runnableAtNext == null)
                        ? false
                        : true;
        if (isRunnable) {
            if (z) {
                BroadcastProcessQueue broadcastProcessQueue3 = broadcastProcessQueue.runnableAtPrev;
                boolean z2 =
                        broadcastProcessQueue3 == null
                                || broadcastProcessQueue3.getRunnableAt()
                                        <= broadcastProcessQueue.getRunnableAt();
                BroadcastProcessQueue broadcastProcessQueue4 = broadcastProcessQueue.runnableAtNext;
                boolean z3 =
                        broadcastProcessQueue4 == null
                                || broadcastProcessQueue4.getRunnableAt()
                                        >= broadcastProcessQueue.getRunnableAt();
                if (!z2 || !z3) {
                    BroadcastProcessQueue removeFromRunnableList =
                            BroadcastProcessQueue.removeFromRunnableList(
                                    this.mRunnableHead, broadcastProcessQueue);
                    this.mRunnableHead = removeFromRunnableList;
                    this.mRunnableHead =
                            BroadcastProcessQueue.insertIntoRunnableList(
                                    removeFromRunnableList, broadcastProcessQueue);
                }
            } else {
                this.mRunnableHead =
                        BroadcastProcessQueue.insertIntoRunnableList(
                                broadcastProcessQueue2, broadcastProcessQueue);
            }
        } else if (z) {
            this.mRunnableHead =
                    BroadcastProcessQueue.removeFromRunnableList(
                            broadcastProcessQueue2, broadcastProcessQueue);
        }
        if (!broadcastProcessQueue.isEmpty()
                || !broadcastProcessQueue.mOutgoingBroadcasts.isEmpty()
                || broadcastProcessQueue.isActive()
                || broadcastProcessQueue.isProcessWarm()) {
            return;
        }
        removeProcessQueue(broadcastProcessQueue.processName, broadcastProcessQueue.uid);
    }

    public final void updateRunningList() {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                updateRunningListLocked();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateRunningListLocked() {
        /*
            Method dump skipped, instructions count: 727
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BroadcastQueueModernImpl.updateRunningListLocked():void");
    }

    public final void waitFor(BooleanSupplier booleanSupplier) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mWaitingFor.add(Pair.create(booleanSupplier, countDownLatch));
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        enqueueUpdateRunningList();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
