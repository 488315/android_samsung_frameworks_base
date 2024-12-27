package com.android.server.am;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import android.util.TimeUtils;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.NeverCompile;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BroadcastProcessQueue {
    public ProcessRecord app;
    public final BroadcastConstants constants;
    public long lastCpuDelayTime;
    public int lastProcessState;
    public BroadcastRecord mActive;
    public int mActiveAssumedDeliveryCountSinceIdle;
    public int mActiveCountConsecutiveNormal;
    public int mActiveCountConsecutiveUrgent;
    public int mActiveCountSinceIdle;
    public boolean mActiveFirstLaunch;
    public int mActiveIndex;
    public boolean mActiveReEnqueued;
    public boolean mActiveViaColdStart;
    public boolean mActiveWasStopped;
    public String mCachedToShortString;
    public String mCachedToString;
    public int mCountAlarm;
    public int mCountDeferred;
    public int mCountEnqueued;
    public int mCountForeground;
    public int mCountForegroundDeferred;
    public int mCountInstrumented;
    public int mCountInteractive;
    public int mCountManifest;
    public int mCountOrdered;
    public int mCountPrioritizeEarliestRequests;
    public int mCountPrioritized;
    public int mCountPrioritizedDeferred;
    public int mCountResultTo;
    public long mForcedDelayedDurationMs;
    public boolean mLastDeferredStates;
    public boolean mProcessFreezable;
    public boolean mProcessInstrumented;
    public boolean mProcessPersistent;
    public boolean mRunnableAtInvalidated;
    public boolean mTimeoutScheduled;
    public boolean mUidForeground;
    public final String processName;
    public BroadcastProcessQueue processNameNext;
    public BroadcastProcessQueue runnableAtNext;
    public BroadcastProcessQueue runnableAtPrev;
    public boolean runningOomAdjusted;
    public String runningTraceTrackName;
    public final int uid;
    public final ArrayDeque mPending = new ArrayDeque();
    public final ArrayDeque mPendingUrgent = new ArrayDeque(4);
    public final ArrayDeque mPendingOffload = new ArrayDeque(4);
    public long mRunnableAt = Long.MAX_VALUE;
    public int mRunnableAtReason = 0;
    public final ArrayList mOutgoingBroadcasts = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface BroadcastConsumer {
        void accept(BroadcastRecord broadcastRecord, int i);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface BroadcastPredicate {
        boolean test(BroadcastRecord broadcastRecord, int i);
    }

    public BroadcastProcessQueue(BroadcastConstants broadcastConstants, String str, int i) {
        Objects.requireNonNull(broadcastConstants);
        this.constants = broadcastConstants;
        Objects.requireNonNull(str);
        this.processName = str;
        this.uid = i;
    }

    public static void assertHealthLocked(ArrayDeque arrayDeque) {
        if (arrayDeque.isEmpty()) {
            return;
        }
        Iterator descendingIterator = arrayDeque.descendingIterator();
        while (descendingIterator.hasNext()) {
            SomeArgs someArgs = (SomeArgs) descendingIterator.next();
            BroadcastRecord broadcastRecord = (BroadcastRecord) someArgs.arg1;
            int i = someArgs.argi1;
            if (!BroadcastRecord.isDeliveryStateTerminal(broadcastRecord.delivery[i]) && !broadcastRecord.deferUntilActive && !((Boolean) broadcastRecord.mMARsTargetReceiver.get(i)).booleanValue()) {
                Preconditions.checkState(SystemClock.uptimeMillis() - broadcastRecord.enqueueTime < 600000, "waitingTime");
            }
        }
    }

    @NeverCompile
    public static void dumpRecord(String str, long j, IndentingPrintWriter indentingPrintWriter, BroadcastRecord broadcastRecord, int i) {
        TimeUtils.formatDuration(broadcastRecord.enqueueTime, j, indentingPrintWriter);
        indentingPrintWriter.print(' ');
        indentingPrintWriter.println(broadcastRecord.toShortString());
        indentingPrintWriter.print("    ");
        int i2 = broadcastRecord.delivery[i];
        indentingPrintWriter.print(BroadcastRecord.deliveryStateToString(i2));
        if (i2 == 4) {
            indentingPrintWriter.print(" at ");
            TimeUtils.formatDuration(broadcastRecord.scheduledTime[i], j, indentingPrintWriter);
        }
        if (str != null) {
            indentingPrintWriter.print(' ');
            indentingPrintWriter.print(str);
        }
        Object obj = broadcastRecord.receivers.get(i);
        if (obj instanceof BroadcastFilter) {
            indentingPrintWriter.print(" for registered ");
            indentingPrintWriter.print(Integer.toHexString(System.identityHashCode((BroadcastFilter) obj)));
        } else {
            indentingPrintWriter.print(" for manifest ");
            indentingPrintWriter.print(((ResolveInfo) obj).activityInfo.name);
        }
        indentingPrintWriter.println();
        int i3 = broadcastRecord.blockedUntilBeyondCount[i];
        if (i3 != -1) {
            indentingPrintWriter.print("    blocked until ");
            indentingPrintWriter.print(i3);
            indentingPrintWriter.print(", currently at ");
            indentingPrintWriter.print(broadcastRecord.beyondCount);
            indentingPrintWriter.print(" of ");
            indentingPrintWriter.println(broadcastRecord.receivers.size());
        }
    }

    public static BroadcastProcessQueue insertIntoRunnableList(BroadcastProcessQueue broadcastProcessQueue, BroadcastProcessQueue broadcastProcessQueue2) {
        if (broadcastProcessQueue == null) {
            return broadcastProcessQueue2;
        }
        long runnableAt = broadcastProcessQueue2.getRunnableAt();
        BroadcastProcessQueue broadcastProcessQueue3 = null;
        BroadcastProcessQueue broadcastProcessQueue4 = broadcastProcessQueue;
        while (broadcastProcessQueue4 != null) {
            if (broadcastProcessQueue4.getRunnableAt() > runnableAt) {
                broadcastProcessQueue2.runnableAtNext = broadcastProcessQueue4;
                broadcastProcessQueue2.runnableAtPrev = broadcastProcessQueue4.runnableAtPrev;
                broadcastProcessQueue4.runnableAtPrev = broadcastProcessQueue2;
                BroadcastProcessQueue broadcastProcessQueue5 = broadcastProcessQueue2.runnableAtPrev;
                if (broadcastProcessQueue5 != null) {
                    broadcastProcessQueue5.runnableAtNext = broadcastProcessQueue2;
                }
                return broadcastProcessQueue4 == broadcastProcessQueue ? broadcastProcessQueue2 : broadcastProcessQueue;
            }
            broadcastProcessQueue3 = broadcastProcessQueue4;
            broadcastProcessQueue4 = broadcastProcessQueue4.runnableAtNext;
        }
        broadcastProcessQueue2.runnableAtPrev = broadcastProcessQueue3;
        broadcastProcessQueue3.runnableAtNext = broadcastProcessQueue2;
        return broadcastProcessQueue;
    }

    public static boolean isDispatchedInQueue(ArrayDeque arrayDeque, Intent intent) {
        SomeArgs someArgs;
        Iterator it = arrayDeque.iterator();
        while (it.hasNext() && (someArgs = (SomeArgs) it.next()) != null) {
            if (intent.filterEquals(((BroadcastRecord) someArgs.arg1).intent)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isQueueEmpty(ArrayDeque arrayDeque) {
        return arrayDeque == null || arrayDeque.isEmpty();
    }

    public static String reasonToString(int i) {
        switch (i) {
            case 0:
                return "EMPTY";
            case 1:
                return "CACHED";
            case 2:
                return "NORMAL";
            case 3:
                return "MAX_PENDING";
            case 4:
                return "BLOCKED";
            case 5:
                return "INSTRUMENTED";
            case 6:
                return "PERSISTENT";
            case 7:
                return "FORCE_DELAYED";
            case 8:
                return "INFINITE_DEFER";
            case 9:
            default:
                return Integer.toString(i);
            case 10:
                return "CONTAINS_FOREGROUND";
            case 11:
                return "CONTAINS_ORDERED";
            case 12:
                return "CONTAINS_ALARM";
            case 13:
                return "CONTAINS_PRIORITIZED";
            case 14:
                return "CONTAINS_INTERACTIVE";
            case 15:
                return "CONTAINS_RESULT_TO";
            case 16:
                return "CONTAINS_INSTRUMENTED";
            case 17:
                return "CONTAINS_MANIFEST";
            case 18:
                return "FOREGROUND";
            case 19:
                return "CORE_UID";
            case 20:
                return "TOP_PROCESS";
            case 21:
                return "PERCEPTIBLE_APP";
        }
    }

    public static BroadcastProcessQueue removeFromRunnableList(BroadcastProcessQueue broadcastProcessQueue, BroadcastProcessQueue broadcastProcessQueue2) {
        if (broadcastProcessQueue == broadcastProcessQueue2) {
            broadcastProcessQueue = broadcastProcessQueue2.runnableAtNext;
        }
        BroadcastProcessQueue broadcastProcessQueue3 = broadcastProcessQueue2.runnableAtNext;
        if (broadcastProcessQueue3 != null) {
            broadcastProcessQueue3.runnableAtPrev = broadcastProcessQueue2.runnableAtPrev;
        }
        BroadcastProcessQueue broadcastProcessQueue4 = broadcastProcessQueue2.runnableAtPrev;
        if (broadcastProcessQueue4 != null) {
            broadcastProcessQueue4.runnableAtNext = broadcastProcessQueue3;
        }
        broadcastProcessQueue2.runnableAtNext = null;
        broadcastProcessQueue2.runnableAtPrev = null;
        return broadcastProcessQueue;
    }

    public boolean addPrioritizeEarliestRequest() {
        int i = this.mCountPrioritizeEarliestRequests;
        if (i != 0) {
            this.mCountPrioritizeEarliestRequests = i + 1;
            return false;
        }
        this.mCountPrioritizeEarliestRequests = i + 1;
        this.mRunnableAtInvalidated = true;
        return true;
    }

    public final String describeStateLocked(long j) {
        StringBuilder sb = new StringBuilder();
        if (isRunnable()) {
            sb.append("runnable at ");
            TimeUtils.formatDuration(getRunnableAt(), j, sb);
        } else {
            sb.append("not runnable");
        }
        sb.append(" because ");
        sb.append(reasonToString(this.mRunnableAtReason));
        return sb.toString();
    }

    public final boolean forEachMatchingBroadcast(BroadcastPredicate broadcastPredicate, BroadcastConsumer broadcastConsumer, boolean z) {
        return forEachMatchingBroadcastInQueue(this.mPendingOffload, broadcastPredicate, broadcastConsumer, z) | forEachMatchingBroadcastInQueue(this.mPending, broadcastPredicate, broadcastConsumer, z) | forEachMatchingBroadcastInQueue(this.mPendingUrgent, broadcastPredicate, broadcastConsumer, z);
    }

    public final boolean forEachMatchingBroadcastInQueue(ArrayDeque arrayDeque, BroadcastPredicate broadcastPredicate, BroadcastConsumer broadcastConsumer, boolean z) {
        Iterator it = arrayDeque.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            SomeArgs someArgs = (SomeArgs) it.next();
            BroadcastRecord broadcastRecord = (BroadcastRecord) someArgs.arg1;
            int i = someArgs.argi1;
            if (broadcastPredicate.test(broadcastRecord, i)) {
                broadcastConsumer.accept(broadcastRecord, i);
                z2 = true;
                if (z) {
                    someArgs.recycle();
                    it.remove();
                    onBroadcastDequeued(broadcastRecord, i);
                } else {
                    this.mRunnableAtInvalidated = true;
                }
            }
        }
        return z2;
    }

    public final BroadcastRecord getActive() {
        BroadcastRecord broadcastRecord = this.mActive;
        Objects.requireNonNull(broadcastRecord);
        return broadcastRecord;
    }

    public final int getActiveIndex() {
        Objects.requireNonNull(this.mActive);
        return this.mActiveIndex;
    }

    public final ArrayDeque getQueueForBroadcast(BroadcastRecord broadcastRecord) {
        return broadcastRecord.urgent ? this.mPendingUrgent : (broadcastRecord.intent.getFlags() & Integer.MIN_VALUE) != 0 ? this.mPendingOffload : this.mPending;
    }

    public final long getRunnableAt() {
        if (this.mRunnableAtInvalidated) {
            updateRunnableAt();
        }
        return this.mRunnableAt;
    }

    public final boolean isActive() {
        return this.mActive != null;
    }

    public final boolean isDeferredUntilActive() {
        if (this.mRunnableAtInvalidated) {
            updateRunnableAt();
        }
        return this.mRunnableAtReason == 8;
    }

    public final boolean isEmpty() {
        return this.mPending.isEmpty() && this.mPendingUrgent.isEmpty() && this.mPendingOffload.isEmpty();
    }

    public final boolean isProcessWarm() {
        ProcessRecord processRecord = this.app;
        return (processRecord == null || processRecord.mOnewayThread == null || processRecord.mKilled) ? false : true;
    }

    public final boolean isRunnable() {
        if (this.mRunnableAtInvalidated) {
            updateRunnableAt();
        }
        return this.mRunnableAt != Long.MAX_VALUE;
    }

    public final void makeActiveNextPending() {
        ArrayDeque queueForNextBroadcast = queueForNextBroadcast();
        if (queueForNextBroadcast == this.mPendingUrgent) {
            this.mActiveCountConsecutiveUrgent++;
        } else if (queueForNextBroadcast == this.mPending) {
            this.mActiveCountConsecutiveUrgent = 0;
            this.mActiveCountConsecutiveNormal++;
        } else if (queueForNextBroadcast == this.mPendingOffload) {
            this.mActiveCountConsecutiveUrgent = 0;
            this.mActiveCountConsecutiveNormal = 0;
        }
        SomeArgs someArgs = !isQueueEmpty(queueForNextBroadcast) ? (SomeArgs) queueForNextBroadcast.removeFirst() : null;
        BroadcastRecord broadcastRecord = (BroadcastRecord) someArgs.arg1;
        this.mActive = broadcastRecord;
        int i = someArgs.argi1;
        this.mActiveIndex = i;
        this.mActiveReEnqueued = someArgs.argi2 == 1;
        this.mActiveCountSinceIdle++;
        this.mActiveAssumedDeliveryCountSinceIdle = (((broadcastRecord.receivers.get(i) instanceof BroadcastFilter) && !broadcastRecord.ordered && broadcastRecord.resultTo == null) ? 1 : 0) + this.mActiveAssumedDeliveryCountSinceIdle;
        this.mActiveViaColdStart = false;
        this.mActiveWasStopped = false;
        someArgs.recycle();
        onBroadcastDequeued(this.mActive, this.mActiveIndex);
    }

    public final void onBroadcastDequeued(BroadcastRecord broadcastRecord, int i) {
        boolean booleanValue = ((Boolean) broadcastRecord.mMARsTargetReceiver.get(i)).booleanValue();
        this.mCountEnqueued--;
        if (broadcastRecord.deferUntilActive || booleanValue) {
            this.mCountDeferred--;
        }
        if (broadcastRecord.isForeground()) {
            if (broadcastRecord.deferUntilActive || booleanValue) {
                this.mCountForegroundDeferred--;
            }
            this.mCountForeground--;
        }
        if (broadcastRecord.ordered) {
            this.mCountOrdered--;
        }
        if (broadcastRecord.alarm) {
            this.mCountAlarm--;
        }
        if (broadcastRecord.prioritized) {
            if (broadcastRecord.deferUntilActive || booleanValue) {
                this.mCountPrioritizedDeferred--;
            }
            this.mCountPrioritized--;
        }
        if (broadcastRecord.interactive) {
            this.mCountInteractive--;
        }
        if (broadcastRecord.resultTo != null) {
            this.mCountResultTo--;
        }
        if (broadcastRecord.callerInstrumented) {
            this.mCountInstrumented--;
        }
        if (broadcastRecord.receivers.get(i) instanceof ResolveInfo) {
            this.mCountManifest--;
        }
        this.mRunnableAtInvalidated = true;
    }

    public final void onBroadcastEnqueued(BroadcastRecord broadcastRecord, int i) {
        boolean booleanValue = ((Boolean) broadcastRecord.mMARsTargetReceiver.get(i)).booleanValue();
        this.mCountEnqueued++;
        if (broadcastRecord.deferUntilActive || booleanValue) {
            this.mCountDeferred++;
        }
        if (broadcastRecord.isForeground()) {
            if (broadcastRecord.deferUntilActive || booleanValue) {
                this.mCountForegroundDeferred++;
            }
            this.mCountForeground++;
        }
        if (broadcastRecord.ordered) {
            this.mCountOrdered++;
        }
        if (broadcastRecord.alarm) {
            this.mCountAlarm++;
        }
        if (broadcastRecord.prioritized) {
            if (broadcastRecord.deferUntilActive || booleanValue) {
                this.mCountPrioritizedDeferred++;
            }
            this.mCountPrioritized++;
        }
        if (broadcastRecord.interactive) {
            this.mCountInteractive++;
        }
        if (broadcastRecord.resultTo != null) {
            this.mCountResultTo++;
        }
        if (broadcastRecord.callerInstrumented) {
            this.mCountInstrumented++;
        }
        if (broadcastRecord.receivers.get(i) instanceof ResolveInfo) {
            this.mCountManifest++;
        }
        this.mRunnableAtInvalidated = true;
    }

    public BroadcastRecord peekNextBroadcastRecord() {
        ArrayDeque queueForNextBroadcast = queueForNextBroadcast();
        if (isQueueEmpty(queueForNextBroadcast)) {
            return null;
        }
        return (BroadcastRecord) ((SomeArgs) queueForNextBroadcast.peekFirst()).arg1;
    }

    public final ArrayDeque queueForNextBroadcast() {
        ArrayDeque arrayDeque = this.mPending;
        ArrayDeque arrayDeque2 = this.mPendingOffload;
        int i = this.mActiveCountConsecutiveNormal;
        BroadcastConstants broadcastConstants = this.constants;
        return queueForNextBroadcast(this.mPendingUrgent, queueForNextBroadcast(arrayDeque, arrayDeque2, i, broadcastConstants.MAX_CONSECUTIVE_NORMAL_DISPATCHES), this.mActiveCountConsecutiveUrgent, broadcastConstants.MAX_CONSECUTIVE_URGENT_DISPATCHES);
    }

    public final ArrayDeque queueForNextBroadcast(ArrayDeque arrayDeque, ArrayDeque arrayDeque2, int i, int i2) {
        if (isQueueEmpty(arrayDeque)) {
            return arrayDeque2;
        }
        if (isQueueEmpty(arrayDeque2)) {
            return arrayDeque;
        }
        SomeArgs someArgs = (SomeArgs) arrayDeque2.peekFirst();
        BroadcastRecord broadcastRecord = (BroadcastRecord) someArgs.arg1;
        return ((this.mCountPrioritizeEarliestRequests > 0 || i >= i2) && broadcastRecord.enqueueTime <= ((BroadcastRecord) ((SomeArgs) arrayDeque.peekFirst()).arg1).enqueueTime && broadcastRecord.beyondCount >= broadcastRecord.blockedUntilBeyondCount[someArgs.argi1]) ? arrayDeque2 : arrayDeque;
    }

    public final boolean setProcessAndUidState(ProcessRecord processRecord, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        this.app = processRecord;
        this.mCachedToString = null;
        this.mCachedToShortString = null;
        boolean z9 = true;
        if (processRecord == null) {
            if (this.mUidForeground) {
                this.mUidForeground = false;
                this.mRunnableAtInvalidated = true;
                z3 = true;
            } else {
                z3 = false;
            }
            if (this.mProcessFreezable) {
                this.mProcessFreezable = false;
                this.mRunnableAtInvalidated = true;
                z4 = true;
            } else {
                z4 = false;
            }
            boolean z10 = z3 | z4;
            if (this.mProcessInstrumented) {
                this.mProcessInstrumented = false;
                this.mRunnableAtInvalidated = true;
                z5 = true;
            } else {
                z5 = false;
            }
            boolean z11 = z10 | z5;
            if (this.mProcessPersistent) {
                this.mProcessPersistent = false;
                this.mRunnableAtInvalidated = true;
            } else {
                z9 = false;
            }
            return z11 | z9;
        }
        if (this.mUidForeground != z) {
            this.mUidForeground = z;
            this.mRunnableAtInvalidated = true;
            z6 = true;
        } else {
            z6 = false;
        }
        if (this.mProcessFreezable != z2) {
            this.mProcessFreezable = z2;
            this.mRunnableAtInvalidated = true;
            z7 = true;
        } else {
            z7 = false;
        }
        boolean z12 = z6 | z7;
        boolean z13 = processRecord.mInstr != null;
        if (this.mProcessInstrumented != z13) {
            this.mProcessInstrumented = z13;
            this.mRunnableAtInvalidated = true;
            z8 = true;
        } else {
            z8 = false;
        }
        boolean z14 = z12 | z8;
        boolean z15 = processRecord.mPersistent;
        if (this.mProcessPersistent != z15) {
            this.mProcessPersistent = z15;
            this.mRunnableAtInvalidated = true;
        } else {
            z9 = false;
        }
        return z14 | z9;
    }

    public boolean shouldBeDeferred() {
        if (this.mRunnableAtInvalidated) {
            updateRunnableAt();
        }
        int i = this.mRunnableAtReason;
        return i == 1 || i == 8;
    }

    public final String toShortString() {
        if (this.mCachedToShortString == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" ");
            ProcessRecord processRecord = this.app;
            sb.append(processRecord != null ? Integer.valueOf(processRecord.mPid) : "?");
            sb.append(":");
            sb.append(this.processName);
            sb.append("/");
            sb.append(UserHandle.formatUid(this.uid));
            this.mCachedToShortString = sb.toString();
        }
        return this.mCachedToShortString;
    }

    public final String toString() {
        if (this.mCachedToString == null) {
            this.mCachedToString = "BroadcastProcessQueue{" + toShortString() + "}";
        }
        return this.mCachedToString;
    }

    public final void traceProcessRunningBegin() {
        Trace.asyncTraceForTrackBegin(64L, this.runningTraceTrackName, toShortString() + " running", hashCode());
    }

    public final void updateRunnableAt() {
        if (this.mRunnableAtInvalidated) {
            this.mRunnableAtInvalidated = false;
            ArrayDeque queueForNextBroadcast = queueForNextBroadcast();
            SomeArgs someArgs = !isQueueEmpty(queueForNextBroadcast) ? (SomeArgs) queueForNextBroadcast.peekFirst() : null;
            if (someArgs == null) {
                this.mRunnableAt = Long.MAX_VALUE;
                this.mRunnableAtReason = 0;
                return;
            }
            BroadcastRecord broadcastRecord = (BroadcastRecord) someArgs.arg1;
            int i = someArgs.argi1;
            long j = broadcastRecord.enqueueTime;
            if (broadcastRecord.beyondCount < broadcastRecord.blockedUntilBeyondCount[i]) {
                this.mRunnableAt = Long.MAX_VALUE;
                this.mRunnableAtReason = 4;
                return;
            }
            long j2 = this.mForcedDelayedDurationMs;
            BroadcastConstants broadcastConstants = this.constants;
            if (j2 > 0) {
                this.mRunnableAt = j2 + j;
                this.mRunnableAtReason = 7;
            } else if (this.mCountForeground > this.mCountForegroundDeferred) {
                this.mRunnableAt = broadcastConstants.DELAY_URGENT_MILLIS + j;
                this.mRunnableAtReason = 10;
            } else if (this.mCountInteractive > 0) {
                this.mRunnableAt = broadcastConstants.DELAY_URGENT_MILLIS + j;
                this.mRunnableAtReason = 14;
            } else if (this.mCountInstrumented > 0) {
                this.mRunnableAt = broadcastConstants.DELAY_URGENT_MILLIS + j;
                this.mRunnableAtReason = 16;
            } else if (this.mProcessInstrumented) {
                this.mRunnableAt = broadcastConstants.DELAY_URGENT_MILLIS + j;
                this.mRunnableAtReason = 5;
            } else if (this.mUidForeground) {
                this.mRunnableAt = broadcastConstants.DELAY_FOREGROUND_PROC_MILLIS + j;
                this.mRunnableAtReason = 18;
            } else {
                ProcessRecord processRecord = this.app;
                if (processRecord != null && processRecord.mState.mSetProcState == 2) {
                    this.mRunnableAt = broadcastConstants.DELAY_FOREGROUND_PROC_MILLIS + j;
                    this.mRunnableAtReason = 20;
                } else if (this.mProcessPersistent) {
                    this.mRunnableAt = broadcastConstants.DELAY_PERSISTENT_PROC_MILLIS + j;
                    this.mRunnableAtReason = 6;
                } else {
                    int i2 = this.uid;
                    if (i2 == 1000) {
                        this.mRunnableAt = j;
                        this.mRunnableAtReason = 19;
                    } else if (this.mCountOrdered > 0) {
                        this.mRunnableAt = j;
                        this.mRunnableAtReason = 11;
                    } else if (this.mCountAlarm > 0) {
                        this.mRunnableAt = j;
                        this.mRunnableAtReason = 12;
                    } else if (this.mCountPrioritized > this.mCountPrioritizedDeferred) {
                        this.mRunnableAt = j;
                        this.mRunnableAtReason = 13;
                    } else if (this.mCountManifest > 0) {
                        this.mRunnableAt = j;
                        this.mRunnableAtReason = 17;
                    } else if (this.mProcessFreezable) {
                        if (!broadcastRecord.deferUntilActive && !((Boolean) broadcastRecord.mMARsTargetReceiver.get(i)).booleanValue()) {
                            this.mRunnableAt = broadcastConstants.DELAY_CACHED_MILLIS + j;
                            this.mRunnableAtReason = 1;
                        } else if (this.mCountDeferred == this.mCountEnqueued) {
                            this.mRunnableAt = Long.MAX_VALUE;
                            this.mRunnableAtReason = 8;
                        } else if (broadcastRecord.isForeground()) {
                            this.mRunnableAt = broadcastConstants.DELAY_URGENT_MILLIS + j;
                            this.mRunnableAtReason = 10;
                        } else if (broadcastRecord.prioritized) {
                            this.mRunnableAt = j;
                            this.mRunnableAtReason = 13;
                        } else if (broadcastRecord.resultTo != null) {
                            this.mRunnableAt = j;
                            this.mRunnableAtReason = 15;
                        } else {
                            this.mRunnableAt = broadcastConstants.DELAY_CACHED_MILLIS + j;
                            this.mRunnableAtReason = 1;
                        }
                    } else if (this.mCountResultTo > 0) {
                        this.mRunnableAt = j;
                        this.mRunnableAtReason = 15;
                    } else if (UserHandle.isCore(i2)) {
                        this.mRunnableAt = j;
                        this.mRunnableAtReason = 19;
                    } else {
                        ProcessRecord processRecord2 = this.app;
                        if (processRecord2 == null || processRecord2.mState.mSetAdj > 200) {
                            this.mRunnableAt = broadcastConstants.DELAY_NORMAL_MILLIS + j;
                            this.mRunnableAtReason = 2;
                        } else {
                            this.mRunnableAt = j;
                            this.mRunnableAtReason = 21;
                        }
                    }
                }
            }
            if (this.mPendingOffload.size() + this.mPendingUrgent.size() + this.mPending.size() >= broadcastConstants.MAX_PENDING_BROADCASTS) {
                this.mRunnableAt = Math.min(this.mRunnableAt, j);
                this.mRunnableAtReason = 3;
            }
        }
    }
}
